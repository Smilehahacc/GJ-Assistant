package com.lynn.controller;

import com.lynn.pojo.ModelInfor;
import com.lynn.pojo.OptionTop;
import com.lynn.service.ModelInforService;
import com.lynn.util.HttpClientUtil;
import com.lynn.util.Pager;
import org.apache.http.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class ModelInforController {

    /**
     *创建数据库操作接口对象
     */
    @Autowired
    ModelInforService modelInforService;

    /**
     *获取所有机型信息
     */
    @RequestMapping(value ="/findAllModel")
    public List<ModelInfor> findAllModel() {
        return modelInforService.findAllModel();
    }

    /**
     *获取所有机型信息（分页显示）
     */
    @RequestMapping(value ="/findModelByPager")
    public Pager<ModelInfor> findModelByPager(@RequestParam("page") int page,
                                              @RequestParam("size") int size) {
        return modelInforService.findModelByPager(page, size);
    }

    /**
     *根据名字获取特点机型
     */
    @RequestMapping(value ="/findModelName")
    public ModelInfor findModelName(@RequestParam("phone_model") String phone_model) {
        return modelInforService.findModelName(phone_model);
    }

    /**
     *根据机型获取
     */
    @RequestMapping(value ="/findModelByName")
    public List<ModelInfor> findModelByName(@RequestParam("phone_model") String phone_model) {
        return modelInforService.findModelByName(phone_model);
    }

    /**
     *根据cpu获取
     */
    @RequestMapping(value ="/findModelByCpu")
    public List<ModelInfor> findModelByCpu(@RequestParam("infor_cpu") String infor_cpu) {
        return modelInforService.findModelByCpu(infor_cpu);
    }

    /**
     *根据用户提交筛选条件获取机型
     */
    @RequestMapping(value ="/findModelByOption")
    public List<ModelInfor> findModelByOption(@RequestParam("option_cpu") String option_cpu,
                                              @RequestParam("option_brand") String option_brand,
                                              @RequestParam("option_refresh_rate") String option_refresh_rate,
                                              @RequestParam("option_5g") String option_5g,
                                              @RequestParam("option_nfc") String option_nfc,
                                              @RequestParam("option_infrared") String option_infrared,
                                              @RequestParam("option_headset") String option_headset,
                                              @RequestParam("option_typec") String option_typec,
                                              @RequestParam("option_waterproof") String option_waterproof,
                                              @RequestParam("option_ufs") String option_ufs,
                                              @RequestParam("option_hifi") String option_hifi,
                                              @RequestParam("option_ois") String option_ois,
                                              @RequestParam("option_zoom") String option_zoom,
                                              @RequestParam("option_wide_angle") String option_wide_angle,
                                              @RequestParam("option_micro") String option_micro,
                                              @RequestParam("option_tof") String option_tof,
                                              @RequestParam("option_hfr") String option_hfr,
                                              @RequestParam("option_slow") String option_slow,
                                              @RequestParam("option_price_low") String option_price_low,
                                              @RequestParam("option_price_high") String option_price_high,
                                              @RequestParam("option_weight_low") String option_weight_low,
                                              @RequestParam("option_weight_high") String option_weight_high,
                                              @RequestParam("option_battery_low") String option_battery_low,
                                              @RequestParam("option_battery_high") String option_battery_high,
                                              @RequestParam("option_screen") String option_screen,
                                              @RequestParam("option_size_low") String option_size_low,
                                              @RequestParam("option_size_high") String option_size_high,
                                              @RequestParam("option_resolving_power") String option_resolving_power) {
        int price_low, price_high, weight_low, weight_high, battery_low, battery_high;
        float size_low, size_high;
        try {
            price_low = option_price_low.equals("")?0:Integer.parseInt(option_price_low);
            price_high = option_price_high.equals("")?0:Integer.parseInt(option_price_high);
            weight_low = option_weight_low.equals("")?0:Integer.parseInt(option_weight_low);
            weight_high = option_weight_high.equals("")?0:Integer.parseInt(option_weight_high);
            battery_low = option_battery_low.equals("")?0:Integer.parseInt(option_battery_low);
            battery_high = option_battery_high.equals("")?0:Integer.parseInt(option_battery_high);
            size_low = option_size_low.equals("")?0:Float.parseFloat(option_size_low);
            size_high = option_size_high.equals("")?0:Float.parseFloat(option_size_high);
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
            return null;
        }
        return modelInforService.findModelByOption(option_cpu, option_brand, option_refresh_rate,
                                                    option_5g,  option_nfc,  option_infrared,
                                                    option_headset,  option_typec,  option_waterproof,
                                                    option_ufs,  option_hifi,  option_ois,
                                                    option_zoom,  option_wide_angle,  option_micro,
                                                    option_tof,  option_hfr,  option_slow,
                                                    price_low,  price_high,  weight_low,
                                                    weight_high,  battery_low,  battery_high,
                                                    option_screen,  size_low,  size_high,
                                                    option_resolving_power);
    }

    /**
     *更新机型数据（手动）
     */
    @RequestMapping(value ="/updateModel")
    public String updateModel() {
        String result = HttpClientUtil.getInstance().getHttpResponse("updateModel");
        if(!result.equals("ERROR")) {
            System.out.println("手动更新机型数据成功！");
            return result;
        } else {
            System.out.println("手动更新机型数据失败！");
            return "ERROR";
        }
    }

    /**
     *更新机型数据（自动）
     */
    @RequestMapping(value ="/updateModelAutomatic")
    public String updateModelAutomatic(@RequestParam("interval") int interval) {
        String result = HttpClientUtil.getInstance().getHttpResponse("updateModelAutomatic"+"?interval="+interval);
        if(!result.equals("ERROR")) {
            System.out.println("自动更新机型数据成功！");
            return "SUCCESS";
        } else {
            System.out.println("自动更新机型数据失败！");
            return "ERROR";
        }
    }

    /**
     *获取用户最喜欢的品牌
     */
    @RequestMapping(value ="/getTopBrand")
    public List<OptionTop> getTopBrand() {
        return modelInforService.getTopBrand();
    }

    /**
     *获取用户最喜欢的cpu
     */
    @RequestMapping(value ="/getTopCpu")
    public List<OptionTop>  getTopCpu() {
        return modelInforService.getTopCpu();
    }

    @RequestMapping(value ="/getTopCharacter")
    public Map<String, Object> getTopCharacter() {
        return modelInforService.getTopCharacter();
    }

    @RequestMapping(value ="/getTopCamera")
    public Map<String, Object> getTopCamera() {
        return modelInforService.getTopCamera();
    }
}