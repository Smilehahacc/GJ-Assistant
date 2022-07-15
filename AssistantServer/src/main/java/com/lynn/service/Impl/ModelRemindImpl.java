package com.lynn.service.Impl;

import com.lynn.dao.ModelContrastMapper;
import com.lynn.dao.ModelInforMapper;
import com.lynn.dao.ModelRemindMapper;
import com.lynn.pojo.ModelContrast;
import com.lynn.pojo.ModelInfor;
import com.lynn.pojo.ModelRemind;
import com.lynn.service.ModelRemindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelRemindImpl implements ModelRemindService {

    /**
     *创建数据库操作接口对象
     */

    @Autowired
    ModelRemindMapper modelRemindMapper;
    @Autowired
    ModelInforMapper modelInforMapper;

    /**
     * 查找对比机型是否存在
     */
    @Override
    public ModelRemind findModelRemind(int user_id, String phone_model) {
        ModelRemind modelRemind = new ModelRemind();
        modelRemind.setUser_id(user_id);
        modelRemind.setPhone_model(phone_model);
        return modelRemindMapper.findModelRemind(modelRemind);
    }

    /**
     * 查找对比机型(根据id)
     */
    @Override
    public ModelRemind findModelRemindById(int remind_id) {
        return modelRemindMapper.findModelRemindById(remind_id);
    }

    /**
     * 查找用户标记的机型
     */
    @Override
    public List<ModelRemind> findUserRemind(int user_id) {
        List<ModelRemind> ModelRemindList = modelRemindMapper.findUserRemind(user_id);
        for (ModelRemind m:ModelRemindList) {
            int priceNow = modelInforMapper.findModelName(m.getPhone_model()).getInfor_price();
            // 若现价发生变动则更新
            if(priceNow!=m.getPrice_now()) {
                ModelRemind update = new ModelRemind();
                update.setRemind_id(m.getRemind_id());
                update.setPrice_now(priceNow);
                modelRemindMapper.updateModelRemind(update);
            }
        }
        return ModelRemindList;
    }

    /**
     * 添加机型标记
     */
    @Override
    public Boolean newModelRemind(int user_id, String phone_model, int sign_date) {
        ModelRemind modelRemind = new ModelRemind();
        modelRemind.setUser_id(user_id);
        modelRemind.setPhone_model(phone_model);
        modelRemind.setSign_date(sign_date);
        ModelInfor modelInfor = modelInforMapper.findModelName(phone_model);
        int price = modelInfor.getInfor_price();
        modelRemind.setPrice_sign(price);
        modelRemind.setPrice_now(price);
        int result = modelRemindMapper.newModelRemind(modelRemind);
        return result==1;
    }

    /**
     * 删除机型价格标记
     */
    @Override
    public Boolean deleteModelRemind(int user_id, String phone_model) {
        ModelRemind modelRemind = new ModelRemind();
        modelRemind.setUser_id(user_id);
        modelRemind.setPhone_model(phone_model);
        int result = modelRemindMapper.deleteModelRemind(modelRemind);
        return result==1;
    }

    /**
     * 更新机型价格标记
     */
    @Override
    public Boolean updateModelRemind(int remind_id, int sign_date) {
        ModelRemind modelRemind = modelRemindMapper.findModelRemindById(remind_id);
        modelRemind.setSign_date(sign_date);
        modelRemind.setPrice_sign(modelInforMapper.findModelName(modelRemind.getPhone_model()).getInfor_price());
        int result = modelRemindMapper.updateModelRemind(modelRemind);
        return result==1;
    }
}
