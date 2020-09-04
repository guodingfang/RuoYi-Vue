import { isObject } from '@rucloud/cloud-share/dist/utils';

function isNotObject(x) {
  return !isObject && x !== null && x !== undefined;
}
/**
 * 将默认配置 合并到当前配置 以当前策略为主
 * @param {*} cur 传入配置
 * @param {*} def 默认配置
 */
function mixin(cur, def) {
  const keys = Object.keys(def);
  let len = keys.length;

  while (len--) {
    const key = keys[len];
    if (def.hasOwnProperty(key)) {
      // 没有值 直接赋值
      if (cur[key] === undefined) {
        if (isObject(def[key])) {
          cur[key] = {};
          // 深拷贝
          mixin(cur[key], def[key]);
          continue;
        } else {
          cur[key] = def[key];
        }
        continue;
      }

      // 为null置空
      if (cur[key] && def[key] === null) {
        cur[key] = null;
        continue;
      }

      // object
      if (isObject(cur[key]) && isObject(def[key])) {
        mixin(cur[key], def[key]);
      } else {
        continue;
      }
    }
  }
  return cur;
}

export default mixin;
