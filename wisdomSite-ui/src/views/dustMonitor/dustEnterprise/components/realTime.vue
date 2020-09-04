<template>
  <section class="dwarp">
    <div class="select-warp">
      <el-button type="primary" @click="openLive" v-show="show">监测点视频</el-button>
      <span class="tb-span">监测点</span>
      <el-select @change="changeSelect" value-key="monitoringEquipmentNo" v-model="monitorParm" placeholder="排序">
        <el-option
          v-for="item in selectList"
          :key="item.monitoringEquipmentNo"
          :label="`${item.monitoringPointName}(${item.onlineOfflineName})`"
          :value="item" />
      </el-select>
    </div>

    <gauge :monitorParm='monitorParm' class="comt" style="min-width: 797px;flex:1" />
    <gaMonitor :monitorParm='monitorParm' class="comt" style="flex:1;min-width: 300px;margin-right: 17px;" />

    <live-dialog
      :dialogFlag.sync="liveDialogFlag"
      :videoEquipmentId.sync="videoEquipmentId"
    />
  </section>
</template>
<script>
import gauge from './gauge'
import gaMonitor from './ga-monitor'
import { getSelectList, } from "@/api/dustMonitor";
import LiveDialog from './../../components/LiveDialog'
export default {
  name: "realTime",
  components: {
    gauge,gaMonitor,LiveDialog
  },
  inject: ['detailParm'],
  async created() {
    const re = await getSelectList({projectId:this.detailParm.projectId})
    this.selectList = re.rows
    this.monitorParm = this.$lGet(this,'selectList.0', {})
    // console.log(this.detailParm);
  },
  watch: {
    'monitorParm.videoEquipmentId': {
      handler(val) {
        if (val) {
          this.show = true
        } else {
          this.show = false
        }
      },
      immediate: true
      // deep:true
    }
  },
  data() {
    return {
      selectList: [],
      monitorParm: {},
      liveDialogFlag: false,
      videoEquipmentId: null,
      show: true
    }
  },
  methods: {
    changeSelect() {},
    /** 打开查看框*/
    openLive() {
      console.log();
      this.videoEquipmentId = this.monitorParm.videoEquipmentId
      this.liveDialogFlag = true;
      // getOnlineVideo({...rowData, id: '', playMode: 'hls'}).then(res => {
      //   this.liveInfo = {...rowData, palyUrl: res.msg}
      //   this.liveDialogFlag = true;
      // })
    },
  }
};
</script>
<style lang="scss" scoped>
.dwarp{
  width: 100%;
  height: calc(100vh - 136px);
  overflow-y: auto;
  overflow-x: hidden;
  display: flex;
}
.comt{
  margin-left: 17px;
}
.tb-span{
  color: #999999;
  font-size: 14px;
  margin: 0 10px;
}
.select-warp{
  position: absolute;
  top: -40px;
  right: 0;
}
</style>
