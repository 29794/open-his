package xyz.ly11.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xyz.ly11.constants.Constants;
import xyz.ly11.domain.DictData;
import xyz.ly11.dto.DictDataDTO;
import xyz.ly11.dto.DictTypeDTO;
import xyz.ly11.mapper.DictTypeMapper;
import xyz.ly11.domain.DictType;
import xyz.ly11.service.DictTypeService;
import xyz.ly11.vo.DataGridView;

/**
 * @author by 29794
 * @date 2020/10/15 23:30
 */
@Service
public class DictTypeServiceImpl implements DictTypeService {

    final
    DictTypeMapper dictTypeMapper;

    public DictTypeServiceImpl(DictTypeMapper dictTypeMapper) {
        this.dictTypeMapper = dictTypeMapper;
    }

    @Override
    public DataGridView listPage(DictTypeDTO dictTypeDTO) {
        Page<DictType> page = new Page<>(dictTypeDTO.getPageNum(), dictTypeDTO.getPageSize());
        QueryWrapper<DictType> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(dictTypeDTO.getDictName()), DictType.COL_DICT_NAME, dictTypeDTO.getDictName());
        wrapper.like(StringUtils.isNotBlank(dictTypeDTO.getDictType()), DictType.COL_DICT_TYPE, dictTypeDTO.getDictName());
        wrapper.eq(StringUtils.isNotBlank(dictTypeDTO.getStatus()), DictType.COL_STATUS, dictTypeDTO.getStatus());
        wrapper.ge(dictTypeDTO.getBeginTime() != null, DictType.COL_CREATE_TIME, dictTypeDTO.getBeginTime());
        wrapper.ge(dictTypeDTO.getEndTime() != null, DictType.COL_CREATE_TIME, dictTypeDTO.getEndTime());
        Page<DictType> dictTypePage = this.dictTypeMapper.selectPage(page, wrapper);
        return new DataGridView(dictTypePage.getTotal(), dictTypePage.getRecords());
    }

    @Override
    public DataGridView list() {
        QueryWrapper<DictType> wrapper = new QueryWrapper<>();
        wrapper.eq(DictType.COL_STATUS, Constants.STATUS_TRUE);
        return new DataGridView(null, this.dictTypeMapper.selectList(wrapper));
    }

    @Override
    public Boolean checkDictTypeUnique(Long dictId, String dictType) {
        dictId = (dictId == null) ? -1L : dictId;
        QueryWrapper<DictType> qw = new QueryWrapper<>();
        qw.eq(DictType.COL_DICT_TYPE, dictType);
        DictType sysDictType = this.dictTypeMapper.selectOne(qw);
        return null != sysDictType && dictId.longValue() != sysDictType.getDictId().longValue();
    }

    @Override
    public int insert(DictTypeDTO dictTypeDTO) {
        DictType dictType = new DictType();
        BeanUtil.copyProperties(dictTypeDTO, dictType);
        dictType.setCreateBy(dictTypeDTO.getSimpleUser().getUserName());
        dictType.setCreateTime(LocalDateTime.now());
        return this.dictTypeMapper.insert(dictType);
    }

    @Override
    public int update(DictTypeDTO dictTypeDTO) {
        DictType dictType = new DictType();
        BeanUtil.copyProperties(dictTypeDTO, dictType);
        dictType.setUpdateBy(dictTypeDTO.getSimpleUser().getUserName());
        dictType.setUpdateTime(LocalDateTime.now());
        return this.dictTypeMapper.updateById(dictType);
    }

    @Override
    public int deleteDictTypeByIds(Long[] dictIds) {
        List<Long> asIdList = Arrays.asList(dictIds);
        if (asIdList.size() > 0) {
            return this.dictTypeMapper.deleteBatchIds(asIdList);
        } else {
            return -1;
        }
    }

    @Override
    public DictType selectDictTypeById(Long dictId) {
        return this.dictTypeMapper.selectById(dictId);
    }

    @Override
    public void dictCacheAsync() {

    }
}
