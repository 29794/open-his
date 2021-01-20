package xyz.ly11.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import xyz.ly11.domain.Role;

import java.util.List;

/**
 * @author 29794
 * @date 1/16/2021 17:19
 * role: mapper 接口
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据角色IDS删除sys_role_menu中间表的数据
     *
     * @param ids 角色的id
     */
    void deleteRoleMenuByRoleIds(@Param("ids") List<Long> ids);

    /**
     * 根据角色IDS删除sys_role_user中间表的数据
     *
     * @param ids 角色的id
     */
    void deleteRoleUserByRoleIds(@Param("ids") List<Long> ids);


    /**
     * 保存角色和菜单之关的关系
     *
     * @param roleId 角色id
     * @param menuId 菜单id
     */
    void saveRoleMenu(@Param("roleId") Long roleId, @Param("menuId") Long menuId);


    /**
     * 根据用户IDS删除sys_role_user里面的数据
     *
     * @param ids 用户的ids
     */
    void deleteRoleUserByUserIds(@Param("ids") List<Long> ids);

    /**
     * 根据菜单权限ID删除sys_role_menu
     */
    void deleteRoleMenuByMenuIds(@Param("ids") List<Long> ids);

    /**
     * 根据用户ID查询用户拥有的角色IDS
     *
     * @param userId 用户的id
     * @return 查询的角色ids
     */
    List<Long> selectRoleIdsByUserId(@Param("userId") Long userId);

    /**
     * 保存角色和用户的关系
     *
     * @param userId 用户的id
     * @param roleId 角色的id
     */
    void saveRoleUser(@Param("userId") Long userId, @Param("roleId") Long roleId);




}