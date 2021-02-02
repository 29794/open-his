package xyz.ly11.controller.erp;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.ly11.dto.InventoryLogDTO;
import xyz.ly11.service.InventoryLogService;
import xyz.ly11.vo.AjaxResult;
import xyz.ly11.vo.DataGridView;

/**
 * @author 29794
 * @date 2/2/2021 22:20
 * 入库记录Controller
 */
@Slf4j
@RestController
@RequestMapping("erp/inventoryLog")
public class InventoryLogController {

    @Reference
    InventoryLogService inventoryLogService;

    /**
     * 分页查询
     */
    @GetMapping("listInventoryLogForPage")
    @HystrixCommand
    public AjaxResult listMedicinesForPage(InventoryLogDTO inventoryLogDTO) {
        DataGridView gridView = this.inventoryLogService.listInventoryLogPage(inventoryLogDTO);
        return AjaxResult.success("查询成功", gridView.getData(), gridView.getTotal());
    }


}