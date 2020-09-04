
<template>
  <div class="" style="margin-left:15px;" >
    <el-form :data="projectData">
      <el-tabs v-model="activeIndex" class="el-menu-demo" >
        <el-tab-pane index="1" label="项目概况" name="1">
          <div style="padding-left:100px;">
            <!-- 项目id -->
            <div style="" class="row-style mb20">

              <p class="com-left">项目id:</p>
              <div class="com-right">
                <label for="">
                  <input value="" type="text" placeholder="" class="input-l270" v-model="projectData.projectNo">
                </label>
                <p style="margin:8px 0;">
                  <small style="font-size: 12px;color: #9da4a5;">支持6-20个字母、数字、下划线,必须字母或数字开头,只能修改一次哦</small>
                </p>
              </div>

            </div>
            <!-- 项目名称 -->
            <div class="row-style mb20">
              <p class="com-left">

                项目名称:
                <span style="color: red;">*</span>
              </p>
              <div class="com-right">
                <label for="">
                  <input type="text" placeholder="请输入项目名称" class="input-l270" v-model="projectData.projectName">
                </label>
              </div>

            </div>

            <!-- 项目别名 -->
            <div class="row-style mb20">
              <p class="com-left">
                项目别名:
              </p>
              <div class="com-right">
                <label for="">
                  <input type="text" placeholder="请输入项目别名" class="input-l270" v-model="projectData.projectAbbreviation">
                </label>
              </div>

            </div>

            <!-- 项目地址 -->
            <div class="row-style mb20">
              <p class="com-left">

                项目地址:
                <span style="color: red;">*</span>
              </p>
              <div class="com-right">
                <label for="">
                  <input type="text" placeholder="请输入项目地址" class="input-l270" v-model="projectData.projectAddress">
                </label>
              </div>

            </div>

            <!-- 归属分公司/部门 -->
            <div class="row-style mb20">
              <p class="com-left">

                归属分公司/部门:
                <span style="color: red;">*</span>
              </p>
              <el-select placeholder="请选择项目负责人" v-model="projectData.projectUnitName" class="ml5 com-right">
                <el-option v-for="item in projectData.projectUnitName" :label="item.label" :value="item.value" :key="item.value"></el-option>
              </el-select>

            </div>


            <!--项目负责人 -->
            <div class="row-style mb20">
              <p class="com-left">
                项目负责人:
                <span style="color: red;margin-left:5px;">*</span>
              </p>
              <div class="com-right">
                <label for="">
                  <input type="text" placeholder="请输入项目别名" class="input-l270" v-model="projectData.projectPersonName">
                </label>
              </div>
            </div>

            <!-- 项目状态 -->
            <div style="display:flex;" class="row-style mb20">
              <div class="" style="display:flex;align-items:center;height:35px;">
                <p class="com-left">
                  项目状态:
                </p>
                <div style="width:300px;">
                  <el-select placeholder="请选择项目状态" v-model="projectData.projectStatus" class="com-right">
                    <el-option v-for="item in projectStatus" :label="item.label" :value="item.value"
                               :key="item.value"></el-option>
                  </el-select>
                </div>
              </div>

              <div class="" style="display:flex;align-items:center;height:35px;">
                <p class="com-left" style="width:100px;">
                  项目规模:
                </p>
                <div style="width:300px;">
                  <el-radio-group v-model="projectData.buildScale" class="com-right">
                    <el-radio :label="3">大型</el-radio>
                    <el-radio :label="2">中型</el-radio>
                    <el-radio :label="1">小型</el-radio>
                  </el-radio-group>
                </div>
              </div>
            </div>
            <div style="text-align:right;width:100%;">
              <el-button style="background:#09a17b;margin-right:35px;" type="primary" @click="onSubmit()">保存</el-button>
            </div>

          </div>
        </el-tab-pane>

        <el-tab-pane index="2" label="工程信息" name="2">
          <engineering/>
        </el-tab-pane>
      </el-tabs>

    </el-form>
  </div>
</template>

<script>
  import {listProject, getProjectById, addProject, updateProject, deleteProject, exportProject} from "@/api/ws/project";
  import Engineering from './engineering'

  export default {
    name: 'Detail',
    components: {Engineering},
    data() {
      return {
        projectStatus: [
          {
            label: "在建",
            value: 1
          },
          {
            label: "完成",
            value: 2
          },
          {
            label: "未建",
            value: 3
          },
        ],

        activeIndex: 1,
        radio: 3,
        projectData: '',
        value1: ''
      }
    },
    methods: {
      loadingProjectInfo() {
        let projectInfoJson = sessionStorage.getItem("projectInfoJson");
        this.projectData =  JSON.parse(projectInfoJson);
        this.activeIndex=this.$route.params.activeIndex
        console.log(this.projectData);//注意JSON转译
      },
      onSubmit() {
        let data={
          "wsProject": this.projectData,
          "wsProjectMilestoneplans": null
        }
        updateProject(data).then(response =>{
          if (response.code === 200) {
            this.$message({
              type: 'success',
              message: response.msg,
              duration: 1000
            })
          }else {
            this.$message({
              type: 'error',
              message: response.msg,
              duration: 1000
            })
          }
        })
      },

    },
    created() {
      this.loadingProjectInfo()
    }
  }
</script>

<style less="scss" scoped>
  /* 项目概况的 */
  .row-style {
    display: table;
    width: 100%;
  }

  .com-left {
    display: table-cell;
    width: 135px;
    text-align: right;
    height: 35px;
    line-height: 35px;
  }

  .com-right {
    display: table-cell;
    padding: 0 20px;
    box-sizing: border-box;
    vertical-align: top;
  }

  .input-l270 {
    width: 100%;
    height: 35px;
    line-height: 35px;
    border: 1px solid #e9f2ef;
    font-size: .85rem;
    padding: 7px 10px 6px;
    color: #939997;
    border-radius: 0;
    box-sizing: border-box;
    overflow: hidden;
  }
</style>
