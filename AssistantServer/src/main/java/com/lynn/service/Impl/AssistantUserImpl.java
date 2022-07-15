package com.lynn.service.Impl;

/**
 * 〈由注解标记的一个service类〉<br>
 * 〈〉
 *
 * @author 景景
 * @create 2022/2/17
 * @since 1.0.0
 */

import com.lynn.dao.AssistantUserMapper;
import com.lynn.pojo.AssistantUser;
import com.lynn.service.AssistantUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AssistantUserImpl implements AssistantUserService {
    /**
     *创建数据库操作接口对象
     */
    @Autowired
    AssistantUserMapper assistantUserMapper;

    /**
     * 登录验证，用户密码是否正确
     */
    @Override
    public Boolean loginCheck(String phone, String password) {
        AssistantUser assistantUser = new AssistantUser();
        assistantUser.setUser_phone(phone);
        AssistantUser u = assistantUserMapper.findUser(assistantUser);
        // 判断输入的用户是否存在，密码是否正确
        if(u!=null) {
            if(u.getUser_password().equals(password)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 手机注册
     */
    @Override
    public Boolean registerByPhone(String phone, String password) {
        // 将未知信息用手机号拼接直接实现注册，后续用户再修改信息
        AssistantUser assistantUser = new AssistantUser();
        assistantUser.setUser_name(phone);
        assistantUser.setUser_phone(phone);
        assistantUser.setUser_password(password);
        assistantUser.setUser_portrait("default_portrait.png");
        assistantUserMapper.newUser(assistantUser);
        return true;
    }

    /**
     * 手机验证（是否已经注册）
     */
    @Override
    public Boolean phoneCheck(String phone) {
        AssistantUser assistantUser = new AssistantUser();
        assistantUser.setUser_phone(phone);
        if(assistantUserMapper.findUser(assistantUser)!=null) {
            return false;
        }
        return true;
    }

    /**
     * 通过用户名来获取用户所有信息
     */
    @Override
    public AssistantUser findUserByName(String name) {
        System.out.println("通过用户名来获取用户所有信息！");
        AssistantUser assistantUser = new AssistantUser();
        assistantUser.setUser_name(name);
        return assistantUserMapper.findUser(assistantUser);
    }

    /**
     * 通过用户手机来获取用户所有信息
     */
    @Override
    public AssistantUser findUserByPhone(String phone) {
        System.out.println("通过用户手机来获取用户所有信息！");
        AssistantUser assistantUser = new AssistantUser();
        assistantUser.setUser_phone(phone);
        return assistantUserMapper.findUser(assistantUser);
    }

    /**
     * 通过模糊查询用户名来获取用户所有信息
     */
    @Override
    public List<AssistantUser> findUserByNameVague(String name) {
        System.out.println("通过模糊查询用户名来获取用户所有信息！");
        AssistantUser assistantUser = new AssistantUser();
        assistantUser.setUser_name(name);
        return assistantUserMapper.findUserVague(assistantUser);
    }

    /**
     * 通过模糊查询用户手机来获取用户所有信息
     */
    @Override
    public List<AssistantUser> findUserByPhoneVague(String phone) {
        System.out.println("通过模糊查询用户手机来获取用户所有信息！");
        AssistantUser assistantUser = new AssistantUser();
        assistantUser.setUser_phone(phone);
        return assistantUserMapper.findUserVague(assistantUser);
    }

    /**
     * 注销用户
     */
    @Override
    public Boolean deleteUser(String userPhone) {
        System.out.println("注销用户执行！");
        int result = assistantUserMapper.deleteUser(userPhone);
        return result==1;
    }

    /**
     * 解封用户
     */
    @Override
    public Boolean cancelDeleteUser(String userPhone) {
        System.out.println("解封用户！");
        int result = assistantUserMapper.cancelDeleteUser(userPhone);
        return result==1;
    }

    /**
     * 查找所有的用户信息
     */
    @Override
    public List<AssistantUser> findAllUser() {
        System.out.println("查询所有执行！");
        List<AssistantUser> u = assistantUserMapper.findAllUser();
        return u;
    }

    /**
     * 修改用户信息
     */
    @Override
    public Boolean updateUser(String userId, String userName, String userPhone, String userPassword,
                                   String userModel, int userSex, int userState) {
        int id = Integer.parseInt(userId);
        AssistantUser assistantUser = new AssistantUser();
        assistantUser.setUser_id(id);
        assistantUser.setUser_name(userName);
        assistantUser.setUser_phone(userPhone);
        assistantUser.setUser_password(userPassword);
        assistantUser.setUser_model(userModel);
        assistantUser.setUser_sex(userSex);
        assistantUser.setUser_state(userState);
        int isSuccess = assistantUserMapper.updateUser(assistantUser);
        System.out.println("修改用户信息执行！"+isSuccess);
        // 更新语句成功
        if(isSuccess == 1) {
            return true;
        }else {
            return false;
        }

    }

    /**
     * 管理员登录验证
     */
    @Override
    public Boolean loginAdminCheck(String name, String password) {
        AssistantUser u = assistantUserMapper.findAdmin(name);
        // 判断输入的用户是否存在，密码是否正确
        if(u!=null) {
            if(u.getUser_password().equals(password)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

}