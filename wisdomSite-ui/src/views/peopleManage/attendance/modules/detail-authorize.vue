<template>
  <el-dialog
    title="授权"
    :visible.sync="isShow"
    width="900px"
    :before-close="handleClose"
    :close-on-click-modal="false"
  >
    <h4 class="tit">基本信息</h4>
    <div class="basic">
      <el-form label-width="100px" class="basic_left">
        <el-form-item label="姓名">
          <span style="padding-left: 16px;">{{ detailData.name }}</span>
        </el-form-item>
        <el-form-item label="身份证号">
          <span style="padding-left: 16px;">{{ detailData.idNumber }}</span>
        </el-form-item>
        <el-form-item label="参建单位">
          <span style="padding-left: 16px;">{{ detailData.unitName }}</span>
        </el-form-item>
        <el-form-item label="班组">
          <span style="padding-left: 16px;">{{ detailData.teamName }}</span>
        </el-form-item>
        <el-form-item label="工种">
          <span style="padding-left: 16px;">{{ detailData.workTypeName }}</span>
        </el-form-item>
      </el-form>
      <div class="basic_right">
        <el-image :src="detailData.faceUrl" fit="cover"></el-image>
      </div>
    </div>
     <h4 class="tit">基本信息</h4>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="授权成功" name="1">
        <authorizeTable :tableData='tableData' />
      </el-tab-pane>
      <el-tab-pane label="授权失败" name="0">
        <authorizeTable :tableData='tableData' state='0' />
      </el-tab-pane>
      <el-tab-pane label="授权中" name="2">
        <authorizeTable :tableData='tableData' />
      </el-tab-pane>
    </el-tabs>
  </el-dialog>
</template>
<script>
import { getStable } from "@/api/people/authorize";
import authorizeTable from './authorize-table'
export default {
  name: "detailAuthorize",
  data() {
    return {
      activeName: '1',
      tableData: []
    };
  },
  components: {
    authorizeTable,
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
    guids: {
      type: [Array],
      default: () => []
    },
    state: {
      type: String,
      default: () => null
    }
  },
  watch: {
    isShow: {
      async handler(flag) {
        if (flag) {
          const re = await getStable({
            guid: this.detailData.guid,
            authStatus: this.activeName
          });
          this.tableData = re.rows;
        }
      },
      immediate: true
    }
  },
  created() {
    console.log(this.$DStore.state);
  },
  methods: {
    handleClose() {
      this.$emit("on-close");
    },
    async handleClick(tab, event) {
      const re = await getStable({
        guid: this.detailData.guid,
        authStatus: this.activeName
      });
      this.tableData = re.rows;
    }
  }
};
</script>
<style lang="scss" scoped>
.tit {
  border-left: 4px solid #1ab394;
  padding-left: 10px;
  width: 100%;
  .tit_right {
    padding: 0;
    float: right;
  }
}
.basic{
  display: flex;
  .basic_left {
    // flex:1
  }
  .basic_right{
    margin-left: 60px;
    width: 250px;
    height: 250px;
    .el-image{
      width: 100%;height: 100%;
    }
  }
}
</style>
