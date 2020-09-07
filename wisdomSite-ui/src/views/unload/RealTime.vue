<template>
  <div class="realtime-wrap">
    <div class="query-box">
      <el-select
        v-if="role.type === 0"
        placeholder="项目名称"
        v-model="queryParams.projectId"
        style="width: 180px; margin-right: 20px"
        @change="getChange()"
        filterable
        clearable
      >
        <el-option
          v-for="item in projectList"
          :key="item.id"
          :label="item.projectName"
          :value="item.id"
        />
      </el-select>
    </div>
    <!-- getProject -->
    <div class="top-monitor">
      <div class="top-monitor-item">
        <img class="top-monitor-icon" src="~@/assets/image/device.png">
        <p class="top-monitor-text">
          <b>{{dataNum.equipmentNum}}<span class="top-monitor-unit">台</span></b>
          <span>在场设备数量</span>
        </p>
      </div>
      <div class="top-monitor-item">
        <img class="top-monitor-icon" src="~@/assets/image/s_pie.png">
        <p class="top-monitor-text">
          <b>{{dataNum.equipmentIdleRate}}<span class="top-monitor-unit">%</span></b>
          <span>当前设备空闲率</span>
        </p>
      </div>
      <div class="top-monitor-item">
        <img class="top-monitor-icon" src="~@/assets/image/s_alarm.png">
        <p class="top-monitor-text">
          <b>{{dataNum.callPoliceEquipmentNum}}<span class="top-monitor-unit">台</span></b>
          <span>当前设备报警数</span>
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
      <p class="split-tit">实时监控
        <span class="tit-info">
            <span class="r-tit">
              <i class="iconfont icon-zhongdiandanwei"></i>当前载重
            </span>
            <span class="r-tit">
              <svg-icon class="icon_ele" class-name="iconfont" icon-class="electric" />当前电压
            </span>
          </span>
      </p>
      <div class="btm-cont">
        <div class="device-info-item" :class="{'deIfem': item.enableDisable == '2'}"
        v-for="(item,index) in spreadList" :key="index" @click="detailEquipment(item)">
          <img class="driver_photo" src="~@/assets/image/seize.png" alt="">
          <p class="driver_building">{{item.equipmentName}}</p>
          <div class="dev-info-left-cont">
            <p class="dev-info-left-cont-head">
                <span class="status-item">
                  <i class="iconfont icon-zhongdiandanwei" style="color: #08a17b;"></i>
                  {{item.weight}}kg</span>
              <span class="status-item">
                  <svg-icon class="icon_ele" class-name="iconfont" icon-class="electric" />
                  {{item.voltage}}V</span>
            </p>
            <p class="status-box">
              <span :class="item.enableDisable == '2' ? 'status-item' : 'status-item alarm-yellow'"><i></i>预警</span>
              <span :class="item.enableDisable == '2' ? 'status-item' : 'status-item alarm-red'"><i></i>报警</span>
              <!-- <span :class="item.warningStatus==='0'?'status-item':'status-item alarm-yellow'"><i></i>预警</span> -->
              <!-- <span :class="item.alarmStatus==='0'?'status-item':'status-item alarm-red'"><i></i>报警</span> -->
            </p>
          </div>
        </div>
      </div>
    </div>
    <component
      :is="model"
      :detailData='detailData'
      :is-show="popShow"
      @on-close="closePop"
    />
  </div>
</template>

<script>
  import {
    getNum,getVdata,getImage,detailLeft
  } from "@/api/unload";
  import {
    listProject,
  } from "@/api/people/attendance";
  import detailReal from "./modules/detail-real";
  export default {
    name: "RealTime",
    components: {
      detailReal
    },
    props: {
      role: {
        type: Object,
        required: true,
      },
    },
    data() {
      return {
        queryParams: {
          projectId: null
        },
        spreadList: [],
        dataNum:{},
        dataImage: null,
        popShow: false,
        detailData: {},
        state: null,
        model: null,
        projectList: [],
      }
    },
    async created() {
      this.getProjectList()
    },
    watch: {
      'queryParams.projectId': {
        handler(val){
          if (val) {
            this.getChange()
          }
        },
        immediate:true
      }
    },

    methods: {
      getChange() {
        this.getNum()
        this.getVdata()
        this.getImage()
      },
      /** 获取项目下拉查询 */
      async getProjectList() {
        listProject().then(response => {
          this.projectList = response.rows;
          //TODO
          this.queryParams.projectId = response.rows[0].id
        });
      },
      showPop(flag) {
        this.popShow = flag
      },
      closePop(resful) {
        this.showPop(false);
        if (resful == 'setStaff') {
          // this.getData(this.paramInit);
        }
        this.model = null;
      },
      async getNum() {
        const re = await getNum(this.queryParams)
        this.dataNum = re.data
      },
      async getVdata() {
        const re = await getVdata(this.queryParams)
        this.spreadList = re.rows
      },
      async getImage() {
        const re = await getImage(this.queryParams)
        this.dataImage = re.data.fieldlayoutAddress
      },
      async detailEquipment(item) {
        const re = await detailLeft({monitoringEquipmentNo:item.monitoringEquipmentNo})
        this.detailData = {...re.data,monitoringEquipmentNo:item.monitoringEquipmentNo}
        this.model = 'detailReal'
        this.showPop(true)
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
  .query-box{
    position: absolute;
    top: -40px;
    right: 0;
  }
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
        // width: 62px;
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
      .device-info-item{
        position: relative;
        display: flex;
        align-items: center;
        width: 290px;
        height: 100px;
        margin-bottom: 20px;
        margin-right: 20px;
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
            // margin-bottom: 20px;
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
            margin: 0;
          }
          .status-item{
            display: flex;
            align-items: center;
            // margin-right: 20px;
            width: 67px;
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
        .driver_photo{
          width: 57px;
          // min-height: 69px;
          border: 1px dashed #ecf2f1;
        }
        .driver_building{
          padding: 0 20px 0 10px;
        }
      }
      .deIfem{
        // background: #eaeaea;
        background: #eeeeee;
      }
    }
  }
  .icon_ele{
    margin-right: 5px;
  }
}
</style>
