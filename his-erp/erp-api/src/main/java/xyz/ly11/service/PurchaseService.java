package xyz.ly11.service;

import xyz.ly11.domain.Purchase;
import xyz.ly11.domain.PurchaseItem;
import xyz.ly11.domain.SimpleUser;
import xyz.ly11.dto.PurchaseDTO;
import xyz.ly11.dto.PurchaseFormDTO;
import xyz.ly11.vo.DataGridView;

import java.util.List;

/**
 * @author 29794
 * @date 2/1/2021 20:50
 * 采购入库的业务接口
 */
public interface PurchaseService {
    /**
     * 分页查询
     *
     * @param purchaseDTO 分页查询数据
     * @return 查询结果
     */
    DataGridView listPurchasePage(PurchaseDTO purchaseDTO);

    /**
     * 根据采购单据号查询单据信息
     *
     * @param purchaseId 采购单号
     * @return 查询结果
     */
    Purchase getPurchaseById(String purchaseId);

    /**
     * 提交 审核
     *
     * @param purchaseId 采购单号
     * @return 审核结果
     */
    int doAudit(String purchaseId, SimpleUser simpleUser);

    /**
     * 作废
     *
     * @param purchaseId 采购单号
     * @return 作废结果
     */
    int doInvalid(String purchaseId);

    /**
     * 审核通过
     *
     * @param purchaseId 采购单号
     * @return 审核结果
     */
    int auditPass(String purchaseId);

    /**
     * 审核不通过
     *
     * @param purchaseId 采购单号
     * @return 审核结果
     */
    int auditNoPass(String purchaseId, String auditMsg);

    /**
     * 根据ID查询一个采购信息详情
     *
     * @param purchaseId 采购单号
     * @return 查询接过
     */
    List<PurchaseItem> getPurchaseItemById(String purchaseId);

    /**
     * 暂存采购单数据和详情
     *
     * @param purchaseFormDTO 表单数据
     */
    int addPurchaseAndItem(PurchaseFormDTO purchaseFormDTO);

    /**
     * 保存并提交审核采购单数据和详情
     *
     * @param purchaseFormDTO 表单数据
     */
    int addPurchaseAndItemToAudit(PurchaseFormDTO purchaseFormDTO);

    /**
     * 保存并提交审核采购单数据和详情
     *
     * @param purchaseId        采购单id
     * @param currentSimpleUser 创建采购单的用户信息
     */
    int doInventory(String purchaseId, SimpleUser currentSimpleUser);


}