<template>
  <div>
    <div
      class
      style="margin:10px 10px 10px 15px;display:flex;align-items:center;"
    >
      <div style="margin-left:10px;">全部（{{ this.total }}）</div>

      <el-form :inline="true">
        <el-form-item>
          <el-date-picker
            v-model="dateRange"
            @change="dateChange"
            value-format="yyyy-MM-dd"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label>
          <el-select
            v-model="queryParams.projectId"
            placeholder="项目名称"
            clearable
          >
            <el-option
              v-for="item in projectList"
              :key="item.projectName"
              :label="item.projectName"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label>
          <el-select
            v-model="queryParams.acceptanceState"
            placeholder="验收状态"
            clearable
          >
            <el-option key="合格" label="合格" value="1" />
            <el-option key="不合格" label="不合格" value="2" />
            <el-option key="待验收" label="待验收" value="3" />
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button
            class="filter-item"
            type="primary"
            icon="el-icon-search"
            @click="getSearchQualityList()"
            >搜索</el-button
          >
          <el-button
            type="warning"
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['system:dept:export']"
            >导出</el-button
          >
          <el-button
            class="filter-item"
            type="primary"
            icon="el-icon-plus"
            @click="dialogApplyAcceptDetail = true"
            v-hasPermi="['system:dept:add']"
            >申请验收</el-button
          >
        </el-form-item>
      </el-form>
    </div>

    <el-table :data="acceptTableData" stripe style="width: 100%">
      <el-table-column label="验收状态" width>
        <template slot-scope="scope">
          <span
            v-if="scope.row.acceptanceStateName == '合格'"
            style="background:green;padding:8px;color:#fff;"
            >{{ scope.row.acceptanceStateName }}</span
          >
          <span
            v-else-if="scope.row.acceptanceStateName == '待验收'"
            style="background:#ff6600;padding:8px;color:#fff;"
            >{{ scope.row.acceptanceStateName }}</span
          >
          <span
            v-else-if="scope.row.acceptanceStateName == '不合格'"
            style="background:red;padding:8px;color:#fff;"
            >{{ scope.row.acceptanceStateName }}</span
          >
        </template>
      </el-table-column>
      <el-table-column
        prop="planverificationDate"
        label="验收时限"
        width
      ></el-table-column>
      <el-table-column
        prop="projectName"
        label="项目名称"
        width
      ></el-table-column>
      <el-table-column
        prop="applyPersonName"
        label="申请人"
        width="70"
      ></el-table-column>
      <el-table-column
        prop="applyDate"
        label="申请时间"
        width
      ></el-table-column>
      <el-table-column
        prop="acceptancePosition"
        label="验收部位"
        width
      ></el-table-column>
      <el-table-column
        prop="acceptanceContent"
        label="验收内容"
        width
      ></el-table-column>
      <el-table-column
        prop="verifyPersonName"
        label="验收人"
        width="60"
      ></el-table-column>
      <el-table-column label="操作" width>
        <template slot-scope="scope">
          <el-button
            @click="handleToDetail(scope.row)"
            type="text"
            size="small"
            style="color:#058061;"
            >查看</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 点击查看 -->
    <el-dialog
      title="质量验收详情"
      :visible.sync="dialogAcceptDetail"
      width="70%;"
      append-to-body
    >
      <div>
        <!-- 验收申请 -->
        <div>
          <p class="tableP mb20">
            <span style="color:#666;">验收申请</span>
          </p>
          <div>
            <div style="display:flex;">
              <div class="com-table mb25">
                <p class="com-left">项目名称:</p>
                <span class="ax_default">{{
                  acceptanceApplyData.projectName
                }}</span>
              </div>
              <div class="com-table mb25">
                <p class="com-left">验收部位:</p>
                <span class="ax_default">{{
                  acceptanceApplyData.acceptancePosition
                }}</span>
              </div>
            </div>

            <div style="display:flex;">
              <div class="com-table mb25">
                <p class="com-left">验收内容:</p>
                <span class="ax_default">{{
                  acceptanceApplyData.acceptanceContent
                }}</span>
              </div>
              <div class="com-table mb25">
                <p class="com-left">申请人:</p>
                <span class="ax_default">{{
                  acceptanceApplyData.applyPersonName
                }}</span>
              </div>
            </div>

            <div style="display:flex;">
              <div class="com-table mb25">
                <p class="com-left">申请时间:</p>
                <span class="ax_default">{{
                  acceptanceApplyData.applyDate
                }}</span>
              </div>
            </div>

            <div v-for="item in acceptanceRecordData" :key="item.id">
              <div v-if="item.id != null && item.id != ''">
                <div
                  v-if="
                    item.acceptanceFrequency == '1' &&
                      acceptanceApplyData.applyNum != 0
                  "
                >
                  <!--<div v-if="acceptanceApplyData.acceptanceState != '1'">-->
                  <div style="display:flex;">
                    <div class="com-table mb25">
                      <p class="com-left">计划验收时间:</p>
                      <span class="ax_default">{{
                        item.planverificationDate
                      }}</span>
                    </div>
                    <div class="com-table mb25">
                      <p class="com-left">验收人:</p>
                      <span class="ax_default">{{
                        item.acceptancePersonName
                      }}</span>
                    </div>
                  </div>
                  <!-- </div>-->
                </div>

                <div v-else-if="item.acceptanceFrequency == '0'">
                  <!--<div v-if="acceptanceApplyData.acceptanceState != '1'">-->
                  <div style="display:flex;">
                    <div class="com-table mb25">
                      <p class="com-left">计划验收时间:</p>
                      <span class="ax_default">{{
                        acceptanceApplyData.planverificationDate
                      }}</span>
                    </div>
                    <div class="com-table mb25">
                      <p class="com-left">验收人:</p>
                      <span class="ax_default">{{
                        acceptanceApplyData.verifyPersonName
                      }}</span>
                    </div>
                  </div>
                  <!-- </div>-->
                </div>
                <div v-else-if="acceptanceApplyData.applyNum == 0">
                  <!--<div v-if="acceptanceApplyData.acceptanceState != '1'">-->
                  <div style="display:flex;">
                    <div class="com-table mb25">
                      <p class="com-left">计划验收时间:</p>
                      <span class="ax_default">{{
                        acceptanceApplyData.planverificationDate
                      }}</span>
                    </div>
                    <div class="com-table mb25">
                      <p class="com-left">验收人:</p>
                      <span class="ax_default">{{
                        acceptanceApplyData.verifyPersonName
                      }}</span>
                    </div>
                  </div>
                  <!-- </div>-->
                </div>
              </div>
            </div>
            <div v-if="acceptanceRecordData.length == 0">
              <div style="display:flex;">
                <div class="com-table mb25">
                  <p class="com-left">计划验收时间:</p>
                  <span class="ax_default">{{
                    acceptanceApplyData.planverificationDate
                  }}</span>
                </div>
                <div class="com-table mb25">
                  <p class="com-left">验收人:</p>
                  <span class="ax_default">{{
                    acceptanceApplyData.verifyPersonName
                  }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- ----------------------------------  -->
        <div>
          <div v-for="(item, index) in acceptanceRecordData" :key="item.id">
            <!-- 验收结果 -->
            <div v-if="item.id != null && item.id != ''">
              <p class="tableP mb20">
                <span style="color:#666;">验收结果</span>
              </p>

              <div>
                <div style="display:flex;">
                  <div class="com-table mb25">
                    <p class="com-left">验收人:</p>
                    <span class="ax_default">{{ item.verifyPersonName }}</span>
                  </div>
                  <div class="com-table mb25">
                    <p class="com-left">验收时间:</p>
                    <span class="ax_default">{{ item.verificationDate }}</span>
                  </div>
                </div>

                <div style="display:flex;">
                  <div class="com-table mb25">
                    <p class="com-left">结果描述:</p>
                    <span class="ax_default">{{
                      item.acceptanceDescribe
                    }}</span>
                  </div>
                </div>

                <div style="display:flex;">
                  <div class="com-table mb25">
                    <p class="com-left">现场照片:</p>
                    <span style="margin-right:8px;">
                      <img
                        src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1591899810736&di=0d25557d98bd7d7970193707b290ad0a&imgtype=0&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D1531221012%2C95085541%26fm%3D214%26gp%3D0.jpg"
                        alt
                        width="90"
                        height="90"
                      />
                    </span>
                    <span style="margin-right:8px;">
                      <img
                        src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1591899810736&di=0d25557d98bd7d7970193707b290ad0a&imgtype=0&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D1531221012%2C95085541%26fm%3D214%26gp%3D0.jpg"
                        alt
                        width="90"
                        height="90"
                      />
                    </span>
                    <span style="margin-right:8px;">
                      <img
                        src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1591899810736&di=0d25557d98bd7d7970193707b290ad0a&imgtype=0&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D1531221012%2C95085541%26fm%3D214%26gp%3D0.jpg"
                        alt
                        width="90"
                        height="90"
                      />
                    </span>
                  </div>
                </div>

                <div style="display:flex;">
                  <div class="com-table mb25">
                    <p class="com-left">验证结果:</p>
                    <span
                      style="margin-right:8px;color:#fff;background:red;border-radius:5px;padding:5px;font-weight:400;"
                    >
                      {{ item.acceptanceStateName }}
                    </span>
                  </div>
                </div>
              </div>
            </div>

            <!-- 再次申请 -->

            <div v-for="(item2, index2) in acceptanceApplyData.applyNum">
              <div v-if="index2 == index">
                <p class="tableP mb20">
                  <span style="color:#666;">再次申请</span>
                </p>
                <div>
                  <div style="display:flex;">
                    <div class="com-table mb25">
                      <p class="com-left">计划验收时间:</p>
                      <span class="ax_default">{{
                        item.planverificationDate
                      }}</span>
                    </div>
                    <div class="com-table mb25">
                      <p class="com-left">验收人:</p>
                      <span class="ax_default">{{
                        item.acceptancePersonName
                      }}</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!----------------------------------------------------------------------            -->
            <!-- <div v-if="acceptanceApplyData.applyNum != ''">
                <p class="tableP mb20">
                  <span style="color:#666;">再次申请</span>
                </p>
                <div>
                  <div style="display:flex;">
                    <div class="com-table mb25">
                      <p class="com-left">计划验收时间:</p>
                      <span class="ax_default">{{item.planverificationDate}}</span>
                    </div>
                    <div class="com-table mb25">
                      <p class="com-left">验收人:</p>
                      <span class="ax_default">{{item.acceptancePersonName}}</span>
                    </div>
                  </div>
                </div>
            </div>-->
          </div>
        </div>
      </div>
      <div style="text-align:right;width:100%;">
        <el-button
          style="background:#09a17b;margin-right:35px;"
          type="primary"
          @click="dialogCheckDetail = false"
          >关闭</el-button
        >
      </div>
    </el-dialog>

    <!-- 申请验收 -->

    <el-dialog
      title="验收"
      :visible.sync="dialogApplyAcceptDetail"
      width="70%;"
      append-to-body
    >
      <div>
        <div class="row-style mb20">
          <p class="com-left">
            验收部位:
            <span style="color: red;">*</span>
          </p>
          <div class="com-right">
            <label for="">
              <el-input
                type="text"
                v-model="applyAccept.acceptancePosition"
                placeholder=""
              ></el-input>
            </label>
          </div>
        </div>

        <div class="row-style mb20">
          <p class="com-left">
            验收内容:
            <span style="color: red;">*</span>
          </p>
          <div class="com-right">
            <label for="">
              <el-input
                type="textarea"
                v-model="applyAccept.acceptanceContent"
                placeholder=""
              ></el-input>
            </label>
          </div>
        </div>

        <div class="row-style mb20">
          <p class="com-left">
            验收人:
            <span style="color: red;">*</span>
          </p>
          <!-- <div> -->
          <el-select
            placeholder=""
            v-model="applyAccept.acceptancePersonId"
            class="ml5 com-right"
          >
            <el-option
              v-for="item in userList"
              :label="item.userName"
              :value="item.userId"
              :key="item.userName"
            ></el-option>
          </el-select>
          <!-- </div> -->

          <p class="com-left">
            验收时间:
            <span style="color: red;">*</span>
          </p>
          <div>
            <el-date-picker
              v-model="applyAccept.planverificationDate"
              type="date"
              placeholder="选择日期"
            >
            </el-date-picker>
          </div>
        </div>

        <div style="text-align:right;width:100%;">
          <el-button
            style="background:#09a17b;margin-right:35px;"
            type="primary"
            @click="addApplyAccept()"
            >保存</el-button
          >
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listQualityAccept,
  getCheckUser,
  addApply,
  exportApplyAccept,
  acceptanceApplyDetail,
  acceptanceRecordDetail,
  listProject
} from "@/api/ws/qualityAccept";

// 质量检查查询参数
const queryData = {
  pageNum: 1,
  pageSize: 10,
  milepostName: "",
  milepostSort: null
};
//新增质量验收参数字段
const defaultParamQualityAccept = {
  acceptancePosition: "",
  acceptanceContent: "",
  planverificationDate: "",
  acceptancePersonId: ""
};

export default {
  data() {
    return {
      queryData1: {
        acceptanceState: "",
        projectId: ""
      },
      userList: "",
      projectList: [],
      qualityAcceptQuery: Object.assign({}, queryData),
      applyAccept: Object.assign({}, defaultParamQualityAccept),
      // 分页参数
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        kssj: "",
        jssj: "",
        projectId: undefined,
        acceptanceState: undefined
      },
      total: 0,
      dateRange: "", //日期范围
      acceptTableData: null,
      dialogAcceptDetail: false,
      statusOptions: [],
      dialogApplyAcceptDetail: false,
      dialogCheckDetail: false,
      acceptanceApplyData: "",
      acceptanceRecordData: ""
    };
  },
  created() {
    this.getQualityAcceptList(), this.getUser(), this.getProjectList();
  },

  methods: {
    //查询人员
    getUser() {
      getCheckUser().then(response => {
        this.userList = response.rows;
      });
    },

    // 获取验收数据
    getQualityAcceptList() {
      listQualityAccept(this.qualityAcceptQuery).then(response => {
        this.acceptTableData = response.rows;
        this.total = response.total;
      });
    },

    //日期选择时触发
    dateChange() {
      (this.queryParams.kssj = this.dateRange[0]),
        (this.queryParams.jssj = this.dateRange[1]);
    },

    // 查看详情
    handleToDetail(value) {
      this.dialogAcceptDetail = true;
      acceptanceApplyDetail(value.id).then(response => {
        this.acceptanceApplyData = response.data;
      });
      acceptanceRecordDetail(value.id).then(response => {
        this.acceptanceRecordData = response.data;
      });
    },

    //项目信息
    getProjectList() {
      listProject().then(response => {
        this.projectList = response.rows;
      });
    },
    // 点击搜索
    getSearchQualityList() {
      listQualityAccept(this.queryParams).then(response => {
        this.acceptTableData = response.rows;
        this.total = response.total;
      });
    },

    addApplyAccept() {
      addApply(this.applyAccept)
        .then(response => {
          if (response.code == 200) {
            this.$message({
              message: response.msg,
              type: "success",
              duration: 1000
            });
            (this.dialogApplyAcceptDetail = false), this.getQualityAcceptList();
          } else {
            this.$message({
              message: response.msg,
              type: "success",
              duration: 1000
            });
          }
        })
        .catch(response => {
          console.log("错误信息", response);
        });
    },
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出质量检查数据！", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return exportApplyAccept(queryParams);
        })
        .then(response => {
          this.download(response.msg);
        })
        .catch(function() {});
    }
  }
};
</script>

<style>
.qualityLevel {
  padding-left: 0 !important;
  padding-right: 0 !important;
}
.tableP {
  border-left: 3px solid #09a17b;
}
.tableP span {
  padding-left: 8px;
  color: #939997;
}
.com-table {
  display: table;
  width: 100%;
}

.project-milestone {
  border: 1px solid #e9f2ef;
}
.border-bottom {
  border-bottom: 1px solid #e9f2ef;
}

/* 字体 */
.ax_default {
  font-family: "Arial Normal", "Arial";
  font-weight: 400;
  font-style: normal;
  font-size: 14px;
  color: #999999;
  /* text-align: center; */
  line-height: normal;
}

.row-style {
  display: table;
  width: 100%;
}
.com-left {
  display: table-cell;
  width: 135px;
  text-align: right;
  height: 35px;
  line-height: 35px;
}
.com-right {
  display: table-cell;
  padding: 0 20px;
  box-sizing: border-box;
  vertical-align: top;
}
.input-l270 {
  width: 100%;
  height: 35px;
  line-height: 35px;
  border: 1px solid #e9f2ef;
  font-size: 0.85rem;
  padding: 7px 10px 6px;
  color: #939997;
  border-radius: 0;
  box-sizing: border-box;
  overflow: hidden;
}
</style>
