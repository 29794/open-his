package xyz.ly11.controller.system;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.ly11.dto.DictTypeDTO;
import xyz.ly11.service.DictTypeService;
import xyz.ly11.utils.ShiroSecurityUtils;
import xyz.ly11.vo.AjaxResult;
import xyz.ly11.vo.DataGridView;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author by 29794
 * @date 2020/10/16 0:59
 */
@RestController
@RequestMapping("system/dict/type")
public class DictTypeController {

    final
    DictTypeService dictTypeService;

    public DictTypeController(DictTypeService dictTypeService) {
        this.dictTypeService = dictTypeService;
    }

    /**
     * 分页查询
     */
    @GetMapping("listForPage")
    public AjaxResult listForPage(DictTypeDTO dictTypeDTO) {
        DataGridView gridView = this.dictTypeService.listPage(dictTypeDTO);
        return AjaxResult.success("查询成功", gridView.getData(), gridView.getTotal());
    }

    /**
     * 添加
     */
    @PostMapping("addDictType")
    public AjaxResult addDictType(@Validated @RequestBody DictTypeDTO dictTypeDTO) {
        if (dictTypeService.checkDictTypeUnique(dictTypeDTO.getDictId(), dictTypeDTO.getDictType())) {
            return AjaxResult.fail("新增字典【" + dictTypeDTO.getDictName() + "】失败，字典类型已存在");
        }
        dictTypeDTO.setSimpleUser(ShiroSecurityUtils.getCurrentSimpleUser());
        return AjaxResult.toAjax(this.dictTypeService.insert(dictTypeDTO));
    }

    /**
     * 修改
     */
    @PutMapping("updateDictType")
    public AjaxResult updateDictType(@Validated @RequestBody DictTypeDTO dictTypeDTO) {
        if (dictTypeService.checkDictTypeUnique(dictTypeDTO.getDictId(), dictTypeDTO.getDictType())) {
            return AjaxResult.fail("修改字典【" + dictTypeDTO.getDictName() + "】失败，字典类型已存在");
        }
        dictTypeDTO.setSimpleUser(ShiroSecurityUtils.getCurrentSimpleUser());
        return AjaxResult.toAjax(this.dictTypeService.update(dictTypeDTO));
    }


    /**
     * 根据ID查询一个字典信息
     */
    @GetMapping("getOne/{dictId:\\d}")
    public AjaxResult getDictType(@PathVariable @Validated @NotNull(message = "字典ID不能为空") Long dictId) {
        return AjaxResult.success(this.dictTypeService.selectDictTypeById(dictId));
    }

    /**
     * 删除
     */
    @DeleteMapping("deleteDictTypeByIds/{dictIds}")
    public AjaxResult updateDictType(@PathVariable @Validated @NotEmpty(message = "要删除的ID不能为空") Long[] dictIds) {
        return AjaxResult.toAjax(this.dictTypeService.deleteDictTypeByIds(dictIds));
    }

    /**
     * 查询所有可用的字典类型
     */
    @GetMapping("selectAllDictType")
    public AjaxResult selectAllDictType() {
        return AjaxResult.success(this.dictTypeService.list().getData());
    }

    /**
     * todo 这里的缓存可以使用Spring Boot 提供的缓存机制代替
     * 手动同步缓存至redis
     */
    @GetMapping("dictCacheAsync")
    public AjaxResult dictCacheAsync() {
        try {
            this.dictTypeService.dictCacheAsync();
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }

}
