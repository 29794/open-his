package xyz.ly11.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.config.annotation.Service;
import xyz.ly11.constants.Constants;
import xyz.ly11.domain.Provider;
import xyz.ly11.dto.ProviderDTO;
import xyz.ly11.mapper.ProviderMapper;
import xyz.ly11.service.ProviderService;
import xyz.ly11.vo.DataGridView;

import java.util.Arrays;
import java.util.List;

/**
 * @author 29794
 * @date 1/23/2021 21:41
 * 供应商业务实现
 */
@RequiredArgsConstructor
@Service(version ="1.0",methods = {@Method(name = "addProvider", retries = 0)})
public class ProviderServiceImpl implements ProviderService {

    final ProviderMapper providerMapper;

    @Override
    public DataGridView listProviderPage(ProviderDTO providerDTO) {
        Page<Provider> page = new Page<>(providerDTO.getPageNum(), providerDTO.getPageSize());
        QueryWrapper<Provider> qw = new QueryWrapper<>();
        qw.like(StringUtils.isNotBlank(providerDTO.getProviderName()), Provider.COL_PROVIDER_NAME, providerDTO.getProviderName());
        qw.like(StringUtils.isNotBlank(providerDTO.getContactName()), Provider.COL_CONTACT_NAME, providerDTO.getContactName());
        //(tel like ? or mobile like ?)
        qw.and(StringUtils.isNotBlank(providerDTO.getContactTel()), providerQueryWrapper -> providerQueryWrapper.like(Provider.COL_CONTACT_TEL, providerDTO.getContactTel())
                .or().like(Provider.COL_CONTACT_MOBILE, providerDTO.getContactTel()));
        qw.eq(StringUtils.isNotBlank(providerDTO.getStatus()), Provider.COL_STATUS, providerDTO.getStatus());
        this.providerMapper.selectPage(page, qw);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @Override
    public Provider getOne(Long providerId) {
        return this.providerMapper.selectById(providerId);
    }

    @Override
    public int addProvider(ProviderDTO providerDTO) {
        Provider provider = new Provider();
        BeanUtil.copyProperties(providerDTO, provider);
        provider.setCreateTime(DateUtil.date());
        provider.setCreateBy(providerDTO.getSimpleUser().getUserName());
        return this.providerMapper.insert(provider);
    }

    @Override
    public int updateProvider(ProviderDTO providerDTO) {
        Provider provider = new Provider();
        BeanUtil.copyProperties(providerDTO, provider);
        provider.setUpdateBy(providerDTO.getSimpleUser().getUserName());
        return this.providerMapper.updateById(provider);
    }

    @Override
    public int deleteProviderByIds(Long[] providerIds) {
        List<Long> ids = Arrays.asList(providerIds);
        if (ids.size() > 0) {
            return this.providerMapper.deleteBatchIds(ids);
        }
        return 0;
    }

    @Override
    public List<Provider> selectAllProvider() {
        QueryWrapper<Provider> qw = new QueryWrapper<>();
        qw.eq(Provider.COL_STATUS, Constants.STATUS_TRUE);
        return this.providerMapper.selectList(qw);
    }


}