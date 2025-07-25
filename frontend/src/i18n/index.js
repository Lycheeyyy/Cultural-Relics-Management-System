import Vue from 'vue'
import VueI18n from 'vue-i18n'

Vue.use(VueI18n)

const messages = {
  en: require('./locales/en.json'),
  zh: require('./locales/zh.json')
}

export default new VueI18n({
  locale: localStorage.getItem('lang') || 'zh', // 默认语言
  messages
})