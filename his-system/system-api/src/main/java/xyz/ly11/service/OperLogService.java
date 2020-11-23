package xyz.ly11.service;

import xyz.ly11.domain.OperLog;
import xyz.ly11.dto.OperLogDTO;
import xyz.ly11.vo.DataGridView;

public interface OperLogService {

    /**
     * 插入操作日志
     *
     * @param operLog
     */
    void insertOperLog(OperLog operLog);

    /**
     * 分页查询操作日志
     *
     * @param operLogDTO
     * @return
     */
    DataGridView listForPage(OperLogDTO operLogDTO);

    /**
     * 根据ID删除操作日志
     *
     * @param infoIds
     * @return
     */
    int deleteOperLogByIds(Long[] infoIds);

    /**
     * 清空操作日志
     *
     * @return
     */
    int clearAllOperLog();


}

