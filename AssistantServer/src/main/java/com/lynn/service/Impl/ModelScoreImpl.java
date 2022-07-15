package com.lynn.service.Impl;

import com.lynn.dao.ModelEvaluateMapper;
import com.lynn.dao.ModelScoreMapper;
import com.lynn.pojo.ModelContrast;
import com.lynn.pojo.ModelInfor;
import com.lynn.pojo.ModelScore;
import com.lynn.service.ModelScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈由注解标记的一个service类〉<br>
 * 〈〉
 *
 * @author 景景
 * @create 2022/2/17
 * @since 1.0.0
 */

@Service
public class ModelScoreImpl implements ModelScoreService {

    /**
     *创建数据库操作接口对象
     */

    @Autowired
    ModelScoreMapper modelScoreMapper;

    /**
     * 获取机型对应的用户打分
     */
    @Override
    public ModelScore getScore(int user_id, int phone_id) {
        ModelScore modelScore = new ModelScore();
        modelScore.setUser_id(user_id);
        modelScore.setPhone_id(phone_id);
        return modelScoreMapper.getScore(modelScore);
    }

    /**
     * 获取机型对应平均分
     */
    @Override
    public ModelScore getAverageScore(int phone_id) {
        List<ModelScore> list  = modelScoreMapper.getModelScore(phone_id);
        float[] score = new float[6];
        float count_all = 0;
        int count = list.size();
        for (ModelScore s:list) {
            score[0] += s.getScore_appearance();
            score[1] += s.getScore_camera();
            score[2] += s.getScore_endurance();
            score[3] += s.getScore_performance();
            score[4] += s.getScore_screen();
        }
        for(int i = 0; i<5; i++) {
            score[i] =(float)(Math.round(score[i]/count*100)/100);
            count_all += score[i];
        }
        score[5] = count_all/5;
        ModelScore modelScore = new ModelScore();
        modelScore.setPhone_id(phone_id);
        modelScore.setScore_appearance(score[0]);
        modelScore.setScore_camera(score[1]);
        modelScore.setScore_endurance(score[2]);
        modelScore.setScore_performance(score[3]);
        modelScore.setScore_screen(score[4]);
        modelScore.setScore_verage(score[5]);
        return modelScore;
    }

    /**
     * 新增用户打分
     */
    @Override
    public Boolean newScore(int user_id, int phone_id, float score_appearance, float score_camera,
                               float score_endurance, float score_performance, float score_screen) {
        ModelScore modelScore = new ModelScore();
        modelScore.setUser_id(user_id);
        modelScore.setPhone_id(phone_id);
        modelScore.setScore_appearance(score_appearance);
        modelScore.setScore_camera(score_camera);
        modelScore.setScore_endurance(score_endurance);
        modelScore.setScore_performance(score_performance);
        modelScore.setScore_screen(score_screen);
        int result = modelScoreMapper.newScore(modelScore);
        return result==1;
    }

    /**
     * 修改用户打分
     */
    @Override
    public Boolean modifyScore(int score_id, float score_appearance, float score_camera, float score_endurance,
                        float score_performance, float score_screen) {
        ModelScore modelScore = new ModelScore();
        modelScore.setScore_id(score_id);
        modelScore.setScore_appearance(score_appearance);
        modelScore.setScore_camera(score_camera);
        modelScore.setScore_endurance(score_endurance);
        modelScore.setScore_performance(score_performance);
        modelScore.setScore_screen(score_screen);
        int result = modelScoreMapper.modifyScore(modelScore);
        return result==1;
    }
}