<template>
  <div class="realtime-wrap">
    <div class="top-monitor">
      <div class="top-monitor-item">
        <img class="top-monitor-icon s-elevator" src="~@/assets/image/s_elevator.png">
        <p class="top-monitor-text">
          <b>{{topData.deviceNumber}}<span class="top-monitor-unit">台</span></b>
          <span>在场升降机数量</span>
        </p>
        <h5 class="elevator-num_h5_text">
          <p class="elevator-elevator_sub">单笼 <b>{{topData.deviceNumber - topData.doubleDeviceNumber}}</b>台</p>
          <p class="elevator-elevator_sub">双笼 <b>{{topData.doubleDeviceNumber}}</b>台</p>
        </h5>
      </div>
      <div class="top-monitor-item">
        <img class="top-monitor-icon" src="~@/assets/image/s_pie.png">
        <p class="top-monitor-text">
          <b>{{topData.totalMonitorDeviceNumber}}<span class="top-monitor-unit">%</span></b>
          <span>监控在线率</span>
        </p>
        <h5 class="elevator-num_h5_text">
          <p class="elevator-elevator_sub">在线 <b>{{topData.onlineMonitorDeviceNumber}}</b>台</p>
          <p class="elevator-elevator_sub">离线 <b  style="color: #4d4d4d">{{topData.totalMonitorDeviceNumber - topData.onlineMonitorDeviceNumber}}</b>台</p>
        </h5>
      </div>
      <div class="top-monitor-item">
        <img class="top-monitor-icon" src="~@/assets/image/s_alarm.png">
        <p class="top-monitor-text">
          <b style="color: rgb(240, 84, 84)">{{topData.preAlarmingNumber + topData.alarmingNumber}}<span class="top-monitor-unit">台</span></b>
          <span>今日告警</span>
        </p>
        <h5 class="elevator-num_h5_text">
          <p class="elevator-elevator_sub">提醒 <b  style="color: rgb(255, 194, 103)">{{topData.preAlarmingNumber}}</b>次</p>
          <p class="elevator-elevator_sub">报警 <b  style="color: rgb(255, 129, 62)">{{topData.alarmingNumber}}</b>次</p>
        </h5>
      </div>
    </div>
    <div class="mid-monitor">
      <p class="split-tit">设备场布图</p>
      <div class="mid-cont">
        <div class="item" v-for="(e, i) in elements" :style="{top: e.y+'%', left: e.x+'%', width: round, height: round, lineHeight: round}">
          {{e.name}}
        </div>
        <el-image class="layout-pic" :src="imgUrl"></el-image>
      </div>
    </div>
    <div class="btm-monitor">
      <p class="split-tit">实时监控
        <span class="tit-info">
            <span class="r-tit">
              <i class="iconfont icon-zhongdiandanwei"></i>今日吊重
            </span>
            <span class="r-tit_red">
              <i class="iconfont icon-tanhao"></i>今日告警
            </span>
          </span>
          <span></span>
      </p>
      <div class="btm-cont">
        <p class="s_tit">双笼升降机</p>
        <div v-for="(item, i) in doubleEle" class="device-info-double-item" @click="openDetail(item)">
          <div class="device-info-item" style="margin-bottom: 0;border-bottom: 0">
<!--            <span class="online-status"> {{item.statisticsList[0].onlineStatus === '2' ? '离线' : ''}}</span>-->
            <span class="online-status"> {{devStatus(item.statisticsList[0].onlineStatus, item.statisticsList[0].inOutStatus)}}</span>
            <div class="dev-info-left-cont">
              <p class="dev-info-left-cont-head">
              <span class="head-icon-box">
                <i :class="`iconfont icon-shengjiangji icon-tower ${item.statisticsList[0].onlineStatus === '1' ? 'online' : ''}`"></i>
                <span class="icon-device_name" :title="item.deviceName">{{item.deviceName}}</span>
              </span>
                <span class="total-text-box">
                <span class="green_font">
                  <i class="iconfont icon-zhongdiandanwei"></i>{{item.statisticsList[0].totalWeight}}
                </span>
                <span class="red_font">
                  <i class="iconfont icon-tanhao"></i>{{item.statisticsList[0].alarmingNumber}}
                </span>
              </span>
              </p>
              <span class="jl-green">左笼</span>
              <p class="status-box">
                <span class="`status-item ${item.statisticsList[0].realTimeWarningStatus==='1'?'warn':''}`"><i></i>预警</span>
                <span class="`status-item ${item.statisticsList[0].realTimeWarningStatus==='1'?'warn':''}`"><i></i>报警</span>
<!--                <span class="status-item">&emsp;&emsp;</span>-->
              </p>
            </div>
            <img class="driver_photo" src="~@/assets/image/driver_pic.png" alt="">
          </div>
          <div class="device-info-item" style="border-top: 0">
            <span class="online-status"> {{item.statisticsList[1].onlineStatus === '2' ? '离线' : ''}}</span>
            <div class="dev-info-left-cont">
              <p class="dev-info-left-cont-head">
              <span class="head-icon-box">
                <i :class="`iconfont icon-shengjiangji icon-tower ${item.statisticsList[1].onlineStatus === '1' ? 'online' : ''}`"></i>
                <span class="icon-device_name" :title="item.deviceName">{{item.deviceName}}</span>
              </span>
                <span class="total-text-box">
                <span class="green_font">
                  <i class="iconfont icon-zhongdiandanwei"></i>{{item.statisticsList[1].totalWeight}}
                </span>
                <span class="red_font">
                  <i class="iconfont icon-tanhao"></i>{{item.statisticsList[1].alarmingNumber}}
                </span>
              </span>
              </p>
              <span class="jl-green">右笼</span>
              <p class="status-box">
                <span class="`status-item ${item.statisticsList[1].realTimeWarningStatus==='1'?'warn':''}`"><i></i>预警</span>
                <span class="`status-item ${item.statisticsList[1].realTimeWarningStatus==='1'?'warn':''}`"><i></i>报警</span>
              </p>
            </div>
            <img class="driver_photo" src="~@/assets/image/driver_pic.png" alt="">
          </div>
        </div>
        <p class="s_tit">单笼升降机</p>
        <div v-for="(item, i) in singleEle" class="device-info-item" @click="openDetail(item)">
<!--          <span class="online-status"> {{item.statisticsList[0].onlineStatus === '2' ? '离线' : ''}}</span>-->
          <span class="online-status"> {{devStatus(item.statisticsList[0].onlineStatus, item.statisticsList[0].inOutStatus)}}</span>
          <div class="dev-info-left-cont">
            <p class="dev-info-left-cont-head">
              <span class="head-icon-box">
                <i :class="`iconfont icon-shengjiangji icon-tower ${item.statisticsList[0].onlineStatus === '1' ? 'online' : ''}`"></i>
                <span class="icon-device_name" :title="item.deviceName">{{item.deviceName}}</span>
              </span>
              <span class="total-text-box">
                <span class="green_font">
                  <i class="iconfont icon-zhongdiandanwei"></i>{{item.statisticsList[0].totalWeight}}
                </span>
                <span class="red_font">
                  <i class="iconfont icon-tanhao"></i>{{item.statisticsList[0].alarmingNumber}}
                </span>
              </span>
            </p>
            <span class="jl-green">&emsp;</span>
            <p class="status-box">
              <span :class="`status-item ${item.statisticsList[0].realTimeWarningStatus==='1'?'warn':''}`"><i></i>预警</span>
              <span :class="`status-item ${item.statisticsList[0].realTimeWarningStatus==='2'?'err':''}`"><i></i>报警</span>
            </p>
          </div>
          <img class="driver_photo" src="~@/assets/image/driver_pic.png" alt="">
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {
    getRtAll,
    getRtList,
    getLayOutMap,
    getElePosList,
  } from "@/api/smartMonitor/elevator";
  export default {
    name: "RealTime",
    components: {},
    data() {
      return {
        imgUrl: '',
        picWidth: '',
        picHeight: '',
        queryParams: {
          projectId: this.$store.state.user.curRole.id,
        },
        resourcesUrl: 'http://39.103.140.108:8080',
        singleEle: [],
        doubleEle: [],
        elements: [],
        topData: {},
        mapWidth: '',

      }
    },
    created() {
      this.getTopData();
      this.getElevatorList();
    },
    mounted() {
      this.$nextTick(() => {
        this.mapWidth = document.querySelector('.realtime-wrap').clientWidth;
        window.onresize = () => {
          this.mapWidth = document.querySelector('.realtime-wrap').clientWidth;
        }
        this.getLayOutMap();
      })
    },
    methods: {
      devStatus(onlineStatus, inOutStatus) {
        //item.statisticsList[0].onlineStatus === '2' ? '离线' : ''
        if (inOutStatus === '0') {
          return '离场'
        } else if (onlineStatus === '2'){
          return '离线'
        }
        return ''
      },
      //获取场部图
      async getLayOutMap() {
        const { data:{width, height, fieldlayoutAddress} } = await getLayOutMap(this.queryParams.projectId, 2);
        const { data: posList } = await getElePosList(this.queryParams.projectId);
        this.imgUrl = this.resourcesUrl + fieldlayoutAddress;
        this.picWidth = width;
        let elements = [];
        let r = 50*this.mapWidth / Number(width);
        posList.map(e => {
          elements.push({
            x: Number(e.xOffset)*100/width, y: Number(e.yOffset)*100/height, r, name: e.deviceName
          })
        })
        this.elements = elements;
        // this.picWidth = width;
        // this.picHeight = height;
        // this.imgUrl = this.resourcesUrl + data.fieldlayoutAddress
      },
      //获取场部图的设备
      // async getElePosList() {
      //   const { data } = await getElePosList(this.projectId);
      //   let elements = [];
      //   data.map(e => {
      //     elements.push({
      //       id: e.id, x: Number(e.xOffset), y: Number(e.yOffset), name: e.deviceName
      //     })
      //   })
      //   this.elements = elements;
      // },
      async getTopData() {
        const { data } = await getRtAll(this.queryParams);
        this.topData = data;
      },
      async getElevatorList() {
        const { data } = await getRtList(this.queryParams);
        data.map(e => {
          e.deviceType === '1' ? this.singleEle.push(e) : this.doubleEle.push(e)
        })
        // this.topData = data;
      },
      openDetail(item) {
          const { id, statisticsList: devList } = item;
          const sn = devList.map(e => e.serialNumber).join(',')
        /** 进入详情页 */
        this.$router.push({name: `elevatorDetail`, query: { id, sn }})
      }
    },
    computed: {
      round() {
        return this.mapWidth*50/this.picWidth + 'px'
      }
    }
  }
</script>

<style scoped lang="scss">
.realtime-wrap{
  /*display: flex;*/
  width: 100%;
  /*height: calc(100vh - 130px);*/
  padding: 0 10px;
  font-size: 14px;
  line-height: 1.15;
  .top-monitor{
    display: flex;
    align-items: center;
    justify-content: space-around;
    .top-monitor-item{
      display: flex;
      align-items: center;
      justify-content: center;
      width: 33.3%;
      height: 170px;
      color: #696969;
      box-sizing: border-box;
      .top-monitor-icon{
        font-size: 62px;
        margin-right: 45px;
        width: 62px;
        height: 62px;
        &.s-elevator{
          width: auto;
          height: 62px;
        }
      }
      .top-monitor-text{
        display: flex;
        flex-direction: column;
        text-align: center;
        b{
          color: #4c4e4d;
          font-size: 42px;
          .top-monitor-unit{
            color: #696969;
            font-size: 12px;
          }
        }
      }
      .elevator-num_h5_text{
        display: flex;
        flex-direction: column;
        text-align: center;
        justify-content: space-around;
        width: 125px;
        height: 105px;
        padding: 10px 0 0 20px;
        font-weight: normal;
        .elevator-elevator_sub {
          display: flex;
          justify-content: space-around;
          align-items: baseline;
          font-size: 14px;
          b{
            font-size: 24px;
            color: #00b589;
          }
        }
      }
    }
  }
  .mid-monitor {
    width: 100%;
    .mid-cont{
      position: relative;
      .item{
        position: absolute;
        /*height: 100px;*/
        background: #09a17b;
        /*line-height: 1;*/
        text-align: center;
        /*display: flex;*/
        align-items: center;
        color: #ffffff;
        z-index: 2;
        border-radius: 50%;
      }
      .layout-pic{
        width: 100%;
      }
      /*height: apx;*/
      /*background: #1ab394;*/
    }
  }
  .btm-monitor{
    width: 100%;
    height: auto;
    .split-tit{
      .tit-info{
        display: flex;
        i{
          margin-right: 5px;
        }
        .r-tit{
          display: flex;
          align-items: center;
          margin-right: 10px;
          color: #08a17b;
          font-size: 12px;
        }
        .r-tit_red{
          color: #f05454;
          font-size: 12px;
        }
      }
    }
    .btm-cont{
      width: 100%;
      /*height: calc(100vh - 300px);*/
      overflow-y: auto;
      margin-right: 10px;
      display: flex;
      flex-wrap: wrap;
      /*justify-content: space-between;*/
      .s_tit{
        width: 100%;
        color: #47494a;
        font-size: 12px;
      }
      .device-info-item{
        position: relative;
        display: flex;
        width: 290px;
        height: 100px;
        margin-bottom: 20px;
        margin-right: 20px;
        padding: 20px 10px;
        border: 1px solid #ecf2f1;
        cursor: pointer;
        .online-status{
          position: absolute;
          top: 5px;
          right: 5px;
        }
        &.double{
          height: 200px;
          flex-wrap: wrap;
        }
        .dev-info-left-cont{
          display: flex;
          flex-wrap: wrap;
          justify-content: space-between;
          .dev-info-left-cont-head{
            display: flex;
            align-items: center;
            font-size: 12px;
            margin-bottom: 20px;
            margin-top: 0;
            .head-icon-box{
              display: flex;
              align-items: center;
              justify-content: space-between;
              .icon-tower{
                width: 24px;
                height: 24px;
                margin-right: 10px;
                font-size: 20px;
                color: #fff;
                text-align: center;
                line-height: 25px;
                background: #989898;
                &.online{
                  background: #08a17b;
                }
              }
              .icon-device_name{
                width: 50px;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
              }
            }
          }
          .total-text-box{
            display: flex;
            align-items: center;
            width: 110px;
            justify-content: space-between;
            font-size: 12px;
            .green_font{
              color: #08a17b;
            }
            .red_font{
              color: #f05454;
            }
          }
          .status-box{
            display: flex;
            align-items: center;
            font-size: 12px;
            margin-top: 0;
            /*width: 100%;*/
            .status-item{
              display: flex;
              align-items: center;
              margin-right: 20px;
              color: #939997;
              &.warn{
                i{
                  background-color: #ffc267;
                }
              }
              &.err {
                i {
                  background-color: #ff813e;
                }
              }
              i{
                width: 14px;
                height: 14px;
                border-radius: 50%;
                margin-right: 5px;
                background: #eaeaea
              }
            }
          }
        }
        .driver_photo{
          width: 68px;
          min-height: 69px;
          border: 1px dashed #ecf2f1;
        }
      }
    }
    .btm-right{
      width: calc(100% - 620px);
      .btm-right-con{
        width: 100%;
        height: calc(100vh - 340px);
        border: 1px solid #ecf2f1;
      }
    }
  }
}
</style>
