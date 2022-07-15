package com.lynn.service.Impl;

/**
 * 〈由注解标记的一个service类〉<br>
 * 〈〉
 *
 * @author 景景
 * @create 2022/2/17
 * @since 1.0.0
 */

import com.lynn.dao.AssistantUserMapper;
import com.lynn.dao.ModelEvaluateMapper;
import com.lynn.pojo.AssistantUser;
import com.lynn.pojo.ModelEvaluate;
import com.lynn.service.ModelEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ModelEvaluateImpl implements ModelEvaluateService {
    /**
     *创建数据库操作接口对象
     */

    @Autowired
    ModelEvaluateMapper modelEvaluateMapper;
    @Autowired
    AssistantUserMapper assistantUserMapper;

    /**
     * 获取机型对应的评价
     */
    @Override
    public List<ModelEvaluate> getEvaluate(int phone_id) {
        return modelEvaluateMapper.getEvaluate(phone_id);
    }

    /**
     * 获取所有机型评价
     */
    @Override
    public List<ModelEvaluate> getAllEvaluate() {
        return modelEvaluateMapper.getAllEvaluate();
    }

    /**
     * 按用户名获取机型评价
     */
    @Override
    public List<ModelEvaluate> findEvaluateByUser(String user_name) {
        return modelEvaluateMapper.findEvaluateByUser(user_name);
    }

    /**
     * 按机型获取机型评价
     */
    @Override
    public List<ModelEvaluate> findEvaluateByModel(String phone_model) {
        return modelEvaluateMapper.findEvaluateByModel(phone_model);
    }

    /**
     * 新增机型用户评价
     */
    @Override
    public Boolean newEvaluate(int user_id, int phone_id, String evaluate_content, int evaluate_date) {
        ModelEvaluate modelEvaluate = new ModelEvaluate();
        modelEvaluate.setUser_id(user_id);
        modelEvaluate.setPhone_id(phone_id);
        modelEvaluate.setEvaluate_content(evaluate_content);
        modelEvaluate.setEvaluate_date(evaluate_date);
        AssistantUser u = new AssistantUser();
        u.setUser_id(user_id);
        AssistantUser user = assistantUserMapper.findUser(u);
        modelEvaluate.setUser_name(user.getUser_name());
        modelEvaluate.setUser_portrait(user.getUser_portrait());
        int result = modelEvaluateMapper.newEvaluate(modelEvaluate);
        return result==1;
    }

    /**
     * 用户删除对应的机型评价
     */
    @Override
    public Boolean deleteEvaluate(int evaluate_id) {
        int result = modelEvaluateMapper.deleteEvaluate(evaluate_id);
        return result==1;
    }

    /**
     * 管理员恢复机型评价
     */
    @Override
    public Boolean recoveryEvaluate(int evaluate_id) {
        int result = modelEvaluateMapper.recoveryEvaluate(evaluate_id);
        return result==1;
    }
}