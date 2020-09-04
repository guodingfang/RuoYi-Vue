<template>
  <el-dialog
    title="参建单位"
    :visible.sync="isShow"
    width="900px"
    :before-close="close"
    :close-on-click-modal="false"
  >
    <div class="t_form">
      <el-form
        ref="formData"
        :model="formData"
        :rules="rules"
        size="small"
        label-position="left"
        label-width="140px"
      >
        <el-row>
          <div class="top_form">
            <el-col :span="12">
              <el-form-item label="企业名称" prop="unitName">
                <el-input
                  v-model="formData.unitName"
                  placeholder="最长为30字"
                  clearable
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="企业类型" class="c_l cl_tw">
                <el-select
                  v-model="formData.unitTypeParentId"
                  @change="getType"
                  placeholder="建设单位"
                >
                  <el-option
                    v-for="item in typeParent"
                    :key="item.id"
                    :label="item.typeName"
                    :value="item.id"
                  >
                  </el-option>
                </el-select>
                <el-select v-model="formData.unitTypeId" placeholder="建设单位">
                  <el-option
                    v-for="item in typeList"
                    :key="item.id"
                    :label="item.typeName"
                    :value="item.id"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </div>
        </el-row>
        <el-form-item label="社会统一信用代码" prop="unitOrgcode">
          <el-input
            v-model="formData.unitOrgcode"
            placeholder="社会统一信用代码为18位"
            clearable
          ></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="注册日期" prop="regDate">
              <el-date-picker
                v-model="formData.regDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择日期"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="注册所在地" class="c_l c_tre" prop="regAreaId">
              <el-select
                v-model="formData.provinceId"
                @change="getCity()"
                placeholder="省"
              >
                <el-option
                  v-for="item in provinceList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                >
                </el-option>
              </el-select>
              <el-select
                v-model="formData.cityId"
                @change="getRegAreaList()"
                placeholder="市"
              >
                <el-option
                  v-for="item in cityList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                >
                </el-option>
              </el-select>
              <el-select v-model="formData.regAreaId" placeholder="区">
                <el-option
                  v-for="item in regAreaList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="注册资本（万元）">
              <el-input
                v-model="formData.regCapital"
                placeholder=""
                clearable
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="企业性质" class="c_l">
              <el-select
                v-model="formData.unitNatureCode"
                placeholder="企业性质"
              >
                <el-option
                  v-for="item in unitNatureList"
                  :key="item.dictValue"
                  :label="item.dictLabel"
                  :value="item.dictValue"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="法定代表人">
              <el-input
                v-model="formData.legalPerson"
                placeholder=""
                clearable
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="营业地址" class="c_l">
              <el-input
                v-model="formData.address"
                placeholder=""
                clearable
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="联系人">
              <el-input
                v-model="formData.contacts"
                placeholder=""
                clearable
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" class="c_l">
              <el-input
                v-model="formData.contactNumber"
                placeholder=""
                clearable
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="办公电话">
              <el-input
                v-model="formData.officeNumber"
                placeholder=""
                clearable
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="传真号" class="c_l">
              <el-input
                v-model="formData.faxNumber"
                placeholder=""
                clearable
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注">
          <el-input
            v-model="formData.comment"
            placeholder=""
            clearable
          ></el-input>
        </el-form-item>
        <div class="f_stort" v-if="this.state === 'addRoster'">
          <span class="f_text">添加班组/部门</span>
          <el-button class="b_text" type="text" @click="addDomain"
            >添加</el-button
          >
        </div>

        <el-row v-if="this.state === 'addRoster'">
          <el-col
            :span="24"
            v-for="(item, index) in formData.teams"
            :key="index + 1009"
          >
            <el-form-item
              label="班组/部门名称"
              :key="index + 1009"
              :class="{ c_l: index / 2 }"
              :prop="'teams.' + index + '.teamName'"
              :rules="{
                required: true,
                message: ' ',
                trigger: 'blur'
              }"
            >
              <el-input
                v-model="item.teamName"
                placeholder=""
                clearable
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span style="font-size: 14px;padding: 8px;">
        添加参建单位？试试从参建单位库中添加
        <el-button type="text" @click="addTable">点击添加</el-button>
      </span>
    </div>
    <div class="form_btn">
      <el-button type="primary" @click="_submit()">
        保存
      </el-button>
    </div>
  </el-dialog>
</template>
<script>
import {
  getEnterprise,
  getArea,
  getNature,
  addProjectUnit,
  modifyProjectUnit
} from "@/api/people/company";
export default {
  name: "add",
  props: {
    isShow: {
      type: Boolean,
      default: false
    },
    detailData: {
      type: Object,
      default: () => {}
    },
    state: {
      type: String,
      default: () => null
    }
  },
  data() {
    return {
      formData: {
        teams: [{ teamName: "" }],
        unitTypeId: null
      },
      // 表单校验
      rules: {
        unitName: [{ required: true, message: " " }],
        unitOrgcode: [{ required: true, message: " " }],
        provinceId: [{ required: true, message: " " }],
        regAreaId: [{ required: true, message: " " }]
      },
      options: [
        {
          value: "选项1",
          label: "黄金糕"
        },
        {
          value: "选项2",
          label: "双皮奶"
        }
      ],
      unitNatureList: [],
      typeParent: [],
      typeList: [],
      provinceList: [],
      cityList: [],
      regAreaList: []
    };
  },
  created() {
    this.selectList();
  },
  watch: {
    isShow: {
      async handler(flag) {
        if (flag) {
          const { unitTypeId } = this.detailData;
          this.formData = {
            teams: [{ teamName: "" }],
            ...this.detailData,
            unitTypeId: unitTypeId ? unitTypeId : null
          };
          if (this.state == 'modifyRoster') {
            const re = await getEnterprise({
              parentId: this.formData.unitTypeParentId
            });
            this.typeList = re.data;
            const resc = await getArea({ parentId: this.formData.provinceId });
            this.cityList = resc.data;
            const resa = await getArea({ parentId: this.formData.cityId });
            this.regAreaList = resa.data;
          }
        }
      },
      immediate: true
    }
  },
  methods: {
    async selectList() {
      const re = await getEnterprise({ parentId: "0" });
      const res = await getArea({ parentId: "0" });
      const rs = await getNature();
      this.typeParent = re.data;
      this.provinceList = res.data;
      this.unitNatureList = rs.data;
    },
    async getType() {
      // this.typeParent = res.data
      this.formData.unitTypeId = null;
      const re = await getEnterprise({
        parentId: this.formData.unitTypeParentId
      });
      this.typeList = re.data;
      // this.typeList = res.data
    },
    async getCity() {
      const res = await getArea({ parentId: this.formData.provinceId });
      this.cityList = res.data;
      console.log(this.cityList, this.provinceId);
    },
    async getRegAreaList() {
      const res = await getArea({ parentId: this.formData.cityId });
      this.regAreaList = res.data;
    },
    addDomain() {
      this.formData.teams = this.formData.teams ? this.formData.teams : [];
      this.formData.teams = this.formData.teams.concat([
        { teamName: null }
      ]);
    },
    _submit() {
      // this.$refs["formData"].resetFields();
      this.$refs["formData"].validate(async valid => {
        if (valid) {
          let re;
          // this.formData.teams.forEach((item, index) => {
          //   if (!item.teamName) {
          //     item.flag = false;
          //   }
          // });
          // this.formData.teams = this.formData.teams.filter(
          //   i => i.flag != false
          // );
          // this.formData.teams =
          //   this.formData.teams.length > 0 ? this.formData.teams : null;
          if (this.state === "addRoster") {
            re = await addProjectUnit(this.formData);
          } else {
            re = await modifyProjectUnit(this.formData);
          }
          if (re.code == 200) {
            this.$message({
              message: re.msg,
              type: "success"
            });
            this.close();
          }
          console.log(re);
        }
      });
    },
    addTable() {
      this.close();
      this.$emit("on-utable");
    },
    close() {
      this.$emit("on-close");
    }
  }
};
</script>
<style lang="scss" scoped>
$--border-size: 1px solid #dddcdc;
::v-deep {
  // .form-warp {
  //   border-bottom: $--border-size;
  // }
  // .top_form{
  //  .el-form-item {
  //     border-top: none !important;
  //   }
  // }
  .c_l {
    .el-form-item__label {
      border-left: $--border-size;
    }
  }
  .cl_tw {
    .el-select {
      width: 120px;
    }
  }
  .c_tre {
    .el-select {
      width: 80px;
    }
  }
  .f_stort {
    font-size: 14px;
    padding: 8px;
    color: #606266;
    border-bottom: $--border-size;
    .f_text {
      display: inline-block;
      width: 140px;
      text-align: center;
    }
    .b_text {
      float: right;
      padding: 0;
    }
  }

  .el-form-item {
    margin: 0;
    border-bottom: $--border-size;
  }
  .el-form-item__label {
    border-right: $--border-size;
    padding-right: 0;
    text-align: center;
    background: #f9fbfc;
  }
  .el-form-item__content {
    padding-left: 12px;
  }
  .el-input__inner {
    border: none;
  }
}

.table-tit {
  font-size: 16px;
  font-weight: normal;
  padding: 16px 30px;
  margin: 0;
  border-bottom: $--border-size;
  .el-icon-close {
    float: right;
  }
}
.t_form {
  // margin-top: 20px;
  border: $--border-size;
  border-radius: 4px;
}
.form_btn {
  // float: right;
  text-align: right;
  margin: 20px 10px 0;
}
</style>
