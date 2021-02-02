package xyz.ly11.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import xyz.ly11.domain.InventoryLog;
import xyz.ly11.dto.InventoryLogDTO;
import xyz.ly11.mapper.InventoryLogMapper;
import xyz.ly11.service.InventoryLogService;
import xyz.ly11.vo.DataGridView;

/**
 * @author 29794
 * @date 2/2/2021 22:17
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryLogServiceImpl implements InventoryLogService {

    final
    private InventoryLogMapper inventoryLogMapper;

    @Override
    public DataGridView listInventoryLogPage(InventoryLogDTO inventoryLogDTO) {
        Page<InventoryLog> page=new Page<>(inventoryLogDTO.getPageNum(),inventoryLogDTO.getPageSize());
        QueryWrapper<InventoryLog> qw=new QueryWrapper<>();
        qw.like(StringUtils.isNotBlank(inventoryLogDTO.getPurchaseId()),InventoryLog.COL_PURCHASE_ID,inventoryLogDTO.getPurchaseId());
        qw.like(StringUtils.isNotBlank(inventoryLogDTO.getMedicinesName()),InventoryLog.COL_MEDICINES_NAME,inventoryLogDTO.getMedicinesName());
        qw.eq(StringUtils.isNotBlank(inventoryLogDTO.getMedicinesType()),InventoryLog.COL_MEDICINES_TYPE,inventoryLogDTO.getMedicinesType());
        qw.eq(StringUtils.isNotBlank(inventoryLogDTO.getPrescriptionType()),InventoryLog.COL_PRESCRIPTION_TYPE,inventoryLogDTO.getPrescriptionType());
        qw.eq(StringUtils.isNotBlank(inventoryLogDTO.getProducterId()),InventoryLog.COL_PRODUCTER_ID,inventoryLogDTO.getProducterId());
        qw.eq(StringUtils.isNotBlank(inventoryLogDTO.getPrescriptionType()),InventoryLog.COL_PRESCRIPTION_TYPE,inventoryLogDTO.getPrescriptionType());

        qw.ge(inventoryLogDTO.getBeginTime()!=null,InventoryLog.COL_CREATE_TIME,inventoryLogDTO.getBeginTime());
        qw.le(inventoryLogDTO.getEndTime()!=null,InventoryLog.COL_CREATE_TIME,inventoryLogDTO.getEndTime());
        qw.orderByDesc(InventoryLog.COL_CREATE_TIME);
        this.inventoryLogMapper.selectPage(page,qw);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

}