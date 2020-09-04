<template>
  <div class="cad-container">
    <el-row :gutter="20">
      <el-col :span="6" :xs="24">
        <div class="search">
          <el-select
            v-model="userId"
            placeholder="姓名搜索"
            filterable
            clearable
            size="small"
            style="width: 100%;"
            @change="changeList"
          >
            <el-option
              v-for="item in projectList"
              :key="item.userId"
              :label="item.nickName"
              :value="item.userId"
            />
            <template slot="prefix">
              <i slot="prefix" class="el-input__icon el-icon-search"></i>
            </template>
            <!-- <i slot="suffix" class="el-input__icon el-icon-search"></i> -->
          </el-select>
          <vue-scroll ref="scroll">
            <el-tree
              class="proj-list"
              :data="projectList"
              ref="vuetree"
              :props="defaultProps"
              highlight-current
              node-key="userId"
              default-expand-all
              @node-click="clickTree"
            />
          </vue-scroll>
        </div>
      </el-col>
      <el-col :span="18" :xs="24" class="" style="border-left: 1px solid #dddcdc;margin-left: -1px;">
        <h3 class="table-tit">组织角色</h3>
        <div class="card">
          <div class="card_title">
            <div class="tit">所属组织</div>
          </div>
          <div class="card_content">
            <div class="card_text" v-for="(item,index) in organizeList" :key="index">
              {{item.orgName}}
              <el-button type="text" class="tx-btn" @click="deleteData(item)">删除</el-button>
            </div>
          </div>

        </div>
        <div class="card">
          <div class="card_title">
            <div class="tit">角色权限</div>
            <el-button type="text" class="ct_btn" @click="modifyForm">编辑</el-button>
          </div>
          <div class="card_content">
            <div class="card_span" v-for='(item,index) in roleName' :key="index">
              <div class="cdsp_l">
                <img src="./image/seize.png" alt="">
                {{item}}
              </div>

            </div>
          </div>
        </div>
      </el-col>
    </el-row>
    <addForm
      :state="state"
      :detailData="detailData"
      :roleData='roleData'
      :roleList='roleList'
      :roleIdList="roleIds"
      :visible="visible"
      @on-close="closeForm"
    />
  </div>
</template>

<script>
import { getLtable, detailUser, getRoleList,detailOrganize,deleteFn } from "@/api/system/enterpriseUser";
import addForm from "./modules/add-form";
export default {
  name: "enterpriseUser",
  data() {
    return {
      userId: null,
      projectList: [],
      pojList: [],
      loading: false,
      tableData: [],
      defaultProps: {
        children: "children",
        label: "nickName"
      },
      visible: false,
      state: null,
      detailData: {},
      total: 0,
      roleData: {},
      roleList: [],
      roleIds: [],
      roleName: [],
      organizeList:[],
    };
  },
  components: {
    addForm
  },
  async created() {
    await this.getSelect();
    this.changeList();
    this.detailOrganize()
  },
  mounted() {},
  computed: {
    param() {
      return {
        userId: this.userId,
        detailData: {}
      };
    }
  },
  methods: {
    async showForm(flag) {
      this.visible = flag;
    },
    async closeForm(flag) {
      console.log(flag);
      if (flag) {
        this.detailOrganize()
      }
      this.showForm(false);
    },

    async modifyForm(item) {
      this.state = "modify";
      this.showForm(true);
    },
    async deleteData(item) {
      console.log(item);
      return new Promise(res => {
        this.$confirm("是否确认删除?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(async () => {
            const re = await deleteFn({
              organizationid:item.organizationid,
              accountid:item.accountid,
              type:item.type,
            });
            if (re.code === 200) {
              this.$message({
                message: re.msg,
                type: "success",
                duration: 1000
              });
              // this.getTable();
            }
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除"
            });
          });
      });
    },
    async getSelect() {
      console.log(this.$store.state.user.enterpriseId);
      const re = await getLtable({
        enterpriseId: this.$store.state.user.enterpriseId
      });
      this.projectList = re.rows;
      this.userId = this.$lGet(this,'projectList.0.userId',null)
      // this.userId = this.projectList[0].userId;
    },
    async detailOrganize() {
      const re = await detailOrganize(this.userId)
      console.log(re);
      this.organizeList = re.orgNames
      this.roleData = re.data
      this.roleName = re.data.roles.map(i => i.roleName)
      this.roleIds = re.data.roles.map(i => i.roleId)
      this.roleList = re.roles
      this.roleIds = re.roleIds
    },
    async detailUser() {
      const re = await detailUser(this.userId)
      this.roleIds = re.roles.map(i => i.roleId)
      // this.roleName = re.data.roles.map(i => i.roleName)
      // console.log();
    },
    async changeList() {
      this.$nextTick(() => {
        this.$refs["vuetree"].setCurrentKey(this.userId);
      });
    },

    clickTree(data) {
      this.userId = data.userId;
      this.detailOrganize()
    }
  }
};
</script>

<style scoped lang="scss">
::v-deep {
  .el-col {
    padding: 0 !important;
    .warp {
      margin: 16px 20px 0 30px;
    }
  }
  .proj-list{
    .el-tree-node{
      border-bottom: 1px solid #dddcdc;
    }
    .el-tree-node__content{
      height: 36px;
    }
  }
}

.card{
  border: 1px solid #dddcdc;
  margin: 17px;
}
.card_title{
  height: 40px;
  background: #e9f0f0;
  display: flex;
  @include flex(flex-start, center);
  .tit{
    border-left: 4px solid #1ab394;
    // line-height: 40px;
    margin-left: 16px;
    padding-left: 10px;
    font-weight: normal;
    font-size: 14px;
    color: #666666;
  }
  .ct_btn{
    margin: 0 16px 0 auto;
  }
}
.card_content{
  margin: 10px 0;
}
.card_text{
  line-height: 40px;
  margin-left: 20px;
}
.tx-btn{
  color:#F56C6C !important;
  margin-left: 17px;
}
.card_span{
  display: inline-block;
  line-height: 40px;
  margin-left: 20px;
  .cdsp_l{
    @include flex(flex-start, center);
    img {
      margin-right: 10px;
    }
  }
}
.cad-container {
  margin-right: 20px;
  .search {
    padding: 10px 20px;
    border-right: 1px solid #dddcdc;
  }
  .table-tit {
    font-size: 16px;
    font-weight: normal;
    padding: 16px 30px;
    margin: 0;
    border-bottom: 1px solid #dddcdc;
  }
  .proj-list {
    margin: 10px 0;
    ::v-deep {
      .el-tree-node:focus > .el-tree-node__content {
        background-color: #ecf2f2;
      }
    }
  }
}
</style>
