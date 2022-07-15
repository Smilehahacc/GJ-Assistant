<template>
  <transition enter-active-class="animated slideInLeft">
    <!-- 文章详情 -->
    <div id="app">
      <div class="article-head">
        <van-nav-bar title="资讯详情"
                     left-text="返回"
                     right-text=""
                     left-arrow
                     style="height: 100%;line-height: 60px"
                     @click-left="onClickLeft"/>
      </div>
      <div class="article-head-title">
        <p @click="backToHome">&nbsp;
          <van-icon name="location-o" />&nbsp;首页&nbsp;/&nbsp;资讯详情</p>
      </div>
      <iframe id="show-iframe"
              :src="articleUrl"
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
  name: 'articleDetail',
  inject: ['reload'],
  data () {
    return {
      articleUrl: ''
    }
  },
  mounted () {
    const oIframe = document.getElementById('show-iframe')
    const deviceWidth = document.documentElement.clientWidth
    const deviceHeight = document.documentElement.clientHeight * 6
    oIframe.style.width = deviceWidth + 'px'
    oIframe.style.height = deviceHeight + 'px'
  },
  methods: {
    onClickLeft () {
      this.$router.push('/main')
    },
    backToHome () {
      this.$router.push('/main')
    }
  },
  // 监听路由变化，实时更新参数
  watch: {
    // 检查参数是否变化，变化则更新
    '$route' (to, from) {
      if (this.$route.params.articleUrl) {
        this.articleUrl = this.$route.params.articleUrl
      }
    }
  },
  created () {
    this.articleUrl = this.$route.params.articleUrl
  }
}

</script>
<style scoped>
.article-head {
  position: absolute;
  top: 0px;
  width: 100%;
  height: 60px;
  background-color: white;
  z-index: 999;
}

.article-head-title {
  position: absolute;
  top: 85px;
  left: 4.8%;
  width: 90%;
  height: 50px;
  background-color: white;
  z-index: 999;
}

.article-head-title p {
  line-height: 50px;
  font-size: 16px;
  text-align: left;
  color: #515a6e;
}
</style>
