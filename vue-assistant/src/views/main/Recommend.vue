<template>
  <transition enter-active-class="animated slideInLeft">
    <div id='app'>
      <van-sticky>
        <div class="search-head">
          <div class="popup-button">
            <van-icon name="ellipsis"
                      :size='24'
                      @click="showPopup()" />
          </div>
          <div class="search-title">
            <p>新机推荐</p>
          </div>
          <div class="search-button">
            <van-icon name="search"
                      :size='24'
                      @click="search()" />
          </div>
        </div>
        <LeftMenu :show="show"
                  @closePopupShow="closePopup" />
      </van-sticky>
      <!-- 加载中 -->
      <div v-if="isLoadingShow" style="margin: 20px 0 0 0">
        <van-loading size="24px">加载中...</van-loading>
      </div>
      <ModelList :modelList="modelList"
                 @refreshModelList="refreshModelList" />
    </div>
  </transition>
</template>
<script>
import LeftMenu from '@/components/LeftMenu'
import ModelList from '@/components/ModelList'
export default {
  name: 'recommend',
  components: {
    LeftMenu: LeftMenu,
    ModelList: ModelList
  },
  data () {
    return {
      show: false,
      searchModel: '',
      isLoading: false,
      isLoadingShow: false,
      errorImg: 'this.src="' + require('@/assets/img/mobile/blank.png') + '"',
      modelList: []
    }
  },
  methods: {
    // 打开和关闭侧边菜单
    showPopup () {
      this.show = true
    },
    closePopup (e) {
      this.show = e
    },
    // 打开机型查询页面
    search () {
      this.$router.push('/search')
    },
    // 打开机型详细参数界面
    openModelDetail (index) {
      this.$router.push({ name: 'ModelDetail', params: { modelDetail: this.modelList[index] } })
    },
    // 获取机型列表
    findAllModel () {
      this.isLoadingShow = true
      this.$axios.post('/findAllModel', {
      }).then(data => {
        console.log('获取所有机型列表成功！')
        if (data.data !== null) {
          this.modelList = data.data
          this.isLoadingShow = false
        }
      }).catch(error => {
        console.log(error)
        this.isLoadingShow = false
        this.$toast.fail('获取所有机型列表！' + error.status + ',' + error.statusText)
      })
    },
    // 更新新机推荐列表
    refreshModelList (list) {
      this.modelList = list
    }
  },
  created () {
    this.findAllModel()
  }
}
</script>
<style scoped>
.search-head {
  height: 60px;
  width: 100%;
  background-color: white;
}

.popup-button {
  width: 10%;
  float: left;
  height: 40px;
  padding: 8px 0 0 4%;
}

.search-title {
  width: 72%;
  height: 60px;
  line-height: 60px;
  float: left;
  text-align: center;
  font-size: 18px;
}

.search-button {
  width: 10%;
  height: 40px;
  float: right;
  padding: 10px 4% 0 0;
  color: #ff8800;
}
</style>
