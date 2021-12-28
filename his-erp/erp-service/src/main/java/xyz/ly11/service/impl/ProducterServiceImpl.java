package xyz.ly11.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.config.annotation.Service;
import xyz.ly11.constants.Constants;
import xyz.ly11.domain.Producter;
import xyz.ly11.dto.ProducterDTO;
import xyz.ly11.mapper.ProducterMapper;
import xyz.ly11.service.ProducterService;
import xyz.ly11.vo.DataGridView;

import java.util.Arrays;
import java.util.List;

/**
 * @author 29794
 * @date 1/22/2021 21:49
 * 生产厂家的业务接口的实现
 */
@Slf4j
@Service(methods = {@Method(name = "addProducter",retries = 0)})/*注意使用的是 Dubbo 的Service注解*/
@RequiredArgsConstructor
public class ProducterServiceImpl implements ProducterService {

    final ProducterMapper producterMapper;

    @Override
    public DataGridView listProducterPage(ProducterDTO producterDTO) {
        Page<Producter> page = new Page<>(producterDTO.getPageNum(), producterDTO.getPageSize());
        QueryWrapper<Producter> qw = new QueryWrapper<>();
        qw.like(StringUtils.isNotBlank(producterDTO.getProducterName()), Producter.COL_PRODUCTER_NAME, producterDTO.getProducterName());
        qw.like(StringUtils.isNotBlank(producterDTO.getKeywords()), Producter.COL_KEYWORDS, producterDTO.getKeywords());
        qw.eq(StringUtils.isNotBlank(producterDTO.getProducterTel()), Producter.COL_PRODUCTER_TEL, producterDTO.getProducterTel());
        qw.eq(StringUtils.isNotBlank(producterDTO.getStatus()), Producter.COL_STATUS, producterDTO.getStatus());

        // 大于
        qw.ge(producterDTO.getBeginTime() != null, Producter.COL_CREATE_TIME, producterDTO.getBeginTime());
        // 小于
        qw.le(producterDTO.getEndTime() != null, Producter.COL_CREATE_TIME, producterDTO.getEndTime());
        this.producterMapper.selectPage(page, qw);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @Override
    public Producter getOne(Long producterId) {
        return this.producterMapper.selectById(producterId);
    }

    @Override
    public int addProducter(ProducterDTO producterDTO) {
        Producter producter = new Producter();
        BeanUtil.copyProperties(producterDTO, producter);
        producter.setCreateTime(DateUtil.date());
        producter.setCreateBy(producterDTO.getSimpleUser().getUserName());
        return this.producterMapper.insert(producter);
    }

    @Override
    public int updateProducter(ProducterDTO producterDTO) {
        Producter producter = new Producter();
        BeanUtil.copyProperties(producterDTO, producter);
        producter.setUpdateBy(producterDTO.getSimpleUser().getUserName());
        return this.producterMapper.updateById(producter);
    }

    @Override
    public int deleteProducterByIds(Long[] producterIds) {
        List<Long> ids = Arrays.asList(producterIds);
        if (ids.size() > 0) {
            return this.producterMapper.deleteBatchIds(ids);
        }
        return 0;
    }

    @Override
    public List<Producter> selectAllProducter() {
        QueryWrapper<Producter> qw = new QueryWrapper<>();
        qw.eq(Producter.COL_STATUS, Constants.STATUS_TRUE);
        return this.producterMapper.selectList(qw);
    }

}