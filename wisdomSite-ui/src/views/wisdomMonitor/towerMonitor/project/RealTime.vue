<template>
  <div class="realtime-wrap">
    <div class="top-monitor">
      <div class="top-monitor-item">
        <img class="top-monitor-icon" src="~@/assets/image/s_tower.png">
        <p class="top-monitor-text">
          <b>{{topData.towerCraneOnSiteNum}}<span class="top-monitor-unit">台</span></b>
          <span>在场塔吊数量</span>
        </p>
      </div>
      <div class="top-monitor-item">
        <img class="top-monitor-icon" src="~@/assets/image/s_pie.png">
        <p class="top-monitor-text">
          <b>{{topData.monitoringOnlineRate}}<span class="top-monitor-unit">%</span></b>
          <span>监控在线率</span>
        </p>
      </div>
      <div class="top-monitor-item">
        <img class="top-monitor-icon" src="~@/assets/image/s_alarm.png">
        <p class="top-monitor-text">
          <b>{{topData.violationTodayNum}}<span class="top-monitor-unit">次</span></b>
          <span>今日违章</span>
        </p>
      </div>
      <div class="top-monitor-item">
        <img class="top-monitor-icon" src="~@/assets/image/s_redpie.png">
        <p class="top-monitor-text">
          <b>{{topData.violationRate}}<span class="top-monitor-unit">%</span></b>
          <span>违章率</span>
        </p>
      </div>
    </div>
    <div class="mid-monitor">
      <p class="split-tit">设备场布图</p>
      <div class="mid-cont">
        <div class="item" v-for="(e, i) in elements" :style="{top: e.y+'%', left: e.x+'%', width: e.r*zoom+'px', height: e.r*zoom+'px', lineHeight: e.r*zoom+'px'}">
          {{e.name}}
        </div>
        <el-image class="layout-pic" :src="imgUrl"></el-image>
      </div>
    </div>
    <div class="btm-monitor">
      <p class="split-tit">群塔防碰撞
        <span class="tit-info">
            <span class="r-tit">
              <i class="iconfont icon-zhongdiandanwei"></i>今日吊重
            </span>
            <span class="r-tit_red">
              <i class="iconfont icon-tanhao"></i>今日违章
            </span>
          </span>
        <span></span>
      </p>
      <div class="btm-cont">
        <div class="device-info-item" v-for="item in btmList" @click="openDetail(item)">
          <div class="dev-info-left-cont">
            <p class="dev-info-left-cont-head">
                <span class="head-icon-box">
                  <i class="iconfont icon-tadiaotubiao icon-tower"></i>
                  <span class="icon-device_name" :title="item.towerCraneName">{{item.towerCraneName}}</span>
                </span>
              <span class="total-text-box">
                  <span class="green_font">
                    <i class="iconfont icon-zhongdiandanwei"></i>{{item.hoistingToday}}
                  </span>
                  <span class="red_font">
                    <i class="iconfont icon-tanhao"></i>{{item.totalViolationToday}}
                  </span>
                </span>
            </p>
            <p class="status-box">
              <span :class="item.warningStatus==='0'?'status-item':'status-item alarm-yellow'"><i></i>预警</span>
              <span :class="item.alarmStatus==='0'?'status-item':'status-item alarm-red'"><i></i>报警</span>
              <span class="status-item">&emsp;&emsp;</span>
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
    towerEquipmentList,
    getLayOutMap,
    projectRealTimeMonitoring,
    equipmentRealtimeMonitoring,
  } from "@/api/smartMonitor/tower";

  export default {
    name: "RealTime",
    components: {

    },
    data() {
      return {
        topData: {},
        btmList: [],
        elements: [],
        mapWidth: '',
        picWidth: '',
        imgUrl: '',
        queryParams: {
          projectId: this.$store.state.user.curRole.id,
        },
        detailDialogFlag: false,
      }
    },
    created() {
      this.getTopData();
      this.getBtmData();
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
      //获取场部图
      async getLayOutMap() {
        const { data:{width, height, fieldlayoutAddress} } = await getLayOutMap(this.queryParams.projectId, 2);
        const { rows: posList } = await towerEquipmentList({
          pageNum: 1,
          pageSize: 99,
          presence: 1,
        });
        console.log(posList)
        this.imgUrl = process.env.VUE_APP_BASE_PIC + fieldlayoutAddress;
        this.picWidth = width;
        let elements = [];
        let zoom = this.mapWidth / Number(width);
        posList.map(e => {
          if(!!e.radius){
            elements.push({
              id: e.id, x: Number(e.coordinateX)*100/width, y: Number(e.coordinateY)*100/height, r: Number(e.radius), name: e.equipmentName
            })
          }
        })
        this.elements = elements;
        // this.picWidth = width;
        // this.picHeight = height;
        // this.imgUrl = this.resourcesUrl + data.fieldlayoutAddress
      },
      getTopData() {
        console.log(this.queryParams)
        projectRealTimeMonitoring(this.queryParams).then(res => {
          this.topData = res.data
        })
      },
      getBtmData() {
        equipmentRealtimeMonitoring(this.queryParams).then(res => {
          this.btmList = res.data
        })
      },
      openDetail(item) {
       const { towerCraneId, monitoringEquipmentNo} = item;
        /** 进入详情页 */
        this.$router.push({name: `towerDetail`, query: { towerCraneId, monitoringEquipmentNo }})
      }
    },
    computed: {
      zoom() {
        return this.mapWidth/this.picWidth
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
      width: 25%;
      height: 170px;
      color: #696969;
      box-sizing: border-box;
      .top-monitor-icon{
        font-size: 62px;
        margin-right: 45px;
        width: 62px;
        height: 62px;
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
    }
  }
  .mid-monitor {
    width: 100%;
    .mid-cont{
      position: relative;
      .item{
        position: absolute;
        /*height: 100px;*/
        background: rgba(0,0,0,0.4);
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
      .device-info-item{
        position: relative;
        display: flex;
        width: 290px;
        height: 100px;
        margin-right: 20px;
        margin-bottom: 20px;
        padding: 20px 10px;
        border: 1px solid #ecf2f1;
        cursor: pointer;
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
                background: #08a17b;
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
            .status-item{
              display: flex;
              align-items: center;
              margin-right: 20px;
              color: #939997;
              i{
                width: 14px;
                height: 14px;
                border-radius: 50%;
                margin-right: 5px;
                background: #eaeaea
              }
              &.alarm-yellow{
                i{
                  background: #ff813e
                }
              }
              &.alarm-red{
                i{
                  background: #f05454
                }
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
