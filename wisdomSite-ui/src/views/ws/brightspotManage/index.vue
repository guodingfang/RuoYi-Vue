<template>
  <div style="margin-left:15px;">
      <el-tabs v-model="activeIndex" class="el-menu-demo">
          <el-tab-pane index="1" label="项目部亮点" name="first">
              <div>
                  <div class="" style="margin:10px 10px 10px 15px;display:flex;align-items:center;justify-content:space-between;">
                        <div style="margin-left:10px;">全部（{{this.brightspotTableData.length}}）</div>

                        <el-form :inline="true">
                            <el-form-item label="">
                              <el-select
                                v-model="queryParams.projectId"
                                placeholder="项目名称"
                                clearable
                              >
                                <el-option
                                  v-for="item in projectList"
                                  :key="item.projectName"
                                  :label="item.projectName"
                                  :value="item.id"
                                />
                              </el-select>
                            </el-form-item>
                            <el-form-item>
                              <el-button
                                class="filter-item"
                                type="primary"
                                icon="el-icon-search"
                                @click="getSearchQualityList()"
                              >搜索</el-button>
                                <el-button
                                class="filter-item"
                                type="primary"
                                icon="el-icon-plus"
                                @click="dialogAddLightspot=true"
                                v-hasPermi="['system:dept:add']"
                                >新增项目亮点</el-button>
                            </el-form-item>
                        </el-form>
                    </div>

                    <el-table
                    :data="brightspotTableData"
                        stripe
                        style="width: 100%"
                    >
                        <el-table-column
                            prop="projectName"
                            label="项目名称"
                            width=""
                        >

                        </el-table-column>
                        <el-table-column
                            prop="brightspotPosition"
                            label="亮点部位"
                            width=""
                        >
                        </el-table-column>

                        <el-table-column
                            prop="brightspotDescribe"
                            label="亮点描述"
                            width=""
                        >
                        </el-table-column>
                        <el-table-column
                            prop="uploadPersonName"
                            label="上传人"
                            width=""
                        >
                        </el-table-column>
                        <el-table-column
                            prop="uploadDate"
                            label="上传日期"
                            width=""
                        >
                        </el-table-column>
                        <el-table-column
                            label="操作"
                            >
                            <template slot-scope="scope">
                                <el-button @click="LightspotDetail(scope.row)" plain size="middle" style="color:#058061;">查看</el-button>
                            </template>
                        </el-table-column>
                    </el-table>

                    <!-- --------------新增项目部亮点 -->
                    <el-dialog
                        title="项目部亮点"
                        :visible.sync="dialogAddLightspot"
                        width="70%;"
                        append-to-body
                        >
                        <div>
                            <div class="row-style mb20">
                                <p class="com-left">
                                    亮点部位:
                                    <span style="color: red;">*</span>
                                </p>
                                <div class="com-right">
                                    <label for="">
                                        <input type="text" v-model="brightspotManageListQuery.brightspotPosition" placeholder="" class="input-l270">
                                    </label>
                                </div>
                            </div>

                            <div class="row-style mb20">
                                <p class="com-left">
                                    亮点描述:
                                    <span style="color: red;">*</span>
                                </p>
                                <div class="com-right">
                                    <label for="">
                                        <input type="text" v-model="brightspotManageListQuery.brightspotDescribe" placeholder="" class="input-l270">
                                    </label>
                                </div>
                            </div>

                            <!-- 上传图片 -->
                            <div class="row-style mb20">
                                <p class="com-left">
                                    上传图片:
                                    <span style="color: red;">*</span>
                                </p>
                                <div class="com-right">
                                    <el-upload
                                    action="https://jsonplaceholder.typicode.com/posts/"
                                    list-type="picture-card"
                                    :on-preview="handlePictureCardPreview"
                                    :on-remove="handleRemove"
                                    :on-success="uploadSuccess"
                                    :limit="limit">
                                    <i class="el-icon-plus"></i>
                                    </el-upload>
                                    <el-dialog :visible.sync="dialogVisibleimg">
                                        <img width="100%" :src="dialogImageUrl" alt="">
                                    </el-dialog>
                                </div>
                                
                            </div>
                        </div>
                        <div style="text-align:right;width:100%;">
                            <el-button style="background:#09a17b;margin-right:35px;" type="primary" @click="addDrightspotManage">保存</el-button>
                        </div>
                    </el-dialog>


                    <!-- ----------------//查看亮点详情弹框 -->
                    <el-dialog
                        title="项目部亮点详情"
                        :visible.sync="dialogLightspotDetail"
                        width="70%;"
                        append-to-body
                        >
                        <div>
                            <div class="row-style mb20">
                                <div style="display:flex;">
                                    <div class="com-table mb25">
                                        <p class="com-left">项目名称:</p>
                                        <span class="ax_default">{{brightspotManageData.projectName}}</span>
                                    </div>
                                </div>
                            </div>
                            <div class="row-style mb20">
                                <div style="display:flex;">
                                    <div class="com-table mb25">
                                        <p class="com-left">亮点部位:</p>
                                        <span class="ax_default">{{brightspotManageData.brightspotPosition}}</span>
                                    </div>
                                </div>
                            </div>

                            <div class="row-style mb20">
                                <div style="display:flex;">
                                    <div class="com-table mb25">
                                        <p class="com-left">亮点描述:</p>
                                        <span class="ax_default">{{brightspotManageData.brightspotDescribe}}</span>
                                    </div>
                                </div>
                            </div>

                            <div class="row-style mb20">
                                <div style="display:flex;">
                                    <div class="com-table mb25">
                                        <p class="com-left">上传人:</p>
                                        <span class="ax_default">{{brightspotManageData.uploadPersonName}}</span>
                                    </div>
                                </div>
                                <div style="display:flex;">
                                    <div class="com-table mb25">
                                        <p class="com-left">上传日期:</p>
                                        <span class="ax_default">{{brightspotManageData.uploadDate}}</span>
                                    </div>
                                </div>
                            </div>

                            <!-- 图片 -->
                            <div class="row-style mb20">
                                <div style="display:flex;">
                                    <div class="com-table mb25">
                                        <p class="com-left">亮点图片:</p>
                                        <div class="ml15" style="">

                                            <span style="margin-right:8px;">
                                                <img src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1591899810736&di=0d25557d98bd7d7970193707b290ad0a&imgtype=0&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D1531221012%2C95085541%26fm%3D214%26gp%3D0.jpg" alt="" width="90" height="90">
                                            </span>
                                            <span style="margin-right:8px;">
                                                <img src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1591899810736&di=0d25557d98bd7d7970193707b290ad0a&imgtype=0&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D1531221012%2C95085541%26fm%3D214%26gp%3D0.jpg" alt="" width="90" height="90">
                                            </span>
                                            <span style="margin-right:8px;">
                                                <img src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1591899810736&di=0d25557d98bd7d7970193707b290ad0a&imgtype=0&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D1531221012%2C95085541%26fm%3D214%26gp%3D0.jpg" alt="" width="90" height="90">
                                            </span>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div style="text-align:right;width:100%;">
                            <el-button style="background:#09a17b;margin-right:35px;" type="primary" @click="dialogLightspotDetail=false">关闭</el-button> 
                        </div>
                    </el-dialog>
              </div>
          </el-tab-pane>
      </el-tabs>
  </div>
</template>

<script>
  import {listBrightspotManager, addDrightspot,detailsBrightspotManager,listProject} from "@/api/ws/brightspotManage";
  import Team from "../brightspotManage/index";

  const defaultParamBrightspotManage = {
    id:'',
    brightspotPosition: '',
    brightspotDescribe:'',
    brightspotImageAddres:'',
    uploadPersonId:'',
    uploadDate:''
  };

  // 亮点查询参数
  const queryData = {
    pageNum: 1,
    pageSize: 10,
  };

export default {
    name:'brightspotManage',
    data(){
        return{
            brightspotManageQuery: Object.assign({}, queryData),
            brightspotManageListQuery:Object.assign({}, defaultParamBrightspotManage),
            activeIndex:'first',
            // 查询参数
            queryParams: {
              pageNum: 1,
              pageSize: 10,
              projectId: undefined,
            },
            dialogAddLightspot:false,
            projectList:[

            ],
            dialogLightspotDetail:false,
            
            // 亮点数据
          brightspotTableData:'',
          brightspotManageData:'',
            // 上传图片
            dialogImageUrl: '',
            dialogVisibleimg: false,
            limit:5,
        }
    },
    created(){
      this.getQualityList(),
      this.getProjectList(),
      this.getSearchQualityList()
    },
    methods:{
        LightspotDetail(value){
          detailsBrightspotManager(value.id).then(response =>{

            this.dialogLightspotDetail=true;
            this.brightspotManageData = response.data;
          }
          );
        },

        // 上传图片相关
        handleRemove(file, fileList) {
            console.log(file, fileList);
        },
        handlePictureCardPreview(file) {
            console.log('handlePictureCardPreview',file)
            this.dialogImageUrl = file.url;
            this.dialogVisibleimg = true;
        },
        uploadSuccess(response, file, fileList){
            console.log('response',response)
            console.log('file',file)
            console.log('fileList',fileList)
        },

        // 保存
        addDrightspotManage(){
          addDrightspot(this.brightspotManageListQuery).then(response => {
            if(response.code == 200){
              this.$message({
                message: response.msg,
                type: 'success',
                duration:1000
              });
              this.dialogAddLightspot = false,
              this.getQualityList()
            }
          });
        },

      //查询亮点列表
      getQualityList(){
        listBrightspotManager(this.brightspotManageQuery).then(response =>{
            this.brightspotTableData = response.rows;
            this.total=response.total
          }
        );
      },
      //项目信息
      getProjectList(){
        listProject().then(response => {
          this.projectList = response.rows;
        });
      },
      // 点击搜索
      getSearchQualityList(){
        listBrightspotManager(this.queryParams).then(response =>{
            this.brightspotTableData = response.rows;
            this.total=response.total
          }
        );
      },
    }
}
</script>

<style>
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


.project-milestone{
    border:1px solid #e9f2ef;
}
.border-bottom {
    border-bottom: 1px solid #e9f2ef;
}

/* 字体 */
.ax_default {
    font-family: 'Arial Normal', 'Arial';
    font-weight: 400;
    font-style: normal;
    font-size: 14px;
    color: #999999;
    /* text-align: center; */
    margin-left: 15px;
    height: 35px;
    line-height:35px;
    /* line-height: normal; */
}


.row-style{
    display:table;
    width:100%;
}
.com-left{
    display: table-cell;
    width:135px;
    text-align: right;
    height: 35px;
    line-height:35px;
}
.com-right{
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
