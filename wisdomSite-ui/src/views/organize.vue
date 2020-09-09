<template>
  <el-container>
    <el-header>
      <div class="log">
        <i class="jl_logo">  </i>智慧工地平台
      </div>
      <navbar />
    </el-header>
    <el-main>
      <div class="org-box">
        <div class="org-head">请选择您要登录的企业/项目</div>
        <div class="org-cont">
          <el-input
            placeholder="按照名称过滤"
            suffix-icon="el-icon-search"
            v-model="orgName"
          >
          </el-input>
          <section class="tree-cont">
            <el-tree
              :data="organizeData"
              :props="defaultProps"
              :expand-on-click-node="false"
              :filter-node-method="filterNode"
              node-key="id"
              :default-expanded-keys="expandArr"
              ref="tree"
              @node-click="handleNodeClick"
            >
              <span :class="['custom-tree-node', data.auth===0?'disable': '']" slot-scope="{ node, data }">
                <span>{{ node.label }}</span>
                <span class="tree_right"></span>
              </span>
            </el-tree>
          </section>
        </div>
      </div>
    </el-main>
  </el-container>
</template>

<script>
  import _ from 'lodash';
  import { getOrgTree, organizationSelect } from '@/api/system/organize';
  import Navbar from '@/layout/components/Navbar'
  export default {
    name: 'Organize',
    data() {
      return {
        orgName: '',
        organizeData: undefined,
        defaultProps: {
          children: "children",
          label: "label"
        },
        expandArr: []
      }
    },
  components: {
    Navbar,
  },
    created() {
      this.getOrgTree()
    },
    methods: {
      getOrgTree() {
        getOrgTree().then(res => {
          // this.expandArr = _.map(res.data[0].children, 'id'); //这里是再多显示一级
          this.expandArr = [ res.data[0].id ];
          this.organizeData = res.data
        })
      },
      // 筛选节点
      filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
      },
      // 节点单击事件
      handleNodeClick(data) {
        if (data.auth === 0) {
          return false
        }
        const {id, type} = data
        organizationSelect({organizationId: id, type}).then(res => {
          this.$store.dispatch('SetCurrentRole', data)
          this.$router.replace({ path: "/" });
        })
        // this.queryParams.enterpriseId = data.id;
        // this.getList();
      },
    },
    beforeRouteLeave (to, from, next) {
      if( !this.$store.state.user.curRole ) {
        this.$store.dispatch('LogOut').then(() => {
          location.reload()
        })
      } else {
        next()
      }
    },
    watch: {
      // 根据名称筛选部门树
      orgName(val) {
        this.$refs.tree.filter(val);
      }
    },
  }
</script>

<style scoped lang="scss">
  .el-header {
    // background-color: rgba(9, 161, 123, 1);
    // color: #ffffff;
    // line-height: 60px;
    // font-size: 20px;
    padding: 0;
    .log{
      color: #ffffff;
      font-size: 20px;
      width: 200px;
      height: 50px;
      line-height: 50px;
      position: absolute;
      z-index: 999;
      margin-left: 17px;
    }
    .jl_logo{
      width: 51px;
      height: 35px;
      display: inline-block;
      background: url(~@/assets/logo/jl_logo.png) no-repeat center;
      background-size: contain;
      position: relative;
      top: 8px;
    }
  }

  .el-main {
    .org-box{
      margin: 100px auto 0;
      width: 600px;
      border: 1px solid rgba(242, 242, 242, 1);
      font-size: 14px;
      color: #666666;
      .org-head{
        line-height: 40px;
        text-indent: 10px;
        border-bottom: 1px solid rgba(242, 242, 242, 1);
      }
      .org-cont{
        padding: 20px;
        .tree-cont{
          margin-top: 10px;
        }
        .custom-tree-node {
          flex: 1;
          display: flex;
          align-items: center;
          justify-content: space-between;
          font-size: 14px;
          padding-right: 8px;
          &.disable{
            color: #c0c4cc;
          }
        }
      }
    }
  }

</style>
