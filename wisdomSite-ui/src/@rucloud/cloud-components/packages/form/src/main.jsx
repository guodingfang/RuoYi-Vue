import {
  get,
  set,
  Base,
  mixin,
  isArray,
  flatSlots,
  setHelper,
  isFunction,
  isEmptyObject,
} from '../../share';

import rulesDefault from './rules';
import configDefault from './config';
import strategiesDefault, { handleItems } from './strategies';
import SelfItem from '../../share/self';

import './form.scss';

const name = 'RcForm';

class Form extends Base {
  static initIns;

  static _isFirst = true;

  static create(configNew) {
    const { config = {}, strategies = {}, rules = {} } = configNew;
    const ins = this.initIns;

    return new Form(
      mixin(config, ins.config),
      mixin(strategies, ins.strategies),
      mixin(rules, ins.rules)
    );
  }

  constructor(config, strategies, rules) {
    super('form', config, strategies);
    this.rules = rules;
    if (Form._isFirst === true) {
      Form.initIns = this;
      Form._isFirst = false;
      this.isRoot = true;
    }
  }
}

function RcFormFn(formIns) {
  return {
    name,

    components: {
      SelfItem,
    },

    /**
     * provider 设置 ref 依赖 vue-ref
     */
    inject: {
      setFormRef: {
        default: () => () => {},
      },
    },

    props: {
      /**
       * 设置的form 实例的 ref name
       */
      refName: {
        type: String,
        default: 'formRef',
      },

      /**
       * el-form 配置
       */
      formConfig: {
        type: Function,
        default: () => ({}),
      },

      /**
       * form-item 配置
       */
      items: {
        type: Function,
        default: h => [],
      },

      /**
       * select 下拉，checkbox-group, radio-group 配置
       */
      options: {
        type: Object,
        default: () => ({}),
      },

      /**
       * 返回searchIns实例
       */
      insConfig: {
        type: Object,
        default: () => ({}),
      },

      /**
       * setValues
       */
      setValue: {
        type: Object,
        default: () => ({}),
      },

      /**
       * set-value是否严格按照初始values合并
       */
      strictMerge: {
        type: Boolean,
        default: false,
      },

      /**
       * 不允许自动合并setValue
       */
      disableAutoMerge: {
        type: Boolean,
        default: false,
      },

      /**
       * extra components
       */
      extraComponents: {
        type: Object,
        default: () => ({}),
      },

      /**
       * get ref
       */
      getRef: {
        type: Function,
        default: () => {},
      },
    },

    data() {
      this.formIns = isEmptyObject(this.insConfig)
        ? formIns
        : Form.create(this.insConfig);

      this.valuesHooks = {}; // 在某个阶段进行某个操作

      return {
        valuesIn: {},
      };
    },

    created() {
      // install components
      this.installComponents();
    },

    watch: {
      items: {
        handler(val) {
          this.initValues(this.validConfig(val));
        },
        immediate: true,
      },
      setValue: {
        handler(val) {
          if (this.disableAutoMerge) return;
          this.mergeValue(val);
        },
        deep: true,
      },
    },

    mounted() {
      if (Object.keys(this.setValue).length) {
        if (this.disableAutoMerge) return;
        this.mergeValue(this.setValue);
      }

      this.triggerHooks('mounted', this.valuesIn);
    },

    methods: {
      /**
       * config 类型判断
       * @param {*} configFn
       * @param {*} name
       */
      validConfig(configFn, name) {
        let config = [];
        if (isFunction(configFn)) {
          config = configFn(this.$createElement, this);
        } else {
          console.error(`${name} config should pass a function which return an array`);
        }
        return config;
      },

      /**
       * 动态 配置的 动态更新
       * 参看高级查询 value 字段
       */

      addBaseConfig(items) {
        this.initValues(items, true);
      },

      /**
       * 根据config 初始化valuesIn
       * @param {*} config
       */
      initValues(config, updateBaseConfig) {
        const out = {};
        const depHooks = (prop, hKey, hooks, co) => {
          // 有hooks就收集
          // key -> hKey -> prop -> error
          if (hooks) {
            if (hKey) {
              this.valuesHooks[hKey] = hooks;
              hooks._baseConfig = co;
            } else {
              // eslint-disable-next-line no-lonely-if
              if (prop) {
                this.valuesHooks[prop] = hooks;
                hooks._baseConfig = co;
              } else {
                console.error('hooks need hKey or prop');
              }
            }
          }
        };

        const recursive = config => {
          config.forEach(co => {
            const { prop, propGen, hooks, hKey, type, dynamicConfig } = co;

            if (type === 'wrap') {
              recursive(co.items);
            } else {
              if (updateBaseConfig && !dynamicConfig) return;
              if (prop) {
                if (isArray(prop)) {
                  const [name, value] = prop;
                  out[name] = value;
                  depHooks(name, hKey, hooks, co);
                } else {
                  depHooks(prop, hKey, hooks, co);
                  out[prop] = undefined;
                }
              } else {
                depHooks(prop, hKey, hooks);
              }
              /**
               * 额外加入一些 valuesIn的参数
               */
              if (isFunction(propGen)) {
                propGen(out);
              }
            }
          });
        };
        recursive(config);
        /**
         * 动态 配置的 动态更新
         */
        if (!updateBaseConfig) {
          this.valuesIn = out;
        }
      },

      /**
       * sometimes we want use extra components in slots, so,
       * we can dynamicly registe components to rcForm constructor
       */
      installComponents() {
        // eslint-disable-next-line prefer-destructuring
        const components = this.constructor.options.components;
        Object.keys(this.extraComponents).forEach(name => {
          const curCom = this.extraComponents[name];
          if (!components[name]) {
            components[name] = curCom;
          }
        });
      },

      /**
       * 管理refs
       * @param {*} c
       */
      manageRefs(c) {
        this.setFormRef(this.refName, c);
        this.getRef(c);
        this.formRef = c; // 增加自身 form ref 引用
      },

      /**
       * 合并参数
       * @param {*} val
       */
      mergeValue(val) {
        const keys = Object.keys(this.valuesIn);
        Object.keys(val).forEach(key => {
          if (this.strictMerge === true) {
            if (keys.indexOf(key) > -1) {
              this.valuesIn[key] = val[key];
            }
          } else {
            // 浅拷贝
            this.valuesIn = Object.assign(this.valuesIn, val);
          }
        });
      },

      /**
       * 获取values
       */
      getValue() {
        const values = { ...this.valuesIn };
        this.triggerHooks('beforeGetValue', values);
        return values;
      },

      /**
       * 触发hooks
       */
      triggerHooks(type, ...args) {
        const keys = Object.keys(this.valuesHooks);
        keys.forEach(name => {
          if (this.valuesHooks[name][type]) {
            // 重新生成args
            const args_c = [...args, this.valuesHooks[name]._baseConfig];
            this.valuesHooks[name][type].apply(this, args_c);
          }
        });
      },
    },

    render() {
      // TODO 性能问题需要考虑
      const formConfig = this.validConfig(this.formConfig, 'form-config');
      const items = this.validConfig(this.items, 'form-items');
      /**
       * 参看高级查询动态props 需要动态更新基础配置
       */
      this.addBaseConfig(items);
      mixin(formConfig, this.formIns.getConfig('EL_FORM', {}));
      set(formConfig, 'props.model', this.valuesIn);
      const renderContent = items => {
        return items
          .filter(i => [undefined, false, '', null, 0].indexOf(i.hide) > -1)
          .map((config, index) => {
            let node = '';
            const { type, render, itemmap, vuemap } = config;

            if (render) {
              // eslint-disable-next-line no-undef
              return (node = render(h, config, this));
            }

            /**
             * 包裹元素
             */
            if (type === 'wrap') {
              const { items = [], key } = config;
              if (key === undefined) {
                console.error(`type 'wrap' need key`);
              }
              return (node = (
                <div class={[`is-${key || index}`]} {...itemmap}>
                  {renderContent(items)}
                </div>
              ));
            }

            setHelper(
              [itemmap, vuemap, get(vuemap, 'props', undefined)],
              [
                () => set(config, 'itemmap', {}),
                () => set(config, 'vuemap', {}),
                () => set(config, 'vuemap.props', {}),
              ]
            );

            if (type) {
              // TODO
              mixin(config.vuemap, this.formIns.getConfig(type, {}));
              node = this.formIns
                .getStrategy(type)
                // eslint-disable-next-line no-undef
                .call(this, h, config, this.formIns, { handleItems, flatSlots });
            } else {
              console.error('type params is required');
            }

            return node;
          });
      };

      return (
        <div class="rc-form">
          <el-form
            {...formConfig}
            vRcRef={c => {
              this.manageRefs(c);
            }}
          >
            {renderContent(items)}
          </el-form>
        </div>
      );
    },
  };
}

function RcForm(configIn = {}) {
  // 平台全局配置
  const configProject = get(configIn, 'config.form', {});
  const strategiesProject = get(configIn, 'strategies.form', {});
  const rulesProject = get(configIn, 'rules.form', {});

  mixin(configProject, configDefault);
  mixin(strategiesProject, strategiesDefault);
  mixin(rulesProject, rulesDefault);

  return RcFormFn(new Form(configProject, strategiesProject, rulesProject));
}

RcForm.cname = name;

export default RcForm;
