<template>
  <div id='app'>
    <keep-alive>
      <router-view v-if="isRouterAlive" />
    </keep-alive>
  </div>
</template>

<script>
export default {
  name: 'app',
  provide () {
    return {
      reload: this.reload
    }
  },
  data () {
    return {
      isRouterAlive: true
    }
  },
  components: {

  },
  methods: {
    // 页面刷新方法
    reload () {
      this.isRouterAlive = false
      this.$nextTick(function () {
        this.isRouterAlive = true
      })
    },
    postMethods () {
      this.$axios.post('/api/newExtra', {
        replyId: 1
      }).then(response => {
        console.log('xxx')
        if (response.data === 'SUCCESS') {
        } else {

        }
      }).catch(error => {
        console.log(error)
        this.$Message.error('请求失败！' + error.status + ',' + error.statusText)
      })
    }
  }
}
</script>

// 样式列表声明
<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #1a1a1a;
  /* #17233d 标题 Title
  #515a6e 正文 Content
  #808695 辅助/图标 Sub Color
  #c5c8ce 失效 Disabled
  #dcdee2 边框 Border
  #e8eaec 分割线 Divider
  #f8f8f9 背景 Background */
}

* {
  margin: 0;
  padding: 0;
}
</style>
