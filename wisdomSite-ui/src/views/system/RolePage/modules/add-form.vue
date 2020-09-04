<template>
  <!-- 分配角色数据权限对话框 -->
  <el-dialog
    title="角色"
    :visible.sync="visible"
    width="500px"
    append-to-body
    :before-close="handleClose"
  >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="form.roleName" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item label="角色顺序" prop="roleSort">
          <el-input-number v-model="form.roleSort" controls-position="right" :min="0" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
        </el-form-item>
        <el-form-item label="菜单权限">
          <el-tree
            :data="menuOptions"
            show-checkbox
            ref="menu"
            node-key="id"
            empty-text="加载中，请稍后"
            :props="defaultProps"
          ></el-tree>
        </el-form-item>
      </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="_submit">确 定</el-button>
      <el-button @click="handleClose">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { uploadAvatar } from "@/api/system/user";
/* selectList */
import { getRoleList, addRoleUser } from "@/api/system/enterpriseUser";
import { treeselect as menuTreeselect, roleMenuTreeselect } from "@/api/system/menu";
export default {
  name: "addForm",
  data() {
    return {
      // 表单参数
      form: {
        roleSort: 0,
        status: "0"
      },
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
        roleName: [
          { required: true, message: "角色名称不能为空", trigger: "blur" }
        ],
        roleSort: [
          { required: true, message: "角色顺序不能为空", trigger: "blur" }
        ]
      },
      // 状态数据字典
      statusOptions: [],
      // 部门列表
      menuOptions: [],
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
    state: {
      type: String,
      default: () => null
    },
    roleIdList: {
      type: Array,
      default: () => []
    }
  },
  watch: {
    visible: {
      handler(val) {
        if (val) {
          this.roleIds = this.roleIdList;
          // console.log(this.roleIds, this.roleIdList);
        }
      },
      immediate: true
    }
  },
  async created() {
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
    this.getMenuTreeselect();
  },
  methods: {
    /** 查询菜单树结构 */
    getMenuTreeselect() {
      menuTreeselect().then(response => {
        this.menuOptions = response.data;
      });
    },
    handleClose() {
      this.$emit("on-close");
    },
    // 所有菜单节点数据
    getMenuAllCheckedKeys() {
      // 目前被选中的菜单节点
      let checkedKeys = this.$refs.menu.getHalfCheckedKeys();
      // 半选中的菜单节点
      let halfCheckedKeys = this.$refs.menu.getCheckedKeys();
      checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
      return checkedKeys;
    },
    /** 提交按钮 */
    _submit: function() {
      this.form.menuIds = this.getMenuAllCheckedKeys();
      addRoleUser({
        ...this.form,
      }).then(res => {
        if (res.code === 200) {
          this.$message({
            message: res.msg,type: "success",
          });
          this.$emit("on-close", true);
        }
      });
    }
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
