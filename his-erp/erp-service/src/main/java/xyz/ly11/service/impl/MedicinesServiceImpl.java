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
import xyz.ly11.domain.Medicines;
import xyz.ly11.dto.MedicinesDTO;
import xyz.ly11.mapper.MedicinesMapper;
import xyz.ly11.service.MedicinesService;
import xyz.ly11.vo.DataGridView;

import java.util.Arrays;
import java.util.List;

/**
 * @author 29794
 * @date 1/23/2021 21:24
 * 药品信息的业务实现
 */
@RequiredArgsConstructor
@Service(methods = {@Method(name = "addMedicines", retries = 0)})
public class MedicinesServiceImpl implements MedicinesService {

    final MedicinesMapper medicinesMapper;

    @Override
    public DataGridView listMedicinesPage(MedicinesDTO medicinesDTO) {
        Page<Medicines> page = new Page<>(medicinesDTO.getPageNum(), medicinesDTO.getPageSize());
        QueryWrapper<Medicines> qw = new QueryWrapper<>();
        qw.like(StringUtils.isNotBlank(medicinesDTO.getMedicinesName()), Medicines.COL_MEDICINES_NAME, medicinesDTO.getMedicinesName());
        qw.like(StringUtils.isNotBlank(medicinesDTO.getKeywords()), Medicines.COL_KEYWORDS, medicinesDTO.getKeywords());
        qw.eq(StringUtils.isNotBlank(medicinesDTO.getMedicinesType()), Medicines.COL_MEDICINES_TYPE, medicinesDTO.getMedicinesType());
        qw.eq(StringUtils.isNotBlank(medicinesDTO.getProducterId()), Medicines.COL_PRODUCTER_ID, medicinesDTO.getProducterId());
        qw.eq(StringUtils.isNotBlank(medicinesDTO.getPrescriptionType()), Medicines.COL_PRESCRIPTION_TYPE, medicinesDTO.getPrescriptionType());
        qw.eq(StringUtils.isNotBlank(medicinesDTO.getStatus()), Medicines.COL_STATUS, medicinesDTO.getStatus());
        this.medicinesMapper.selectPage(page, qw);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @Override
    public Medicines getOne(Long medicinesId) {
        return this.medicinesMapper.selectById(medicinesId);
    }

    @Override
    public int addMedicines(MedicinesDTO medicinesDTO) {
        Medicines medicines = new Medicines();
        BeanUtil.copyProperties(medicinesDTO, medicines);
        medicines.setCreateTime(DateUtil.date());
        medicines.setCreateBy(medicinesDTO.getSimpleUser().getUserName());
        return this.medicinesMapper.insert(medicines);
    }

    @Override
    public int updateMedicines(MedicinesDTO medicinesDTO) {
        Medicines medicines = new Medicines();
        BeanUtil.copyProperties(medicinesDTO, medicines);
        medicines.setUpdateBy(medicinesDTO.getSimpleUser().getUserName());
        return this.medicinesMapper.updateById(medicines);
    }

    @Override
    public int deleteMedicinesByIds(Long[] medicinesIds) {
        List<Long> ids = Arrays.asList(medicinesIds);
        if (ids.size() > 0) {
            return this.medicinesMapper.deleteBatchIds(ids);
        }
        return 0;
    }

    @Override
    public List<Medicines> selectAllMedicines() {
        QueryWrapper<Medicines> qw = new QueryWrapper<>();
        qw.eq(Medicines.COL_STATUS, Constants.STATUS_TRUE);
        return this.medicinesMapper.selectList(qw);
    }

    @Override
    public int updateMedicinesStorage(Long medicinesId, Long medicinesStockNum) {
        Medicines medicines = new Medicines();
        medicines.setMedicinesId(medicinesId);
        medicines.setMedicinesStockNum(medicinesStockNum);
        return this.medicinesMapper.updateById(medicines);
    }

}