<template>
  <section>
    <el-button type="primary" @click="addConfig" class='btn'>新增智能电表</el-button>
    <span class="jl-green mr10 pointer title">全部（{{total}}）</span>
    <Table class="table-top" :tableData="tableData" :tableConfig="tableConfig">
      <template v-slot:operate>
        <el-table-column label="操作" align="left">
          <template slot-scope="scope">
            <span
              class="jl-green mr10 pointer"
              @click="modifyConfig(scope.row.id)"
              >修改</span
            >
            <span
              class="jl-green mr10 pointer"
              @click="deleteConfig(scope.row)"
              >删除</span
            >
          </template>
        </el-table-column>
      </template>
      <template v-slot:statusName>
        <el-table-column label="启用禁用" align="left">
          <template slot-scope="scope">
            <el-switch
              @change="stateConfig(scope.row)"
              v-model="scope.row.enableDisable"
              :active-value="1"
              :inactive-value="2"
              active-color="#13ce66"
              inactive-color="#909399">
            </el-switch>
          </template>
        </el-table-column>
      </template>
      <template v-slot:whether>
        <el-table-column label="是否总表" align="left">
          <template slot-scope="scope">
            <span v-if="scope.row.whether == 1">是</span>
            <span v-else>否</span>
          </template>
        </el-table-column>
      </template>
    </Table>
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum "
      :limit.sync="queryParams.pageSize"
      @pagination="getCtable"
    />
    <el-dialog
      title="智能电表"
      :visible.sync="isShow"
      width="500px"
      :close-on-click-modal="false"
    >
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="设备名称" prop="equipmentName">
          <el-input
            v-model="ruleForm.equipmentName"
            placeholder="设备名称"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item ref='monitoringEquipment' label="监测设备" prop="monitoringEquipment">
          <!-- <el-select clearable filterable v-model="ruleForm.monitoringEquipment" placeholder="监测设备">
            <el-option
              v-for="item in monit"
              :key="item.serialNumber"
              :label="item.serialNumber"
              :value="item.serialNumber"
            ></el-option>
          </el-select> -->
          <el-input class="btnInput" :disabled="!isCreate" v-model="ruleForm.monitoringEquipment">
            <el-button type="text" slot="prefix" @click="selCams">选择</el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="安装区域" prop="installationArea">
          <el-select clearable filterable v-model="ruleForm.installationArea" placeholder="安装区域">
            <el-option
              v-for="item in site"
              :key="item.id"
              :label="item.partitionName"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否总表">
          <el-radio-group v-model="ruleForm.whether">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="2">否</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button class="r_button" @click="showModel(false)">取消</el-button>
        <el-button type="primary" class="r_button" @click="_submit">保存</el-button>
      </div>
    </el-dialog>
    <sel-cams
      :listData="camLists"
      :dialogFlag.sync="showSels"
      @changes="getCams"
    />
  </section>
</template>
<script>
import { extendMoment } from 'moment-range';
import { getCtable, addConfig, modifyConfig, stateConfig, deleteConfig, detailConfig, listMonit, listSite } from "@/api/scada/configure";
import Table from '@/components/Table';
import SelCams from "./SelCams";
export default {
  name: "configure",
  components: {
    Table,
    SelCams,
    // detailPro,
  },
  data() {
    return {
      tableConfig: [
        { slot: 'operate' },
        { prop: 'equipmentName', label: '设备名称' },
        { prop: 'monitoringEquipment', label: '监测设备'},
        { prop: 'installationAreaName', label: '安装区域'},
        { slot: 'whether' },
        { slot: 'statusName' },
      ],
      total: 0,
      tableData: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      isShow:false,
      ruleForm: {},
      rules: {
        equipmentName: [{ required: true, message: "请输入设备名称", trigger: "blur" }],
        monitoringEquipment: [{ required: true, message: "请选择监测设备" }],
        installationArea: [{ required: true, message: "请选择安装区域", trigger: "change" }],
      },
      monit: [],
      site: [],
      state: null,
      showSels:false,
      camLists: [],
    };
  },
  computed: {
    isCreate() {
      return this.state === 'add'? true: false
    }
  },
  async created() {
    this.getCtable()
  },
  methods: {
    async selCams() {
      // const { rows:camList } = await getChannelList(0);
      this.camLists = this.monit
      this.showSels = true;
      // const { rows:camList } = this.videoEquipment
    },
    getCams(e) {
      this.ruleForm.monitoringEquipment = e.serialNumber
      this.$nextTick(()=>{
        this.$refs.monitoringEquipment.clearValidate();
      })
    },
    async getSelect() {
      const re = await listMonit()
      this.monit = re.rows
      const res = await listSite()
      this.site = res.rows
    },
    async getCtable() {
      const re = await getCtable(this.queryParams)
      this.tableData = re.rows
      this.total = re.total
      this.getSelect()
    },
    showModel(flag) {
      this.isShow = flag
    },
    addConfig() {
      this.ruleForm = {}
      this.state = 'add'
      this.showModel(true)
    },
    async modifyConfig(id) {
      //projectId
      this.state = 'modify'
      const re = await detailConfig(id)
      this.ruleForm = re.data
      this.showModel(true)
    },
    _submit() {
      this.$refs["ruleForm"].validate(async valid => {
        if (!valid) return;
        this.loading = true
        let re
        if (this.state === 'add') {
          re = await addConfig(this.ruleForm);
        } else {
          re = await modifyConfig(this.ruleForm);
        }
        if (re.code == 200) {
          this.$message({
            message: re.msg,type: "success",
          });
          this.showModel(false)
          this.getCtable()
        }
        this.loading = false
      });
    },
    async stateConfig(row) {
      stateConfig({
        enableDisable: row.enableDisable,
        id: row.id
      }).then(res=>{
        if (res.code == 200) {
          this.$message({
            message: res.msg,
            type: "success",
          });
        }
        this.getCtable();
      }).catch(err=>{
        this.getCtable();
      })
    },
    async deleteConfig(row) {
      if (row.enableDisable != 2) {
        this.$message({
          message: '状态为禁用时才可删除',
          type: 'warning'
        });
        return
      }
      return new Promise(res => {
        this.$confirm("是否确认删除?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(async () => {
            const re = await deleteConfig(row.id);
            if (re.code === 200) {
              this.$message({
                message: re.msg,
                type: "success",
              });
              this.getCtable();
            }
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除"
            });
          });
      });
    },
  }
};
</script>
<style lang="scss" scoped>
.title{
  display: inline-block;
  margin: 20px 0;
}
.btn{
  position: absolute;
  right: 0;
  top: -42px;
}
</style>
