package xyz.ly11.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import springfox.documentation.annotations.Cacheable;
import xyz.ly11.constants.Constants;
import xyz.ly11.domain.DictData;
import xyz.ly11.dto.DictDataDTO;
import xyz.ly11.mapper.DictDataMapper;
import xyz.ly11.service.DictDataService;
import xyz.ly11.vo.DataGridView;

/**
 * @author by 29794
 * @date 2020/10/15 23:54
 */
@Service
public class DictDataServiceImpl implements DictDataService {

    final
    DictDataMapper dictDataMapper;

    final
    StringRedisTemplate stringRedisTemplate;

    public DictDataServiceImpl(DictDataMapper dictDataMapper, StringRedisTemplate stringRedisTemplate) {
        this.dictDataMapper = dictDataMapper;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public DataGridView listPage(DictDataDTO dictDataDTO) {
        Page<DictData> page = new Page<>(dictDataDTO.getPageNum(), dictDataDTO.getPageSize());
        QueryWrapper<DictData> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(dictDataDTO.getDictType()), DictData.COL_DICT_TYPE, dictDataDTO.getDictType());
        wrapper.like(StringUtils.isNotBlank(dictDataDTO.getDictLabel()), DictData.COL_DICT_LABEL, dictDataDTO.getDictLabel());
        wrapper.eq(StringUtils.isNotBlank(dictDataDTO.getStatus()), DictData.COL_STATUS, dictDataDTO.getStatus());
        wrapper.orderByAsc(DictData.COL_DICT_SORT);
        this.dictDataMapper.selectPage(page, wrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @Override
    public int insert(DictDataDTO dictDataDTO) {
        DictData dictData = new DictData();
        BeanUtil.copyProperties(dictDataDTO, dictData);
        //设置创建者，创建时间
        dictData.setCreateBy(dictDataDTO.getSimpleUser().getUserName());
        dictData.setCreateTime(DateUtil.date());
        return this.dictDataMapper.insert(dictData);
    }

    @Override
    public int update(DictDataDTO dictDataDTO) {
        DictData dictData = new DictData();
        BeanUtil.copyProperties(dictDataDTO, dictData);
        //设置修改人
        dictData.setUpdateBy(dictDataDTO.getSimpleUser().getUserName());
        return this.dictDataMapper.updateById(dictData);
    }

    @Override
    public int deleteDictDataByIds(Long[] dictCodeIds) {
        List<Long> ids = Arrays.asList(dictCodeIds);
        if (ids.size() > 0) {
            return this.dictDataMapper.deleteBatchIds(ids);
        } else {
            return -1;
        }

    }

    /**
     * 可以添加Spring Boot缓存
     *
     * @param dictType
     * @return
     */
    @Override
    public List<DictData> selectDictDataByDictType(String dictType) {
        String key = Constants.DICT_REDIS_PREFIX + dictType;
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        String json = opsForValue.get(key);
        List<DictData> dictDataList = JSON.parseArray(json, DictData.class);
        if (dictDataList != null) {
            return dictDataList;
        }
        //查询数据库
        QueryWrapper<DictData> wrapper = new QueryWrapper<>();
        wrapper.eq(DictData.COL_DICT_TYPE, dictType);
        //可用的
        wrapper.eq(DictData.COL_STATUS, Constants.STATUS_TRUE);
        wrapper.orderByAsc(DictData.COL_DICT_SORT);
        return this.dictDataMapper.selectList(wrapper);
    }

    @Override
    public DictData selectDictDataById(Long dictCode) {
        return this.dictDataMapper.selectById(dictCode);
    }
}
