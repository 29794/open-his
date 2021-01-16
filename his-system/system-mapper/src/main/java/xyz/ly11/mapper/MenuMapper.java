package xyz.ly11.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import xyz.ly11.domain.Menu;

import java.util.List;

/**
 * @author 29794
 * @date 1/16/2021 15:23
 * 菜单权限
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据菜单id查询它的子节点个数
     *
     * @param menuId 菜单的id
     * @return 节点个数
     */
    Long queryChildCountByMenuId(Long menuId);

    /**
     * 根据角色ID查询所有选中的权限菜单ID【只查子节点的】
     *
     * @param roleId 角色id
     * @return 子节点的菜单id
     */
    List<Long> queryMenuIdsByRoleId(@Param("roleId") Long roleId);


}