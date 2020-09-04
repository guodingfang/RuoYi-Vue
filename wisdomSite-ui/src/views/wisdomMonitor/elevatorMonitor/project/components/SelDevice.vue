<template>
  <el-dialog  :title="title" :visible.sync="flag" width="520px" append-to-body @close="closeDialog" :close-on-click-modal="false">
    <div class="cam-list">
      <el-radio-group v-model="sel" style="width: 100%;">
        <div class="list-item" v-if="type==='jk'" v-for="(item, i) in listData" :key="item.id">
          <div class="item-left"></div>
          <div class="item-right">
            <div style="flex: 1 1 0;">
              <p>{{item.installationPosition}}</p>
              <p>设备编号：{{item.serialNumber}}</p>
            </div>
            <div class="item-radio">
              <el-radio :label="i"><i></i></el-radio>
            </div>
          </div>
        </div>
        <div class="list-item" v-if="type==='dg'"  v-for="(item, i) in listData" :key="item.id">
          <div class="item-left"></div>
          <div class="item-right">
            <div style="flex: 1 1 0;">
              <p>{{item.installationPosition}}</p>
              <p>设备编号：{{item.equipmentSerialnumber}}</p>
            </div>
            <div class="item-radio">
              <el-radio :label="i"><i></i></el-radio>
            </div>
          </div>
        </div>
        <div class="list-item" v-if="type==='sj'" v-for="(item, i) in listData" :key="item.id">
          <div class="item-left"></div>
          <div class="item-right">
            <div style="flex: 1 1 0;">
              <p>{{item.installationPosition}}</p>
              <p>设备编号：{{item.serialNumber}}</p>
            </div>
            <div class="item-radio">
              <el-radio :label="i"><i></i></el-radio>
            </div>
          </div>
        </div>
        <div class="list-item" v-if="type==='cb'" v-for="(item, i) in listData" :key="item.id">
          <div class="item-left"></div>
          <div class="item-right">
            <div style="flex: 1 1 0;">
              <p>{{item.recordNumber}}</p>
              <p>{{item.deviceName}}</p>
            </div>
            <div class="item-radio">
              <el-radio :label="i"><i></i></el-radio>
            </div>
          </div>
        </div>
      </el-radio-group>
    </div>
    <span slot="footer" class="dialog-footer">
       <el-button @click="cancel">取消</el-button>
      <el-button type="primary" @click="confirmSel">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import {
    getChannelList,
  } from "@/api/smartMonitor/video";

  export default {
    name: 'SelDevice',
    props: {
      dialogFlag: {
        required: true,
        type: Boolean,
        default: false,
      },
      listData: {
        type: Array,
        default: () => [],
      },
      title: {
        type: String,
        default: ''
      },
      type: {
        type: String,
        default: ''
      }
    },
    data() {
      return {
        sel: 0,
        camForm: {
          equipmentType: '',
        },
        deviceType: [],
      };
    },
    created() {
    },
    methods: {
      /* 关闭 */
      closeDialog() {
        this.sel = 0;
      },
      confirmSel() {
        this.flag = false;
        this.$emit('change', this.listData[this.sel]);
      },
      cancel() {
        this.flag = false;
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
    },
  };
</script>

<style lang='scss' scoped>
  .cam-list{
    width: 100%;
    height: 600px;
    overflow: auto;
    padding: 0 10px;
    .list-item{
      display: flex;
      align-items: center;
      justify-content: space-between;
      margin-bottom: 15px;
      border: 1px solid #e1ece8;
      .item-left{
        display: flex;
        align-items: center;
        justify-content: center;
        width: 67px;
        height: 67px;
        background: #f8faf9;
      }
      .item-right{
        display: flex;
        align-items: center;
        justify-content: space-between;
        flex: 1 1;
        padding: 0 10px;
        .item-radio{
          width: 30px;
          height: 30px;
        }
        p{
          font-size: 14px;
          color: #606266;
        }
      }
    }
  }

</style>
