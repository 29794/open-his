package xyz.ly11.controller.system;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.ly11.aspect.annotation.Log;
import xyz.ly11.aspect.enums.BusinessType;
import xyz.ly11.dto.DeptDTO;
import xyz.ly11.service.DeptService;
import xyz.ly11.utils.ShiroSecurityUtils;
import xyz.ly11.vo.AjaxResult;
import xyz.ly11.vo.DataGridView;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author 29794
 * @date 1/15/2021 23:05
 * 科室管理接口
 */
@RestController
@RequestMapping("system/dept")
@RequiredArgsConstructor
public class DeptController {

    final DeptService deptService;

    /**
     * 分页查询
     *
     * @param deptDTO 部门查询条件
     * @return 分页查询结果
     */
    @GetMapping("listDeptForPage")
    public AjaxResult listDeptForPage(DeptDTO deptDTO) {
        DataGridView dataGridView = this.deptService.listPage(deptDTO);
        return AjaxResult.success("查询成功", dataGridView.getData(), dataGridView.getTotal());
    }

    @GetMapping("selectAllDept")
    public AjaxResult selectAllDept() {
        return AjaxResult.success(this.deptService.list());
    }

    /**
     * 根据id查询科室数据
     *
     * @param deptId 科室ID
     * @return 查询结果
     */
    @GetMapping("getDeptById/{deptId}")
    public AjaxResult getDeptById(@PathVariable @Validated @NotNull(message = "科室ID为空") Long deptId) {
        return AjaxResult.success(this.deptService.getOne(deptId));
    }

    /**
     * 新增科室
     *
     * @param deptDTO 科室数据
     * @return 新增结果
     */
    @PostMapping("addDept")
    @Log(title = "新增科室", businessType = BusinessType.INSERT)
    public AjaxResult addDept(@Validated DeptDTO deptDTO) {
        deptDTO.setSimpleUser(ShiroSecurityUtils.getCurrentSimpleUser());
        return AjaxResult.toAjax(this.deptService.addDept(deptDTO));
    }

    /**
     * 修改科室
     *
     * @param deptDTO 需要修改的数据
     * @return 修改结果
     */
    @PutMapping("updateDept")
    @Log(title = "修改科室", businessType = BusinessType.UPDATE)
    public AjaxResult updateDept(@Validated DeptDTO deptDTO) {
        deptDTO.setSimpleUser(ShiroSecurityUtils.getCurrentSimpleUser());
        return AjaxResult.toAjax(this.deptService.updateDept(deptDTO));
    }

    /**
     * 删除科室数据
     *
     * @param deptIds 科室ID
     * @return 删除结果
     */
    @Log(title = "删除科室", businessType = BusinessType.DELETE)
    @DeleteMapping("/deleteDeptByIds/{deptIds}")
    public AjaxResult deleteDeptByIds(@PathVariable @Validated @NotEmpty(message = "科室ID为空") Long[] deptIds) {
        return AjaxResult.toAjax(this.deptService.deleteDeptByIds(deptIds));
    }

}