import Vue from 'vue'
import './init';
import '@/styles/index.scss'; // global css
import Cookies from 'js-cookie'

import 'normalize.css/normalize.css' // a modern alternative to CSS resets

import Element from 'element-ui'
import './assets/styles/element-variables.scss'

import '@/assets/styles/index.scss' // global css
import '@/assets/styles/jlkj.scss' // jlkj css
import App from './App'
import store from './store'
import router from './router'
import permission from './directive/permission'

import './assets/icons' // icon
import './permission' // permission control
import { getDicts } from "@/api/system/dict/data";
import { getConfigKey } from "@/api/system/config";
import { parseTime, resetForm, addDateRange, selectDictLabel, download, handleTree } from "@/utils/jlkj";
import Pagination from "@/components/Pagination";
import DStore from './store/dict';
import '@/directive/permission/plugins'
import dayjs from 'dayjs';
Vue.prototype.$dayjs = dayjs;

// 全局方法挂载
Vue.prototype.getDicts = getDicts
Vue.prototype.getConfigKey = getConfigKey
Vue.prototype.parseTime = parseTime
Vue.prototype.resetForm = resetForm
Vue.prototype.addDateRange = addDateRange
Vue.prototype.selectDictLabel = selectDictLabel
Vue.prototype.download = download
Vue.prototype.handleTree = handleTree

Vue.prototype.msgSuccess = function (msg) {
  this.$message({ showClose: true, message: msg, type: "success" });
}

Vue.prototype.msgError = function (msg) {
  this.$message({ showClose: true, message: msg, type: "error" });
}

Vue.prototype.msgInfo = function (msg) {
  this.$message.info(msg);
}

// window.onload = function(win) {
//   let a = document.getElementById('iframeRef').contentWindow.document.getElementById('weather-v2-plugin-h5')
//   console.log(a);
//   console.log(document.getElementById(win).contentWindow.document);
// }
Vue.prototype.mywindow = window

// 全局组件挂载
Vue.component('Pagination', Pagination)

Vue.use(permission)
//全局设置element size
Vue.use(Element, { size: 'small' })
/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */

Vue.use(Element, {
  size: Cookies.get('size') || 'small' // set element-ui default size
})

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  DStore,
  render: h => h(App)
})
