<template>
  <transition enter-active-class="animated fadeInLeft">
    <div id='app'>
      <!-- 导航栏 -->
      <van-sticky>
        <van-nav-bar title="应用说明"
                     left-text="返回"
                     left-arrow
                     style="height: 100%;line-height: 60px"
                     @click-left="onClickLeft()">
        </van-nav-bar>
      </van-sticky>

      <div>
        <p class="explain" v-for="(explainList,index) in explainList" :key="index">{{ explainList }}</p>
      </div>

    </div>
  </transition>
</template>
<script>
export default {
  name: 'explain',
  components: {
  },
  data () {
    return {
      explainList: [
        '1.本平台所收集用户信息都经过加密，平台将坚决抵制泄露信息的行为。',
        '2.平台涉及购买链接均来自第三方电商平台，任何相关产品问题均由第三方厂商承担。若用户购买手机出现任何问题，请咨询卖家。',
        '3.平台永久免费使用，不收取用户任何费用，若用户发现第三方收费链接请勿点击。',
        '4.任何个人或单位不得在平台内散布不良信息，若有发现可联系管理员及时审核，平台将采取封禁处理，严重者将追究其法律责任。',
        '5.平台内机型数据均来源于第三方站点，仅供参考，若有误差请以官方为准。',
        '6.若在使用过程中有任何问题可以通过问题反馈功能提交反馈，请提供详细问题描述，开发者将尽快处理问题。',
        '7.若存在无法解决问题可反馈或联系开发者，邮箱：lin630@vip.qq.com。',
        '8.您的支持是我们最大的动力，最后祝您生活愉快！'
      ]
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
.explain {
  font-size: 16px;
  text-align: left;
  margin: 8% 6% 8% 6%;
}
</style>
