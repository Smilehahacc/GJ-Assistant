<template>
  <!-- 侧边菜单 -->
  <van-popup v-model="show"
             position="left"
             @close="closePopup()"
             :style="{ width: '70%',height:'100%' }">
    <transition enter-active-class="animated fadeInLeft"
                leave-active-class="animated flash">
      <div v-if="show">
        <div class="menu-choice"
             v-for="(choice, index) in choiceList"
             :key="index"
             :class="choice.id==choiceIndex?'hover':''"
             @click="choiceClick(choice.id)">
          <van-icon :name="choice.icon" />&nbsp;&nbsp;&nbsp;&nbsp;{{ choice.content }}
        </div>
        <div style="height:30px;width:100%;"></div>
        <div class="menu-choice"
             v-for="(choice, index) in otherChoiceList"
             :key="index+8"
             :class="choice.id==choiceIndex?'hover':''"
             @click="choiceClick(choice.id)">
          <van-icon :name="choice.icon" />&nbsp;&nbsp;&nbsp;&nbsp;{{ choice.content }}
        </div>
      </div>
    </transition>
  </van-popup>
</template>

<script>
export default {
  name: 'leftMenu',
  props: {
    show: {
      type: Boolean,
      require: true
    }
  },
  data () {
    return {
      choiceIndex: -1,
      choiceList: [{
        id: 0,
        icon: 'comment-o',
        content: '手机资讯',
        url: '/main'
      }, {
        id: 1,
        icon: 'new-o',
        content: '新机推荐',
        url: '/recommend'
      }, {
        id: 2,
        icon: 'search',
        content: '靓机查询',
        url: '/search'
      }, {
        id: 3,
        icon: 'chart-trending-o',
        content: '机型对比',
        url: '/contrast'
      }, {
        id: 4,
        icon: 'shop-o',
        content: '附近门店',
        url: '/nearbyStore'
      }, {
        id: 5,
        icon: 'sign',
        content: '降价提醒',
        url: '/remind'
      }, {
        id: 6,
        icon: 'orders-o',
        content: 'DXO排行',
        url: '/cameraRanking'
      }, {
        id: 7,
        icon: 'fire-o',
        content: '站内热点',
        url: '/popular'
      }],
      otherChoiceList: [{
        id: 8,
        icon: 'user-o',
        content: '用户中心',
        url: '/login'
      }, {
        id: 9,
        icon: 'bulb-o',
        content: '问题反馈',
        url: '/feedback'
      }, {
        id: 10,
        icon: 'cluster-o',
        content: '应用说明',
        url: '/explain'
      }]
      // {
      //   id: 10,
      //   icon: 'setting-o',
      //   content: '设置',
      //   url: '/setting'
      // }]
    }
  },
  methods: {
    closePopup () {
      this.$emit('closePopupShow', false)
    },
    // 点击某个选项
    choiceClick (e) {
      this.choiceIndex = e
      this.closePopup()
      setTimeout(() => {
        if (e < 8) {
          this.$router.push(this.choiceList[e].url)
        } else {
          var count = e - 8
          this.$router.push(this.otherChoiceList[count].url)
        }
      }, 600)
    }
  },
  mounted () {
  },
  created () {
  }
}
</script>

<style scoped>
/* 左侧菜单按钮 */
.menu-choice {
  width: 100%;
  height: 60px;
  /* border-bottom: 2px solid#dcdee26e; */
  text-align: left;
  padding: 0 0 0 20px;
  font-size: 20px;
  line-height: 60px;
  color: #515a6e;
}

.menu-choice.hover {
  background-color: #dcdee26e;
}
</style>
