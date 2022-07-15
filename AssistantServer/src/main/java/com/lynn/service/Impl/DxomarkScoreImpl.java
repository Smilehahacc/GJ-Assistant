package com.lynn.service.Impl;

/**
 * 〈由注解标记的一个service类〉<br>
 * 〈〉
 *
 * @author 景景
 * @create 2022/4/16
 * @since 1.0.0
 */

import com.lynn.dao.DxomarkScoreMapper;
import com.lynn.pojo.DxomarkScore;
import com.lynn.service.DxomarkScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DxomarkScoreImpl implements DxomarkScoreService {
    /**
     *创建数据库操作接口对象
     */

    @Autowired
    DxomarkScoreMapper dxomarkScoreMapper;

    /**
     * 获取所有分数
     */
    @Override
    public List<DxomarkScore> getAllDxomarkScore() {
        return dxomarkScoreMapper.getAllDxomarkScore();
    }

    /**
     * 按机型查询
     */
    @Override
    public List<DxomarkScore> findDxomarkScoroByModel(String phone_model) {
        return dxomarkScoreMapper.findDxomarkScoroByModel(phone_model);
    }

}