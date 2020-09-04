
<template>
  <div class="ele-pos-wrap">
    <div class="ele-btn-wrap">
      <div class="btn-l">
        <el-upload
          class="upload-btn"
          name="avatarfile"
          :action="action"
          :headers="headers"
          :show-file-list="false"
          :on-success="handleUploadSuccess"
          :before-upload="beforeAvatarUpload"
        >
          <el-button size="small" type="primary">点击上传</el-button>
        </el-upload>
        <el-button size="small" type="primary" @click="addUnload">添加设备</el-button>
      </div>
      <div class="btn-r">
        <el-button size="small" class="jl-btn" @click="addMapDevice">保存位置</el-button>
      </div>
    </div>
    <div class="pos-box-wrap">
      <div class="pos-wrap" :style="{ width: picWidth+ 'px', height: picHeight + 'px' }">
        <img class="bg-pic" ref="bg" :src="backgroundUrl" @load="loadImage($event)" alt="">
        <tower
          v-for="element in elements"
          :key="element.id"
          :x="element.x"
          :y="element.y"
          :w="50"
          :h="50"
          :z-index="element.id"
          :resizable="false"
          :lock-aspect-ratio="true"
          @dragging="(left, top) => dragging(element.id, left, top)"
          @dragstop="(left, top) => dragstop(element.id, left, top)"
          @resizestop="(x, y, width, height) => onResizeStop(element.id, x, y, width, height)"
          :parent="true">
          <div class="item">
            <el-popover
              placement="top"
              width="160"
              v-model="visible">
              <p>确定删除吗？</p>
              <div style="text-align: right; margin: 0">
                <el-button size="mini" type="text" @click="visible = false">取消</el-button>
                <el-button type="primary" size="mini" @click="delEle(element.id)">确定</el-button>
              </div>
              <i  slot="reference" class="el-icon-delete del-icon"></i>
            </el-popover>
            {{element.name}}
          </div>
          <!--          <p>Hello! I'm a flexible component. You can drag me around and you can resize me.<br>
                      X: {{ x }} / Y: {{ y }} - Width: {{ width }} / Height: {{ height }}</p>-->
        </tower>
      </div>
    </div>
    <sel-device
      title="未设置场布位置的设备"
      type="cb"
      :listData="selList"
      :dialogFlag.sync="showSel"
      @change="getSel"
    />
  </div>

</template>

<script>
  import {
    getDMList,
    deviceManaList,
    addLayOutMap,
    getLayOutMap,
    addMapDevice,
    delMapDevice
  } from "@/api/unload/index";
  import VueDraggableResizable from 'vue-draggable-resizable'
  import { getToken } from "@/utils/auth";
  import 'vue-draggable-resizable/dist/VueDraggableResizable.css'
  import SelDevice from "../components/SelDevice";
  export default {
    name: "LayoutMap",
    components: {
      SelDevice,
      tower: VueDraggableResizable,
    },
    data(){
      return{
        visible: false,
        projectId: this.$store.state.user.curRole.id,
        showSel: false,
        headers: { Authorization: getToken() },
        resourcesUrl: 'http://39.103.140.108:8080',
        action: process.env.VUE_APP_BASE_API + '/system/user/profile/avatar',
        imgUrl: '',
        backgroundUrl: '',
        picId: null,
        picWidth: 500,
        picHeight: 500,
        elements: [
          // {id: 1, x: 0, y: 0, text: 'Element 1'},
        ],
        selList: [],
        width: 0,
        height: 0,
        parent: false,
      }
    },
    created(){
      console.log('mounted')
      this.getLayOutMap();
      this.getUnloadPosList();
    },
    methods:{
      showPop(e) {
        console.log('click')
        this.visible = true
      },
      async delEle(id) {
        await delMapDevice(id);
        await this.getUnloadPosList();
        this.visible = false;
        this.msgSuccess('删除成功');
      },
      async getLayOutMap() {
        const { data } = await getLayOutMap(this.projectId);
        this.picWidth = data.width;
        this.picHeight = data.height;
        this.picId = data.id;
        this.imgUrl = data.fieldlayoutAddress;
        this.backgroundUrl = this.resourcesUrl + data.fieldlayoutAddress
        console.log(data)
      },
      async getUnloadPosList() {
        const { rows } = await getDMList({
          pageNum: 1,
          pageSize: 99,
        });
        let elements = [];
        rows.map(e => {
          if(!!e.coordinateX){
            elements.push({
              id: e.id, x: Number(e.coordinateX), y: Number(e.coordinateY), name: e.equipmentName
            })
          }
        })
        this.elements = elements;
      },
      async addMapDevice() {
        let elements = this.elements;
        let req = elements.map(e => {
          return {
            id: e.id,
            coordinateX: e.x,
            coordinateY: e.y,
          }
        })
        console.log(req)
        await addMapDevice(req)
        this.msgSuccess('保存成功')
      },
      /*选择设备*/
      async addUnload() {
        const { rows } = await deviceManaList();
        this.selList = rows;
        this.showSel = true;
      },
      getSel(e) {
        this.elements.push({
          id: e.id, x: 250, y: 250,  name: e.equipmentName||'暂时没名字',
        });
      },
      // onResize(id, x, y, width, height) {
      //   this.resizeId = id;
      //
      //   if (! this.sync) return;
      //
      //   const offsetX = left - this.draggingElement.x;
      //   const offsetY = top - this.draggingElement.y;
      //
      //   const deltaX = this.deltaX(offsetX);
      //   const deltaY = this.deltaY(offsetY);
      //
      //   this.elements.map(el => {
      //     if (el.id !== id) {
      //       el.x += deltaX;
      //       el.y += deltaY;
      //     }
      //
      //     return el;
      //   });
      // },
      onResizeStop(id, x, y, width, height) {
        console.log(width)
        // if (! this.sync) return;
        this.elements.map(el => {
          if (el.id === id) {
            el.x = x;
            el.y = y;
            el.r = width;
          }
          return el;
        });
      },
      dragging(id, left, top) {
        this.draggingId = id;

        if (! this.sync) return;

        const offsetX = left - this.draggingElement.x;
        const offsetY = top - this.draggingElement.y;

        const deltaX = this.deltaX(offsetX);
        const deltaY = this.deltaY(offsetY);

        this.elements.map(el => {
          if (el.id !== id) {
            el.x += deltaX;
            el.y += deltaY;
          }

          return el;
        });
      },
      dragstop(id, left, top) {
        this.elements.map(el => {
          if (el.id === id) {
            el.x = left;
            el.y = top;
          }

          return el;
        });

        // this.draggingId = null;
        this.prevOffsetX = 0;
        this.prevOffsetY = 0;
      },
      deltaX(offsetX) {
        const ret = offsetX - this.prevOffsetX;

        this.prevOffsetX = offsetX;

        return ret;
      },
      deltaY(offsetY) {
        const ret = offsetY - this.prevOffsetY;

        this.prevOffsetY = offsetY;

        return ret;
      },
      async loadImage(e) {
        const { clientWidth, clientHeight } = this.$refs.bg
        this.picWidth = clientWidth;
        this.picHeight = clientHeight;
        const res = await addLayOutMap({
          projectId: this.projectId,
          id: this.picId,
          width: clientWidth,
          height: clientHeight,
          fieldlayoutAddress: this.imgUrl,
        })
        console.log(res)
        this.$nextTick(() => {

          window.dispatchEvent(new Event('resize'))

        })
        console.log('加载好了')
      },
      handleUploadSuccess (response, file, fileList) {
        this.imgUrl = file.response.imgUrl
        this.backgroundUrl = this.resourcesUrl + file.response.imgUrl
        console.log(file.response.imgUrl)
      },
      beforeAvatarUpload (file) {
        /*        const isLt2M = file.size / 1024 / 1024 < 2
                if (!isLt2M) {
                  this.$message.error('上传头像图片大小不能超过 2MB!')
                }
                return isLt2M*/
      }
    }
  }
</script>

<style scoped lang="scss">
  /deep/.draggable.vdr{
    border: none;
  }
  .ele-pos-wrap{
    padding: 10px;
    box-sizing: border-box;
    .upload-btn{
      display: inline-block;
    }
    .ele-btn-wrap{
      display: flex;
      justify-content: space-between;
      min-height: 60px;
      margin-top: 10px;
    }
    .pos-box-wrap{
      width: 100%;
      position: relative;
      .pos-wrap{
        width: 500px;
        height: 500px;
        background: #ffffff;
        .bg-pic{
          position: absolute;
          left: 0;
          top: 0;
          z-index: 0;
        }
        .item{
          width: 100%;
          height: 100%;
          font-size: 12px;
          color: #ffffff;
          background: #09a17b;
          border-radius: 50%;
          padding: 3px;
          box-sizing: border-box;
          cursor: pointer;
          text-align: center;
          display: flex;
          align-items: center;
          position: relative;
          justify-content: center;
          &:hover{
            .del-icon{
              display: block;
            }
          }
          .del-icon{
            color: #ff0000;
            font-size: 24px;
            position: absolute;
            z-index: 999;
            top: 50%;
            margin-top: -12px;
            left: 50%;
            margin-left: -12px;
            display: none;
          }
        }
      }
    }
  }
  .box-point-1{
    width: 4px;
    height: 4px;
    position: absolute;
    background-color: #ffffff;
    border: #000 solid 1px;
    z-index: 9999;
  }
  .box-point-2{
    width: 4px;
    height: 4px;
    position: absolute;
    background-color: #ffffff;
    border: #000 solid 1px;
    z-index: 9999;
  }
  .box-content{
    border: chartreuse solid 1px;
    position: absolute;
    z-index: 999;
    cursor: move;
  }
  .active{
    display: block;
  }
  .toolbox{
    position: fixed;
    right: 100px;
    top: 90px;
    border: #2c3e50 solid 1px;
    padding: 5px;
    border-radius: 5px;
    background-color: dimgray;
  }
</style>
