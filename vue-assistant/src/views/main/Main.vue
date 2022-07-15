<template>
  <transition enter-active-class="animated slideInRight">
    <div id='app'>
      <AssistantHeader />
      <!-- 头部文章 -->
      <div class="article-head">
        <van-swipe class="my-swipe"
                   :autoplay="2000"
                   indicator-color="white"
                   style="height:100%;width:99.4%;">
          <van-swipe-item v-for="(articleHeadList, index) in articleHeadList"
                          :key="index"
                          @click="openHeadArticle(index)">
            <div style="height:100%;width:100%;position: relative;">
              <img :src="articleHeadList.article_img"
                   style="height:100%;width:100%">
              <p class="article-head-title">{{articleHeadList.article_title}}</p>
            </div>

          </van-swipe-item>
        </van-swipe>
      </div>

      <div class="article-list">
        <div class="article-list-head">
          <span>手机资讯</span>
        </div>
        <van-pull-refresh v-model="isLoading"
                          success-text="刷新成功"
                          @refresh="onRefresh">
          <transition enter-active-class="animated fadeInRight"
                      leave-active-class="animated flash">
            <div v-if="isShow">
              <div class="article-panel"
                   v-for="(articleList, index) in articleList"
                   :key="index"
                   @click="openArticle(index)">
                <div class="article-img"><img :src="articleList.article_img"></div>
                <div class="article">
                  <p class="article-title">{{ articleList.article_title }}</p>
                  <div class="article-detial">
                    <p>{{ articleList.article_date }}</p>
                    <p>
                      <van-icon name="eye-o" /> {{ articleList.article_view }}</p>
                    <p>
                      <van-icon name="chat-o" /> {{ articleList.article_comment }}</p>
                  </div>

                </div>
              </div>
            </div>
          </transition>
        </van-pull-refresh>

      </div>
    </div>
  </transition>
</template>
<script>
import AssistantHeader from '@/components/Header'
export default {
  name: 'main',
  inject: ['reload'],
  components: {
    AssistantHeader: AssistantHeader
  },
  data () {
    return {
      show: false,
      isShow: true,
      isLoading: false,
      articleHeadList: [],
      articleList: []
    }
  },
  mounted () {
  },
  methods: {
    openHeadArticle (index) {
      this.$router.push({ name: 'ArticleDetail', params: { articleUrl: this.articleHeadList[index].article_url } })
    },
    openArticle (index) {
      this.$router.push({ name: 'ArticleDetail', params: { articleUrl: this.articleList[index].article_url } })
    },
    onRefresh () {
      this.getAllArticle()
      setTimeout(() => {
        this.isLoading = false
        this.isShow = false
        setTimeout(() => {
          this.isShow = true
        }, 200)
      }, 1000)
    },
    // 获取头部文章
    getHeadArticle () {
      this.$axios.post('/getHeadArticle', {
      }).then(data => {
        console.log('获取头部文章')
        if (data.data !== null) {
          this.articleHeadList = data.data
        }
      }).catch(error => {
        console.log(error)
        this.$toast.fail('获取头部文章失败！' + error.status + ',' + error.statusText)
      })
    },
    // 获取所有文章
    getAllArticle () {
      this.$axios.post('/getAllArticle', {
      }).then(data => {
        console.log('获取所有文章')
        if (data.data !== null) {
          this.articleList = data.data
        }
      }).catch(error => {
        console.log(error)
        this.$toast.fail('获取所有文章失败！' + error.status + ',' + error.statusText)
      })
    }
  },
  created () {
    this.getHeadArticle()
    this.getAllArticle()
  }
}
</script>

<style scoped>
/* 750 * 100% */
* {
  margin: 0;
  padding: 0;
}

#app {
  height: auto;
  width: 100%;
  min-height: 1000px;
  background-color: #f8f8f9;
}

/* 头部文章 */
.article-head {
  height: 180px;
  width: 90%;
  text-align: center;
  margin: 0 auto;
  /* background-color: red; */
}

.article-head-title {
  position: absolute;
  bottom: 0;
  left: 0;
  height: 35px;
  line-height: 35px;
  font-size: 16px;
  width: 100%;
  background-color: rgba(0, 0, 0, 0.12);
  text-align: left;
  padding-left: 15px;
}

.my-swipe .van-swipe-item {
  color: #fff;
  font-size: 20px;
  line-height: 150px;
  text-align: center;
  background-color: rgb(255, 255, 255);
}

.article-list {
  height: auto;
  width: 90%;
  text-align: center;
  margin: 20px auto;
  background-color: white;
}

.article-list-head {
  height: 30px;
  width: 90%;
  text-align: center;
  margin: 10px auto;
}

.article-list span {
  padding-top: 8px;
  float: left;
  width: auto;
  height: 100%;
  line-height: 30px;
  font-size: 18px;
  border-bottom: 2px solid#2db7f5;
}
/* 单个文章面板 */
.article-panel {
  width: 100%;
  height: 125px;
  margin: 0 auto;
  border-bottom: 2px solid#dcdee26e;
}
/* 文章图片 */
.article-img {
  float: left;
  /* width: auto;
  height: 80px; */
  width: 34%;
  height: auto;
  margin: 15px 15px;
  background-color: red;
}

.article-img img {
  float: left;
  /* width: auto;
  height: 100%; */
  width: 100%;
  height: auto;
}

.article {
  position: relative;
  float: right;
  /* width: 200px; */
  width: 55%;
  height: 80px;
  margin: 20px 0 0 0;
}

.article-title {
  font-size: 16px;
  text-align: left;
  line-height: 20px;
  margin: 0 15px 0 0;
}

.article-detial {
  font-size: 18px;
  width: 90%;
  margin: 0px 0 0 0px;
}

.article-detial p {
  font-size: 14px;
  float: left;
  width: auto;
  margin: 10px 10px 0 0px;
  color: #515a6e;
  height: 16px;
  line-height: 16px;
}
</style>
