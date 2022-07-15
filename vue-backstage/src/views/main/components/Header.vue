<template>
  <div id='app'>
    <BackTop></BackTop>
    <div class='head'>
      <div style="width: auto"><a class='head-title'
           @click='refresh'>购机助手后台管理</a></div>
      <div style="float: left;margin: 0;"><a class='head-title1'>购机助手，您的专属购机助理</a></div>
      <div class='user-bar'>
        <el-dropdown>
          <a href="javascript:void(0)">
            <el-avatar class='user-portrait'
                       v-bind:src='portraitSrc' />
          </a>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>个人中心</el-dropdown-item>
            <el-dropdown-item>设置</el-dropdown-item>
            <el-dropdown-item><a @click="logout()">退出登录</a></el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
  </div>
</template>

<script>
// 固定写法，参数的赋值
export default {
  name: 'header',
  inject: ['reload'],
  data () {
    return {
      portraitSrc: require('@/assets/img/portrait.png'),
      isLogin: '',
      userId: '',
      userName: '',
      userPhone: '',
      userPortrait: ''
    }
  },
  // 一些页面交互相关方法
  methods: {
    // 退出登录
    logout () {
      this.$message.success('退出成功！')
      this.clearUser()
      setTimeout(() => {
        this.$router.push('/')
      }, 500)
    },
    // 设置cookie
    setCookie (cname, cvalue, exdays) {
      var d = new Date()
      d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000))
      var expires = 'expires=' + d.toUTCString()
      console.info(cname + '=' + cvalue + '; ' + expires)
      document.cookie = cname + '=' + cvalue + '; ' + expires
      console.info(document.cookie)
    },
    // 获取cookie
    getCookie (cname) {
      var name = cname + '='
      var ca = document.cookie.split(';')
      console.log('正在获取cookie...')
      for (var i = 0; i < ca.length; i++) {
        var c = ca[i]
        console.log(c)
        while (c.charAt(0) === ' ') c = c.substring(1)
        if (c.indexOf(name) !== -1) {
          return c.substring(name.length, c.length)
        }
      }
      return ''
    },
    // 获取登录信息
    getUser () {
      if (this.getCookie('isLogin') === true) {
        this.isLogin = true
        this.userId = this.getCookie('userId')
        this.userName = this.getCookie('userName')
        this.userPhone = this.getCookie('userPhone')
        this.userPortrait = this.getCookie('userPortrait')
      }
    },
    // 清除登录信息
    clearUser () {
      this.setCookie('isLogin', false, 7)
      this.setCookie('userId', '', 7)
      this.setCookie('userName', '', 7)
      this.setCookie('userPhone', '', 7)
      this.setCookie('userPortrait', '', 7)
      this.isLogin = false
    }
  },
  // 生命周期函数，打开页面后自动运行
  created () {
    this.getUser()
  }
}
</script>

<style scoped>
#app {
  position: relative;
  width: 100%;
  height: 70px;
  background: #ffffff;
  border-bottom: #dcdee2 1px solid;
}

.menu {
  text-decoration: none;
  color: #333;
  width: 100%;
  height: 100%;
}

.head {
  width: 100%;
  height: 100%;
  margin: 0 auto;
}

.head-title {
  position: relative;
  float: left;
  height: 100%;
  width: 300px;
  line-height: 70px;
  text-align: center;
  font-size: 32px;
  color: #2d8cf0;
  margin-left: 20px;
}

.head-title1 {
  height: 100%;
  width: 100px;
  line-height: 70px;
  text-align: center;
  font-size: 18px;
  color: black;
}

.user-bar {
  float: right;
  height: 100%;
  width: 180px;
  margin-top: 10px;
}

.head-icon1 {
  position: relative;
  float: left;
  margin-top: 14px;
  width: 60px;
  height: 100%;
}

.head-icon2 {
  position: relative;
  float: left;
  margin-top: 14px;
  width: 60px;
  height: 100%;
}

.user-portrait {
  float: right;
  right: 10px;
  top: -42px;
}

#userInfor,
#userInforModify {
  width: 100%;
  height: 100%;
}

.user-head {
  width: 100%;
  height: 220px;
}

.user-protraitLarge {
  width: 100px;
  height: 100px;
  border-radius: 100%;
  overflow: hidden;
}

.user-head button {
  position: relative;
  float: right;
  top: 68px;
}

.user-infor {
  position: relative;
  width: 100%;
  height: 120px;
}

.infor-name {
  width: 100%;
  font-size: 28px;
  color: #17233d;
}

/* 用户详细信息，粉丝、关注和主题数 */
.infor-detail {
  float: left;
  margin-top: 10px;
  width: 60px;
  color: #515a6e;
  font-size: 14px;
}

/* 用户头像 */
.infor-autograph {
  margin-top: 8px;
  float: left;
  width: 420px;
  font-size: 14px;
}

.user-detail {
  width: 100%;
  height: 300px;
}

.modify-head {
  width: 100%;
  height: 200px;
}

.upload {
  position: relative;
  float: right;
  top: 68px;
}

.modify-title {
  width: 100%;
  font-size: 16px;
  margin-top: 20px;
}

.modify-input {
  width: 150px;
  margin-top: 10px;
}
</style>
