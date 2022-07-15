package com.lynn.service.Impl;
import com.lynn.dao.ModelInforMapper;
import com.lynn.dao.ModelOptionMapper;
import com.lynn.pojo.ModelInfor;
import com.lynn.pojo.ModelOption;
import com.lynn.pojo.OptionTop;
import com.lynn.service.ModelInforService;
import com.lynn.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈由注解标记的一个service类〉<br>
 * 〈〉
 *
 * @author 景景
 * @create 2022/2/17
 * @since 1.0.0
 */

@Service
public class ModelInforImpl implements ModelInforService {

    /**
     *创建数据库操作接口对象
     */

    @Autowired
    ModelInforMapper modelInforMapper;
    @Autowired
    ModelOptionMapper modelOptionMapper;

    /**
     * 获取所有机型信息
     */
    @Override
    public List<ModelInfor> findAllModel() {
        return modelInforMapper.findAllModel();
    }

    /**
     * 获取所有机型信息（分页显示）
     */
    @Override
    public Pager<ModelInfor> findModelByPager(int page, int size){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", (page-1)*size);
        params.put("size", size);
        Pager<ModelInfor> pager = new Pager<ModelInfor>();
        List<ModelInfor> list = modelInforMapper.findModelByPager(params);
        pager.setRows(list);
        pager.setTotal(modelInforMapper.count());
        return pager;
    }

    /**
     * 通过名字查找机型
     */
    @Override
    public ModelInfor findModelName(String phone_model) {
        return modelInforMapper.findModelName(phone_model);
    }

    /**
     * 通过机型查找
     */
    @Override
    public List<ModelInfor> findModelByName(String phone_model) {
        return modelInforMapper.findModelByName(phone_model);
    }


    /**
     * 通过cpu查找
     */
    @Override
    public List<ModelInfor> findModelByCpu(String infor_cpu) {
        return modelInforMapper.findModelByCpu(infor_cpu);
    }



    /**
     * 根据用户输入选项获取机型
     */
    @Override
    public List<ModelInfor> findModelByOption(String option_cpu, String option_brand, String option_refresh_rate,
                                       String option_5g, String option_nfc, String option_infrared,
                                       String option_headset, String option_typec, String option_waterproof,
                                       String option_ufs, String option_hifi, String option_ois,
                                       String option_zoom, String option_wide_angle, String option_micro,
                                       String option_tof, String option_hfr, String option_slow,
                                       int option_price_low, int option_price_high, int option_weight_low,
                                       int option_weight_high, int option_battery_low, int option_battery_high,
                                       String option_screen, float option_size_low, float option_size_high,
                                       String option_resolving_power) {

        ModelOption modelOption = new ModelOption();
        modelOption.setOption_cpu(option_cpu);
        modelOption.setOption_brand(option_brand);
        modelOption.setOption_refresh_rate(option_refresh_rate);
        modelOption.setOption_5g(option_5g);
        modelOption.setOption_nfc(option_nfc);
        modelOption.setOption_infrared(option_infrared);
        modelOption.setOption_headset(option_headset);
        modelOption.setOption_typec(option_typec);
        modelOption.setOption_waterproof(option_waterproof);
        modelOption.setOption_ufs(option_ufs);
        modelOption.setOption_hifi(option_hifi);
        modelOption.setOption_ois(option_ois);
        modelOption.setOption_zoom(option_zoom);
        modelOption.setOption_wide_angle(option_wide_angle);
        modelOption.setOption_micro(option_micro);
        modelOption.setOption_tof(option_tof);
        modelOption.setOption_hfr(option_hfr);
        modelOption.setOption_slow(option_slow);
        modelOption.setOption_price_low(option_price_low);
        modelOption.setOption_price_high(option_price_high);
        modelOption.setOption_weight_low(option_weight_low);
        modelOption.setOption_weight_high(option_weight_high);
        modelOption.setOption_battery_low(option_battery_low);
        modelOption.setOption_battery_high(option_battery_high);
        modelOption.setOption_screen(option_screen);
        modelOption.setOption_size_low(option_size_low);
        modelOption.setOption_size_high(option_size_high);
        modelOption.setOption_resolving_power(option_resolving_power);
        int result = modelOptionMapper.newModelOption(modelOption);
        if(result == 1) {
            System.out.println("存入用户偏好选择成功！");
        } else {
            System.out.println("存入用户偏好选择失败！");
        }
        return modelInforMapper.findModelByOption(modelOption);
    }

    /**
     * 获取用户最喜欢的品牌
     */
    @Override
    public List<OptionTop>  getTopBrand() {
        return modelOptionMapper.getTopBrand();
    }

    /**
     * 获取用户最喜欢的cpu
     */
    @Override
    public List<OptionTop>  getTopCpu() {
        return modelOptionMapper.getTopCpu();
    }

    /**
     * 获取用户最喜欢的机型特征
     */
    @Override
    public Map<String, Object> getTopCharacter() {
        return modelOptionMapper.getTopCharacter();
    }

    /**
     * 获取用户最喜欢的机型相机特征
     */
    @Override
    public Map<String, Object> getTopCamera() {
        return modelOptionMapper.getTopCamera();
    }
}