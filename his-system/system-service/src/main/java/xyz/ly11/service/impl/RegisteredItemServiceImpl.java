package xyz.ly11.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import xyz.ly11.constants.Constants;
import xyz.ly11.domain.RegisteredItem;
import xyz.ly11.dto.RegisteredItemDTO;
import xyz.ly11.mapper.RegisteredItemMapper;
import xyz.ly11.service.RegisteredItemService;
import xyz.ly11.vo.DataGridView;

import java.util.Arrays;
import java.util.List;

/**
 * @author 29794
 * @date 1/20/2021 22:11
 * 挂号费用的业务接口的具体实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RegisteredItemServiceImpl implements RegisteredItemService {

    final RegisteredItemMapper registeredItemMapper;

    @Override
    public DataGridView listRegisteredItemPage(RegisteredItemDTO registeredItemDTO) {
        Page<RegisteredItem> page = new Page<>(registeredItemDTO.getPageNum(), registeredItemDTO.getPageSize());
        QueryWrapper<RegisteredItem> qw = new QueryWrapper<>();
        qw.like(StringUtils.isNotBlank(registeredItemDTO.getRegItemName()), RegisteredItem.COL_REG_ITEM_NAME, registeredItemDTO.getRegItemName());
        qw.eq(StringUtils.isNotBlank(registeredItemDTO.getStatus()), RegisteredItem.COL_STATUS, registeredItemDTO.getStatus());
        this.registeredItemMapper.selectPage(page, qw);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @Override
    public RegisteredItem getOne(Long registeredItemId) {
        return this.registeredItemMapper.selectById(registeredItemId);
    }

    @Override
    public int addRegisteredItem(RegisteredItemDTO registeredItemDTO) {
        RegisteredItem registeredItem = new RegisteredItem();
        BeanUtil.copyProperties(registeredItemDTO, registeredItem);
        registeredItem.setCreateTime(DateUtil.date());
        registeredItem.setCreateBy(registeredItemDTO.getSimpleUser().getUserName());
        return this.registeredItemMapper.insert(registeredItem);
    }

    @Override
    public int updateRegisteredItem(RegisteredItemDTO registeredItemDTO) {
        RegisteredItem registeredItem = new RegisteredItem();
        BeanUtil.copyProperties(registeredItemDTO, registeredItem);
        registeredItem.setUpdateBy(registeredItemDTO.getSimpleUser().getUserName());
        return this.registeredItemMapper.updateById(registeredItem);
    }

    @Override
    public int deleteRegisteredItemByIds(Long[] registeredItemIds) {
        log.debug(Arrays.toString(registeredItemIds));
        List<Long> ids = Arrays.asList(registeredItemIds);
        if (ids.size() > 0) {
            return this.registeredItemMapper.deleteBatchIds(ids);
        }
        return 0;
    }

    @Override
    public List<RegisteredItem> queryAllRegisteredItems() {
        QueryWrapper<RegisteredItem> qw = new QueryWrapper<>();
        qw.eq(RegisteredItem.COL_STATUS, Constants.STATUS_TRUE);
        return this.registeredItemMapper.selectList(qw);
    }

}