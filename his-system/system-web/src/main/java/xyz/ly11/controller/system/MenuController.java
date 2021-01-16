package xyz.ly11.controller.system;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.ly11.constants.Constants;
import xyz.ly11.dto.MenuDTO;
import xyz.ly11.service.MenuService;
import xyz.ly11.utils.ShiroSecurityUtils;
import xyz.ly11.vo.AjaxResult;

import java.util.List;

/**
 * @author 29794
 * @date 1/16/2021 15:55
 * 彩带权限控制器
 */
@RestController
@RequestMapping("/system/menu")
@RequiredArgsConstructor
public class MenuController {

    final
    MenuService menuService;

    /**
     * 查询全部的菜单权限数据
     *
     * @param menuDTO 菜单查询表单的数据
     * @return 查询结果
     */
    @GetMapping("/listAllMenus")
    public AjaxResult listAllMenus(MenuDTO menuDTO) {
        return AjaxResult.success(this.menuService.listAllMenus(menuDTO));
    }

    /**
     * 查询菜单的下拉数
     *
     * @return 查询结果
     */
    @GetMapping("/selectMenuTree")
    public AjaxResult selectMenuTree() {
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setStatus(Constants.STATUS_TRUE);
        return AjaxResult.success(this.menuService.listAllMenus(menuDTO));
    }

    /**
     * 插入菜单
     *
     * @param menuDTO 菜单数据
     * @return 插入结果
     */
    @PostMapping("/addMenu")
    public AjaxResult addMenu(@Validated MenuDTO menuDTO) {
        menuDTO.setSimpleUser(ShiroSecurityUtils.getCurrentSimpleUser());
        return AjaxResult.toAjax(this.menuService.addMenu(menuDTO));
    }

    /**
     * 更新菜单数据
     *
     * @param menuDTO 菜单数据
     * @return 更新结果
     */
    @PutMapping("/updateMenu")
    public AjaxResult updateMenu(@Validated MenuDTO menuDTO) {
        menuDTO.setSimpleUser(ShiroSecurityUtils.getCurrentSimpleUser());
        return AjaxResult.toAjax(this.menuService.updateMenu(menuDTO));
    }

    /**
     * 根据菜单的ID查询
     *
     * @param menuId 菜单的id
     * @return 查询的结果
     */
    @GetMapping("getMenuById/{menuId}")
    public AjaxResult getMenuById(@PathVariable Long menuId) {
        return AjaxResult.success(this.menuService.getOne(menuId));
    }

    /**
     * 根据菜单id删除
     *
     * @param menuId 菜单id
     * @return 删除结果
     */
    @DeleteMapping("/deleteMenuById/{menuId}")
    public AjaxResult deleteMenuById(@PathVariable Long menuId) {
        if (this.menuService.hasChildByMenuId(menuId)) {
            return AjaxResult.fail("当前需要删除的菜单下存在子节点，请先删除子节点！");
        }
        return AjaxResult.toAjax(this.menuService.deleteMenuById(menuId));
    }


    /**
     * 根据角色ID查询菜单权限ID数据
     */
    @GetMapping("getMenuIdsByRoleId/{roleId}")
    public AjaxResult getMenuIdsByRoleId(@PathVariable Long roleId) {
        List<Long> ids = this.menuService.getMenusIdsByRoleId(roleId);
        return AjaxResult.success(ids);
    }


}