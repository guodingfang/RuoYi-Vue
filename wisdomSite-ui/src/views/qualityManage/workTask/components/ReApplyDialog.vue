<template>
  <el-dialog title="验收" :visible.sync="flag" width="70%" @close="closeDialog" :close-on-click-modal="false">
    <div class="add-project-wrap">
      <el-form ref="acceptForm" :model="acceptForm" :rules="rules" size="small" label-width="120px">
        <el-form-item label="验收部位：" prop="acceptanceState">
          <el-input type="text" disabled v-model="acceptForm.acceptancePosition" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="验收内容：" prop="acceptanceContent">
          <el-input disabled type="textarea" :rows="2" v-model="acceptForm.acceptanceContent" placeholder=""></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="验收人：">
              <el-select
                placeholder="请选择验收人"
                v-model="acceptForm.verifyPersonId"
                class="w100"
              >
                <el-option
                  v-for="item in userList"
                  :label="item.nickName"
                  :value="item.userId"
                  :key="item.userId"
                >
                  <span style="float: left">{{ item.nickName }}</span>
                  <span style="float: right; color: #8492a6; font-size: 13px">{{ item.phonenumber }}</span>
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="验收时限：">
              <el-date-picker
                v-model="acceptForm.planverificationDate"
                type="date"
                placeholder=""
                :picker-options="pickerOptions"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="cancel()">取消</el-button>
        <el-button size="small" type="primary" @click="addAcceptance()">保存</el-button>
      </div>
    </div>
  </el-dialog>
</template>

<script>
  import MulPicUpload from '@/components/MulPicUpload/index';
  import { addAcceptIncrease } from '@/api/quality/workTask';
  import { getCheckUser } from "@/api/quality/checkManager";
  export default {
    name: 'ReApplyDialog',
    components: { MulPicUpload },
    props: {
      dialogFlag: {
        required: true,
        type: Boolean,
        default: false,
      },
      id: {
        required: true,
      },
      acceptForms: {
        type: Object,
        default: () => {},
      },
    },
    data() {
      return{
        pickerOptions: {
          disabledDate(time) {
            return time.getTime() + 24 * 3600 * 1000 < Date.now();
          },
        },
        acceptForm: {
          acceptancePosition: '',
          acceptanceContent: '',
          verifyPersonId: '',
          planverificationDate: ""
        },
        dialogImageUrl: '',
        dialogVisible: false,
        // 表单校验
        rules: {
          acceptanceState: [
            { required: true, message: "请选择结果", trigger: "blur" }
          ],
          acceptanceDescribe: [
            { required: true, message: "请输描述", trigger: "blur" }
          ]
        },
        userList: []
      }
    },
    created() {
      this.getUser();
    },
    methods: {
      //查询人员
      getUser() {
        getCheckUser({
          enterpriseId: this.$store.state.user.enterpriseId
        }).then(response => {
          this.userList = response.rows;
        });
      },
      /** 验收*/
      addAcceptance() {
        this.$refs["acceptForm"].validate( valid => {
          if (!valid) return;
          this.acceptForm.applyId = this.id;
          addAcceptIncrease(this.acceptForm).then(res => {
            this.msgSuccess("添加成功");
            this.flag = false;
            this.$emit('query');
          }).catch(e => {
            console.log(e)
          })
        });
      },
      /** 关闭框 */
      closeDialog() {
        this.acceptForm = {};
        this.$refs["acceptForm"].resetFields();
      },
      cancel() {
        this.flag = false;
      },
      picChange(pic) {
        this.acceptForm.verifyImageAddres = pic;
      }
    },
    computed: {
      flag: {
        get() {
          return this.dialogFlag;
        },
        set(val) {
          this.$emit('update:dialogFlag', val);
        },
      },
    },
    watch: {
      acceptForms(val) {
        this.acceptForm = Object.assign({}, val);
      },
    }
  }
</script>

<style scoped>

</style>
