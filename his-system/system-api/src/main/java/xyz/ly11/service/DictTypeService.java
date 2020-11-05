package xyz.ly11.service;

import xyz.ly11.domain.DictType;
import xyz.ly11.dto.DictTypeDTO;
import xyz.ly11.vo.DataGridView;

/**
 * @author by 29794
 * @date 2020/10/15 23:30
 */
public interface DictTypeService {

    /**
     * 分页查询字典类型
     *
     * @param dictTypeDTO
     * @return
     */
    DataGridView listPage(DictTypeDTO dictTypeDTO);

    /**
     * 查询所有字典类型
     *
     * @return
     */
    DataGridView list();

    /**
     * 检查字典类型是否存在
     *
     * @param dictType
     * @return
     */
    Boolean checkDictTypeUnique(Long dictId, String dictType);

    /**
     * 插入新的字典类型
     *
     * @param dictTypeDTO
     * @return
     */
    int insert(DictTypeDTO dictTypeDTO);

    /**
     * 修改的字典类型
     *
     * @param dictTypeDTO
     * @return
     */
    int update(DictTypeDTO dictTypeDTO);

    /**
     * 根据ID删除字典类型
     *
     * @param dictIds
     * @return
     */
    int deleteDictTypeByIds(Long[] dictIds);

    /**
     * 根据ID查询一个字典类型
     *
     * @param dictId
     * @return
     */
    DictType selectDictTypeById(Long dictId);

    /**
     * 同步字典数据到缓存
     */
    void dictCacheAsync();

}
