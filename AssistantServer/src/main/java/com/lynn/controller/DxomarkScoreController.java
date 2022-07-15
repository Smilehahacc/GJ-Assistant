package com.lynn.controller;

import com.lynn.pojo.DxomarkScore;
import com.lynn.service.DxomarkScoreService;
import com.lynn.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class DxomarkScoreController {

    /**
     *创建数据库操作接口对象
     */
    @Autowired
    DxomarkScoreService dxomarkScoreService;

    /**
     *获取所有机型评价
     */
    @RequestMapping(value ="/getAllDxomarkScore")
    public List<DxomarkScore> getAllDxomarkScore(){
        return dxomarkScoreService.getAllDxomarkScore();
    }

    /**
     *删除机型评价
     */
    @RequestMapping(value ="/findDxomarkScoroByModel")
    public List<DxomarkScore> findDxomarkScoroByModel(@RequestParam("phone_model") String phone_model){
        return dxomarkScoreService.findDxomarkScoroByModel(phone_model);
    }

    /**
     *更新DXO榜单数据（手动）
     */
    @RequestMapping(value ="/updateDxomark")
    public String updateDxomark() {
        String result = HttpClientUtil.getInstance().getHttpResponse("updateDxomark");
        if(!result.equals("ERROR")) {
            System.out.println("手动更新DXO榜单数据成功！");
            return result;
        } else {
            System.out.println("手动更新DXO榜单数据失败！");
            return "ERROR";
        }
    }

    /**
     *更新DXO榜单数据（自动）
     */
    @RequestMapping(value ="/updateDxomarkAutomatic")
    public String updateDxomarkAutomatic(@RequestParam("interval") int interval) {
        String result = HttpClientUtil.getInstance().getHttpResponse("updateDxomarkAutomatic"+"?interval="+interval);
        if(!result.equals("ERROR")) {
            System.out.println("自动更新DXO榜单数据成功！");
            return "SUCCESS";
        } else {
            System.out.println("自动更新DXO榜单数据失败！");
            return "ERROR";
        }
    }

}