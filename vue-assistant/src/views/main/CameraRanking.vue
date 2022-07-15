<template>
  <transition enter-active-class="animated fadeInRight">
    <div id='app'>
      <!-- 导航栏 -->
      <van-sticky>
        <van-nav-bar title="DXOMARK排行"
                     left-text="返回"
                     left-arrow
                     style="height: 100%;line-height: 60px"
                     @click-left="onClickLeft()"
                     @click-right="onClickRight()">
          <van-icon name="exchange"
                    size="22"
                    slot="right" />
        </van-nav-bar>
        <!-- 子项标题栏 -->
        <div class="remind-title"
             v-if="!isLoadingShow">
          <div style="background-color: #f8f8f9">
            <p class="title-detail">机型名称</p>
            <p class="title-detail1">相机</p>
            <p class="title-detail1">自拍</p>
            <p class="title-detail1">音频</p>
          </div>
        </div>
      </van-sticky>

      <!-- Dxomark列表 -->
      <div class="model-remind">
        <van-pull-refresh v-model="isLoading"
                          success-text="刷新成功"
                          @refresh="onRefresh">
          <transition enter-active-class="animated fadeInRight"
                      leave-active-class="animated flash">
            <!-- Dxomark列表面板-->
            <div>
              <!-- 子项详细参数 -->
              <div class="remind-detail"
                   v-for="(dxomarkScore, index) in dxomarkScore"
                   :key="index">
                <p class="detail">{{ dxomarkScore.phone_model }}</p>
                <p class="detail1">&nbsp;{{ setScore(dxomarkScore.score_camera) }}&nbsp;</p>
                <p class="detail1">&nbsp;{{ setScore(dxomarkScore.score_selfie) }}&nbsp;</p>
                <p class="detail1">&nbsp;{{ setScore(dxomarkScore.score_audio) }}&nbsp;</p>
              </div>

            </div>
          </transition>
        </van-pull-refresh>
      </div>

      <p class="tips">*数据来源于DXOMARK，请以官网信息为准</p>
    </div>
  </transition>
</template>
<script>
export default {
  name: 'cameraRanking',
  components: {
  },
  data () {
    return {
      isLoading: false,
      dxomarkScore: []
    }
  },
  methods: {
    // 返回上一级
    onClickLeft () {
      this.$router.go(-1)
    },
    onClickRight () {
      this.$toast.success('跳转成功')
      window.location.href = 'https://www.dxomark.com/cn/category/smartphone-reviews/'
    },
    onRefresh () {
      this.getAllDxomarkScore()
      this.isLoading = true
      setTimeout(() => {
        this.isLoading = false
      }, 1000)
    },
    // 获取榜单所有分数
    getAllDxomarkScore () {
      this.$axios.post('/getAllDxomarkScore', {
      }).then(data => {
        if (data.data !== null && data.data.length !== 0) {
          this.dxomarkScore = data.data
        }
      }).catch(error => {
        console.log(error)
        this.$toast.fail('获取榜单所有分数失败' + error.status + ',' + error.statusText)
      })
    },
    // 分数格式化
    setScore (val) {
      if (val === 0) {
        return ''
      } else {
        return val
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
    }
  },
  mounted () {
    this.getAllDxomarkScore()
  }
}
</script>
<style scoped>
.remind-title,
.remind-detail {
  width: 100%;
  min-height: 60px;
  height: auto;
  text-align: center;
  color: #17233d;
  font-size: 16px;
  background-color: #ffffff;
  margin: 0 0 10px 0;
}
.title-detail {
  float: left;
  width: 36%;
  line-height: 60px;
  padding: 0 2% 0 2%;
}
.title-detail1 {
  float: left;
  line-height: 60px;
  width: 20%;
}
.detail {
  float: left;
  width: 36%;
  padding: 0 2% 0 2%;
}
.detail1 {
  float: left;
  width: 20%;
}
.remind-detail {
  color: #515a6e;
  background-color: white;
  border-bottom: 2px solid#f8f8f9;
}
.tips {
  float: left;
  height: 40px;
  color: #c0c4cc;
  margin: 20px 0 0 8%;
  font-size: 16px;
}
</style>
