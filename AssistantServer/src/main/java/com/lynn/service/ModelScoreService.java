package com.lynn.service;

import com.lynn.pojo.ModelScore;

/**
 * 〈实现service类的接口〉<br>
 * 〈〉
 *
 * @author 景景
 * @create 2022/2/23
 * @since 1.0.0
 */

public interface ModelScoreService {
    ModelScore getScore(int user_id, int phone_id);
    ModelScore getAverageScore(int phone_id);
    Boolean newScore(int user_id, int phone_id, float score_appearance, float score_camera, float score_endurance,
                     float score_performance, float score_screen);
    Boolean modifyScore(int score_id, float score_appearance, float score_camera, float score_endurance,
                        float score_performance, float score_screen);
}