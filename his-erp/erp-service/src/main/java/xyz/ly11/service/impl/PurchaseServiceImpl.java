package xyz.ly11.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.ly11.constants.Constants;
import xyz.ly11.domain.*;
import xyz.ly11.dto.PurchaseDTO;
import xyz.ly11.dto.PurchaseFormDTO;
import xyz.ly11.dto.PurchaseItemDTO;
import xyz.ly11.mapper.InventoryLogMapper;
import xyz.ly11.mapper.MedicinesMapper;
import xyz.ly11.mapper.PurchaseItemMapper;
import xyz.ly11.mapper.PurchaseMapper;
import xyz.ly11.service.PurchaseService;
import xyz.ly11.utils.IdGeneratorSnowflake;
import xyz.ly11.vo.DataGridView;

import java.util.Collections;
import java.util.List;

/**
 * @author 29794
 * @date 2/1/2021 20:50
 * 采购入库的业务接口的具体实现
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    final PurchaseMapper purchaseMapper;

    final PurchaseItemMapper purchaseItemMapper;

    final InventoryLogMapper inventoryLogMapper;

    final MedicinesMapper medicinesMapper;

    @Override
    public DataGridView listPurchasePage(PurchaseDTO purchaseDTO) {
        Page<Purchase> page = new Page<>(purchaseDTO.getPageNum(), purchaseDTO.getPageSize());
        QueryWrapper<Purchase> qw = new QueryWrapper<>();
        qw.eq(StringUtils.isNotBlank(purchaseDTO.getProviderId()), Purchase.COL_PROVIDER_ID, purchaseDTO.getProviderId());
        qw.eq(StringUtils.isNotBlank(purchaseDTO.getStatus()), Purchase.COL_STATUS, purchaseDTO.getStatus());
        qw.like(StringUtils.isNotBlank(purchaseDTO.getApplyUserName()), Purchase.COL_APPLY_USER_NAME, purchaseDTO.getApplyUserName());
        qw.orderByDesc(Purchase.COL_CREATE_TIME);
        this.purchaseMapper.selectPage(page, qw);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @Override
    public Purchase getPurchaseById(String purchaseId) {
        return this.purchaseMapper.selectById(purchaseId);
    }

    @Override
    public int doAudit(String purchaseId, SimpleUser simpleUser) {
        Purchase purchase = this.purchaseMapper.selectById(purchaseId);
        purchase.setStatus(Constants.STOCK_PURCHASE_STATUS_2);//设置状态为待审核
        purchase.setApplyUserName(simpleUser.getUserName());
        purchase.setApplyUserId(Long.valueOf(simpleUser.getUserId().toString()));
        return this.purchaseMapper.updateById(purchase);
    }

    @Override
    public int doInvalid(String purchaseId) {
        Purchase purchase = this.purchaseMapper.selectById(purchaseId);
        purchase.setStatus(Constants.STOCK_PURCHASE_STATUS_5);//设置状态为作废
        return this.purchaseMapper.updateById(purchase);
    }

    @Override
    public int auditPass(String purchaseId) {
        Purchase purchase = this.purchaseMapper.selectById(purchaseId);
        purchase.setStatus(Constants.STOCK_PURCHASE_STATUS_3);//设置状态为审核通过
        return this.purchaseMapper.updateById(purchase);
    }

    @Override
    public int auditNoPass(String purchaseId, String auditMsg) {
        Purchase purchase = this.purchaseMapper.selectById(purchaseId);
        purchase.setStatus(Constants.STOCK_PURCHASE_STATUS_4);//设置状态为审核不通过
        purchase.setExamine(auditMsg);
        return this.purchaseMapper.updateById(purchase);
    }

    @Override
    public List<PurchaseItem> getPurchaseItemById(String purchaseId) {
        if (null != purchaseId) {
            QueryWrapper<PurchaseItem> qw = new QueryWrapper<>();
            qw.eq(PurchaseItem.COL_PURCHASE_ID, purchaseId);
            return purchaseItemMapper.selectList(qw);
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * 暂存   采购单的状态是Constants.STOCK_PURCHASE_STATUS_1
     *
     * @param purchaseFormDTO 订购单表单
     * @return 暂存结果
     */
    @Override
    public int addPurchaseAndItem(PurchaseFormDTO purchaseFormDTO) {
        Purchase purchase = this.purchaseMapper.selectById(purchaseFormDTO.getPurchaseDto().getPurchaseId());
        if (null != purchase) {
            //删除之前的数据
            this.purchaseMapper.deleteById(purchase.getPurchaseId());
            //删除之前的详情数据
            QueryWrapper<PurchaseItem> qw = new QueryWrapper<>();
            qw.eq(PurchaseItem.COL_PURCHASE_ID, purchase.getPurchaseId());
            this.purchaseItemMapper.delete(qw);
        }
        //保存采购单主表数据
        Purchase newPurchase = new Purchase();
        BeanUtil.copyProperties(purchaseFormDTO.getPurchaseDto(), newPurchase);
        newPurchase.setStatus(Constants.STOCK_PURCHASE_STATUS_1);//未提交状态
        newPurchase.setCreateBy(purchaseFormDTO.getPurchaseDto().getSimpleUser().getUserName());
        newPurchase.setCreateTime(DateUtil.date());
        int a = this.purchaseMapper.insert(newPurchase);
        //保存详情数据
        for (PurchaseItemDTO item : purchaseFormDTO.getPurchaseItemDtos()) {
            PurchaseItem purchaseItem = new PurchaseItem();
            BeanUtil.copyProperties(item, purchaseItem);
            purchaseItem.setPurchaseId(newPurchase.getPurchaseId());
            purchaseItem.setItemId(IdGeneratorSnowflake.snowflakeId().toString());
            this.purchaseItemMapper.insert(purchaseItem);
        }
        return a;
    }

    /**
     * 保存并提交审核  状态应该是Constants.STOCK_PURCHASE_STATUS_2
     *
     * @param purchaseFormDTO 订购单表单数据
     * @return 保存结果
     */
    @Override
    public int addPurchaseAndItemToAudit(PurchaseFormDTO purchaseFormDTO) {
        Purchase purchase = this.purchaseMapper.selectById(purchaseFormDTO.getPurchaseDto().getPurchaseId());
        if (null != purchase) {
            //删除之前的数据
            this.purchaseMapper.deleteById(purchase.getPurchaseId());
            //删除之前的详情数据
            QueryWrapper<PurchaseItem> qw = new QueryWrapper<>();
            qw.eq(PurchaseItem.COL_PURCHASE_ID, purchase.getPurchaseId());
            this.purchaseItemMapper.delete(qw);
        }
        //保存采购单主表数据
        Purchase newPurchase = new Purchase();
        BeanUtil.copyProperties(purchaseFormDTO.getPurchaseDto(), newPurchase);
        newPurchase.setStatus(Constants.STOCK_PURCHASE_STATUS_2);//待审核
        newPurchase.setCreateBy(purchaseFormDTO.getPurchaseDto().getSimpleUser().getUserName());
        newPurchase.setCreateTime(DateUtil.date());
        //设置申请人和申请人的ID
        newPurchase.setApplyUserId(Long.valueOf(purchaseFormDTO.getPurchaseDto().getSimpleUser().getUserId().toString()));
        newPurchase.setApplyUserName(purchaseFormDTO.getPurchaseDto().getSimpleUser().getUserName());
        int a = this.purchaseMapper.insert(newPurchase);
        //保存详情数据
        for (PurchaseItemDTO item : purchaseFormDTO.getPurchaseItemDtos()) {
            PurchaseItem purchaseItem = new PurchaseItem();
            BeanUtil.copyProperties(item, purchaseItem);
            purchaseItem.setPurchaseId(newPurchase.getPurchaseId());
            purchaseItem.setItemId(IdGeneratorSnowflake.snowflakeId().toString());
            this.purchaseItemMapper.insert(purchaseItem);
        }
        return a;
    }

    /**
     * 入库
     * 逻辑
     * 向stock_inventory_log表里面添加数据 并更新stock_medicines的库存
     *
     * @param purchaseId        采购单id
     * @param currentSimpleUser 创建用户信息
     */
    @Override
    public int doInventory(String purchaseId, SimpleUser currentSimpleUser) {
        Purchase purchase = this.purchaseMapper.selectById(purchaseId);
        if (null != purchase || purchase.getStatus().equals(Constants.STOCK_PURCHASE_STATUS_3)) {
            //查询详情
            QueryWrapper<PurchaseItem> qw = new QueryWrapper<>();
            qw.eq(PurchaseItem.COL_PURCHASE_ID, purchase.getPurchaseId());
            List<PurchaseItem> purchaseItems = this.purchaseItemMapper.selectList(qw);
            for (PurchaseItem purchaseItem : purchaseItems) {
                InventoryLog inventoryLog = new InventoryLog();
                inventoryLog.setInventoryLogId(purchaseItem.getItemId());
                inventoryLog.setPurchaseId(purchaseItem.getPurchaseId());
                inventoryLog.setMedicinesId(purchaseItem.getMedicinesId());
                inventoryLog.setInventoryLogNum(purchaseItem.getPurchaseNumber());
                inventoryLog.setTradePrice(purchaseItem.getTradePrice());
                inventoryLog.setTradeTotalAmount(purchaseItem.getTradeTotalAmount());
                inventoryLog.setBatchNumber(purchaseItem.getBatchNumber());
                inventoryLog.setMedicinesName(purchaseItem.getMedicinesName());
                inventoryLog.setMedicinesType(purchaseItem.getMedicinesType());
                inventoryLog.setPrescriptionType(purchaseItem.getPrescriptionType());
                inventoryLog.setProducterId(purchaseItem.getProducterId());
                inventoryLog.setConversion(purchaseItem.getConversion());
                inventoryLog.setUnit(purchaseItem.getUnit());
                inventoryLog.setCreateTime(DateUtil.date());
                inventoryLog.setCreateBy(currentSimpleUser.getUserName());
                //保存数据
                inventoryLogMapper.insert(inventoryLog);

                //更新药品库存
                Medicines medicines = this.medicinesMapper.selectById(purchaseItem.getMedicinesId());
                medicines.setMedicinesStockNum(medicines.getMedicinesStockNum() + purchaseItem.getPurchaseNumber());
                medicines.setUpdateBy(currentSimpleUser.getUserName());
                this.medicinesMapper.updateById(medicines);
            }
            //入库成功  修改单据的状态为入库成功
            purchase.setStatus(Constants.STOCK_PURCHASE_STATUS_6);//入库成功
            purchase.setStorageOptTime(DateUtil.date());
            purchase.setStorageOptUser(currentSimpleUser.getUserName());
            return this.purchaseMapper.updateById(purchase);
        }
        return -1;
    }


}