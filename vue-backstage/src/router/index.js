import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/views/login/Login'
import Home from '@/views/main/Home'
import ManageCenter from '@/views/main/ManageCenter'
import UserAssess from '@/views/main/UserAssess'
import AboutTeam from '@/views/main/AboutTeam'
import DataManage from '@/views/main/modular/DataManage'
import userManage from '@/views/main/modular/userManage'
import Statistics from '@/views/main/modular/Statistics'
import Suggest from '@/views/main/modular/Suggest'

Vue.use(Router)

export default new Router({
  routes: [{
    path: '/',
    name: 'login',
    component: Login
  }, {
    path: '/login',
    name: 'login',
    component: Login
  }, {
    path: '/home',
    name: 'home',
    component: Home,
    redirect: '/manageCenter',
    children: [{
      path: '/manageCenter',
      component: ManageCenter,
      redirect: '/dataManage',
      children: [{
        path: '/dataManage',
        component: DataManage
      },
      {
        path: '/userManage',
        component: userManage
      },
      {
        path: '/statistics',
        component: Statistics
      },
      {
        path: '/suggest',
        component: Suggest
      }
      ]
    },
    {
      path: '/userAssess',
      component: UserAssess
    },
    {
      path: '/aboutTeam',
      component: AboutTeam
    }
    ]
  }]
})
