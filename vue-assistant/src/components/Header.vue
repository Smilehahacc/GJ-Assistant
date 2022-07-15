<template>
  <van-sticky>
    <div class="assistant-head">
      <div class="assistant-logo">
        <img :src="require('@/assets/img/logo.png')">
      </div>
      <div class="assistant-popup-button">
        <van-icon name="ellipsis"
                  :size='24'
                  @click="showPopup()" />
      </div>
    </div>
    <!-- 左侧功能菜单 -->
    <LeftMenu :show="show"
              @closePopupShow="closePopup" />
  </van-sticky>
</template>

<script>
import LeftMenu from '@/components/LeftMenu'
export default {
  name: 'assistantHeader',
  components: {
    LeftMenu: LeftMenu
  },
  data () {
    return {
      isLogin: true,
      userId: '',
      userName: '',
      show: false
    }
  },
  methods: {
    // 打开和关闭侧边菜单
    showPopup () {
      this.show = true
    },
    closePopup (e) {
      this.show = e
    }
  },
  created () {

  }
}
</script>

<style scoped>
/* 头部栏 */
.assistant-head {
  margin-bottom: 20px;
  height: 60px;
  width: 100%;
  background-color: white;
  box-shadow: 0px 1px 0px 0px rgba(90, 90, 90, 0.2);
}

.assistant-logo {
  float: left;
  height: 100%;
  width: 200px;
}

.assistant-logo img {
  float: left;
  margin: 0px 20px;
  height: 60px;
  width: auto;
}

.assistant-popup-button {
  float: right;
  padding: 8px 6% 0 0;
}
</style>
