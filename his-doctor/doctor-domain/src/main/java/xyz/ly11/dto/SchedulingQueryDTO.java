package xyz.ly11.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 29794
 * @date 2/12/2021 14:48
 * 排版信息查询数据传输对象
 */
@ApiModel(value = "xyx-ly11-dto-SchedulingQueryDTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchedulingQueryDTO implements Serializable {
    private static final long serialVersionUID = 506626123621506525L;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 部门ID
     */
    private Long deptId;
    /**
     * 页面传过来的的上一周下一周
     */
    private String queryDate;

    /**
     * 开始时间
     */
    private String beginDate;
    /**
     * 结束时间
     */
    private String endDate;


}