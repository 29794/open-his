package xyz.ly11.service;

import xyz.ly11.domain.CheckItem;
import xyz.ly11.dto.CheckItemDTO;
import xyz.ly11.vo.DataGridView;

import java.util.List;

/**
 * @author 29794
 * @date 1/20/2021 21:46
 * 检查项目的业务接口
 */
public interface CheckItemService {

    /**
     * 分页查询
     *
     * @param checkItemDTO 分页查询数据
     * @return 查询结果
     */
    DataGridView listCheckItemPage(CheckItemDTO checkItemDTO);

    /**
     * 根据ID查询
     *
     * @param checkItemId 检查项目的id
     * @return 查询得到的检查项目
     */
    CheckItem getOne(Long checkItemId);

    /**
     * 添加
     *
     * @param checkItemDTO 检查项目
     * @return 新增结果
     */
    int addCheckItem(CheckItemDTO checkItemDTO);

    /**
     * 修改
     *
     * @param checkItemDTO 检查项目
     * @return 修改结果
     */
    int updateCheckItem(CheckItemDTO checkItemDTO);

    /**
     * 根据ID删除
     *
     * @param checkItemIds 需要删除的id
     * @return 删除结果
     */
    int deleteCheckItemByIds(Long[] checkItemIds);

    /**
     * 查询所有可用的检查项目
     *
     * @return 查询结果
     */
    List<CheckItem> queryAllCheckItems();


}