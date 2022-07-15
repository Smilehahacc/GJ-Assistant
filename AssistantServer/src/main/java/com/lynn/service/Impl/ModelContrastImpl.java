package com.lynn.service.Impl;
import com.lynn.dao.ModelContrastMapper;
import com.lynn.pojo.ModelContrast;
import com.lynn.service.ModelContrastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 〈由注解标记的一个service类〉<br>
 * 〈〉
 *
 * @author 景景
 * @create 2022/3/10
 * @since 1.0.0
 */

@Service
public class ModelContrastImpl implements ModelContrastService {

    /**
     *创建数据库操作接口对象
     */

    @Autowired
    ModelContrastMapper modelContrastMapper;

    /**
     * 查找对比机型是否存在
     */
    @Override
    public ModelContrast findModelContrast(int user_id, String phone_model) {
        ModelContrast modelContrast = new ModelContrast();
        modelContrast.setUser_id(user_id);
        modelContrast.setPhone_model(phone_model);
        return modelContrastMapper.findModelContrast(modelContrast);
    }


    /**
     * 查找用户添加的对比机型
     */
    @Override
    public List<ModelContrast> findUserContrast(int user_id) {
        return modelContrastMapper.findUserContrast(user_id);
    }


    /**
     * 添加一个对比机型
     */
    @Override
    public Boolean newModelContrast(int user_id, String phone_model) {
        ModelContrast modelContrast = new ModelContrast();
        modelContrast.setUser_id(user_id);
        modelContrast.setPhone_model(phone_model);
        int result = modelContrastMapper.newModelContrast(modelContrast);
        return result==1;
    }


    /**
     * 删除某个要对比的机型
     */
    @Override
    public Boolean deleteModelContrast(int user_id, String phone_model) {
        ModelContrast modelContrast = new ModelContrast();
        modelContrast.setUser_id(user_id);
        modelContrast.setPhone_model(phone_model);
        int result = modelContrastMapper.deleteModelContrast(modelContrast);
        return result==1;
    }
}