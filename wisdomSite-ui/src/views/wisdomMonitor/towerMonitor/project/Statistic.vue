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
        <recent-chart />
        <recent-week-chart v-if="activeName === 'first'" :projectId="projectId"/>
      </el-tab-pane>
      <el-tab-pane label="近30日" name="second">
        <recent-month-chart v-if="activeName === 'second'" :projectId="projectId"/>
      </el-tab-pane>
      <el-tab-pane label="自定义" name="third">
        <customize  v-if="activeName === 'third'" :projectId="projectId" :queryDate="queryDate"/>
      </el-tab-pane>
    </el-tabs>
    <more-dialog
      :dialogFlag.sync="dialogFlag"
      :role="type"
      :projectId="projectId"
      :companyId="companyId"
      :queryDate="queryDate"
      :days="days"
    />
  </div>
</template>

<script>
  import RecentWeekChart from "./components/RecentWeekChart";
  import RecentMonthChart from "./components/RecentMonthChart";
  import Customize from "./components/Customize";
  import MoreDialog from "../enterprise/components/MoreDialog";
  import moment from "moment";
  export default {
    name: "Statistic",
    components: {
      Customize,
      RecentWeekChart,
      RecentMonthChart,
      MoreDialog
    },
    data() {
      return {
        days: [],
        queryDate: moment().format("yyyy-MM-DD"),
        projectId: this.$store.state.user.curRole.id,
        companyId: '',
        pickerOptions: {
          disabledDate(time) {
            return time.getTime() > Date.now();
          },
        },
        activeName: 'first',
        dialogFlag: false,
        type: 1,
      }
    },
    methods: {
      openDialog() {
        this.dialogFlag = true;
        if (this.activeName==='first') {
          this.days = [moment().subtract(6, 'days').format('YYYY-MM-DD'), moment().format("yyyy-MM-DD")];
        } else if (this.activeName==='second') {
          this.days = [moment().subtract(29, 'days').format('YYYY-MM-DD'), moment().format("yyyy-MM-DD")];
        } else {
          this.days = [this.queryDate, this.queryDate];
        }
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

