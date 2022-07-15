package com.lynn.dao;

import com.lynn.pojo.AssistantFeedback;

import java.util.List;

public interface AssistantFeedbackMapper {

    /**
     * 获取所有问题反馈
     * @return Result<List>
     */
    List<AssistantFeedback> getAllFeedback();

    /**
     * 根据模块查找问题反馈
     * @param feedback_modular 问题发生模块
     * @return Result<List>
     */
    List<AssistantFeedback> findFeedbackByModular(String feedback_modular);


    /**
     * 根据发生频率查找问题反馈
     * @param feedback_frequency 问题发生频率
     * @return Result<List>
     */
    List<AssistantFeedback> findFeedbackByFrequency(String feedback_frequency);

    /**
     * 新增问题反馈
     * @param assistantFeedback 问题反馈
     * @return Result<int>
     */
    int newFeedback(AssistantFeedback assistantFeedback);

    /**
     * 管理员删除问题反馈（问题已解决）
     * @param feedback_id 问题反馈id
     * @return Result<int>
     */
    int deleteFeedback(int feedback_id);

    /**
     * 管理员恢复问题反馈（问题还未解决）
     * @param feedback_id 问题反馈id
     * @return Result<int>
     */
    int recoveryFeedback(int feedback_id);
}
