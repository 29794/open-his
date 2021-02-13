package xyz.ly11.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @author 29794
 * @date 2/12/2021 11:40
 * 班值的数据传输对象
 */
@ApiModel(value = "xyz-ly11-domain-SchedulingDTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "his_scheduling")
public class SchedulingDTO implements Serializable {
    private static final long serialVersionUID = -1385971518912763955L;

    private Long userId;

    private Long deptId;
    /**
     * 上午 下午  晚上
     */
    private String subsectionType;
    /**
     * 星期的值班值
     */
    private Collection<String> schedulingType;
    /**
     * 星期值班的记录 key 是日期
     */
    @JsonIgnore
    private Map<String, String> record;


    public SchedulingDTO(Long userId, Long deptId, String subsectionType, Map<String, String> map) {
        this.userId = userId;
        this.subsectionType = subsectionType;
        this.record = map;
        this.deptId = deptId;
    }
}