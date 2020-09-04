import { set, isArray, isString, isObject, flatSlots, baseStrategies } from '../../share';

function handleRules(rules, config, formIns) {
  const df = formIns.rules;
  // 默认rules去重
  const hash = {};
  return rules.reduce((cur, i) => {
    let name;
    let options = {};
    // object
    if (isObject(i)) {
      cur.push(i);
      return cur;
    }
    // array
    if (isArray(i)) {
      // eslint-disable-next-line prefer-destructuring
      name = i[0];
      options = i[1] || {};
    }
    // string
    if (isString(i)) {
      name = i;
    }

    if (hash[name] === undefined) {
      const vfn = df[name];
      if (vfn) {
        cur.push(vfn(options, config));
        hash[name] = true;
      } else {
        console.warn(`${name} is not defined in default rules`, df);
      }
    }
    return cur;
  }, []);
}

export function handleItems(config, formIns) {
  // global
  if (formIns.config.globalBeforeHandle) {
    formIns.config.globalBeforeHandle(this, config, set, formIns);
  }

  // handle
  const { key, prop, label, disabled, rules = [], beforeMount = _ => _ } = config;
  const propName = isArray(prop) ? prop[0] : prop;
  const keyName = key || propName;

  // set keys
  set(config, 'itemmap.key', keyName);

  // merge item props
  const finalProps = {
    ...config.itemmap.props,
    prop: propName,
    label,
    rules: handleRules(rules, config, formIns),
  };

  set(config, 'itemmap.props', finalProps);

  if (disabled !== undefined) {
    set(config, 'vuemap.props.disabled', disabled);
  }

  // local beforeMount
  beforeMount && beforeMount(this, config, set, formIns);

  return config;
}

function factory(type) {
  return function factoryForm(h, config, formIns) {
    const { itemmap, slotItem = {} } = handleItems.call(this, config, formIns);
    return (
      <el-form-item class={[`is-${itemmap.key}`]} {...itemmap}>
        {baseStrategies[type].call(this, h, config, formIns)}
        {flatSlots(slotItem)}
      </el-form-item>
    );
  };
}

export default {
  select: factory('select'),
  input: factory('input'),
  switch: factory('switch'),
  upload: factory('upload'),
  checkbox: factory('checkbox'),
  radio: factory('radio'),
  slider: factory('slider'),
  'time-select': factory('time-select'),
  'time-picker': factory('time-picker'),
  'date-picker': factory('date-picker'),
  'checkbox-group': factory('checkbox-group'),
  'radio-group': factory('radio-group'),
  'tree-select': factory('tree-select'),
};
