<template>
  <el-dialog :title="title" :visible.sync="flag" width="690px" :close-on-click-modal="false">
    <div class="list-wrap">
      <div class="list-l list-aside">
        <div class="list-item" v-for="(item, i) in allData" :key="item.projectName" v-if="i<allData.length/2">
          <div style="width: 8%">{{i+1}}</div>
          <div style="width: 39%">{{item.projectName}}</div>
          <div>{{item.personnelProjectProportion}}人</div>
          <div style="margin-right: 0">{{item.proportion}}%</div>
        </div>
      </div>
      <div class="list-r list-aside">
        <div class="list-item" v-for="(item, i) in allData" :key="item.projectName" v-if="i>=allData.length/2">
          <div style="width: 8%">{{i+1}}</div>
          <div style="width: 39%">{{item.projectName}}</div>
          <div>{{item.personnelProjectProportion}}人</div>
          <div style="margin-right: 0">{{item.proportion}}%</div>
        </div>
      </div>
    </div>
  </el-dialog>
</template>

<script>
  export default {
    name: "SeeAllDailog",
    props: {
      dialogFlag: {
        required: true,
        type: Boolean,
        default: false,
      },
      title: {
        type: String,
        datalut: '各项目在场人员占比'
      },
      allData: {
        type: Array,
        default: () => [],
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
    filters: {
      formatPercent(val) {
        return Math.round(val * 100)
      }
    }
  }
</script>

<style scoped lang="scss">
  /deep/.el-dialog__body{
    height: 500px;
  }
  /deep/.el-dialog:not(.is-fullscreen){
    margin-top: 15vh !important;
  }
  .list-wrap{
    display: flex;
    .list-aside{
      width: 50%;
      box-sizing: border-box;
      &.list-l{
        border-right: 1px solid #e9f2ef;
      }
      .list-item{
        text-align: left;
        padding: 0 20px 20px;
        div{
          width: 18%;
          display: inline-block;
          margin-right: 10px;
          overflow: hidden;
          white-space: nowrap;
          text-overflow: ellipsis;
        }
      }
    }
  }
</style>
