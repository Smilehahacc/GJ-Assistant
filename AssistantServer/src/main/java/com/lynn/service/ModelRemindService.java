package com.lynn.service;

import com.lynn.pojo.ModelContrast;
import com.lynn.pojo.ModelRemind;

import java.util.List;

/**
 * 〈实现service类的接口〉<br>
 * 〈〉
 *
 * @author 景景
 * @create 2022/2/23
 * @since 1.0.0
 */
public interface ModelRemindService {

    ModelRemind findModelRemind(int user_id, String phone_model);
    ModelRemind findModelRemindById(int remind_id);
    List<ModelRemind>  findUserRemind(int user_id);
    Boolean newModelRemind(int user_id, String phone_model, int sign_date);
    Boolean deleteModelRemind(int user_id, String phone_model);
    Boolean updateModelRemind(int remind_id, int sign_date);
}