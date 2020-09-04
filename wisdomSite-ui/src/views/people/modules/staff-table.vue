<template>
  <el-dialog
    title="从建筑工人库中添加人员"
    :visible.sync="isShow"
    width="900px"
    :before-close="close"
    :close-on-click-modal="false"
  >
    <div class="title">
      第一步：请选择人员所属参建单位、班组、工种。
    </div>
    <!-- <div class="select_list"> -->
      <el-select v-model="personType" placeholder="人员类型">
        <el-option label="建筑工人" value="0"></el-option>
        <el-option label="管理人员" value="1"></el-option>
      </el-select>

      <el-select @change="getTeam" v-model="unitId" placeholder="建设单位">
        <el-option
          v-for="item in unitList"
          :key="item.id"
          :label="item.unitName"
          :value="item.id"
        >
        </el-option>
      </el-select>
      <el-select v-model="teamId" placeholder="班组/部门">
        <el-option
          v-for="item in teamsList"
          :key="item.id"
          :label="item.teamName"
          :value="item.id"
        >
        </el-option>
      </el-select>
      <el-select v-model="workType" placeholder="工种/岗位">
        <el-option
          v-for="item in $DStore.state.getWorkType"
          :key="item.dictValue"
          :label="item.dictLabel"
          :value="item.dictValue"
        >
        </el-option>
      </el-select>
    <!-- </div> -->
    <div class="title">
      第二步，请搜索并选择人员。
    </div>
    <el-input
      placeholder="输入姓名或身份证号码搜索"
      class="keyword"
      v-model="queryParams.keyWordName"
    >
      <i slot="suffix" @click="getData()" class="el-input__icon el-icon-search"></i>
    </el-input>

    <el-table
      default-expand-all
      row-key="id"
      ref="table"
      v-loading="loading"
      :data="tableData"
      :highlight-current-row="true"
      @row-click="handleClick"
    >
      <!-- <el-table-column type="selection" width="55" /> -->
      <el-table-column
        label="姓名"
        align="left"
        prop="name"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="身份证号"
        align="left"
        prop="idNumber"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="手机号"
        align="left"
        prop="mobilePhone"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="年龄"
        align="left"
        prop="age"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="性别"
        align="left"
        prop="sexName"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="住址"
        align="left"
        prop="address"
        :show-overflow-tooltip="true"
      />
    </el-table>
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getData"
    />
    <el-tag
      v-for="(tag, index) in tagList"
      :disable-transitions="true"
      :key="index"
      closable
      type="success"
      @close="delTag(tag, index)"
    >
      {{ tag.name }}
    </el-tag>
    <span slot="footer" class="dialog-footer">
      <el-button @click="close()">取 消</el-button>
      <el-button type="primary" @click="_submit">确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
import { getUnit, getPtableAll, addUtable, addBatch } from "@/api/people/company";
import cloneDeep from "lodash/cloneDeep";
import unionBy from "lodash/unionBy";
export default {
  name: "rTable",
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
  data() {
    return {
      loading: false,
      tableData: [],
      tableSelection: [],
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      tagList: [],
      personType: "",
      unitId: "",
      teamId: "",
      workType: "",
      keywordName: "",
      unitList: [],
      teamsList: []
    };
  },
  async created() {
    this.getData();
    this.getUnit();
  },
  methods: {
    close() {
      this.$emit("on-close");
    },
    async getUnit() {
      const re = await getUnit({ projectId: this.detailData.projectId });
      this.unitList = re.rows;
    },
    getTeam() {
      if (this.unitList && this.unitList.length > 0) {
        let arr = this.unitList.filter(item => {
          return item.id == this.unitId;
        });
        this.teamsList = arr[0].teams;
      }
    },
    delTag(item, index) {
      this.tagList.splice(index, 1);
    },
    handleClick(val) {
      let arr = [];
      arr.push(val);
      if (this.tagList && this.tagList.length > 0) {
        this.tagList.forEach((item, index) => {
          if (item.id == val.id) {
            this.tagList.splice(index, 1);
          } else {
            this.tagList = unionBy(this.tagList.concat(arr), "id");
          }
        });
      } else {
        this.tagList = unionBy(this.tagList.concat(arr), "id");
      }
    },
    async _submit() {
      if (this.tagList && this.tagList.length > 0) {
        const ids = this.tagList.map(item => item.id);
        const re = await addBatch({
          personType: this.personType,
          unitId: this.unitId,
          teamId: this.teamId,
          workType: this.workType,
          projectId: this.detailData.projectId,
          personLibraryIds: ids
        });
        if (re.code === 200) {
          this.$message({ type: "success", message: re.msg });
          this.close();
        }
      } else {
        this.$message({ type: "info", message: "请选择数据" });
      }
    },

    async getData() {
      const re = await getPtableAll(this.queryParams);
      this.total = re.total;
      this.tableData = re.rows;
    }
  }
};
</script>
<style lang="scss" scoped>
$--border-size: 1px solid #dddcdc;
.title {
  padding: 16px 0;
}
.el-tag {
  margin-right: 10px;
}
.keyword{
  margin-bottom: 16px;
  width: 220px;
}
</style>
