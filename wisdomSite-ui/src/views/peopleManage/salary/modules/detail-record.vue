<template>
  <el-dialog
    title="安全巡视详情"
    :visible.sync="isShow"
    width="1000px"
    @close="handleClose"
    :close-on-click-modal="false"
  >
    <el-form ref="detailData" size="small" label-width="140px">
      <h4 class="tit">整改任务</h4>
      <el-row>
        <el-col :span="12">
          <el-form-item label="项目名称：">
            <span class="detail-val">{{ basic.projectName }}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="项目所属单位：">
            <span class="detail-val">{{ basic.projectUnitName }}</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="检查人：">
            <span class="detail-val">{{ basic.checkPositionName }}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="检查时间：">
            <span class="detail-val">{{ basic.checkDate }}</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="检查部位：">
        <span class="detail-val">{{ basic.checkPosition }}</span>
      </el-form-item>
      <el-form-item label="检查结果：">
        <span class="detail-val">{{ basic.checkResult }}</span>
      </el-form-item>
      <el-form-item label="整改前照片：" ref="upload">
        <!-- <mul-pic-upload :value="item.repairImageAddres" :readOnly=true></mul-pic-upload> -->
        <mul-pic-upload :readOnly=true :value="basic.uploadAddress" />
      </el-form-item>
      <!-- 循环回复+验证 -->
      <div v-for="item in list" :key="item.id">
        <!-- wsSafetyHazardRectify -->
        <h4 class="tit">隐患整改</h4>
          <el-row>
            <el-col :span="12">
              <el-form-item label="整改人：">
                <span class="detail-val">{{ item.wsSafetyHazardRectify.rectifyPersonName }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="整改时间：">
                <span class="detail-val">{{ item.wsSafetyHazardRectify.rectifyTime }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="结果描述：">
                <span class="detail-val">{{ item.wsSafetyHazardRectify.resultDescription }}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="整改后照片：" ref="upload">
            <!-- <mul-pic-upload :value="item.repairImageAddres" :readOnly=true></mul-pic-upload> -->
            <mul-pic-upload :readOnly=true :value="item.wsSafetyHazardRectify.rectifyImageAddress" />
          </el-form-item>

          <h4 class="tit">隐患验证</h4>
          <el-row>
            <el-col :span="12">
              <el-form-item label="验证人：">
                <span class="detail-val">{{ item.wsSafetyHazardVerify.verifyPersonName }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="验证时间：">
                <span class="detail-val">{{ item.wsSafetyHazardVerify.verifyDate }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="结果描述：">
                <span class="detail-val">{{ item.wsSafetyHazardVerify.verifyResultDescription }}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="现场照片：" ref="upload">
            <!-- <mul-pic-upload :value="item.repairImageAddres" :readOnly=true></mul-pic-upload> -->
            <mul-pic-upload :readOnly=tru :value="item.wsSafetyHazardVerify.verifyImageAddres" />
          </el-form-item>
          <el-form-item label="验证结果：" ref="upload">
            <StatusTag :status="item.wsSafetyHazardVerify.verifyResultName"/>
          </el-form-item>
      </div>
    </el-form>
  </el-dialog>
</template>
<script>
import { getCheckUser } from "@/api/quality/checkManager";
import { getTPtable } from "@/api/people/blacklist";
import StatusTag from "@/components/StatusTag/index";
import MulPicUpload from "@/components/MulPicUpload/index";
export default {
  name: "detail",
  components: { MulPicUpload, StatusTag },
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
      userList: [],
      tableData: [],
      tableSelection: [],
      rules: {},
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      tagList: [],
      innerVisible: false,
      basic: {},
      list: []
    };
  },
  watch: {
    isShow: {
      handler(flag) {
        if (flag) {
          // this.ruleForm = this.detailData
          this.basic = this.detailData.wsSafetyInspectionRecord;
          this.list = this.detailData.wsSafetyHazardRectifyAndVerifyList;
          console.log(this.detailData);
        }
      },
      immediate: true
    }
  },
  created() {
    this.getTPtable();
    getCheckUser().then(response => {
      this.userList = response.rows;
    });
  },
  methods: {
    handleClose() {
      this.$emit("on-close");
    },
    picChange(e) {
      this.detailData.projectLogo = e;
    },
    async getTPtable() {
      const re = await getTPtable();
      this.tableData = re.rows;
      this.total = re.total;
    },
    handleSelection(val) {
      this.tableSelection = val;
    },
    delTag(item, index) {
      this.tagList.splice(index, 1);
    },
    _submitAdd() {
      this.innerVisible = false;
      this.detailData.personIds = this.tagList.map(item => item.id);
      this.detailData.senderName = this.tagList
        .map(item => item.name)
        .join(",");
      this.$refs.senderName.clearValidate();
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
    }
  }
};
</script>
<style lang="stylus" scoped></style>
