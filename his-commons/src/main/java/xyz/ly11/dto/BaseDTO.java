package xyz.ly11.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import xyz.ly11.domain.SimpleUser;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author by 29794
 * @date 2020/10/9 1:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseDTO implements Serializable {

    private static final long serialVersionUID = -2363571738662171335L;

    /**
     * 页码 默认1
     */
    private Integer pageNum = 1;

    /**
     * 每页显示条数 默认10
     */
    private Integer pageSize = 10;

    /**
     * 当前操作对象
     */
    private SimpleUser simpleUser;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginTime;
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endTime;


}
