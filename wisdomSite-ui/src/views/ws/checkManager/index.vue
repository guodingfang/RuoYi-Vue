<template>
  <div style="margin-left:15px;">
    <el-tabs v-model="activeIndex" class="el-menu-demo">
      <el-tab-pane index="1" label="质量巡检" name="second">
        <div class style="margin:10px 10px 10px 15px;display:flex;         align-items:center;">
          <div style="margin-left:10px;">全部（{{this.total}}）</div>

          <el-form :inline="true">
            <el-form-item>
              <el-date-picker
                v-model="dateRange"
                @change="dateChange"
                value-format="yyyy-MM-dd"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
              ></el-date-picker>
            </el-form-item>
            <!-- ------------------------------ -->

            <!-- ------------------------------ -->
            <el-form-item label>
              <el-select v-model="queryParams.projectId" placeholder="项目名称" filterable clearable>
                <el-option
                  v-for="item in projectList"
                  :key="item.projectName"
                  :label="item.projectName"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label>
              <el-select v-model="queryParams.status" placeholder="整改状态" clearable>
                <el-option key="待整改" label="待整改" value="1" />
                <el-option key="待验证" label="待验证" value="2" />
                <el-option key="已完成" label="已完成" value="3" />
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
                type="warning"
                icon="el-icon-download"
                size="mini"
                @click="handleExport"
                v-hasPermi="['system:dept:export']"
              >导出</el-button>
              <el-button
                class="filter-item"
                type="primary"
                icon="el-icon-plus"
                @click="dialogAddCheckTask=true"
                v-hasPermi="['system:dept:add']"
              >新增整改任务</el-button>
            </el-form-item>
          </el-form>
        </div>
        <el-table
          :data="checkManagerList"
          stripe
          style="width: 100%"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width></el-table-column>
          <el-table-column prop="statusName" label="整改状态" width></el-table-column>
          <el-table-column prop="repairLimitDate" label="整改时限" width></el-table-column>
          <el-table-column prop="projectName" label="项目名称" width></el-table-column>
          <el-table-column prop="checkPositionName" label="检查人" width="70"></el-table-column>
          <el-table-column prop="checkDate" label="检查时间" width></el-table-column>
          <el-table-column prop="checkPosition" label="检查部位" width></el-table-column>
          <el-table-column prop="qualityProblemLevelName" label="质量问题等级" width></el-table-column>
          <el-table-column
            class-name="qualityProblemName"
            prop="qualityProblemName"
            label="问题类型"
            width="130"
          ></el-table-column>
          <el-table-column class-name="comment" prop="comment" label="问题描述" width="130"></el-table-column>
          <el-table-column prop="repairPersonName" label="整改人" width="60"></el-table-column>
          <el-table-column prop="verifyPersonName" label="验证人" width="60"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                @click="checkProblemDetail(scope.row)"
                plain
                size="middle"
                style="color:#058061;"
              >查看</el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-pagination
          v-show="total>0"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          background
          :page-sizes="[10,20,30,40,50]"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          layout="total, sizes,prev, pager, next, jumper"
          :total="total"
        />
        <!-- @pagination="getList" -->

        <!-- 新增整改任务弹框 -->
        <el-dialog
          title="新增整改任务"
          :visible.sync="dialogAddCheckTask"
          width="70%;"
          append-to-body
          custom-class="el-dialog__header"
          :model="checkManagerListQuery"
        >
          <div style>
            <!-- 检查部位 -->
            <div class="row-style mb20">
              <p class="com-left">
                检查部位:
                <span style="color: red;">*</span>
              </p>
              <div class="com-right">
                <label for>
                  <input
                    type="text"
                    v-model="checkManagerListQuery.checkPosition"
                    placeholder
                    class="input-l270"
                  />
                </label>
              </div>
            </div>

            <!-- 问题等级 -->
            <div class="row-style mb20">
              <p class="com-left">问题等级:</p>
              <div style="width:300px;">
                <el-radio-group
                  v-model="checkManagerListQuery.qualityProblemLevel"
                  class="com-right"
                >
                  <el-radio :label="1">一般</el-radio>
                  <el-radio :label="2">严重</el-radio>
                </el-radio-group>
              </div>
            </div>

            <!-- 问题分类 -->
            <div class="row-style mb20">
              <p class="com-left">
                问题分类:
                <span style="color: red;">*</span>
              </p>
              <el-select
                placeholder
                v-model="checkManagerListQuery.qualityProblemId"
                class="ml5 com-right"
              >
                <el-option
                  v-for="item in checkManagerTypeList"
                  :label="item.problemTypeName"
                  :value="item.id"
                  :key="item.problemTypeName"
                ></el-option>
              </el-select>
            </div>

            <!-- 问题描述 -->
            <div class="row-style mb20">
              <p class="com-left">
                问题描述:
                <span style="color: red;">*</span>
              </p>
              <div class="com-right">
                <el-input
                  type="textarea"
                  :rows="2"
                  v-model="checkManagerListQuery.comment"
                  placeholder="请输入"
                  class
                ></el-input>
              </div>
            </div>
            <div class="row-style mb20">
              <p class="com-left">
                整改要求:
                <span style="color: red;">*</span>
              </p>
              <div class="com-right">
                <el-input
                  type="textarea"
                  :rows="2"
                  v-model="checkManagerListQuery.rectificationRequirements"
                  placeholder="请输入"
                  class
                ></el-input>
              </div>
            </div>

            <!--整改人 -->
            <div class="row-style mb20">
              <p class="com-left">
                整改人:
                <span style="color: red;margin-left:5px;">*</span>
              </p>
              <el-select
                class="com-right"
                placeholder="请选择项目负责人"
                v-model="checkManagerListQuery.repairPersonId"
              >
                <el-option
                  v-for="item in userList"
                  :label="item.userName"
                  :value="item.userId"
                  :key="item.userName"
                ></el-option>
              </el-select>
            </div>

            <div style="display:flex;" class="row-style mb20">
              <div class style="display:flex;align-items:center;height:35px;">
                <p class="com-left" style="width:100px;">整改时限:</p>
                <div style="width:260px;">
                  <el-date-picker
                    v-model="checkManagerListQuery.repairLimitDate"
                    type="date"
                    placeholder="选择日期"
                  ></el-date-picker>
                </div>
              </div>
            </div>

            <!-- 验证人-->
            <div class="row-style mb20">
              <p class="com-left">
                验证人:
                <span style="color: red;">*</span>
              </p>
              <div style="width:300px;">
                <el-select
                  placeholder="请选择"
                  v-model="checkManagerListQuery.verifyPersonId"
                  class="com-right"
                >
                  <el-option
                    v-for="item in userList"
                    :label="item.userName"
                    :value="item.userId"
                    :key="item.userName"
                  ></el-option>
                </el-select>
              </div>
            </div>

            <!-- 上传图片 -->
            <div class="row-style mb20">
              <p class="com-left">
                上传图片:
                <span style="color: red;">*</span>
              </p>
              <vue-cropper></vue-cropper>
              <el-upload
                list-type="picture-card"
                :before-upload="beforeUpload"
                :on-preview="handlePictureCardPreview"
                :on-remove="handleRemove"
                :limit="limit"
                style="display:flex;"
              >
                <!-- <div style="display:flex;" class="" v-if="options.img.length>0" >
                        <img v-for="item in options.img" :key="item" :src="item" >
                </div>-->

                <i class="el-icon-plus"></i>
              </el-upload>
              <!-- <el-upload :before-upload="beforeUpload">
                      <img v-if="checkManagerListQuery.img" :src="checkManagerListQuery.img" class="avatar">
                      <i class="el-icon-plus"></i>
              </el-upload>-->

              <el-dialog :visible.sync="dialogVisibleimg">
                <img width="100%" v-for="item in options.img" :key="item" :src="item" alt />
              </el-dialog>
            </div>

            <div style="text-align:right;width:100%;">
              <el-button
                style="background:#09a17b;margin-right:35px;"
                type="primary"
                @click="checkManagerAdd"
              >保 存</el-button>
              <el-button
                style="background:#09a17b;margin-right:35px;"
                type="primary"
                @click="dialogAddProject=true"
              >保存并继续</el-button>
            </div>
          </div>
        </el-dialog>

        <!-- 点击查看 -->
        <el-dialog title="质量问题详情" :visible.sync="dialogCheckDetail" width="70%;" append-to-body>
          <div>
            <div>
              <p class="tableP mb20">
                <span style="color:#666;">整改任务</span>
              </p>

              <div>
                <div style="display:flex;">
                  <div class="com-table mb25">
                    <p class="com-left">项目名称:</p>
                    <span class="ax_default">{{checkManagerData.projectName}}</span>
                  </div>
                  <div class="com-table mb25">
                    <p class="com-left">项目所属单位:</p>
                    <span class="ax_default">{{checkManagerData.projectUnitName}}</span>
                  </div>
                </div>

                <div style="display:flex;">
                  <div class="com-table mb25">
                    <p class="com-left">检查人:</p>
                    <span class="ax_default">{{checkManagerData.checkPositionName}}</span>
                  </div>
                  <div class="com-table mb25">
                    <p class="com-left">检查时间:</p>
                    <span class="ax_default">{{checkManagerData.checkDate}}</span>
                  </div>
                </div>

                <div style="display:flex;">
                  <div class="com-table mb25">
                    <p class="com-left">检查部位:</p>
                    <span class="ax_default">{{checkManagerData.checkPosition}}</span>
                  </div>
                </div>

                <div style="display:flex;">
                  <div class="com-table mb25">
                    <p class="com-left">问题等级:</p>
                    <span class="ax_default">{{checkManagerData.qualityProblemLevelName}}</span>
                  </div>
                  <div class="com-table mb25">
                    <p class="com-left">问题类别:</p>
                    <span class="ax_default">{{checkManagerData.qualityProblemName}}</span>
                  </div>
                </div>

                <div style="display:flex;">
                  <div class="com-table mb25">
                    <p class="com-left">问题描述:</p>
                    <span class="ax_default">{{checkManagerData.comment}}</span>
                  </div>
                </div>

                <div style="display:flex;">
                  <div class="com-table mb25">
                    <p class="com-left">整改人:</p>
                    <span class="ax_default">{{checkManagerData.repairPersonName}}</span>
                  </div>
                  <div class="com-table mb25">
                    <p class="com-left">整改期限:</p>
                    <span class="ax_default">{{checkManagerData.repairLimitDate}}</span>
                  </div>
                </div>

                <div style="display:flex;">
                  <div class="com-table mb25">
                    <p class="com-left">验证人:</p>
                    <span class="ax_default">{{checkManagerData.verifyPersonName}}</span>
                  </div>
                </div>

                <div style="display:flex;">
                  <div class="com-table mb25">
                    <p class="com-left">整改前照片:</p>
                    <span style="margin-right:8px;">
                      <img
                        src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1591899810736&di=0d25557d98bd7d7970193707b290ad0a&imgtype=0&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D1531221012%2C95085541%26fm%3D214%26gp%3D0.jpg"
                        alt
                        width="90"
                        height="90"
                      />
                    </span>
                    <span style="margin-right:8px;">
                      <img
                        src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1591899810736&di=0d25557d98bd7d7970193707b290ad0a&imgtype=0&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D1531221012%2C95085541%26fm%3D214%26gp%3D0.jpg"
                        alt
                        width="90"
                        height="90"
                      />
                    </span>
                    <span style="margin-right:8px;">
                      <img
                        src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1591899810736&di=0d25557d98bd7d7970193707b290ad0a&imgtype=0&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D1531221012%2C95085541%26fm%3D214%26gp%3D0.jpg"
                        alt
                        width="90"
                        height="90"
                      />
                    </span>
                  </div>
                </div>
              </div>

              <!-- 等级之类的 -->
              <div></div>
            </div>

            <div>
              <div v-for="item in checkManagerDataList" :key="item.id">
                <!-- 整改回复 -->
                <div v-if="item.id != null && item.id != ''">
                  <p class="tableP mb20">
                    <span style="color:#666;">整改回复</span>
                  </p>

                  <div>
                    <div style="display:flex;">
                      <div class="com-table mb25">
                        <p class="com-left">整改人:</p>
                        <span class="ax_default">{{item.repairPersonName}}</span>
                      </div>
                      <div class="com-table mb25">
                        <p class="com-left">整改时间:</p>
                        <span class="ax_default">{{item.repairDate}}</span>
                      </div>
                    </div>

                    <div style="display:flex;">
                      <div class="com-table mb25">
                        <p class="com-left">整改描述:</p>
                        <span class="ax_default">{{item.repairResultDescription}}</span>
                      </div>
                    </div>

                    <div style="display:flex;">
                      <div class="com-table mb25">
                        <p class="com-left">整改后照片:</p>
                        <span style="margin-right:8px;">
                          <img
                            src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1591899810736&di=0d25557d98bd7d7970193707b290ad0a&imgtype=0&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D1531221012%2C95085541%26fm%3D214%26gp%3D0.jpg"
                            alt
                            width="90"
                            height="90"
                          />
                        </span>
                        <span style="margin-right:8px;">
                          <img
                            src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1591899810736&di=0d25557d98bd7d7970193707b290ad0a&imgtype=0&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D1531221012%2C95085541%26fm%3D214%26gp%3D0.jpg"
                            alt
                            width="90"
                            height="90"
                          />
                        </span>
                        <span style="margin-right:8px;">
                          <img
                            src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1591899810736&di=0d25557d98bd7d7970193707b290ad0a&imgtype=0&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D1531221012%2C95085541%26fm%3D214%26gp%3D0.jpg"
                            alt
                            width="90"
                            height="90"
                          />
                        </span>
                      </div>
                    </div>
                  </div>

                  <div v-if="item.verifyId!=null && item.verifyId != ''">
                    <p class="tableP mb20">
                      <span style="color:#666;">整改验证</span>
                    </p>

                    <div>
                      <div style="display:flex;">
                        <div class="com-table mb25">
                          <p class="com-left">验证人:</p>
                          <span class="ax_default">{{item.verifyPersonNmae}}</span>
                        </div>
                        <div class="com-table mb25">
                          <p class="com-left">验证时间:</p>
                          <span class="ax_default">{{item.verifyDate}}</span>
                        </div>
                      </div>

                      <div style="display:flex;">
                        <div class="com-table mb25">
                          <p class="com-left">结果描述:</p>
                          <span class="ax_default">{{item.verifyResultDescription}}</span>
                        </div>
                      </div>

                      <div style="display:flex;">
                        <div class="com-table mb25">
                          <p class="com-left">现场照片:</p>
                          <span style="margin-right:8px;">
                            <img
                              src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1591899810736&di=0d25557d98bd7d7970193707b290ad0a&imgtype=0&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D1531221012%2C95085541%26fm%3D214%26gp%3D0.jpg"
                              alt
                              width="90"
                              height="90"
                            />
                          </span>
                          <span style="margin-right:8px;">
                            <img
                              src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1591899810736&di=0d25557d98bd7d7970193707b290ad0a&imgtype=0&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D1531221012%2C95085541%26fm%3D214%26gp%3D0.jpg"
                              alt
                              width="90"
                              height="90"
                            />
                          </span>
                          <span style="margin-right:8px;">
                            <img
                              src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1591899810736&di=0d25557d98bd7d7970193707b290ad0a&imgtype=0&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D1531221012%2C95085541%26fm%3D214%26gp%3D0.jpg"
                              alt
                              width="90"
                              height="90"
                            />
                          </span>
                        </div>
                      </div>

                      <div style="display:flex;">
                        <div class="com-table mb25">
                          <p class="com-left">验证结果:</p>
                          <span
                            style="margin-right:8px;color:#fff;background:red;border-radius:5px;padding:5px;font-weight:400;"
                          >{{item.verifyResult}}</span>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div style="text-align:right;width:100%;">
            <el-button
              style="background:#09a17b;margin-right:35px;"
              type="primary"
              @click="dialogCheckDetail=false"
            >关闭</el-button>
          </div>
        </el-dialog>
      </el-tab-pane>

      <el-tab-pane index="2" label="质量问题设置" name="first">
        <!-- 质量问题设置 -->
        <div
          style="width:650px;height:50px;line-height:50px;display:flex;justify-content: space-between;align-items:center;"
        >
          <span style="margin-right:15px;">质量问题分类模板</span>
          <el-button
            plain
            size="middle"
            style="margin-right:15px;color:#058061;font-weight:400;height:35px;"
            @click="openAddQualityProblem=true"
          >新增</el-button>
        </div>

        <el-table :data="checkManagerTypeLists" border style="width: 652px;">
          <el-table-column prop="problemTypeName" label="分类名称" min-width="20%" width="300"></el-table-column>

          <el-table-column align="center" label="操作" min-width="30%" width="350">
            <template slot-scope="scope">
              <el-button
                @click="handleClickToUpdate(scope.$index,scope.row)"
                plain
                size="middle"
                style="color:#058061;"
              >修改</el-button>

              <el-button
                v-if="scope.row.isDelete=='N'"
                @click="handleClickToForbidden (scope.$index,scope.row)"
                plain
                size="middle"
                style="color:#058061;"
              >禁用</el-button>

              <el-button
                v-else
                @click="handleClickToForbidden(scope.$index,scope.row)"
                plain
                size="middle"
                style="color:#058061;"
              >启用</el-button>

              <el-button
                @click="handleClickToDelete(scope.$index,scope.row)"
                plain
                size="middle"
                style="color:#058061;"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <!-- 新增质量问题模板的弹框 -->
        <el-dialog title="分类名称" :visible.sync="openAddQualityProblem" width="600px" center>
          <el-form :model="formQualityType">
            <!-- <el-col :span="12"> -->
            <el-form-item label="质量问题分类名称" prop="leader">
              <el-input
                v-model="ProblemTypeListQuery.problemTypeName"
                placeholder="请输入里问题分类名称"
                maxlength="20"
              />
            </el-form-item>
            <!-- </el-col> -->
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="openAddQualityProblem = false">取 消</el-button>
            <el-button type="primary" @click="submitUpdate">确 定</el-button>
          </span>
        </el-dialog>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import {
  listCheckManager,
  getCheckManagerById,
  addCheckManager,
  updateCheckManager,
  problemTypeList,
  exportCheckManager,
  getCheckType,
  getCheckUser,
  addProblemType,
  updateProblemType,
  handleClickToForbiddenProblemType,
  deleteProblemType,
  checkManagerDetail,
  checkManagerDetailList,
  listProject,
  uploadAvatar
} from "@/api/ws/checkManager";
import store from "@/store";
import { VueCropper } from "vue-cropper";
// import multiUpload from "@/api/components/Upload"

// 质量检查查询参数
const queryData = {
  pageNum: 1,
  pageSize: 10
};

//新增质量检查字段参数定义
const defaultParamCheckManager = {
  projectId: "",
  checkPosition: "",
  qualityProblemLevel: "",
  qualityProblemId: "",
  comment: "",
  rectificationRequirements: "",
  repairPersonId: "",
  repairLimitDate: "",
  verifyPersonId: "",
  checkPositionAddres: ""
};

//新增质量检查字段参数定义
const defaultParamProblemType = {
  id: "",
  problemTypeName: "",
  isDelete: ""
};

export default {
  name: "CheckManager",
  components: { VueCropper },
  props: {
    user: {
      type: Object
    }
  },
  data() {
    const generateData = _ => {
      const data = [];
      const cities = ["上海", "北京", "广州", "深圳", "南京", "西安", "成都"];
      const pinyin = [
        "shanghai",
        "beijing",
        "guangzhou",
        "shenzhen",
        "nanjing",
        "xian",
        "chengdu"
      ];
      cities.forEach((city, index) => {
        data.push({
          label: city,
          key: index,
          pinyin: pinyin[index]
        });
      });
      return data;
    };
    return {
      data: generateData(),
      value: [],
      filterMethod(query, item) {
        return item.pinyin.indexOf(query) > -1;
      },
      multipleSelection: [], //表格多选
      options: {
        img: []
      },
      // 下拉框中的项目列表
      selectProjectList: [],
      // 下拉框中的整改状态列表
      SelectStatusOptions: [],

      checkManagerQuery: Object.assign({}, queryData),
      checkManagerListQuery: Object.assign({}, defaultParamCheckManager),
      ProblemTypeListQuery: Object.assign({}, defaultParamProblemType),

      userList: "",
      checkManagerTypeList: "",
      checkManagerTypeLists: "",
      // 上传图片
      dialogImageUrl: "",
      dialogVisibleimg: false,
      limit: 3,

      activeIndex: "second",
      dateRange: "", //日期范围
      projectName: "",
      projectList: "",
      statusValue: "",
      statusList: "",

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        kssj: "",
        jssj: "",
        projectId: undefined,
        status: undefined
      },
      // 分页参数
      queryParams1: {
        pageNum: 1,
        pageSize: 10,
        roleName: undefined,
        roleKey: undefined,
        status: undefined
      },
      total: 0, //分页相关

      //显示质量详情弹框

      dialogCheckDetail: false,
      dialogAddCheckTask: false, //显示新增整改任务弹框
      dialogCheckDetail: false,
      level: "3", // 问题等级
      // 状态数据字典
      statusOptions: [],
      checkManagerList: null,
      // 质量问题分类模板相关
      form: {},

      openAddQualityProblem: false, //增加问题分类模板弹框
      qualityProblemList: "",
      formQualityType: "",
      checkManagerData: "",
      checkManagerDataList: ""
    };
  },
  created() {
    this.getQualityList(), //质量问题列表
      this.getUser(),
      this.getCheckManagerType(),
      this.getQualityProblemType(),
      this.getSelectProjectList(),
      this.getProjectList();
  },
  methods: {
    //----------------------------------------------------------------
    // 上传图片
    uploadImg() {
      // console.log('refssssssssssss',this.$refs.cropper)
      // this.$refs.cropper.getCropBlob(data => {
      let formData = new FormData();
      formData.append("avatarfile", data);
      console.log("formdata", formData);
      console.log("data", data);
      uploadAvatar(formData).then(response => {
        if (response.code === 200) {
          this.open = false;

          this.options.img = process.env.VUE_APP_BASE_API + response.imgUrl;
          this.msgSuccess("修改成功");
        }
        // this.$refs.cropper.clearCrop();
      });
      // });
    },
    //------------------------------------------------------

    //---------------分页--------------------------------------------------
    handleSizeChange(val) {
      this.queryParams.pageSize = val;
      console.log("SizeChange", val);
      this.getQualityList();
    },
    handleCurrentChange(val) {
      this.queryParams.pageNum = val;
      console.log("CurrentChange", val);
      this.getQualityList();
    },
    //-----------------------------------------------------------------
    //多选
    handleSelectionChange(val) {
      console.log("mulSelec", val);
      this.multipleSelection = val;
    },
    /** 单元格字体格式*/
    cellStyle: function(row, column, rowIndex, columnIndex) {
      console.log("row", row);
      if (row.row.active == false) {
        return "color:#999999;";
      } else {
        return "";
      }
    },
    //ch查询质量检查 巡检问题列表
    getQualityList() {
      listCheckManager(this.queryParams).then(response => {
        this.checkManagerList = response.rows;
        this.total = response.total;
      });
    },
    //日期选择时触发
    dateChange() {
      (this.queryParams.kssj = this.dateRange[0]),
        (this.queryParams.jssj = this.dateRange[1]);
    },
    // 点击搜索
    getSearchQualityList() {
      console.log("queryParams", this.queryParams);
      listCheckManager(this.queryParams).then(response => {
        this.checkManagerList = response.rows;
        this.total = response.total;
        this.queryParams.status = "";
        this.dateRange = [];
        this.queryParams.kssj = "";
        this.queryParams.jssj = "";
        console.log("queryParams333", this.queryParams, this.dateRange);
      });
    },
    //查询人员
    getUser() {
      getCheckUser().then(response => {
        this.userList = response.rows;
      });
    },
    //项目信息
    getProjectList() {
      listProject().then(response => {
        this.projectList = response.rows;
      });
    },
    //查询质量问题
    getCheckManagerType() {
      this.ProblemTypeListQuery.isDelete = "N";
      getCheckType(this.ProblemTypeListQuery).then(response => {
        this.checkManagerTypeList = response.rows;
      });
      getCheckType().then(response => {
        this.checkManagerTypeLists = response.rows;
      });
    },

    /** 添加按钮操作 */
    checkManagerAdd: function() {
      addCheckManager(this.checkManagerListQuery)
        .then(response => {
          if (response.code == 200) {
            this.$message({
              message: response.msg,
              type: "success",
              duration: 1000
            });
            (this.dialogAddCheckTask = false), this.getQualityList();
          } else {
            this.$message({
              message: response.msg,
              type: "success",
              duration: 1000
            });
          }
        })
        .catch(response => {
          console.log("错误信息", response);
        });
    },

    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出质量检查数据！", "导出", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return exportCheckManager(queryParams);
        })
        .then(response => {
          this.download(response.msg);
        })
        .catch(function() {});
    },

    // 查看详情
    checkProblemDetail(value) {
      this.dialogCheckDetail = true;
      checkManagerDetail(value.id).then(response => {
        this.checkManagerData = response.data;
      });
      checkManagerDetailList(value.id).then(response => {
        this.checkManagerDataList = response.data;
      });
    },
    // 禁用-启用 质量问题
    handleClickToForbidden(index, value) {
      this.ProblemTypeListQuery.id = this.checkManagerTypeLists[index11].id;
      if (value.isDelete == "N") {
        this.ProblemTypeListQuery.isDelete = "Y";
      } else {
        this.ProblemTypeListQuery.isDelete = "N";
      }
      handleClickToForbiddenProblemType(this.ProblemTypeListQuery).then(
        response => {
          if (response.code == 200) {
            this.$message({
              message: response.msg,
              type: "success",
              duration: 1000
            });
            this.getQualityProblemType();
          }
        }
      );
    },

    // 质量问题删除
    handleClickToDelete(index, value) {
      this.ProblemTypeListQuery.id = this.checkManagerTypeLists[index11].id;
      deleteProblemType(this.ProblemTypeListQuery.id).then(response => {
        if (response.code == 200) {
          this.$message({
            message: response.msg,
            type: "success",
            duration: 1000
          });
          this.getQualityProblemType();
        }
      });
    },
    // 模板分类修改
    handleClickToUpdate(index, value) {
      this.ProblemTypeListQuery.id = this.checkManagerTypeLists[index11].id;
      this.ProblemTypeListQuery.problemTypeName = this.checkManagerTypeLists[
        index11
      ].problemTypeName;
      this.openAddQualityProblem = true;
    },

    getQualityProblemType() {
      problemTypeList().then(response => {
        this.qualityProblemList = response.rows;
      });
    },
    //修改 确定按钮
    submitUpdate() {
      if (
        this.ProblemTypeListQuery.id != null &&
        this.ProblemTypeListQuery.id != "" &&
        this.ProblemTypeListQuery.id != undefined
      ) {
        updateProblemType(this.ProblemTypeListQuery).then(response => {
          if (response.code == 200) {
            this.$message({
              message: response.msg,
              type: "success",
              duration: 1000
            });
            (this.openAddQualityProblem = false), this.getQualityProblemType();
          }
        });
      } else {
        addProblemType(this.ProblemTypeListQuery).then(response => {
          if (response.code == 200) {
            this.$message({
              message: response.msg,
              type: "success",
              duration: 1000
            });
            (this.openAddQualityProblem = false), this.getQualityProblemType();
          }
        });
      }
    },

    handleQuery() {
      console.log("ggg", this.dateRange);
    },
    // 上传图片相关
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePictureCardPreview(file) {
      console.log("handlePictureCardPreview", file);
      this.dialogImageUrl = file.url;
      this.dialogVisibleimg = true;
    },
    uploadSuccess(response, file, fileList) {
      this.uploadFile();
      console.log("response", response);
      console.log("file", file);
      console.log("fileList", fileList);
    },

    /** 查询角色列表 */
    getList() {
      this.loading = true;
      // listRole(this.addDateRange(this.queryParams, this.dateRange)).then(
      //     response => {
      //     this.roleList = response.rows;
      //     this.total = response.total;
      //     this.loading = false;
      //     }
      // );
    },

    // 获取项目选择下拉框里的数据
    getSelectProjectList() {},
    // 获取状态选择下拉框里的数据
    getSelectStatusOptions() {},

    selectTrigger() {
      console.log("--------------");
    },

    // ----------------------------------------a hai-------------------------------
    // 上传预处理
    beforeUpload(file) {
      this.dialogVisibleimg = true;
      if (file.type.indexOf("image/") == -1) {
        this.msgError("文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。");
      } else {
        const timeStamp = new Date() - 0;
        const copyFile = new File([file], `${timeStamp}_${file.name}`);
        this.uploadFile(copyFile);
        return false;
      }
    },

    // 上传图片
    uploadFile(file) {
      let formData = new FormData();
      formData.append("avatarfile", file);
      uploadAvatar(formData).then(response => {
        if (response.code === 200) {
          let imgSrc = process.env.VUE_APP_BASE_API + response.imgUrl;
          this.options.img.push(imgSrc);
          alert(this.options.img);
          this.msgSuccess("修改成功");
        }
      });
    }
    //---------------------------------------------------------------------------------
  }
};
</script>

<style less="scss" scoped>
.qualityLevel {
  padding-left: 0 !important;
  padding-right: 0 !important;
}
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

.project-milestone {
  border: 1px solid #e9f2ef;
}
.border-bottom {
  border-bottom: 1px solid #e9f2ef;
}

/* 字体 */
.ax_default {
  font-family: "Arial Normal", "Arial";
  font-weight: 400;
  font-style: normal;
  font-size: 14px;
  color: #999999;
  /* text-align: center; */
  line-height: normal;
}

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
  font-size: 0.85rem;
  padding: 7px 10px 6px;
  color: #939997;
  border-radius: 0;
  box-sizing: border-box;
  overflow: hidden;
}
.el-button--mini {
  padding: 10px 15px;
  font-size: 14px;
  border-radius: 3px;
}
</style>
