package xyz.ly11.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xyz.ly11.constants.Constants;
import xyz.ly11.domain.Dept;
import xyz.ly11.dto.DeptDTO;
import xyz.ly11.mapper.DeptMapper;
import xyz.ly11.service.DeptService;
import xyz.ly11.vo.DataGridView;

import java.util.Arrays;
import java.util.List;

/**
 * @author 29794
 * @date 1/15/2021 21:07
 * todo
 */
@Service
@RequiredArgsConstructor
public class DeptServiceImpl implements DeptService {

    final DeptMapper deptMapper;

    @Override
    public DataGridView listPage(DeptDTO deptDTO) {
        Page<Dept> page = new Page<>(deptDTO.getPageNum(), deptDTO.getPageSize());
        QueryWrapper<Dept> qw = new QueryWrapper<>();
        qw.like(StringUtils.isNotBlank(deptDTO.getDeptName()), Dept.COL_DEPT_NAME, deptDTO.getDeptName());
        qw.eq(StringUtils.isNotBlank(deptDTO.getStatus()), Dept.COL_STATUS, deptDTO.getStatus());
        qw.ge(deptDTO.getBeginTime() != null, Dept.COL_CREATE_TIME, deptDTO.getBeginTime());
        qw.le(deptDTO.getEndTime() != null, Dept.COL_CREATE_TIME, deptDTO.getEndTime());
        qw.orderByAsc(Dept.COL_ORDER_NUM);
        this.deptMapper.selectPage(page, qw);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @Override
    public List<Dept> list() {
        QueryWrapper<Dept> qw = new QueryWrapper<>();
        qw.eq(Dept.COL_STATUS, Constants.STATUS_TRUE);
        qw.orderByAsc(Dept.COL_ORDER_NUM);
        return this.deptMapper.selectList(qw);
    }

    @Override
    public Dept getOne(Long deptId) {
        return this.deptMapper.selectById(deptId);
    }

    @Override
    public int addDept(DeptDTO deptDTO) {
        Dept dept = new Dept();
        BeanUtils.copyProperties(deptDTO, dept);
        dept.setCreateBy(deptDTO.getSimpleUser().getUserName());
        dept.setCreateTime(DateUtil.date());
        return this.deptMapper.insert(dept);
    }

    @Override
    public int updateDept(DeptDTO deptDTO) {
        Dept dept = new Dept();
        BeanUtils.copyProperties(deptDTO, dept);
        dept.setUpdateBy(deptDTO.getSimpleUser().getUserName());
        return this.deptMapper.updateById(dept);
    }

    @Override
    public int deleteDeptByIds(Long[] deptIds) {
        List<Long> asList = Arrays.asList(deptIds);
        if (asList.size() > 0) {
            return this.deptMapper.deleteBatchIds(asList);
        }
        return 0;
    }
}