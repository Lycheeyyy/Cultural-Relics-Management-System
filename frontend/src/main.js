//import Vue from 'vue'
//import App from './App.vue'
//import router from './router'
//import ElementUI from 'element-ui'
//import 'element-ui/lib/theme-chalk/index.css'
//import '@/assets/global.css'
//import SlideVerify from 'vue-monoplasty-slide-verify'
//
//
//import VueI18n from 'vue-i18n'
//
//
//Vue.use(VueI18n) // 必须先调用
//
//const i18n = new VueI18n({
//  locale: localStorage.getItem('lang') || 'zh',
//  messages: {
//    zh: require('./i18n/locales/zh.json'),
//    en: require('./i18n/locales/en.json')
//  }
//})
//
//new Vue({
//  router,
//  i18n, // 关键注入点
//  render: h => h(App)
//}).$mount('#app')
//
//Vue.use(SlideVerify)
//
//Vue.config.productionTip = false
//Vue.use(ElementUI,{size:'small'});
//
//new Vue({
//  router,
//  render: h => h(App)
//}).$mount('#app')
//

import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import '@/assets/global.css'
import SlideVerify from 'vue-monoplasty-slide-verify'
import VueI18n from 'vue-i18n'

// 1. 初始化插件
Vue.use(VueI18n)
Vue.use(SlideVerify)

// 2. 创建 i18n 实例
const i18n = new VueI18n({
  locale: localStorage.getItem('lang') || 'zh',
  messages: {
    zh: require('./i18n/locales/zh.json'),
    en: require('./i18n/locales/en.json')
  },
  silentTranslationWarn: true // 禁用警告
})

// 3. 初始化ElementUI（必须在i18n之后）
Vue.use(ElementUI, {
  size: 'small',
  i18n: (key, value) => i18n.t(key, value)  // 关键集成点
})

Vue.config.productionTip = false

// 4. 创建唯一的Vue实例（包含所有注入）
new Vue({
  router,
  i18n,  // 确保这里注入
  render: h => h(App)
}).$mount('#app')