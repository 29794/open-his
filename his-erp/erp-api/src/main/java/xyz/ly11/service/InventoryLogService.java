package xyz.ly11.service;

import xyz.ly11.dto.InventoryLogDTO;
import xyz.ly11.vo.DataGridView;

/**
 * @author 29794
 * @date 2/2/2021 22:15
 */
public interface InventoryLogService {

    /**
     * 分页查询
     */
    DataGridView listInventoryLogPage(InventoryLogDTO inventoryLogDTO);


}