package com.lynn.dao;

import com.lynn.pojo.AssistantUser;

import java.util.List;

public interface AssistantUserMapper {

    /**
     * 查找所有用户
     * @return Result<List>
     */
    List<AssistantUser> findAllUser();

    /**
     * 通过用户id来注销用户
     * @param assistantUser 用户的对象
     * @return Result<int>
     */
    int newUser(AssistantUser assistantUser);

    /**
     * 通过用户id来注销用户
     * @param user_phone 用户的手机
     * @return Result<int>
     */
    int deleteUser(String user_phone);

    /**
     * 通过用户id来解封用户
     * @param user_phone 用户的手机
     * @return Result<int>
     */
    int cancelDeleteUser(String user_phone);

    /**
     * 通过用户id，用户名或者用户电话来查找用户
     * @param assistantUser 用户信息对象
     * @return Result<UserInfor>
     */
     AssistantUser findUser(AssistantUser assistantUser);

    /**
     * 通过用户id，用户名或者用户电话来模糊查找用户
     * @param assistantUser 用户信息对象
     * @return Result<UserInfor>
     */
    List<AssistantUser> findUserVague(AssistantUser assistantUser);

    /**
     * 传入对象，实现用户信息的更新
     * @param assistantUser 用户信息对象
     * @return Result<int>
     */
     int updateUser(AssistantUser assistantUser);

    /**
     * 查找管理员
     * @return Result<List>
     */
     AssistantUser findAdmin(String user_name);

}
