<template>
  <div class="organize-wrap">
    <section class="left-section">
      <el-input
        placeholder="项目名称搜索"
        suffix-icon="el-icon-search"
        v-model="orgName"
      />
      <div class="add-btn-group">
        <el-button icon="el-icon-plus" :disabled="selNode.type===1" class="add_btn" size="mini" @click="addOrgLeaf">组织</el-button>
        <el-button icon="el-icon-plus" :disabled="selNode.type===1" class="add_btn" size="mini" @click="addProjLeaf">项目</el-button>
      </div>
      <div class="tree-con">
        <el-tree
          :data="organizeData"
          :props="defaultProps"
          :expand-on-click-node="false"
          :filter-node-method="filterNode"
          ref="tree"
          node-key="id"
          :default-expanded-keys="expandArr"
          @node-click="handleNodeClick"
        >
          <span :class="['custom-tree-node', data.auth===0?'disable': '', data.id === selNode.id?'has-sel': '']" slot-scope="{ node, data }">
            <span><i v-show="data.type===1" class="el-icon-s-flag"></i>{{ node.label }}</span>
            <span class="tree_right">
              <el-button
                type="text"
                size="mini"
                v-show="data.id === selNode.id"
                @click="data.type===0?updateOrg(node, data):updateProj(node, data)"
              >
                编辑
              </el-button>
              <el-button
                type="text"
                size="mini"
                v-show="data.id === selNode.id"
                @click="data.type===0?delOrgNode(node, data):delProjNode(node, data)"
              >
                删除
              </el-button>
            </span>
          </span>
        </el-tree>
      </div>
    </section>
    <section class="right-section">
      <div class="right-head-box">
        <span>组织成员（{{total}}）</span>
        <el-input
          style="width: 350px"
          placeholder="按姓名/手机号搜索"
          suffix-icon="el-icon-search"
          v-model="queryParams.phonenumber"
          @change="getMemberList"
        />
      </div>
      <div class="right-con">
        <div class="table-btn">
          <el-button
            type="primary"
            style="margin-bottom: 12px;"
            @click="addMember"
          >新增成员</el-button>
        </div>
        <Table :tableData="tableData" :tableConfig="tableConfig">
          <template v-slot:role>
            <el-table-column
              label="角色"
            >
              <template slot-scope="scope">
                -
              </template>
            </el-table-column>
          </template>
          <template v-slot:operate>
            <el-table-column label="操作" align="left">
              <template slot-scope="scope">
                <el-button @click="editMember(scope.row)" type="text">编辑</el-button>
                <el-button @click="delMember(scope.row)" type="text">移除</el-button>
              </template>
            </el-table-column>
          </template>
        </Table>
        <Pagination
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getMemberList"
        />
      </div>
    </section>
    <!--组织弹框-->
    <organ-dialog
      :isCreate="isCreate"
      :dialogFlag.sync="organDialogFlag"
      :organForms.sync="organData"
      @query="getOrgTree"
    />
    <proj-dialog
      :isCreate="isCreate"
      :dialogFlag.sync="projDialogFlag"
      :projForms.sync="projData"
      @query="getOrgTree"
    />
    <member-dialog
      :isCreate="isCreate"
      :dialogFlag.sync="memberDialogFlag"
      :memberForms.sync="memberData"
      @query="getMemberList"
    />
  </div>
</template>

<script>
  import {
    getOrgTree,
    getMemberList,
    getOrgInfo,
    delOrganize,
    delMember,
    getOrgData,
    getMemberData,
  } from '@/api/system/organize';
  import { getProjectById } from "@/api/project/project";
  import { deleteProject } from '@/api/project/project';
  import Table from "@/components/Table";
  import Pagination from "@/components/Pagination";
  import OrganDialog from "./components/OrganDialog";
  import ProjDialog from "./components/ProjDialog";
  import MemberDialog from "./components/MemberDialog";
  import {mapGetters} from "vuex";
  export default {
    name: "OrganizeMana",
    components: {
      MemberDialog,
      ProjDialog,
      OrganDialog,
      Table,
      Pagination,
    },
    data() {
      return {
        orgName: '',
        organizeData: undefined,
        defaultProps: {
          children: "children",
          label: "label"
        },
        tableConfig: [
          { slot: 'operate'},
          { prop: "nickName", label: "姓名" },
          { prop: "phonenumber", label: "手机号码" }
        ],
        total: 0,
        tableData: [],
        selNode: {
          type: this.$store.state.user.curRole.type,
          id: this.$store.state.user.curRole.id,
        },
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          // enterpriseId: 101,
          phonenumber: '',
          organizationId: this.$store.state.user.curRole.id,
          type: this.$store.state.user.curRole.type,
        },
        isCreate: false,
        organDialogFlag: false,
        projDialogFlag: false,
        memberDialogFlag: false,
        projData: {},
        organData: {},
        memberData: {},
        expandArr: []
      }
    },
    created() {
      this.getOrgTree();
      this.getMemberList();
    },
    methods: {
      /** 获取组织树*/
      getOrgTree() {
        getOrgTree().then(res => {
          this.expandArr = [ res.data[0].id ];
          this.organizeData = res.data
        })
      },
      /** 查询 列表 */
      getMemberList() {
        getMemberList(this.queryParams).then(response => {
          this.tableData = response.rows;
          this.total = response.total;
        });
      },
      /** 新增组织框 */
      async addOrgLeaf() {
        const {id, type, label} =  this.selNode;
        if(!this.selNode.id) {
          this.msgError('请选择节点');
          return false;
        }
        this.isCreate = true;
        this.organData = {
          parentId: id,
          parentName: label,
          orgType: type
        }
        this.organDialogFlag = true;
      },
      /** 编辑组织 */
      async updateOrg(node, data) {
        this.isCreate = false;
        const { data: orgData } = await getOrgData(data.id);
        this.organData = orgData
        this.organDialogFlag = true;
      },
      /** 打开项目框 */
      addProjLeaf() {
        const {id, label} =  this.selNode;
        if(!this.selNode.id) {
          this.msgError('请选择节点');
          return false;
        }
        this.isCreate = true;
        this.projData = {
          projectUnitId: id,
          projectUnitName: label
        }

        this.projDialogFlag = true
      },
      /** 更新项目*/
      async updateProj(node, data) {
        this.isCreate = false;
        const { data: projData } = await getProjectById(data.id);
        this.projData = projData
        this.projDialogFlag = true;
      },
      /** 新增成员 */
      addMember() {
        const {id, type} = this.selNode;
        this.isCreate = true;
        this.memberData = {...this.memberData, organizationId: id, type, roleIds: []}
        this.memberDialogFlag = true
      },
      /** 编辑成员 */
      async editMember(row) {
        this.isCreate = false;
        const { data: memberData, roleIds } = await getMemberData(row.userId);
        console.log(memberData)
        this.memberData = { ...memberData, roleIds }
        this.memberDialogFlag = true;
      },
      /** 删除组织*/
      delOrgNode(node, data) {
        if ( node.childNodes.length > 0) {
          this.msgError('请先删除下级组织及项目')
          return false;
        }
        this.$confirm('是否确认删除该组织?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          delOrganize(data.id).then(response => {
            this.getOrgTree();
            this.msgSuccess("删除成功");
          });
        }).catch(function() {});
      },
      /**删除项目*/
      delProjNode(node, data) {
        this.$confirm('是否确认删除该项目?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          deleteProject(data.id).then(response => {
            this.getOrgTree();
            this.msgSuccess("删除成功");
          });
        }).catch(function() {});
      },
      /** 移除成员*/
      delMember(row) {
        this.$confirm('确定移除该成员？', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          const {id, type} = this.selNode;
          delMember({accountid: row.userId, organizationid: id, type}).then(response => {
            this.getMemberList();
            this.msgSuccess("删除成功");
          });
        }).catch(function() {});
      },
      // 筛选节点
      filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
      },
      // 节点单击事件
      handleNodeClick(data) {
        if (data.auth === 0) {
          this.selNode = {}
          return false
        }
        this.selNode = data;
        const {id, type} = data;
        this.queryParams = Object.assign(this.queryParams,{organizationId: id, type})
        // organizationSelect({organizationId: id, type}).then(res => {
        //   console.log(res)
        // })
        // this.queryParams.enterpriseId = data.id;
        this.getMemberList();
      },
    },
    watch: {
      // 根据名称筛选部门树
      orgName(val) {
        this.$refs.tree.filter(val);
      }
    },
  }
</script>

<style scoped lang="scss">
.organize-wrap{
  display: flex;
  justify-content: space-between;
  .left-section{
    width: 400px;
    border-right: 1px solid #dddcdc;
    padding: 10px;
    box-sizing: border-box;
    .add-btn-group{
      display: flex;
      justify-content: space-between;
      margin-top: 10px;
      .add_btn{
        width: 180px;
        height: 30px;
      }
    }
    .tree-con{
      margin-top: 30px;
    }
    .custom-tree-node {
      flex: 1;
      display: flex;
      align-items: center;
      justify-content: space-between;
      font-size: 14px;
      padding-right: 8px;
      &.disable{
        color: #c0c4cc;
      }
      &.has-sel{
        background-color: #ebf7f4;
      }
    }
  }
  .right-section{
    flex: 1;
    .right-head-box{
      display: flex;
      height: 48px;
      justify-content: space-between;
      align-items: center;
      font-size: 16px;
      box-sizing: border-box;
      font-weight: normal;
      margin: 0;
      border-bottom: 1px solid #dddcdc;
      padding: 0 20px;
    }
    .right-con{
      padding: 20px;
      box-sizing: border-box;
    }
  }
}
</style>
