/* eslint-disable prefer-const */
import { isArray, get, set } from './rucloud-share';

/**
 * 添加  placeholder and maxlength
 * @param {*} config
 */
export function beforeMount(config, ins) {
  const useBaseTypes = ['search'].indexOf(ins.type) > -1;

  if (useBaseTypes) {
    // global handle
    if (ins.config.globalBeforeHandle) {
      ins.config.globalBeforeHandle(this, config, set, ins);
    }
  }

  let {
    type,
    label,
    vuemap,
    maxlength,
    beforeMount = _ => _,
    belongAttr = false, // 手动确定是增加到 attrs
  } = config;
  // type which placeHolder、 maxlength should be added to attrs
  const attsContainer = Object.keys(get(ins, 'config.IN_ATTR_MAP', {}));
  const belongAttrs = belongAttr || attsContainer.indexOf(type) > -1;

  // add 最大宽度
  if (maxlength !== undefined && belongAttrs) {
    set(vuemap, 'attrs.maxlength', maxlength);
  }

  const emptyLabel = [undefined, null];
  label = emptyLabel.indexOf(label) === -1 ? label : '';
  // 默认placeHolder生成函数
  const defaultPl = config => (type === 'select' ? `请选择${label}` : `请输入${label}`);

  // placeholder
  const setPl = () => {
    const genPlaceHolder = ins.config.genPlaceHolder || defaultPl;
    const plText = genPlaceHolder();
    const keyName = belongAttrs ? 'attrs' : 'props';
    const path = [keyName, 'placeholder'];
    const pl = get(vuemap, path, undefined);
    if (pl === undefined) {
      set(vuemap, path, plText);
    }
  };
  setPl();

  // vuemap set lifecycle search
  if (useBaseTypes) {
    beforeMount && beforeMount(this, config, set, ins);
  }
}

/**
 * 平铺 slots
 * @param {*} slots
 */
export function flatSlots(slots) {
  const slotsOut = [];
  Object.keys(slots).forEach(name => {
    const slot = slots[name];
    // 验证组件的 slot 属性
    /* istanbul ignore else */
    if (get(slot, 'data.slot', false) === name || name === 'default') {
      slotsOut.push(slot);
    } else {
      console.warn(
        `${name} slot need named specificly like \n<template slot="${name}">\n ${slot} \n</template>`
      );
    }
  });
  return slotsOut;
}

export function preDecon(h, config) {
  const { vuemap, prop, slotGen = {}, selfUpdate = false, optionMeta } = config;
  const { valuesIn } = this;
  const propName = isArray(prop) ? prop[0] : prop;

  // optionMeta选项处理
  let optionAlias = ['value', 'label'];
  let optionStore = this;
  let optionPath = ['options', propName];

  if (isArray(optionMeta)) {
    optionAlias[0] = optionMeta[0] || 'value';
    optionAlias[1] = optionMeta[1] || 'label';
  }

  if (isArray(get(config, 'optionMeta.alias'))) {
    optionAlias[0] = optionMeta.alias[0] || 'value';
    optionAlias[1] = optionMeta.alias[1] || 'label';
  }

  if (get(config, 'optionMeta.store')) {
    optionStore = optionMeta.store;
  }

  if (get(config, 'optionMeta.path')) {
    optionPath = optionMeta.path;
  }

  return {
    vuemap,
    propName,
    valuesIn,
    slotGen,
    selfUpdate,
    optionMeta: {
      alias: optionAlias,
      store: optionStore,
      path: optionPath,
    },
  };
}

/**
 * element select
 * @param {e} h
 * @param {*} config
 */
function select(h, config, ins) {
  beforeMount.call(this, config, ins);
  let { vuemap, propName, slotGen = {}, valuesIn, optionMeta } = preDecon.call(
    this,
    h,
    config
  );

  const opKey = optionMeta.alias[0];
  const opVal = optionMeta.alias[1];

  slotGen = {
    default: get(optionMeta.store, optionMeta.path, []).map(o => (
      <el-option value={o[opKey]} label={o[opVal]} key={o[opKey]} />
    )),
    ...slotGen,
  };

  return (
    <el-select vModel={valuesIn[propName]} {...vuemap}>
      {flatSlots(slotGen)}
    </el-select>
  );
}

/**
 * checkbox-group
 * @param {*} h
 * @param {*} config
 */
function checkboxGroup(h, config, ins) {
  beforeMount.call(this, config, ins);
  let { vuemap, propName, slotGen = {}, valuesIn, optionMeta } = preDecon.call(
    this,
    h,
    config
  );
  const opKey = optionMeta.alias[0];
  const opVal = optionMeta.alias[1];
  // 合并slots
  slotGen = {
    default: get(optionMeta.store, optionMeta.path, []).map(o => (
      <el-checkbox label={o[opKey]} name={propName}>
        {o[opVal]}
      </el-checkbox>
    )),
    ...slotGen,
  };

  return (
    <el-checkbox-group vModel={valuesIn[propName]} {...vuemap}>
      {flatSlots(slotGen)}
    </el-checkbox-group>
  );
}

function radioGroup(h, config, ins) {
  beforeMount.call(this, config, ins);
  let { vuemap, propName, slotGen = {}, valuesIn, optionMeta } = preDecon.call(
    this,
    h,
    config
  );
  const opKey = optionMeta.alias[0];
  const opVal = optionMeta.alias[1];
  // 合并slots
  slotGen = {
    default: get(optionMeta.store, optionMeta.path, []).map(o => (
      <el-radio label={o[opKey]}>{o[opVal]}</el-radio>
    )),
    ...slotGen,
  };

  return (
    <el-radio-group vModel={valuesIn[propName]} {...vuemap}>
      {flatSlots(slotGen)}
    </el-radio-group>
  );
}

/**
 * element input
 * @param {e} h
 * @param {*} config
 */
function input(h, config, ins) {
  beforeMount.call(this, config, ins);
  let { vuemap, propName, slotGen, valuesIn } = preDecon.call(this, h, config);
  return (
    <el-input vModel={valuesIn[propName]} {...vuemap}>
      {flatSlots(slotGen)}
    </el-input>
  );
}

/**
 * element timerSelect
 * @param {e} h
 * @param {*} config
 */
function timeSelect(h, config, ins) {
  beforeMount.call(this, config, ins);
  let { vuemap, propName, slotGen, valuesIn } = preDecon.call(this, h, config);
  return (
    <el-time-select vModel={valuesIn[propName]} {...vuemap}>
      {flatSlots(slotGen)}
    </el-time-select>
  );
}

/**
 * element timePicker
 * @param {e} h
 * @param {*} config
 */
function timePicker(h, config, ins) {
  beforeMount.call(this, config, ins);
  let { vuemap, propName, slotGen, valuesIn } = preDecon.call(this, h, config);
  return (
    <el-time-picker vModel={valuesIn[propName]} {...vuemap}>
      {flatSlots(slotGen)}
    </el-time-picker>
  );
}

/**
 * element datePicker
 * @param {e} h
 * @param {*} config
 */
function datePicker(h, config, ins) {
  beforeMount.call(this, config, ins);
  let { vuemap, propName, slotGen, valuesIn } = preDecon.call(this, h, config);
  return (
    <el-date-picker vModel={valuesIn[propName]} {...vuemap}>
      {flatSlots(slotGen)}
    </el-date-picker>
  );
}

/**
 * element switch
 * @param {e} h
 * @param {*} config
 */
function elSwitch(h, config, ins) {
  beforeMount.call(this, config, ins);
  let { vuemap, propName, slotGen, valuesIn } = preDecon.call(this, h, config);
  return (
    <el-switch vModel={valuesIn[propName]} {...vuemap}>
      {flatSlots(slotGen)}
    </el-switch>
  );
}

/**
 * vue-upload-component
 * @param {*} h
 * @param {*} config
 */
function upload(h, config, ins) {
  const { getRef = () => {}, disabled = false } = config;
  let { vuemap, propName, slotGen, valuesIn } = preDecon.call(this, h, config);
  slotGen = {
    default: (
      <el-button type="primary" icon="el-icon-plus" disabled={disabled}>
        选择文件
      </el-button>
    ),
    ...slotGen,
  };
  return (
    <file-upload
      vRcRef={c => {
        getRef(c, this);
      }}
      vModel={valuesIn[propName]}
      {...vuemap}
    >
      {flatSlots(slotGen)}
    </file-upload>
  );
}

function checkbox(h, config, ins) {
  beforeMount.call(this, config, ins);
  let { vuemap, propName, slotGen, valuesIn } = preDecon.call(this, h, config);
  return (
    <el-checkbox vModel={valuesIn[propName]} {...vuemap}>
      {flatSlots(slotGen)}
    </el-checkbox>
  );
}

export default {
  select,
  input,
  upload,
  checkbox,
  switch: elSwitch,
  'time-select': timeSelect,
  'time-picker': timePicker,
  'date-picker': datePicker,
  'checkbox-group': checkboxGroup,
  'radio-group': radioGroup,
};
