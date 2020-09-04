<template>
  <el-dialog
    title="新增随机巡视隐患"
    :visible.sync="isShow"
    width="1000px"
    :before-close="handleClose"
    :close-on-click-modal="false"
  >
      <el-form
        ref="detailData"
        :model="detailData"
        :rules="rules"
        size="small"
        label-width="140px"
      >
        <el-form-item label="检查部位：" prop="checkPosition">
          <el-input
            v-model="detailData.checkPosition"
            placeholder=""
          ></el-input>
        </el-form-item>
        <el-form-item label="隐患等级：" prop="hazardLevel">
          <el-radio-group v-model="detailData.hazardLevel" class="w100">
            <el-radio :label="1">一般</el-radio>
            <el-radio :label="2">严重</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="作业类型：" prop="operationTypeId">
          <el-select
            placeholder="请选择作业类型"
            style="width: 100%"
            v-model="detailData.operationTypeId"
          >
            <el-option
              v-for="item in operationList"
              :label="item.operationName"
              :value="item.id"
              :key="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="隐患描述：">
          <el-input
            type="textarea"
            :rows="2"
            v-model="detailData.hazardDescribe"
            placeholder=""
          ></el-input>
        </el-form-item>
        <el-form-item label="整改要求：">
          <el-input
            type="textarea"
            :rows="2"
            v-model="detailData.rectifyRequire"
            placeholder=""
          ></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="整改人："  prop="rectifyPersonId">
              <el-select
                placeholder="请选择整改人"
                style="width: 320px"
                v-model="detailData.rectifyPersonId"
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
            <el-form-item label="整改时限：" prop="rectifyLimitDate">
              <el-date-picker
                class="is-picker"
                v-model="detailData.rectifyLimitDate"
                type="date"
                 style="width: 320px"
                value-format="yyyy-MM-dd"
                placeholder="选择日期"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="验证人：" prop="verifyPersonId">
          <el-select
            placeholder="请选择验证人"
            v-model="detailData.verifyPersonId"
            style="width: 320px"
          >
            <el-option
              v-for="item in userList"
              :label="item.nickName"
              :value="item.userId"
              :key="item.userId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item ref='senderName' label="抄送人：">
          <el-input v-model="detailData.senderName" :readonly="true">
            <i @click="innerVisible = true" slot="suffix" class="el-input__icon el-icon-plus"></i>
          </el-input>
        </el-form-item>
        <el-form-item label="上传图片：" ref='upload'>
          <!-- <mul-pic-upload :value="item.repairImageAddres" :readOnly=true></mul-pic-upload> -->
          <mul-pic-upload
            :value="detailData.uploadAddress"
            :limit="5"
            @input="picChange"
          />
        </el-form-item>
      </el-form>
      <el-dialog
        width="700px"
        title="选择人员"
        :visible.sync="innerVisible"
        append-to-body
        :close-on-click-modal="false">
        <el-input
          style="width: 350px;margin-bottom: 17px;"
          placeholder="按姓名/手机号搜索"
          suffix-icon="el-icon-search"
          v-model="queryParams.phonenumber"
          @change="getTPtable"
        />
        <el-table
          default-expand-all
          row-key="userId"
          :data="tableData"
          @selection-change="handleSelection"
          @row-click="handleClick"
        >
          <!-- <el-table-column type="selection" width="55" align="center" /> -->
          <el-table-column  label="姓名"  align="left" prop="nickName" :show-overflow-tooltip="true" />
          <!-- <el-table-column  label="身份证号"  align="left" prop="idNumber" :show-overflow-tooltip="true" /> -->
          <el-table-column  label="手机号"  align="left" prop="phonenumber" :show-overflow-tooltip="true" />
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
          {{ tag.userName }}
        </el-tag>
        <div slot="footer" class="dialog-footer">
          <el-button class="r_button" @click="innerVisible = false">取消</el-button>
          <el-button type="primary" class="r_button" @click="_submitAdd">保存</el-button>
        </div>
      </el-dialog>
      <div slot="footer" class="dialog-footer">
        <el-button class="r_button" @click="handleClose">取消</el-button>
        <el-button type="primary" class="r_button" @click="_submit">保存</el-button>
      </div>
  </el-dialog>
</template>
<script>
import {
  getCheckUser
} from "@/api/quality/checkManager";
import {
  addInspec,
  getOtable,
} from "@/api/Inspection/record";
import { getTPtable } from "@/api/people/blacklist";
import StatusTag from "@/components/StatusTag/index";
import MulPicUpload from "@/components/MulPicUpload/index";
import cloneDeep from "lodash/cloneDeep";
import unionBy from "lodash/unionBy";
import isArray from "lodash/isArray";
export default {
  name: 'detail',
  components: {MulPicUpload, StatusTag},
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
  data () {
    return {
      userList: [],
      tableData: [],
      tableSelection: [],
      rules: {
        checkPosition: [
          { required: true, message: "请输入检查部位", trigger: "blur" }
        ],
        hazardLevel: [
          { required: true, message: "请选择隐患等级", trigger: "change" }
        ],
        operationTypeId: [
          { required: true, message: "请选择作业类型", trigger: "blur" }
        ],
        rectifyPersonId: [
          { required: true, message: "请选择整改人", trigger: "change" }
        ],
        rectifyLimitDate: [
          { required: true, message: "请选择整改时限" }
        ],
        verifyPersonId: [
          { required: true, message: "请选择验证人", trigger: "change" }
        ],
      },
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      tagList: [],
      operationList: [],
      innerVisible: false,
    }
  },
  watch: {
    isShow: {
      handler(flag) {
        if (flag) {
          // this.ruleForm = this.detailData
        }
      },
      immediate: true
    },
  },
  created() {
    this.getTPtable()
    this.getOtable()
    getCheckUser({
      enterpriseId: this.$store.state.user.enterpriseId
    }).then(response => {
      this.userList = response.rows;
    });
  },
  methods: {
    handleClose() {
      this.$emit("on-close");
    },
    picChange(e) {
      this.detailData.uploadAddress = e
      console.log(e);
    },
    _submit() {
      this.$refs["detailData"].validate(valid => {
        if (!valid) return;
        this.loading = true
        addInspec(this.detailData)
          .then(res => {
            this.msgSuccess(res.msg);
            this.$emit("on-close", true);
          })
          .catch(e => {
            console.log(e);
          });
          this.loading = false
      });
    },
    async getOtable() {
      const re = await getOtable();
      this.operationList = re.rows;
    },
    async getTPtable() {
      const re = await getCheckUser({
        ...this.queryParams,
        enterpriseId: this.$store.state.user.enterpriseId
      });
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
      this.detailData.senderId = this.tagList.map(item => item.userId).join(',')
      this.detailData.senderName = (this.tagList.map(item => item.userName)).join(',')
      this.$refs.senderName.clearValidate();
    },
    handleClick(val) {
      let arr = [];
      arr.push(val);
      console.log(arr);
      if (this.tagList && this.tagList.length > 0) {
        this.tagList.forEach((item, index) => {
          if (item.userId == val.userId) {
            this.tagList.splice(index, 1);
          } else {
            this.tagList = unionBy(this.tagList.concat(arr), "userId");
          }
        });
      } else {
        this.tagList = unionBy(this.tagList.concat(arr), "userId");
      }
    },
  }
}
</script>
<style lang='scss' scoped>
.el-tag{
  margin-right: 10px;
}
</style>
