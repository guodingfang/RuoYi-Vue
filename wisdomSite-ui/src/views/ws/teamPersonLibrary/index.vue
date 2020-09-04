<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="身份证编号" prop="idNumber">
        <el-input
          v-model="queryParams.idNumber"
          placeholder="请输入身份证编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="人员类型 建筑工人：0；管理人员：1；" prop="personType">
        <el-select v-model="queryParams.personType" placeholder="请选择人员类型 建筑工人：0；管理人员：1；" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="人员状态  退场：0 ;在场：1；" prop="personStatus">
        <el-select v-model="queryParams.personStatus" placeholder="请选择人员状态  退场：0 ;在场：1；" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="人员黑名单 黑名单：0；白名单：1" prop="isBlack">
        <el-input
          v-model="queryParams.isBlack"
          placeholder="请输入人员黑名单 黑名单：0；白名单：1"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否班组长 是：1；否：0" prop="isMonitor">
        <el-input
          v-model="queryParams.isMonitor"
          placeholder="请输入是否班组长 是：1；否：0"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="实名卡号" prop="cardNo">
        <el-input
          v-model="queryParams.cardNo"
          placeholder="请输入实名卡号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号" prop="mobilePhone">
        <el-input
          v-model="queryParams.mobilePhone"
          placeholder="请输入手机号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['ws:teamPersonLibrary:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['ws:teamPersonLibrary:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['ws:teamPersonLibrary:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['ws:teamPersonLibrary:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="teamPersonLibraryList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="身份证编号" align="center" prop="idNumber" />
      <el-table-column label="人员类型 建筑工人：0；管理人员：1；" align="center" prop="personType" />
      <el-table-column label="人员状态  退场：0 ;在场：1；" align="center" prop="personStatus" />
      <el-table-column label="人员黑名单 黑名单：0；白名单：1" align="center" prop="isBlack" />
      <el-table-column label="是否班组长 是：1；否：0" align="center" prop="isMonitor" />
      <el-table-column label="是否特殊作业人员 是：1；否：2" align="center" prop="isSpecial" />
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="性别 男：1，女：2" align="center" prop="sex" />
      <el-table-column label="民族代码" align="center" prop="nationCode" />
      <el-table-column label="出生日期" align="center" prop="birthDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.birthDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="地址" align="center" prop="address" />
      <el-table-column label="签发机关" align="center" prop="authority" />
      <el-table-column label="生效日期" align="center" prop="effectiveDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.effectiveDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="失效日期" align="center" prop="invalidDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.invalidDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="户口性质" align="center" prop="residenceNature" />
      <el-table-column label="实名卡号" align="center" prop="cardNo" />
      <el-table-column label="手机号" align="center" prop="mobilePhone" />
      <el-table-column label="工资卡号" align="center" prop="payCard" />
      <el-table-column label="工资卡银行" align="center" prop="payCardBank" />
      <el-table-column label="开户行名称" align="center" prop="nameOfBank" />
      <el-table-column label="政治面貌" align="center" prop="politicsStatusCode" />
      <el-table-column label="文化程度代码" align="center" prop="cultureStandardCode" />
      <el-table-column label="血压" align="center" prop="bloodPressure" />
      <el-table-column label="心率" align="center" prop="heartRate" />
      <el-table-column label="人脸照片路径" align="center" prop="faceUrl" />
      <el-table-column label="体重" align="center" prop="weight" />
      <el-table-column label="身高(cm)" align="center" prop="height" />
      <el-table-column label="健康相关" align="center" prop="healthRelated" />
      <el-table-column label="健康备注" align="center" prop="healthRemark" />
      <el-table-column label="合同编号" align="center" prop="contactNumber" />
      <el-table-column label="合同类型" align="center" prop="contactType" />
      <el-table-column label="合同生效日期" align="center" prop="contactEffectiveDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.contactEffectiveDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="合同失效日期" align="center" prop="contactInvalidDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.contactInvalidDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="合同附件1地址" align="center" prop="attachmentOneUrl" />
      <el-table-column label="备注" align="center" prop="comment" />
      <el-table-column label="创建时间" align="center" prop="createdTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['ws:teamPersonLibrary:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['ws:teamPersonLibrary:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改班组人员库对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="身份证编号" prop="idNumber">
          <el-input v-model="form.idNumber" placeholder="请输入身份证编号" />
        </el-form-item>
        <el-form-item label="人员类型 建筑工人：0；管理人员：1；">
          <el-select v-model="form.personType" placeholder="请选择人员类型 建筑工人：0；管理人员：1；">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="人员状态  退场：0 ;在场：1；">
          <el-radio-group v-model="form.personStatus">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="人员黑名单 黑名单：0；白名单：1" prop="isBlack">
          <el-input v-model="form.isBlack" placeholder="请输入人员黑名单 黑名单：0；白名单：1" />
        </el-form-item>
        <el-form-item label="是否班组长 是：1；否：0" prop="isMonitor">
          <el-input v-model="form.isMonitor" placeholder="请输入是否班组长 是：1；否：0" />
        </el-form-item>
        <el-form-item label="是否特殊作业人员 是：1；否：2" prop="isSpecial">
          <el-input v-model="form.isSpecial" placeholder="请输入是否特殊作业人员 是：1；否：2" />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="性别 男：1，女：2">
          <el-select v-model="form.sex" placeholder="请选择性别 男：1，女：2">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="民族代码" prop="nationCode">
          <el-input v-model="form.nationCode" placeholder="请输入民族代码" />
        </el-form-item>
        <el-form-item label="出生日期" prop="birthDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.birthDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择出生日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入地址" />
        </el-form-item>
        <el-form-item label="签发机关" prop="authority">
          <el-input v-model="form.authority" placeholder="请输入签发机关" />
        </el-form-item>
        <el-form-item label="生效日期" prop="effectiveDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.effectiveDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择生效日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="失效日期" prop="invalidDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.invalidDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择失效日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="户口性质" prop="residenceNature">
          <el-input v-model="form.residenceNature" placeholder="请输入户口性质" />
        </el-form-item>
        <el-form-item label="实名卡号" prop="cardNo">
          <el-input v-model="form.cardNo" placeholder="请输入实名卡号" />
        </el-form-item>
        <el-form-item label="手机号" prop="mobilePhone">
          <el-input v-model="form.mobilePhone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="工资卡号" prop="payCard">
          <el-input v-model="form.payCard" placeholder="请输入工资卡号" />
        </el-form-item>
        <el-form-item label="工资卡银行" prop="payCardBank">
          <el-input v-model="form.payCardBank" placeholder="请输入工资卡银行" />
        </el-form-item>
        <el-form-item label="开户行名称" prop="nameOfBank">
          <el-input v-model="form.nameOfBank" placeholder="请输入开户行名称" />
        </el-form-item>
        <el-form-item label="政治面貌" prop="politicsStatusCode">
          <el-input v-model="form.politicsStatusCode" placeholder="请输入政治面貌" />
        </el-form-item>
        <el-form-item label="文化程度代码" prop="cultureStandardCode">
          <el-input v-model="form.cultureStandardCode" placeholder="请输入文化程度代码" />
        </el-form-item>
        <el-form-item label="血压" prop="bloodPressure">
          <el-input v-model="form.bloodPressure" placeholder="请输入血压" />
        </el-form-item>
        <el-form-item label="心率" prop="heartRate">
          <el-input v-model="form.heartRate" placeholder="请输入心率" />
        </el-form-item>
        <el-form-item label="人脸照片路径" prop="faceUrl">
          <el-input v-model="form.faceUrl" placeholder="请输入人脸照片路径" />
        </el-form-item>
        <el-form-item label="体重" prop="weight">
          <el-input v-model="form.weight" placeholder="请输入体重" />
        </el-form-item>
        <el-form-item label="身高(cm)" prop="height">
          <el-input v-model="form.height" placeholder="请输入身高(cm)" />
        </el-form-item>
        <el-form-item label="健康相关" prop="healthRelated">
          <el-input v-model="form.healthRelated" placeholder="请输入健康相关" />
        </el-form-item>
        <el-form-item label="健康备注" prop="healthRemark">
          <el-input v-model="form.healthRemark" placeholder="请输入健康备注" />
        </el-form-item>
        <el-form-item label="合同编号" prop="contactNumber">
          <el-input v-model="form.contactNumber" placeholder="请输入合同编号" />
        </el-form-item>
        <el-form-item label="合同类型">
          <el-select v-model="form.contactType" placeholder="请选择合同类型">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="合同生效日期" prop="contactEffectiveDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.contactEffectiveDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择合同生效日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="合同失效日期" prop="contactInvalidDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.contactInvalidDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择合同失效日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="合同附件1地址" prop="attachmentOneUrl">
          <el-input v-model="form.attachmentOneUrl" placeholder="请输入合同附件1地址" />
        </el-form-item>
        <el-form-item label="备注" prop="comment">
          <el-input v-model="form.comment" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listTeamPersonLibrary, getTeamPersonLibrary, delTeamPersonLibrary, addTeamPersonLibrary, updateTeamPersonLibrary, exportTeamPersonLibrary } from "@/api/ws/teamPersonLibrary";

export default {
  name: "TeamPersonLibrary",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 班组人员库表格数据
      teamPersonLibraryList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        idNumber: undefined,
        personType: undefined,
        personStatus: undefined,
        isBlack: undefined,
        isMonitor: undefined,
        name: undefined,
        cardNo: undefined,
        mobilePhone: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        personType: [
          { required: true, message: "人员类型 建筑工人：0；管理人员：1；不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "姓名不能为空", trigger: "blur" }
        ],
        sex: [
          { required: true, message: "性别 男：1，女：2不能为空", trigger: "blur" }
        ],
        nationCode: [
          { required: true, message: "民族代码不能为空", trigger: "blur" }
        ],
        invalidDate: [
          { required: true, message: "失效日期不能为空", trigger: "blur" }
        ],
        residenceNature: [
          { required: true, message: "户口性质不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询班组人员库列表 */
    getList() {
      this.loading = true;
      listTeamPersonLibrary(this.queryParams).then(response => {
        this.teamPersonLibraryList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        idNumber: undefined,
        personType: undefined,
        personStatus: "0",
        isBlack: undefined,
        isMonitor: undefined,
        isSpecial: undefined,
        name: undefined,
        sex: undefined,
        nationCode: undefined,
        birthDate: undefined,
        address: undefined,
        authority: undefined,
        effectiveDate: undefined,
        invalidDate: undefined,
        residenceNature: undefined,
        cardNo: undefined,
        mobilePhone: undefined,
        payCard: undefined,
        payCardBank: undefined,
        nameOfBank: undefined,
        politicsStatusCode: undefined,
        cultureStandardCode: undefined,
        bloodPressure: undefined,
        heartRate: undefined,
        faceUrl: undefined,
        weight: undefined,
        height: undefined,
        healthRelated: undefined,
        healthRemark: undefined,
        contactNumber: undefined,
        contactType: undefined,
        contactEffectiveDate: undefined,
        contactInvalidDate: undefined,
        attachmentOneUrl: undefined,
        comment: undefined,
        updatedTime: undefined,
        createdTime: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加班组人员库";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getTeamPersonLibrary(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改班组人员库";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateTeamPersonLibrary(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addTeamPersonLibrary(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除班组人员库编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delTeamPersonLibrary(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有班组人员库数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportTeamPersonLibrary(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>