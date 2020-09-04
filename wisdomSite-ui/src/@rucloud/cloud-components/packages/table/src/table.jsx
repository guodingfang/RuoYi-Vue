/**
 * 公共table封装
 */
/* eslint-disable */
import defaultBtns from './defaultBtns';
import dConfig, { ASSETS_PROPS } from './defaultTable';
import tableSetting from './table-setting';
import get from 'lodash/get';
import debounce from 'lodash/debounce';
import cloneDeep from 'lodash/cloneDeep';
// import { SUC_CODE } from 'erp_share/http/response';
// import RcPagination from './../pagination';
const SUC_CODE = 0

import './table.scss';

export function isUndef(v) {
  return v === undefined || v === null;
}
const name = 'RcTable';

const RcTableFn = config => ({
// export default {
  name: 'rc-table',
  components: {
    tableSetting,
    // RcPagination
  },
  props: {
    colSetting: {
      type: Object,
      default: () => {
        return {};
      },
    },
    // 操作区 table + 分页去 loading
    wrapLoading: {
      type: Boolean,
      default: false,
    },
    // table区 loading
    tableLoading: {
      type: Boolean,
      default: false,
    },
    // 用于区分数据的唯一性 flag 默认是 id
    uniqFlag: {
      type: [Number, String],
      default: 'fid',
    },
    // 当前选中的数据
    selectedItem: {
      type: Array,
      default() {
        return [];
      },
    },
    // 标记的的数据, 当前页面有的数据 默认 选中
    markItems: {
      type: Array,
      default() {
        return [];
      },
    },
    // 是否显示操作栏
    operate: {
      type: Boolean,
      default: true,
    },
    // 是否显示分页
    pagination: {
      type: Boolean,
      default: true,
    },
    // 是否显示 table
    table: {
      type: Boolean,
      default: true,
    },
    // table 数据
    tableData: {
      type: Array,
      default() {
        return [];
      },
    },
    // table 所有config 参见element UI
    tableConfig: {
      type: Object,
      default() {
        return {};
      },
    },
    // 列配置 参照 element UI所有配置
    colConfig: {
      type: Array,
      default() {
        return [];
      },
    },
    // 操作栏配置 默认是 button 也可自定义 render函数
    operateConfig: {
      type: Array,
      default() {
        return [];
      },
    },
    // 分页配置 具体见 element UI
    paginationConfig: {
      type: Object,
      default() {
        return {};
      },
    },
    // 远程 config
    fetchConfig: {
      type: Object,
      default() {
        return {};
      },
    },
    //tHeight
    tHeight: {
      type: [Number,String],
      default:() =>{
        return null
      }
    }
  },
  activated() {
    if (this.$refs['comTable']) {
      this.$refs['comTable'].doLayout();
    }
  },
  data() {
    // 默认配置
    const fetchFn = get(this.$ER_CONFIG, 'TABLE.fetchConfig', false);
    this.tableFetchConfig = fetchFn ? fetchFn.call(this) : dConfig.fetchConfig.call(this);

    this.ORIGIN_DATA = [];
    return {
      defaultBtns: defaultBtns.call(this),
      selfConfig: [],
      remoteConfig: [],
      showSettingDialog: false,
      settingStyle: {},
    };
  },
  created() {
    this.mergeSettingConfig(this.fetchConfig);
    this.getRemoteConfig(this.tableFetchConfig);
  },
  mounted() {
    // set setting方法
    if (this.fetchConfig.name && this.$parent) {
      this.$parent.ER_TABLE = this;
    }
    this.initSettinBtnLayout();
    const that = this
    // window.onresize =()  =>{
    //   return (()=>{
    //     this.$refs['comTable'].doLayout();
    //   })()
    // }
  },
  computed: {
    colOwn() {
      const result = [];
      const len = this.selfConfig.length;
      for (let i = 0; i < len; i++) {
        const { label, prop } = this.selfConfig[i];
        let flag = '';
        if (label || prop) {
          flag = label + prop;
        }
        const remoteProps = get(
          this.remoteConfig.filter(i => i.er_flag === flag),
          '0',
          {}
        );
        result[i] = {
          ...this.selfConfig[i],
          ...remoteProps,
        };
      }
      const re = result
        .sort((a, b) => {
          // er_sort 可能为 0
          if (!isUndef(a.er_sort) && !isUndef(b.er_sort)) {
            return a.er_sort - b.er_sort;
          }
        })
        .map(i => {
          delete i.er_flag;
          // delete i.er_sort;
          return Object.freeze(i);
        });
      return re;
    },
    settingData() {
      return cloneDeep(this.colOwn);
    },
  },
  watch: {
    colConfig: {
      handler(config) {
        this.selfConfig = config.map(i => Object.freeze(i));
      },
      immediate: true,
    },
  },
  methods: {
    // 初始化
    initSettings(config) {
      //
    },
    initSettinBtnLayout() {
      setTimeout(() => {
        const refsTable = this.$refs['comTable'];
        if (refsTable) {
          let i = 0;
          const refChildren = refsTable.$el.children;
          while (i < refChildren.length - 1) {
            const child = refChildren[i];
            if (/el-table__header-wrapper/gi.test(child.className)) {
              const th = child.querySelectorAll('thead th')[0];
              const bgColor = window.getComputedStyle(th, null).getPropertyValue('background-color');
              this.settingStyle = {
                height: child.clientHeight + 'px',
                backgroundColor: bgColor,
              };
              break;
            }
            i++;
          }
        }
      }, 300);
    },
    // merge config
    mergeSettingConfig(config) {
      if (config.name) {
        this.tableFetchConfig = {
          ...this.tableFetchConfig,
          ...config,
        };
      }
    },
    async getRemoteConfig(config) {
      const re = await this.tableFetchConfig.fetch(config);
      this.ORIGIN_DATA = cloneDeep(re || []);
      this.remoteConfig = re || [];
      this.initSettinBtnLayout();
    },
    async updateRemoteConfig(config) {
      const re = await this.tableFetchConfig.update(config);
      if (re.code === SUC_CODE) {
        this.$message && this.$message.success(get(re, 'message', ''));
        this.settingDialog(false);
        this.getRemoteConfig(this.tableFetchConfig);
      }
    },
    settingDialog(val) {
      this.showSettingDialog = val;
      if (val === false) {
        this.initSettinBtnLayout();
      }
    },
  },
  render() {
    // 默认配置
    // const dfc = get(this.$ER_CONFIG, 'TABLE', false);
    const dfc = get(config, 'config.TABLE', false);
    // const dfc = get(this.$ER_CONFIG, 'TABLE', false);
    const defaultConfig = dfc ? dfc : dConfig;

    const {
      wrapLoading,
      defaultBtns,
      tableLoading,
      table,
      tableData,
      tableConfig,
      operate,
      operateConfig,
      pagination,
      paginationConfig,
      $slots,
      // colConfig,
    } = this;
    const colConfig = this.colOwn;

    const generateCol = function(colArr) {
      const sortable = get(tableConfig, 'sortable', false);
      return colArr.map((col, index) => {
        // hidden 标志
        if (col.er_hide) {
          return '';
        }
        if (col.render) {
          const { render, renderHeader, children, ...args } = col;
          const scopedSlots = {
            default: render(h),
            // header: renderHeader(h),
          };
          return (
            <el-table-column
              key={JSON.stringify(col)}
              {...{ props: { ...defaultConfig.colConfig.props, sortable, ...args } }}
              scopedSlots={scopedSlots}
            >
              {children && children.length && generateCol(children)}
            </el-table-column>
          );
        }
        const { children, ...args } = col;
        return (
          <el-table-column key={JSON.stringify(col)} {...{ props: { ...defaultConfig.colConfig.props, sortable, ...args } }}>
            {children && children.length && generateCol(children)}
          </el-table-column>
        );
      });
    };
    return (
      <div class="er-tableown" vLoading={wrapLoading}>
        <tableSetting
          setting-data={this.settingData}
          setting-show={this.showSettingDialog}
          save={config => {
            this.updateRemoteConfig({
              ...this.tableFetchConfig,
              _tableConfig: config,
            });
          }}
          reset={config => {
            this.remoteConfig = cloneDeep(this.ORIGIN_DATA);
          }}
          apply={config => {
            this.settingDialog(false);
            this.remoteConfig = config;
          }}
          vOn:on-close={val => {
            this.settingDialog(val);
          }}
        ></tableSetting>
        {operate &&
          ($slots.operate ? (
            $slots.operate
          ) : (
            <div class="er-tableown__operate">
              {[...operateConfig, ...defaultBtns].map((btnPro, index) => {
                const { slotText, events, props, render, hide } = btnPro;
                if (hide === true) {
                  return '';
                }
                return render ? (
                  render(h)
                ) : (
                  <el-button {...{ props: props, on: events }} key={index}>
                    {slotText}
                  </el-button>
                );
              })}
            </div>
          ))}
        <div class="er-tableown__table">
          {this.fetchConfig.name && (
            <div
              class="er-tableown__setting-btn"
              style={this.settingStyle}
              vOn:click={() => {
                this.settingDialog(true);
              }}
            >
              <i class="el-icon-more"></i>
            </div>
          )}
          <el-table
            ref="comTable"
            row-key={this.uniqFlag}
            vLoading={tableLoading}
            height={this.tHeight}
            data={tableData}
            {...{
              props: { ...defaultConfig.table.props, ...tableConfig.props },
              on: {
                ...defaultConfig.table.events,
                ...tableConfig.events,
                'header-dragend': () => {
                  if (tableConfig.events['header-dragend']) {
                    try {
                      tableConfig.events['header-dragend']();
                    } catch (error) {
                      console.warn(error);
                    }
                  }
                  this.initSettinBtnLayout();
                },
              },
            }}
          >
            {generateCol(colConfig)}
          </el-table>
        </div>
        {pagination && (
          <div class="er-tableown__pagination">
            <el-pagination
              class={'rc-pag'}
              {...{
                props: paginationConfig.props,
                on: paginationConfig.events,
              }}
            />
          </div>
        )}
      </div>
    );
  },
});

function RcTable(config) {
  return RcTableFn(config);
}
RcTable.cname = name;

export default RcTable;