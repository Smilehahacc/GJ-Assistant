package com.lynn.dao;

import com.lynn.pojo.ModelScore;

import java.util.List;


public interface ModelScoreMapper {
    /**
     * 获取用户对机型的打分
     * @param modelScore 机型的评分对象
     * @return Result<List>
     */
    ModelScore getScore(ModelScore modelScore);

    /**
     * 获取机型的所有用户打分
     * @param phone_id 机型的id
     * @return Result<List>
     */
    List<ModelScore> getModelScore(int phone_id);

    /**
     *
     * @param modelScore 用户的打分
     * @return Result<int>
     */
    int newScore(ModelScore modelScore);

    /**
     * 用户删除自己对于机型的评价
     * @param modelScore 用户的打分
     * @return Result<int>
     */
    int modifyScore(ModelScore modelScore);

}
