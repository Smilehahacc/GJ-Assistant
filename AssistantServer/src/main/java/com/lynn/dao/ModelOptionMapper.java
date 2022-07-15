package com.lynn.dao;

import com.lynn.pojo.ModelOption;
import com.lynn.pojo.OptionTop;

import java.util.List;
import java.util.Map;

public interface ModelOptionMapper {

    /**
     * 新建用户选择
     * @return Result<List>
     */
    int newModelOption(ModelOption modelOption);

    /**
     * 获取用户最喜欢的品牌
     * @return Result<List>
     */
    List<OptionTop> getTopBrand();


    /**
     * 获取用户最喜欢的cpu
     * @return Result<List>
     */
    List<OptionTop> getTopCpu();

    /**
     * 获取用户最喜欢的机型特征
     * @return Result<Map>
     */
    Map<String, Object> getTopCharacter();

    /**
     * 获取用户最喜欢的机型相机特征
     * @return Result<Map>
     */
    Map<String, Object> getTopCamera();
}
