package xyz.ly11.controller.system;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.ly11.dto.DictDataDTO;
import xyz.ly11.service.DictDataService;
import xyz.ly11.utils.ShiroSecurityUtils;
import xyz.ly11.vo.AjaxResult;
import xyz.ly11.vo.DataGridView;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 数据字典的具体信息API
 *
 * @author by 29794
 * @date 2020/10/18 19:57
 */
@RestController
@RequestMapping("system/dict/data")
public class DictDataController {

    final
    DictDataService dictDataService;

    public DictDataController(DictDataService dictDataService) {
        this.dictDataService = dictDataService;
    }

    /**
     * 分页查询
     */
    @GetMapping("listForPage")
    public AjaxResult listForPage( DictDataDTO dictDataDTO) {
        DataGridView gridView = this.dictDataService.listPage(dictDataDTO);
        return AjaxResult.success("查询成功", gridView.getData(), gridView.getTotal());
    }

    /**
     * 添加
     */
    @PostMapping("addDictData")
    public AjaxResult addDictData( @Validated DictDataDTO dictDataDTO) {
        dictDataDTO.setSimpleUser(ShiroSecurityUtils.getCurrentSimpleUser());
        return AjaxResult.toAjax(this.dictDataService.insert(dictDataDTO));
    }

    /**
     * 修改
     */
    @PutMapping("updateDictData")
    public AjaxResult updateDictData( @Validated DictDataDTO dictDataDTO) {
        dictDataDTO.setSimpleUser(ShiroSecurityUtils.getCurrentSimpleUser());
        return AjaxResult.toAjax(this.dictDataService.update(dictDataDTO));
    }


    /**
     * 根据ID查询一个字典信息
     */
    @GetMapping("getOne/{dictCode:-?[1-9]\\d*}")
    public AjaxResult getDictData(@PathVariable @Validated @NotNull(message = "字典数据ID不能为空") Long dictCode) {
        return AjaxResult.success(this.dictDataService.selectDictDataById(dictCode));
    }

    /**
     * 删除
     */
    @DeleteMapping("deleteDictDataByIds/{dictCodeIds}")
    public AjaxResult updateDictData(@PathVariable @Validated @NotEmpty(message = "需要要删除的字典数据ID不能为空") Long[] dictCodeIds) {
        return AjaxResult.toAjax(this.dictDataService.deleteDictDataByIds(dictCodeIds));
    }

    /**
     * 查询所有可用的字典类型
     */
    @GetMapping("getDataByType/{dictType}")
    public AjaxResult getDataByType(@PathVariable @Validated @NotEmpty(message = "字典类型不能为空") String dictType) {
        return AjaxResult.success(this.dictDataService.selectDictDataByDictType(dictType));
    }


}
