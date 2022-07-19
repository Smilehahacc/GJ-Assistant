<template>
  <div class="model-list">
    <van-pull-refresh v-model="isLoading"
                      success-text="刷新成功"
                      @refresh="onRefresh()">
      <transition enter-active-class="animated fadeInRight"
                  leave-active-class="animated flash">
        <div v-if="isShow">
          <div class="model-panel"
               v-for="(modelList, index) in modelList"
               :key="index"
               @click="openModelDetail(index)">
            <div class="model-img"><img :src="require('@/assets/img/mobile/'+modelList.phone_model+'.png')"
                   :onerror="errorImg"></div>
            <div class="model">
              <p class="model-name">{{ modelList.phone_model }}</p>
              <div class="model-detial">
                <div class="model-detial1">
                  <p>处理器：{{ modelList.infor_cpu }}&nbsp;&nbsp;&nbsp;电池：{{ modelList.infor_battery }}mAh</p>
                </div>
                <div class="model-detial2">
                  <p>屏幕：{{ modelList.infor_screen_size }}英寸&nbsp;{{ modelList.infor_resolving_power }}</p>
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
  name: 'modelList',
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
      isLoadingShow: false,
      isShow: false,
      errorImg: 'this.src="' + require('@/assets/img/mobile/blank.png') + '"'
    }
  },
  methods: {
    // 打开对应的机型详情页面
    openModelDetail (index) {
      this.$router.push({ name: 'ModelDetail', params: { modelDetail: this.modelList[index] } })
    },
    onRefresh () {
      this.findAllModel()
      setTimeout(() => {
        this.isLoading = false
        this.isShow = false
        setTimeout(() => {
          this.isShow = true
        }, 200)
      }, 1000)
    },
    // 获取机型列表
    findAllModel () {
      this.$axios.post('/findAllModel', {
      }).then(data => {
        console.log('获取所有机型列表成功！')
        if (data.data !== null && data.data.length !== 0) {
          this.$emit('refreshModelList', data.data)
        }
      }).catch(error => {
        console.log(error)
        this.$toast.fail('获取失败！' + error.status + ',' + error.statusText)
      })
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
  height: 100px;
  margin: 0;
  border-bottom: 2px solid#dcdee26e;
}
/* 机型图片 */
.model-img {
  float: left;
  width: auto;
  height: 80px;
  margin: 10px 0 0 0;
}

.model-img img {
  float: left;
  width: auto;
  height: 100%;
}

.model {
  float: left;
  width: 70%;
  height: 100px;
  margin: 10px 0 0 0;
}

.model-name {
  font-size: 18px;
  text-align: left;
  line-height: 20px;
  margin: 0 15px 0 0;
  color: #17233d;
}

.model-detial {
  width: 100%;
  text-align: left;
  font-size: 16px;
  color: #515a6e;
  /* margin: 10px 0 0 0; */
}

.model-detial1 {
  width: 100%;
}
.model-detial1 p {
  font-size: 16px;
  text-align: left;
  width: auto;
}

.model-detial2 {
  font-size: 16px;
  width: 100%;
}

.model-detial2 p {
  font-size: 16px;
  text-align: left;
  width: 100%;
}
</style>
