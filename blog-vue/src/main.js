import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'

import mavonEditor from 'mavon-editor'

import 'mavon-editor/dist/css/index.css'
import "./permission"
import axios from "@/axios";
Vue.use(mavonEditor)
Vue.config.productionTip = false
Vue.prototype.$axios = axios

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
