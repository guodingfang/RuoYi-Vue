<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="企业名称" prop="unitName">
        <el-input
          v-model="queryParams.unitName"
          placeholder="请输入企业名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="企业类型ID" prop="unitTypeId">
        <el-input
          v-model="queryParams.unitTypeId"
          placeholder="请输入企业类型ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="社会统一信用代码" prop="unitOrgcode">
        <el-input
          v-model="queryParams.unitOrgcode"
          placeholder="请输入社会统一信用代码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="企业性质code" prop="unitNatureCode">
        <el-input
          v-model="queryParams.unitNatureCode"
          placeholder="请输入企业性质code"
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
          v-hasPermi="['ws:participatingUnitLibrary:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['ws:participatingUnitLibrary:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['ws:participatingUnitLibrary:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['ws:participatingUnitLibrary:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="participatingUnitLibraryList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID 主键" align="center" prop="id" />
      <el-table-column label="企业名称" align="center" prop="unitName" />
      <el-table-column label="企业类型ID" align="center" prop="unitTypeId" />
      <el-table-column label="社会统一信用代码" align="center" prop="unitOrgcode" />
      <el-table-column label="注册日期" align="center" prop="regDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.regDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="注册区域ID" align="center" prop="regAreaId" />
      <el-table-column label="注册资本" align="center" prop="regCapital" />
      <el-table-column label="企业性质code" align="center" prop="unitNatureCode" />
      <el-table-column label="法人代表" align="center" prop="legalPerson" />
      <el-table-column label="营业地址" align="center" prop="address" />
      <el-table-column label="联系人" align="center" prop="contacts" />
      <el-table-column label="联系电话" align="center" prop="contactNumber" />
      <el-table-column label="办公电话" align="center" prop="officeNumber" />
      <el-table-column label="传真号" align="center" prop="faxNumber" />
      <el-table-column label="备注" align="center" prop="comment" />
      <el-table-column label="班组名词连接字符" align="center" prop="teamNames" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['ws:participatingUnitLibrary:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['ws:participatingUnitLibrary:remove']"
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

    <!-- 添加或修改参建单位库对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="企业名称" prop="unitName">
          <el-input v-model="form.unitName" placeholder="请输入企业名称" />
        </el-form-item>
        <el-form-item label="企业类型ID" prop="unitTypeId">
          <el-input v-model="form.unitTypeId" placeholder="请输入企业类型ID" />
        </el-form-item>
        <el-form-item label="社会统一信用代码" prop="unitOrgcode">
          <el-input v-model="form.unitOrgcode" placeholder="请输入社会统一信用代码" />
        </el-form-item>
        <el-form-item label="注册日期" prop="regDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.regDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择注册日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="注册区域ID" prop="regAreaId">
          <el-input v-model="form.regAreaId" placeholder="请输入注册区域ID" />
        </el-form-item>
        <el-form-item label="注册资本" prop="regCapital">
          <el-input v-model="form.regCapital" placeholder="请输入注册资本" />
        </el-form-item>
        <el-form-item label="企业性质code" prop="unitNatureCode">
          <el-input v-model="form.unitNatureCode" placeholder="请输入企业性质code" />
        </el-form-item>
        <el-form-item label="法人代表" prop="legalPerson">
          <el-input v-model="form.legalPerson" placeholder="请输入法人代表" />
        </el-form-item>
        <el-form-item label="营业地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入营业地址" />
        </el-form-item>
        <el-form-item label="联系人" prop="contacts">
          <el-input v-model="form.contacts" placeholder="请输入联系人" />
        </el-form-item>
        <el-form-item label="联系电话" prop="contactNumber">
          <el-input v-model="form.contactNumber" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="办公电话" prop="officeNumber">
          <el-input v-model="form.officeNumber" placeholder="请输入办公电话" />
        </el-form-item>
        <el-form-item label="传真号" prop="faxNumber">
          <el-input v-model="form.faxNumber" placeholder="请输入传真号" />
        </el-form-item>
        <el-form-item label="备注" prop="comment">
          <el-input v-model="form.comment" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="班组名词连接字符" prop="teamNames">
          <el-input v-model="form.teamNames" placeholder="请输入班组名词连接字符" />
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
import { listParticipatingUnitLibrary, getParticipatingUnitLibrary, delParticipatingUnitLibrary, addParticipatingUnitLibrary, updateParticipatingUnitLibrary, exportParticipatingUnitLibrary } from "@/api/ws/participatingUnitLibrary";

export default {
  name: "ParticipatingUnitLibrary",
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
      // 参建单位库表格数据
      participatingUnitLibraryList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        unitName: undefined,
        unitTypeId: undefined,
        unitOrgcode: undefined,
        unitNatureCode: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        unitName: [
          { required: true, message: "企业名称不能为空", trigger: "blur" }
        ],
        unitTypeId: [
          { required: true, message: "企业类型ID不能为空", trigger: "blur" }
        ],
        unitOrgcode: [
          { required: true, message: "社会统一信用代码不能为空", trigger: "blur" }
        ],
        regAreaId: [
          { required: true, message: "注册区域ID不能为空", trigger: "blur" }
        ],
        unitNatureCode: [
          { required: true, message: "企业性质code不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询参建单位库列表 */
    getList() {
      this.loading = true;
      listParticipatingUnitLibrary(this.queryParams).then(response => {
        this.participatingUnitLibraryList = response.rows;
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
        unitName: undefined,
        unitTypeId: undefined,
        unitOrgcode: undefined,
        regDate: undefined,
        regAreaId: undefined,
        regCapital: undefined,
        unitNatureCode: undefined,
        legalPerson: undefined,
        address: undefined,
        contacts: undefined,
        contactNumber: undefined,
        officeNumber: undefined,
        faxNumber: undefined,
        comment: undefined,
        teamNames: undefined
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
      this.title = "添加参建单位库";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getParticipatingUnitLibrary(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改参建单位库";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateParticipatingUnitLibrary(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addParticipatingUnitLibrary(this.form).then(response => {
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
      this.$confirm('是否确认删除参建单位库编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delParticipatingUnitLibrary(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有参建单位库数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportParticipatingUnitLibrary(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>