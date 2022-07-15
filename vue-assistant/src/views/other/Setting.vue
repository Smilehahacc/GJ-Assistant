<template>
  <transition enter-active-class="animated slideInRight">
    <div id='app'>
      <!-- 导航栏 -->
      <van-sticky>
        <van-nav-bar title="设置"
                     left-text="返回"
                     left-arrow
                     style="height: 100%;line-height: 60px"
                     @click-left="onClickLeft()">
        </van-nav-bar>
      </van-sticky>
    </div>
  </transition>
</template>
<script>
export default {
  name: 'setting',
  components: {
  },
  data () {
    return {
    }
  },
  methods: {
    // 返回上一级
    onClickLeft () {
      this.$router.go(-1)
    }
  },
  created () {
  }
}
</script>
<style scoped>
</style>
