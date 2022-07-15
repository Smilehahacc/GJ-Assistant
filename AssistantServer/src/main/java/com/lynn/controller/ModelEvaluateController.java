package com.lynn.controller;

import com.lynn.pojo.ModelEvaluate;
import com.lynn.service.ModelEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ModelEvaluateController {

    /**
     *创建数据库操作接口对象
     */
    @Autowired
    ModelEvaluateService modelEvaluateService;


    /**
     *获取机型评价
     */
    @RequestMapping(value ="/test")
    public String test(){
        return "SUCCESS";
    }

    /**
     *获取机型评价
     */
    @RequestMapping(value ="/getEvaluate")
    public List<ModelEvaluate> getEvaluate(@RequestParam("phone_id") int phone_id){
        return modelEvaluateService.getEvaluate(phone_id);
    }

    /**
     *获取所有机型评价
     */
    @RequestMapping(value ="/getAllEvaluate")
    public List<ModelEvaluate> getAllEvaluate(){
        return modelEvaluateService.getAllEvaluate();
    }

    /**
     *根据用户名获取机型评价
     */
    @RequestMapping(value ="/findEvaluateByUser")
    public List<ModelEvaluate> findEvaluateByUser(@RequestParam("user_name") String user_name){
        return modelEvaluateService.findEvaluateByUser(user_name);
    }

    /**
     *根据机型获取机型评价
     */
    @RequestMapping(value ="/findEvaluateByModel")
    public List<ModelEvaluate> findEvaluateByModel(@RequestParam("phone_model") String phone_model){
        return modelEvaluateService.findEvaluateByModel(phone_model);
    }

    /**
     *新增机型评价
     */
    @RequestMapping(value ="/newEvaluate")
    public String newEvaluate(@RequestParam("user_id") int user_id, @RequestParam("phone_id") int phone_id,
                              @RequestParam("evaluate_content") String evaluate_content,
                              @RequestParam("evaluate_date") int evaluate_date){
        if(modelEvaluateService.newEvaluate(user_id, phone_id, evaluate_content, evaluate_date)) {
            return "SUCCESS";
        } else {
            return "ERROR";
        }
    }

    /**
     *删除机型评价
     */
    @RequestMapping(value ="/deleteEvaluate")
    public String deleteEvaluate(@RequestParam("evaluate_id") int evaluate_id){
        if(modelEvaluateService.deleteEvaluate(evaluate_id)) {
            return "SUCCESS";
        } else {
            return "ERROR";
        }
    }

    /**
     *恢复机型评价
     */
    @RequestMapping(value ="/recoveryEvaluate")
    public String recoveryEvaluate(@RequestParam("evaluate_id") int evaluate_id){
        if(modelEvaluateService.recoveryEvaluate(evaluate_id)) {
            return "SUCCESS";
        } else {
            return "ERROR";
        }
    }
}