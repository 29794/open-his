package xyz.ly11.service;

import xyz.ly11.domain.DictData;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.ly11.dto.DictDataDTO;
import xyz.ly11.vo.DataGridView;

import java.util.List;

/**
 * @author by 29794
 * @date 2020/10/15 23:54
 */
public interface DictDataService {

    /**
     * 分页查询字典数据类型
     *
     * @param dictDataDTO
     * @return
     */
    DataGridView listPage(DictDataDTO dictDataDTO);


    /**
     * 插入新的字典类型
     *
     * @param dictDataDTO
     * @return
     */
    int insert(DictDataDTO dictDataDTO);

    /**
     * 修改的字典类型
     *
     * @param dictDataDTO
     * @return
     */
    int update(DictDataDTO dictDataDTO);

    /**
     * 根据ID删除字典类型
     *
     * @param dictCodeIds
     * @return
     */
    int deleteDictDataByIds(Long[] dictCodeIds);

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType
     * @return
     */
    List<DictData> selectDictDataByDictType(String dictType);

    /**
     * 根据ID查询一个字典类型
     *
     * @param dictCode
     * @return
     */
    DictData selectDictDataById(Long dictCode);


}
