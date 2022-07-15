package com.lynn.service;

import com.lynn.pojo.ModelContrast;
import com.lynn.pojo.ModelInfor;

import java.util.List;

/**
 * 〈实现service类的接口〉<br>
 * 〈〉
 *
 * @author 景景
 * @create 2022/2/23
 * @since 1.0.0
 */
public interface ModelContrastService {

    ModelContrast findModelContrast(int user_id, String phone_model);
    List<ModelContrast>  findUserContrast(int user_id);
    Boolean newModelContrast(int user_id, String phone_model);
    Boolean deleteModelContrast(int user_id, String phone_model);
}