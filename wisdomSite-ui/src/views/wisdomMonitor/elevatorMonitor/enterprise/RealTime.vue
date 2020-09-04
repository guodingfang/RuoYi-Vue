<template>
<div class="realtime-wrap">
  <section class="left-map">
    <project-select v-show="false" :formProjId.sync="projectId"  class="proj-sel"/>
    <div id="map" class="map-body"></div>
  </section>
  <section class="right-aside">
    <p class="show-more clearfix">
      <button type="button" class="more-btn jl-btn" v-show="false"><span>更多详情</span></button>
    </p>
    <p class="splitTit">当前区域：{{curArea}}</p>
    <article class="des-item">
      <p class="des-item-p">
        <b>{{rData.deviceNumber}}<span>台</span></b>
        <span>升降机设备总数</span>
      </p>
      <p class="des-item-p">
        <b>{{rData.projectNumber}}<span>个</span></b>
        <span>所属项目总数</span>
      </p>
    </article>
    <article class="des-item">
      <p class="des-item-p">
        <b style="color: #f05454">{{rData.doubleDeviceNumber}}<span>台</span></b>
        <span>双笼升降机</span>
      </p>
      <p class="des-item-p">
        <b style="color: #f05454">{{rData.deviceNumber-rData.doubleDeviceNumber}}<span>台</span></b>
        <span>单笼升降机</span>
      </p>
    </article>
    <article class="des-item">
      <p class="des-item-p">
        <b>{{rData.totalMonitorDeviceNumber}}<span>台</span></b>
        <span>监控设备总数</span>
      </p>
      <p class="des-item-p">
        <span class="online-status">在线<b>{{rData.onlineMonitorDeviceNumber}}</b>台</span>
        <span class="online-status">离线<b style="color: #4C4E4D;">{{rData.totalMonitorDeviceNumber-rData.onlineMonitorDeviceNumber}}</b>台</span>
      </p>
    </article>
    <article class="des-item">
      <p class="des-item-p">
        <b style="color: #f05454">{{rData.alarmingNumber}}<span>次</span></b>
        <span>今日报警次数</span>
      </p>
      <p class="des-item-p">
        <b style="color: #f05454">{{rData.alarmingProjectNumber}}<span>个</span></b>
        <span>报警项目总数</span>
      </p>
    </article>
    <p class="refresh_time">
      数据更新时间：{{updateTime}}
      <button type="button" class="jl-btn more-btn" @click="loadData">
        <span>刷 新</span>
      </button>
    </p>
  </section>
</div>
</template>

<script>
  import moment from 'moment';
  import {
    getAreaData,
    getAreaAllData,
  } from "@/api/smartMonitor/elevator";
  import ProjectSelect from "@/components/CommonSelect/ProjectSelect";
  /**
   * 关于地图的缩放请注意
   * 2.0系统里缩放分4个等级 1=全国，2=省，3=市，4=区
   * 百度地图的zoom和 系统的缩放对应关系如下
   *  (1)zoom <= 7 全国
   *  (2) 8 <= zoom <= 12 省
   *  (3) 13 <= zoom <= 14 市
   *  (4) 15 <= zoom 项目
   * */
  export default {
    name: "RealTime",
    components: {ProjectSelect},
    data() {
      return {
        updateTime: '',
        projectId: 0,
        queryParams: {
          latitude: 0,
          longitude: 0,
          zoom: 1,
        },
        curArea: '全国',
        rData: {
          deviceNumber: 0,
          projectNumber: 0,
          doubleDeviceNumber: 0,
          totalMonitorDeviceNumber: 0,
          onlineMonitorDeviceNumber: 0,
          alarmingNumber: 0,
          alarmingProjectNumber: 0,
        },
      }
    },

    created() {
      this.initMap();
    },
    methods: {
      initMap() {
        const _this = this;
        this.$nextTick(() => {
          this.map = new BMap.Map('map'); // 创建Map实例
          // this.map.addControl(new BMap.NavigationControl());
          // this.map.addControl(new BMap.MapTypeControl());
          this.map.enableScrollWheelZoom(true); // 开启鼠标滚轮缩放
          this.map.centerAndZoom(new BMap.Point(117.34145366186866, 31.908246281191683), 5);
          this.map.addEventListener('dragend', function(e) {
            _this.queryParams = {
              ..._this.queryParams,
              latitude: e.point.lat,
              longitude: e.point.lng,
            };
            _this.getLocation(e.point);
            _this.loadData();
          });
          this.map.addEventListener('zoomend', function(e) {
            console.log('zoomend',)
            const mapZoom = this.getZoom();
            const center = this.getCenter();
            if (mapZoom < 8) {
              _this.queryParams.zoom = 1;
            } else if (mapZoom < 13) {
              _this.queryParams.zoom = 2;
            } else if (mapZoom < 15) {
              _this.queryParams.zoom = 3;
            } else {
              _this.queryParams.zoom = 4;
            }
            _this.queryParams = {
              ..._this.queryParams,
              latitude: center.lat,
              longitude: center.lng,
            };
            _this.getLocation(center);
            _this.loadData()
            // geoc.getLocation(this.getCenter(), function(rs){
            //
            // });
          });
          _this.loadData()
        })
      },
      getLocation(point) {
        const _this = this;
        const geoc = new BMap.Geocoder();
        geoc.getLocation(point, function(rs){
          const { addressComponents: {province, city, district} } = rs;
          switch (_this.queryParams.zoom) {
            case 1:
              _this.curArea = '全国';
              break;
            case 2:
              _this.curArea = province ;
              break;
            case 3:
              _this.curArea = province + city;
              break;
            case 4:
              _this.curArea = province + city + district;
              break;
          }
          if (_this.queryParams.zoom > 1) {
            geoc.getPoint(_this.curArea, function(point){
              console.log(_this.curArea, point)
              // map.centerAndZoom(point, 16);
              // map.addOverlay(new BMap.Marker(point));
            });
          }
        });
      },
      getLatLng(address) {
        const geoc = new BMap.Geocoder();
        return new Promise((resolve, reject) => {
          geoc.getPoint(address, function(point){
            resolve(point)
            // console.log(_this.curArea, point)
            // map.centerAndZoom(point, 16);
            // map.addOverlay(new BMap.Marker(point));
          }, address);
        })
      },
      addMapLabel(info, zoom) {
        let point = new BMap.Point(info.longitude,info.latitude);
        let label = new BMap.Label(`<p style='margin:0 0 5px'>${info.address}</p><p style='margin: 0'><b>${info.deviceNumber}</b> 台</p>`, {position: point});  // 创建文本标注对象
        label.setStyle({
          backgroundColor: '#00B589',
          color: '#ffffff',
          font: '16px arial, sans-serif',
          minWidth: '70px',
          display: 'inline',
          border: '2px solid rgb(161, 255, 195)',
          borderRadius: '10px',
          textAlign: 'center',
          padding: '2px 3px',
          cursor: 'pointer'
        });
        label.addEventListener('click', () => {

          if(this.queryParams.zoom===4) {
            // this.$parent.$parent.activeName = 'second';
            console.log('我点了项目',this.$parent)
          } else {
            this.map.setCenter(point)
            this.map.setZoom(zoom)
          }
        })
        this.map.addOverlay(label);
      },
      async getAreaData() {
        const _this = this;
        const { data } = await getAreaData(this.queryParams)
        this.map.clearOverlays();
        data.map(p => {
          switch(_this.queryParams.zoom) {
            case 1:
              _this.addMapLabel(p, 8);
              break
            case 2:
              _this.addMapLabel(p, 13);
              break;
            case 3:
              _this.addMapLabel(p, 15);
              break;
            case 4:
              _this.addMapLabel(p, 15);
              break;
          }

        })
        if ( this.queryParams.zoom === 1 ) {

        } else if (this.queryParams.zoom === 2)  {
          // TODO 先临时这样把
          // data.map(p => {
          //   this.getLatLng(p.address).then(res => {
          //     console.log('w我是地址',p.address, res)
          //     p.longitude = res.lng;
          //     p.latitude = res.lat;
          //     _this.addMapLabel(p, 13)
          //   })
          // })
        } else if(this.queryParams.zoom === 3) {
          // TODO 先临时这样把
          // data.map(p => {
          //
          //   this.getLatLng(p.address).then(res => {
          //     console.log('w我是地址',p.address, res)
          //     p.longitude = res.lng;
          //     p.latitude = res.lat;
          //     _this.addMapLabel(p, 14)
          //   })
          // })
        } else {
          // data.map(p => {
          //   _this.addMapLabel(p, 15)
          // })
        }
      },
      async getAreaAllData() {
        const { data } = await getAreaAllData(this.queryParams);
        this.rData = data;
      },
      loadData() {
        this.getAreaData();
        this.getAreaAllData();
        this.updateTime = moment().format('YYYY-MM-DD HH:mm:ss')
      }
    },
    computed: {
    }
  }
</script>

<style scoped lang="scss">
.realtime-wrap{
    display: flex;
    width: 100%;
    height: calc(100vh - 130px);
    padding: 0 10px;
  .proj-sel {
    position: absolute;
    top: 30px;
    left: 30px;
    z-index: 999;
  }
  .left-map{
    flex: 1;
    .map-body{
      width: 100%;
      height: 100%;
    }
  }
  .right-aside{
    width: 380px;
    padding-left: 10px;
    line-height: 1.6;
    .more-btn{
      color: #00b589;
      border-color: #00b589;
      border-radius: 0;
      float: right;
    }
    .splitTit {
      display: flex;
      justify-content: space-between;
      position: relative;
      margin: 10px 0;
      padding-left: 10px;
      color: #939997;
      font-size: 14px;
      &:after{
        position: absolute;
        left: 0;
        width: 4px;
        height: 16px;
        background: #00b588;
        content: " ";
      }
    }
    .refresh_time{
      position: absolute;
      bottom: 10px;
      width: 380px;
      padding: 0 10px;
      background: #fff;
      display: flex;
      align-items: center;
      justify-content: space-between;
      color: #677373;
      font-size: 14px;
    }
    .des-item{
      display: flex;
      align-items: center;
      justify-content: space-around;
      margin-bottom: 10px;
      width: 100%;
      height: 21%;
      border: 1px solid #ecf2f1;
      .des-item-p{
        width: 45%;
        display: flex;
        align-items: center;
        flex-direction: column;
        color: #696969;
        b{
          font-size: 42px;
          color: #00b589;
        }
        span{
          font-size: 14px;
          color: #696969;
          font-weight: 400;
        }
        .online-status{
          width: 78%;
          justify-content: space-around;
          display: flex;
          align-items: center;
          b{
            font-size: 24px;
          }
        }
      }
    }
  }
}
</style>
