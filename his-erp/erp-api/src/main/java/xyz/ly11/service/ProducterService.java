package xyz.ly11.service;

import xyz.ly11.domain.Producter;
import xyz.ly11.dto.ProducterDTO;
import xyz.ly11.vo.DataGridView;

import java.util.List;

/**
 * @author 29794
 * @date 1/22/2021 21:49
 * 生产厂家业务接口
 */
public interface ProducterService {

    /**
     * 分页查询
     *
     * @param producterDTO 生产厂家分页查询数据
     * @return 查询数据
     */
    DataGridView listProducterPage(ProducterDTO producterDTO);

    /**
     * 根据ID查询
     *
     * @param producterId 生产厂家id
     * @return 查询结果
     */
    Producter getOne(Long producterId);

    /**
     * 添加
     *
     * @param producterDTO 新增的生产厂家
     * @return 新增结果
     */
    int addProducter(ProducterDTO producterDTO);

    /**
     * 修改
     *
     * @param producterDTO 修改的生产厂家数据
     * @return 修改结果
     */
    int updateProducter(ProducterDTO producterDTO);

    /**
     * 根据ID删除
     *
     * @param producterIds 生产厂家的id
     * @return 删除结果
     */
    int deleteProducterByIds(Long[] producterIds);

    /**
     * 查询所有可用的生产厂家
     */
    List<Producter> selectAllProducter();


}