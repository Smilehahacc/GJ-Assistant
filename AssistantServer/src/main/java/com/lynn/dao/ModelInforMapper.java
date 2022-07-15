package com.lynn.dao;

import com.lynn.pojo.ModelInfor;
import com.lynn.pojo.ModelOption;

import java.util.List;
import java.util.Map;

public interface ModelInforMapper {
    /**
     * 查找所有机型
     * @return Result<List>
     */
    List<ModelInfor> findAllModel();

    List<ModelInfor> findModelByPager(Map<String, Object> params);
    long count();

    /**
     * 按机型查找
     * @param phone_model 机型名称
     * @return ModelInfor
     */
    ModelInfor findModelName(String phone_model);

    /**
     * 按机型查找
     * @param phone_model 机型名称
     * @return Result<List>
     */
    List<ModelInfor> findModelByName(String phone_model);

    /**
     * 按机型cpu查找
     * @param infor_cpu cpu名称
     * @return Result<List>
     */
    List<ModelInfor> findModelByCpu(String infor_cpu);

    /**
     * 按条件查找机型
     * @param modelOption 用户的机型筛选条件
     * @return Result<List>
     */
    List<ModelInfor> findModelByOption(ModelOption modelOption);

}
