<template>
  <el-dialog title="历史记录"  :visible.sync="flag" width="560px" @close="handleClose" @open="handleOpen" :show-close="false" :close-on-click-modal="false">
    <div class="more-detail-wrap">
      <i class="el-icon-close close-btn" @click="closeDialog"></i>
      <div class="query-box">
        <el-input
          style="width: 220px;"
          placeholder="请输入司机姓名"
          v-model="queryParams.driversName"
          size="mini"
        >
          <el-button slot="append" icon="el-icon-search" @click="getHisList"></el-button>
        </el-input>
        <el-date-picker
          placeholder="请选择日期"
          size="mini"
          style="width: 220px;"
          v-model="queryParams.searchTime"
          value-format="yyyy-MM-dd"
        >
        </el-date-picker>
        </div>
        <div class="history-list">
          <div class="history-item">
            <div class="elevator-driver_list" v-for="item in hisData">
              <img :src="item.photoAddress">
              <div class="elevator-list_right">
                <p>姓名：{{item.driversName}}</p>
                <p>身份证：{{item.idNumber}}</p>
                <p>特种作业证：{{item.specialOperationNumber}}</p>
                <p>确认日期：{{item.identificationTime}}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <Pagination
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        layout="prev, pager, next"
        @pagination="getHisList"
      />
  </el-dialog>
</template>

<script>
  import moment from 'moment';
  import {
    getDrivHisList,
  } from "@/api/smartMonitor/tower";
  import Pagination from "@/components/Pagination";
  export default {
    name: "HistoryDialog",
    props: {
      dialogFlag: {
        required: true,
        type: Boolean,
        default: false,
      },
      tId: {
        required: true,
        default: ''
      }
    },
    components: {
      Pagination
    },
    data() {
      return {
        activeName: 'first',
        queryParams: {
          towerCraneId: this.tId,
          pageNum: 1,
          pageSize: 4,
          driversName: '',
          searchTime: moment().format('yyyy-MM-DD'),
        },
        total: 0,
        hisData: [],
      }
    },
    created() {
    },
    methods: {
      async getHisList() {
        const { rows } = await getDrivHisList(this.queryParams);
        this.hisData = rows;
      },
      /* 关闭 */
      closeDialog() {
        this.$emit('update:dialogFlag', false);
      },
      handleOpen() {
        this.getHisList();
      },
      handleClose() {
        console.log('弹框关闭了哦')
      }
    },
    watch: {
      sn(val) {
        this.queryParams.serialNumber = val;
        console.log(val)
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
  }
</script>

<style scoped lang="scss">
  /deep/.el-dialog__body{
    padding-top: 0;
  }
  .query-box{
    margin: 15px 0;
    display: flex;
    justify-content: space-between;
  }
  .more-detail-wrap{
    .close-btn{
      float: right;
      cursor: pointer;
      position: absolute;
      top: 20px;
      right: 20px;
      font-size: 16px;
      z-index: 99;
    }
    .more-btn{
      color: #00b589;
      border-color: #00b589;
      border-radius: 0;
      float: right;
      height: 28px;
      line-height: 1;
    }
    .history-list{
      .elevator-driver_list{
        img{
          width: 116px;
          height: 116px;
          margin-right: 15px;
          border: 1px dashed #e1ece8;
        }
        p{
          margin: 5px;
        }
        display: flex;
        align-items: center;
        width: 100%;
        margin-bottom: 10px;
        padding: 10px;
        border: 1px solid #e1ece8;
        .elevator-list_right{
          flex: 1 1;
        }
      }
    }
  }
</style>
