package com.lynn.dao;

import com.lynn.pojo.ModelContrast;
import com.lynn.pojo.ModelEvaluate;
import com.lynn.pojo.ModelRemind;

import java.util.List;

public interface ModelRemindMapper {

    /**
     * 查找机型标记是否存在
     * @param modelRemind 机型标记对象
     * @return Result<List>
     */
    ModelRemind findModelRemind(ModelRemind modelRemind);

    /**
     * 根据id查找机型标记是否存在
     * @param remind_id 机型标记id
     * @return Result<List>
     */
    ModelRemind findModelRemindById(int remind_id);

    /**
     * 查找用户选定的机型标记
     * @param user_id 用户id
     * @return Result<List>
     */
    List<ModelRemind> findUserRemind(int user_id);

    /**
     * 添加机型标记
     * @param modelRemind 机型标记对象
     * @return Result<List>
     */
    int newModelRemind(ModelRemind modelRemind);

    /**
     * 删除机型标记
     * @param modelRemind 机型标记对象
     * @return Result<List>
     */
    int deleteModelRemind(ModelRemind modelRemind);

    /**
     * 更新机型标记
     * @param modelRemind 机型标记对象
     * @return Result<List>
     */
    int updateModelRemind(ModelRemind modelRemind);

}
