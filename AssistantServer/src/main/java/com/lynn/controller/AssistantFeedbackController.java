package com.lynn.controller;

import com.lynn.pojo.AssistantFeedback;
import com.lynn.pojo.ModelEvaluate;
import com.lynn.service.AssistantFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AssistantFeedbackController {

    /**
     *创建数据库操作接口对象
     */
    @Autowired
    AssistantFeedbackService assistantFeedbackService;


    /**
     *获取所有问题反馈
     */
    @RequestMapping(value ="/getAllFeedback")
    public List<AssistantFeedback> getAllFeedback(){
        return assistantFeedbackService.getAllFeedback();
    }

    /**
     *根据模块查找问题反馈
     */
    @RequestMapping(value ="/findFeedbackByModular")
    public List<AssistantFeedback> findFeedbackByModular(@RequestParam("feedback_modular") String feedback_modular){
        return assistantFeedbackService.findFeedbackByModular(feedback_modular);
    }

    /**
     *根据发生频率查找问题反馈
     */
    @RequestMapping(value ="/findFeedbackByFrequency")
    public List<AssistantFeedback> findFeedbackByFrequency(@RequestParam("feedback_frequency") String feedback_frequency){
        return assistantFeedbackService.findFeedbackByFrequency(feedback_frequency);
    }

    /**
     *新增问题反馈
     */
    @RequestMapping(value ="/newFeedback")
    public String newFeedback(@RequestParam("feedback_resume") String feedback_resume,
                              @RequestParam("feedback_detail") String feedback_detail,
                              @RequestParam("feedback_modular") String feedback_modular,
                              @RequestParam("feedback_frequency") String feedback_frequency,
                              @RequestParam("feedback_date") int feedback_date,
                              @RequestParam("feedback_user") String feedback_user){
        if(assistantFeedbackService.newFeedback(feedback_resume, feedback_detail, feedback_modular,
                feedback_frequency, feedback_date, feedback_user)) {
            return "SUCCESS";
        } else {
            return "ERROR";
        }
    }

    /**
     *管理员删除问题反馈（问题已解决）
     */
    @RequestMapping(value ="/deleteFeedback")
    public String deleteFeedback(@RequestParam("feedback_id") int feedback_id){
        if(assistantFeedbackService.deleteFeedback(feedback_id)) {
            return "SUCCESS";
        } else {
            return "ERROR";
        }
    }

    /**
     * 管理员恢复问题反馈（问题还未解决）
     */
    @RequestMapping(value ="/recoveryFeedback")
    public String recoveryFeedback(@RequestParam("feedback_id") int feedback_id){
        if(assistantFeedbackService.recoveryFeedback(feedback_id)) {
            return "SUCCESS";
        } else {
            return "ERROR";
        }
    }
}