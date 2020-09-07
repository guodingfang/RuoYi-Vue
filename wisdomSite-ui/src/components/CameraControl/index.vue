<template>
  <div :class="['camera-wrap', cusClass]">
    <div class="video-box">
      <slot name="video-head"></slot>
      <div :id="playerConfig.id" :style="rootStyle" class="xg-player">
      </div>
    </div>
    <div class="control-box">
      <slot name="control-head"></slot>
      <div class="top-group ctrl-group">
        <button class="control-btn" @click="doControl('4')"><i class="el-icon-top-left"></i></button>
        <button class="control-btn" @click="doControl('0')"><i class="el-icon-top"></i></button>
        <button class="control-btn" @click="doControl('6')"><i class="el-icon-top-right"></i></button>
      </div>
      <div class="mid-group ctrl-group">
        <button class="control-btn" @click="doControl('2')"><i class="el-icon-back"></i></button>
        <button class="control-btn" style="font-size: 12px">超清</button>
        <button class="control-btn" @click="doControl('3')"><i class="el-icon-right"></i></button>
      </div>
      <div class="bot-group ctrl-group">
        <button class="control-btn" @click="doControl('5')"><i class="el-icon-bottom-left"></i></button>
        <button class="control-btn" @click="doControl('1')"><i class="el-icon-bottom"></i></button>
        <button class="control-btn" @click="doControl('7')"><i class="el-icon-bottom-right"></i></button>
      </div>
      <div class="zoom-box mt20">
        <button class="control-btn zoom" @click="doControl('9')">缩小</button>
        <button class="control-btn zoom" @click="doControl('8')">放大</button>
      </div>
      <div class="zoom-box mt20">
        <button class="control-btn zoom" @click="doCapture">抓拍</button>
        <button class="control-btn zoom" @click="playBack">回放</button>
      </div>
      <div class="mt20">
        <el-calendar>
          <!-- 这里使用的是 2.5 slot 语法，对于新项目请使用 2.6 slot 语法-->
          <template
            slot="dateCell"
            slot-scope="{date, data}"
          >
            <p :class="data.isSelected ? 'is-selected' : ''">
              {{ data.day.split('-').slice(1)[1] }}
            </p>
          </template>
        </el-calendar>
      </div>
      <p class="mt20">操作信息</p>
      <el-input type="textarea" :rows="10"></el-input>
    </div>
  </div>
</template>

<script>
  import 'xgplayer'
  import hlsjsPlayer from 'xgplayer-hls.js';
  import { camControl, capture, getOnlineVideo } from  '@/api/system/device';

  export default {
    name: "CameraControl",
    components: {

    },
    props: {
      liveInfo: {
        type: Object,
        default: () => {},
      },
      cusClass: {
        type: String,
        default: '',
      }
    },
    data() {
      return {
        Player: null,
        rootStyle: {
          width: '100%',
          minHeight: '600px'
        },
        playMode: 'hls',
        playUrl: ''
      }
    },
    mounted() {
      this.$nextTick(() => {
        this.initPlayer()
      })
    },
    beforeUpdate() {
      // this.initPlayer();
    },
    beforeDestroy() {
      console.log('destroy')
      this.player && typeof this.player.destroy === 'function' && this.player.destroy();
    },
    computed: {
      playerConfig: {
        get() {
          return {
            id: 'vs',
            url: this.playUrl,
            autoplay: true,
            playsinline: true,
            fluid: true,
            loop: true,
          }
        },
        set(val) {
          console.log('我变了', val)
        }
      }
    },
    watch: {
      // 'liveInfo.palyUrl': function (val) {
      //   this.initPlayer();
      // },
      liveInfo: {
        deep: true,
        immediate: true,
        handler(val) {
          this.getPlayUrl()
          console.log('i am change', val)
        }
      },
    },
    methods: {
      getPlayUrl() {
        //equipmentSerialnumber, channelNumber,
        const { equipmentSerialnumber, channelNumber } = this.liveInfo;
        console.log('mode', this.playMode)
        getOnlineVideo({ equipmentSerialnumber, channelNumber, playMode: this.playMode }).then(res => {
          this.playUrl = res.msg
          this.initPlayer()
        })
      },
      initPlayer() {
        try {
          this.player && typeof this.player.destroy === 'function' && this.player.destroy();
        } catch (e) {
          console.log(e)
        }
        if (this.playerConfig.url && this.playerConfig.url !== '') {
          this.player = new hlsjsPlayer(this.playerConfig);
          this.$emit('player', this.player)
        }
      },
      doControl(action) {
        console.log(action)
        camControl({
          "direction": action,
          "channelNumber": this.liveInfo.channelNumber,
          // "hls": "",
          // "hlsHd": "",
          // "rtmp": "",
          // "rtmpHd": "",
          "serialNumber": this.liveInfo.equipmentSerialnumber,
          "speed": '1'
        }).then(res => {

        })
      },
      doCapture() {
/*        capture({
          serialNumber: this.liveInfo.equipmentSerialnumber,
          channelNumber: this.liveInfo.channelNumber
        }).then(res => {
        })*/
          this.downloadIamge()
      },
      playBack() {
        // this.getPlayUrl();
      },
      downloadIamge(imgsrc, name) {
        let _this = this
        let player = this.player
        let canvas = document.createElement('canvas')
        let canvasCtx = canvas.getContext('2d')
        let img = new Image()
        canvas.width = 960
        canvas.height = 576
        img.onload = (function () {
          canvasCtx.drawImage(player.video, 0, 0, canvas.width, canvas.height)
          img.setAttribute('crossOrigin', 'anonymous')
          img.src = canvas.toDataURL('image/png').replace('image/png', 'image/octet-stream')
          let screenShotImg = img.src.replace(/^data:image\/[^;]+/, 'data:application/octet-stream')
          _this.saveScreenShot(screenShotImg, '抓拍.png')
        })()
      },
      saveScreenShot (data, filename) {
        let saveLink = document.createElement('a')
        saveLink.href = data
        saveLink.download = filename
        let event = document.createEvent('MouseEvents')
        event.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null)
        saveLink.dispatchEvent(event)
      }
    }
  }
</script>

<style scoped lang="scss">
  .camera-wrap{
    width: 100%;
    display: flex;
    .video-box{
      flex: 1;
      max-width: 1240px;
      .xg-player{
        width: 100%;
        height: 600px;
        background: #000000;
      }
    }
    .control-box{
      width: 260px;
      /*height: 600px;*/
      font-size: 14px;
      border: 1px solid #09a17b;
      margin-left: 5px;
      padding: 0 16px;
      .ctrl-group{
        display: flex;
        justify-content: space-between;
        margin-top: 20px;
      }
      .zoom-box{
        display: flex;
        justify-content: space-between;
      }
      .control-btn{
        width: 48px;
        height: 48px;
        color: #09a17b;
        background: #ffffff;
        border:1px solid #09a17b;
        outline: none;
        margin: 0;
        padding: 0;
        font-size: 20px;
        line-height: 48px;
        cursor: pointer;
        &:hover{
          color: #ffffff;
          background: #09a17b;
        }
        &.zoom{
          width: 85px;
          height: 30px;
          font-size: 12px;
          line-height: 30px;
        }
      }
    }
  }
  .is-selected {
    background-color: #09a17b !important;
    border-radius: 50%;
    color: #fff;
  }
</style>
