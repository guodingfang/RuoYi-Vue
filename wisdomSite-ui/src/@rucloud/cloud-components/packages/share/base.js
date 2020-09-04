import { get } from '@rucloud/cloud-share/dist/utils';
import { deepmerge } from 'deepmerge/dist/umd';
import mixin from './mixin';

export default class Base {
  constructor(module, config, strategies) {
    this.config = config || {};
    this.strategies = strategies || {};
    this.type = module;
  }

  baseSet(data, newData, type) {
    let dataIn = data;
    if (type) {
      dataIn[type] = newData;
    } else {
      dataIn = newData;
    }
  }

  baseGet(data, type, alter = {}) {
    if (type) {
      return get(data, type, alter);
    }
    return data;
  }

  baseMerge(data, newData, type) {
    const dataIn = data;
    if (type) {
      data[type] = deepmerge(data[type], newData);
    } else {
      dataIn = newData;
    }
  }

  getConfig(type, alter = {}) {
    return this.baseGet(this.config, type, alter);
  }

  setConfig(config, type) {
    this.baseSet(this.config, config, type);
  }

  mergeConfig(config, type) {
    this.baseMerge(this.config, config, type);
  }

  setStrategy(stra, type) {
    this.baseSet(this.strategy, stra, type);
  }

  getStrategy(type, alter = () => {}) {
    return this.baseGet(this.strategies, type, alter);
  }

  // 挂在mixin
  mixinConfig(data, type) {
    return mixin.apply(this, [data, this.getConfig(type)]);
  }

  // 挂在mixin
  mixinStrategy(data, type) {
    return mixin.apply(this, [data, this.getStrategy(type)]);
  }
}
