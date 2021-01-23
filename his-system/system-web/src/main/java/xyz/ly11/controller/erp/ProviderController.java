package xyz.ly11.controller.erp;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.ly11.aspect.annotation.Log;
import xyz.ly11.aspect.enums.BusinessType;
import xyz.ly11.dto.ProviderDTO;
import xyz.ly11.service.ProviderService;
import xyz.ly11.utils.ShiroSecurityUtils;
import xyz.ly11.vo.AjaxResult;
import xyz.ly11.vo.DataGridView;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author 29794
 * @date 1/23/2021 21:55
 * 供应商的api
 */
@RequestMapping("erp/provider")
@Slf4j
@RestController
public class ProviderController {

    @Reference
    private ProviderService providerService;


    /**
     * 分页查询
     */
    @GetMapping("listProviderForPage")
    @HystrixCommand
    public AjaxResult listProviderForPage(ProviderDTO providerDTO) {
        DataGridView gridView = this.providerService.listProviderPage(providerDTO);
        return AjaxResult.success("查询成功", gridView.getData(), gridView.getTotal());
    }

    /**
     * 添加
     */
    @PostMapping("addProvider")
    @HystrixCommand
    @Log(title = "添加供应商", businessType = BusinessType.INSERT)
    public AjaxResult addProvider(@Validated ProviderDTO providerDTO) {
        providerDTO.setSimpleUser(ShiroSecurityUtils.getCurrentSimpleUser());
        return AjaxResult.toAjax(this.providerService.addProvider(providerDTO));
    }

    /**
     * 修改
     */
    @PutMapping("updateProvider")
    @HystrixCommand
    @Log(title = "修改供应商", businessType = BusinessType.UPDATE)
    public AjaxResult updateProvider(@Validated ProviderDTO providerDTO) {
        providerDTO.setSimpleUser(ShiroSecurityUtils.getCurrentSimpleUser());
        return AjaxResult.toAjax(this.providerService.updateProvider(providerDTO));
    }


    /**
     * 根据ID查询一个供应商信息
     */
    @GetMapping("getProviderById/{providerId}")
    @HystrixCommand
    public AjaxResult getProviderById(@PathVariable @Validated @NotNull(message = "供应商ID不能为空") Long providerId) {
        return AjaxResult.success(this.providerService.getOne(providerId));
    }

    /**
     * 删除
     */
    @DeleteMapping("deleteProviderByIds/{providerIds}")
    @HystrixCommand
    @Log(title = "删除供应商", businessType = BusinessType.DELETE)
    public AjaxResult deleteProviderByIds(@PathVariable @Validated @NotEmpty(message = "要删除的ID不能为空") Long[] providerIds) {
        return AjaxResult.toAjax(this.providerService.deleteProviderByIds(providerIds));
    }

    /**
     * 查询所有可用的供应商
     */
    @HystrixCommand
    @GetMapping("selectAllProvider")
    public AjaxResult selectAllProvider() {
        return AjaxResult.success(this.providerService.selectAllProvider());
    }


}