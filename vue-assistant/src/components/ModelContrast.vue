<template>
  <div class="model-list">
    <van-pull-refresh v-model="isLoading"
                      success-text="刷新成功"
                      @refresh="onRefresh">
      <transition enter-active-class="animated fadeInRight"
                  leave-active-class="animated flash">
        <!-- 机型展示面板-->
        <div v-if="isShow">
          <van-swipe-cell v-for="(modelList, index) in modelList"
                          :key="index">
            <div class="model-panel">
              <div class="model-img"><img :src="require('@/assets/img/mobile/'+modelList.phone_model+'.png')"
                     :onerror="errorImg"
                     @click="checkModel(index)">
              </div>
              <div class="model">
                <p class="model-name"
                   @click="checkModel(index)">{{ modelList.phone_model }}<span style="text-align: end;float: right">{{ modelList.infor_price!=0?modelList.infor_price+'元':'暂无报价' }}</span></p>
                <div class="model-detial">
                  <div @click="checkModel(index)">
                    <p>处理器：{{ modelList.infor_cpu }}&nbsp;&nbsp;&nbsp;电池：{{ modelList.infor_battery }}mAh</p>
                  </div>
                  <div @click="checkModel(index)">
                    <p>屏幕：{{ modelList.infor_screen_size }}英寸&nbsp;{{ modelList.infor_screen_material }}&nbsp;{{ modelList.infor_resolving_power }}</p>
                  </div>
                </div>
              </div>
              <!-- 选择框 -->
              <van-checkbox v-model="modelList.isChecked"
                            shape="square"
                            style="margin: 15% 0 0 0"
                            checked-color="#07c160"></van-checkbox>

            </div>
            <van-button slot="left"
                        square
                        text="删除"
                        type="danger"
                        @click="deleteConfirm(index)"
                        style="height:140px" />
          </van-swipe-cell>
        </div>
      </transition>
    </van-pull-refresh>
  </div>
</template>
<script>
export default {
  name: 'modelContrast',
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
    // 勾选对比机型选择框
    checkModel (index) {
      this.$emit('checkModel', index)
    },
    // 确认是否删除对比机型
    deleteConfirm (index) {
      this.$dialog.confirm({
        message: '确认删除该机型吗？'
      }).then(() => {
        this.deleteModel(index)
      }).catch(() => {
        this.showNotify('已取消', '#ff0000')
      })
    },
    // 删除对比机型
    deleteModel (index) {
      var userId = this.getCookie('userId')
      this.$axios.post('/deleteModelContrast', {
        user_id: userId,
        phone_model: this.modelList[index].phone_model
      }).then(response => {
        if (response.data === 'SUCCESS') {
          this.getContrast()
          this.showNotify('删除成功', '#19be6b')
        } else {
          this.$toast.fail('删除对比机型失败')
        }
      }).catch(error => {
        console.log(error)
        this.$toast.fail('删除对比机型失败，请检查网络' + error.status + ',' + error.statusText)
      })
    },
    onRefresh () {
      this.getContrast()
      setTimeout(() => {
        this.isLoading = false
        this.isShow = false
        setTimeout(() => {
          this.isShow = true
        }, 200)
      }, 1000)
    },
    // 获取用户添加的对比机型
    getContrast () {
      var userId = this.getCookie('userId')
      this.$axios.post('/findUserContrast', {
        user_id: userId
      }).then(data => {
        if (data.data !== null && data.data.length !== 0) {
          this.$emit('refreshModelList', data.data)
        }
      }).catch(error => {
        console.log(error)
        this.$toast.fail('获取用户的对比机型失败' + error.status + ',' + error.statusText)
      })
    },
    // 顶部消息通知
    showNotify (message, color) {
      this.$notify({
        message: message,
        color: '#ffffff',
        background: color,
        duration: 900
      })
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
  width: 60%;
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
</style>
