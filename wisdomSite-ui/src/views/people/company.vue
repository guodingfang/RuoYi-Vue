<template>
  <div class="wrap">
    <el-tabs v-model="activeName" class="warp_tab">
      <el-tab-pane label="花名册" name="first">
        <roster v-if="activeName === 'first'" />
      </el-tab-pane>
      <el-tab-pane label="退场" name="dic" >
        <!-- 新增班组和修改班组未做全，需要补充点 -->
        <exit v-if="activeName === 'dic'" />
      </el-tab-pane>
      <el-tab-pane label="黑名单" name="second">
        <!-- 新增班组和修改班组未做全，需要补充点 -->
        <blacklist v-if="activeName === 'second'" />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import {
  listProject,
  getTableFn,
  getDetailFn,
  deleteFn,
  getTableList
} from "@/api/quality/cadManage";
import roster from "./modules/roster";
import blacklist from "./modules/blacklist";
import exit from "./modules/exit";
// import external from './modules/external'
import { has } from '@/mixins'

export default {
  name: "company",
  components: {
    roster,
    blacklist,
    exit,
    // external
  },
  mixins: [ has ],
  data() {
    return {
      activeName: "first"
      // activeName: "second"
    };
  },
  mounted() {
  },
  methods: {
  }
};
</script>

<style scoped lang="scss">
.wrap {
  padding: 0 10px;
  height: 100%;
}
::v-deep{
  .warp_tab{
    .el-tabs__content{
      overflow: inherit;
    }
  }

}
// .el-tabs{
//   height: 100%;
//   ::v-deep{
//     .el-tabs__content{
//       height: calc(100% - 52px);
//       .el-tab-pane{
//         height: 100%;
//       }
//     }
//   }

// }
</style>
