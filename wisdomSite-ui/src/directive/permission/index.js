import hasRole from './hasRole'
import hasPermi from './hasPermi'
import hasTab from './hasTab'

const install = function(Vue) {
  Vue.directive('hasRole', hasRole)
  Vue.directive('hasPermi', hasPermi)
  Vue.directive('hasTab', hasTab)
}

if (window.Vue) {
  window['hasRole'] = hasRole
  window['hasPermi'] = hasPermi
  window['hasTab'] = hasTab
  Vue.use(install); // eslint-disable-line
}

export default install
