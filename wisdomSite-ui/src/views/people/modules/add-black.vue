<template>
  <el-dialog
    title="黑名单"
    :visible.sync="isShow"
    width="500px"
    :before-close="handleClose"
    :close-on-click-modal="false"
  >
    <el-form
      :model="ruleForm"
      :rules="rules"
      ref="ruleForm"
      label-width="100px"
      class="demo-ruleForm"
    >
      <el-form-item label="违规日期" prop="illegalDate">
        <el-date-picker style="width: 100%"
          v-model="ruleForm.illegalDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择违规日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item ref='tbNames' label="黑名单人员" prop="tbNames">
        <el-input v-model="ruleForm.tbNames" :readonly="true">
          <i @click="innerVisible = true" slot="suffix" class="el-input__icon el-icon-plus"></i>
        </el-input>
      </el-form-item>
      <el-form-item label="违规内容" prop="illegalReason">
        <el-input
          v-model="ruleForm.illegalReason"
          placeholder=""
          type="textarea"
          :rows="2"
          clearable
        ></el-input>
      </el-form-item>
      <!-- ruleForm.imageUrl ? imgList : []"> -->
      <el-form-item ref="upload" label="违规信息">
        <el-upload
          action="#"
          class="elUp"
          :before-upload="beforeUpload"
          :on-remove="handleRemove"
          :limit="1"
          :on-exceed="handleExceed"
          :file-list="ruleForm.fileInfo">
          <el-button size="small" type="text">
            点击上传
          </el-button>
          <div slot="tip" class="el-upload__tip">	附件支持格式(jpg,png,pdf,doc,docx,xls,xlsx)，不超过1M</div>
        </el-upload>
         <!-- <mul-pic-upload :value="ruleForm.imageUrl" @input="picChange"/> -->
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" class="r_button" @click="_submit">保存</el-button>
    </div>
    <el-dialog
      width="700px"
      title="选择人员"
      :visible.sync="innerVisible"
      append-to-body
      :close-on-click-modal="false">
      <el-table
        default-expand-all
        row-key="id"
        :data="tableData"
        @selection-change="handleSelection"
        @row-click="handleClick"
      >
        <!-- <el-table-column type="selection" width="55" align="center" /> -->
        <el-table-column  label="项目"  align="left" prop="projectName" :show-overflow-tooltip="true" />
        <el-table-column  label="姓名"  align="left" prop="name" :show-overflow-tooltip="true" />
        <el-table-column  label="身份证号"  align="left" prop="idNumber" :show-overflow-tooltip="true" />
      </el-table>
      <Pagination
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getTPtable"
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
      <div slot="footer" class="dialog-footer">
        <el-button class="r_button" @click="innerVisible = false">取消</el-button>
        <el-button type="primary" class="r_button" @click="_submitAdd">保存</el-button>
      </div>
    </el-dialog>
  </el-dialog>
</template>
<script>
import { getUnit,setAllData } from "@/api/people/company";
import { getTPtable,addBlack,modifyBlack,uploadAll } from "@/api/people/blacklist";
//  import MulPicUpload from "@/components/MulPicUpload/index";
import cloneDeep from "lodash/cloneDeep";
import unionBy from "lodash/unionBy";
import isArray from "lodash/isArray";
export default {
  name: "addBlack",
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
  // components: {MulPicUpload },
  data() {
    return {
      innerVisible: false,
      ruleForm: {
        tbNames: "",
        fileInfo: [],
        id: []
      },
      imgList: [],
      rules: {
        illegalDate: [{ required: true, message: "请选择违规日期", trigger: "blur" }],
        tbNames: [{ required: true, message: "请选择黑名单人员", trigger: "blur" }],
        illegalReason: [{ required: true, message: "请输入违规内容", trigger: "blur" }],
      },
      unitList: [],
      teamsList: [],
      tableData: [],
      tableSelection: [],
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      tagList: [],
      // formData: {},
      // ids: [],
      // tbNames: ''
    };
  },
  created() {
    this.getUnit();
    this.getTPtable();
    // console.log(this.$DStore.state);
  },
  watch: {
    isShow: {
      handler(flag) {
        if (flag) {
          // if (this.state == 'modifyBlack') {

          //   console.log(this.tagList);
          // } else {
          //   this.ruleForm = this.detailData
          // }
            const { id,name } = this.detailData
            this.ruleForm = {
              personIds: isArray(id) ? id : [id],
              tbNames: isArray(name) ? name.join(',') : name
            }
            if (this.state == 'modifyBlack') {
              this.ruleForm = {
                ...this.detailData,
                personIds: isArray(id) ? id : [id],
                tbNames: isArray(name) ? name.join(',') : name
              }
              // Object.assign(this.ruleForm, this.detailData);
            }
            console.log(this.ruleForm);
            if (isArray(id)) {
              id.forEach((item,index)=>{
                this.tagList.push({
                  id: item.id,
                  name: name[index]
                })
              })
            } else {
              if (id) {
                this.tagList = [{id,name}]
              } else {
                this.tagList = []
              }
            }
        }
      },
      immediate: true
    },
  },
  methods: {

    // picChange(e) {
    //   this.ruleForm.imageUrl = e
    // },
    handleClose() {
      this.$emit("on-close");
    },
    async getUnit() {
      const re = await getUnit({ projectId: this.detailData.projectId });
      this.unitList = re.rows;
    },
    getTeam() {
      if (this.unitList && this.unitList.length > 0) {
        let arr = this.unitList.filter(item => {
          return item.id == this.ruleForm.unitId;
        });
        this.teamsList = arr[0].teams;
      }
    },
    _submit() {
      this.$refs['ruleForm'].validate(async (valid) => {
        if (valid) {
          let re
          if (this.state == 'modifyBlack') {
            re = await modifyBlack(this.ruleForm)
          } else {
            re = await addBlack({
              ...this.ruleForm,
              personStatus: 0,
              blacklist: 1
            })
          }
          if (re.code == 200) {
            this.$message({
              message: re.msg,type: "success",
            });
            this.$emit("on-close",'setStaff');
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    async getTPtable() {
      const re = await getTPtable(this.queryParams);
      this.tableData = re.rows
      this.total = re.total
    },
    handleSelection(val) {
      this.tableSelection = val
    },
    delTag(item, index) {
      this.tagList.splice(index, 1);
    },
    _submitAdd() {
      this.innerVisible = false
      this.ruleForm.personIds = this.tagList.map(item => item.id)
      this.ruleForm.tbNames = (this.tagList.map(item => item.name)).join(',')
      this.$refs.tbNames.clearValidate();
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
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    // 上传预处理
    beforeUpload(file) {
      this.dialogVisibleimg = true;
      var testmsg = file.name.substring(file.name.lastIndexOf('.') + 1)
      let arr = ['jpg','png','pdf','doc','docx','xls','xlsx']
      if (arr.indexOf(testmsg) === -1) {
        alert('上传文件格式有误')
        return false
      }else {
        const timeStamp = new Date() - 0;
        const copyFile = new File([file], `${timeStamp}_${file.name}`);
        this.uploadFile(copyFile);
        return false;
      }

    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    // 上传图片
    uploadFile(file) {
      let formData = new FormData();
      formData.append("file", file);
      uploadAll(formData).then(response => {
        if (response.code === 200) {
          let imgSrc = process.env.VUE_APP_BASE_API + response.data.url;
          let arr = [{
            name: response.data.name,
            url: imgSrc
          }]
          this.ruleForm.fileInfo = arr
          this.ruleForm.imageUrl = response.data.id
          this.$forceUpdate()
          this.$refs.upload.clearValidate();
          // this.dialogImageUrl = imgSrc;
          // this.msgSuccess("修改成功");
        }
      });
    }
  }
};
</script>
<style lang="scss" scoped>
$--border-size: 1px solid #dddcdc;
.el-tag {
  margin-right: 10px;
}
::v-deep {
  .elUp{
    .el-upload-list__item{
      transition: none
    }
  }
}
//   .el-form {
//     // padding: 20px;
//     border: $--border-size;
//   }
//   .el-form-item__label {
//     border-right: $--border-size;
//     padding-right: 0;
//     text-align: center;
//     background: #f9fbfc;
//   }
//   .el-form-item__content {
//     margin-left: 12px;
//   }
//   .el-form-item {
//     margin: 0;
//     border-bottom: $--border-size;
//   }
//   .buttom {
//     border-bottom: none;
//   }
//   .el-select {
//     width: 100%;
//   }
//   .el-input__inner {
//     border-color: rgba(0, 0, 0, 0);
//   }
//   .is-error .el-input__inner {
//     border-color: #ff4949 !important;
//   }
// }
</style>
