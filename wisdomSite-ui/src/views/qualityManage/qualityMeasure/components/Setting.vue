<template>
  <div class="measure-container">
    <div class="l-box">
      <div class="tit">分项工程（{{projList.length}}）<el-button class="add-btn" type="primary" @click="showAdd">新增分项工程</el-button></div>
      <el-input
        placeholder="分项工程名称搜索"
        suffix-icon="el-icon-search"
        v-model="keyWords"
       >
      </el-input>
      <ul class="proj-list">
        <li :class="`proj-item ${selId===item.id?'active':''}`"
            v-for="item in projList"
            :key="item.id"
            @click="getSubDetail(item.id)"
        ><span>{{item.subitemProject}}</span> <el-button @click="handleDel(item.id)" type="text" size="mini">删除</el-button></li>
      </ul>
    </div>
    <div class="r-box">
      <div class="tit">分项工程-{{!selId?'新增':'编辑'}}</div>
      <div class="from-wrap">
        <el-form
          ref="dataForm"
          :model="dataForm"
          :rules="dataRules"
          size="medium"
          label-width="140px"
        >
          <el-form-item label="分项工程名称" prop="wsEnterpriseMeasuredrealquantitySetup.subitemProject" >
            <el-input  v-model="dataForm.wsEnterpriseMeasuredrealquantitySetup.subitemProject"></el-input>
          </el-form-item>
          <el-form-item label="实测内容：" prop="wsEnterpriseMeasuredrealquantitySetupContent">
            <div class="measure-cont-wrap">
              <el-row type="flex" class="head">
                <el-col :span="5">实测内容</el-col>
                <el-col :span="5">最小值</el-col>
                <el-col :span="5">最大值</el-col>
                <el-col :span="5">单位</el-col>
                <el-col :span="4">操作</el-col>
              </el-row>
              <el-row  v-for="(item, index) in dataForm.wsEnterpriseMeasuredrealquantitySetupContent" :key="item.key">
                <el-col :span="5">
                  <el-form-item
                    :prop="'wsEnterpriseMeasuredrealquantitySetupContent.' + index + '.measuredContent'"
                    label=""
                    :rules="[{ required: true, message: '请输入实测内容', trigger: 'blur' }]">
                    <el-input v-model="item.measuredContent"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="5">
                  <el-form-item
                    :prop="'wsEnterpriseMeasuredrealquantitySetupContent.' + index + '.minNum'"
                    label=""
                    :rules="[{ required: true, message: '请输入最小值', trigger: 'blur' }, { type: 'number', message: '必须为数字值'}]">
                    <el-input v-model="item.minNum"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="5">
                  <el-form-item
                    :prop="'wsEnterpriseMeasuredrealquantitySetupContent.' + index + '.maxNum'"
                    label=""
                    :rules="[{ required: true, message: '请输入最大值', trigger: 'blur' }, { type: 'number', message: '必须为数字值'}]">
                    <el-input v-model="item.maxNum"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="5">
                  <el-form-item
                    :prop="'wsEnterpriseMeasuredrealquantitySetupContent.' + index + '.company'"
                    label=""
                    :rules="[{ required: true, message: '请输入单位', trigger: 'blur' }]">
                    <el-input v-model="item.company"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="4">
                  <el-button type="text" @click.prevent="delMeasure(item)">删除</el-button>
                </el-col>
              </el-row>
            </div>
            <p class="add-measure" @click="addMeasure">+新增实测内容</p>
            <div class="save-wrap">
              <el-button type="primary" @click="!selId?handleAdd():handleEdit()">保存</el-button>
            </div>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
  import _ from 'lodash';
  import {
    getSubProjList,
    getSubProjDetail,
    addSubProj,
    editSubProj,
    delSubProj,
  } from "@/api/quality/measure";

  export default {
    name: "Setting",
    data() {
      return {
        // 查询参数
        selId: '',
        keyWords: '',
        projList: [],
        dataForm: {
          wsEnterpriseMeasuredrealquantitySetup: {
            subitemProject: ''
          },
          wsEnterpriseMeasuredrealquantitySetupContent:[
            {
              measuredContent: '',
              minNum: '',
              maxNum: '',
              company: '',
            }
          ]
        },
        dataRules: {
          'wsEnterpriseMeasuredrealquantitySetup.subitemProject': [
            { required: true, message: '请填写分项工程名称', trigger: 'blur' },
          ],
          'wsEnterpriseMeasuredrealquantitySetupContent': [
            { required: true, message: '请填写分项工程名称', trigger: 'blur' },
          ]
        }
      };
    },
    watch: {
      keyWords: _.debounce(function () {
        this.getSubProjList();
      }, 500),
    },
    created() {
      this.getSubProjList()
    },
    methods: {
      getSubProjList() {
        getSubProjList(this.keyWords).then(res => {
          this.projList = res.rows
        })
      },
      async getSubDetail(id) {
        const { data } = await getSubProjDetail(id);
        this.dataForm = data;
        this.selId = id;
      },
      addMeasure() {
        this.dataForm.wsEnterpriseMeasuredrealquantitySetupContent.push({
          measuredContent: '',
          minNum: '',
          maxNum: '',
          company: '',
          key: Date.now()
        })
      },
      delMeasure(item) {
        // if(item.id) {
        if (false) {
          // this.$confirm('确认删除?', "警告", {
          //   confirmButtonText: "确定",
          //   cancelButtonText: "取消",
          //   type: "warning"
          // }).then(() => {
          //   delChannel(item.id).then(res => {
          //     //TODO 这里有待优化
          //     const index = this.channForm.wsSetupInfoList.indexOf(item)
          //     if (index !== -1) {
          //       this.channForm.wsSetupInfoList.splice(index, 1)
          //     }
          //     this.msgSuccess("删除成功");
          //   });
          // }).catch(() => {});
        } else {
          const index = this.dataForm.wsEnterpriseMeasuredrealquantitySetupContent.indexOf(item)
          if (index !== -1) {
            this.dataForm.wsEnterpriseMeasuredrealquantitySetupContent.splice(index, 1)
          }
        }
      },
      showAdd() {
        this.selId = '';
        this.resetFrom();
      },
      handleAdd() {
        this.$refs.dataForm.validate(async (valid) => {
          if (!valid) return;
          try {
            await addSubProj(this.dataForm);
            this.resetFrom();
            this.getSubProjList();
            this.msgSuccess('添加成功');
          } catch (e) {
            console.log(e);
          }
        });
      },
      handleEdit() {
        this.$refs.dataForm.validate(async (valid) => {
          if (!valid) return;
          try {
            await editSubProj(this.dataForm);
            this.getSubProjList();
            this.msgSuccess('编辑成功');
          } catch (e) {
            console.log(e);
          }
        });
      },
      resetFrom() {
        this.dataForm = {
          wsEnterpriseMeasuredrealquantitySetup: {
            subitemProject: ''
          },
          wsEnterpriseMeasuredrealquantitySetupContent:[
            {
              measuredContent: '',
              minNum: '',
              maxNum: '',
              company: '',
            }
          ]
        };
      },
      async handleDel(id) {
        try {
          await this.$confirm('确定删除吗?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          });
          await delSubProj(id);
          await this.getSubProjList();
          this.msgSuccess('删除成功');
        } catch (e) {
          console.log(e)
        }
      }
    }
  };
</script>

<style scoped lang="scss">
  .measure-container{
    border-top: 1px solid #dddcdc;
    min-height: 600px;
    display: flex;
    .tit{
      height: 60px;
      line-height: 60px;
      text-indent: 20px;
      .add-btn{
        float: right;
        margin-top: 14px;
      }
    }
    .l-box{
      width: 380px;
      padding-right: 20px;
      box-sizing: border-box;
      border-right: 1px solid #dddcdc;
      .proj-list{
        color: #666666;
        font-size: 12px;
        line-height: 34px;
        padding: 0;
        .proj-item{
          display: flex;
          justify-content: space-between;
          padding-right: 10px;
          padding-left: 20px;
          box-sizing: border-box;
          &.active{
            background-color: rgba(236, 242, 242, 1);
          }
          &:hover{
            background-color: rgba(236, 242, 242, 1);
          }
        }
      }
    }
    .r-box{
      flex: 1;
      .measure-cont-wrap{
        border: 1px solid #eee;
        border-bottom: 0;
      }
      /deep/{
        .head{
          .el-col{
            padding: 0;
          }
        }
        .el-row{
          border-bottom: 1px solid #eee;
        }
        .el-col{
          text-align: center;
          padding: 10px;
          box-sizing: border-box;
        }
      }
      .add-measure{
        text-align: center;
        line-height: 30px;
        border: 1px dashed #00b588;
        color: #00b588;
        cursor: pointer;
      }
    }
  }
</style>
