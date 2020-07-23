import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    // isLogin: window.localStorage.getItem("isLogin" || "[]") == null ? "" : true,
    // user: {
    //   userName:
    //     window.localStorage.getItem("user" || "[]") == null
    //       ? ""
    //       : JSON.parse(window.localStorage.getItem("user" || "[]")).userName,
    //   password:
    //     window.localStorage.getItem("user" || "[]") == null
    //       ? ""
    //       : JSON.parse(window.localStorage.getItem("user" || "[]")).password,

    //   userId:
    //     window.localStorage.getItem("user" || "[]") == null
    //       ? ""
    //       : JSON.parse(window.localStorage.getItem("user" || "[]")).userId
    // },
    isLogin: JSON.parse(sessionStorage.getItem("isLogin")) || false,
    user: JSON.parse(sessionStorage.getItem("user")) || {},
    blocks: [],
    scroll: 0,
    maxPageNum: 3,
    keyWord: ""
  },
  mutations: {
    login(state, user) {
      state.isLogin = true;
      state.user = user;
      sessionStorage.setItem("user", JSON.stringify(user));
      sessionStorage.setItem("isLogin", true);
    },
    scroll(state, curr) {
      state.scroll = curr;
    },
    commitBlock(state, block) {
      state.blocks.push(block);
    },
    exit(state) {
      state.isLogin = false;
      state.user = {};
      window.localStorage.setItem("user", null);
      window.localStorage.setItem("isLogin", false);
    }
  },
  actions: {},
  modules: {},
  getters: {
    getScroll: state => {
      return state.scroll;
    },
    getLogState: state => {
      return state.isLogin;
    },
    getName: state => {
      return state.user.userName;
    },
    getIcon: state => {
      return state.user.iconUrl;
    },
    getId: state => {
      return state.user.userId;
    },
    getBlocks: state => {
      return state.blocks;
    }
  }
});
