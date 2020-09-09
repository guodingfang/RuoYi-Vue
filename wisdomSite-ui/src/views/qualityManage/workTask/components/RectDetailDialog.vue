<template>
  <el-dialog
    title="质量检查详情"
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
        <h4 class="tit">整改任务</h4>
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目名称：">
              <span class="detail-val">{{ detailData.projectName }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目所属单位：">
              <span class="detail-val">{{ detailData.projectUnitName }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="检查人：">
              <span class="detail-val">{{ detailData.checkPositionName }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="检查时间：">
              <span class="detail-val">{{ detailData.checkDate }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="检查部位：">
          <span class="detail-val">{{ detailData.checkPosition }}</span>
        </el-form-item>

        <div class="line mt20 mb20"></div>

        <el-row>
          <el-col :span="12">
            <el-form-item label="问题等级：">
              <span class="detail-val"><status-tag :status="detailData.qualityProblemLevelName"/></span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="问题类别：">
              <span class="detail-val">{{
                detailData.qualityProblemName
              }}</span>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="问题描述：">
              <span class="detail-val">{{ detailData.comment }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="整改要求：">
              <span class="detail-val">{{
                detailData.rectificationRequirements
              }}</span>
            </el-form-item>
          </el-col>
        </el-row>

        <div class="line mt20 mb20"></div>

        <el-form-item label="整改人：">
          <span class="detail-val">{{ detailData.repairPersonName }}</span>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="验证人：">
              <span class="detail-val">{{ detailData.verifyPersonName }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="整改期限：">
              <span class="detail-val">{{ detailData.repairLimitDate }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="整改前照片：">
          <mul-pic-upload :value="detailData.checkPositionAddres" :readOnly="true"/>
        </el-form-item>
        <div class="line"></div>

        <!-- 循环回复+验证 -->
        <div v-for="item in detailData.list" :key="item.id">
          <!--    整改回复   -->
          <h4 class="tit">整改回复</h4>
          <el-row>
            <el-col :span="12">
              <el-form-item label="整改人：">
                <span class="detail-val">{{ item.repairPersonName }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="整改时间：">
                <span class="detail-val">{{ item.repairDate }}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="结果描述：">
            <span class="detail-val">{{ item.repairResultDescription }}</span>
          </el-form-item>
          <el-form-item label="整改后照片：">
            <ul>
              <mul-pic-upload :value="item.repairImageAddres" :readOnly="true"/>
            </ul>
          </el-form-item>
          <!--    整改验证   -->
          <template v-if="item.verifyId">
          <h4 class="tit">整改验证</h4>

          <el-row>
            <el-col :span="12">
              <el-form-item label="整改人：">
                <span class="detail-val">{{ item.repairPersonName }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="整改时间：">
                <span class="detail-val">{{ item.verifyDate | formatDate }}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="结果描述：">
            <span class="detail-val">{{ item.verifyResultDescription }}</span>
          </el-form-item>
          <el-form-item label="现场照片：">
            <mul-pic-upload :value="item.verifyImageAddres" :readOnly=true></mul-pic-upload>
<!--            <ul>-->
<!--              <li><img src="" alt="" />{{ detailData.projectName }}</li>-->
<!--            </ul>-->
          </el-form-item>
          <el-form-item label="验证结果：">
            <span class="detail-val">{{ item.verifyResult }}</span>
          </el-form-item>
          </template>
        </div>
      </el-form>
    </div>
  </el-dialog>
</template>

<script>
  import StatusTag from "@/components/StatusTag/index";
  import MulPicUpload from "@/components/MulPicUpload/index";
  import { parseTime } from "@/utils/jlkj";
  export default {
    name: "RectDetailDialog",
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
    filters: {
      formatDate(val) {
        return parseTime(val)
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
