package xyz.ly11.service;

import xyz.ly11.domain.Role;
import xyz.ly11.dto.RoleDTO;
import xyz.ly11.vo.DataGridView;

import java.util.List;

/**
 * @author 29794
 * @date 1/16/2021 17:19
 * 角色业务接口
 */
public interface RoleService {

    /**
     * 分页查询角色
     *
     * @param roleDTO 分页数据
     * @return 查询数据
     */
    DataGridView listRolePage(RoleDTO roleDTO);

    /**
     * 查询全部可以使用的角色
     *
     * @return 角色集合
     */
    List<Role> listAllRoles();

    /**
     * 根据角色id查询
     *
     * @param roleId 角色id
     * @return 角色信息
     */
    Role getOne(Long roleId);

    /**
     * 新增角色信息
     *
     * @param roleDTO 新增角色的表单数据
     * @return 新增结果
     */
    int addRole(RoleDTO roleDTO);

    /**
     * 修改角色信息
     *
     * @param roleDTO 修改的表单数据
     * @return 修改结果
     */
    int updateRole(RoleDTO roleDTO);

    /**
     * 删除角色信息
     *
     * @param roleIds 角色id集合
     * @return 删除结果
     */
    int deleteRoleByIds(Long[] roleIds);

    /**
     * 保存角色和菜单之间的关系
     *
     * @param roleId  角色id
     * @param menuIds 菜单id集合
     */
    void saveRoleMenu(Long roleId, Long[] menuIds);


}