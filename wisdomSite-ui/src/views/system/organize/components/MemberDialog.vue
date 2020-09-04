<template>
  <el-dialog :visible.sync="flag" width="800px" @open="handleOpen" @close="closeDialog" :show-close="false" :close-on-click-modal="false">
    <div class="member-dialog-wrap">
      <i class="el-icon-close close-btn" @click="closeDialog"></i>
      <el-tabs v-model="activeName">
        <el-tab-pane label="添加新成员" name="first">
          <el-form
            ref="memberForm"
            :model="memberForm"
            :rules="memberRules"
            size="medium"
            label-width="140px"
            style="margin-top: 20px"
          >
            <el-form-item label="手机号" prop="phonenumber" >
              <el-input  v-model="memberForm.phonenumber"></el-input>
            </el-form-item>
            <el-form-item label="姓名：" prop="nickName">
              <el-input  v-model="memberForm.nickName"></el-input>
            </el-form-item>
            <el-form-item label="备注：" prop="remark">
              <el-input v-model="memberForm.remark"></el-input>
            </el-form-item>
            <el-form-item label="角色设置：" prop="roleIds">
              <el-checkbox-group v-model="memberForm.roleIds">
                <el-checkbox v-for="role in roleList" :label="role.roleId" :key="role.roleId">{{role.roleName}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="选择已有成员" name="second" v-if="isCreate">
          <el-input
            placeholder="按姓名/手机号搜索"
            suffix-icon="el-icon-search"
            v-model="queryParams.phonenumber"
            @change="getMemberList"
            style="margin-top: 20px"
          />
          <Table :tableData="tableData" :tableConfig="tableConfig" @select="selected" ref="memberTable" class="member-table">
            <template v-slot:selection>
              <el-table-column fixed="left" type="selection" align="center" width="50px"></el-table-column>
            </template>
          </Table>
          <div class="has-sel">
            <div class="has-num">已选择（{{selection.length || 0}}）人</div>
            <div class="mem-tags">
              <el-tag
                v-for="member in selection"
                :key="member.userId"
                closable
                @close="removeSel(member)"
                type="info">
                {{member.nickName}}
              </el-tag>
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
    <span slot="footer" class="dialog-footer">
      <template v-if="activeName==='first'">
        <el-button type="primary" @click="isCreate === false?handleUpdate():handleAdd(true)">保存</el-button>
        <el-button v-if="isCreate" type="primary" @click="isCreate === false?handleUpdate():handleAdd(false)">保存并继续添加</el-button>
      </template>
      <template v-else>
        <el-button type="primary" @click="addAlreadyMember()">保存</el-button>
      </template>
    </span>
  </el-dialog>
</template>

<script>
  import {
    addOrganize,
    getAllRole,
    addMember,
    updateMember,
    getMemberList,
    selAlreadyMember,
    getUserInfo,
    checkOrgUser
  } from '@/api/system/organize';
  import _ from 'lodash';
  import Table from "@/components/Table";
  export default {
    name: 'MemberDialog',
    components: {
      Table
    },
    props: {
      dialogFlag: {
        required: true,
        type: Boolean,
        default: false,
      },
      isCreate: {
        type: Boolean,
        default: true,
      },
      isMove: {
        type: Boolean,
        default: false,
      },
      memberForms: {
        type: Object,
        default: () => {},
      },
    },
    data() {
      let checkUser = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('电话不能为空'));
        }
        getUserInfo(value).then(res => {
          if (res.hasOwnProperty('user')) {
            this.memberForm = { ...{...res.user }, ...this.memberForm, roleIds: res.roleIds }
          }
          callback();
        })
/*        setTimeout(() => {
          if (!Number.isInteger(value)) {
            callback(new Error('请输入数字值'));
          } else {
            if (value < 18) {
              callback(new Error('必须年满18岁'));
            } else {
              callback();
            }
          }
        }, 1000);*/
      };
      return {
        queryParams: {
          // pageNum: 1,
          // pageSize: 5,
          enterpriseId: this.$store.state.user.enterpriseId,
          phonenumber: '',
          // organizationId: '',
          // type: '',
        },
        tableConfig: [
          { slot: 'selection' },
          { prop: "nickName", label: "姓名" },
          { prop: "phonenumber", label: "手机号码" },
          { prop: "remark", label: "备注" },
        ],
        total: 0,
        tableData: [],
        memberForm: {
          roleIds: [],
        },
        treeListProps: {
          label: 'name',
          value: 'id',
        },
        roleList: [],
        roleIds: [],
        deviceType: [],
        selection: [], //被选召的人
        memberRules: {
          phonenumber: [
            { validator: checkUser, trigger: 'blur' },
          ],
          nickName: [
            { required: true, message: '请填写姓名', trigger: 'blur' },
          ],
          roleIds: [
            { type: 'array', required: true, message: '请至少选择一个角色', trigger: 'blur' },
          ],
        },
        activeName: 'first',
      };
    },
    created() {

    },
    methods: {
      /** 查询 列表 */
      getMemberList() {
        getMemberList(this.queryParams).then(response => {
          this.tableData = response.rows;
          this.total = response.total;
        });
      },
      /*获取所有的角色*/
      async getAllRole() {
        const { data } = await getAllRole();
        console.log(data)
        this.roleList = data;
      },
      /* 添加 */
      handleAdd(close) {
        this.$refs.memberForm.validate(async (valid) => {
          if (!valid) return;
          try {
            await addMember(this.memberForm);
            this.msgSuccess('添加成功');
            if (close) {
              this.flag = false;
              this.$emit('query');
            } else {
              this.$refs.memberForm.resetFields();
            }
          } catch (e) {
            console.log(e);
          }
        });
      },
      /* 添加已有成员 */
      async addAlreadyMember() {
        // console.log(this.selection.map(user => user.userId))
        const reqData = {
          accountIds: _.map(this.selection, 'userId').join(','),
          organizationId: this.memberForm.organizationId,
          type: this.memberForm.type,
        };
        await selAlreadyMember(reqData);
        this.msgSuccess('添加成功');
        this.flag = false;
        this.$emit('query');
      },
      /* 编辑 */
      handleUpdate() {
        this.$refs.memberForm.validate(async (valid) => {
          if (!valid) return;
          try {
            await updateMember(this.memberForm);
            this.msgSuccess('编辑成功');
            this.flag = false;
            this.$emit('query');
          } catch (e) {
            console.log(e);
          }
        });
      },
      /* 关闭 */
      closeDialog() {
        this.memberForm = {
          roleIds: [],
        };
        this.$refs.memberForm.resetFields();
        this.flag = false;
      },
      /*打开弹框*/
      handleOpen() {
        console.log('open')
        this.getAllRole();
        this.getMemberList();
      },
      /* 员工选择 */
      selected(selection, row) {
        if (selection.length > this.selection.length) {
          checkOrgUser({
            userId: row.userId,
            organizationId: this.memberForm.organizationId,
            type: this.memberForm.type,
          }).then(res => {
            this.selection = selection;
          }).catch(err => {
            this.removeSel(row);
          });
        } else {
          this.selection = selection;
        }
      },
      removeSel(tag) {
        this.$refs.memberTable.$refs.tableData.toggleRowSelection(tag);
      },
    },
    computed: {
      flag: {
        get() {
          return this.dialogFlag;
        },
        set(val) {
          this.$emit('update:dialogFlag', val);
          // this.$emit('update:memberForms', {});
          this.isMove ? this.$emit('update:isMove', false) : ''
        },
      },
    },
    watch: {
      memberForms(val) {
        console.log('======================================',val)
        this.memberForm = Object.assign({}, val);
        // this.memberForm.officeId = Number(this.memberForm.officeId);
      },
    },
  };
</script>

<style lang='scss' scoped>
  /deep/.el-dialog__header{
    border-bottom: 0;
  }
  /deep/.el-dialog__body{
    padding-top: 0;
    margin-top: -50px;
  }
  /deep/.member-table th .el-checkbox{
    display: none;
  }
  .member-dialog-wrap{
    .close-btn{
      float: right;
      cursor: pointer;
      position: absolute;
      top: 20px;
      right: 20px;
      font-size: 16px;
      z-index: 99;
    }
    .member-table{
      margin: 30px 0;
    }
    .has-sel{
      line-height: 24px;
      display: flex;
      .has-num{
        width: 140px;
      }
    }
    /deep/.el-tabs__item{
      font-size: 14px !important;
      font-weight: normal;
      color: #666;
    }
    /deep/.el-tabs__nav-wrap::after{
      height: .5px;
    }
  }
</style>
