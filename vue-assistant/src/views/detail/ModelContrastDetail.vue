<template>
  <transition enter-active-class="animated slideInRight">
    <!-- 机型对比详情 -->
    <div id="app">
      <!-- 导航栏 -->
      <van-sticky>
        <van-nav-bar title="对比详情"
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

      <!-- 手机图片查看 -->
      <van-image-preview v-model="isShowImg"
                         :closeable="true"
                         :images="imgUrl">
      </van-image-preview>

      <!-- 机型对比列表 -->
      <div class="model-contrast-head">
        <div class="model-detail-head"
             v-for="(modelDetail, index) in modelListChecked"
             :key="index">
          <div class="model-img"><img :src="require('@/assets/img/mobile/'+modelDetail.phone_model+'.png')"
                 :onerror="errorImg"
                 style="width: 80%;height: auto">
          </div>
          <div class="model-name">
            <p>{{ modelDetail.phone_model }}</p>
          </div>
        </div>
      </div>
      <!-- 具体对比面板，细分为几个子项目 -->
      <van-tabs v-model="active"
                swipeable>
        <van-tab title="主要">
          <!-- 手机特点 -->
          <div class="model-detail-characteristic">
            <div class="detail">
              <div class="detail-title-main">
                <p>手机特点</p>
              </div>
            </div>
            <div class="detail"
                 v-for="(modelDetail, index) in modelListChecked"
                 :key="index">
              <div class="detail-title">
                <p>{{ modelDetail.phone_model }}</p>
              </div>
              <div class="detail-content"
                   v-html="getAppointStr('屏幕类型',modelDetail.infor_basic,false)">
              </div>
            </div>
          </div>

          <!-- 参考价格 -->
          <div class="model-detail-characteristic">
            <div class="detail">
              <div class="detail-title-main">
                <p>参考价格</p>
              </div>
            </div>
            <div class="detail"
                 v-for="(modelDetail, index) in modelListChecked"
                 :key="index">
              <div class="detail-title">
                <p>{{ modelDetail.phone_model }}</p>
              </div>
              <div class="detail-content">
                <p>{{ modelDetail.infor_price }}元起</p>
              </div>
            </div>
          </div>

          <!-- 参考价格 -->
          <div class="model-detail-characteristic">
            <div class="detail">
              <div class="detail-title-main">
                <p>手机详情</p>
              </div>
            </div>
            <div class="detail"
                 v-for="(modelDetail, index) in modelListChecked"
                 :key="index">
              <div class="detail-title">
                <p>{{ modelDetail.phone_model }}</p>
              </div>
              <div class="detail-content">
                <van-button icon="shopping-cart-o"
                            size="small"
                            style="margin:-2% 0 0 0"
                            @click="openBuy(modelDetail.infor_buy_link)"
                            color="#ff0033">京东自营</van-button>
                <van-button icon="photo-o"
                            size="small"
                            style="margin:-2% 0 0 8%"
                            @click="openImg(modelDetail.phone_model)"
                            color="#ff9900">查看图片</van-button>
              </div>
            </div>
          </div>

          <!-- 处理器 -->
          <div class="model-detail-characteristic">
            <div class="detail">
              <div class="detail-title-main">
                <p>处理器</p>
              </div>
            </div>
            <div class="detail"
                 v-for="(modelDetail, index) in modelListChecked"
                 :key="index">
              <div class="detail-title">
                <p>{{ modelDetail.phone_model }}</p>
              </div>
              <div class="detail-content">
                <p>{{ modelDetail.infor_cpu }}</p>
              </div>
            </div>
          </div>

          <!-- 屏幕 -->
          <div class="model-detail-characteristic">
            <div class="detail">
              <div class="detail-title-main">
                <p>屏幕</p>
              </div>
            </div>
            <div class="detail"
                 v-for="(modelDetail, index) in modelListChecked"
                 :key="index">
              <div class="detail-title">
                <p>{{ modelDetail.phone_model }}</p>
              </div>
              <div class="detail-content">
                <p>{{ modelDetail.infor_screen_size}}英寸&nbsp;{{modelDetail.infor_resolving_power}}&nbsp;{{modelDetail.infor_screen_material }}</p>
              </div>
            </div>
          </div>

          <!-- 屏幕特性 -->
          <div class="model-detail-characteristic">
            <div class="detail">
              <div class="detail-title-main">
                <p>屏幕特性</p>
              </div>
            </div>
            <div class="detail"
                 v-for="(modelDetail, index) in modelListChecked"
                 :key="index">
              <div class="detail-title">
                <p>{{ modelDetail.phone_model }}</p>
              </div>
              <div class="detail-content"
                   v-html="getAppointStr('屏幕技术',modelDetail.infor_screen, true)">
              </div>
            </div>
          </div>

          <!-- 尺寸重量 -->
          <div class="model-detail-characteristic">
            <div class="detail">
              <div class="detail-title-main">
                <p>尺寸重量</p>
              </div>
            </div>
            <div class="detail"
                 v-for="(modelDetail, index) in modelListChecked"
                 :key="index">
              <div class="detail-title">
                <p>{{ modelDetail.phone_model }}</p>
              </div>
              <div class="detail-content">
                <p>{{ modelDetail.infor_size}}&nbsp;{{modelDetail.infor_weight}}g</p>
              </div>
            </div>
          </div>

          <!-- 电池容量 -->
          <div class="model-detail-characteristic">
            <div class="detail">
              <div class="detail-title-main">
                <p>电池容量</p>
              </div>
            </div>
            <div class="detail"
                 v-for="(modelDetail, index) in modelListChecked"
                 :key="index">
              <div class="detail-title">
                <p>{{ modelDetail.phone_model }}</p>
              </div>
              <div class="detail-content">
                <p>{{ modelDetail.infor_battery}}mAh</p>
              </div>
            </div>
          </div>

          <!-- 充电规格 -->
          <div class="model-detail-characteristic">
            <div class="detail">
              <div class="detail-title-main">
                <p>充电规格</p>
              </div>
            </div>
            <div class="detail"
                 v-for="(modelDetail, index) in modelListChecked"
                 :key="index">
              <div class="detail-title">
                <p>{{ modelDetail.phone_model }}</p>
              </div>
              <div class="detail-content"
                   v-html="getAppointStr('电池充电',modelDetail.infor_hardware,false)">
              </div>
            </div>
          </div>

          <!-- 系统UI -->
          <div class="model-detail-characteristic">
            <div class="detail">
              <div class="detail-title-main">
                <p>系统UI</p>
              </div>
            </div>
            <div class="detail"
                 v-for="(modelDetail, index) in modelListChecked"
                 :key="index">
              <div class="detail-title">
                <p>{{ modelDetail.phone_model }}</p>
              </div>
              <div class="detail-content">
                <p v-html="getAppointStr1('操作系统','<br/>',modelDetail.infor_basic,false)"></p>
                <p v-html="getAppointStr1('出厂系统内核','<br/>',modelDetail.infor_basic,false)"></p>
              </div>
            </div>
          </div>

          <!-- 发售时间 -->
          <div class="model-detail-characteristic">
            <div class="detail">
              <div class="detail-title-main">
                <p>发售时间</p>
              </div>
            </div>
            <div class="detail"
                 v-for="(modelDetail, index) in modelListChecked"
                 :key="index">
              <div class="detail-title">
                <p>{{ modelDetail.phone_model }}</p>
              </div>
              <div class="detail-content"
                   v-html="getAppointStr1('上市日期','<br/>',modelDetail.infor_basic,false)">
              </div>
            </div>
          </div>

        </van-tab>
        <van-tab title="拍照">
          <!-- 拍摄特点 -->
          <div class="model-detail-characteristic">
            <div class="detail">
              <div class="detail-title-main">
                <p>拍摄特点</p>
              </div>
            </div>
            <div class="detail"
                 v-for="(modelDetail, index) in modelListChecked"
                 :key="index">
              <div class="detail-title">
                <p>{{ modelDetail.phone_model }}</p>
              </div>
              <div class="detail-content">
                <p v-html="getAppointStr1('其他摄像头参数','<br/>',modelDetail.infor_camera,false)"></p>
              </div>
            </div>
          </div>

          <!-- 前置摄像头 -->
          <div class="model-detail-characteristic">
            <div class="detail">
              <div class="detail-title-main">
                <p>前置摄像头</p>
              </div>
            </div>
            <div class="detail"
                 v-for="(modelDetail, index) in modelListChecked"
                 :key="index">
              <div class="detail-title">
                <p>{{ modelDetail.phone_model }}</p>
              </div>
              <div class="detail-content">
                <p v-html="getAppointStr1('前置摄像头','<br/>',modelDetail.infor_camera,false)"></p>
              </div>
            </div>
          </div>

          <!-- 后置摄像头 -->
          <div class="model-detail-characteristic">
            <div class="detail">
              <div class="detail-title-main">
                <p>后置摄像头</p>
              </div>
            </div>
            <div class="detail"
                 v-for="(modelDetail, index) in modelListChecked"
                 :key="index">
              <div class="detail-title">
                <p>{{ modelDetail.phone_model }}</p>
              </div>
              <div class="detail-content">
                <p v-html="getAppointStr1('后置摄像头','<br/>',modelDetail.infor_camera,false)"></p>
              </div>
            </div>
          </div>

          <!-- 拍照功能 -->
          <div class="model-detail-characteristic">
            <div class="detail">
              <div class="detail-title-main">
                <p>拍照功能</p>
              </div>
            </div>
            <div class="detail"
                 v-for="(modelDetail, index) in modelListChecked"
                 :key="index">
              <div class="detail-title">
                <p>{{ modelDetail.phone_model }}</p>
              </div>
              <div class="detail-content">
                <p v-html="getAppointStr1('拍照功能','<br/>',modelDetail.infor_camera,false)"></p>
              </div>
            </div>
          </div>

          <!-- 视频拍摄 -->
          <div class="model-detail-characteristic">
            <div class="detail">
              <div class="detail-title-main">
                <p>视频拍摄</p>
              </div>
            </div>
            <div class="detail"
                 v-for="(modelDetail, index) in modelListChecked"
                 :key="index">
              <div class="detail-title">
                <p>{{ modelDetail.phone_model }}</p>
              </div>
              <div class="detail-content">
                <p v-html="getAppointStr1('视频拍摄','<br/>',modelDetail.infor_camera,false)"></p>
              </div>
            </div>
          </div>

          <!-- 闪光灯 -->
          <div class="model-detail-characteristic">
            <div class="detail">
              <div class="detail-title-main">
                <p>闪光灯</p>
              </div>
            </div>
            <div class="detail"
                 v-for="(modelDetail, index) in modelListChecked"
                 :key="index">
              <div class="detail-title">
                <p>{{ modelDetail.phone_model }}</p>
              </div>
              <div class="detail-content">
                <p v-html="getAppointStr1('闪光灯','<br/>',modelDetail.infor_camera,false)"></p>
              </div>
            </div>
          </div>

        </van-tab>
        <van-tab title="硬件支持">
          <!-- 传感器 -->
          <div class="model-detail-characteristic">
            <div class="detail">
              <div class="detail-title-main">
                <p>传感器</p>
              </div>
            </div>
            <div class="detail"
                 v-for="(modelDetail, index) in modelListChecked"
                 :key="index">
              <div class="detail-title">
                <p>{{ modelDetail.phone_model }}</p>
              </div>
              <div class="detail-content">
                <p v-html="getAppointStr1('感应器类型','<br/>',modelDetail.infor_service,false)"></p>
              </div>
            </div>
          </div>

          <!-- 充电与数据接口 -->
          <div class="model-detail-characteristic">
            <div class="detail">
              <div class="detail-title-main">
                <p>充电与数据接口</p>
              </div>
            </div>
            <div class="detail"
                 v-for="(modelDetail, index) in modelListChecked"
                 :key="index">
              <div class="detail-title">
                <p>{{ modelDetail.phone_model }}</p>
              </div>
              <div class="detail-content">
                <p v-html="getAppointStr1('机身接口','<br/>',modelDetail.infor_network,false)"></p>
              </div>
            </div>
          </div>

          <!-- NFC -->
          <div class="model-detail-characteristic">
            <div class="detail">
              <div class="detail-title-main">
                <p>NFC</p>
              </div>
            </div>
            <div class="detail"
                 v-for="(modelDetail, index) in modelListChecked"
                 :key="index">
              <div class="detail-title">
                <p>{{ modelDetail.phone_model }}</p>
              </div>
              <div class="detail-content">
                <p v-html="getAppointStr1('NFC','<br/>',modelDetail.infor_network,false)"></p>
              </div>
            </div>
          </div>

          <!-- 红外传输 -->
          <div class="model-detail-characteristic">
            <div class="detail">
              <div class="detail-title-main">
                <p>红外传输</p>
              </div>
            </div>
            <div class="detail"
                 v-for="(modelDetail, index) in modelListChecked"
                 :key="index">
              <div class="detail-title">
                <p>{{ modelDetail.phone_model }}</p>
              </div>
              <div class="detail-content">
                <p v-html="checkStr('红外',modelDetail.infor_network)"></p>
              </div>
            </div>
          </div>

          <!-- 定位导航 -->
          <div class="model-detail-characteristic">
            <div class="detail">
              <div class="detail-title-main">
                <p>定位导航</p>
              </div>
            </div>
            <div class="detail"
                 v-for="(modelDetail, index) in modelListChecked"
                 :key="index">
              <div class="detail-title">
                <p>{{ modelDetail.phone_model }}</p>
              </div>
              <div class="detail-content">
                <p v-html="getAppointStr1('导航','<br/>',modelDetail.infor_network,false)"></p>
              </div>
            </div>
          </div>

          <!-- WLAN/WiFi规格 -->
          <div class="model-detail-characteristic">
            <div class="detail">
              <div class="detail-title-main">
                <p>WLAN/WiFi规格</p>
              </div>
            </div>
            <div class="detail"
                 v-for="(modelDetail, index) in modelListChecked"
                 :key="index">
              <div class="detail-title">
                <p>{{ modelDetail.phone_model }}</p>
              </div>
              <div class="detail-content">
                <p v-html="getAppointStr1('WLAN功能','<br/>',modelDetail.infor_network,false)"></p>
              </div>
            </div>
          </div>

          <!-- SIM卡类型 -->
          <div class="model-detail-characteristic">
            <div class="detail">
              <div class="detail-title-main">
                <p>SIM卡类型</p>
              </div>
            </div>
            <div class="detail"
                 v-for="(modelDetail, index) in modelListChecked"
                 :key="index">
              <div class="detail-title">
                <p>{{ modelDetail.phone_model }}</p>
              </div>
              <div class="detail-content">
                <p v-html="getAppointStr1('SIM卡类型','<br/>',modelDetail.infor_network,false)"></p>
              </div>
            </div>
          </div>

          <!-- 硬件特点 -->
          <div class="model-detail-characteristic">
            <div class="detail">
              <div class="detail-title-main">
                <p>硬件特点</p>
              </div>
            </div>
            <div class="detail"
                 v-for="(modelDetail, index) in modelListChecked"
                 :key="index">
              <div class="detail-title">
                <p>{{ modelDetail.phone_model }}</p>
              </div>
              <div class="detail-content">
                <p v-html="getAppointStr1('其他硬件参数','<br/>',modelDetail.infor_hardware,false)"></p>
              </div>
            </div>
          </div>

          <!-- 支持网络类型 -->
          <div class="model-detail-characteristic">
            <div class="detail">
              <div class="detail-title-main">
                <p>支持网络类型</p>
              </div>
            </div>
            <div class="detail"
                 v-for="(modelDetail, index) in modelListChecked"
                 :key="index">
              <div class="detail-title">
                <p>{{ modelDetail.phone_model }}</p>
              </div>
              <div class="detail-content">
                <p v-html="getAppointStr1('','SIM卡类型',modelDetail.infor_network,true)"></p>
              </div>
            </div>
          </div>

        </van-tab>
      </van-tabs>
    </div>
  </transition>
</template>
<script>
export default {
  name: 'modelContrastDetail',
  components: {

  },
  data () {
    return {
      active: '',
      isShowImg: false,
      errorImg: 'this.src="' + require('@/assets/img/mobile/blank.png') + '"',
      imgUrl: [],
      modelList: [],
      modelListChecked: []
    }
  },
  methods: {
    // 返回上一级
    onClickLeft () {
      this.$router.go(-1)
    },
    onClickRight () {
      this.$toast.success('收藏成功')
    },
    // 处理集合，筛选出被选择项
    dealModelList () {
      this.modelList = this.$route.params.modelList
      this.modelListChecked = []
      var list = this.modelList
      for (var i = 0; i < list.length; i++) {
        if (list[i].isChecked) {
          this.modelListChecked.push(list[i])
        }
      }
    },
    // 打开购买链接
    openBuy (url) {
      if (url === '' || url === null) {
        this.$toast.fail('暂时无法跳转到购买链接哦～')
      } else {
        this.$router.push({ name: 'BuyDetail', params: { buyLink: url } })
      }
    },
    // 查看手机图片
    openImg (url) {
      this.isShowImg = true
      this.imgUrl = [require('@/assets/img/mobile/' + url + '.png')]
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
    },
    // 查找字符（是否支持该功能）
    checkStr (strCheck, strOriginal) {
      if (strOriginal === null || strOriginal === '') {
        return '暂无数据'
      }
      if (strOriginal.indexOf(strCheck) !== -1) {
        return '支持'
      } else {
        return '不支持'
      }
    }
  },
  // 监听路由变化，实时更新参数
  watch: {
    // 检查参数是否变化，变化则更新
    '$route' (to, from) {
      if (this.$route.params.modelList) {
        this.dealModelList()
      }
    }
  },
  created () {
    this.dealModelList()
  }
}
</script>
<style scoped>
/* 机型对比头部，包含机型图片和名称 */
.model-contrast-head {
  height: 160px;
  overflow: auto;
}
.model-detail-head {
  float: left;
  width: 50%;
  height: 100%;
}
.model-img {
  float: left;
  width: 100%;
  height: 120px;
}
.model-name {
  float: left;
  height: 40px;
  line-height: 40px;
  text-align: center;
  font-size: 16px;
  width: 100%;
}

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
  width: 18%;
  text-align: left;
  margin: 4% 6% 0 6%;
}
.detail-title p {
  word-wrap: break-word;
}
/* 子项内容 */
.detail-content {
  float: left;
  width: 64%;
  /* background-color: red; */
  text-align: left;
  margin: 4.5% 6% 0 0;
}
</style>
