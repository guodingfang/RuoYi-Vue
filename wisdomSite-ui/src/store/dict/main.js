// dict 功能封装
import get from 'lodash/get';
import befMixin from './mixin';
import isArray from 'lodash/isArray';
import isFunction from 'lodash/isFunction';

let Vue;
class DStore {
  constructor(options) {
    this.options = options;
    this.count = {};
    this.cache = {};
    this.refreshContainer = new Set();
    // init
    let { silent } = Vue.config;
    Vue.config.silent = true;
    this._vm = new Vue({
      data: {
        state: {
          COUNT: {},
          CHANGE_FLAG: {},
          COUNT_ALL: 1,
        },
      },
    });
    Vue.config.silent = silent;
    silent = undefined;
  }

  // 动态添加 options为后期动态添加options做准备
  addOption(options) {
    this.options = {
      ...this.options,
      ...options,
    };
  }

  get state() {
    return this._vm.state;
  }

  set state(v) {
    if (process.env.NODE_ENV !== 'production') {
      console.warn('state can not be set directly', v);
    }
  }

  // 获取配置的key值
  getTKey(type) {
    return get(this.options, [type, 'key'], undefined);
  }

  // 获取配置value
  getTVal(type) {
    return get(this.options, [type, 'value'], undefined);
  }

  // 获取配置type => 字典会根据type返回一组数据
  getTType(type) {
    return get(this.options, [type, 'type'], undefined);
  }

  // 刷新
  async refresh(typeArr, isFirst) {
    // 刷新 已经修改的 下拉
    const allTypes = typeArr || Array.from(this.refreshContainer);

    allTypes.forEach(type => {
      if (!this.options[type]) {
        console.warn(`${type} should be added to options`, this.options);
      }
      const { fetch = () => Promise.resolve([]), path = 'data' } = get(
        this.options,
        [type, 'refresh'],
        {}
      );
      if (fetch) {
        const fetchUrl = async() => {
          const re = await fetch();
          this._setData(type, get(re, path, []));
        };
        // 第一次加载
        if (isFirst) {
          fetchUrl();
        } else {
          if (this.refreshContainer.has(type)) {
            fetchUrl();
          }
        }
      }
    });
  }
  // 本地刷新
  localRefresh(typesArg) {
    typesArg.forEach(typeObj => {
      // 本地刷新 select
      const { type, callBack } = typeObj;
      this.cache[type] = callBack(this.cache[type]);
    });
  }
  _setData(type, data) {
    const _this = this;
    if (!isArray(data)) return;
    // 标识是刷新的标识
    this._vm.state.COUNT_ALL++;
    // 当前刷新的标志
    this._vm.state.CHANGE_FLAG = { type, hash: new Date().getTime() };
    // 可以做一个计数器 后期可能会用
    this.count[type] ? this.count[type]++ : (this.count[type] = 1);
    // cache一份操作
    if (this.cache[type] === undefined) {
      Object.defineProperty(this.cache, type, {
        enumerable: true,
        configurable: true,
        set(newValue) {
          // 先设置
          if (newValue) {
            _this._vm.$set(
              _this._vm.state,
              type,
              newValue.map(i => Object.freeze(i))
            );
          }
        },
      });
    }
    // 赋值
    this.cache[type] = data;
    // 赋值数据
    // data 必须是数组
    // this._vm.$set(this._vm.state, type, data.map(i => Object.freeze(i)));

    // 删除 refreshcontainer 中的字段
    if (this.refreshContainer.has(type)) {
      this.refreshContainer.delete(type);
    }
  }

  // get via type
  get(c, inType) {
    const lists = get(this, ['_vm', 'state', c], []);
    const t = this.getTType(c); // 获取type对应的映射字段, 不分类则没有值
    return lists.filter(data => {
      // 没有则返回所有数据
      if (t === undefined) {
        return true;
      }
      return data[t] === inType;
    });
  }

  // value => key
  getKey(type, value, out) {
    if (value === undefined || value === null) {
      return value;
    }
    const keyComputed = this.getTKey(type);
    const valComputed = this.getTVal(type);
    return get(
      this.get(type).filter(item => item[valComputed] === value),
      [0, out || keyComputed],
      null
    );
  }

  // key => value
  getValue(type, key, out) {
    if (key === undefined || key === null) {
      return key;
    }
    const keyComputed = this.getTKey(type);
    const valComputed = this.getTVal(type);
    return get(
      this.get(type).filter(item => item[keyComputed] === key),
      [0, out || valComputed],
      null
    );
  }

  addChange(param) {
    if (isArray(param)) {
      this.refreshContainer = new Set([...this.refreshContainer, ...param]);
    } else {
      this.refreshContainer.add(param);
    }
  }

  // type [key, val] 'param1.param2'（第3个参数为自定义的参数,想拿什么值用,号分割：fParam: [['allUserOrgId'], ['value', 'label'], 'username,des'],）
  _format(arrIn, listsKeyVal, keyVal, extraParam, filterFn, callBack) {
    // 过滤数据
    const arr = isFunction(filterFn) ? filterFn(arrIn) : arrIn;
    const out = [];
    // 源数据 对应的 key value 对应的 name
    const [tKey, tValue] = listsKeyVal;
    const [key, value] = keyVal;
    const exPara = extraParam ? extraParam.split(',') : [];
    arr.forEach(item => {
      const obj = {
        [key]: item[tKey],
        [value]: item[tValue],
      };
      if (exPara.length) {
        exPara.forEach(param => (obj[param] = item[param]));
      }
      // 新增回到函数 用于自定义参数
      callBack && callBack(obj, item);
      out.push(obj);
    });
    return out;
  }

  // expose format
  format(path, keyVal, extraParam, filterFn, callBack) {
    const [type, inType] = path; // ['dict, 'sex'] or ['people']
    const lists = this.get(type, inType);
    // 获取源数据 key value
    const listsKeyVal = [this.getTKey(type), this.getTVal(type)];
    // format 数据
    return this._format(
      lists,
      listsKeyVal,
      keyVal,
      extraParam,
      filterFn,
      callBack
    );
  }
}

function install(_Vue) {
  if (Vue && _Vue === Vue) {
    if (process.env.NODE_ENV !== 'production') {
      console.error(
        '[dStore] already installed. Vue.use(dStore) should be called only once.'
      );
    }
    return;
  }
  Vue = _Vue;
  Vue.mixin({ beforeCreate: befMixin });
}

export default {
  install,
  DStore,
};
