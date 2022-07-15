package com.lynn.dao;

import com.lynn.pojo.ModelEvaluate;

import java.util.List;

public interface ModelEvaluateMapper {
    /**
     * 获取机型对应的评价
     * @param phone_id 机型的id
     * @return Result<List>
     */
    List<ModelEvaluate> getEvaluate(int phone_id);

    /**
     * 获取所有机型评价
     * @return Result<List>
     */
    List<ModelEvaluate> getAllEvaluate();

    /**
     * 获取用户对机型的评价
     * @param user_name 用户名
     * @return Result<List>
     */
    List<ModelEvaluate> findEvaluateByUser(String user_name);


    /**
     * 获取某机型的所有评价
     * @param phone_model 机型名
     * @return Result<List>
     */
    List<ModelEvaluate> findEvaluateByModel(String phone_model);

    /**
     * 新增评价
     * @param modelEvaluate 用户的评价
     * @return Result<int>
     */
    int newEvaluate(ModelEvaluate modelEvaluate);

    /**
     * 用户删除自己对于机型的评价
     * @param evaluate_id 评价id
     * @return Result<int>
     */
    int deleteEvaluate(int evaluate_id);

    /**
     * 管理员恢复对于机型的评价
     * @param evaluate_id 评价id
     * @return Result<int>
     */
    int recoveryEvaluate(int evaluate_id);


}
