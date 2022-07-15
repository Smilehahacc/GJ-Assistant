package com.lynn.controller;

import com.lynn.pojo.ModelContrast;
import com.lynn.pojo.ModelInfor;
import com.lynn.service.ModelContrastService;
import com.lynn.service.ModelInforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ModelContrastController {

    /**
     *创建数据库操作接口对象
     */
    @Autowired
    ModelContrastService modelContrastService;
    @Autowired
    ModelInforService modelInforService;

    /**
     *添加用户对比机型
     */
    @RequestMapping(value ="/findModelContrast")
    public String findModelContrast(@RequestParam("user_id") int user_id,
                                   @RequestParam("phone_model") String phone_model) {
        if(modelContrastService.findModelContrast(user_id, phone_model)!=null) {
            return "SUCCESS";
        } else {
            return "ERROR";
        }
    }

//    /**
//     * 查找用户添加的对比机型
//     */
//    @RequestMapping(value ="/findUserContrast")
//    public List<ModelContrast> findUserContrast(@RequestParam("user_id") int user_id) {
//        return modelContrastService.findUserContrast(user_id);
//
//    }

    /**
     * 查找用户添加的对比机型
     */
    @RequestMapping(value ="/findUserContrast")
    public List<ModelInfor> findUserContrast(@RequestParam("user_id") int user_id) {
        List<ModelInfor> list  = new ArrayList<ModelInfor>();
        List<ModelContrast> mcList = modelContrastService.findUserContrast(user_id);
        for (ModelContrast mc:mcList) {
            ModelInfor m = new ModelInfor();
            m = modelInforService.findModelName(mc.getPhone_model());
            list.add(m);
        }
        return list;
    }



    /**
     *添加用户对比机型
     */
    @RequestMapping(value ="/newModelContrast")
    public String newModelContrast(@RequestParam("user_id") int user_id,
                                    @RequestParam("phone_model") String phone_model) {
        if (modelContrastService.findModelContrast(user_id, phone_model)==null) {
            if(modelContrastService.newModelContrast(user_id, phone_model)) {
                return "SUCCESS";
            } else {
                return "ERROR";
            }
        }else {
            return "ERROR";
        }
    }


    /**
     *删除某个要对比的机型
     */
    @RequestMapping(value ="/deleteModelContrast")
    public String deleteModelContrast(@RequestParam("user_id") int user_id,
                                      @RequestParam("phone_model") String phone_model) {
        if(modelContrastService.deleteModelContrast(user_id, phone_model)) {
            return "SUCCESS";
        } else {
            return "ERROR";
        }
    }
}