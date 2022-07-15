package com.lynn.service;

import com.lynn.pojo.DxomarkScore;

import java.util.List;

/**
 * 〈实现service类的接口〉<br>
 * 〈〉
 *
 * @author 景景
     * @create 2022/4/16
 * @since 1.0.0
 */

public interface DxomarkScoreService {

    List<DxomarkScore> getAllDxomarkScore();
    List<DxomarkScore> findDxomarkScoroByModel(String phone_model);
}