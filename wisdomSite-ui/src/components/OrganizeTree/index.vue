<template>
  <div>
    <el-input
      placeholder="项目名称搜索"
      suffix-icon="el-icon-search"
      v-model="orgName"
    />
    <div class="tree-con">
      <el-tree
        :data="organizeData"
        :props="defaultProps"
        :expand-on-click-node="false"
        :filter-node-method="filterNode"
        node-key="id"
        ref="tree"
        :default-expanded-keys="expandArr"
        @node-click="handleNodeClick"
      >
<!--          <span class="custom-tree-node" slot-scope="{ node, data }">
            <span>{{ node.label }}</span>
            <span class="tree_right">
              <el-button
                type="text"
                size="mini"
                v-show="data.id === selNode.id"
                @click="() => modifyForm(node, data)"
              >
                编辑
              </el-button>
              <el-button
                type="text"
                size="mini"
                v-show="data.id === selNode.id"
                @click="() => deleteForm(node, data)"
              >
                删除
              </el-button>
            </span>
          </span>-->
      </el-tree>
    </div>
  </div>
</template>

<script>
  import {
    getOrgTree,
  } from '@/api/system/organize';
  export default {
    name: "OrganizeTree",
    data() {
      return {
        orgName: '',
        organizeData: undefined,
        defaultProps: {
          children: "children",
          label: "label"
        },
        selNode: {
          type: '',
          id: '',
        },
        expandArr: []
      }
    },
    created() {
      this.getOrgTree();
    },
    methods: {
      /** 获取组织树*/
      getOrgTree() {
        getOrgTree().then(res => {
          // this.expandArr = [ res.data[0].id ];
          this.expandArr = [ this.$store.state.user.selectRole.id || res.data[0].id ];
          this.organizeData = res.data;
        })
      },
      // 筛选节点
      filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
      },
      // 节点单击事件
      handleNodeClick(data) { //原先有data node ele三个参数
        this.selNode = data;
        // const {id, type} = data;
        this.$emit('node-click', data);
      },
    },
    watch: {
      // 根据名称筛选部门树
      orgName(val) {
        this.$refs.tree.filter(val);
      }
    }
  }
</script>

<style scoped lang="scss">
  .tree-con{
    margin-top: 30px;
  }
  .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    padding-right: 8px;
  }
</style>
