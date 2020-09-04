<template>
  <div >
    <el-table
      size="medium"
      :data="tableData"
      ref="tableData"
      :border="border"
      :stripe="stripe"
      row-key="id"
      :lazy="lazy"
      :max-height="maxHeight"
      :load="load"
      :height="height"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      highlight-current-row
      v-loading="loadingFlag"
      @row-dblclick="rowDetail"
      element-loading-text="加载中"
      element-loading-spinner="el-icon-loading"
      @select="handleSelect"
      @selection-change="handleSelectionChange"
      element-loading-background="rgba(255, 255, 255, 0.6)"
      :header-cell-style="{borderRight:'none'}">
      <div v-for="item in tableConfig" :key="item.prop">
        <el-table-column
          v-if="item.prop"
          :prop="item.prop"
          :width="item.width"
          :label="item.label"
          :align="align"
          :fixed="item.fixed"
          show-overflow-tooltip
        >
        </el-table-column>
        <el-table-column
          v-if="item.type == 'time'"
          :label="item.label"
          width="124px"
          :align="align"
        >
          <template slot-scope="scope">
            <span>{{scope.row[item.custom] | dateFormat}}</span>
          </template>
        </el-table-column>
        <el-table-column
          v-if="item.type == 'longName'"
          :label="item.label"
          :fixed="item.fixed"
          align="center"
          width="220px"
        >
          <template slot-scope="scope">
            <span v-if="scope.row[item.custom].length <= 13">{{scope.row[item.custom]}}</span>
            <el-popover v-else trigger="hover" placement="top">
              <p>{{ scope.row[item.custom] }}</p>
              <div slot="reference" class="name-wrapper">
                <span>{{ scope.row[item.custom].substring(0,13)+'...'}}</span>
              </div>
            </el-popover>
          </template>
        </el-table-column>
        <slot
          v-if="item.slot"
          :name="item.slot"
        ></slot>
      </div>
    </el-table>
  </div>
</template>

<script>
  import { mapState } from 'vuex';

  export default {
    name: 'Table',
    data() {
      return {
      };
    },
    props: {
      height: {
        type: [String,Number],
        default: null,
      },
      maxHeight: {
        type: [String,Number],
        default: null,
      },
      tableTitle: {
        type: String,
        default: '查询列表',
      },
      tableData: {
        type: Array,
        default: () => [],
      },
      customHeight: {
        type: [Number, String],
        default: 600,
      },
      tableConfig: {
        type: Array,
        default: () => [],
      },
      border: {
        type: Boolean,
        default: false,
      },
      align: {
        type: String,
        default: 'left',
      },
      stripe: {
        type: Boolean,
        default: false,
      },
      dbClick: {
        type: Boolean,
        default: true,
      },
      isNormalList: {
        type: Boolean,
        default: true,
      },
      lazy: {
        type: Boolean,
        default: false,
      },
      load: {
        type: Function,
        dafault: () => {}
      }
    },
    methods: {
      handleSelectionChange(val) {
        this.$emit('selection-change', val);
      },
      handleSelect(selection, row) {
        this.$emit('select', selection, row);
      },
      rowDetail(row) {
        if (!this.dbClick) return;
        this.$emit('rowDetail', row);
      }
    },
    mounted() {
    },
    computed: {
      ...mapState('app', ['loadingFlag']),
    },

  };
</script>

<style lang='less' scoped>

</style>
