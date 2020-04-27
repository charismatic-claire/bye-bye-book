import Vue from 'vue'
import BootstrapVue from 'bootstrap-vue'
import VueRouter from 'vue-router'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import App from './App.vue'
import store from './store'
import routes from './routes'

// Configure Vue
Vue.config.productionTip = false
Vue.config.devtools = true
Vue.use(BootstrapVue)
Vue.use(VueRouter)

// Instantiate router
const router = new VueRouter({
  routes,
  mode: 'history'
})

// Vue instance
new Vue({
  render: h => h(App),
  store,    // Inject Vuex store into Vue instance
  router    // Inject Router into Vue instance
}).$mount('#app')
