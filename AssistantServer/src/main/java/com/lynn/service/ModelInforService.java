package com.lynn.service;

import com.lynn.pojo.ModelInfor;
import com.lynn.pojo.OptionTop;
import com.lynn.util.Pager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈实现service类的接口〉<br>
 * 〈〉
 *
 * @author 景景
 * @create 2022/2/23
 * @since 1.0.0
 */
public interface ModelInforService {

    List<ModelInfor> findAllModel();

    Pager<ModelInfor> findModelByPager(int page,int size);

    ModelInfor findModelName(String phone_model);

    List<ModelInfor> findModelByName(String phone_model);

    List<ModelInfor> findModelByCpu(String infor_cpu);

    List<ModelInfor> findModelByOption(String option_cpu, String option_brand, String option_refresh_rate,
                                       String option_5g, String option_nfc, String option_infrared,
                                       String option_headset, String option_typec, String option_waterproof,
                                       String option_ufs, String option_hifi, String option_ois,
                                       String option_zoom, String option_wide_angle, String option_micro,
                                       String option_tof, String option_hfr, String option_slow,
                                       int option_price_low, int option_price_high, int option_weight_low,
                                       int option_weight_high, int option_battery_low, int option_battery_high,
                                       String option_screen, float option_size_low, float option_size_high,
                                       String option_resolving_power);

    List<OptionTop>  getTopBrand();
    List<OptionTop>  getTopCpu();
    Map<String, Object> getTopCharacter();
    Map<String, Object> getTopCamera();

}