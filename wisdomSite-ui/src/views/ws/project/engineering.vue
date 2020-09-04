<template>
  <div style="padding:20px;width:100%;">
    <el-form :data="projectData">
      <p class="tableP mb20">
        <span>工程属性</span>
      </p>

      <div class="com-table mb25">
        <p class="com-left">建筑用途:</p>
        <!--            <el-select  placeholder="请选择工程用途" v-model="type" class="com-right">-->
        <!--                <el-option v-for="item in cities" :label="item.label" :value="item.value" :key="item.value"></el-option>-->
        <!--            </el-select>-->
      </div>

      <div class="com-table mb25">
        <p class="com-left">建筑结构类型:</p>
        <div class="com-right">
          <label for="">
            <input type="text" placeholder="请输入项目建筑结构类型" class="input-l270" v-model="projectData.buildStructureType">
          </label>
        </div>
      </div>

      <div class="com-table mb25">
        <p class="com-left">单体数量(个):</p>
        <div class="com-right">
          <label for="">
            <input type="number" placeholder="请输入项目单体数量" class="input-l270" v-model="projectData.numMonomers">
          </label>
        </div>
        <p class="com-left">建筑面积(m2):</p>
        <div class="com-right">
          <label for="">
            <input type="number" placeholder="请输入项目建筑面积" class="input-l270" v-model="projectData.buildArea">
          </label>
        </div>
      </div>


      <div class="com-table mb25">
        <p class="com-left">地上面积(m2):</p>
        <div class="com-right">
          <label for="">
            <input type="number" placeholder="请输入项目单体数量" class="input-l270" v-model="projectData.groundArea">
          </label>
        </div>
        <p class="com-left">地下面积(m2):</p>
        <div class="com-right">
          <label for="">
            <input type="number" placeholder="请输入项目建筑面积" class="input-l270" v-model="projectData.undergroundArea">
          </label>
        </div>
      </div>

      <div class="com-table mb25">
        <p class="com-left">建筑高度(m):</p>
        <div class="com-right">
          <label for="">
            <input type="number" placeholder="请输入项目单体数量" class="input-l270" v-model="projectData.buildHeight">
          </label>
        </div>
        <p class="com-left">地上层数:</p>
        <div class="com-right">
          <label for="">
            <input type="number" placeholder="请输入项目建筑面积" class="input-l270" v-model="projectData.groundLayernum">
          </label>
        </div>
      </div>

      <div class="com-table mb25">
        <p class="com-left">地下深度(m):</p>
        <div class="com-right">
          <label for="">
            <input type="number" placeholder="请输入项目单体数量" class="input-l270" v-model="projectData.undergroundDepth">
          </label>
        </div>
        <p class="com-left">基坑深度(m):</p>
        <div class="com-right">
          <label for="">
            <input type="number" placeholder="请输入项目建筑面积" class="input-l270" v-model="projectData.foundationpitDepth">
          </label>
        </div>
      </div>


      <p class="tableP mb20">
        <span>关联单位</span>
      </p>
      <div class="com-table mb25">
        <p class="com-left">建设单位:</p>
        <div class="com-right">
          <label for="">
            <input type="text" placeholder="请输入项目建设单位" class="input-l270" v-model="projectData.constructionUnit">
          </label>
        </div>
        <p class="com-left">设计单位:</p>
        <div class="com-right">
          <label for="">
            <input type="text" placeholder="请输入项目设计单位" class="input-l270" v-model="projectData.designer">
          </label>
        </div>
      </div>

      <div class="com-table mb25">
        <p class="com-left">监理单位:</p>
        <div class="com-right">
          <label for="">
            <input type="text" placeholder="请输入项目监理单位" class="input-l270" v-model="projectData.controlUnit">
          </label>
        </div>
        <p class="com-left">施工单位:</p>
        <div class="com-right">
          <label for="">
            <input type="text" placeholder="请输入项目监理单位" class="input-l270" v-model="projectData.ceneralcontractUnit">
          </label>
        </div>
      </div>

      <div class="com-table mb25">
        <p class="com-left">安全监督单位:</p>
        <div class="com-right">
          <label for="">
            <input type="text" placeholder="请输入项目监理单位" class="input-l270" v-model="projectData.safetySupervision">
          </label>
        </div>
        <p class="com-left">质量监督单位:</p>
        <div class="com-right">
          <label for="">
            <input type="text" placeholder="请输入项目监理单位" class="input-l270" v-model="projectData.qualitySupervision">
          </label>
        </div>
      </div>

      <!-- 施工信息 -->
      <p class="tableP mb20">
        <span>施工信息</span>
      </p>
      <div class="com-table mb25">
        <p class="com-left">投资类型:</p>
        <el-select placeholder="请选择投资类别" v-model="projectData.lnvestmentType" class="com-right">
          <el-option v-for="item in lnvestmentType" :label="item.label" :value="item.value"
                     :key="item.value"></el-option>
        </el-select>
      </div>

      <div class="com-table mb25">
        <p class="com-left">中标编号:</p>
        <div class="com-right">
          <label for="">
            <input type="text" placeholder="请输入项目中标编号" class="input-l270" v-model="projectData.bidNo">
          </label>
        </div>
        <p class="com-left">合同造价(万元):</p>
        <div class="com-right">
          <label for="">
            <input type="number" placeholder="请输入项目合同造价" class="input-l270" v-model="projectData.contractCost">
          </label>
        </div>
      </div>

      <div class="com-table mb25">
        <p class="com-left">施工许可证:</p>
        <div class="com-right">
          <label for="">
            <input type="text" placeholder="请输入项目施工许可证" class="input-l270" v-model="projectData.constructionPermit">
          </label>
        </div>
      </div>

      <div class="com-table mb25">
        <p class="com-left">开工时间:</p>
        <div class="com-right">
          <!-- <label for="">
              <input type="text" placeholder="请输入项目开工时间" class="input-l270" v-model="projectData.buildArea">
          </label> -->
          <el-date-picker
            class="input-1270"
            v-model="projectData.startTime"
            type="date"
            placeholder="选择日期">
          </el-date-picker>
        </div>
        <p class="com-left">竣工时间:</p>
        <div class="com-right">
          <el-date-picker
            class=""
            v-model="projectData.completeTime"
            type="date"
            placeholder="选择日期">
          </el-date-picker>
        </div>
      </div>

      <div class="com-table mb25">
        <p class="com-left">项目经理:</p>
        <div class="com-right">
          <label for="">
            <input type="text" placeholder="请输入项目经理姓名" class="input-l270" v-model="projectData.projectManagerName">
          </label>
        </div>
        <p class="com-left">联系电话:</p>
        <div class="com-right">
          <label for="">
            <input type="number" placeholder="请输入联系电话" class="input-l270" v-model="projectData.contactNumber">
          </label>
        </div>
      </div>

      <div class="com-table mb25">
        <p class="com-left">发薪银行:</p>
        <el-select placeholder="请选择发薪银行" class="com-right" v-model="projectData.payBank">
          <el-option v-for="item in bank" :label="item.label" :value="item.value" :key="item.value"></el-option>
        </el-select>
        <p class="com-left">开户银行:</p>
        <div class="com-right">
          <label for="">
            <input type="text" placeholder="请输入开户银行" class="input-l270" v-model="projectData.bankDeposit">
          </label>
        </div>
      </div>

      <div class="com-table mb25">
        <p class="com-left">发薪账号:</p>
        <div class="com-right">
          <label for="">
            <input type="number" placeholder="请输入发薪账号" class="input-l270" v-model="projectData.payAccountno">
          </label>
        </div>
        <p class="com-left">发薪日期:</p>
        <div class="com-right">
          <label for="">
            <input type="text" placeholder="请输入1-31之间的数" class="input-l270" v-model="projectData.payDate">
          </label>
        </div>
      </div>

      <div class="com-table mb25">
        <p class="com-left">安全目标:</p>
        <div class="com-right">
          <label for="">
            <input type="number" placeholder="请输入安全目标" class="input-l270" v-model="projectData.safetyObjectives">
          </label>
        </div>
        <p class="com-left">质量目标:</p>
        <div class="com-right">
          <label for="">
            <input type="text" placeholder="请输入质量目标" class="input-l270" v-model="projectData.qualityObjectives">
          </label>
        </div>
      </div>

      <div class="com-table mb25">
        <p class="com-left">合同备案号:</p>
        <div class="com-right">
          <label for="">
            <input type="text" placeholder="请输入合同备案号" class="input-l270" v-model="projectData.contractRecordno">
          </label>
        </div>
      </div>

      <div class="com-table mb25">
        <p class="com-left">备注:</p>
        <div class="com-right">
          <label for="">
            <input type="textarea" placeholder="" class="input-l270" style="height:70px;" v-model="projectData.remarks">
          </label>
        </div>
      </div>


      <div class="com-table mb25">
        <p class="com-left">里程碑:</p>
        <div class="com-right project-milestone">
          <el-table
            class="com-table mb25"
            :data="projectMilepost"
            style="">
            <el-table-column
              align="center"
              label="里程碑"
              min-width="20%"
              width="">
              <template slot-scope="scope">{{scope.row.milepostName}}</template>
            </el-table-column>


                                    
            <el-table-column
              align="center"
              label="时间"
              min-width="20%"
              width="">                        
              <template
                 slot-scope="scope">                  
                <el-date-picker
                  class="input-1270"
                  v-model="scope.row.milepostDate"
                  type="date"
                  placeholder="选择日期">  
                  {{scope.row.milepostDate}}
                </el-date-picker>
                                            
              </template>
                                      
            </el-table-column>


          </el-table>
        </div>
      </div>
      <div style="text-align:right;width:100%;">
        <el-button style="background:#09a17b;margin-right:35px;" type="primary" @click="onSubmit()">保存</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
  import {updateProject, findProjectMilestonePlan, updateProjectMilestonePlan} from "@/api/ws/project";
  import {parseTime} from  "@/utils/jlkj"

  export default {
    name: 'Engineering',
    data() {
      return {
        lnvestmentType: [
          {
            label: "政府投资",
            value: 1
          },
          {
            label: "国有企业投资",
            value: 2
          },
          {
            label: "非国有企业投资",
            value: 3
          },
          {
            label: "其他",
            value: 4
          },
        ],
        bank: [
          {
            label: "中国建设银行",
            value: 1
          },
          {
            label: "中国工商银行",
            value: 2
          },
          {
            label: "中国农业银行",
            value: 3
          },
          {
            label: "其他银行",
            value: 4
          },
        ],
        input1: '',
        projectData: '',
        projectMilepost: null,
        wsProjectMilepost: null,
        milepostDate: null,



      }
    },

    methods: {
      loadingProjectInfo() {
        let projectInfoJson = sessionStorage.getItem("projectInfoJson");
        this.projectData =  JSON.parse(projectInfoJson);
        this.activeIndex = this.$route.params.activeIndex

        findProjectMilestonePlan(this.projectData.id).then(response => {
          this.projectMilepost = response.data;
        })

      },
      onSubmit() {
        let wsProjectMilestoneplans = [];
        // let flag1 = false;
        // let flag2 = false;
        for(let i = 0;i<this.projectMilepost.length;i++){
          let param = {
            milepostDate: null,
            milepostId: null
          };
          param.milepostDate = parseTime(this.projectMilepost[i].milepostDate);
          param.milepostId = this.projectMilepost[i].milepostId;
          wsProjectMilestoneplans.push(param)
        }

        let data={
          "wsProject": this.projectData,
          "wsProjectMilestoneplans": wsProjectMilestoneplans
        }
        updateProject(data).then(response => {
         if(response.code === 200){
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

        // updateProjectMilestonePlan(updateDates).then(response =>{
        //   console.log(response)
        //   if (response.code == 200) {
        //     flag2 = true;
        //   }
        // })

      },

    },
    created() {
      this.loadingProjectInfo()
    }

  }
</script>

<style less="scss" scoped>
  .tableP {
    border-left: 3px solid #09a17b;
  }

  .tableP span {
    padding-left: 8px;
    color: #939997;
  }

  .com-table {
    display: table;
    width: 100%;
  }

  .com-right {
    vertical-align: top;
    display: table-cell;
  }

  .com-left {
    display: table-cell;
    width: 135px;
    text-align: right;
    padding-right: 20px;
    line-height: 35px;
  }

  .project-milestone {
    border: 1px solid #e9f2ef;
  }

  .border-bottom {
    border-bottom: 1px solid #e9f2ef;
  }
</style>
