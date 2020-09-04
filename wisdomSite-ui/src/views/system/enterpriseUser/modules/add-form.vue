<template>
  <el-dialog
    title="角色编辑"
    :visible.sync="visible"
    width="70%"
    :before-close="handleClose"
  >
    <div class="add-wrap">
      <el-checkbox-group v-model="roleIds">
        <el-checkbox v-for="item in roleList" :label="item.roleId" :key='item.roleId'>
          {{item.roleName}}
        </el-checkbox>
      </el-checkbox-group>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" type="primary" @click="submitForm()" :loading="loading"
          >保存</el-button
        >
      </div>
      <!-- <el-dialog :visible.sync="dialogVisible" size="tiny" append-to-body>
        <img
          width="100%"
          :src="imageUrl"
          alt=""
        />
      </el-dialog> -->
    </div>
  </el-dialog>
</template>
<script>
import { uploadAvatar } from "@/api/system/user";
/* selectList */
import { getRoleList, modifyRole } from "@/api/system/enterpriseUser";
export default {
  name: "addForm",
  data() {
    return {
      // 表单校验
      rules: {
        regionName: [
          { required: true, message: "请输入区域名称", trigger: "blur" }
        ],
        parentId: [
          { required: true, message: "请输入所属区域", trigger: "blur" }
        ],
        dialogVisible: false
        // brightspotImageAddres: [
        //   { required: true, message: "请上传照片", trigger: "blur" }
        // ]
      },
      parentList: [],
      loading: false,
      list:[],
      roleIds: this.roleIdList
    };
  },
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    detailData: {
      type: Object,
      default: () => {}
    },
    roleData: {
      type: Object,
      default: () => {}
    },
    state: {
      type: String,
      default: () => null
    },
    roleList: {
      type: Array,
      default: () => []
    },
    roleIdList: {
      type: Array,
      default: () => []
    },
  },
  watch: {
    visible: {
      handler(val) {
        if (val) {
          this.roleIds = this.roleIdList
          console.log(this.roleIds, this.roleIdList);
        }
      },
      immediate:true
    }
  },
  async created() {
    this.getSelect()
  },
  methods: {
    async getSelect() {
      // console.log(re);
    },
    handleClose() {
      this.$emit("on-close");
    },
    /** 提交按钮 */
    submitForm: function() {
      // const jsonArr = this.roleIds.map(i => {
      //   return this.list.find(o => i == o.roleId)
      // })
      modifyRole({
        ...this.roleData,
        roleIds: this.roleIds
      }).then(res => {
        if (res.code === 200) {
          this.msgSuccess("修改成功");
          this.$emit("on-close", true);
        }
      });
    },
  }
};
</script>
<style lang="scss" scoped>
.dialog-footer {
  text-align: right;
}
.avatar {
  width: 100%;
  height: 100%;
}
.add-wrap {
  /deep/.el-upload-list--picture-card {
    .el-upload-list__item {
      width: 100px;
      height: 100px;
      line-height: 102px;
      border-radius: 0;
    }
  }
  /deep/.el-upload--picture-card {
    width: 100px;
    height: 100px;
    line-height: 102px;
    border-radius: 0;
  }
  .tit {
    border-left: 4px solid #1ab394;
    padding-left: 10px;
  }
}
</style>
