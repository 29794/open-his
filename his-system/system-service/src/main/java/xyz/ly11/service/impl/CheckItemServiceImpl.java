package xyz.ly11.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import xyz.ly11.constants.Constants;
import xyz.ly11.domain.CheckItem;
import xyz.ly11.dto.CheckItemDTO;
import xyz.ly11.mapper.CheckItemMapper;
import xyz.ly11.service.CheckItemService;
import xyz.ly11.vo.DataGridView;

import java.util.Arrays;
import java.util.List;

/**
 * @author 29794
 * @date 1/20/2021 21:46
 * 检查项目的业务接口实现
 */
@Service
@RequiredArgsConstructor
public class CheckItemServiceImpl implements CheckItemService {

    final CheckItemMapper checkItemMapper;

    @Override
    public DataGridView listCheckItemPage(CheckItemDTO checkItemDTO) {
        Page<CheckItem> page = new Page<>(checkItemDTO.getPageNum(), checkItemDTO.getPageSize());
        QueryWrapper<CheckItem> qw = new QueryWrapper<>();
        qw.like(StringUtils.isNotBlank(checkItemDTO.getCheckItemName()), CheckItem.COL_CHECK_ITEM_NAME, checkItemDTO.getCheckItemName());
        qw.like(StringUtils.isNotBlank(checkItemDTO.getKeywords()), CheckItem.COL_KEYWORDS, checkItemDTO.getKeywords());
        qw.eq(StringUtils.isNotBlank(checkItemDTO.getTypeId()), CheckItem.COL_TYPE_ID, checkItemDTO.getTypeId());
        qw.eq(StringUtils.isNotBlank(checkItemDTO.getStatus()), CheckItem.COL_STATUS, checkItemDTO.getStatus());
        this.checkItemMapper.selectPage(page, qw);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @Override
    public CheckItem getOne(Long checkItemId) {
        return this.checkItemMapper.selectById(checkItemId);
    }

    @Override
    public int addCheckItem(CheckItemDTO checkItemDTO) {
        CheckItem checkItem = new CheckItem();
        BeanUtil.copyProperties(checkItemDTO, checkItem);
        checkItem.setCreateTime(DateUtil.date());
        checkItem.setCreateBy(checkItemDTO.getSimpleUser().getUserName());
        return this.checkItemMapper.insert(checkItem);
    }

    @Override
    public int updateCheckItem(CheckItemDTO checkItemDTO) {
        CheckItem checkItem = new CheckItem();
        BeanUtil.copyProperties(checkItemDTO, checkItem);
        checkItem.setUpdateBy(checkItemDTO.getSimpleUser().getUserName());
        return this.checkItemMapper.updateById(checkItem);
    }

    @Override
    public int deleteCheckItemByIds(Long[] checkItemIds) {
        List<Long> ids = Arrays.asList(checkItemIds);
        if (ids.size() > 0) {
            return this.checkItemMapper.deleteBatchIds(ids);
        }
        return 0;
    }

    @Override
    public List<CheckItem> queryAllCheckItems() {
        QueryWrapper<CheckItem> qw = new QueryWrapper<>();
        qw.eq(CheckItem.COL_STATUS, Constants.STATUS_TRUE);
        return this.checkItemMapper.selectList(qw);
    }

}