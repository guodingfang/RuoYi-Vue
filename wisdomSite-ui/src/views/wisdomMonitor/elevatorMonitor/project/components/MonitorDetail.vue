<template>
  <el-dialog  title="监控详情" :visible.sync="flag" width="1400px" @close="closeDialog" :close-on-click-modal="false">
    <div class="monitor-detail-wrap">
      <div class="detail-left">
        <p class="split-tit">实时监控</p>
        <div class="video-box">
          <img src="@/assets/image/tower_1.png" alt="">
          <div>
            <p class="video-btn"><i class="el-icon-video-camera-solid"></i>驾驶舱监控</p>
          </div>
        </div>
        <div class="status-box">
          <div class="status-item">
            <p>幅度</p>
          </div>
          <div class="status-item">
            <p>幅度</p>
          </div>
          <div class="status-item">
            <p>幅度</p>
          </div>
          <div class="status-item">
            <p>幅度</p>
          </div>
          <div class="status-item">
            <p>幅度</p>
          </div>
          <div class="status-item">
            <p>幅度</p>
          </div>
          <div class="status-item">
            <p>幅度</p>
          </div>
        </div>
        <div class="mon-status-tit">
          监控状态
          <p>
            <span>正常</span>
            <span>预警</span>
            <span>违章</span>
          </p>
        </div>
        <div class="status-block">
          <span>限位</span>
          <span>限位</span>
          <span>限位</span>
          <span>限位</span>
          <span>限位</span>
          <span>限位</span>
          <span>限位</span>
        </div>
      </div>
      <div class="detail-right">
        <div class="r-top">
          <div class="r-top-l">
            <p class="split-tit">塔机信息 </p>
            <p class="r-top-l-text">备案号：苏备DA-T-II-1772</p>
            <p class="r-top-l-text">塔机型号：</p>
            <p class="r-top-l-text">产权单位：</p>
            <p class="r-top-l-text">安装单位：</p>
          </div>
          <div class="r-top-l">
            <p class="split-tit">司机信息 </p>
            <p class="r-top-l-text">姓名：张三</p>
            <p class="r-top-l-text">身份证：苏备DA-T-II-1772</p>
            <p class="r-top-l-text">特种作业证：苏备DA-T-II-1772</p>
            <p class="r-top-l-text">身份确认事件：2020-02-02 02:02:02</p>
          </div>
        </div>
        <div class="r-mid">
          <p class="split-tit">工作记录</p>
        </div>
        <div class="r-btm">
          <p class="split-tit">告警记录</p>
        </div>
      </div>
    </div>
  </el-dialog>
</template>

<script>
  import {
    addOrganize,
  } from '@/api/system/organize';
  export default {
    name: 'MonitorDetail',
    components: {

    },
    props: {
      dialogFlag: {
        required: true,
        type: Boolean,
        default: false,
      },
      organForms: {
        type: Object,
        default: () => {},
      },
    },
    data() {
      return {
        organForm: {
          equipmentType: '',
        },
      };
    },
    created() {

    },
    methods: {
      /* 关闭 */
      closeDialog() {
        this.organForm = {};
        this.$refs.organForm.resetFields();
      },
    },
    computed: {
      flag: {
        get() {
          return this.dialogFlag;
        },
        set(val) {
          this.$emit('update:dialogFlag', val);
          // this.$emit('update:organForms', {});
        },
      },
    },
    watch: {
      organForms(val) {
        this.organForm = Object.assign({}, val);
        // this.organForm.officeId = Number(this.organForm.officeId);
      },
    },
  };
</script>

<style lang='scss' scoped>
.monitor-detail-wrap{
  display: flex;
  justify-content: space-between;
  width: 100%;
  height: 780px;
  .detail-left{
    width: 515px;
    height: 770px;
    padding: 0 10px;
    border: 1px solid #e1ece8;
    .video-box{
      display: flex;
      justify-content: space-around;
      margin-bottom: 20px;
      .video-btn{
        width: 95px;
        padding: 10px;
        color: #565b5b;
        height: 75px;
        border: 1px solid #eee;
        margin-bottom: 10px;
        text-align: center;
        line-height: 1.6;
        i{
          display: block;
          color: #00b589;
          font-size: 24px;
          height: 38px;
          line-height: 38px;
        }

      }
    }
    .status-box{
      display: flex;
      flex-wrap: wrap;
      justify-content: space-between;
      .status-item{
        width: 32%;
        height: 105px;
        margin-bottom: 10px;
        border: 1px solid #e1ece8;
      }
    }
    .mon-status-tit{
      display: flex;
      align-items: center;
      justify-content: space-between;
      margin-top: 30px;
      p{
        display: flex;
        width: 160px;
        justify-content: space-around;
        line-height: 1.58;
        span{
          position: relative;
          width: 50px;
          padding-left: 10px;
          &:before{
            position: absolute;
            top: 7px;
            left: 0;
            width: 8px;
            height: 8px;
            background: #ffc267;
            content: " ";
          }
          &:nth-of-type(2):before{
            background: #ff813e;
          }
          &:nth-of-type(2):before{
            background: #f05454;
          }
        }
      }
    }
    .status-block{
      display: flex;
      align-items: center;
      justify-content: space-around;
      padding-top: 10px;
      span{
        width: 72px;
        height: 30px;
        line-height: 30px;
        color: #fff;
        border: 1px solid #f7f7f7;
        text-align: center;
        background: rgb(0, 181, 137);
      }
    }
  }
  .detail-right{
    width: 820px;
    .r-top{
      display: flex;
      justify-content: space-around;
      width: 100%;
      padding: 0 10px 10px;
      border: 1px solid #e1ece8;
      .r-top-l{
        width: 320px;
        .r-top-l-text{
          margin: 0;
          padding-left: 10px;
          line-height: 28px;
        }
      }
    }
    .r-mid{
      margin: 20px 0 0;
      padding: 0 10px 10px;
      border: 1px solid #e1ece8;
    }
    .r-btm{
      margin: 20px 0 0;
      padding: 0 10px 10px;
      border: 1px solid #e1ece8;
    }
  }
}
</style>
