package xyz.ly11.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import xyz.ly11.constants.Constants;
import xyz.ly11.domain.Role;
import xyz.ly11.dto.RoleDTO;
import xyz.ly11.mapper.RoleMapper;
import xyz.ly11.service.RoleService;
import xyz.ly11.vo.DataGridView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author 29794
 * @date 1/16/2021 17:19
 * 角色业务的实现
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    final RoleMapper roleMapper;

    @Override
    public DataGridView listRolePage(RoleDTO roleDTO) {
        Page<Role> page = new Page<>(roleDTO.getPageNum(), roleDTO.getPageSize());
        QueryWrapper<Role> qw = new QueryWrapper<>();
        qw.like(StringUtils.isNotBlank(roleDTO.getRoleName()), Role.COL_ROLE_NAME, roleDTO.getRoleName());
        qw.like(StringUtils.isNotBlank(roleDTO.getRoleCode()), Role.COL_ROLE_CODE, roleDTO.getRoleCode());
        qw.eq(StringUtils.isNotBlank(roleDTO.getStatus()), Role.COL_STATUS, roleDTO.getStatus());
        qw.ge(roleDTO.getBeginTime() != null, Role.COL_CREATE_TIME, roleDTO.getBeginTime());
        qw.le(roleDTO.getEndTime() != null, Role.COL_CREATE_TIME, roleDTO.getEndTime());
        qw.orderByAsc(Role.COL_ROLE_SORT);
        this.roleMapper.selectPage(page, qw);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @Override
    public List<Role> listAllRoles() {
        QueryWrapper<Role> qw = new QueryWrapper<>();
        qw.eq(Role.COL_STATUS, Constants.STATUS_TRUE);
        qw.orderByAsc(Role.COL_ROLE_SORT);
        return this.roleMapper.selectList(qw);
    }

    @Override
    public Role getOne(Long roleId) {
        return this.roleMapper.selectById(roleId);
    }

    @Override
    public int addRole(RoleDTO roleDTO) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDTO, role);
        role.setCreateBy(roleDTO.getSimpleUser().getUserName());
        role.setCreateTime(DateUtil.date());
        return this.roleMapper.insert(role);
    }

    @Override
    public int updateRole(RoleDTO roleDTO) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDTO, role);
        role.setUpdateBy(roleDTO.getSimpleUser().getUserName());
        role.setUpdateTime(DateUtil.date());
        return this.roleMapper.updateById(role);
    }

    @Override
    public int deleteRoleByIds(Long[] roleIds) {
        List<Long> asList = Arrays.asList(roleIds);
        if (asList.size() > 0) {
            return this.roleMapper.deleteBatchIds(asList);
        }
        return 0;
    }

    @Override
    public void saveRoleMenu(Long roleId, Long[] menuIds) {
        //根据角色ID删除sys_role_menu的数据
        this.roleMapper.deleteRoleMenuByRoleIds(Collections.singletonList(roleId));
        for (Long menuId : menuIds) {
            this.roleMapper.saveRoleMenu(roleId, menuId);
        }

    }

    @Override
    public List<Long> getRoleIdsByUserId(Long userId) {
        if(null==userId){
            return Collections.emptyList();
        }
        return this.roleMapper.selectRoleIdsByUserId(userId);
    }

    @Override
    public void saveRoleUser(Long userId, Long[] roleIds) {
        //根据用户ID先删除sys_role_menu里面原来的数据
        this.roleMapper.deleteRoleUserByUserIds(Collections.singletonList(userId));
        for (Long roleId : roleIds) {
            this.roleMapper.saveRoleUser(userId,roleId);
        }

    }
}