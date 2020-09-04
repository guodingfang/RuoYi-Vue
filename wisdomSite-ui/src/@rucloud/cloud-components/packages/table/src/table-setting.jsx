import draggable from 'vuedraggable';
import './table-setting.scss';
import get from 'lodash/get';
import cloneDeep from 'lodash/cloneDeep';
import { ASSETS_PROPS } from './defaultTable';

export default {
  name: 'TableSetting',
  components: {
    draggable,
  },
  props: {
    settingData: {
      type: Array,
      default: () => [],
    },
    settingShow: {
      type: Boolean,
      default: false,
    },
    save: {
      type: Function,
      default: () => {},
    },
    apply: {
      type: Function,
      default: () => {},
    },
    reset: {
      type: Function,
      default: () => {},
    },
  },
  data() {
    return {
      currentTag: {},
      colOwn: [],
    };
  },
  computed: {},
  watch: {
    settingData: {
      handler(val) {
        this.currentTag = {};
        this.colOwn = val;
      },
      immediate: true,
    },
  },
  methods: {
    // drag
    toggleActive(item) {
      const seeCols = this.colOwn.filter(i => [null, false, undefined].includes(i.er_hide));
      if (seeCols.length === 1 && !!!item.er_hide === true) {
        this.$message.error('至少显示一列');
      } else {
        this.$set(item, 'er_hide', !!!item.er_hide);
      }
    },
    setCurretnTag(item) {
      this.currentTag = item;
    },
    getComputedParam() {
      const result = [];
      this.colOwn.forEach((i, index) => {
        const obj = {};
        const { label, prop } = i;
        ASSETS_PROPS.forEach(ele => {
          if (i[ele]) {
            obj[ele] = i[ele];
          }
        });
        obj.er_flag = label + prop;
        obj.er_sort = index;
        result.push(obj);
      });
      return result;
    },
  },
  render() {
    const toDialog = {
      on: {
        'update:visible': val => {
          this.$emit('on-close', val);
        },
      },
      props: {
        title: '表格设置',
        direction: 'rtl',
        visible: this.settingShow,
        size: '400px',
        'modal-append-to-body': true,
      },
    };
    let inputNode = [];
    if (this.currentTag.width) {
      inputNode.push(
        <p>
          <span class="card-label">宽度: </span>
          <el-input-number size="mini" min={10} vModel={this.currentTag.width}></el-input-number>
        </p>
      );
    }
    if (this.currentTag['min-width']) {
      inputNode.push(
        <p>
          <span class="card-label">宽度: </span>
          <el-input-number size="mini" min={10} vModel={this.currentTag['min-width']}></el-input-number> {/* <el-tag>最小宽度</el-tag> */}
        </p>
      );
    }

    return (
      <el-drawer {...toDialog}>
        <div class="er-tableown__drag">
          <div
            class={{
              'er-tableown__drag__operate': true,
              forbidden: !((this.currentTag.label || this.currentTag.selfLabel) && !this.currentTag.er_hide),
            }}
          >
            <el-card class="er-tableown__drag__operate_card">
              <div class="card-tags-name">{get(this.currentTag, 'label', '配置选项')}</div>
              {inputNode}
              <p>
                <span class="card-label">文字: </span>
                <el-radio-group vModel={this.currentTag.align}>
                  <el-radio label="left">左</el-radio>
                  <el-radio label="center">中</el-radio>
                  <el-radio label="right">右</el-radio>
                </el-radio-group>
              </p>
              <p>
                <span class="card-label">固定: </span>
                <el-radio-group vModel={this.currentTag.fixed}>
                  <el-radio label="left">左</el-radio>
                  <el-radio label="right">右</el-radio>
                  <el-radio label={false}>无</el-radio>
                </el-radio-group>
              </p>
            </el-card>
          </div>
          <div class="er-tableown__drag__tags">
            <el-card class="box-card">
              <draggable list={this.colOwn} class="drag-container">
                {this.colOwn.map(item => {
                  if (!item.label && !item.selfLabel) {
                    return '';
                  }
                  const nodeOut = (
                    <div
                      class={{
                        'er-tableown-dragitem': true,
                        hidden: item.er_hide,
                      }}
                      key={item.props}
                    >
                      <span
                        class={{
                          'er-tableown-dragitem--left': true,
                          active:
                            (item.label && this.currentTag.label === item.label) ||
                            (item.selfLabel && this.currentTag.selfLabel === item.selfLabel),
                        }}
                      >
                        <i
                          class="el-icon-setting pointer"
                          vOn:click={() => {
                            this.setCurretnTag(item);
                          }}
                        ></i>
                      </span>
                      <span class="er-tableown-dragitem--middle">{item.label || item.selfLabel}</span>
                      <span
                        class="er-tableown-dragitem--right"
                        vOn:click={() => {
                          this.toggleActive(item);
                        }}
                      >
                        <i class="el-icon-view pointer"></i>
                      </span>
                    </div>
                  );
                  return nodeOut;
                })}
              </draggable>
            </el-card>
            <div class="er-tableown-drag-operate">
              <el-button
                type="primary"
                vOn:click={() => {
                  this.apply(this.getComputedParam());
                }}
              >
                预览
              </el-button>
              <el-button
                type="primary"
                vOn:click={() => {
                  this.reset();
                }}
              >
                恢复
              </el-button>
              <el-button
                type="primary"
                vOn:click={() => {
                  this.save(this.getComputedParam());
                }}
              >
                保存
              </el-button>
            </div>
          </div>
        </div>
      </el-drawer>
    );
  },
};
