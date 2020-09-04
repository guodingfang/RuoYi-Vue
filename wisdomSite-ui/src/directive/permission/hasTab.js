 /**
 * 操作权限处理
 * Copyright (c) 2019 jlkj
 */

import store from '@/store'
import isArray from 'lodash/isArray';

export default {
  inserted(el, binding, vnode) {
    const { value } = binding
    const all_permission = "*:*:*";
    const permissions = store.getters && store.getters.permissions
    console.log(permissions);

    if (value && value instanceof Array && value.length > 0) {
      const permissionFlag = value
      const flag = value[1]
      console.log(flag);

      const hasPermissions = permissions.some(permission => {
        return all_permission === permission || permissionFlag.includes(permission)
      })
      if (!hasPermissions) {
        el.parentNode && el.parentNode.removeChild(el)
      }
    } else {
      throw new Error(`请设置操作权限标签值`)
    }
  }
}
