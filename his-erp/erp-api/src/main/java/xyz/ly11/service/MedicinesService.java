package xyz.ly11.service;

import xyz.ly11.domain.Medicines;
import xyz.ly11.dto.MedicinesDTO;
import xyz.ly11.vo.DataGridView;

import java.util.List;

/**
 * @author 29794
 * @date 1/23/2021 21:24
 * 药品信息的业务接口
 */
public interface MedicinesService {

    /**
     * 分页查询
     *
     * @param medicinesDTO i查询条件
     * @return 查询结果
     */
    DataGridView listMedicinesPage(MedicinesDTO medicinesDTO);

    /**
     * 根据ID查询
     *
     * @param medicinesId 药品信息Id
     * @return 查询结果
     */
    Medicines getOne(Long medicinesId);

    /**
     * 添加
     *
     * @param medicinesDTO 新增的药品信息的数据
     * @return 新增结果
     */
    int addMedicines(MedicinesDTO medicinesDTO);

    /**
     * 修改
     *
     * @param medicinesDTO 修过数据
     * @return 修改结果
     */
    int updateMedicines(MedicinesDTO medicinesDTO);

    /**
     * 根据ID删除
     *
     * @param medicinesIds 删除的id
     * @return 删除结果
     */
    int deleteMedicinesByIds(Long[] medicinesIds);

    /**
     * 查询所有可用生产厂家
     */
    List<Medicines> selectAllMedicines();

    /**
     * 调整库存
     */
    int updateMedicinesStorage(Long medicinesId, Long medicinesStockNum);


}