package com.lynn.controller;

import com.lynn.pojo.ModelScore;
import com.lynn.service.ModelScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ModelScoreController {
    /**
     *创建数据库操作接口对象
     */
    @Autowired
    ModelScoreService modelScoreService;

    /**
     *获取机型评价
     */
    @RequestMapping(value ="/getScore")
    public ModelScore getScore(@RequestParam("user_id") int user_id, @RequestParam("phone_id") int phone_id) {
        return modelScoreService.getScore(user_id, phone_id);
    }

    /**
     *获取机型平均分
     */
    @RequestMapping(value ="/getAverageScore")
    public ModelScore getAverageScore(@RequestParam("phone_id") int phone_id) {
        return modelScoreService.getAverageScore(phone_id);
    }



    /**
     *获取机型用户打分
     */
    @RequestMapping(value ="/newScore")
    public String newScore(@RequestParam("user_id") int user_id, @RequestParam("phone_id") int phone_id,
                           @RequestParam("score_appearance") String score_appearance,
                           @RequestParam("score_camera") String score_camera,
                           @RequestParam("score_endurance") String score_endurance,
                           @RequestParam("score_performance") String score_performance,
                           @RequestParam("score_screen") String score_screen) {
        float a,b,c,d,e;
        try {
            a = Float.parseFloat(score_appearance);
            b = Float.parseFloat(score_camera);
            c = Float.parseFloat(score_endurance);
            d = Float.parseFloat(score_performance);
            e = Float.parseFloat(score_screen);
        } catch (Exception exp) {
            System.out.println("发生错误"+exp.getMessage());
            return "ERROR";
        }
        if(modelScoreService.newScore(user_id, phone_id, a, b, c, d, e)) {
            return "SUCCESS";
        } else {
            return "ERROR";
        }
    }

    /**
     *修改机型用户打分
     */
    @RequestMapping(value ="/modifyScore")
    public String modifyScore(@RequestParam("score_id") int score_id,
                              @RequestParam("score_appearance") String score_appearance,
                              @RequestParam("score_camera") String score_camera,
                              @RequestParam("score_endurance") String score_endurance,
                              @RequestParam("score_performance") String score_performance,
                              @RequestParam("score_screen") String score_screen) {
        float a,b,c,d,e;
        try {
            a = score_appearance.equals("")?0:Float.parseFloat(score_appearance);
            b = score_camera.equals("")?0:Float.parseFloat(score_camera);
            c = score_endurance.equals("")?0:Float.parseFloat(score_endurance);
            d = score_performance.equals("")?0:Float.parseFloat(score_performance);
            e = score_screen.equals("")?0:Float.parseFloat(score_screen);
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
            return "ERROR";
        }
        if(modelScoreService.modifyScore(score_id, a, b, c, d, e)) {
            return "SUCCESS";
        } else {
            return "ERROR";
        }
    }
}