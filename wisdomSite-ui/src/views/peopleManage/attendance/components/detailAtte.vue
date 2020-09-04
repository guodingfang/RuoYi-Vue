<template>
  <el-dialog
    title="考勤记录"
    :visible.sync="isShow"
    width="1200px"
    :before-close="handleClose"
    :close-on-click-modal="false"
  >
    <table v-for="(item,index) in tableData.attendanceDataOfMonth" :key="index" class="mailTable">
      <tr>
        <td class="column">
          {{ index + 1 }}
        </td>
        <td v-for="(tdname,indextd) in item" :key="indextd">
          <span v-if="tdname.attendanceTime">
            {{ tdname.attendanceTime }}
            {{ tdname.direction == 1 ? '进' : '出' }}
          </span>
        </td>
      </tr>
    </table>
  </el-dialog>
</template>
<script>
export default {
  name: 'detailAtte',
  data () {
    return {
    }
  },
  props: {
    isShow: {
      type: Boolean,
      default: false
    },
    detailData: {
      type: [Object, Array],
      default: () => {}
    },
    state: {
      type: String,
      default: () => null
    }
  },
  watch: {
    isShow: {
      handler(flag) {
        this.tableData = this.detailData
      },
      immediate: true
    },
  },
  methods: {
    handleClose() {
      this.$emit("on-close");
    },
  }
}
</script>
<style lang='scss' scoped>
.mailTable,
.mailTable tr,
.mailTable tr td {
  border: 1px solid #e6eaee;
  text-align: center;
}
table {
  border-collapse:collapse;
}

.mailTable {
  font-size: 12px;
  color: #71787e;
}
.mailTable tr td {
  border: 1px solid #e6eaee;
  width: 150px;
  height: 20px;
  line-height: 20px;
  box-sizing: border-box;
  // padding: 0 10px;
}
.mailTable tr td.column {
  background-color: #eff3f6;
  color: #393c3e;
}
</style>
