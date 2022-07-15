<template>
  <transition enter-active-class="animated fadeInLeft">
    <div id="app">
      <div class="search-head">
        <div class="back-button">
          <van-icon name="replay"
                    :size='24'
                    @click="back()" />
        </div>
        <div class="search-bar">
          <van-search v-model="searchModel"
                      :placeholder="searchTitle" />
        </div>
        <div class="search-button">
          <van-icon name="search"
                    :size='24'
                    @click="search()" />
        </div>
      </div>
      <van-sticky>
        <van-dropdown-menu>
          <van-dropdown-item v-model="value1"
                             :options="option1"
                             @change="changeSortFirst(value1)" />
          <van-dropdown-item v-model="value2"
                             :options="option2"
                             @change="changeSortSecond(value2)" />
          <van-dropdown-item v-model="value3"
                             title="筛选"
                             ref="dropdown"
                             @open="openRightMenu()" />
        </van-dropdown-menu>
      </van-sticky>
      <div class="search-tips"
           v-if="isShowTips">
        <div class="search-way-choice">
          <h>请选择搜索方式</h>
          <div class="way-choice-radio">
            <van-radio-group v-model="searchWay"
                             :icon-size="16"
                             @change="changSearchWay(searchWay)"
                             direction="horizontal">
              <van-radio name="1">机型搜索</van-radio>
              <van-radio name="2">处理器搜索</van-radio>
            </van-radio-group>
          </div>
        </div>
        <h>搜索小贴士</h>
        <div class="search-tips-content">
          <div class="tips-content"
               v-for="(searchTips, index) in searchTips"
               :key="index">
            <van-tag type="success">{{ searchTips.tips }}</van-tag>
            <p>{{ searchTips.tips_content }}</p>
          </div>
        </div>
      </div>

      <div class="search-result"
           v-if="!isShowTips">
        <!-- 加载中 -->
        <div v-if="isLoadingShow" style="margin: 20px 0 0 0">
          <van-loading size="24px">加载中...</van-loading>
        </div>
        <ModelListDetail :modelList="modelList" />
      </div>
      <!-- 右侧机型筛选菜单 -->
      <RightMenu :show="show"
                 @closeRightMenuShow="closeRightMenu"
                 @startSearch="searchByOption" />
    </div>
  </transition>
</template>
<script>
import ModelListDetail from '@/components/ModelListDetail'
import RightMenu from '@/components/RightMenu'
export default {
  name: 'search',
  components: {
    ModelListDetail: ModelListDetail,
    RightMenu: RightMenu
  },
  data () {
    return {
      isLoadingShow: false,
      show: false,
      showRight: false,
      isShowTips: true,
      searchModel: '',
      searchTitle: '搜索手机',
      searchWay: '1',
      modelOption: [],
      searchTips: [{
        tips: 'tips1',
        tips_content: '可以选择通过手机型号或者处理器信息两种方式查询机型'
      }, {
        tips: 'tips2',
        tips_content: '点击排序选项可对搜索结果进行排序'
      }, {
        tips: 'tips3',
        tips_content: '如果想获得更加强大的搜索功能，请点击筛选按钮以便呼出参数筛选菜单'
      }],
      value1: 0,
      value2: 'a',
      value3: 's',
      option1: [
        { text: '全部机型', value: 0 },
        { text: '新款机型', value: 1 }
      ],
      option2: [
        { text: '默认排序', value: 'a' },
        { text: '价格排序', value: 'b' },
        { text: '屏幕尺寸', value: 'c' },
        { text: '机身重量', value: 'd' },
        { text: '电池容量', value: 'e' }
      ],
      option3: [
        { text: '筛选', value: 's' }
      ],
      modelList: [],
      modelListBack: []
    }
  },
  mounted () {

  },
  methods: {
    back () {
      if (!this.isShowTips) {
        this.isShowTips = true
      } else {
        this.$router.go(-1)
      }
    },
    // 按机型名称查找手机
    search () {
      // this.$toast('开始按输入条件查找手机' + this.searchModel)
      if (this.searchWay === '1') {
        this.findModelByName()
      } else if (this.searchWay === '2') {
        this.findModelByCpu()
      }
      this.isShowTips = false
    },
    // 开始按筛选条件查询
    searchByOption (modelOption) {
      this.show = false
      this.phone_model = modelOption
      this.$refs.dropdown.toggle(false)
      this.$toast('开始按筛选条件查询机型')
      this.$axios.post('/findModelByOption', {
        option_cpu: modelOption[0].option_cpu,
        option_brand: modelOption[0].option_brand,
        option_refresh_rate: modelOption[0].option_refresh_rate,
        option_5g: modelOption[0].option_5g,
        option_nfc: modelOption[0].option_nfc,
        option_infrared: modelOption[0].option_infrared,
        option_headset: modelOption[0].option_headset,
        option_typec: modelOption[0].option_typec,
        option_waterproof: modelOption[0].option_waterproof,
        option_ufs: modelOption[0].option_ufs,
        option_hifi: modelOption[0].option_hifi,
        option_ois: modelOption[0].option_ois,
        option_zoom: modelOption[0].option_zoom,
        option_wide_angle: modelOption[0].option_wide_angle,
        option_micro: modelOption[0].option_micro,
        option_tof: modelOption[0].option_tof,
        option_hfr: modelOption[0].option_hfr,
        option_slow: modelOption[0].option_slow,
        option_price_low: modelOption[0].option_price_low,
        option_price_high: modelOption[0].option_price_high,
        option_weight_low: modelOption[0].option_weight_low,
        option_weight_high: modelOption[0].option_weight_high,
        option_battery_low: modelOption[0].option_battery_low,
        option_battery_high: modelOption[0].option_battery_high,
        option_screen: modelOption[0].option_screen,
        option_size_low: modelOption[0].option_size_low,
        option_size_high: modelOption[0].option_size_high,
        option_resolving_power: modelOption[0].option_resolving_power
      }).then(data => {
        if (data.data !== null) {
          this.modelList = data.data
          this.isShowTips = false
          this.$toast('完成查询')
          this.cleanSort()
          if (data.data.length === 0) {
            this.$toast('抱歉，没有搜索到相关机型')
            this.isShowTips = true
          }
        }
      }).catch(error => {
        console.log(error)
        this.$toast.fail('根据机型名查询列表失败！' + error.status + ',' + error.statusText)
      })
    },
    // 根据机型名查询
    findModelByName () {
      this.isLoadingShow = true
      this.$axios.post('/findModelByName', {
        phone_model: this.searchModel
      }).then(data => {
        console.log('根据机型名查询列表成功！')
        if (data.data !== null) {
          this.isLoadingShow = false
          this.modelList = data.data
          this.cleanSort()
          if (data.data.length === 0) {
            this.$toast('抱歉，没有搜索到相关机型')
            this.isShowTips = true
          }
        }
      }).catch(error => {
        console.log(error)
        this.isLoadingShow = false
        this.$toast.fail('根据机型名查询列表失败！' + error.status + ',' + error.statusText)
      })
    },
    // 根据cpu查询机型
    findModelByCpu () {
      this.isLoadingShow = true
      this.$axios.post('/findModelByCpu', {
        infor_cpu: this.searchModel
      }).then(data => {
        console.log('根据cpu查询机型成功！')
        if (data.data !== null) {
          this.isLoadingShow = false
          this.modelList = data.data
          this.cleanSort()
          if (data.data.length === 0) {
            this.$toast('抱歉，没有搜索到相关机型')
            this.isShowTips = true
          }
        }
      }).catch(error => {
        console.log(error)
        this.isLoadingShow = false
        this.$toast.fail('根据cpu查询机型失败！' + error.status + ',' + error.statusText)
      })
    },
    // 打开和关闭侧边菜单
    openRightMenu () {
      this.show = true
    },
    closeRightMenu (val) {
      this.show = val
      this.$refs.dropdown.toggle(false)
    },
    // 改变排序方式1
    changeSortFirst (value) {
      var list
      if (value === 0) {
        list = this.sortByKey(this.modelList, 'phone_id')
        this.modelList = JSON.parse(JSON.stringify(list))
        this.$toast.success('当前为查看全部机型')
      } else if (value === 1) {
        list = this.sortByKey(this.modelList, 'infor_price')
        this.modelList = JSON.parse(JSON.stringify(list))
        this.$toast.success('按新机排序完成')
      }
    },
    // 改变排序方式2
    changeSortSecond (value) {
      var list
      // 价格排序
      if (value === 'a') {
        list = this.sortByKey(this.modelList, 'phone_id')
        this.modelList = JSON.parse(JSON.stringify(list))
        this.$toast.success('已恢复默认排序')
      } else if (value === 'b') {
        list = this.sortByKey(this.modelList, 'infor_price')
        this.modelList = JSON.parse(JSON.stringify(list))
        this.$toast.success('按价格排序完成')
      } else if (value === 'c') {
        list = this.sortByKey(this.modelList, 'infor_screen_size')
        this.modelList = JSON.parse(JSON.stringify(list))
        this.$toast.success('按屏幕尺寸排序完成')
      } else if (value === 'd') {
        list = this.sortByKey(this.modelList, 'infor_weight')
        this.modelList = JSON.parse(JSON.stringify(list))
        this.$toast.success('按机身重量排序完成')
      } else if (value === 'e') {
        list = this.sortByKey(this.modelList, 'infor_battery')
        this.modelList = JSON.parse(JSON.stringify(list))
        this.$toast.success('按电池容量排序完成')
      }
    },
    sortByKey (array, key) {
      return array.sort(function (a, b) {
        var x = a[key]
        var y = b[key]
        return ((x < y) ? -1 : ((x > y) ? 1 : 0))
      })
    },
    // 清除排序选项
    cleanSort () {
      this.value1 = 0
      this.value2 = 'a'
      this.value3 = 's'
    },
    // 改变搜索方式
    changSearchWay (searchWay) {
      if (searchWay === '1') {
        this.searchTitle = '搜索手机'
      } else if (searchWay === '2') {
        this.searchTitle = '搜索处理器'
      }
    }
  },
  created () {
  }
}

</script>
<style scoped>
/* 头部，包含返回按钮，搜索栏和搜索按钮 */
.search-head {
  height: 60px;
  width: 100%;
  background-color: white;
  box-shadow: 0px 1px 0px 0px rgba(90, 90, 90, 0.2);
}

.back-button {
  width: 10%;
  float: left;
  height: 40px;
  padding: 12px 0 0 4%;
  color: #ff8800;
}

.search-bar {
  float: left;
  width: 72%;
  height: 40px;
  line-height: 60px;
  text-align: center;
  font-size: 18px;
  padding: 5px 0 0 0;
}

.search-button {
  float: right;
  width: 10%;
  height: 40px;
  padding: 12px 4% 0 0;
  /* color: #2db7f5; */
}

.search-tips,
.search-result {
  width: 100%;
  min-height: 400px;
  height: auto;
}
/* 搜索提示 */
.search-tips {
  font-size: 18px;
  color: #17233d;
}

.search-tips h {
  font-size: 22px;
  color: #17233d;
}

.search-way-choice {
  margin: 20px 0 0 0;
}

/* 按钮组 */
.way-choice-radio {
  width: 100%;
  padding: 20px 20% 30px 20%;
  font-size: 18px;
  color: #515a6e;
}

/* 搜索提示的内容 */
.search-tips-content {
  font-size: 18px;
  width: 60%;
  margin: 0 auto;
  text-align: left;
}
.tips-content {
  margin: 20px 0 0 0;
}
</style>
