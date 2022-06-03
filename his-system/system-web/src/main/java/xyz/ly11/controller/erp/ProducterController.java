package xyz.ly11.controller.erp;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.ly11.aspect.annotation.Log;
import xyz.ly11.aspect.enums.BusinessType;
import xyz.ly11.controller.BaseController;
import xyz.ly11.dto.ProducterDTO;
import xyz.ly11.service.ProducterService;
import xyz.ly11.utils.ShiroSecurityUtils;
import xyz.ly11.vo.AjaxResult;
import xyz.ly11.vo.DataGridView;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author 29794
 * @date 1/22/2021 22:13
 * 生产厂家的 api
 */
@RequestMapping("/erp/producter")
@Slf4j
@RestController
public class ProducterController extends BaseController {

    @Reference
    private ProducterService producterService;

    /**
     * 分页查询
     */
    @GetMapping("listProducterForPage")
    @HystrixCommand
    public AjaxResult listProducterForPage(ProducterDTO producterDTO) {
        DataGridView gridView = this.producterService.listProducterPage(producterDTO);
        return AjaxResult.success("查询成功", gridView.getData(), gridView.getTotal());
    }

    /**
     * 添加
     */
    @PostMapping("addProducter")
    @Log(title = "添加生产厂家", businessType = BusinessType.INSERT)
    @HystrixCommand
    public AjaxResult addProducter(@Validated ProducterDTO producterDTO) {
        producterDTO.setSimpleUser(ShiroSecurityUtils.getCurrentSimpleUser());
        return AjaxResult.toAjax(this.producterService.addProducter(producterDTO));
    }

    /**
     * 修改
     */
    @PutMapping("updateProducter")
    @Log(title = "修改生产厂家", businessType = BusinessType.UPDATE)
    @HystrixCommand
    public AjaxResult updateProducter(@Validated ProducterDTO producterDTO) {
        producterDTO.setSimpleUser(ShiroSecurityUtils.getCurrentSimpleUser());
        return AjaxResult.toAjax(this.producterService.updateProducter(producterDTO));
    }


    /**
     * 根据ID查询一个生产厂家信息
     */
    @GetMapping("getProducterById/{producterId}")
    @HystrixCommand
    public AjaxResult getProducterById(@PathVariable @Validated @NotNull(message = "生产厂家ID不能为空") Long producterId) {
        return AjaxResult.success(this.producterService.getOne(producterId));
    }

    /**
     * 删除
     */
    @DeleteMapping("deleteProducterByIds/{producterIds}")
    @Log(title = "删除生产厂家", businessType = BusinessType.DELETE)
    @HystrixCommand
    public AjaxResult deleteProducterByIds(@PathVariable @Validated @NotEmpty(message = "要删除的ID不能为空") Long[] producterIds) {
        return AjaxResult.toAjax(this.producterService.deleteProducterByIds(producterIds));
    }

    /**
     * 查询所有可用的生产厂家
     */
    @GetMapping("selectAllProducter")
    @HystrixCommand
    public AjaxResult selectAllProducter() {
        return AjaxResult.success(this.producterService.selectAllProducter());
    }


}