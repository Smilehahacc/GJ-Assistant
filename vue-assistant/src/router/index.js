import Vue from 'vue'
import Router from 'vue-router'
import Main from '@/views/main/Main'
import Login from '@/views/login/Login'
import ArticleDetail from '@/views/detail/ArticleDetail'
import Search from '@/views/main/Search'
import Recommend from '@/views/main/Recommend'
import Contrast from '@/views/main/Contrast'
import NearbyStore from '@/views/main/NearbyStore'
import Remind from '@/views/main/Remind'
import CameraRanking from '@/views/main/CameraRanking'
import Popular from '@/views/main/Popular'
import Setting from '@/views/other/Setting'
import Feedback from '@/views/other/Feedback'
import Explain from '@/views/other/Explain'
import Test from '@/views/detail/Test'
import ModelDetail from '@/views/detail/ModelDetail'
import EvaluateDetail from '@/views/detail/EvaluateDetail'
import ModelContrastDetail from '@/views/detail/ModelContrastDetail'
import BuyDetail from '@/views/detail/BuyDetail'

Vue.use(Router)

export default new Router({
  routes: [{
    path: '/',
    name: 'Main',
    component: Main
  }, {
    path: '/main',
    name: 'Main',
    component: Main
  }, {
    path: '/login',
    name: 'Login',
    component: Login,
    meta: {
      keepAlive: false
    }
  }, {
    path: '/articleDetail',
    name: 'ArticleDetail',
    component: ArticleDetail
  }, {
    path: '/search',
    name: 'Search',
    component: Search,
    meta: {
      title: '机型查询',
      keepAlive: false
    }
  }, {
    path: '/recommend',
    name: 'Recommend',
    component: Recommend
  }, {
    path: '/contrast',
    name: 'Contrast',
    component: Contrast
  }, {
    path: '/nearbyStore',
    name: 'NearbyStore',
    component: NearbyStore
  }, {
    path: '/remind',
    name: 'Remind',
    component: Remind
  }, {
    path: '/cameraRanking',
    name: 'CameraRanking',
    component: CameraRanking
  }, {
    path: '/popular',
    name: 'Popular',
    component: Popular
  }, {
    path: '/setting',
    name: 'Setting',
    component: Setting
  }, {
    path: '/feedback',
    name: 'Feedback',
    component: Feedback
  }, {
    path: '/explain',
    name: 'Explain',
    component: Explain
  }, {
    path: '/test',
    name: 'Test',
    component: Test
  }, {
    path: '/modelDetail',
    name: 'ModelDetail',
    component: ModelDetail
  }, {
    path: '/evaluateDetail',
    name: 'EvaluateDetail',
    component: EvaluateDetail
  }, {
    path: '/modelContrastDetail',
    name: 'ModelContrastDetail',
    component: ModelContrastDetail
  }, {
    path: '/buyDetail',
    name: 'BuyDetail',
    component: BuyDetail
  }],
  mode: 'history'
})
