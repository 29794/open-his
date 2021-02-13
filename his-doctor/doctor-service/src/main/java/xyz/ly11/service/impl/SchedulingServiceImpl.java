package xyz.ly11.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import xyz.ly11.domain.Scheduling;
import xyz.ly11.dto.SchedulingFormDTO;
import xyz.ly11.dto.SchedulingQueryDTO;
import xyz.ly11.mapper.SchedulingMapper;
import xyz.ly11.service.SchedulingService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 29794
 * @date 2/12/2021 11:37
 */
@Service
@RequiredArgsConstructor
public class SchedulingServiceImpl implements SchedulingService {

    final SchedulingMapper schedulingMapper;

    @Override
    public List<Scheduling> queryScheduling(SchedulingQueryDTO schedulingDTO) {
        QueryWrapper<Scheduling> qw = new QueryWrapper<>();
        qw.eq(null != schedulingDTO.getUserId(), Scheduling.COL_USER_ID, schedulingDTO.getUserId());
        qw.eq(null != schedulingDTO.getDeptId(), Scheduling.COL_DEPT_ID, schedulingDTO.getDeptId());
        qw.ge(StringUtils.isNotBlank(schedulingDTO.getBeginDate()), Scheduling.COL_SCHEDULING_DAY, schedulingDTO.getBeginDate());
        qw.le(StringUtils.isNotBlank(schedulingDTO.getEndDate()), Scheduling.COL_SCHEDULING_DAY, schedulingDTO.getEndDate());
        return this.schedulingMapper.selectList(qw);
    }

    @Override
    public int saveScheduling(SchedulingFormDTO schedulingFormDTO) {
        if (null != schedulingFormDTO.getData() && schedulingFormDTO.getData().size() > 0) {
            DateTime dateTime = DateUtil.parse(schedulingFormDTO.getBeginDate(), "yyyy-MM-dd");
            DateTime date = DateUtil.beginOfWeek(dateTime);
            String beginDate = DateUtil.format(date, "yyyy-MM-dd");
            String endDate = DateUtil.format(DateUtil.endOfWeek(dateTime), "yyyy-MM-dd");
            SchedulingFormDTO.SchedulingData schedulingData = schedulingFormDTO.getData().get(0);
            Long userId = schedulingData.getUserId();
            Long deptId = schedulingData.getDeptId();
            if (null != userId) {
                //删除原来的排班
                QueryWrapper<Scheduling> qw = new QueryWrapper<>();
                qw.eq(Scheduling.COL_USER_ID, userId);
                qw.eq(Scheduling.COL_DEPT_ID, deptId);
                qw.between(Scheduling.COL_SCHEDULING_DAY, beginDate, endDate);
                this.schedulingMapper.delete(qw);
                //添加新的排班
                List<String> schedulingDays = initSchedulingDay(date);
                for (SchedulingFormDTO.SchedulingData d : schedulingFormDTO.getData()) {
                    Scheduling scheduling;
                    int i = 0;
                    for (String s : d.getSchedulingType()) {
                        if (StringUtils.isNotBlank(s)) {
                            scheduling = new Scheduling(userId, deptId, schedulingDays.get(i), d.getSubsectionType(), s, new Date(), schedulingFormDTO.getSimpleUser().getUserName());
                            this.schedulingMapper.insert(scheduling);
                        }
                        i++;
                    }
                }
                //受影响的行数
                return 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    /**
     * 初始化值班记录
     *
     * @param startDate 当周第一天
     * @return
     */
    private static List<String> initSchedulingDay(Date startDate) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            DateTime d = DateUtil.offsetDay(startDate, i);
            String key = DateUtil.format(d, "yyyy-MM-dd");
            list.add(key);
        }
        return list;
    }


}