package xyz.ly11.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import xyz.ly11.constants.Constants;
import xyz.ly11.domain.Menu;
import xyz.ly11.domain.SimpleUser;
import xyz.ly11.dto.MenuDTO;
import xyz.ly11.mapper.MenuMapper;
import xyz.ly11.mapper.RoleMapper;
import xyz.ly11.service.MenuService;

import java.util.Collections;
import java.util.List;

/**
 * @author by 29794
 * @date 2020/10/9 2:07
 */
@Service
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;

    private final RoleMapper roleMapper;

    public MenuServiceImpl(MenuMapper menuMapper, RoleMapper roleMapper) {
        this.menuMapper = menuMapper;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<Menu> selectMenuTree(boolean isAdmin, SimpleUser simpleUser) {
        QueryWrapper<Menu> qw = new QueryWrapper<>();
        qw.eq(Menu.COL_STATUS, Constants.STATUS_TRUE);
        qw.in(Menu.COL_MENU_TYPE, Constants.MENU_TYPE_M, Constants.MENU_TYPE_C);
        qw.orderByAsc(Menu.COL_PARENT_ID);
        if (isAdmin) {
            return menuMapper.selectList(qw);
        } else {
            //根据用户id查询用户拥有的菜单信息
            return menuMapper.selectList(qw);
        }
    }

    @Override
    public List<Menu> listAllMenus(MenuDTO menuDTO) {
        QueryWrapper<Menu> qw = new QueryWrapper<>();
        qw.like(StringUtils.isNotBlank(menuDTO.getMenuName()), Menu.COL_MENU_NAME, menuDTO.getMenuName());
        qw.eq(StringUtils.isNotBlank(menuDTO.getStatus()), Menu.COL_STATUS, menuDTO.getStatus());
        return this.menuMapper.selectList(qw);
    }

    @Override
    public Menu getOne(Long menuId) {
        return this.menuMapper.selectById(menuId);
    }

    @Override
    public int addMenu(MenuDTO menuDTO) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuDTO, menu);
        menu.setCreateBy(menuDTO.getSimpleUser().getUserName());
        menu.setCreateTime(DateUtil.date());
        return this.menuMapper.insert(menu);
    }

    @Override
    public int updateMenu(MenuDTO menuDTO) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuDTO, menu);
        menu.setUpdateBy(menuDTO.getSimpleUser().getUserName());
        menu.setUpdateTime(DateUtil.date());
        return this.menuMapper.updateById(menu);
    }

    @Override
    public int deleteMenuById(Long menuId) {
        //先删除role_menu的中间表的数据【后面再加】
        this.roleMapper.deleteRoleMenuByMenuIds(Collections.singletonList(menuId));
        //再删除菜单或权限
        return this.menuMapper.deleteById(menuId);
    }

    @Override
    public boolean hasChildByMenuId(Long menuId) {
        return this.menuMapper.queryChildCountByMenuId(menuId) > 0L;
    }

    @Override
    public List<Long> getMenusIdsByRoleId(Long roleId) {
        return this.menuMapper.queryMenuIdsByRoleId(roleId);
    }




}