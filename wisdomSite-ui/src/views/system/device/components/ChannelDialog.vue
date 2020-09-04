<template>
  <el-dialog  title="视频设备绑定" :visible.sync="flag" width="800px" @close="closeDialog" :close-on-click-modal="false">
    <el-form
      ref="channForm"
      :model="channForm"
      :rules="camRules"
      size="medium"
      label-width="140px"
    >
      <el-form-item label="工程名称：" prop="projectId">
        <project-select type="all" :disabled="true" :formProjId.sync="channForm.projectId"/>
      </el-form-item>
      <el-form-item label="设备序列号" prop="equipmentSerialnumber" >
        <el-input  v-model="channForm.equipmentSerialnumber" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="设备验证码：" prop="verificationCode">
        <el-input  v-model="channForm.verificationCode" :disabled="true"></el-input>
      </el-form-item>
      <el-row v-for="(item, index) in channForm.wsSetupInfoList" :key="item.id">
        <el-col :span="12">
          <el-form-item
            label="安装位置："
            :rules="{required: true, message: '安装位置不能为空', trigger: 'blur' }"
          >
            <el-input v-model="item.installationPosition" placeholder=""></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="通道编号："
            :rules="{required: true, message: '通道编号不能为空', trigger: 'blur' }"
          >
            <el-input :disabled="true" v-model.number="item.channelNumber" placeholder=""></el-input>
          </el-form-item>
        </el-col>
<!--        <el-col :span="4" style="text-align: right">-->
<!--          <el-button @click.prevent="delChannel(item)" type="primary" icon="el-icon-delete"></el-button>-->
<!--        </el-col>-->
      </el-row>
<!--      <el-form-item label="" prop="">-->
<!--        <p class="add-channel" @click="addChannel">新增通道</p>-->
<!--      </el-form-item>-->
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleUpdate">保存</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import {
    addCamear,
    updateCamear,
    getCamearWithSN,
    updateChannel,
    delChannel
  } from "@/api/system/device";
  import ProjectSelect from "@/components/CommonSelect/ProjectSelect";
  export default {
    name: 'ChannelDialog',
    components: {
      ProjectSelect
    },
    props: {
      dialogFlag: {
        required: true,
        type: Boolean,
        default: false,
      },
      isCreate: {
        type: Boolean,
        default: true,
      },
      isMove: {
        type: Boolean,
        default: false,
      },
      channForms: {
        type: Object,
        default: () => {},
      },
    },
    data() {
      let checkSN = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('设备序列号不能为空'));
        }
        console.log('====================',this.isCreate)
        if(this.isCreate) {
          getCamearWithSN(value).then(res => {
            if (res.data) {
              return callback(new Error('该设备已存在'));
            }
          })
        }
        callback();
      };
      return {
        channForm: {
          equipmentType: '',
          wsSetupInfoList: [{
            pos: '位置1',
            num: '编号1'
          }],
        },
        treeListProps: {
          label: 'name',
          value: 'id',
        },
        deviceType: [],
        camRules: {
          equipmentSerialnumber: [
            { required: true, message: '请填写设备验证码', trigger: 'blur' },
          ],
          verificationCode: [
            { required: true, message: '请填写设备验证码', trigger: 'blur' },
          ],
          projectId: [
            { required: true, message: '请填写工程名称', trigger: 'blur' },
          ],
          installationPosition: [
            { required: true, message: '请填写安装位置', trigger: 'blur' },
          ],
        },
      };
    },
    created() {
      this.getDicts("ws_equipment_type").then(response => {
        this.deviceType = response.data;
      });
    },
    methods: {
      /* 编辑 */
      handleUpdate() {
        this.$refs.channForm.validate(async (valid) => {
          if (!valid) return;
          try {
            console.log(this.channForm)
            await updateChannel(this.channForm);
            this.msgSuccess('编辑成功');
            this.flag = false;
            //TODO 需要刷新节点的树
            this.$emit('query');
          } catch (e) {
            console.log(e);
          }
        });
      },
      /* 关闭 */
      closeDialog() {
        this.channForm = {};
        this.$refs.channForm.resetFields();
      },
      delChannel(item) {
        if(item.id) {
          this.$confirm('确认删除?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            delChannel(item.id).then(res => {
              //TODO 这里有待优化
              const index = this.channForm.wsSetupInfoList.indexOf(item)
              if (index !== -1) {
                this.channForm.wsSetupInfoList.splice(index, 1)
              }
              this.msgSuccess("删除成功");
            });
          }).catch(() => {});
        } else {
          const index = this.channForm.wsSetupInfoList.indexOf(item)
          if (index !== -1) {
            this.channForm.wsSetupInfoList.splice(index, 1)
          }
        }
      },
      addChannel() {
        this.channForm.wsSetupInfoList.push({
          installationPosition: '',
          channelNumber: '',
          // enableStatus: 0,
          // id: Date.now()
        })
      }
    },
    computed: {
      flag: {
        get() {
          return this.dialogFlag;
        },
        set(val) {
          this.$emit('update:dialogFlag', val);
          this.$emit('update:channForms', {});
          this.isMove ? this.$emit('update:isMove', false) : ''
        },
      },
    },
    watch: {
      channForms(val) {
        this.channForm = Object.assign({}, val);
        // this.channForm.officeId = Number(this.channForm.officeId);
      },
    },
  };
</script>

<style lang='scss' scoped>
.add-channel{
  border: 1px solid #09a17b;
  background: #ffffff;
  height: 36px;
  line-height: 34px;
  border-radius: 5px;
  width: 100%;
  text-align: center;
  font-size: 14px;
  color: #1ab394;
}
</style>
