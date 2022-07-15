package com.lynn.service;

import com.lynn.pojo.AssistantUser;

import java.util.List;

/**
 * 〈实现service类的接口〉<br>
 * 〈〉
 *
 * @author 景景
 * @create 2022/2/23
 * @since 1.0.0
 */

public interface AssistantUserService {
    Boolean loginCheck(String phone, String password);
    Boolean registerByPhone(String phone, String password);
    Boolean phoneCheck(String phone);
    AssistantUser findUserByName(String name);
    AssistantUser findUserByPhone(String phone);
    List<AssistantUser> findUserByNameVague(String name);
    List<AssistantUser> findUserByPhoneVague(String phone);
    Boolean deleteUser(String userPhone);
    Boolean cancelDeleteUser(String userPhone);
    List<AssistantUser> findAllUser();
    Boolean updateUser(String userId, String userName, String userPhone, String userPassword, String userModel, int userSex, int userState);
    Boolean loginAdminCheck(String name, String password);
}