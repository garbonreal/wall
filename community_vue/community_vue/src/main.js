// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Api from '../api/index.js';
import axios from '../node_modules/axios'
import VueAxios from 'vue-axios'
import App from './App'
import router from './router'
import VueRouter from 'vue-router'
import ElementUI from 'element-ui' //element-ui的全部组件
import 'element-ui/lib/theme-chalk/index.css'
import Varall from '@/components/Varall'
Vue.prototype.Varall = Varall;

Vue.config.productionTip = false;
Vue.use(ElementUI);
Vue.prototype.$axios = axios;
Vue.use(VueRouter);
Vue.prototype.$api = Api;
router.beforeEach((to, from, next) => {
  if(to.meta.title){
    document.title = to.meta.title;
  }
  next()
});
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  axios,
  components: { App },
  template: '<App/>'
});


