package xyz.ly11.controller.erp;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.ly11.aspect.annotation.Log;
import xyz.ly11.aspect.enums.BusinessType;
import xyz.ly11.dto.MedicinesDTO;
import xyz.ly11.service.MedicinesService;
import xyz.ly11.utils.ShiroSecurityUtils;
import xyz.ly11.vo.AjaxResult;
import xyz.ly11.vo.DataGridView;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author 29794
 * @date 1/23/2021 21:37
 * 药品信息api
 */
@RestController
@RequestMapping("/erp/medicines")
public class MedicinesController {

    @Reference
    private MedicinesService medicinesService;

    /**
     * 分页查询
     */
    @GetMapping("listMedicinesForPage")
    @HystrixCommand
    public AjaxResult listMedicinesForPage(MedicinesDTO medicinesDTO) {
        DataGridView gridView = this.medicinesService.listMedicinesPage(medicinesDTO);
        return AjaxResult.success("查询成功", gridView.getData(), gridView.getTotal());
    }

    /**
     * 添加
     */
    @PostMapping("addMedicines")
    @HystrixCommand
    @Log(title = "添加药品信息", businessType = BusinessType.INSERT)
    public AjaxResult addMedicines(@Validated MedicinesDTO medicinesDTO) {
        medicinesDTO.setSimpleUser(ShiroSecurityUtils.getCurrentSimpleUser());
        return AjaxResult.toAjax(this.medicinesService.addMedicines(medicinesDTO));
    }

    /**
     * 修改
     */
    @PutMapping("updateMedicines")
    @HystrixCommand
    @Log(title = "修改药品信息", businessType = BusinessType.UPDATE)
    public AjaxResult updateMedicines(@Validated MedicinesDTO medicinesDTO) {
        medicinesDTO.setSimpleUser(ShiroSecurityUtils.getCurrentSimpleUser());
        return AjaxResult.toAjax(this.medicinesService.updateMedicines(medicinesDTO));
    }


    /**
     * 根据ID查询一个药品信息信息
     */
    @GetMapping("getMedicinesById/{medicinesId}")
    @HystrixCommand
    public AjaxResult getMedicinesById(@PathVariable @Validated @NotNull(message = "药品信息ID不能为空") Long medicinesId) {
        return AjaxResult.success(this.medicinesService.getOne(medicinesId));
    }

    /**
     * 删除
     */
    @DeleteMapping("deleteMedicinesByIds/{medicinesIds}")
    @HystrixCommand
    @Log(title = "删除药品信息", businessType = BusinessType.DELETE)
    public AjaxResult deleteMedicinesByIds(@PathVariable @Validated @NotEmpty(message = "要删除的ID不能为空") Long[] medicinesIds) {
        return AjaxResult.toAjax(this.medicinesService.deleteMedicinesByIds(medicinesIds));
    }

    /**
     * 查询所有可用的药品信息
     */
    @HystrixCommand
    @GetMapping("selectAllMedicines")
    public AjaxResult selectAllMedicines() {
        return AjaxResult.success(this.medicinesService.selectAllMedicines());
    }

    /**
     * 调整库存
     */
    @HystrixCommand
    @Log(title = "调整药品库存信息", businessType = BusinessType.UPDATE)
    @PostMapping("updateMedicinesStorage/{medicinesId}/{medicinesStockNum}")
    public AjaxResult xx(@PathVariable Long medicinesId, @PathVariable Long medicinesStockNum) {
        int i = this.medicinesService.updateMedicinesStorage(medicinesId, medicinesStockNum);
        return AjaxResult.toAjax(i);
    }


}