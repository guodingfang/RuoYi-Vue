// 前端分页
export function selfPagination(currentPage, total, pageSize) {
  const start = pageSize * (currentPage - 1) <= 0 || pageSize >= total ? 0 : pageSize * (currentPage - 1);
  const end = start + pageSize >= total ? total + 1 : start + pageSize;
  return [start, end];
}
const proPagesizes = [10, 15, 20, 30];
const devPagesizes = [5, 10, 15, 20, 25];
const isPro = process.env.NODE_ENV === 'production';

// default mix
const props = function() {
  return {
    background: true,
    pageSizes: isPro ? proPagesizes : devPagesizes,
    currentPage: 1,
    pageSize: isPro ? proPagesizes[0] : devPagesizes[0],
    total: 0,
    // layout: 'total, sizes, prev, pager, next, jumper',
    layout: 'sizes, prev, pager, next, jumper',
  };
};

const currentChange = function(module, value) {
  try {
    if (module) {
      this.setPage(value, null, null, module.name);
      this[module.fetch]();
    } else {
      this.setPage(value, null, null);
      this[this.paginationSearchName] && this[this.paginationSearchName]();
    }
  } catch (error) {
    console.warn(error);
  }
};

const sizeChange = function(module, size) {
  try {
    if (module) {
      this.setPage(false, false, size, module.name);
      this[module.fetch]();
    } else {
      this.setPage(false, false, size);
      this[this.paginationSearchName] && this[this.paginationSearchName]();
    }
  } catch (error) {
    console.warn(error);
  }
};

const events = function(module) {
  return Object.freeze({
    'current-change': value => {
      currentChange.call(this, module, value);
    },
    'size-change': size => {
      sizeChange.call(this, module, size);
    },
  });
};

const methods = {
  setPage(current, total, pageSize, moduleName, layout) {
    const { props } = moduleName ? this.paginationParam[moduleName] : this.paginationParam;
    props.currentPage = current === 0 ? 0 : current || props.currentPage;
    props.total = total === 0 ? 0 : total || props.total;
    props.pageSize = pageSize === 0 ? 0 : pageSize || props.pageSize;
    props.layout = layout || props.layout
  },
};

// 单个 分页
export default {
  data() {
    return {
      paginationSearchName: 'getData',
      paginationParam: {
        props: props(),
        events: events.call(this),
      },
      tHeight: 0
    };
  },
  methods,
};

// moudles [{name: a, fetch: }]
export function multi(modules) {
  const modulesGen = function() {
    const paginationParam = {};
    for (let i = 0; i < modules.length; i++) {
      const nameIn = modules[i];
      let module;
      if (typeof nameIn !== 'object') {
        module = {
          name: nameIn,
          fetch: `${nameIn}_getData`,
        };
      } else {
        module = nameIn;
      }
      const { name, fetch = `${name}_getData` } = module;
      paginationParam[name] = {
        module: name,
        paginationSearchName: fetch,
        props: props(),
        events: events.call(this, { name, fetch }),
      };
    }
    return paginationParam;
  };
  return {
    data() {
      return {
        paginationParam: modulesGen.call(this),
      };
    },
    methods,
  };
}

import get from 'lodash/get';
export function canPass(selectedItems, config, context) {
  let canPass = false;
  // const isMulti = !!config.reqMulti;
  const isMulti = !!config;
  const selected = selectedItems;
  // 没选择
  if (selected.length === 0) {
    context.$message.warning('请选择需要操作的数据');
    canPass = false;
    return canPass;
  }
  // 多选
  // 多选有值
  if (isMulti) {
    canPass = true;
  } else {
    // 单选多值
    if (selected.length > 1) {
      context.$message.warning('只支持单条操作');
      canPass = false;
    } else {
      // 单选单值
      canPass = true;
    }
  }
  return canPass;
}

/**
 * @param {arr} selectedItems table选择的数据元素
 * @param {obj} config 当前按钮的config
 * @param {fn} reqFn 执行的异步函数
 * @param {this} context 上下文 this
 * @param {string} loadingFlag wrap loading 字段
 */
export function wrapReq(
  selectedItems,
  config,
  reqFn,
  context,
  loadingFlag = 'wrapLoading'
) {
  return async() => {
    if (context.SINGLE_OPERATE === true) {
      if (!get(selectedItems, '0', undefined)) { return context.$message.warning('数据不能为空'); }
    } else {
      if (!canPass(selectedItems, config, context)) return;
    }
    if (context[loadingFlag] !== undefined) {
      context[loadingFlag] = true;
    } else {
      console.warn(`需要指定 ${loadingFlag}`);
      return;
    }
    // 1. 验证 2. loading 3. 请求
    reqFn && (await reqFn()); // 一般会有异步的请求
    context[loadingFlag] = false;
  };
}
