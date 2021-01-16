package xyz.ly11.service;

import xyz.ly11.domain.Menu;
import xyz.ly11.domain.SimpleUser;
import xyz.ly11.dto.MenuDTO;

import java.util.List;

/**
 * @author by 29794
 * @date 2020/10/9 2:07
 */
public interface MenuService {

    /**
     * 查询菜单信息
     * 如查用户是超级管理员，那么查询所以菜单和权限
     * 如果用户是普通用户，那么根据用户ID关联角色和权限
     *
     * @param isAdmin    是否是超级管理员
     * @param simpleUser 如果isAdmin=true  simpleUser可以为空
     */
    List<Menu> selectMenuTree(boolean isAdmin, SimpleUser simpleUser);

    /**
     * 根据条件查询所有菜单
     *
     * @param menuDTO 菜单信息
     * @return 查询结果
     */
    List<Menu> listAllMenus(MenuDTO menuDTO);

    /**
     * 根据ID查询菜单和权限
     *
     * @param menuId 菜单id
     * @return 菜单权限
     */
    Menu getOne(Long menuId);

    /**
     * 添加菜单或权限
     *
     * @param menuDTO 菜单数据
     * @return 影响行数
     */
    int addMenu(MenuDTO menuDTO);

    /**
     * 修改菜单或权限
     *
     * @param menuDTO 需要修改的菜单信息
     * @return 修改结果
     */
    int updateMenu(MenuDTO menuDTO);

    /**
     * 根据ID删除菜单或权限
     *
     * @param menuId 菜单id
     * @return 删除结果
     */
    int deleteMenuById(Long menuId);

    /**
     * 根据菜单ID判断菜单是否有子节点
     *
     * @param menuId 菜单id
     * @return 是否存在子节点
     */
    boolean hasChildByMenuId(Long menuId);

    /**
     * 根据角色ID查询菜单权限ID数据
     * @param roleId 角色id
     * @return 查询结果
     */
    List<Long> getMenusIdsByRoleId(Long roleId);




}