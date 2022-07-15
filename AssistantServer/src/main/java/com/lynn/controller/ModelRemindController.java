package com.lynn.controller;

import com.lynn.pojo.ModelRemind;

import com.lynn.service.ModelRemindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ModelRemindController {

    /**
     *创建数据库操作接口对象
     */
    @Autowired
    ModelRemindService modelRemindService;

    /**
     *添加用户机型价格标记
     */
    @RequestMapping(value ="/findModelRemind")
    public String findModelRemind(@RequestParam("user_id") int user_id,
                                   @RequestParam("phone_model") String phone_model) {
        if(modelRemindService.findModelRemind(user_id, phone_model)!=null) {
            return "SUCCESS";
        } else {
            return "ERROR";
        }
    }

    /**
     * 查找用户添加的价格标记
     */
    @RequestMapping(value ="/findUserRemind")
    public List<ModelRemind> findUserRemind(@RequestParam("user_id") int user_id) {
        return modelRemindService.findUserRemind(user_id);
    }



    /**
     *添加用户机型价格标记
     */
    @RequestMapping(value ="/newModelRemind")
    public String newModelRemind(@RequestParam("user_id") int user_id,
                                 @RequestParam("phone_model") String phone_model,
                                 @RequestParam("sign_date") int sign_date) {
        if (modelRemindService.findModelRemind(user_id, phone_model)==null) {
            if(modelRemindService.newModelRemind(user_id, phone_model, sign_date)) {
                return "SUCCESS";
            } else {
                return "ERROR";
            }
        }else {
            return "ERROR";
        }
    }


    /**
     *删除某个已标记的机型
     */
    @RequestMapping(value ="/deleteModelRemind")
    public String deleteModelRemind(@RequestParam("user_id") int user_id,
                                      @RequestParam("phone_model") String phone_model) {
        if(modelRemindService.deleteModelRemind(user_id, phone_model)) {
            return "SUCCESS";
        } else {
            return "ERROR";
        }
    }

    /**
     *更新某个已标记的机型
     */
    @RequestMapping(value ="/updateModelRemind")
    public String updateModelRemind(@RequestParam("remind_id") int remind_id,
                                    @RequestParam("sign_date") int sign_date) {
        if(modelRemindService.updateModelRemind(remind_id, sign_date)) {
            return "SUCCESS";
        } else {
            return "ERROR";
        }
    }
}