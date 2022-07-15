package com.lynn.service;

import com.lynn.pojo.ModelEvaluate;

import java.util.List;

/**
 * 〈实现service类的接口〉<br>
 * 〈〉
 *
 * @author 景景
 * @create 2022/2/23
 * @since 1.0.0
 */

public interface ModelEvaluateService {

    List<ModelEvaluate> getEvaluate(int phone_id);
    List<ModelEvaluate> getAllEvaluate();
    List<ModelEvaluate> findEvaluateByUser(String user_name);
    List<ModelEvaluate> findEvaluateByModel(String phone_model);
    Boolean newEvaluate(int user_id, int phone_id, String evaluate_content, int evaluate_date);
    Boolean deleteEvaluate(int evaluate_id);
    Boolean recoveryEvaluate(int evaluate_id);
}