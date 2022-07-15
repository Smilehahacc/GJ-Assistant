<template>
  <transition enter-active-class="animated slideInRight">
    <!-- 机型信息详情 -->
    <div id="app">
      <!-- 导航栏 -->
      <van-sticky>
        <van-nav-bar :title="modelDetail.phone_model"
                     left-text="返回"
                     left-arrow
                     style="height: 100%;line-height: 60px"
                     @click-left="onClickLeft()"
                     @click-right="onClickRight()">
          <van-icon name="exchange"
                    size="22"
                    slot="right" />
        </van-nav-bar>
      </van-sticky>

      <!-- 分享面板 -->
      <van-share-sheet v-model="showShare"
                       title="立即分享给好友"
                       :options="options"
                       @select="onSelect" />

      <!-- 手机图片查看 -->
      <van-image-preview v-model="isShowImg"
                         :closeable="true"
                         :images="imgUrl">
      </van-image-preview>

      <div class="model-detail-head">
        <div class="model-img"><img :src="require('@/assets/img/mobile/'+modelDetail.phone_model+'.png')"
               :onerror="errorImg"
               @click="openImg(modelDetail.phone_model)">
        </div>
        <div class="model">
          <div class="model-detial">
            <div class="model-detial1">
              <p>处理器：{{ modelDetail.infor_cpu }}</p>
              <p>屏幕：{{ modelDetail.infor_screen_size }}英寸</p>
              <p>{{ modelDetail.infor_screen_material }}&nbsp;{{ modelDetail.infor_resolving_power }}</p>
              <p>电池：{{ modelDetail.infor_battery }}mAh</p>
            </div>
          </div>
        </div>
      </div>
      <!-- 手机特点 -->
      <div class="model-detail-characteristic">
        <div class="detail">
          <div class="detail-title">
            <p>手机特点</p>
          </div>
          <div class="detail-content"
               v-html="modelDetail.infor_main">
          </div>
        </div>
        <div class="detail">
          <div class="detail-title">
            <p>参考价格</p>
          </div>
          <div class="detail-content">
            {{ modelDetail.infor_price }}元起
          </div>
        </div>
        <div class="detail">
          <div class="detail-title">
            <p>跳转查看</p>
          </div>
          <div class="detail-content">
            <van-button icon="shopping-cart-o"
                        size="small"
                        style="margin:-2% 0 0 0"
                        @click="openBuy(modelDetail.infor_buy_link)"
                        color="#ff0033">京东自营</van-button>
          </div>
        </div>
        <div class="detail">
          <div class="detail-title">
            <p>降价提醒</p>
          </div>
          <div class="detail-content">
            <van-button icon="sign"
                        size="small"
                        style="margin:-2% 0 0 0"
                        @click="addRemind(modelDetail.phone_model)"
                        color="linear-gradient(to right, #c165dd, #5c27fe)">{{ isAddRemind?remindTips1:remindTips }}</van-button>
          </div>
        </div>
        <div class="detail">
          <van-button class="contrast-button"
                      :class="isAddContrast?'hover':''"
                      @click="addContrast()">{{ isAddContrast?contrastTips1:contrastTips }}</van-button>
          <van-button class="contrast-button"
                      style="background-color: #19be6b"
                      @click="openContrast()">{{ contrastOpenTips }}</van-button>
        </div>
      </div>

      <!-- 基本信息 -->
      <div class="model-detail-characteristic">
        <div class="detail">
          <div class="detail-title-main">
            <p>概况</p>
          </div>
        </div>
        <div class="detail">
          <div class="detail-title">
            <p>基本信息</p>
          </div>
          <div class="detail-content"
               v-html="modelDetail.infor_basic">
          </div>
        </div>
      </div>

      <!-- 外观特点 -->
      <div class="model-detail-characteristic">
        <div class="detail">
          <div class="detail-title-main">
            <p>外观</p>
          </div>
        </div>
        <div class="detail">
          <div class="detail-title">
            <p>基本</p>
          </div>
          <div class="detail-content">
            {{ modelDetail.infor_screen_size }}英寸&nbsp;{{ modelDetail.infor_resolving_power }}&nbsp;{{ modelDetail.infor_screen_material }}
          </div>
        </div>
        <div class="detail">
          <div class="detail-title">
            <p>屏幕参数</p>
          </div>
          <div class="detail-content"
               v-html="getAppointStr1('触摸屏类型','其他屏幕参数',modelDetail.infor_screen,false)">
          </div>
        </div>
        <div class="detail">
          <div class="detail-title">
            <p>屏幕特性</p>
          </div>
          <div class="detail-content"
               v-html="getAppointStr('屏幕参数',modelDetail.infor_screen,false)">
          </div>
        </div>
        <div class="detail">
          <div class="detail-title">
            <p>尺寸和重量</p>
          </div>
          <div class="detail-content">
            {{ modelDetail.infor_size }}&nbsp;{{ modelDetail.infor_weight }}g
          </div>
        </div>
      </div>

      <!-- 电池信息 -->
      <div class="model-detail-characteristic">
        <div class="detail">
          <div class="detail-title-main">
            <p>电池</p>
          </div>
        </div>
        <div class="detail">
          <div class="detail-title">
            <p>基本信息</p>
          </div>
          <div class="detail-content"
               v-html="getAppointStr1('电池类型','电池充电',modelDetail.infor_hardware,false)">
          </div>
        </div>
        <div class="detail">
          <div class="detail-title">
            <p>充电规格</p>
          </div>
          <div class="detail-content"
               v-html="getAppointStr('电池充电',modelDetail.infor_hardware,false)">
          </div>
        </div>
      </div>

      <!-- 拍照与录像 -->
      <div class="model-detail-characteristic">
        <div class="detail">
          <div class="detail-title-main">
            <p>拍照与录像</p>
          </div>
        </div>
        <div class="detail">
          <div class="detail-title">
            <p>基本信息</p>
          </div>
          <div class="detail-content"
               v-html="getAppointStr1('摄像头总数','视频拍摄', modelDetail.infor_camera, true)">
          </div>
        </div>
        <div class="detail">
          <div class="detail-title">
            <p>视频拍摄</p>
          </div>
          <div class="detail-content"
               v-html="getAppointStr1('视频拍摄','<br/>',modelDetail.infor_camera,false)">
          </div>
        </div>
        <div class="detail">
          <div class="detail-title">
            <p>拍照功能</p>
          </div>
          <div class="detail-content"
               v-html="getAppointStr1('拍照功能','<br/>',modelDetail.infor_camera,false)">
          </div>
        </div>
      </div>

      <!-- 网络与连接 -->
      <div class="model-detail-characteristic">
        <div class="detail">
          <div class="detail-title-main">
            <p>网络与连接</p>
          </div>
        </div>
        <div class="detail">
          <div class="detail-title">
            <p>详情</p>
          </div>
          <div class="detail-content"
               v-html="getAppointStr('SIM卡类型',modelDetail.infor_network,true)">
          </div>
        </div>
      </div>

      <!-- 附件信息 -->
      <div class="model-detail-characteristic">
        <div class="detail">
          <div class="detail-title-main">
            <p>手机附件</p>
          </div>
        </div>
        <div class="detail">
          <div class="detail-title">
            <p>包装清单</p>
          </div>
          <div class="detail-content"
               v-html="modelDetail.infor_enclosure">
          </div>
        </div>
      </div>

      <!-- 其他信息 -->
      <div class="model-detail-characteristic">
        <div class="detail">
          <div class="detail-title-main">
            <p>其他</p>
          </div>
        </div>
        <div class="detail">
          <div class="detail-title">
            <p>功能与服务</p>
          </div>
          <div class="detail-content"
               v-html="modelDetail.infor_service">
          </div>
        </div>

        <div class="detail">
          <div class="detail-title">
            <p>更多</p>
          </div>
          <div class="detail-content">
            <van-button class="other-button"
                        style="background-color: #ff9900"
                        @click="openEvaluate()">用户评价</van-button>
            <van-button class="other-button"
                        style="background-color: red"
                        @click="openNearbyStore()">附近门店</van-button>
          </div>
        </div>
      </div>

      <!-- 说明 -->
      <div class="model-detail-characteristic">
        <div class="detail">
          <p style="margin:4% 0 0 0">*数据来源于网络，若有误差请以实机为准</p>
        </div>
      </div>

    </div>
  </transition>
</template>
<script>
export default {
  name: 'modelDetail',
  data () {
    return {
      showShare: false,
      options: [[
        { name: '微信', icon: 'wechat' },
        { name: '微博', icon: 'weibo' },
        { name: 'QQ', icon: 'qq' }
      ], [{ name: '复制链接', icon: 'link' },
        { name: '分享海报', icon: 'poster' },
        { name: '二维码', icon: 'qrcode' }
      ]],
      isShowImg: false,
      isAddRemind: false,
      isAddContrast: false,
      errorImg: 'this.src="' + require('@/assets/img/mobile/blank.png') + '"',
      imgUrl: [],
      remindTips: '添加价格标记',
      remindTips1: '取消价格标记',
      contrastTips: '加入对比',
      contrastTips1: '取消对比',
      contrastOpenTips: '进入对比',
      modelDetail: []
    }
  },
  mounted () {

  },
  methods: {
    // 返回上一级
    onClickLeft () {
      this.$router.go(-1)
    },
    onClickRight () {
      // this.$toast('分享成功')
      this.showShare = true
    },
    onSelect (option) {
      this.$toast(option.name)
      this.showShare = false
    },
    // 查看手机图片
    openImg (url) {
      this.isShowImg = true
      this.imgUrl = [require('@/assets/img/mobile/' + url + '.png')]
    },
    // 打开购买链接
    openBuy (url) {
      if (url === '' || url === null) {
        this.$toast.fail('暂时无法跳转到购买链接哦～')
      } else {
        this.$router.push({ name: 'BuyDetail', params: { buyLink: url } })
      }
    },
    // 获取机型状态
    getModelState () {
      var userId = this.getCookie('userId')
      if (userId !== '') {
        this.$axios.post('/findModelRemind', {
          user_id: userId,
          phone_model: this.modelDetail.phone_model
        }).then(response => {
          if (response.data === 'SUCCESS') {
            this.isAddRemind = true
          } else {
            this.isAddRemind = false
          }
        }).catch(error => {
          console.log(error)
          this.$toast.fail('检查机型价格标记成功' + error.status + ',' + error.statusText)
        })
        this.$axios.post('/findModelContrast', {
          user_id: userId,
          phone_model: this.modelDetail.phone_model
        }).then(response => {
          if (response.data === 'SUCCESS') {
            this.isAddContrast = true
          } else {
            this.isAddContrast = false
          }
        }).catch(error => {
          console.log(error)
          this.$toast.fail('检查对比机型成功' + error.status + ',' + error.statusText)
        })
      }
      this.isAddRemind = false
      this.isAddContrast = false
    },
    // 添加机型价格标记
    addRemind () {
      var userId = this.getCookie('userId')
      var t = new Date().getTime()
      t = parseInt(t / 1000)
      if (userId === '' || userId.length === 0) {
        this.$toast.fail('还未登陆哦')
      } else {
        // 添加价格标记
        if (!this.isAddRemind) {
          this.$axios.post('/newModelRemind', {
            user_id: userId,
            phone_model: this.modelDetail.phone_model,
            sign_date: t
          }).then(response => {
            if (response.data === 'SUCCESS') {
              this.$toast.success('已添加标记')
              this.isAddRemind = true
            } else {
              this.$toast.fail('添加标记失败')
            }
          }).catch(error => {
            console.log(error)
            this.$toast.fail('添加价格标记失败' + error.status + ',' + error.statusText)
          })
        } else { // 取消价格标记
          this.$axios.post('/deleteModelRemind', {
            user_id: userId,
            phone_model: this.modelDetail.phone_model
          }).then(response => {
            if (response.data === 'SUCCESS') {
              this.$toast.success('已取消标记')
              this.isAddRemind = false
            } else {
              this.$toast.fail('取消标记失败')
            }
          }).catch(error => {
            console.log(error)
            this.$toast.fail('取消价格标记失败' + error.status + ',' + error.statusText)
          })
        }
      }
    },
    // 加入对比
    addContrast () {
      var userId = this.getCookie('userId')
      if (userId === '') {
        this.$toast.fail('还未登陆哦')
      } else {
        // 加入对比
        if (!this.isAddContrast) {
          this.$axios.post('/newModelContrast', {
            user_id: userId,
            phone_model: this.modelDetail.phone_model
          }).then(response => {
            if (response.data === 'SUCCESS') {
              this.$toast.success('已加入对比')
              this.isAddContrast = true
            } else {
              this.$toast.fail('加入对比失败')
            }
          }).catch(error => {
            console.log(error)
            this.$toast.fail('加入对比失败' + error.status + ',' + error.statusText)
          })
        } else { // 取消对比
          this.$axios.post('/deleteModelContrast', {
            user_id: userId,
            phone_model: this.modelDetail.phone_model
          }).then(response => {
            if (response.data === 'SUCCESS') {
              this.$toast.success('取消对比成功')
              this.isAddContrast = false
            } else {
              this.$toast.fail('取消对比失败')
            }
          }).catch(error => {
            console.log(error)
            this.$toast.fail('取消对比失败' + error.status + ',' + error.statusText)
          })
        }
      }
    },
    // 打开对比页面
    openContrast () {
      this.$router.push('/contrast')
    },
    // 打开用户评价
    openEvaluate () {
      this.$router.push({ name: 'EvaluateDetail', params: { modelDetail: this.modelDetail } })
    },
    // 打开附近门店
    openNearbyStore () {
      this.$router.push('/nearbyStore')
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
    // 从某指定字符串开始，截取由其开始到最后的字符串
    getAppointStr (strBegin, strOriginal, isNeed) {
      try {
        var index = strOriginal.indexOf(strBegin)
        if (isNeed) {
          strOriginal = strOriginal.substring(index, strOriginal.length)
        } else {
          strOriginal = strOriginal.substring(index + strBegin.length + 1, strOriginal.length)
        }
        return strOriginal
      } catch (e) {
        return '暂无数据'
      }
    },
    // 从某指定字符串开始，截取由其开始到指定字符串结束的字符串
    getAppointStr1 (strBegin, strEnd, strOriginal, isNeed) {
      try {
        var index = strOriginal.indexOf(strBegin)
        if (index === -1) {
          return '暂无数据'
        }
        var strNew = this.getAppointStr(strBegin, strOriginal, true)
        var index1 = strNew.indexOf(strEnd)
        if (isNeed) {
          strOriginal = strOriginal.substring(index, index + index1)
        } else {
          strOriginal = strOriginal.substring(index + strBegin.length + 1, index + index1)
        }
        return strOriginal
      } catch (e) {
        return '暂无数据'
      }
    }
  },
  // 监听路由变化，实时更新参数
  watch: {
    // 检查参数是否变化，变化则更新
    '$route' (to, from) {
      if (this.$route.params.modelDetail) {
        this.modelDetail = this.$route.params.modelDetail
        this.getModelState()
      }
    }
  },
  created () {
    this.modelDetail = this.$route.params.modelDetail
  }
}

</script>
<style scoped>
/* 每类参数父项 */
.model-detail-head,
.model-detail-characteristic {
  width: 100%;
  min-height: 60px;
  height: auto;
  background-color: white;
  border-bottom: 8px solid#f8f8f9;
  overflow: auto;
}
/* 细分子项参数列表 */
.detail {
  width: 100%;
  height: auto;
  border-bottom: 2px solid#f8f8f9;
  overflow: auto;
  color: #17233d;
  font-size: 16px;
  padding: 0 0 4% 0;
}
/* 子项主标题 */
.detail-title-main {
  font-size: 20px;
  font-weight: bold;
  text-align: left;
  margin: 4% 0 0 6%;
}
/* 子项标题 */
.detail-title {
  float: left;
  width: 24%;
  text-align: left;
  margin: 4% 0 0 6%;
}
/* 子项内容 */
.detail-content {
  float: left;
  width: 64%;
  /* background-color: red; */
  text-align: left;
  margin: 4.5% 6% 0 0;
}
/* 机型图片 */
.model-img {
  float: left;
  width: 48%;
  height: auto;
  margin: 6% 0 0 0;
}

.model-img img {
  float: left;
  width: 100%;
  height: 100%;
}

.model {
  float: left;
  width: 50%;
  min-height: 100px;
  margin: 2% 2% 0 0;
}

.model-detial {
  width: 100%;
  text-align: left;
  font-size: 16px;
  /* color: #515a6e; */
  color: #17233d;
  font-weight: bold;
  margin: 2% 6% 10% 0;
}

.model-detial1 p {
  background-color: #f8f8f9;
  margin: 6% 4% 0 0;
}

/* 对比按钮 */
.contrast-button,
.other-button {
  height: 30px;
  line-height: 30px;
  text-align: center;
  width: 38%;
  font-size: 16px;
  color: white;
  margin: 4% 2% 0 2%;
  border-radius: 6px;
  background-color: #2d8cf0;
}
.contrast-button.hover {
  background-color: #ff9900;
}

/* 其他按钮 */
.other-button {
  width: 44%;
  margin: -2% 2% 0 2%;
}
</style>
