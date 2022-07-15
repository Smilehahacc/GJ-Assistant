<template>
  <div class="model-list">
    <van-pull-refresh v-model="isLoading"
                      success-text="刷新成功"
                      @refresh="onRefresh">
      <transition enter-active-class="animated fadeInRight"
                  leave-active-class="animated flash">
        <!-- 机型展示面板-->
        <div v-if="isShow">
          <div class="model-panel"
               v-for="(modelList, index) in modelList"
               :key="index">
            <div class="model-img"><img :src="require('@/assets/img/mobile/'+modelList.phone_model+'.png')"
                   :onerror="errorImg"></div>
            <div class="model">
              <p class="model-name"
                 @click="openModelDetail(index)">{{ modelList.phone_model }}<span style="text-align: end;float: right">{{ modelList.infor_price!=0?modelList.infor_price+'元':'暂无报价' }}</span></p>
              <div class="model-detial">
                <div @click="openModelDetail(index)">
                  <p>处理器：{{ modelList.infor_cpu }}&nbsp;&nbsp;&nbsp;电池：{{ modelList.infor_battery }}mAh</p>
                </div>
                <div @click="openModelDetail(index)">
                  <p>屏幕：{{ modelList.infor_screen_size }}英寸&nbsp;{{ modelList.infor_screen_material }}&nbsp;{{ modelList.infor_resolving_power }}</p>
                </div>
                <div class="buy-button">
                  <van-button icon="shopping-cart-o"
                              size="small"
                              @click="openBuy(modelList.infor_buy_link)"
                              color="#ff0033">京东自营</van-button>
                </div>
              </div>
            </div>
          </div>
        </div>

      </transition>
    </van-pull-refresh>
  </div>
</template>
<script>
export default {
  name: 'modelListDetail',
  props: {
    modelList: {
      type: Array,
      require: true
    }
  },
  components: {

  },
  data () {
    return {
      isLoading: false,
      isShow: false,
      errorImg: 'this.src="' + require('@/assets/img/mobile/blank.png') + '"'
    }
  },
  methods: {
    // 打开对应的机型详情页面
    openModelDetail (index) {
      this.$router.push({ name: 'ModelDetail', params: { modelDetail: this.modelList[index] } })
    },
    // 打开购买链接
    openBuy (url) {
      if (url === '' || url === null) {
        this.$toast.fail('暂时无法跳转到购买链接哦～')
      } else {
        this.$router.push({ name: 'BuyDetail', params: { buyLink: url } })
      }
    },
    onRefresh () {
      setTimeout(() => {
        this.isLoading = false
        this.isShow = false
        setTimeout(() => {
          this.isShow = true
        }, 200)
      }, 1000)
    }
  },
  created () {
    setTimeout(() => {
      this.isShow = true
    }, 100)
  }
}
</script>
<style scoped>
.model-list {
  min-height: 200px;
  height: auto;
  width: 100%;
  text-align: center;
  margin: 0 auto;
  background-color: white;
}
/* 每个机型的列表面板 */
.model-panel {
  float: left;
  width: 100%;
  min-height: 140px;
  height: auto;
  margin: 0;
  border-bottom: 2px solid#dcdee26e;
}
/* 机型图片 */
.model-img {
  float: left;
  width: 30%;
  height: auto;
  margin: 4% 0 0 0;
}

.model-img img {
  float: left;
  width: 100%;
  height: 100%;
}

.model {
  float: left;
  width: 68%;
  min-height: 100px;
  height: auto;
  margin: 4% 2% 0 0;
}

.model-name {
  font-size: 18px;
  text-align: left;
  line-height: 20px;
  margin: 0 6% 0 0;
}

.model-detial {
  width: 100%;
  text-align: left;
  font-size: 15px;
  color: #515a6e;
  margin: 2% 6% 0 0;
}
/* 购买按钮 */
.buy-button {
  width: 100%;
  height: auto;
  margin: 10px 0 10px 0;
  text-align: left;
}
</style>
