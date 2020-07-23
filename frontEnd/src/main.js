import Vue from "vue";
import "./plugins/bootstrap-vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import { BootstrapVue, BootstrapVueIcons } from "bootstrap-vue";
import axios from "axios";
import VueAxios from "vue-axios";
Vue.use(BootstrapVue);
Vue.use(BootstrapVueIcons);
Vue.use(VueAxios, axios);
Vue.config.productionTip = false;
import moment from "moment"; //导入文件
Vue.prototype.$moment = moment; //赋值使用
moment.locale("zh-cn"); //国际化，中文
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
router.beforeEach((to, from, next) => {
  if (to.matched.some(m => m.meta.requireAuth)) {
    // 对路由进行验证
    if (store.state.isLogin) {
      // 已经登陆
      next(); // 正常跳转到你设置好的页面
    } else {
      // 未登录则跳转到登陆界面
      next({ path: "/login" });
    }
  } else {
    next();
  }
});
