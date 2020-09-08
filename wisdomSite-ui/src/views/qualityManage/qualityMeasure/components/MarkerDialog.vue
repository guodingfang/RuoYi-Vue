<template>
  <el-dialog  title="实测实量点" :visible.sync="flag" append-to-body @open="openDialog" width="800px" @close="closeDialog" :close-on-click-modal="false">
    <div style="width: 100%;height: 600px">
      <l-map
        ref="map"
        :min-zoom="minZoom"
        :crs="crs"
        style="height: 100%; width: 100%;"
      >
        <l-image-overlay
          :url="url"
          :bounds="bounds"
        />
        <l-circle-marker
          v-if="!readOnly"
          :lat-lng="newStar.center"
          :radius="newStar.radius"
          :color="newStar.color"
          :fillColor="newStar.color"
          :fillOpacity="1"
        />
        <l-circle-marker
          v-for="(star, index) in selectTableData"
          :key="index"

          :lat-lng="star.center"
          :radius="star.radius"
          :color="star.color"
          :fillColor="star.color"
          :fillOpacity="1"
        />
<!--去除单点-->
<!--        <l-circle-marker-->
<!--          v-if="!isCreate"-->
<!--          :lat-lng="star.center"-->
<!--          :radius="star.radius"-->
<!--          :color="star.color"-->
<!--          :fillColor="star.color"-->
<!--          :fillOpacity="1"-->
<!--        />-->

<!--        <l-marker-->
<!--          v-for="star in stars"-->
<!--          :key="star.name"-->
<!--          :lat-lng="star"-->
<!--        >-->
<!--          <l-icon-default :image-path="path" />-->
<!--          <l-popup :content="star.name" />-->
<!--        </l-marker>-->
      </l-map>
    </div>
    <div class="legend">
      <span class="red"></span> 已标记
      <span class="yellow"></span> 修改标记
      <span class="blue"></span> 现标记
    </div>
    <div slot="footer" v-if="!readOnly">
      <el-button type="primary" @click="setPoint">保存</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {
    addLocation,
    editLocation,
  } from "@/api/quality/measure";
  import { CRS } from "leaflet";
  // import * as Vue2Leaflet from 'vue2-leaflet'; // VALID
  import { LMap, LImageOverlay, LMarker, LPopup, LCircleMarker } from "vue2-leaflet";
  import 'leaflet/dist/leaflet.css';
  export default {
    name: 'MarkerDialog',
    components: {
      LMap,
      LImageOverlay,
      LMarker,
      LPopup,
      LCircleMarker
    },
    props: {
      dialogFlag: {
        required: true,
        type: Boolean,
        default: false,
      },
      dataForms: {
        type: Object,
        default: () => {},
      },
      selectTableData: {
        type: Array,
        default: () => [],
      },
      isCreate: {
        type: Boolean,
        default: true,
      },
      readOnly: {
        type: Boolean,
        default: false,
      },
    },
    data() {
      return {
        path: "/images/",
        picBase: process.env.VUE_APP_BASE_PIC,
        url: this.dataForms.mapUrl,
        bounds: [[-126.5, -125], [1021.5, 1023]],
        minZoom: -2,
        crs: CRS.Simple,
        star:{
          center: [0, 0],
          radius: 6,
          color: 'red'
        },
        newStar: {
          center: [0, 0],
          radius: 6,
          color: 'blue'
        }
      };
    },
    created() {

    },
    mounted() {

    },
    methods: {
      setPoint() {
        this.$emit('mChange', this.newStar);
        this.flag = false;
      },
      /* 添加 */
      handleAdd() {
        this.$refs.dataForm.validate(async (valid) => {
          if (!valid) return;
          try {
            console.log(this.dataForm)
            await addLocation(this.dataForm);
            this.msgSuccess('添加成功');
            this.flag = false;
            this.$emit('query');
          } catch (e) {
            console.log(e);
          }
        });
      },
      /* 编辑 */
      handleUpdate() {
        this.$refs.dataForm.validate(async (valid) => {
          if (!valid) return;
          try {
            await editLocation(this.dataForm);
            this.msgSuccess('编辑成功');
            this.flag = false;
            this.$emit('query');
          } catch (e) {
            console.log(e);
          }
        });
      },
      /* 关闭 */
      closeDialog() {
        this.dataForm = {
          coordinateX: 0,
          coordinateY: 0
        };
      },
      openDialog() {
        this.$nextTick(() => {
          const _this = this;
          const map = this.$refs.map.mapObject;
          map.setView([500, 500], -1);
          map.on('click', function(ev) {
            if(!_this.readOnly) {
              _this.$set(_this.newStar, 'center', [ev.latlng.lat, ev.latlng.lng] );
            }
            // { name: "Deneb", lng: 218.7, lat: 8.3 }
          });
        });
      },
      openMarker() {

      },
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
    watch: {
      dataForms(val) {
        this.dataForm = val;
        this.$set(this.star, 'center', [Number(val.coordinateX||0), Number(val.coordinateY||0)] );
        // this.dataForm = Object.assign({ orderNum: 0 }, val);
        this.url = val.mapUrl
        // this.dataForm.officeId = Number(this.dataForm.officeId);
      },
    },
  };
</script>

<style lang='scss' scoped>
  /deep/.leaflet-control-attribution{
    display: none;
  }
  .form-des{
    color: #cccccc;
    padding-left: 10px;
  }
  .legend{
    width: 100%;
    text-align: center;
    line-height: 30px;
    span{
      display: inline-block;
      width: 12px;
      height: 12px;
      border-radius: 50%;
      &.blue{
        background-color: blue;
      }
      &.yellow {
        background-color: yellow;
      }
      &.red{
        background-color: red;
      }
    }
  }
</style>
