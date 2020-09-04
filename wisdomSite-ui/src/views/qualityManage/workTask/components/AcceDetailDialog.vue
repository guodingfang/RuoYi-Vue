<template>
  <el-dialog
    title="质量验收详情"
    :visible.sync="flag"
    width="70%"
    height="600px"
    @close="closeDialog"
    :close-on-click-modal="false"
  >
    <div class="dialog-wrap">
      <el-form
        ref="detailData"
        :model="detailData"
        :rules="rules"
        size="small"
        label-width="140px"
      >
        <h4 class="tit">验收申请</h4>
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目名称：">
              <span class="detail-val">{{ detailData.projectName }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="验收部位：">
              <span class="detail-val">{{
                detailData.acceptancePosition
              }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="验收内容：">
              <span class="detail-val">{{ detailData.acceptanceContent }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="申请人：">
              <span class="detail-val">{{ detailData.applyPersonName }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="申请时间：">
          <span class="detail-val">{{ detailData.applyDate }}</span>
        </el-form-item>
        <div class="line"></div>

        <el-row>
          <el-col :span="12">
            <el-form-item label="计划验收时间：">
              <span class="detail-val">{{
                detailData.planverificationDate
              }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="验收人：">
              <span class="detail-val">{{
                detailData.verifyPersonName
              }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 循环 -->
        <div v-for="item in detailData.list" :key="item.id">
          <!--    验收结果h4   -->
          <h4 class="tit">验收结果</h4>
          <el-row>
            <el-col :span="12">
              <el-form-item label="验收人：">
                <span class="detail-val">{{ item.verifyPersonName }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="验收时间：">
                <span class="detail-val">{{ item.verificationDate }}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="结果描述：">
            <span class="detail-val">{{ item.acceptanceDescribe }}</span>
          </el-form-item>
          <el-form-item label="现场照片：">
            <mul-pic-upload :value="item.acceptanceDescribe"/>
          </el-form-item>
          <el-form-item label="验收结果">
            <span class="detail-val">
                <status-tag :status="item.acceptanceStateName" />
            </span>
          </el-form-item>
          <!--    再次申请   -->
          <h4 class="tit">再次申请</h4>

          <el-row>
            <el-col :span="12">
              <el-form-item label="计划验收时间：">
                <span class="detail-val">{{ item.planverificationDate }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="验收人：">
                <span class="detail-val">{{ item.acceptancePersonName }}</span>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
      </el-form>
    </div>
  </el-dialog>
</template>

<script>
  import StatusTag from "@/components/StatusTag/index";
  import MulPicUpload from "@/components/MulPicUpload/index";
  export default {
    name: "AcceDetailDialog",
    components: {MulPicUpload, StatusTag},
    props: {
      dialogFlag: {
        required: true,
        type: Boolean,
        default: false
      },
      detailDatas: {
        type: Object,
        default: () => {}
      }
    },
    data() {
      return {
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
          projectName: [{ required: true, message: "项目名称", trigger: "blur" }],
          invokeTarget: [
            { required: true, message: "调用目标字符串不能为空", trigger: "blur" }
          ],
          cronExpression: [
            { required: true, message: "cron执行表达式不能为空", trigger: "blur" }
          ]
        }
      };
    },
    methods: {
      /** 添加项目*/
      addProject() {
        this.$refs["detailData"].validate(valid => {
          if (!valid) return;
          addProject(this.detailData)
            .then(res => {
              this.msgSuccess("添加成功");
              this.flag = false;
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
  .dialog-wrap {
    height: 600px;
    overflow: auto;
    margin-top: -20px;
    .detail-val{
      color: #999999;
    }
    /deep/.el-form-item--small.el-form-item{
      margin-bottom: 0
    }
    /deep/.el-upload-list--picture-card {
      .el-upload-list__item {
        width: 80px;
        height: 80px;
        line-height: 82px;
        border-radius: 0;
      }
    }
    /deep/.el-upload--picture-card {
      width: 80px;
      height: 80px;
      line-height: 82px;
      border-radius: 0;
    }
    .tit {
      border-left: 4px solid #1ab394;
      padding-left: 10px;
      font-weight: normal;
    }
  }
</style>
