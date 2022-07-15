package com.lynn.controller;

import com.lynn.pojo.AssistantUser;
import com.lynn.service.AssistantUserService;
import com.lynn.util.ZhenziSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
public class AssistantUserController {

    /**
     *创建数据库操作接口对象
     */
    @Autowired
    AssistantUserService assistantUserService;

//    public void setAssistantUserService(AssistantUserService assistantUserService) {
//        this.assistantUserService = assistantUserService;
//    }
//
//    public AssistantUserService getAssistantUserService() {
//        return assistantUserService;
//    }

    /**
     *用户登录
     */
    @RequestMapping(value ="/login")
    public String login(@RequestParam("phone") String phone, @RequestParam("password") String password,
                        HttpServletResponse response) {
        // 判断验证结果
        if(assistantUserService.loginCheck(phone,password)) {
            Cookie cookie=new Cookie("userId",assistantUserService.findUserByPhone(phone).getUser_id()+"");
            response.addCookie(cookie);
            Cookie cookie1=new Cookie("userName",assistantUserService.findUserByPhone(phone).getUser_phone());
            response.addCookie(cookie1);
            return "SUCCESS";
        } else {
            return "ERROR";
        }
    }

    /**
     *接收RequestBody为Json串时的登录请求
     */
    @RequestMapping(value ="/loginJson")
    public String loginJson(@RequestBody Map requestBody){

        // 判断登录验证结果
        if(assistantUserService.loginCheck((String)requestBody.get("name"),(String)requestBody.get("password"))) {
            return "SUCCESS";
        } else {
            return "ERROR";
        }
    }

    /**
     *用户注册（手机验证注册）
     */
    @RequestMapping(value ="/registerByPhone")
    public String registerByPhone(@RequestParam("phone") String phone, @RequestParam("password") String password,
                                  @RequestParam("code") String code, HttpServletRequest request) {

        String codeNow = "";
        // 判断注册验证结果
        if(assistantUserService.phoneCheck(phone)) {
            // 检查cookie是否与输入的验证码相同
            Cookie[] cookies = request.getCookies();
            if(cookies != null && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(phone)) {
                        codeNow = cookie.getValue();
                        System.out.println("当前cookie中的code为："+ codeNow);
                    }
                }
            }else {
                System.out.println("抱歉，cookie为空");
                return "ERROR";
            }
            // 验证码正确，成功注册
            if(codeNow.equals(code)) {
                assistantUserService.registerByPhone(phone,password);
                System.out.println("验证码正确，注册成功！");
                return "SUCCESS";
            }
        }
        System.out.println("账号已存在，注册失败！");
        return "ERROR";
    }

    /**
     *发送短信
     */
    @RequestMapping(value ="/sendSms")
    public String sendSms(@RequestParam("phone") String phone, HttpServletResponse response) throws Exception {

        // 设置验证码有效时间
        int min =5;
        // 手机未被注册，则调用接口发送短信，并创建生命期为min分钟的cookie
        if(assistantUserService.phoneCheck(phone)) {
            response.setHeader("Access-Control-Allow-Origin","*");
            String code = ZhenziSms.getInstance().sendSMS(phone);
            Cookie cookie=new Cookie(phone,code);
            cookie.setMaxAge(60*min);
            response.addCookie(cookie);
            System.out.println("注册验证码已发送，为："+code);
            return "SUCCESS";
        }
        return "ERROR";
    }

    /**
     *通过用户名来获取用户所有信息
     */
    @RequestMapping(value ="/findUserByName")
    public AssistantUser findUserByName(@RequestParam("name") String name
    ){
        return assistantUserService.findUserByName(name);
    }

    /**
     *通过用户名来获取用户所有信息
     */
    @RequestMapping(value ="/findUserByPhone")
    public AssistantUser findUserByPhone(@RequestParam("phone") String phone
    ){
        return assistantUserService.findUserByPhone(phone);
    }


    /**
     *通过模糊查询用户名来获取用户所有信息
     */
    @RequestMapping(value ="/findUserByNameVague")
    public List<AssistantUser> findUserByNameVague(@RequestParam("name") String name
    ){
        return assistantUserService.findUserByNameVague(name);
    }

    /**
     *通过模糊查询用户名来获取用户所有信息
     */
    @RequestMapping(value ="/findUserByPhoneVague")
    public List<AssistantUser> findUserByPhoneVague(@RequestParam("phone") String phone
    ){
        return assistantUserService.findUserByPhoneVague(phone);
    }

    /**
     *查找所有用户
     */
    @RequestMapping(value ="/findAllUser")
    public List<AssistantUser> findAllUser(){
        return  assistantUserService.findAllUser();
    }

    /**
     *获取用户Id
     */
    @RequestMapping(value ="/getUserIdByCookie")
    public String getUserIdByCookie(HttpServletRequest request) {
        String userId = "1";
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userId")) {
                    userId =  cookie.getValue();
                }
            }
        }
        return userId;
    }

    /**
     *更新用户信息
     */
    @RequestMapping(value ="/updateUser")
    public String updateUser(@RequestParam("userId") String userId, @RequestParam("userName") String userName,
                                  @RequestParam("userPhone") String userPhone, @RequestParam("userPassword") String userPassword,
                                  @RequestParam("userModel") String userModel, @RequestParam("userSex") int userSex,
                                  @RequestParam("userState") int userState,
                                  HttpServletRequest request, HttpServletResponse response) {
        Boolean isSuccess = assistantUserService.updateUser(userId, userName, userPhone, userPassword, userModel, userSex, userState);
        String n ="";
        int min = 60*60*24*7;
        // 成功用户名则更新cookie
        if(isSuccess) {
            Cookie[] cookies = request.getCookies();
            if(cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("userName")) {
                        n =  cookie.getValue();
                    }
                }
            }
            // 名字已被修改，更新cookie
            if(!userName.equals(n)) {
                Cookie cookie=new Cookie(userName,n);
                cookie.setMaxAge(60*min);
                response.addCookie(cookie);
            }
        }
        if(isSuccess) {
            return "SUCCESS";
        }else {
            return "ERROR";
        }
    }


    /**
     *注销用户
     */
    @RequestMapping(value ="/deleteUser")
    public String deleteUser(@RequestParam("userPhone") String userPhone) {
        if(assistantUserService.deleteUser(userPhone)) {
            return "SUCCESS";
        }else {
            return "ERROR";
        }
    }

    /**
     *解封用户
     */
    @RequestMapping(value ="/cancelDeleteUser")
    public String cancelDeleteUser(@RequestParam("userPhone") String userPhone) {
        if(assistantUserService.cancelDeleteUser(userPhone)) {
            return "SUCCESS";
        }else {
            return "ERROR";
        }
    }

    /**
     *管理员登录
     */
    @RequestMapping(value ="/loginAdmin")
    public String loginAdmin(@RequestParam("name") String name, @RequestParam("password") String password) {
        // 判断验证结果
        if(assistantUserService.loginAdminCheck(name,password)) {
            return "SUCCESS";
        } else {
            return "ERROR";
        }
    }
}