# GJ-Assistant购机助手移动端app（H5项目）
## 一、项目说明
本项目客户端和管理端均使用 `Vue` 框架来实现
项目旨在为用户提供一个手机购买推荐平台，基于HTML5的方案也使其能够适配多种移动设备。项目前台部分为用户提供大量手机参数、购买信息等的展示，同时也提供了降价提醒、多机型横向对比、平台数据统计等实用功能。后台部分还可对平台数据进行修改、更新等，并对用户资料进行管理。

## 二、项目预览


## 三、项目功能
### 1、机型相关功能
*  新机推荐、机型详情查看
*  机型查询、机型对比
*  手机资讯、附近门店
*  手机拍照排行
### 2、用户相关功能
*  用户登录注册
*  降价提醒、站内热点
*  问题反馈
### 3、后台相关功能
*  数据管理（机型/资讯数据查看、修改、更新）
*  用户管理（用户禁言、评论管理）
*  站内统计（统计用户查询偏好）
*  用户反馈（问题反馈查看与管理）

## 四、技术栈
### 1、后端
SSM + Maven + MySQL
### 2、前端
Vue2.0 + Vant2 + Element UI + Vue-Router + Vuex + Axios + Echarts + Animate.css
### 3、总结
1）数据源：使用Python进行爬取，并存储在MySQL数据库中。  
2）后端：采用SSM框架进行搭建，通过Maven对项目依赖进行管理，整个项目使用Github进行版本管理。  
3）前端：前台部分使用基于Vue.js的Vant移动端框架，后台使用Element UI框架，实现前后端分离。

## 五、开发环境
操作系统：Windows 11  
  
JDK： jdk-1.8.0_181 
  
MySQL：mysql-8.0.28（或更高版本）
  
Nodejs：v16.15.0  
  
Tomcat：Tomcat 9.0.37  
  
IDE：IntelliJ IDEA 2022 / VS Code  

## 六、项目结构
### 1、后端（AssistantServer）  
SSM项目，导入IDEA配置好Tomcat服务器即可运行。包含项目运行所需接口，通过Maven对项目所需依赖包进行管理。
### 2、前台（vue-assistant）   
vue项目，使用VS Code进行编写。采用的前端框架为基于Vue 2.0的Vant2移动端组件库，对Vue 3.0可能存在不兼容问题，在后续安装项目依赖时要注意，详见框架官网：https://youzan.github.io/vant/v2/#/zh-CN/
### 3、后台（vue-backstage）
vue项目，使用VS Code进行编写。采用的前端框架为基于Vue 2.0的Element UI，详见框架官网：https://element.eleme.io/#/zh-CN

## 七、下载运行
### 1、下载项目到本地
```
# 运行下面的代码，将项目克隆到本地仓库
git clone git@github.com:Smilehahacc/GJ-Assistant.git
```
### 2、修改配置文件
1）创建数据库 将 'GJ Assistant\AssistantServer' 文件夹中的 'assistant.sql' 文件导入数据库。

### 3、xxx

### 4、xxx

### 5、xxx


















