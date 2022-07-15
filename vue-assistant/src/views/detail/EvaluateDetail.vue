<template>
  <transition enter-active-class="animated fadeInLeft">
    <!-- 用户评价详情 -->
    <div id='app'>
      <van-sticky>
        <van-nav-bar :title="modelDetail.phone_model"
                     left-text="返回"
                     left-arrow
                     style="height: 100%;line-height: 60px"
                     @click-left="onClickLeft()"
                     @click-right="onClickRight()">
          <van-icon name="star-o"
                    size="22"
                    slot="right" />
        </van-nav-bar>
      </van-sticky>

      <!-- 用户评价图表 -->
      <div class="evaluate-head">
        <van-circle v-model="count"
                    v-for="(modelScore, index) in modelScore"
                    :key="index"
                    :rate="rateTransform(modelScore.score_num)"
                    :stroke-width="60"
                    size="100px"
                    :text="modelScore.score_name+' '+modelScore.score_num" />
      </div>

      <van-pull-refresh v-model="isLoading"
                        success-text="刷新成功"
                        @refresh="onRefresh">
        <!-- 用户评价列表 -->
        <div class="evaluate-panel"
             v-for="(evaluateList, index) in evaluateList"
             :key="index">
          <div class="evaluate-top">
            <div class="portrait-img">
              <van-image round
                         width="40px"
                         height="40px"
                         :src="require('@/assets/img/'+evaluateList.user_portrait)" />
            </div>
            <p style="font-size: 20px;font-weight: blod;margin:0 0 10px 0;">{{ evaluateList.user_name }}</p>
            <p>{{ formartDate(evaluateList.evaluate_date) }}</p>
          </div>
          <div class="evaluate-bottom">
            <p>{{ evaluateList.evaluate_content }}</p>
            <p style="font-size: 14px;float:right;margin:0 4px 0 0;">第{{ index+1 }}楼</p>
          </div>
        </div>

        <div style="font-size: 16px;height: 40px;line-height: 40px">
          <p>暂无更多</p>
        </div>
      </van-pull-refresh>

      <!-- 回复面板 -->
      <div class="reply-panel">
        <van-field v-model="evaluateText"
                   center
                   clearable
                   placeholder="写下你的评价吧～">
          <van-button slot="button"
                      size="small"
                      @click="addEvaluate()"
                      type="primary">发布</van-button>
        </van-field>
      </div>
    </div>
  </transition>
</template>
<script>
export default {
  name: 'evaluateDetail',
  components: {
  },
  data () {
    return {
      isLoading: false,
      count: 10,
      evaluateText: '',
      userId: '',
      userName: '',
      userPhone: '',
      modelDetail: [],
      modelScore: [{
        score_name: '外观',
        score_num: 7.9
      }, {
        score_name: '相机',
        score_num: 8.6
      }, {
        score_name: '续航',
        score_num: 7.5
      }, {
        score_name: '性能',
        score_num: 9.9
      }, {
        score_name: '屏幕',
        score_num: 8.8
      }],
      evaluateList: []
    }
  },
  methods: {
    // 刷新列表
    onRefresh () {
      setTimeout(() => {
        this.isLoading = false
        this.isShow = false
        this.getEvaluate()
        setTimeout(() => {
          this.isShow = true
        }, 200)
      }, 1000)
    },
    // 返回上一级页面
    onClickLeft () {
      this.$router.go(-1)
    },
    // 跳转到查询页面
    onClickRight () {
      this.$toast.success('右侧按钮点击')
    },
    // 添加评论
    addEvaluate () {
      var t = new Date().getTime()
      t = parseInt(t / 1000)
      if (!this.isLogin) {
        this.$toast.fail('还未登录哦')
      } else {
        this.$axios.post('/newEvaluate', {
          user_id: this.userId,
          phone_id: this.modelDetail.phone_id,
          evaluate_content: this.evaluateText,
          evaluate_date: t
        }).then(response => {
          if (response.data === 'SUCCESS') {
            this.$toast.success('发布评价成功')
            this.evaluateText = ''
            this.getEvaluate()
          } else {
            this.$toast.fail('机型评论失败')
          }
        }).catch(error => {
          console.log(error)
          this.$toast.fail('机型评论请求失败' + error.status + ',' + error.statusText)
        })
      }
    },
    // 获取用户评价
    getEvaluate () {
      this.$axios.post('/getEvaluate', {
        phone_id: this.modelDetail.phone_id
      }).then(data => {
        if (data.data !== null) {
          this.evaluateList = data.data
        }
      }).catch(error => {
        console.log(error)
        this.$toast.fail('获取评价请求失败' + error.status + ',' + error.statusText)
      })
    },
    // 获取登录信息
    getLoginState () {
      var isLogin = this.getCookie('isLogin')
      if (isLogin === 'true') {
        this.isLogin = true
        this.userId = this.getCookie('userId')
        this.userName = this.getCookie('userName')
        this.userPhone = this.getCookie('userPhone')
      } else {
        this.isLogin = false
      }
    },
    // 时间戳转换
    formartDate (param) {
      var time = new Date(param * 1000)
      var y = time.getFullYear()
      var m = time.getMonth() + 1
      var d = time.getDate()
      var h = time.getHours()
      var mm = time.getMinutes()
      var s = time.getSeconds()
      return y + '-' + this.add0(m) + '-' + this.add0(d) + ' ' + this.add0(h) + ':' + this.add0(mm) + ':' + this.add0(s)
    },
    add0 (m) {
      return m < 10 ? '0' + m : m
    },
    // 数字转化
    rateTransform (num) {
      var rate = num * 10
      return rate
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
  // 监听路由变化，实时更新参数
  watch: {
    // 检查参数是否变化，变化则更新
    '$route' (to, from) {
      if (this.$route.params.modelDetail) {
        this.modelDetail = this.$route.params.modelDetail
        this.getLoginState()
        this.getEvaluate()
      }
    }
  },
  created () {
    this.modelDetail = this.$route.params.modelDetail
  }
}
</script>
<style scoped>
.evaluate-panel {
  float: left;
  height: auto;
  min-height: 100px;
  width: 100%;
  overflow: auto;
  font-size: 16px;
  color: #17233d;
  border-bottom: 2px solid#f8f8f9;
  text-align: left;
}
/* 评论上半部分 */
.evaluate-top {
  float: left;
  width: 66%;
  height: 40px;
  margin: 4% 0 0 4%;
}
/* 用户头像 */
.portrait-img {
  float: left;
  width: 20%;
  height: 100%;
  margin: 2% 0 0 0;
}
/* 评论下半部分 */
.evaluate-bottom {
  float: left;
  width: 80%;
  height: auto;
  margin: 6% 4% 4% 16%;
}
.reply-panel {
  width: 96%;
  padding: 0 4% 0 4%;
  height: 40px;
  border-bottom: 2px solid#f8f8f9;
}

.evaluate-head {
  width: 100%;
  margin: 0 0 2% 0;
  background-color: #f8f8f9;
}
</style>
