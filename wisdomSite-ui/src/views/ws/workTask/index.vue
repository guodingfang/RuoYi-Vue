<template>
  <div style="margin-left:15px;">
    <el-tabs v-model="activeName" class="el-menu-demo" @tab-click="handleClickChange">
      <el-tab-pane index="1" label="待整改" name="first">
        <!--<div class="" style="margin:10px 10px 10px 15px;display:flex;align-items:center;">
                  <div style="margin-left:10px;">全部（{{this.total}}）</div>

                   <el-form :inline="true">
                      <el-form-item>
                            <el-date-picker
                            v-model="dateRange"
                            type="daterange"
                            range-separator="至"
                            start-placeholder="开始日期"
                            end-placeholder="结束日期">
                            </el-date-picker>
                      </el-form-item>
                        <el-form-item label="">
                            <el-select
                            v-model="queryParams.deptName"
                            placeholder="项目名称"
                            clearable
                            @keyup.enter.native="handleQuery"
                            >
                                <el-option
                                v-for="dict in statusOptions"
                                :key="dict.dictValue"
                                :label="dict.dictLabel"
                                :value="dict.dictValue"
                                />
                            </el-select>
                        </el-form-item>
                        <el-form-item label="">
                            <el-select v-model="queryParams.status" placeholder="整改状态" clearable>
                            <el-option
                                v-for="dict in statusOptions"
                                :key="dict.dictValue"
                                :label="dict.dictLabel"
                                :value="dict.dictValue"
                            />
                            </el-select>
                        </el-form-item>

                        <el-form-item>
                            <el-button
                            class="filter-item"
                            type="primary"
                            icon="el-icon-search"
                            @click="handleQuery"
                            >搜索</el-button>
                            <el-button type="primary">
                                批量导出
                            </el-button>
                            <el-button
                            class="filter-item"
                            type="primary"
                            icon="el-icon-plus"
                            @click="dialogAddCheckTask=true"
                            v-hasPermi="['system:dept:add']"
                            >新增整改任务</el-button>
                        </el-form-item>
                    </el-form> 
        </div>-->
        <el-table :data="repairCheckManagerList" stripe style="width: 100%">
          <el-table-column prop="repairLimitDate" label="整改时限" width></el-table-column>
          <el-table-column prop="statusName" label="整改状态" width></el-table-column>
          <!--<el-table-column label="整改状态" width>
            <template slot-scope="scope">
              <span
                v-if="scope.row.status=='合格'"
                style="background:green;padding:8px;color:#fff;"
              >{{scope.row.status}}</span>
              <span
                v-else-if="scope.row.status=='待整改'||scope.row.status=='待验证'"
                style="background:#ff6600;padding:8px;color:#fff;"
              >{{scope.row.status}}</span>
              <span
                v-else-if="scope.row.status=='不合格'"
                style="background:red;padding:8px;color:#fff;"
              >{{scope.row.status}}</span>
            </template>
          </el-table-column>-->

          <el-table-column prop="projectName" label="项目名称" width></el-table-column>
          <el-table-column prop="checkPositionName" label="检查人" width="70"></el-table-column>
          <el-table-column prop="checkDate" label="检查时间" width></el-table-column>
          <el-table-column prop="checkPosition" label="检查部位" width></el-table-column>
          <el-table-column prop="qualityProblemLevelName" label="质量问题等级" width></el-table-column>
          <!--<el-table-column class-name="qualityProblemLevelName" label="质量问题等级" width="105" center>
            <template slot-scope="scope">
              <span
                v-if="scope.row.qualityLevel=='一般'"
                style="background:#ff6600;padding:8px;border-radius:5px;color:#fff;"
              >{{scope.row.qualityLevel}}</span>
              <span
                v-else-if="scope.row.qualityLevel=='严重'"
                style="background:red;padding:8px;border-radius:5px;color:#fff;"
              >{{scope.row.qualityLevel}}</span>
            </template>
          </el-table-column>-->
          <el-table-column prop="qualityProblemName" label="问题类型" width="130"></el-table-column>
          <el-table-column prop="comment" label="问题描述" width="130"></el-table-column>
          <el-table-column prop="repairPersonName" label="整改人" width="60"></el-table-column>
          <el-table-column prop="verifyPersonName" label="验证人" width="60"></el-table-column>
          <el-table-column label="操作" width>
            <template slot-scope="scope">
              <el-button
                @click="checkRectificationDetail(scope.row)"
                type="text"
                size="small"
                style="color:#058061;"
              >查看</el-button>
              <el-button
                @click="handleToRectification(scope.row)"
                type="text"
                size="small"
                style="color:#058061;"
              >进行整改</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams1.pageNum"
          :limit.sync="queryParams1.pageSize"
          @pagination="getList"
        />

        <!-- 进行整改的弹框 -->
        <el-dialog
          title="整改"
          :visible.sync="dialogToRectification"
          width="70%;"
          append-to-body
          :model="rectificationData"
            >
          <div>
            <div class="row-style mb20">
              <p class="com-left">
                整改描述:
                <span style="color: red;">*</span>
              </p>
              <div class="com-right">
                <label for>
                  <el-input
                    v-model="rectificationData.repairResultDescription"
                    type="textarea"
                    placeholder
                    class
                  ></el-input>
                </label>
              </div>
            </div>

            <!-- 上传图片 -->
            <div class="row-style mb20">
              <p class="com-left">
                上传图片:
                <span style="color: red;">*</span>
              </p>
              <el-upload
                :action="uploadUrl"
                auto-upload="false"
                :file-list="imgUrlList"
                list-type="picture-card"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :on-success="uploadSuccess1"
                :limit="limit"
              >
                <i class="el-icon-plus"></i>
              </el-upload>
              <el-dialog :visible.sync="dialogVisibleimg">
                <img width="100%" :src="dialogImageUrl" alt />
              </el-dialog>
            </div>

            <div style="text-align:left;width:100%;">
              <el-button
                style="background:#09a17b;margin-left:100px;"
                type="primary"
                @click="addRectification"
              >保 存</el-button>
            </div>
          </div>
        </el-dialog>

        <!-- 点击查看 -->
        <el-dialog
          title="质量问题详情"
          :visible.sync="dialogRectificationCheckDetail"
          width="70%;"
          append-to-body
            >
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
                    <span style="margin-right:8px;" v-for="item in beforRectificationImg" :key="item">
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

                  <!-- 整改验证 -->
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
                          > {{item.verifyResult}}</span>
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
              @click="dialogRectificationCheckDetail=false"
            >关闭</el-button>
          </div>
        </el-dialog>
      </el-tab-pane>


        <!-- 待验证 -->
      <el-tab-pane index="2" label="待验证" name="second">
        <el-table :data="verifyCheckManagerList" stripe style="width: 100%">
          <el-table-column prop="repairLimitDate" label="整改时限" width></el-table-column>
          <el-table-column prop="statusName" label="整改状态" width></el-table-column>
          <el-table-column prop="projectName" label="项目名称" width></el-table-column>
          <el-table-column prop="checkPositionName" label="检查人" width="70"></el-table-column>
          <el-table-column prop="checkDate" label="检查时间" width></el-table-column>
          <el-table-column prop="checkPosition" label="检查部位" width></el-table-column>
          <el-table-column prop="qualityProblemLevelName" label="质量问题等级" width></el-table-column>
          <el-table-column prop="qualityProblemName" label="问题类型" width="130"></el-table-column>
          <el-table-column prop="comment" label="问题描述" width="130"></el-table-column>
          <el-table-column prop="repairPersonName" label="整改人" width="60"></el-table-column>
          <el-table-column prop="verifyPersonName" label="验证人" width="60"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                @click="checkProblemDetail(scope.row)"
                plain
                type="text"
                size="small"
                style="color:#058061;"
              >查看</el-button>
              <el-button
                @click="handleToVerify(scope.row)"
                plain
                type="text"
                size="small"
                style="color:#058061;"
              >进行验证</el-button>
            </template>
          </el-table-column>
        </el-table>

        <!-- 进行验证的弹框  -->
        <el-dialog title="验证" :visible.sync="dialogToVerify" width="70%;" append-to-body
                   :model="resultVerify">
          <div>
            <div class="row-style mb20" style="display:flex;align-items:center;height:35px;">
              <p class="com-left" style>
                验证结果:
                <span style="color: red;">*</span>
              </p>
              <div style="width:300px;">
                <el-radio-group v-model="resultVerify.verifyResult" class="com-right">
                  <el-radio :label="1">合格</el-radio>
                  <el-radio :label="2">不合格</el-radio>
                </el-radio-group>
              </div>
            </div>

            <div class="row-style mb20">
              <p class="com-left">
                整改结果描述:
                <span style="color: red;">*</span>
              </p>
              <div class="com-right">
                <label for>
                  <el-input type="textarea" placeholder class v-model="resultVerify.verifyResultDescription"></el-input>
                </label>
              </div>
            </div>

            <!-- 上传图片 -->
            <div class="row-style mb20">
              <p class="com-left">
                上传图片:
                <span style="color: red;">*</span>
              </p>
              <el-upload
                action="https://jsonplaceholder.typicode.com/posts/"
                list-type="picture-card"
                :on-preview="handlePictureCardPreview"
                :on-remove="handleRemove"
                :on-success="uploadSuccess"
                :limit="limit"
              >
                <i class="el-icon-plus"></i>
              </el-upload>
              <el-dialog :visible.sync="dialogVisibleimg">
                <img width="100%" :src="dialogImageUrl" alt />
              </el-dialog>
            </div>

            <div style="text-align:left;width:100%;">
              <el-button
                style="background:#09a17b;margin-left:100px;"
                type="primary"
                @click="addVerifyfication()"
              >保 存</el-button>
            </div>
          </div>
        </el-dialog>

        <!-- 查看弹框 -->
        <!-- 点击查看 -->
        <el-dialog
          title="质量问题详情"
          :visible.sync="dialogVerifyCheckDetail"
          width="70%;"
          append-to-body
            >
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


                  <!-- 整改验证 -->
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
              @click="dialogVerifyCheckDetail=false"
            >关闭</el-button>
          </div>
        </el-dialog>
      </el-tab-pane>

      <el-tab-pane index="3" label="待验收" name="three">
        <el-table :data="checkTableData" stripe style="width: 100%">
          <el-table-column prop="planverificationDate" label="验收时限" width></el-table-column>
          <el-table-column prop="acceptanceStateName" label="验收状态" width></el-table-column>
          <el-table-column prop="projectName" label="项目名称" width></el-table-column>
          <el-table-column prop="applyPersonName" label="申请人" width="70"></el-table-column>
          <el-table-column prop="applyDate" label="申请时间" width></el-table-column>
          <el-table-column prop="acceptancePosition" label="验收部位" width></el-table-column>
          <el-table-column prop="acceptanceContent" label="验收内容" width="130"></el-table-column>
          <el-table-column prop="acceptanceNum" label="验收次数" width="130"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                @click="checkAcceptanceDetail(scope.row)"
                plain
                size="small"
                type="text"
                style="color:#058061;"
              >查看</el-button>
              <el-button
                @click="handleToAcceptance(scope.row)"
                plain
                size="small"
                type="text"
                style="color:#058061;"
              >进行验收</el-button>
            </template>
          </el-table-column>
        </el-table>

        <!-- 进行验收的弹框  -->
        <el-dialog title="验收" :visible.sync="dialogToAcceptance" width="70%;" append-to-body>
          <div>
            <div class="row-style mb20" style="display:flex;align-items:center;height:35px;">
              <p class="com-left" style>
                验证结果:
                <span style="color: red;">*</span>
              </p>
              <div style="width:300px;">
                <el-radio-group v-model="resultRecord.acceptanceState" class="com-right">
                  <el-radio :label="1">合格</el-radio>
                  <el-radio :label="2">不合格</el-radio>
                </el-radio-group>
              </div>
            </div>

            <div class="row-style mb20">
              <p class="com-left">
                验收结果描述:
                <span style="color: red;">*</span>
              </p>
              <div class="com-right">
                <label for>
                  <el-input type="textarea" placeholder class v-model="resultRecord.acceptanceDescribe"></el-input>
                </label>
              </div>
            </div>

            <!-- 上传图片 -->
            <div class="row-style mb20">
              <p class="com-left">
                上传图片:
                <span style="color: red;">*</span>
              </p>
              <el-upload
                action="https://jsonplaceholder.typicode.com/posts/"
                list-type="picture-card"
                :on-preview="handlePictureCardPreview"
                :on-remove="handleRemove"
                :on-success="uploadSuccess"
                :limit="limit"
              >
                <i class="el-icon-plus"></i>
              </el-upload>
              <el-dialog :visible.sync="dialogVisibleimg">
                <img width="100%" :src="dialogImageUrl" alt />
              </el-dialog>
            </div>

            <div style="text-align:left;width:100%;">
              <el-button
                style="background:#09a17b;margin-left:100px;"
                type="primary"
                @click="addAcceptance()"
              >保 存</el-button>
            </div>
          </div>
        </el-dialog>

        <!-- 查看弹框 -->
        <!-- 点击查看 -->
        <el-dialog title="质量验收详情" :visible.sync="dialogAcceptanceCheckDetail" width="70%;" append-to-body>
          <div>
            <!-- 验收申请 -->
            <div>
              <p class="tableP mb20">
                <span style="color:#666;">验收申请</span>
              </p>
              <div>
                <div style="display:flex;">
                  <div class="com-table mb25">
                    <p class="com-left">项目名称:</p>
                    <span class="ax_default">{{acceptanceApplyData.projectName}}</span>
                  </div>
                  <div class="com-table mb25">
                    <p class="com-left">验收部位:</p>
                    <span class="ax_default">{{acceptanceApplyData.acceptancePosition}}</span>
                  </div>
                </div>

                <div style="display:flex;">
                  <div class="com-table mb25">
                    <p class="com-left">验收内容:</p>
                    <span class="ax_default">{{acceptanceApplyData.acceptanceContent}}</span>
                  </div>
                  <div class="com-table mb25">
                    <p class="com-left">申请人:</p>
                    <span class="ax_default">{{acceptanceApplyData.applyPersonName}}</span>
                  </div>
                </div>

                <div style="display:flex;">
                  <div class="com-table mb25">
                    <p class="com-left">申请时间:</p>
                    <span class="ax_default">{{acceptanceApplyData.applyDate}}</span>
                  </div>
                </div>

                <div v-for="item in acceptanceRecordData" :key="item.id">
                  <div v-if="item.id!=null && item.id!=''">
                    <div v-if="item.acceptanceState == '2'">
                      <div v-if="acceptanceApplyData.acceptanceState == '3'">
                        <div style="display:flex;">
                          <div class="com-table mb25">
                            <p class="com-left">计划验收时间:</p>
                            <span class="ax_default">{{item.planverificationDate}}</span>
                          </div>
                          <div class="com-table mb25">
                            <p class="com-left">验收人:</p>
                            <span class="ax_default">{{item.acceptancePersonName}}</span>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

              </div>
            </div>

            <!-- ----------------------------------  -->
            <div>
              <div v-for="item in acceptanceRecordData" :key="item.id">
                <!-- 验收结果 -->
                <div v-if="item.id!=null && item.id!=''">
                  <p class="tableP mb20">
                    <span style="color:#666;">验收结果</span>
                  </p>

                  <div>
                    <div style="display:flex;">
                      <div class="com-table mb25">
                        <p class="com-left">验收人:</p>
                        <span class="ax_default">{{item.verifyPersonName}}</span>
                      </div>
                      <div class="com-table mb25">
                        <p class="com-left">验收时间:</p>
                        <span class="ax_default">{{item.verificationDate}}</span>
                      </div>
                    </div>

                    <div style="display:flex;">
                      <div class="com-table mb25">
                        <p class="com-left">结果描述:</p>
                        <span class="ax_default">{{item.acceptanceDescribe}}</span>
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
                        <span style="margin-right:8px;color:#fff;background:red;border-radius:5px;padding:5px;font-weight:400;">
                            {{item.acceptanceStateName}}
                        </span>
                      </div>
                    </div>
                  </div>
                </div>

                <!-- 再次申请 -->
                <div v-if="item.acceptanceState == '2'">
                  <div v-if="acceptanceApplyData.acceptanceState == '3'">
                    <p class="tableP mb20">
                      <span style="color:#666;">再次申请</span>
                    </p>
                    <div>
                      <div style="display:flex;">
                        <div class="com-table mb25">
                          <p class="com-left">计划验收时间:</p>
                          <span class="ax_default">{{acceptanceApplyData.planverificationDate}}</span>
                        </div>
                        <div class="com-table mb25">
                          <p class="com-left">验收人:</p>
                          <span class="ax_default">{{acceptanceApplyData.verifyPersonName}}</span>
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
              @click="dialogAcceptanceCheckDetail=false"
            >关闭</el-button>
          </div>
        </el-dialog>
      </el-tab-pane>


      <el-tab-pane index="4" label="不合格" name="four">
        <el-table :data="checkTableDataNo" stripe style="width: 100%">
          <el-table-column prop="planverificationDate" label="验收时限" width></el-table-column>
          <el-table-column prop="acceptanceStateName" label="验收状态" width></el-table-column>
          <el-table-column prop="projectName" label="项目名称" width></el-table-column>
          <el-table-column prop="applyPersonName" label="申请人" width="70"></el-table-column>
          <el-table-column prop="applyDate" label="申请时间" width></el-table-column>
          <el-table-column prop="acceptancePosition" label="验收部位" width></el-table-column>
          <el-table-column prop="acceptanceContent" label="验收内容" width="130"></el-table-column>
          <el-table-column prop="acceptanceNum" label="验收次数" width="130"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                @click="checkDisqualificationDetail(scope.row)"
                plain
                size="small"
                type="text"
                style="color:#058061;"
              >查看</el-button>
              <el-button
                @click="handleToReapply(scope.row)"
                plain
                size="small"
                type="text"
                style="color:#058061;"
              >再次申请</el-button>
            </template>
          </el-table-column>
        </el-table>

            <!-- 再次申请的弹框  -->
        <el-dialog title="再次申请" :visible.sync="dialogToReapply" width="70%;" append-to-body>
          <div>
            <div class="row-style mb20" style="display:flex;align-items:center;height:35px;">
              <p class="com-left" style>
                验收部位:
                <!-- <span style="color: red;">*</span> -->
              </p>
              <div class="com-right">
                <label for>
                  <el-input type="text" placeholder v-model="reapplyData.acceptancePosition" :disabled="true"></el-input>
                </label>
              </div>
            </div>

            <div class="row-style mb20">
              <p class="com-left">
                验收内容:
                <!-- <span style="color: red;">*</span> -->
              </p>
              <div class="com-right">
                <label for>
                  <el-input type="textarea" placeholder v-model="reapplyData.acceptanceContent" :disabled="true"></el-input>
                </label>
              </div>
            </div>
            <div class="row-style mb20">
              <p class="com-left">

                验收人:
                <span style="color: red;">*</span>
              </p>
              <!-- <div> -->
                <!-- reapplyData.acceptancePersonId -->
              <el-select placeholder="" v-model="reapplyData.acceptancePersonId" class="ml5 com-right">
                <el-option v-for="item in userList" :label="item.userName" :value="item.userId" :key="item.userName"></el-option>
              </el-select>
              <!-- </div> -->

              <p class="com-left">

                验收时间:
                <span style="color: red;">*</span>
              </p>
              <div>
                <el-date-picker
                  value-format="yyyy-MM-dd"
                  v-model="reapplyData.planverificationDate"
                  type="date"
                  :picker-options="pickerOptions"
                  placeholder="选择日期">
                </el-date-picker>
              </div>

            </div>

            <div style="text-align:left;width:100%;">
              <el-button
                style="background:#09a17b;margin-left:100px;"
                type="primary"
                @click="addApplication()"
              >保 存</el-button>
            </div>
          </div>
        </el-dialog>

            <!-- 查看弹框 -->
            <!-- 点击查看 -->
        <el-dialog title="质量验收详情" :visible.sync="dialogReapplyCheckDetail" width="70%;" append-to-body>
          <div>
            <!-- 验收申请 -->
            <div>
              <p class="tableP mb20">
                <span style="color:#666;">验收申请</span>
              </p>
              <div>
                <div style="display:flex;">
                  <div class="com-table mb25">
                    <p class="com-left">项目名称:</p>
                    <span class="ax_default">{{acceptanceApplyData.projectName}}</span>
                  </div>
                  <div class="com-table mb25">
                    <p class="com-left">验收部位:</p>
                    <span class="ax_default">{{acceptanceApplyData.acceptancePosition}}</span>
                  </div>
                </div>

                <div style="display:flex;">
                  <div class="com-table mb25">
                    <p class="com-left">验收内容:</p>
                    <span class="ax_default">{{acceptanceApplyData.acceptanceContent}}</span>
                  </div>
                  <div class="com-table mb25">
                    <p class="com-left">申请人:</p>
                    <span class="ax_default">{{acceptanceApplyData.applyPersonName}}</span>
                  </div>
                </div>

                <div style="display:flex;">
                  <div class="com-table mb25">
                    <p class="com-left">申请时间:</p>
                    <span class="ax_default">{{acceptanceApplyData.applyDate}}</span>
                  </div>
                </div>

                <div v-for="item in acceptanceRecordData" :key="item.id">
                  <div v-if="item.id!=null && item.id!=''">
                    <div v-if="item.acceptanceState == '2'">
                      <div v-if="acceptanceApplyData.acceptanceState == '3'">
                        <div style="display:flex;">
                          <div class="com-table mb25">
                            <p class="com-left">计划验收时间:</p>
                            <span class="ax_default">{{item.planverificationDate}}</span>
                          </div>
                          <div class="com-table mb25">
                            <p class="com-left">验收人:</p>
                            <span class="ax_default">{{item.acceptancePersonName}}</span>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

              </div>
            </div>

            <!-- ----------------------------------  -->
            <div>
              <div v-for="item in acceptanceRecordData" :key="item.id">
                <!-- 验收结果 -->
                <div v-if="item.id!=null && item.id!=''">
                  <p class="tableP mb20">
                    <span style="color:#666;">验收结果</span>
                  </p>

                  <div>
                    <div style="display:flex;">
                      <div class="com-table mb25">
                        <p class="com-left">验收人:</p>
                        <span class="ax_default">{{item.verifyPersonName}}</span>
                      </div>
                      <div class="com-table mb25">
                        <p class="com-left">验收时间:</p>
                        <span class="ax_default">{{item.verificationDate}}</span>
                      </div>
                    </div>

                    <div style="display:flex;">
                      <div class="com-table mb25">
                        <p class="com-left">结果描述:</p>
                        <span class="ax_default">{{item.acceptanceDescribe}}</span>
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
                        <span style="margin-right:8px;color:#fff;background:red;border-radius:5px;padding:5px;font-weight:400;">
                            {{item.acceptanceStateName}}
                        </span>
                      </div>
                    </div>
                  </div>
                </div>

                <!-- 再次申请 -->
                <div v-if="item.acceptanceState == '2'">
                  <div v-if="acceptanceApplyData.acceptanceState == '3'">
                    <p class="tableP mb20">
                      <span style="color:#666;">再次申请</span>
                    </p>
                    <div>
                      <div style="display:flex;">
                        <div class="com-table mb25">
                          <p class="com-left">计划验收时间:</p>
                          <span class="ax_default">{{acceptanceApplyData.planverificationDate}}</span>
                        </div>
                        <div class="com-table mb25">
                          <p class="com-left">验收人:</p>
                          <span class="ax_default">{{acceptanceApplyData.verifyPersonName}}</span>
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
              @click="dialogReapplyCheckDetail=false"
            >关闭</el-button>
          </div>
        </el-dialog>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import { listCheckManager,addRepair,addVerify,checkManagerDetail,checkManagerDetailList,listAcceptanceApply,addRecord,getCheckUser,addAcceptIncrease,acceptanceApplyDetail,acceptanceRecordDetail } from "@/api/ws/workTask";
  import Team from "../workTask/index";

  // 工作任务质量检查查询参数待整改
  const repairQueryData = {
    pageNum: 1,
    pageSize: 10,
    milepostName: '',
    milepostSort: null,
    status:'1',
  };

  // 工作任务质量检查查询参数待验证
  const verifyQueryData = {
    pageNum: 1,
    pageSize: 10,
    milepostName: '',
    milepostSort: null,
    status:'2',
  };
  // 工作任务质量验收查询参数待验收
  const applyQueryData = {
    pageNum: 1,
    pageSize: 10,
    milepostName: '',
    milepostSort: null,
    acceptanceState:'3',
  };

  // 工作任务质量验收查询参数不合格
  const acceptanceQueryData = {
    pageNum: 1,
    pageSize: 10,
    milepostName: '',
    milepostSort: null,
    acceptanceState:'2',
  };

export default {
  name: "WorkTask",


  data() {
    return {

      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7;
        },
      },
      repairQuery: Object.assign({}, repairQueryData),
      verifyQuery: Object.assign({}, verifyQueryData),
      applyQuery: Object.assign({}, applyQueryData),
      acceptanceQuery: Object.assign({}, acceptanceQueryData),
      uploadUrl:
        "https://tools.retalltech.com/api/public/v1/aliyun_oss_tools/upload_photo", //图片上传地址
      checkManagerData:[],
      activeName: "first", //选中的状态值，是tab中的name
      dateRange: [],
      queryParams: {
        deptName: undefined,
        status: undefined
      },
      repairCheckManagerList:null,
      verifyCheckManagerList:null,
      checkTableData: '',
      checkTableDataNo:'',

      dialogVisibleimg:false,
      dialogImageUrl:[],
      limit:4,
      total: 1,
      statusOptions: [],

      // 分页参数
      queryParams1: {
        pageNum: 1,
        pageSize: 10,
        roleName: undefined,
        roleKey: undefined,
        status: undefined
      },

      // 质量问题分类模板相关
      form: {},
      formQuality: {
        name: "",
        id: "",
        isActive: ""
      },

      beforRectificationImg:[1,2,3],

      //待整改、待验证、待验收、不合格中进行的按钮
      dialogToRectification: false, //整改的弹框
      dialogToVerify: false, //验证的弹框
      dialogToAcceptance: false,
      dialogToReapply:false,

      // 待整改、待验证、待验收、不合格中查看弹框控制按钮
      dialogRectificationCheckDetail: false,
      dialogVerifyCheckDetail: false,
      dialogAcceptanceCheckDetail: false,
      dialogReapplyCheckDetail:false,
      dialogCheckDetail:false,
      userList:'',
      rectificationData: {
        //进行整改填写的数据
        repairResultDescription: '',
        repairImageAddres:'',
        checkmanagerId:'',
      },


      resultVerify: {//验收填写数据
        checkmanagerId:"",
        verifyResultDescription: "",
        verifyImageAddres:"",
        verifyResult: ""
      },

      resultRecord:{
        acceptanceDescribe:'',
        acceptanceState:'',
        applyId:''
      },

      // 再次申请的回显数据
      reapplyData:{
        acceptancePosition:'',
        acceptanceContent:'',
        planverificationDate:'',
        verifyPersonName:'',
        acceptanceContent:'',
        acceptancePersonId:'',
        id:''
      },
      acceptanceApplyData:'',
      acceptanceRecordData:'',

      imgUrlList: [] //图片上传成功后的地址
    };
  },

  created(){
    this.getQualityList(),
    this.getUser()
  },

  methods: {

    //查询人员
    getUser(){
      getCheckUser().then(response =>{
          this.userList = response.rows;
        }
      );
    },
    // 获取不同状态的整改列表 如待整改、待验收等
    getRectificationList() {},

    // 点击tabs事件
    handleClickChange(tab, event) {
      console.log("tabs event", tab);
      console.log("tabs event2", event);
      this.activeName = tab.name;
      this.getRectificationList();
    },

    handleQuery() {},
    //分页相关
    getList() {},

    //查询列表
    getQualityList(){
      //查询质量检查待整改列表
      listCheckManager(this.repairQuery).then(response =>{
          this.repairCheckManagerList = response.rows;
          this.total=response.total
        }
      );
      //查询质量检查待验证列表
      listCheckManager(this.verifyQuery).then(response =>{
          this.verifyCheckManagerList = response.rows;
          this.total=response.total
        }
      );
      //查询质量验收待验收列表
      listAcceptanceApply(this.applyQuery).then(response =>{
          this.checkTableData = response.rows;
          this.total=response.total
        }
      );
      //查询质量验收不合格列表
      listAcceptanceApply(this.acceptanceQuery).then(response =>{
          this.checkTableDataNo = response.rows;
          this.total=response.total
        }
      );
    },

    

    //待整改中的查看
    checkRectificationDetail(value) {
      this.dialogRectificationCheckDetail = true;
      checkManagerDetail(value.id).then(response => {
        this.checkManagerData = response.data;
      });
      checkManagerDetailList(value.id).then(response => {
        this.checkManagerDataList = response.data;
      });
    },
    //待验证中查看
    checkProblemDetail(value){
      this.dialogVerifyCheckDetail = true;
      checkManagerDetail(value.id).then(response => {
        this.checkManagerData = response.data;
      });
      checkManagerDetailList(value.id).then(response => {
        this.checkManagerDataList = response.data;
      });
    },

    //待验证中的查看
    checkVerifyDetail(row) {
      this.dialogVerifyCheckDetail = true;
    },

    //待验收中的查看
    checkAcceptanceDetail(value){
      this.dialogAcceptanceCheckDetail=true
      acceptanceApplyDetail(value.id).then(response => {
        this.acceptanceApplyData = response.data;
      });
      acceptanceRecordDetail(value.id).then(response => {
        this.acceptanceRecordData = response.data;
      });
    },

    // 不合格中的查看
    checkDisqualificationDetail(value){
      this.dialogReapplyCheckDetail=true
      acceptanceApplyDetail(value.id).then(response => {
        this.acceptanceApplyData = response.data;
      });
      acceptanceRecordDetail(value.id).then(response => {
        this.acceptanceRecordData = response.data;
      });
    },

    //进行整改的按钮
    handleToRectification(value) {
      this.dialogToRectification = true;
      this.rectificationData.checkmanagerId=value.id;
    },
    //整改
    addRectification: function () {
      addRepair(this.rectificationData).then(response => {
        if(response.code == 200){
          this.$message({
            message: response.msg,
            type: 'success',
            duration:1000
          });
          this.dialogToRectification = false,
            this.getQualityList()
        }
      });
    },

    // 进行验证的按钮
    handleToVerify(value) {
      this.dialogToVerify = true;
      this.resultVerify.checkmanagerId=value.id;

    },
    //验证
    addVerifyfication: function () {
      addVerify(this.resultVerify).then(response => {
        if(response.code == 200){
          this.$message({
            message: response.msg,
            type: 'success',
            duration:1000
          });
          this.dialogToVerify = false,
          this.getQualityList()
        }
      });
    },


    //验收里的保存
    addAcceptance: function () {
      addRecord(this.resultRecord).then(response => {
        if(response.code == 200){
          this.$message({
            message: response.msg,
            type: 'success',
            duration:1000
          });
          this.dialogToAcceptance = false,
          this.getQualityList()
        }
      });
    },
    // 再次申请里的保存
    addApplication(){
      addAcceptIncrease(this.reapplyData).then(response=>{
        if(response.code == 200) {
          this.$message({
            message: response.msg,
            type: 'success',
            duration: 1000
          });
          this.dialogToReapply = false,
            this.getQualityList()
        }
      });
    },

    // 进行验收的按钮
    handleToAcceptance(value){
        this.dialogToAcceptance=true
        this.resultRecord.applyId=value.id;
    },
    // 不合格中再次申请的按钮
    handleToReapply(row){
        this.dialogToReapply=true
        this.reapplyData.acceptancePosition=row.acceptancePosition
        this.reapplyData.acceptanceContent=row.acceptanceContent
        this.reapplyData.planverificationDate=row.planverificationDate
        //this.reapplyData.acceptancePersonId=row.verifyPersonName
        this.reapplyData.id=row.id
    },
    

    // 图片上传成功
    uploadSuccess1(res, file) {
      this.imgUrlList.push(res.content.url);
      console.log("rrrrrrrrrrr", this.imgUrlList);
    },
    //图片删除
    handleRemove(file, fileList) {
      console.log("remove", file, fileList);
    },
    // 预览
    handlePreview(){

    },

    //验证里的保存
    addVerify(){

    }




  }
};
</script>

<style lang="scss" scoped>
.qualityLevel{
    padding-left:0 !important;
    padding-right:0 !important;
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
    line-height: normal;
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
