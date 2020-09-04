<template>
  <el-dialog
    title="人员详情"
    :visible.sync="isShow"
    width="900px"
    :before-close="handleClose"
    :close-on-click-modal="false"
  >
    <el-form
      ref="formData"
      size="small"
      label-position="left"
      label-width="90px"
    >
      <div class="dl_warp">
        <div class="dl_left">
          <h4 class="tit">基本信息</h4>
          <el-form-item label="姓名">
            <div class="text">{{ detailData.name }}</div>
          </el-form-item>
          <el-form-item label="出生年月日">
            <div class="text">{{ detailData.birthDate }}</div>
          </el-form-item>
          <el-form-item label="身份证号码	">
            <div class="text">{{ detailData.idNumber }}</div>
          </el-form-item>
          <el-form-item label="住址">
            <div class="text">{{ detailData.address }}</div>
          </el-form-item>
          <h4 class="tit">工资卡信息</h4>
          <el-form-item label="开户银行">
            <div class="text">{{ detailData.nameOfBank }}</div>
          </el-form-item>
          <el-form-item label="银行账号">
            <div class="text">{{ detailData.payCard }}</div>
          </el-form-item>
          <h4 class="tit">合同信息</h4>
          <el-form-item label="合同编号">
            <div class="text">{{ detailData.contactNumber }}</div>
          </el-form-item>
          <el-form-item label="合同类型">
            <div class="text">{{ detailData.contactTypeName }}</div>
          </el-form-item>
          <el-form-item label="生效日期">
            <div class="text">{{ detailData.contactEffectiveDate }}</div>
          </el-form-item>
          <el-form-item label="失效日期">
            <div class="text">{{ detailData.contactInvalidDate }}</div>
          </el-form-item>
        </div>
        <div class="dl_right">
          <h4 class="tit">入职信息</h4>
          <el-form-item label="参建单位">
            <div class="text">{{ detailData.unitName }}</div>
          </el-form-item>
          <el-form-item label="职位">
            <div class="text">{{ detailData.personTypeName }}</div>
          </el-form-item>
          <el-form-item label="班组">
            <div class="text">{{ detailData.teamName }}</div>
          </el-form-item>
          <el-form-item label="工种">
            <div class="text">{{ detailData.workTypeName }}</div>
          </el-form-item>
          <el-form-item label="手机号码">
            <div class="text">{{ detailData.mobilePhone }}</div>
          </el-form-item>
          <el-form-item label="实名卡号">
            <div class="text">{{ detailData.cardNo }}</div>
          </el-form-item>
          <el-form-item label="进场日期">
            <div class="text">{{ detailData.inDate }}</div>
          </el-form-item>
          <el-form-item label="退场日期">
            <div class="text">{{ detailData.outDate }}</div>
          </el-form-item>
          <h4 class="tit">合同附件信息</h4>
          <el-upload
            :disabled='true'
            action="#"
            class="elUp"
            :limit="1"
            :file-list="detailData.fileInfo">
          </el-upload>
        </div>
      </div>
    </el-form>
  </el-dialog>
</template>
<script>
export default {
  name: "detailStaff",
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
      formData: {}
    };
  },
  computed: {
    imgList() {
      return [{
        name: this.getName(this.detailData.attachmentOneUrl),
        url: this.detailData.attachmentOneUrl
      }]
    }
  },
  methods: {
    getName(a) {
      let index = a.lastIndexOf("\/");  
      let str = a.substring(index + 1,a.length);
      return str
    },
    handleClose() {
      this.$emit("on-close");
    }
  }
};
</script>
<style lang="scss" scoped>
::v-deep {
  .el-form-item {
    margin: 0;
  }
}
.dl_warp {
  display: flex;
  .dl_left,
  .dl_right {
    flex: 1;
  }
}
.tit {
  border-left: 4px solid #1ab394;
  padding-left: 10px;
  width: 100%;
  .tit_right {
    padding: 0;
    float: right;
  }
}
</style>
