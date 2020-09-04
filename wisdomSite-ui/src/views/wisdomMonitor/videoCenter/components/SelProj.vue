<template>
<!--  <el-dialog  title="选择监控设备" :visible.sync="flag" width="360px" append-to-body @close="closeDialog" :close-on-click-modal="false">-->
  <div class="sel-wrap" v-show="flag">
    <div class="cam-list">
      <el-input style="width: 200px" v-model="keyWords" placeholder="请输入项目名称搜索" suffix-icon="el-icon-search"></el-input>
      <el-radio-group v-model="sel" style="width: 100%;">
        <div class="list-item" v-for="(item, i) in listData" :key="item.projectId">
          <div class="item-right">
            <div style="flex: 1 1 0;">
              <p>{{item.projectName||'我是项目名'}}</p>
              <p>摄像头数量：{{item.videoNumber}}</p>
            </div>
            <div class="item-radio">
              <el-radio :label="i"><i></i></el-radio>
            </div>
          </div>
        </div>
      </el-radio-group>
    </div>
    <div class="footer">
      <el-button size="small" @click="confirmSel">确定</el-button>
    </div>
  </div>
<!--  </el-dialog>-->
</template>

<script>
  import { projectStatistics } from '@/api/smartMonitor/video'
  export default {
    name: 'SelProj',
    props: {
      dialogFlag: {
        required: true,
        type: Boolean,
        default: false,
      },
/*      listData: {
        type: Array,
        default: () => [],
      },*/
    },
    data() {
      return {
        listData: [],
        keyWords: '',
        sel: 0,
        camForm: {
          equipmentType: '',
        },
        deviceType: [],
      };
    },
    created() {
      this.getProjList();
    },
    methods: {
      async getProjList() {
        const { data: projList } = await projectStatistics({
          projectName: this.keyWords
        });
        this.listData = projList;
      },
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
      keyWords(val) {
        if(!!val) {
          this.getProjList();
        }
      }
    },
  };
</script>

<style lang='scss' scoped>
  .sel-wrap{
    width: 360px;
    position: absolute;
    background: #ffffff;
    top: 0;
    right: 0;
    display: block;
    z-index: 6;
    border: 1px solid #f2f2f2;
    padding: 10px 0;
    .footer{
      text-align: right;
      padding-top: 5px;
      padding-right: 10px;
    }
    .cam-list{
      width: 100%;
      overflow: auto;
      padding: 0 10px;
      .list-item{
        width: 330px;
        /*padding: 0 10px 15px;*/
        box-sizing: border-box;
        display: flex;
        justify-content: space-between;
        align-items: center;
        border-bottom: 1px solid #f2f2f2;
        .item-right{
          width: 100%;
          display: flex;
          align-items: center;
          justify-content: space-between;
          .item-radio{
            width: 30px;
            height: 30px;
          }
          p{
            font-size: 14px;
            color: #606266;
          }
        }
/*        .item-right{
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
        }*/
      }
    }
  }

</style>
