<template>
  <el-dialog
    title="验收"
    :visible.sync="flag"
    width="70%"
    @close="closeDialog"
    :close-on-click-modal="false"
  >
    <div class="add-wrap">
      <el-form
        ref="detailData"
        :model="detailData"
        :rules="rules"
        size="small"
        label-width="140px"
      >
        <el-form-item label="验收部位：" prop="acceptancePosition">
          <el-input
            v-model="detailData.acceptancePosition"
            placeholder=""
          ></el-input>
        </el-form-item>
        <el-form-item label="验收内容：" prop="acceptanceContent">
          <el-input
            type="textarea"
            :rows="2"
            v-model="detailData.acceptanceContent"
            placeholder=""
          ></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="验收人：" prop="acceptancePersonId">
              <el-select
                placeholder="请选择验收人："
                v-model="detailData.acceptancePersonId"
                class="w100"
              >
                <el-option
                  v-for="item in userList"
                  :label="item.nickName"
                  :value="item.userId"
                  :key="item.userId"
                >
                    <span style="float: left">{{ item.nickName }}</span>
                    <span style="float: right; color: #8492a6; font-size: 13px">{{ item.phonenumber }}</span>
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="验收时间：" prop="planverificationDate">
              <el-date-picker
                v-model="detailData.planverificationDate"
                type="date"
                placeholder=""
                :picker-options="pickerOptions"
                value-format="yyyy-MM-dd"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button
        size="small"
        type="primary"
        @click="isCreate == false ? updateProject() : addProject(true)"
        >保存</el-button
      >
      <el-button
        size="small"
        type="primary"
        @click="isCreate == false ? updateProject() : addProject(false)"
        >保存并继续新增</el-button
      >
    </div>
    <el-dialog :visible.sync="dialogVisible" size="tiny" append-to-body>
      <img width="100%" :src="dialogImageUrl" alt="" />
    </el-dialog>
  </el-dialog>
</template>

<script>
import { addApply, getCheckUser } from "@/api/quality/qualityAccept";
export default {
  name: "AddDialog",
  props: {
    dialogFlag: {
      required: true,
      type: Boolean,
      default: false
    },
    detailDatas: {
      type: [Object, Array],
      default: () => {}
    },
    isCreate: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() + 24 * 3600 * 1000 < Date.now();
        },
      },
      detailData: {},
      dialogImageUrl: "",
      dialogVisible: false,
      cities: [
        { value: 1, label: "北京" },
        { value: "Shanghai", label: "上海" },
        { value: "Nanjing", label: "南京" },
        { value: "Chengdu", label: "成都" },
        { value: "Shenzhen", label: "深圳" },
        { value: "Guangzhou", label: "广州" }
      ],
      // 表单校验
      rules: {
        planverificationDate: [
          { required: true, message: "请选择验收时间", trigger: "change" }
        ],
        acceptancePosition: [
          { required: true, message: "请输入验收部位", trigger: "blur" }
        ],
        acceptanceContent: [
          { required: true, message: "请输入验收内容", trigger: "blur" }
        ],
        acceptancePersonId: [
          { required: true, message: "请选择验收人", trigger: "change" }
        ]
      },
      userList: [],
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7;
        }
      },
    };
  },
  created() {
    this.getUser();
  },
  methods: {
    //查询人员
    getUser() {
      getCheckUser({
        enterpriseId: this.$store.state.user.enterpriseId
      }).then(response => {
        console.log(response);
        this.userList = response.rows;
      });
    },
    /** 添加项目*/
    addProject(show) {
      this.$refs["detailData"].validate(valid => {
        if (!valid) return;
        addApply(this.detailData)
          .then(res => {
            this.msgSuccess("添加成功");
            if (show) {
              this.flag = false;
            } else {
              this.detailData = {}
              console.log(this.detailData);
            }
            this.$emit("query");
          })
          .catch(e => {
            console.log(e);
          });
      });
    },
    /** 编辑项目*/
    updateProject() {
      this.$refs["detailData"].validate(valid => {
        if (!valid) return;
        updateProject(this.detailData)
          .then(res => {
            this.msgSuccess("编辑成功");
            this.flag = false;
            this.$emit("query");
          })
          .catch(e => {
            console.log(e);
          });
      });
    },
    /* 关闭项目框 */
    closeDialog() {
      this.detailData = {};
      this.$refs["detailData"].resetFields();
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["detailData"].validate(valid => {
        if (valid) {
          if (this.form.jobId != undefined) {
            updateJob(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addJob(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    }
  },
  computed: {
    flag: {
      get() {
        return this.dialogFlag;
      },
      set(val) {
        this.$emit("update:dialogFlag", val);
      }
    }
  },
  watch: {
    detailDatas(val) {
      console.log("我是val", val);
      this.detailData = Object.assign({}, val);
      this.detailData.id = Number(this.detailData.id);
      // this.detailData.projectStatus = this.detailData.projectStatus.toString();
    }
  }
};
</script>

<style scoped lang="scss">
.dialog-footer {
  text-align: right;
}
.add-wrap {
  /deep/.el-upload-list--picture-card {
    .el-upload-list__item {
      width: 100px;
      height: 100px;
      line-height: 102px;
      border-radius: 0;
    }
  }
  /deep/.el-upload--picture-card {
    width: 100px;
    height: 100px;
    line-height: 102px;
    border-radius: 0;
  }
  /deep/.el-date-editor {
    width: 100%;
  }
  .tit {
    border-left: 4px solid #1ab394;
    padding-left: 10px;
  }
}
</style>
