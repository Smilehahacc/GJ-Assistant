package com.lynn.dao;

import com.lynn.pojo.ModelContrast;

import java.util.List;

public interface ModelContrastMapper {

    /**
     * 查找对比机型是否存在
     * @param modelContrast 机型对比对象
     * @return Result<List>
     */
    ModelContrast findModelContrast(ModelContrast modelContrast);

    /**
     * 查找用户选定的对比机型
     * @param user_id 用户id
     * @return Result<List>
     */
    List<ModelContrast> findUserContrast(int user_id);

    /**
     * 添加对比机型
     * @param modelContrast 机型对比对象
     * @return Result<List>
     */
    int newModelContrast(ModelContrast modelContrast);

    /**
     * 删除对比机型
     * @param modelContrast 机型对比对象
     * @return Result<List>
     */
    int deleteModelContrast(ModelContrast modelContrast);

}
