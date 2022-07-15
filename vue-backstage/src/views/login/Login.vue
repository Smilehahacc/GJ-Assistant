<template>
  <div id='main'>
    <div id='inputMain'>
      <div class='login-main'
           v-bind:style='{display: displayLogin}'>
        <!-- <p class='title'>{{ appTitle }}</p> -->
        <img :src="require('@/assets/img/logo_title.png')"
             class="title-logo">
        <p class='tilte-detail'>{{ loginTitle }}</p>
        <div id='inputDetail'>
          <input type="text"
                 v-model="userName"
                 class='input-style'
                 placeholder="请输入管理员账号" /><br />
          <input type="password"
                 v-model="password"
                 class='input-style'
                 placeholder="密码" /><br />
          <Button type="primary"
                  @click='login'
                  class='button-log'>登录</Button>
        </div>
      </div>
    </div>

    <!-- 页面最底部文字 -->
    <div id='pageBottom'>
      <p>Copyright©2019 Lynn</p>
    </div>
  </div>
</template>
<script>
// 固定写法，参数的赋值
export default {
  name: 'login',
  data () {
    return {
      userInfor: [],
      appTitle: '购机助手后台管理',
      loginTitle: '管理员登录',
      time: 0,
      choiceFlag: true,
      displayLogin: '',
      displayRegister: 'none',
      disabled: false,
      userName: '',
      password: '',
      isLogin: '',
      userId: ''
    }
  },
  // 一些页面交互相关方法
  methods: {
    // 登录请求发送
    login () {
      if (this.username === '') {
        this.$message.warning('请输入用户名或手机号！')
      } else if (this.password === '') {
        this.$message.warning('请输入密码！')
      } else {
        this.$axios.post('/loginAdmin', {
          name: this.userName,
          password: this.password
        }).then(response => {
          if (response.data === 'SUCCESS') {
            this.$message.success('登录成功')
            this.setCookie('isLogin', true, 7)
            this.setCookie('userName', this.userName, 7)
            this.getUser()
            setTimeout(() => {
              this.$router.push('/home')
            }, 500)
          } else {
            this.$message.error('登录失败！请检查输入的帐号和密码')
          }
        }).catch(error => {
          console.log(error)
          this.$message.error('登录请求失败' + error.status + ',' + error.statusText)
        })
      }
    },
    // 登录后获取用户信息
    getUser () {
      var userName = this.getCookie('userName')
      this.$axios.post('/findUserByName', {
        name: userName
      }).then(data => {
        if (data.data !== null) {
          this.setCookie('userId', data.data.user_id + '', 7)
          this.setCookie('userPhone', data.data.user_phone + '', 7)
          this.setCookie('userPortrait', data.data.user_portrait + '', 7)
          this.setCookie('isLogin', 'true', 7)
        } else {
          this.$message.error('获取用户信息失败')
        }
      }).catch(error => {
        console.log(error)
        this.$message.error('获取用户信息请求失败！' + error.status + ',' + error.statusText)
      })
    },
    // 获取登录信息
    getLoginState () {
      var isLogin = this.getCookie('isLogin')
      if (isLogin !== false) {
        // this.$router.push('/home')
      }
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
    }
  },
  // 生命周期函数，打开页面后自动运行
  created () {
    this.getLoginState()
  }
}
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
}

#main {
  position: absolute;
  margin: 0;
  padding: 0;
  height: 100%;
  width: 100%;
  background-image: url("../../assets/img/back.png");
  background-size: 100% 100%;
}

#inputMain {
  position: relative;
  height: 400px;
  width: 435px;
  background-color: rgb(255, 255, 255);
  align-content: center;
  margin: 0 auto;
  margin-top: 100px;
  border: 1px #dcdee2 solid;
}

#inputDetail {
  position: relative;
  width: 350px;
  height: 260px;
  margin: 0 auto;
}

.login-main {
  position: relative;
  height: 440px;
}

.title {
  padding-top: 20px;
  color: #2d8cf0;
  font-weight: bold;
  font-size: 56px;
}

.title-logo {
  height: auto;
  width: 360px;
  padding: 30px 0 10px 0;
  color: #2d8cf0;
  font-weight: bold;
  font-size: 56px;
}

.tilte-detail {
  font-size: 26px;
  font-weight: bold;
  color: #2d8cf0;
  letter-spacing: -4px;
}

.input-style {
  margin-top: 20px;
  font-size: 16px;
  height: 50px;
  width: 100%;
  border-bottom: #dcdee2 1px solid;
  border-left-width: 0px;
  border-right-width: 0px;
  border-top-width: 0px;
  /* border:0; */
  outline: none;
}

.button-log {
  width: 100%;
  height: 40px;
  margin-top: 40px;
  font-size: 16px;
  color: white;
  background-color: #2d8cf0;
  border: none;
}

#pageBottom {
  margin: 20px 0 0 0;
  color: white;
}
</style>
