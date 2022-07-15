<template>
  <transition enter-active-class="animated slideInRight">
    <div id='app'>
      <!-- 导航栏 -->
      <van-sticky>
        <van-nav-bar title="购买详情"
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

      <iframe id="show-iframe"
              :src="buyLink"
              frameborder=0
              name="showHere"
              marginwidth="0"
              marginheight="0"
              vspace="0"
              hspace="0"
              scrolling="no"
              allowtransparency="true"
              allowfullscreen="true"></iframe>
    </div>
  </transition>
</template>
<script>
export default {
  name: 'buyDetail',
  components: {
  },
  data () {
    return {
      showShare: false,
      buyLink: '',
      options: [[
        { name: '微信', icon: 'wechat' },
        { name: '微博', icon: 'weibo' },
        { name: 'QQ', icon: 'qq' }
      ], [{ name: '复制链接', icon: 'link' },
        { name: '分享海报', icon: 'poster' },
        { name: '二维码', icon: 'qrcode' }
      ]]
    }
  },
  mounted () {
    const oIframe = document.getElementById('show-iframe')
    const deviceWidth = document.documentElement.clientWidth
    const deviceHeight = document.documentElement.clientHeight * 3
    oIframe.style.width = deviceWidth + 'px'
    oIframe.style.height = deviceHeight + 'px'
  },
  methods: {
    // 返回上一级
    onClickLeft () {
      this.$router.go(-1)
    },
    onClickRight () {
      this.showShare = true
    },
    onSelect (option) {
      this.$toast(option.name)
      this.showShare = false
    }
  },
  // 监听路由变化，实时更新参数
  watch: {
    '$route' (to, from) {
      if (this.$route.params.buyLink) {
        this.buyLink = this.$route.params.buyLink
      }
    }
  },
  created () {

  }
}
</script>
<style scoped>
.map-head {
  position: absolute;
  top: 0px;
  width: 100%;
  height: 60px;
  background-color: white;
  z-index: 999;
}

.map-head-title {
  position: absolute;
  top: 85px;
  left: 4.8%;
  width: 90%;
  height: 50px;
  background-color: white;
  z-index: 999;
}

.map-head-title p {
  line-height: 50px;
  font-size: 16px;
  text-align: left;
  color: #515a6e;
}
</style>
