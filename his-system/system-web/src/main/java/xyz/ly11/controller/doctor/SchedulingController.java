package xyz.ly11.controller.doctor;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.ly11.controller.BaseController;
import xyz.ly11.domain.Scheduling;
import xyz.ly11.domain.User;
import xyz.ly11.dto.SchedulingDTO;
import xyz.ly11.dto.SchedulingFormDTO;
import xyz.ly11.dto.SchedulingQueryDTO;
import xyz.ly11.service.SchedulingService;
import xyz.ly11.service.UserService;
import xyz.ly11.utils.ShiroSecurityUtils;
import xyz.ly11.vo.AjaxResult;

import java.util.*;

/**
 * @author 29794
 * @date 2/12/2021 14:54
 */
@RequestMapping("/doctor/scheduling")
@RestController
public class SchedulingController extends BaseController {

    @Reference
    SchedulingService schedulingService;

    @Autowired
    UserService userService;

    /**
     * 查询排班的医生信息
     * 如果部门ID为空，那么查询所有要排班的用户信息
     *
     * @param deptId 部门id
     * @return 查询结果
     */
    @GetMapping("queryUsersNeedScheduling")
    public AjaxResult queryUserNeedScheduling(Long deptId) {
        List<User> users = this.userService.querySchedulingUsers(null, deptId);
        return AjaxResult.success(users);
    }

    /**
     * 查询需要排班的医生的排班信息
     *
     * @param schedulingQueryDTO 查询条件
     * @return 查询结果
     */
    @HystrixCommand
    @GetMapping("queryScheduling")
    public AjaxResult queryScheduling(SchedulingQueryDTO schedulingQueryDTO) {
        // 根据部门的id以及用户id查询，如果用户的id和部门的id为空，那就直接查询所有需要排班的用户信息
        List<User> users = userService.querySchedulingUsers(schedulingQueryDTO.getUserId(), schedulingQueryDTO.getDeptId());
        return this.getSchedulingAjaxResult(schedulingQueryDTO, users);
    }

    /**
     * 查询当前登陆用户的的排班信息
     */
    @GetMapping("queryMyScheduling")
    @HystrixCommand
    public AjaxResult queryMyScheduling(SchedulingQueryDTO schedulingQueryDTO) {
        //根据部门ID用户ID查询用户信息 如果用户ID和部门ID都为空，那么就查询所有要排班的用户信息
        List<User> users = userService.querySchedulingUsers(Long.valueOf(ShiroSecurityUtils.getCurrentSimpleUser().getUserId().toString()), schedulingQueryDTO.getDeptId());
        return this.getSchedulingAjaxResult(schedulingQueryDTO, users);
    }

    /**
     * 核心的构造页面数据的方法
     */
    private AjaxResult getSchedulingAjaxResult(SchedulingQueryDTO schedulingQueryDTO, List<User> users) {
        // 获取当前的时间
        DateTime date = DateUtil.date();
        if (StringUtils.isNotBlank(schedulingQueryDTO.getQueryDate())) {
            date = DateUtil.parse(schedulingQueryDTO.getQueryDate(), "yyyy-MM-dd");
            // 根据传入的查询日期判断是周几
            // 1表示周日，2表示周一
            int i = DateUtil.dayOfWeek(date);
            if (i == 1) {
                // 下一周周一的日期
                date = DateUtil.offsetDay(date, 1);
            } else {
                //上一周周天的日期
                date = DateUtil.offsetDay(date, -1);
            }
        }
        // 计算一周的开始日期和结束日期
        DateTime beginTime = DateUtil.beginOfWeek(date);
        DateTime endTime = DateUtil.endOfWeek(date);
        // 把一周的开始时间和结束时间封装到schedulingQueryDTO中
        schedulingQueryDTO.setBeginDate(DateUtil.format(beginTime, "yyyy-MM-dd"));
        schedulingQueryDTO.setEndDate(DateUtil.format(endTime, "yyyy-MM-dd"));
        // 根据开始时间和结束时间查询数据库的数据
        List<Scheduling> list = this.schedulingService.queryScheduling(schedulingQueryDTO);
        // 构建页面需要的数据传输对象
        ArrayList<SchedulingDTO> schedulingDTOS = new ArrayList<>();
        //利用用户数据循环一下
        users.forEach(user -> {
            SchedulingDTO schedulingDTO1 = new SchedulingDTO(user.getUserId(), user.getDeptId(), "1", initMap(beginTime));
            SchedulingDTO schedulingDTO2 = new SchedulingDTO(user.getUserId(), user.getDeptId(), "2", initMap(beginTime));
            SchedulingDTO schedulingDTO3 = new SchedulingDTO(user.getUserId(), user.getDeptId(), "3", initMap(beginTime));
            // 一个用户循环放三条数据
            schedulingDTOS.add(schedulingDTO1);
            schedulingDTOS.add(schedulingDTO2);
            schedulingDTOS.add(schedulingDTO3);
            list.forEach(scheduling -> {
                // 获取数据库中的用户id
                Long userId = scheduling.getUserId();
                // 得到早中晚的类型
                String subsectionType = scheduling.getSubsectionType();
                // 值班日期
                String schedulingDay = scheduling.getSchedulingDay();
                if (user.getUserId().equals(userId)) {
                    switch (subsectionType) {
                        case "1":
                            Map<String, String> record1 = schedulingDTO1.getRecord();
                            record1.put(schedulingDay, scheduling.getSchedulingType());
                            break;
                        case "2":
                            Map<String, String> record2 = schedulingDTO2.getRecord();
                            record2.put(schedulingDay, scheduling.getSchedulingType());
                            break;
                        case "3":
                            Map<String, String> record3 = schedulingDTO3.getRecord();
                            record3.put(schedulingDay, scheduling.getSchedulingType());
                            break;
                    }
                }
            });
            //把map转成数组数据放到schedulingType
            schedulingDTO1.setSchedulingType(schedulingDTO1.getRecord().values());
            schedulingDTO2.setSchedulingType(schedulingDTO2.getRecord().values());
            schedulingDTO3.setSchedulingType(schedulingDTO3.getRecord().values());
        });
        // 组装需要返回的数据对象
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("tableData", schedulingDTOS);

        Map<String, Object> schedulingData = new HashMap<>();
        schedulingData.put("startTimeThisWeek", schedulingQueryDTO.getBeginDate());
        schedulingData.put("endTimeThisWeek", schedulingQueryDTO.getEndDate());
        resMap.put("schedulingData", schedulingData);
        resMap.put("labelNames", initLabel(beginTime));
        return AjaxResult.success(resMap);
    }

    /**
     * 初始化labelNames
     *
     * @param beginTime 一周的开始时间
     * @return 周数据
     */
    private String[] initLabel(DateTime beginTime) {
        String[] labelNames = new String[7];
        for (int i = 0; i < 7; i++) {
            DateTime d = DateUtil.offsetDay(beginTime, i);
            labelNames[i] = DateUtil.format(d, "yyyy-MM-dd") + formatterWeek(i);
        }
        return labelNames;
    }

    /**
     * 翻译周
     */
    private String formatterWeek(int i) {
        switch (i) {
            case 0:
                return "(周一)";
            case 1:
                return "(周二)";
            case 2:
                return "(周三)";
            case 3:
                return "(周四)";
            case 4:
                return "(周五)";
            case 5:
                return "(周六)";
            default:
                return "(周日)";
        }
    }

    /**
     * 初始化值班记录
     */
    private Map<String, String> initMap(DateTime beginTime) {
        Map<String, String> map = new TreeMap<>();//按顺序放 2020-08-03 -  2020-08-09
        for (int i = 0; i < 7; i++) {
            DateTime d = DateUtil.offsetDay(beginTime, i);
            String key = DateUtil.format(d, "yyyy-MM-dd");
            map.put(key, "");
        }
        return map;
    }

    /**
     * 保存排班数据
     */
    @PostMapping("saveScheduling")
//    @HystrixCommand
    public AjaxResult saveScheduling(@RequestBody SchedulingFormDTO schedulingFormDTO) {
        schedulingFormDTO.setSimpleUser(ShiroSecurityUtils.getCurrentSimpleUser());
        return AjaxResult.toAjax(this.schedulingService.saveScheduling(schedulingFormDTO));
    }


}