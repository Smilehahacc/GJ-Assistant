<template>
  <transition enter-active-class="animated slideInLeft">
    <div id='app'>
      <van-sticky>
        <van-nav-bar title="机型对比"
                     left-text="返回"
                     left-arrow
                     style="height: 100%;line-height: 60px"
                     @click-left="onClickLeft()"
                     @click-right="onClickRight()">
          <van-icon name="search"
                    size="24"
                    slot="right" />
        </van-nav-bar>
      </van-sticky>

      <!-- 加载中 -->
      <div v-if="isLoadingShow" style="margin: 20px 0 0 0">
        <van-loading size="24px">加载中...</van-loading>
      </div>

      <!-- 去添加 -->
      <div v-if="isShowTips"
           style="margin: 20px 0 0 0">
        <p style="font-size: 18px;height: 80px;line-height:80px">{{ tipsContent }}</p>
        <van-button color="linear-gradient(to right, #4bb0ff, #6149f6)"
                    icon="add-o"
                    @click="openRecommend()">{{ tipsContentButton }}</van-button>
      </div>

      <!-- 机型对比列表 -->
      <ModelContrast :modelList="modelList"
                     @refreshModelList="refreshModelList"
                     @checkModel="checkModel" />
      <van-button class="contrast-button"
                  @click="openContrastDetail()"
                  v-if="!isShowTips">开始对比</van-button>
    </div>
  </transition>
</template>
<script>
import ModelContrast from '@/components/ModelContrast'
export default {
  name: 'contrast',
  components: {
    ModelContrast: ModelContrast
  },
  data () {
    return {
      isShowTips: false,
      isLoadingShow: false,
      tipsContent: '还没有对比机型哦',
      tipsContentButton: '去添加',
      modelList: []
    }
  },
  methods: {
    // 返回上一级页面
    onClickLeft () {
      this.$router.go(-1)
    },
    // 跳转到查询页面
    onClickRight () {
      this.$router.push('/search')
    },
    // 跳转页面，登陆或去添加页面
    openRecommend () {
      if (this.tipsContentButton !== '去添加') {
        this.$router.push('/login')
      } else {
        this.$router.push('/recommend')
      }
    },
    // 打开对比页详情
    openContrastDetail () {
      if (this.calculationCount()) {
        this.$router.push({ name: 'ModelContrastDetail', params: { modelList: this.modelList } })
      } else {
        this.$toast.fail('至少选择两款机型进行对比哦～')
      }
    },
    // 计算勾选的机型数量，若小于2则不允许用户进行对比
    calculationCount () {
      var count = 0
      for (var i = 0; i < this.modelList.length; i++) {
        if (this.modelList[i].isChecked) {
          count++
        }
      }
      if (count < 2) {
        return false
      } else {
        return true
      }
    },
    // 获取用户添加的对比机型
    getContrast () {
      this.isLoadingShow = true
      var userId = this.getCookie('userId')
      if (userId === '' || userId.length === 0) {
        this.isShowTips = true
        this.tipsContent = '还未登陆哦'
        this.tipsContentButton = '去登陆'
        this.isLoadingShow = false
      } else {
        this.$axios.post('/findUserContrast', {
          user_id: userId
        }).then(data => {
          if (data.data !== null && data.data.length !== 0) {
            this.modelList = data.data
            this.isShowTips = false
          } else {
            this.$toast.fail('还没有添加对比机型哦')
            this.isShowTips = true
          }
          this.isLoadingShow = false
        }).catch(error => {
          console.log(error)
          this.isLoadingShow = false
          this.$toast.fail('获取用户的对比机型失败' + error.status + ',' + error.statusText)
        })
      }
    },
    // 更新用户对比列表
    refreshModelList (list) {
      this.modelList = list
    },
    // 选中机型
    checkModel (index) {
      if (this.modelList[index].isChecked) {
        this.$set(this.modelList[index], 'isChecked', false)
      } else {
        this.$set(this.modelList[index], 'isChecked', true)
      }
    },
    // 处理集合，添加选中标记元素
    dealModelList () {
      for (let val in this.modelList) {
        this.$set(val, 'isChecked', false)
      }
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
    this.getContrast()
  },
  created () {
  }
}
</script>
<style scoped>
/* 对比按钮 */
.contrast-button {
  height: 40px;
  width: 180px;
  line-height: 40px;
  margin: 20px 0 0 0;
  border-radius: 6px;
  color: white;
  background-color: #2d8cf0;
}
</style>
