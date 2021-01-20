package xyz.ly11.service;

import xyz.ly11.domain.RegisteredItem;
import xyz.ly11.dto.RegisteredItemDTO;
import xyz.ly11.vo.DataGridView;

import java.util.List;

/**
 * @author 29794
 * @date 1/20/2021 22:11
 * 挂号费用的业务接口
 */
public interface RegisteredItemService {

    /**
     * 分页查询
     *
     * @param registeredItemDTO 分页查询数据
     * @return 查询结果
     */
    DataGridView listRegisteredItemPage(RegisteredItemDTO registeredItemDTO);

    /**
     * 根据ID查询
     *
     * @param registeredItemId 挂号费用的id
     * @return 查询结果
     */
    RegisteredItem getOne(Long registeredItemId);

    /**
     * 添加
     *
     * @param registeredItemDTO 挂号费用
     * @return 新增结果
     */
    int addRegisteredItem(RegisteredItemDTO registeredItemDTO);

    /**
     * 修改
     *
     * @param registeredItemDTO 修改的挂号费用
     * @return 修改结果
     */
    int updateRegisteredItem(RegisteredItemDTO registeredItemDTO);

    /**
     * 根据ID删除
     *
     * @param registeredItemIds 挂号费用
     * @return 删除结果
     */
    int deleteRegisteredItemByIds(Long[] registeredItemIds);

    /**
     * 查询所有可用的挂号项目
     *
     * @return 查询结果
     */
    List<RegisteredItem> queryAllRegisteredItems();


}