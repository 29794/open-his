package xyz.ly11.service;

import xyz.ly11.domain.Scheduling;
import xyz.ly11.dto.SchedulingFormDTO;
import xyz.ly11.dto.SchedulingQueryDTO;

import java.util.List;

/**
 * @author 29794
 * @date 2/12/2021 11:40
 */
public interface SchedulingService {

    /**
     * 查询排班的数据
     */
    List<Scheduling> queryScheduling(SchedulingQueryDTO schedulingQueryDTO);

    /**
     * 保存排班的数据
     */
    int saveScheduling(SchedulingFormDTO schedulingFormDTO);


}