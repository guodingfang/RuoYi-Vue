<template>
  <div class="statistic-wrap">
    <div class="add-device">
      <el-date-picker
        v-if="activeName==='third'"
        class="sel-date"
        v-model="queryDate"
        value-format="yyyy-MM-dd"
        :picker-options="pickerOptions"
        :clearable="false"
        type="date">
      </el-date-picker>
      <button type="button" @click="openDialog" class="more-btn jl-btn"><span>更多详情</span></button>
    </div>
    <el-tabs v-model="activeName">
      <el-tab-pane label="近7日" name="first">
        <recent-week-chart ref="first" v-if="activeName === 'first'"/>
      </el-tab-pane>
      <el-tab-pane label="近30日" name="second">
        <recent-month-chart ref="second"  v-if="activeName === 'second'"/>
      </el-tab-pane>
      <el-tab-pane label="自定义" name="third">
        <customize ref="third"  :queryDate="queryDate"  v-if="activeName === 'third'"/>
      </el-tab-pane>
    </el-tabs>
    <more-dialog
      :dialogFlag.sync="dialogFlag"
      :role="type"
      :projectId="projectId"
      :companyId="companyId"
      :queryDate="queryDate"
    />
  </div>
</template>

<script>
  import moment from 'moment';
  import Customize from "./components/Customize";
  import RecentWeekChart from "./components/RecentWeekChart";
  import RecentMonthChart from "./components/RecentMonthChart";
  import MoreDialog from "./components/MoreDialog";
  export default {
    name: "Statistic",
    components: {
      RecentWeekChart,
      RecentMonthChart,
      Customize,
      MoreDialog
    },
    props: {
      defaultId: {
        type: String,
        default: '',
      }
    },
    data() {
      return {
        queryDate: moment().format("yyyy-MM-DD"),
        projectId: this.defaultId,
        companyId: '',
        pickerOptions: {
          disabledDate(time) {
            return time.getTime() > Date.now();
          },
        },
        activeName: 'first',
        dialogFlag: false,
        type: 0,
      }
    },

    methods: {
      openDialog() {
        const { type, projectId, companyId } = this.$refs[this.activeName];
        console.log(type)
        this.type = type;
        this.projectId = projectId;
        this.companyId = companyId;
        this.dialogFlag = true;
      }
    }
  }
</script>

<style scoped lang="scss">
  .statistic-wrap{
    .add-device{
      position: absolute;
      top: 10px;
      right: 20px;
      z-index: 99;
      .sel-date{
        width: 150px;
        margin-right: 20px;
      }
      .more-btn{
        color: #00b589;
        border-color: #00b589;
        border-radius: 0;
        float: right;
      }
    }
    /deep/.el-tabs__item{
      font-size: 14px !important;
      font-weight: normal;
      color: #666;
    }
    /deep/.el-tabs__nav-wrap::after{
      height: .5px;
    }
  }
</style>
