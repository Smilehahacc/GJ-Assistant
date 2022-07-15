<template>
  <div id='app'>
    <!-- 导航栏 -->
    <van-sticky>
      <van-nav-bar :title="title"
                   left-text="返回"
                   left-arrow
                   style="height: 100%;line-height: 60px"
                   @click-left="onClickLeft()"
                   @click-right="onClickRight()">
      </van-nav-bar>
    </van-sticky>

    <div v-if="!isLogin">
      <!-- 登录面板 -->
      <transition enter-active-class="animated fadeInRight">
        <div class="login-panel"
             v-if="loginChoice">
          <!-- 输入手机号，调起手机号键盘 -->
          <van-field v-model="userPhone"
                     label="手机号"
                     placeholder="请输入手机号" />
          <!-- 输入密码 -->
          <van-field v-model="userPassword"
                     type="password"
                     label="密码" />
          <div class="transform-button">
            <van-button type="primary"
                        style="float:right;margin: 3% 4% 0 0"
                        @click="transform()"
                        size="mini">{{ transformText }}</van-button>
          </div>
          <van-button slot="button"
                      class="login-button"
                      @click="login()">登录账号</van-button>
        </div>
      </transition>
      <!-- 注册面板 -->
      <transition enter-active-class="animated fadeInRight">
        <div class="register-panel"
             v-if="!loginChoice">
          <van-field v-model="phoneText"
                     label="手机号"
                     placeholder="请输入手机号" />
          <van-field v-model="smsCode"
                     center
                     clearable
                     label="短信验证码"
                     placeholder="请输入短信验证码">
            <van-button slot="button"
                        size="small"
                        @click="sendSms()"
                        :disabled="disabled"
                        type="primary">{{btntxt}}</van-button>
          </van-field>
          <!-- 输入密码 -->
          <van-field v-model="passwordText"
                     type="password"
                     label="设置密码" />
          <div class="transform-button">
            <van-button type="primary"
                        style="float:right;margin: 0 4% 0 0"
                        @click="transform()"
                        size="mini">{{ transformText }}</van-button>
          </div>
          <van-button slot="button"
                      class="register-button"
                      @click="register()">注册账号</van-button>
        </div>
      </transition>
    </div>

    <!-- 用户中心面板 -->
    <transition enter-active-class="animated slideInLeft">
      <div v-if="isLogin"
           class="user-center">
        <div class="user-center-top">
          <div class="portrait-img">
            <van-image round
                       width="40px"
                       height="40px"
                       :src="require('@/assets/img/'+userPortrait)" />
          </div>
          <div class="user-name">
            <p style="font-size: 20px;font-weight: blod;margin:0 0 10px 0;">{{ userName }}</p>
          </div>
        </div>
        <van-button slot="button"
                    class="logout-button"
                    @click="logout()">退出帐号</van-button>
      </div>
    </transition>
  </div>
</template>
<script>
export default {
  name: 'login',
  inject: ['reload'],
  components: {
  },
  data () {
    return {
      title: '',
      disabled: false,
      loginChoice: true,
      isLogin: false,
      userId: '',
      userName: '',
      userPhone: '',
      userPassword: '',
      userPortrait: 'default_portrait.png',
      smsCode: '',
      phoneText: '',
      passwordText: '',
      isCodeRight: false,
      transformText: '注册',
      btntxt: '发送验证码'
    }
  },
  methods: {
    // 返回上一级
    onClickLeft () {
      this.$router.go(-1)
    },
    onClickRight () {
      this.$toast.success('右侧按钮点击')
    },
    // 发送验证码
    sendSms () {
      this.disabled = true
      var reg = 11 && /^((13|14|15|17|18)[0-9]{1}\d{8})$/
      if (this.phoneText === '') {
        this.$toast.fail('请输入手机号码')
      } else if (!reg.test(this.phoneText)) {
        this.$toast.fail('手机格式不正确')
      } else {
        this.$axios.post('/sendSms', {
          phone: this.phoneText
        }).then(response => {
          console.log(response)
          // 通过手机号验证则开始倒计时，若手机号已注册则弹出提示
          if (response.data === 'SUCCESS') {
            this.$toast.success('验证码已发送，请注意查收')
            this.time = 60
            this.timer()
          } else {
            this.disabled = false
            this.$toast.fail('手机号已注册，可直接登录')
          }
        }).catch(error => {
          console.log(error)
          this.disabled = false
          this.$toast.fail('验证码发送失败！' + error.status + ',' + error.statusText)
        })
      }
    },
    timer () {
      if (this.time > 0) {
        this.time--
        this.btntxt = this.time + 's后重新获取'
        setTimeout(this.timer, 1000)
      } else {
        this.time = 0
        this.btntxt = '重新获取短信验证码'
        this.disabled = false
      }
    },
    // 用户登录
    login () {
      var reg = 11 && /^((13|14|15|17|18)[0-9]{1}\d{8})$/
      if (this.userPhone === '') {
        this.$toast.fail('请输入手机号码')
      } else if (!reg.test(this.userPhone)) {
        this.$toast.fail('手机格式不正确')
      } else {
        this.$axios.post('/login', {
          phone: this.userPhone,
          password: this.userPassword
        }).then(response => {
          if (response.data === 'SUCCESS') {
            this.$toast.success('登录成功')
            this.setCookie('userPhone', this.userPhone, 7)
            this.getUser()
            this.userPhone = ''
            this.userPassword = ''
            this.$router.push('/main')
          } else {
            this.$toast('登录失败！请检查输入的帐号和密码')
          }
        }).catch(error => {
          console.log(error)
          this.$toast.fail('登录请求失败' + error.status + ',' + error.statusText)
        })
      }
    },
    // 注册请求处理，验证码发送
    register () {
      if (this.passwordText === '') {
        this.$toast.fail('请输入密码')
      } else if (this.smsCode === '') {
        this.$toast.fail('验证码还未输入哦')
      } else {
        this.$axios.post('/registerByPhone', {
          phone: this.phoneText + '',
          password: this.passwordText + '',
          code: this.smsCode + ''
        }).then(response => {
          if (response.data === 'SUCCESS') {
            this.$toast.success('注册成功！')
            this.loginChoice = true
          } else {
            this.$toast.fail('注册失败，请检查输入的验证码')
          }
        }).catch(error => {
          console.log(error)
          this.$toast.fail('请求失败！' + error.status + ',' + error.statusText)
        })
      }
    },
    // 退出登录
    logout () {
      this.$toast.success('退出成功')
      this.clearUser()
      this.$router.go(-1)
    },
    // 改变面板
    transform () {
      if (this.loginChoice) {
        this.transformText = '登录'
      } else {
        this.transformText = '注册'
      }
      this.loginChoice = !this.loginChoice
    },
    // 登录后获取用户信息
    getUser () {
      var phone = this.getCookie('userPhone')
      this.$axios.post('/findUserByPhone', {
        phone: phone
      }).then(data => {
        if (data.data !== null) {
          this.setCookie('userId', data.data.user_id + '', 7)
          this.setCookie('userName', data.data.user_name + '', 7)
          this.setCookie('isLogin', 'true', 7)
          this.userId = data.data.user_id
          this.userName = data.data.user_name
          this.userPhone = data.data.user_phone
          this.userPortrait = data.data.user_portrait
        } else {
          this.$toast.fail('获取用户信息失败')
        }
      }).catch(error => {
        console.log(error)
        this.$toast.fail('获取用户信息请求失败！' + error.status + ',' + error.statusText)
      })
    },
    // 清除登录信息
    clearUser () {
      this.setCookie('isLogin', '', 7)
      this.setCookie('userId', '', 7)
      this.setCookie('userName', '', 7)
      this.setCookie('userPhone', '', 7)
      this.isLogin = false
    },
    // 获取登录信息
    getLoginState () {
      var isLogin = this.getCookie('isLogin')
      if (isLogin === 'true') {
        this.isLogin = true
        this.title = '用户中心'
        this.getUser()
      } else {
        this.isLogin = false
        this.title = '用户登录'
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
  created () {
    this.getLoginState()
  },
  activated () {
    this.getLoginState()
  }
}
</script>
<style scoped>
/* 按钮 */
.login-button,
.register-button,
.logout-button {
  height: 40px;
  line-height: 40px;
  text-align: center;
  width: 42%;
  font-size: 14px;
  margin: 20px auto;
  border-radius: 6px;
  color: white;
  background-color: #2d8cf0;
}

.logout-button {
  background-color: white;
  color: red;
}
.transform-button {
  float: right;
  width: 100%;
  margin: 0 0 0 0;
}
/* 评论上半部分 */
.user-center-top {
  float: left;
  width: 100%;
  height: 80px;
  margin: 4% 0 4% 0;
}
/* 用户头像 */
.portrait-img,.user-name {
  float: left;
  width: 100%;
  height: 60px;
  margin: 0 auto;
}
/* 评论下半部分 */
.user-center-bottom {
  float: left;
  width: 80%;
  height: auto;
  margin: 6% 4% 4% 16%;
}
</style>
