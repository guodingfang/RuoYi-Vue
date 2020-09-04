<template>
  <div class="moni-wrap">
    <div class="moni-top">
      <camera-control :liveInfo="liveInfo" cusClass="camera-box">
        <p class="split-tit" slot="video-head">{{videoProj.projectName}}</p>
        <p class="split-tit" slot="control-head">云台控制 (仅球机类型可操作)</p>
      </camera-control>
    </div>
    <div class="moni-btm">
      <p class="split-tit sel-wrap">摄像头总数：<span class="cam-num">{{vodeoList.length}}</span>
        <el-select v-model="zoneId" class="sel-area">
          <el-option
            v-for="item in areaList"
            :key="item.zoneName"
            :label="`${item.zoneName}(${item.videoNumber})`"
            :value="item.zoneId"
          ></el-option>
        </el-select>
      </p>
      <div class="video-list">
        <div  v-for="(item, i) in vodeoList" :class="`video-item ${i===videoIndex?'active':''}`" @click="videoIndex=i">
          <img :src="item.wsStartPTZControl.coverPicUrl" alt="">
          <div>
            <h5>{{item.videoMonitoryPointName}}</h5>
          </div>
        </div>
      </div>
    </div>
    <sel-proj
      :listData="projList"
      :dialogFlag.sync="showSel"
      @change="setProj"
    />
  </div>
</template>

<script>
  import { projectStatistics, zoneStatistics, videoList } from '@/api/smartMonitor/video'
  import CameraControl from "@/components/CameraControl/index";
  import SelProj from "./components/SelProj";
  export default {
    name: "VideoMoni",
    components: {
      SelProj,
      CameraControl,
    },
    data() {
      return {
        showSel: false,
        projList: [],
        areaList: [],
        vodeoList: [],
        zoneId: null,
        videoIndex: 0,

      };
    },
    created() {
      this.getProjList();
      // this.getVideoList();
    },
    methods: {
      async getProjList() {
       const { data: projList } = await projectStatistics();
        await this.$store.dispatch('setVideoPid', {
          projectName: '暂无',
          totalNum: 0,
          projectId: this.$store.state.user.curRole.id,
        });
       if (projList.length === 0) {
         return ;
       }
       this.projList = projList;
       await this.$store.dispatch('setVideoPid', projList[0]);
      },
      async getAreaList() {
        const query = {
          projectId: this.videoProj.projectId,
          enabed: 1,
        };
        const { data: areaList } = await zoneStatistics(query);
        if (areaList.length === 0) {
          return ;
        }
        this.areaList = areaList;
        this.zoneId = areaList[0].zoneId;
      },
      async getVideoList() {
        const query = {
          zoneId: this.zoneId,
          enabed: 1,
        };
        const { data:vodeoList } = await videoList(query);
        this.vodeoList = vodeoList;
        this.videoIndex = 0;
      },
      setProj(e) {
        this.$store.dispatch('setVideoPid', e);
      },
    },
    computed: {
      videoProj: {
        get() {
          return this.$store.state.videoMoni.videoProject
        },
        set(val) {
        }
      },
      liveInfo: {
        get() {
          let liveInfo = {};
          const { videoIndex, vodeoList } = this;
          if (vodeoList.length !== 0 && !!vodeoList[videoIndex].wsStartPTZControl) {
            liveInfo.palyUrl = vodeoList[videoIndex].wsStartPTZControl.hls;
            liveInfo.channelNumber = vodeoList[videoIndex].videoChannel;
            liveInfo.equipmentSerialnumber = vodeoList[videoIndex].videoSerialNumber;
          }
          return liveInfo;
        },
        set(val) {

        },
      },
    },
    watch: {
      'videoProj.projectId': function(val) {
        this.getAreaList();
      },
      zoneId(val) {
        console.log(val, this.videoProj.projectId, '正确与否')
        this.getVideoList();
      }
    }
  }
</script>

<style scoped lang="scss">
  .split-tit{
    font-size: 14px;
    color: #333333;
    &.sel-wrap{
      align-items: center;
      justify-content: flex-start;
      .cam-num{
        color: #00b588;
      }
      &:after{
        top: 7px;
      }
    }
    .sel-area{
      width: 200px;
      margin-left: 40px;
    }
  }
  .video-list{
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    padding: 0 10px 20px;
    .video-item{
      display: flex;
      align-items: center;
      justify-content: space-between;
      margin: 10px 10px 0;
      cursor: pointer;
      img{
        width: 120px;
        height: 120px;
        margin-right: 10px;
      }
      h5{
        width: 89px;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        font-size: 14px;
        color: #333333;
        font-weight: normal;
      };
      &.active{
        img{
          border: 2px solid #00b588;
        }
        h5{
          color: #00b588;
        }
      }
    }
  }
  /deep/.camera-box{
    .control-box{
      border: none;
    }
  }
</style>
