package xyz.ly11.service;

import xyz.ly11.domain.Provider;
import xyz.ly11.dto.ProviderDTO;
import xyz.ly11.vo.DataGridView;

import java.util.List;

/**
 * @author 29794
 * @date 1/23/2021 21:41
 * 供应商业务接口
 */
public interface ProviderService {

    /**
     * 分页查询
     *
     * @param providerDTO 查询条件数据
     * @return 查询结果
     */
    DataGridView listProviderPage(ProviderDTO providerDTO);

    /**
     * 根据ID查询
     *
     * @param providerId 供应商id
     * @return 查询结果
     */
    Provider getOne(Long providerId);

    /**
     * 添加
     *
     * @param providerDTO 新增供应商
     * @return 新增结果
     */
    int addProvider(ProviderDTO providerDTO);

    /**
     * 修改
     *
     * @param providerDTO 修改供应商
     * @return 修改结果
     */
    int updateProvider(ProviderDTO providerDTO);

    /**
     * 根据ID删除
     *
     * @param providerIds 删除的供应商id
     * @return 删除结果
     */
    int deleteProviderByIds(Long[] providerIds);

    /**
     * 查询所有可用供应商
     */
    List<Provider> selectAllProvider();


}