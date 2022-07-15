package com.lynn.dao;

import com.lynn.pojo.DxomarkScore;

import java.util.List;

public interface DxomarkScoreMapper {
    /**
     * 查找所有分数
     * @return Result<List>
     */
    List<DxomarkScore> getAllDxomarkScore();

    /**
     * 按机型查找
     * @param phone_model 机型名称
     * @return List
     */
    List<DxomarkScore> findDxomarkScoroByModel(String phone_model);

}
