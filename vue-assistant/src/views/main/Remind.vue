<template>
  <transition enter-active-class="animated slideInRight">
    <div id='app'>
      <!-- 导航栏 -->
      <van-sticky>
        <van-nav-bar title="降价提醒"
                     left-text="返回"
                     left-arrow
                     style="height: 100%;line-height: 60px"
                     @click-left="onClickLeft()"
                     @click-right="onClickRight()">
          <van-icon name="search"
                    size="22"
                    slot="right" />
        </van-nav-bar>
        <!-- 子项标题栏 -->
        <div class="remind-title"
             v-if="!isLoadingShow">
          <div v-if="!isShowTips"
               style="background-color: #f8f8f9">
            <p>标记机型</p>
            <p>标记时间</p>
            <p>标记价格</p>
            <p>实时价格</p>
          </div>
        </div>
      </van-sticky>

      <!-- 加载中 -->
      <div v-if="isLoadingShow"
           style="margin: 20px 0 0 0">
        <van-loading size="24px">加载中...</van-loading>
      </div>

      <!-- 去添加 -->
      <div v-if="isShowTips"
           style="margin: 20px 0 0 0">
        <p style="font-size: 18px;height: 80px;line-height:80px">{{ tipsContent }}</p>
        <van-button color="linear-gradient(to right, #4bb0ff, #6149f6)"
                    icon="add-o"
                    @click="openPage()">{{ tipsContentButton }}</van-button>
      </div>

      <!-- 机型价格标记列表 -->
      <div class="model-remind">
        <van-pull-refresh v-model="isLoading"
                          success-text="刷新成功"
                          @refresh="onRefresh">
          <transition enter-active-class="animated fadeInRight"
                      leave-active-class="animated flash">
            <!-- 机型展示面板-->
            <div v-if="isShow">
              <van-swipe-cell v-for="(modelRemind, index) in modelRemind"
                              :key="index">
                <!-- 子项详细参数 -->
                <div class="remind-detail">
                  <span>{{ modelRemind.phone_model }}</span>
                  <p>{{ formartDate(modelRemind.sign_date) }}</p>
                  <p>{{ modelRemind.price_sign }}</p>
                  <p v-if="isNewPrice(modelRemind.price_sign,modelRemind.price_now)===1?true:false">{{ modelRemind.price_now }}</p>
                  <p v-if="isNewPrice(modelRemind.price_sign,modelRemind.price_now)===2?true:false"
                     style="color: green">{{ modelRemind.price_now }}</p>
                  <p v-if="isNewPrice(modelRemind.price_sign,modelRemind.price_now)===3?true:false"
                     style="color: red">{{ modelRemind.price_now }}</p>
                </div>
                <van-button slot="left"
                            square
                            text="删除"
                            type="danger"
                            @click="deleteConfirm(index)"
                            style="height:60px" />
                <van-button slot="right"
                            square
                            text="更新标记"
                            type="primary"
                            @click="updateModelRemind(index)"
                            style="height:60px" />
              </van-swipe-cell>
            </div>
          </transition>
        </van-pull-refresh>
      </div>
    </div>
  </transition>
</template>
<script>
export default {
  name: 'remind',
  components: {
  },
  data () {
    return {
      isShow: true,
      isShowTips: false,
      isLoading: false,
      isLoadingShow: false,
      tipsContent: '还没有添加价格标记哦',
      tipsContentButton: '去添加',
      modelRemind: []
    }
  },
  methods: {
    // 返回上一级
    onClickLeft () {
      this.$router.go(-1)
    },
    onClickRight () {
      this.$router.push('recommend')
    },
    onRefresh () {
      this.getModelRemind()
      setTimeout(() => {
        this.isLoading = false
        this.isShow = false
        setTimeout(() => {
          this.isShow = true
        }, 200)
      }, 1000)
    },
    // 跳转页面，登陆或去添加页面
    openPage () {
      if (this.tipsContentButton !== '去添加') {
        this.$router.push('/login')
      } else {
        this.$router.push('/recommend')
      }
    },
    // 确认是否删除机型价格标记
    deleteConfirm (index) {
      this.$dialog.confirm({
        message: '确认删除该标记吗？'
      }).then(() => {
        this.deleteModelRemind(index)
      }).catch(() => {
        this.$notify({
          message: '已取消',
          color: '#ffffff',
          background: '#ff0000',
          duration: 900
        })
      })
    },
    // 删除价格标记
    deleteModelRemind (index) {
      var userId = this.getCookie('userId')
      this.isLoadingShow = true
      this.$axios.post('/deleteModelRemind', {
        user_id: userId,
        phone_model: this.modelRemind[index].phone_model
      }).then(response => {
        if (response.data === 'SUCCESS') {
          this.getModelRemind()
          this.showNotify('删除成功', '#19be6b')
        } else {
          this.showNotify('删除失败', '#19be6b')
        }
        this.isLoadingShow = false
      }).catch(error => {
        console.log(error)
        this.isLoadingShow = false
        this.$toast.fail('删除用户的机型价格标记失败' + error.status + ',' + error.statusText)
      })
    },
    // 更新价格标记
    updateModelRemind (index) {
      var t = new Date().getTime()
      t = parseInt(t / 1000)
      this.isLoadingShow = true
      this.$axios.post('/updateModelRemind', {
        remind_id: this.modelRemind[index].remind_id,
        sign_date: t
      }).then(response => {
        if (response.data === 'SUCCESS') {
          this.getModelRemind()
          this.showNotify('已更新', '#19be6b')
        } else {
          this.showNotify('更新失败', '#19be6b')
        }
        this.isLoadingShow = false
      }).catch(error => {
        console.log(error)
        this.isLoadingShow = false
        this.$toast.fail('获取用户的机型价格标记失败' + error.status + ',' + error.statusText)
      })
    },
    // 获取用户添加的对比机型
    getModelRemind () {
      var userId = this.getCookie('userId')
      if (userId === '' || userId.length === 0) {
        this.isShowTips = true
        this.isLoadingShow = false
        this.isShow = false
        this.tipsContent = '还未登陆哦'
        this.tipsContentButton = '去登陆'
      } else {
        this.$axios.post('/findUserRemind', {
          user_id: userId
        }).then(data => {
          if (data.data !== null && data.data.length !== 0) {
            this.modelRemind = data.data
            this.isShowTips = false
          } else {
            this.$toast.fail('还没有添加价格标记哦')
            this.isShowTips = true
          }
          this.isLoadingShow = false
        }).catch(error => {
          console.log(error)
          this.isLoadingShow = false
          this.$toast.fail('获取用户的机型价格标记失败' + error.status + ',' + error.statusText)
        })
      }
    },
    showNotify (message, color) {
      this.$notify({
        message: message,
        color: '#ffffff',
        background: color,
        duration: 900
      })
    },
    // 判断价格是否发生变动
    isNewPrice (value1, value2) {
      if (value1 === value2) {
        return 1
      } else if (value1 < value2) {
        return 2
      } else if (value1 > value2) {
        return 3
      }
    },
    // 时间戳转换
    formartDate (param) {
      var time = new Date(param * 1000)
      var y = time.getFullYear()
      var m = time.getMonth() + 1
      var d = time.getDate()
      return y + '.' + this.add0(m) + '.' + this.add0(d)
    },
    add0 (m) {
      return m < 10 ? '0' + m : m
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
  activated () {
    this.isLoadingShow = true
    this.modelRemind = []
    this.getModelRemind()
  },
  created () {
  }
}
</script>
<style scoped>
.remind-title,
.remind-detail {
  width: 100%;
  height: 60px;
  text-align: center;
  color: #17233d;
  font-size: 16px;
  background-color: #ffffff;
}
.remind-title p,
.remind-detail p {
  float: left;
  width: 25%;
  line-height: 60px;
}

.remind-detail span {
  float: left;
  width: 25%;
  margin: 15px 0 0 0;
}

.remind-detail {
  color: #515a6e;
  background-color: white;
  border-bottom: 2px solid#f8f8f9;
}
</style>
