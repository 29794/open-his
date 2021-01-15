package xyz.ly11.service;

import xyz.ly11.domain.Dept;
import xyz.ly11.dto.DeptDTO;
import xyz.ly11.vo.DataGridView;

import java.util.List;

/**
 * @author 29794
 * @date 1/15/2021 21:07
 * 部门-业务接口
 */
public interface DeptService {

    /**
     * 分页查询
     *
     * @param deptDTO 部门数据传输对像
     * @return 分页结果
     */
    DataGridView listPage(DeptDTO deptDTO);

    /**
     * 查询所有有效部门
     *
     * @return 查询结果
     */
    List<Dept> list();

    /**
     * 根据ID查询一个
     *
     * @param deptId 部门id
     * @return 查询结果
     */
    Dept getOne(Long deptId);

    /**
     * 添加一个部门
     *
     * @param deptDTO 部门数据传输对像
     * @return 影响行数
     */
    int addDept(DeptDTO deptDTO);

    /**
     * 修改部门
     *
     * @param deptDTO 需要修改的部门数据
     * @return 影响行数
     */
    int updateDept(DeptDTO deptDTO);

    /**
     * 根据IDS删除部门
     *
     * @param deptIds 需要删除的部门id
     * @return 影响行数
     */
    int deleteDeptByIds(Long[] deptIds);


}