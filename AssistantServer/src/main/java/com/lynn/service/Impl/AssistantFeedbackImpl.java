package com.lynn.service.Impl;

/**
 * 〈由注解标记的一个service类〉<br>
 * 〈〉
 *
 * @author 景景
 * @create 2022/2/17
 * @since 1.0.0
 */

import com.lynn.dao.AssistantFeedbackMapper;
import com.lynn.dao.AssistantUserMapper;
import com.lynn.dao.ModelEvaluateMapper;
import com.lynn.pojo.AssistantFeedback;
import com.lynn.pojo.AssistantUser;
import com.lynn.pojo.ModelEvaluate;
import com.lynn.service.AssistantFeedbackService;
import com.lynn.service.ModelEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AssistantFeedbackImpl implements AssistantFeedbackService {
    /**
     *创建数据库操作接口对象
     */

    @Autowired
    AssistantFeedbackMapper assistantFeedbackMapper;

    /**
     * 获取所有问题反馈
     */
    @Override
    public List<AssistantFeedback> getAllFeedback() {
        return assistantFeedbackMapper.getAllFeedback();
    }

    /**
     * 根据模块查找问题反馈
     */
    @Override
    public List<AssistantFeedback> findFeedbackByModular(String feedback_modular) {
        return assistantFeedbackMapper.findFeedbackByModular(feedback_modular);
    }

    /**
     * 根据发生频率查找问题反馈
     */
    @Override
    public List<AssistantFeedback> findFeedbackByFrequency(String feedback_frequency) {
        return assistantFeedbackMapper.findFeedbackByFrequency(feedback_frequency);
    }

    /**
     * 新增问题反馈
     */
    @Override
    public Boolean newFeedback(String feedback_resume, String feedback_detail,
                               String feedback_modular, String feedback_frequency,
                               int feedback_date, String feedback_user) {
        AssistantFeedback assistantFeedback = new AssistantFeedback();
        assistantFeedback.setFeedback_resume(feedback_resume);
        assistantFeedback.setFeedback_detail(feedback_detail);
        assistantFeedback.setFeedback_modular(feedback_modular);
        assistantFeedback.setFeedback_frequency(feedback_frequency);
        assistantFeedback.setFeedback_date(feedback_date);
        assistantFeedback.setFeedback_user(feedback_user);
        int result = assistantFeedbackMapper.newFeedback(assistantFeedback);
        return result == 1;
    }

    /**
     * 管理员删除问题反馈（问题已解决）
     */
    @Override
    public Boolean deleteFeedback(int feedback_id) {
        int result = assistantFeedbackMapper.deleteFeedback(feedback_id);
        return result == 1;
    }

    /**
     * 管理员恢复问题反馈（问题还未解决）
     */
    @Override
    public Boolean recoveryFeedback(int feedback_id) {
        int result = assistantFeedbackMapper.recoveryFeedback(feedback_id);
        return result == 1;
    }

}