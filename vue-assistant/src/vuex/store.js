import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
  // 定义状态
  state: {
    isLogin: false,
    userName: '',
    userId: ''
  },

  getters: {
    getUserId (state) {
      return state.userId
    }
  },
  actions: {
    setUserId ({
      commit,
      state
    }, userId) {
      commit('setUserId', userId)
    }
  },
  mutations: {
    setUserId (state, userId) {
      state.userId = userId
    }
  }
})

export default store
