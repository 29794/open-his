package xyz.ly11.controller.system;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.ly11.aspect.annotation.Log;
import xyz.ly11.aspect.enums.BusinessType;
import xyz.ly11.domain.CheckItem;
import xyz.ly11.dto.CheckItemDTO;
import xyz.ly11.service.CheckItemService;
import xyz.ly11.utils.ShiroSecurityUtils;
import xyz.ly11.vo.AjaxResult;
import xyz.ly11.vo.DataGridView;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 29794
 * @date 1/20/2021 22:05
 * 检查项目的api
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/system/checkItem")
public class CheckItemController {

    final CheckItemService checkItemService;

    /**
     * 分页查询
     */
    @GetMapping("listCheckItemForPage")
    public AjaxResult listCheckItemForPage(CheckItemDTO checkItemDTO) {
        DataGridView gridView = this.checkItemService.listCheckItemPage(checkItemDTO);
        return AjaxResult.success("查询成功", gridView.getData(), gridView.getTotal());
    }

    /**
     * 添加
     */
    @PostMapping("addCheckItem")
    @Log(title = "添加检查项目", businessType = BusinessType.INSERT)
    public AjaxResult addCheckItem(@Validated CheckItemDTO checkItemDTO) {
        checkItemDTO.setSimpleUser(ShiroSecurityUtils.getCurrentSimpleUser());
        return AjaxResult.toAjax(this.checkItemService.addCheckItem(checkItemDTO));
    }

    /**
     * 修改
     */
    @PutMapping("updateCheckItem")
    @Log(title = "修改检查项目", businessType = BusinessType.UPDATE)
    public AjaxResult updateCheckItem(@Validated CheckItemDTO checkItemDTO) {
        checkItemDTO.setSimpleUser(ShiroSecurityUtils.getCurrentSimpleUser());
        return AjaxResult.toAjax(this.checkItemService.updateCheckItem(checkItemDTO));
    }


    /**
     * 根据ID查询一个检查项目信息
     */
    @GetMapping("getCheckItemById/{checkItemId}")
    public AjaxResult getCheckItemById(@PathVariable @Validated @NotNull(message = "检查项目ID不能为空") Long checkItemId) {
        return AjaxResult.success(this.checkItemService.getOne(checkItemId));
    }

    /**
     * 删除
     */
    @DeleteMapping("deleteCheckItemByIds/{checkItemIds}")
    @Log(title = "删除检查项目", businessType = BusinessType.DELETE)
    public AjaxResult deleteCheckItemByIds(@PathVariable @Validated @NotEmpty(message = "要删除的ID不能为空") Long[] checkItemIds) {
        return AjaxResult.toAjax(this.checkItemService.deleteCheckItemByIds(checkItemIds));
    }

    /**
     * 查询所有可用的检查项目
     */
    @GetMapping("selectAllCheckItem")
    public AjaxResult selectAllCheckItem() {
        List<CheckItem> checkItems = this.checkItemService.queryAllCheckItems();
        return AjaxResult.success(checkItems);
    }


}