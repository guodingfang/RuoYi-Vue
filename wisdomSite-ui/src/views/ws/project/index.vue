<template>
  <div style="padding:15px 0 0 15px;">
    <el-tabs v-model="activeIndex" class="el-menu-demo" @tab-click="handleSelect">
      <el-tab-pane index="1" label="项目库" name="first">
        <div class="addProject" style="display:flex;">
          <div style="margin-left:10px;">全部（{{total}}）</div>
          <el-button style="background:#09a17b;border-radius:5px;color:#fff;margin-right:20px"
                     @click="dialogAddProject = true">新增项目
          </el-button>


        </div>
        <!-- 实验表格 -->
        <el-table
          :data="projectList"
          stripe

          style="width: 100%">
          <el-table-column
            align="center"
            prop="projectName"
            label="项目名称"
            min-width="26%"
            width="">
          </el-table-column>
          <el-table-column
            align="center"
            prop="projectUnitName"
            label="归属分公司"
            min-width="20%"
            width="">
          </el-table-column>
          <el-table-column
            align="center"
            prop="projectPersonName"
            label="项目负责人"
            min-width="12%"
            width="">
          </el-table-column>

          <el-table-column
            align="center"
            prop="projectAddress"
            label="地址"
            min-width="40%"
            width="">
          </el-table-column>

          <el-table-column
            align="center"
            label="操作"
          >
            <template slot-scope="scope">
              <el-button @click="handleToProjectDetail(scope.row,1)" plain size="middle" style="color:#058061;">项目概况
              </el-button>
              <el-button @click="handleToProjectDetail(scope.row,2)" plain size="middle" style="color:#058061;">工程信息
              </el-button>
            </template>
          </el-table-column>
        </el-table>

        <!-- 新增项目弹框 -->
        <el-dialog title=""
                   :visible.sync="dialogAddProject"
                   width="70%;"
                   append-to-body
                   custom-class="el-dialog__header"
                   :model="listQuery"
        >
          <div style="">

            <!-- 项目名称 -->
            <div class="row-style mb20">
              <p class="com-left">
                项目名称:
                <span style="color: red;">*</span>
              </p>
              <div class="com-right">
                <label for="">
                  <input type="text" v-model="listQuery.projectName" placeholder="请输入项目名称" class="input-l270">
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
                  <input type="text" v-model="listQuery.projectAbbreviation" placeholder="请输入项目别名" class="input-l270">
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
                  <input type="text" v-model="listQuery.projectAddress" placeholder="请输入项目地址" class="input-l270">
                </label>
              </div>

            </div>

            <!-- 归属分公司/部门 -->
            <div class="row-style mb20">
              <p class="com-left">

                归属分公司/部门:
                <span style="color: red;">*</span>
              </p>
              <el-select placeholder="" v-model="listQuery.projectUnitName" class="ml5 com-right">
                <el-option v-for="item in cities" :label="item.label" :value="item.value" :key="item.value"></el-option>
              </el-select>

            </div>


            <!--项目负责人 -->
            <div class="row-style mb20">
              <p class="com-left">
                项目负责人:
                <span style="color: red;margin-left:5px;">*</span>
              </p>
              <el-select class="com-right" placeholder="请选择项目负责人" v-model="listQuery.projectPersonName">
                <el-option v-for="item in cities" :label="item.label" :value="item.value" :key="item.value"></el-option>
              </el-select>
            </div>

            <!--                &lt;!&ndash;项目类型 &ndash;&gt;-->
            <!--                <div class="row-style mb20">-->
            <!--                    <p class="com-left">-->
            <!--                        项目类型:-->
            <!--                        <span style="color: red;">*</span>-->
            <!--                    </p>-->
            <!--                    <el-select  placeholder="请选择项目类型" v-model="citys" class="com-right">-->
            <!--                    <el-option v-for="item in cities" :label="item.label" :value="item.value" :key="item.value"></el-option>-->
            <!--                    </el-select>-->
            <!--                </div>-->

            <!-- 项目状态 -->
            <div style="display:flex;" class="row-style mb20">
              <div class="" style="display:flex;align-items:center;   height:35px;">
                <p class="com-left">
                  项目状态:
                </p>
                <div style="width:300px;">
                  <el-select placeholder="请选择项目状态" v-model="listQuery.projectStatus" class="com-right">
                    <el-option v-for="item in projectStatus" :label="item.label" :value="item.value"
                               :key="item.value"></el-option>
                  </el-select>
                </div>
              </div>


            </div>
            <div class="row-style mb20" style="display:flex;align-items:center;height:35px;">
              <p class="com-left" style="width:100px;">
                项目规模:
              </p>
              <div style="width:300px;">
                <el-radio-group v-model="listQuery.buildScale" class="com-right">
                  <el-radio :label="3">大型</el-radio>
                  <el-radio :label="2">中型</el-radio>
                  <el-radio :label="1">小型</el-radio>
                </el-radio-group>
              </div>
            </div>
            <div style="text-align:right;width:100%;">
              <el-button @click="dialogAddProject = false">取 消</el-button>
              <el-button style="background:#09a17b;margin-right:35px;" type="primary" @click="add">确定</el-button>

              <!-- <el-button type="primary" @click="dialogVisible = false">确 定</el-button> -->
            </div>

          </div>
        </el-dialog>
      </el-tab-pane>

      <el-tab-pane index="2" label="里程碑设置" name="second">
        <!-- 里程碑设置 -->
        <div
          style="width:650px;height:50px;line-height:50px;display:flex;justify-content: space-between;align-items:center;">
          <span style="margin-right:15px;">项目里程碑模板</span>
          <el-button plain size="middle" style="margin-right:15px;color:#058061;font-weight:400;height:35px;"
                     @click="openAddMilestone=true">新增
          </el-button>
        </div>

        <el-table
          :data="milestoneList"
          border
          style="width: 652px;">

          <el-table-column
            prop="milepostName"
            label="里程碑名称"
            min-width="20%"
            width="300">
          </el-table-column>
          <el-table-column
            align="center"
            label="操作"
            min-width="30%"
            width="350"
          >
            <template slot-scope="scope">
              <el-button @click="handleClickToUp(scope.$index,scope.row)" plain size="middle" style="color:#058061;">
                上移
              </el-button>
              <el-button @click="handleClickToDown(scope.$index,scope.row)" plain size="middle" style="color:#058061;">
                下移
              </el-button>
              <el-button @click="handleDelete(scope.$index,scope.row)" plain size="middle" style="color:#058061;">删除
              </el-button>
            </template>
          </el-table-column>

        </el-table>

        <!-- 新增里程碑的弹框 -->
        <el-dialog :title="titleMile" :model="insertParam" :visible.sync="openAddMilestone" width="600px" center>
          <el-form>
            <!-- <el-col :span="12"> -->
            <el-form-item label="里程碑名称" prop="leader">
              <el-input v-model="insertParam.milepostName" placeholder="请输入里程碑名称" maxlength="20"/>
            </el-form-item>
            <!-- </el-col> -->
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="openAddMilestone = false">取 消</el-button>
            <el-button type="primary" @click="submitMilestone">确 定</el-button>
          </span>
        </el-dialog>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import {listProject, getProjectById, addProject, updateProject, deleteProject, exportProject} from "@/api/ws/project";
  import {
    listProjectMilepost,
    getProjectMilepostById,
    removeProjectMilepost,
    addProjectMilepost,
    updateProjectMilepost,
    deleteProjectMilepost
  } from "@/api/ws/projectMilepost";


  const defaultParam = {
    id: undefined,
    projectNo: undefined,
    projectName: undefined,
    projectAbbreviation: undefined,
    projectAddress: undefined,
    projectUnitName: undefined,
    projectPersonName: undefined,
    projectStatus: undefined,
    buildScale: undefined,
    buildStructureType: undefined,
    numMonomers: undefined,
    buildArea: undefined,
    groundArea: undefined,
    buildHeight: undefined,
    groundLayernum: undefined,
    undergroundDepth: undefined,
    foundationpitDepth: undefined,
    constructionUnit: undefined,
    designer: undefined,
    controlUnit: undefined,
    ceneralcontractUnit: undefined,
    safetySupervision: undefined,
    qualitySupervision: undefined,
    lnvestmentType: undefined,
    bidNo: undefined,
    contractCost: undefined,
    constructionPermitUrl: undefined,
    startTime: undefined,
    projectManagerName: undefined,
    contactNumber: undefined,
    payBank: undefined,
    bankDeposit: undefined,
    payAccountno: undefined,
    payDate: undefined,
    qualityObjectives: undefined,
    safetyObjectives: undefined,
    contractRecordno: undefined,
    remarks: undefined,
  };
  const defaultData = {
    milepostName: undefined,
    id: undefined,
    milepostSort: undefined
  };
  // 项目查询参数
  const queryParams = {
    pageNum: 1,
    pageSize: 10,
    projectNo: '',
    projectName: '',
  };
  // 项目里程碑查询参数
  const queryData = {
    pageNum: 1,
    pageSize: 10,
    milepostName: '',
    milepostSort: null,
  };
  export default {
    name: 'Project',
    // props:[
    //   closeOnClickModal:{
    //     type:Boolean,
    //     default:false
    //   }
    // ],

    data() {
      return {
        listQuery: Object.assign({}, defaultParam),
        typeOptions: Object.assign({}, queryParams),
        typeQuery: Object.assign({}, queryData),
        insertParam: Object.assign({}, defaultData),
        // 遮罩层
        loading: true,
        // 项目 表格数据
        projectList: null,
        //里程碑模板 表格数据
        milestoneList: null,
        // 总条数
        total: 0,
        wsProjectMilepost: {},
        wsProjectMilepostUp: {},
        arr: [],
        // 顶部导航默认选中
        activeIndex: 'first',
        dialogAddProject: false,//新增项目弹框
        openAddMilestone: false,//里程碑弹框
        cities: [
          {value: 1, label: '北京'},
          {value: 'Shanghai', label: '上海'},
          {value: 'Nanjing', label: '南京'},
          {value: 'Chengdu', label: '成都'},
          {value: 'Shenzhen', label: '深圳'},
          {value: 'Guangzhou', label: '广州'}
        ],

        projectStatus:[
          {
            label: '在建',
            value: "1"
          },
          {
            label: '完工',
            value: "2"
          },
          {
            label: '未建',
            value: "3"
          },
        ],
        radio: 3,
        titleMile: '新增项目里程碑',
      }
    },
    created() {
      this.getProjectList();
      this.getProjectMeilpostList();
    },
    methods: {
      /** 查询项目 列表 */
      getProjectList() {
        this.loading = true;
        listProject(this.typeOptions).then(response => {
          this.projectList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },

      getProjectMeilpostList() {
        /** 查询项目里程碑模板 列表 */
        listProjectMilepost(this.typeQuery).then(response => {
            this.milestoneList = response.rows;
          }
        );
      },

      /** 添加按钮操作 */
      add: function () {

        addProject(this.listQuery).then(response => {
          if (response.code == 200) {
            this.$message({
              type: 'success',
              message: response.msg,
              duration: 1000
            })
          }
        });

      },
      /** 添加项目里程碑模板操作 */
      submitMilestone() {
        addProjectMilepost(this.insertParam).then(response => {
          this.$message({
            message: '添加成功',
            type: 'success',
            duration: 1000
          });
          this.openAddMilestone = false;
          this.getProjectMeilpostList();
        });
      },

      // 导航点击
      handleSelect(key, keyPath) {
      },

      // 上移
      handleClickToUp(index, value) {
        let currentSort = index;
        if (currentSort > 0) {
          let upDate = this.milestoneList[currentSort - 1];
          this.wsProjectMilepost.id = upDate.id;
          this.wsProjectMilepost.milepostSort = value.milepostSort;
          this.arr.push(this.wsProjectMilepost);
          this.wsProjectMilepostUp.id = value.id;
          this.wsProjectMilepostUp.milepostSort = upDate.milepostSort;
          this.arr.push(this.wsProjectMilepostUp);
          removeProjectMilepost(JSON.stringify(this.arr)).then(response => {
          this.getProjectMeilpostList()
            }
          );
        } else {
          alert('已经是第一条了，移不动了')
        }
      },
      // 下移
      handleClickToDown(index, value) {
        let currentSort = index;
        if ((currentSort + 1) === this.milestoneList.length) {
          alert('已经是最后一条，不可下移');
        } else {
          let downDate = this.milestoneList[currentSort + 1];
          this.wsProjectMilepostUp.id = value.id;
          this.wsProjectMilepostUp.milepostSort = downDate.milepostSort;

          this.arr.push(this.wsProjectMilepostUp);
          this.wsProjectMilepost.id = downDate.id;
          this.wsProjectMilepost.milepostSort = value.milepostSort;

          this.arr.push(this.wsProjectMilepost);

          removeProjectMilepost(JSON.stringify(this.arr)).then(response => {
              this.getProjectMeilpostList()
            }
          );
        }
      },

      handleDelete(index, row) {
        this.handleClickToDelete(row.id);
      },

      //删除方法
      handleClickToDelete(ids) {
        this.$confirm('是否要删除该广告?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteProjectMilepost(ids).then(response => {
            this.getProjectMeilpostList();
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
          });
        })
      },
      // 跳转到项目概况
      handleToProjectDetail(value, value1) {
        // 跳转路有位置已找到  你自己操作
        let projectInfoJson = value;
        projectInfoJson = JSON.stringify(projectInfoJson);
        sessionStorage .setItem("projectInfoJson",projectInfoJson);
        this.$router.push({path: `/project/detail/${value1}`})
      },


    }
  }
</script>

<style less="scss" scoped>
  .addProject {
    padding: 0 20px 0 20px;
    align-items: center;
    display: flex;
    justify-content: space-between;
  }

  /* 新增项目弹框
   */
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

  .el-dialog__header {
    /* 修改组件自带样式 */
    padding: 20px;
    padding-bottom: 10px;
    border-bottom: 1px solid #f1f1f1 !important;
  }
</style>
