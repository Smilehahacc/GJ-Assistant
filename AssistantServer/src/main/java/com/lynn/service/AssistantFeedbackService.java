package com.lynn.service;

import com.lynn.pojo.AssistantFeedback;
import com.lynn.pojo.ModelEvaluate;

import java.util.List;

/**
 * 〈实现service类的接口〉<br>
 * 〈〉
 *
 * @author 景景
 * @create 2022/4/12
 * @since 1.0.0
 */

public interface AssistantFeedbackService {

    List<AssistantFeedback> getAllFeedback();
    List<AssistantFeedback> findFeedbackByModular(String feedback_modular);
    List<AssistantFeedback> findFeedbackByFrequency(String feedback_frequency);
    Boolean newFeedback(String feedback_resume, String feedback_detail, String feedback_modular,
                        String feedback_frequency, int feedback_date, String feedback_user);
    Boolean deleteFeedback(int feedback_id);
    Boolean recoveryFeedback(int feedback_id);
}