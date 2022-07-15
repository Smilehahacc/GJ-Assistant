<template>
  <transition enter-active-class="animated slideInRight">
    <div id='app'>
      <!-- 导航栏 -->
      <van-sticky>
        <van-nav-bar title="问题反馈"
                     left-text="返回"
                     left-arrow
                     style="height: 100%;line-height: 60px"
                     @click-left="onClickLeft()">
        </van-nav-bar>
      </van-sticky>
      <!-- 问题反馈提示文字 -->
      <div class="tag">
        <van-tag v-if="isShowTag"
                 color="rgba(64, 169, 255, 0.16)"
                 text-color="#409EFF"
                 closeable
                 @close="closeTag()"
                 size="large">为了更快解决您的问题，请在发生问题时或再次遇到问题时，立即提交反馈。</van-tag>
      </div>
      <!-- 问题输入区域 -->
      <div class="feedback-input">
        <van-field v-model="feedbackResume"
                   clearable
                   maxlength="32"
                   style="background-color: #f5f8fd;margin: 0 0 4% 0"
                   placeholder="请简述您遇到的问题" />
        <van-field v-model="feedbackDetail"
                   clearable
                   rows="4"
                   autosize
                   type="textarea"
                   maxlength="512"
                   show-word-limit
                   style="background-color: #f5f8fd"
                   placeholder="请详细描述您遇到的问题" />

      </div>
      <!-- 选项 -->
      <div class="feedback-option">
        <van-cell title="选择模块"
                  is-link
                  :value="feedbackOption"
                  @click="isShowOption = true" />
        <van-action-sheet v-model="isShowOption"
                          :actions="actions"
                          cancel-text="取消"
                          description="请选择问题出现模块"
                          @select="onSelect"
                          @cancel="onCancel" />
        <van-cell title="问题出现概率"
                  is-link
                  :value="feedbackOption1"
                  @click="isShowOption1 = true" />
        <van-action-sheet v-model="isShowOption1"
                          :actions="actions1"
                          cancel-text="取消"
                          description="请选择问题出现概率"
                          @select="onSelect1"
                          @cancel="onCancel" />
        <van-cell title="问题发生时间"
                  is-link
                  :value="feedbackOption2"
                  @click="isShowOption2 = true" />
        <van-action-sheet v-model="isShowOption2"
                          description="请选择问题发生时间">
          <van-datetime-picker :v-model="formartDate(feedbackOption2)"
                               type="date"
                               @cancel="onCancel"
                               @confirm="confirmDate"
                               :min-date="minDate"
                               :max-date="maxDate" />
        </van-action-sheet>
      </div>
      <!-- 反馈用户信息填写 -->
      <div class="feedback-user">
        <van-field v-model="feedbackUser"
                   rows="1"
                   type="text"
                   style="background-color: #f5f8fd"
                   placeholder="手机号码/邮箱" />

      </div>
      <!-- 反馈提交按钮 -->
      <div>
        <van-button class="submit-button"
                    @click="submitFeedback()">提交反馈</van-button>
      </div>

    </div>
  </transition>
</template>
<script>
export default {
  name: 'feedback',
  components: {
  },
  data () {
    return {
      isShowTag: true,
      feedbackResume: '',
      feedbackDetail: '',
      feedbackUser: '',
      isShowOption: false,
      isShowOption1: false,
      isShowOption2: false,
      feedbackOption: '必填',
      feedbackOption1: '必填',
      feedbackOption2: '必填',
      dateOption: '',
      minDate: new Date(2020, 0, 1),
      maxDate: new Date(2025, 10, 1),
      actions: [
        { name: '手机资讯' },
        { name: '靓机查询' },
        { name: '新机推荐' },
        { name: '机型对比' },
        { name: '附近门店' },
        { name: '降价提醒' },
        { name: 'Dxo相机排行' },
        { name: '站内热点' },
        { name: '用户中心' },
        { name: '其他问题', subname: '请尽量详细描述' }
      ],
      actions1: [
        { name: '每次都出现' },
        { name: '大概率出现' },
        { name: '偶尔出现' },
        { name: '目前仅本次出现' }
      ]
    }
  },
  methods: {
    // 返回上一级
    onClickLeft () {
      this.$router.go(-1)
    },
    // 关闭提示标签
    closeTag () {
      this.isShowTag = false
    },
    onCancel () {
      this.isShowOption = false
      this.isShowOption1 = false
      this.isShowOption2 = false
    },
    onSelect (item) {
      this.isShowOption = false
      this.feedbackOption = item.name
    },
    onSelect1 (item) {
      this.isShowOption1 = false
      this.feedbackOption1 = item.name
    },
    confirmDate (value) {
      this.isShowOption2 = false
      var t = value.getTime()
      t = parseInt(t / 1000)
      this.dateOption = t
      this.feedbackOption2 = this.formartDate(t)
    },
    // 提交反馈
    submitFeedback () {
      if (this.feedbackResume === '' || this.feedbackDetail === '') {
        this.$toast.fail('请输入详细问题描述')
      } else if (this.feedbackOption === '必填' || this.feedbackOption1 === '必填') {
        this.$toast.fail('请提供完整的反馈条件')
      } else if (this.dateOption === '') {
        this.$toast.fail('请选择问题发生时间')
      } else if (this.feedbackUser === '') {
        this.$toast.fail('请输入您的联系方式')
      } else {
        this.$axios.post('/newFeedback', {
          feedback_resume: this.feedbackResume,
          feedback_detail: this.feedbackDetail,
          feedback_modular: this.feedbackOption,
          feedback_frequency: this.feedbackOption1,
          feedback_date: this.dateOption,
          feedback_user: this.feedbackUser
        }).then(response => {
          if (response.data === 'SUCCESS') {
            this.$toast.success('提交反馈成功！')
            this.cleanInput()
          }
        }).catch(error => {
          console.log(error)
          this.$toast.fail('提交反馈失败！' + error.status + ',' + error.statusText)
        })
      }
    },
    // 清空输入
    cleanInput () {
      this.feedbackResume = ''
      this.feedbackDetail = ''
      this.feedbackOption = '必填'
      this.feedbackOption1 = '必填'
      this.feedbackOption2 = '必填'
      this.feedbackUser = ''
      this.dateOption = ''
    },
    // 时间戳转换
    formartDate (param) {
      var time = new Date(param * 1000)
      var y = time.getFullYear()
      var m = time.getMonth() + 1
      var d = time.getDate()
      return y + '-' + this.add0(m) + '-' + this.add0(d) + ' '
    },
    add0 (m) {
      return m < 10 ? '0' + m : m
    }
  },
  created () {
  }
}
</script>
<style scoped>
/* 问题反馈提示信息*/
.tag {
  text-align: left;
  margin: 0% 4% 4% 4%;
}
/* 问题反馈输入框 */
.feedback-input {
  margin: 0% 4% 4% 4%;
}
/* 问题反馈选择框 */
.feedback-option {
  text-align: left;
  margin: 0 0 0 2%;
}
.feedback-user {
  text-align: left;
  margin: 4% 4% 4% 4%;
}
.submit-button {
  height: 40px;
  line-height: 40px;
  text-align: center;
  width: 76%;
  font-size: 16px;
  color: white;
  margin: 4% 0 0 0;
  border-radius: 18px;
  background-color: #2d8cf0;
}
</style>
