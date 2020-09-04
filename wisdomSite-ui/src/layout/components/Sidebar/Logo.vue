<template>
  <div class="sidebar-logo-container" :class="{'collapse':collapse}">
<!--    <transition name="sidebarLogoFade">-->
      <router-link v-if="collapse" key="collapse" class="sidebar-logo-link" to="/">
        <el-tooltip :content="curRole.label" placement="bottom" effect="light">
          <h1 class="sidebar-title">{{ curRole.label }} </h1>
        </el-tooltip>
      </router-link>
<!--      <router-link v-else key="expand" class="sidebar-logo-link" to="/">
        <h1 class="sidebar-title">{{ title }} </h1>
      </router-link>-->
<!--      TODO 这里后面用公用组件，暂时先酱紫-->
      <el-popover
        placement="bottom"
        width="500"
        trigger="click">
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
        <h1 slot="reference" :title="curRole.label" class="sidebar-title">{{ curRole.label }} <i class="el-icon-arrow-down el-icon--right"></i></h1>
      </el-popover>
<!--    </transition>-->
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import { getOrgTree, organizationSelect } from '@/api/system/organize';


export default {
  name: 'SidebarLogo',
  inject:['reload'],
  props: {
    collapse: {
      type: Boolean,
      required: true
    }
  },
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
  created() {
    this.getOrgTree()
  },
  computed: {
    ...mapGetters(["curRole"]),
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
        this.$store.dispatch('SetCurrentRole', data);
        this.reload()
      })
      // this.queryParams.enterpriseId = data.id;
      // this.getList();
    },
  },
  watch: {
    // 根据名称筛选部门树
    orgName(val) {
      this.$refs.tree.filter(val);
    }
  },
}
</script>

<style lang="scss" scoped>
.sidebarLogoFade-enter-active {
  transition: opacity 1.5s;
}

.sidebarLogoFade-enter,
.sidebarLogoFade-leave-to {
  opacity: 0;
}
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

.sidebar-logo-container {
  position: relative;
  width: 100%;
  height: 50px;
  line-height: 50px;
  background: #09a17b;
  text-align: center;
  overflow: hidden;
  .sidebar-title {
    width: 100%;
    padding: 0 10px;
    box-sizing: border-box;
    display: inline-block;
    margin: 0;
    color: #fff;
    font-weight: 600;
    line-height: 50px;
    font-size: 14px;
    font-family: Avenir, Helvetica Neue, Arial, Helvetica, sans-serif;
    vertical-align: middle;
    cursor: pointer;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }
  & .sidebar-logo-link {
    height: 100%;
    width: 100%;

    & .sidebar-logo {
      width: 32px;
      height: 32px;
      vertical-align: middle;
      margin-right: 12px;
    }
  }

  &.collapse {
    .sidebar-logo {
      margin-right: 0px;
    }
  }
}
</style>
