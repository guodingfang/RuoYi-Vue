<template>
  <el-dialog
    title="授权"
    :visible.sync="isShow"
    width="900px"
    :before-close="handleClose"
    :close-on-click-modal="false"
  >
    <div class="dialog_warp">
      <div class="dialog_left">
        <h4 class="tit">工地划分</h4>
        <ul class="d_left">
          <li :class="{'active': item.id == active}"
            v-for="item in zTable" :key="item.id"
            @click="clickActive(item.id)"
          >
            {{item.name}}
          </li>
        </ul>
      </div>
      <div class="dialog_right">
        <h4 class="tit">考勤机</h4>
        <el-checkbox-group v-model="checkedList">
          <el-checkbox
            @change="handledataChange(item,$event)"
            v-for="item in mTable"
            :label="item.serialNumber" :key="item.serialNumber"
          >
            {{item.name}}
          </el-checkbox>
        </el-checkbox-group>
      </div>
    </div>
    <h4 class="tit">已选考勤机</h4>
    <el-tag
      v-for="(tag, index) in tagList"
      :disable-transitions="true"
      :key="index"
      closable
      type="success"
      @close="delTag(tag, index)"
    >
      {{ tag.name }}
    </el-tag>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" class="r_button" @click="_submit">保存</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { getZtable, getMtable, addDance } from "@/api/people/authorize";
export default {
  name: 'add',
  data () {
    return {
      active: '',
      zTable: [],
      mTable: [],
      checkedList: [],
      tagList: [],
    }
  },
  props: {
    isShow: {
      type: Boolean,
      default: false
    },
    detailData: {
      type: [Object, Array],
      default: () => {}
    },
    guids: {
      type: [Array],
      default: () => []
    },
    state: {
      type: String,
      default: () => null
    }
  },
  created() {
    console.log(this.$DStore.state);
    this.getZtable()
  },
  methods: {
    handleClose() {
      this.$emit("on-close");
    },
    clickActive(id) {
      this.active = id
      this.getMtable()
    },
    handledataChange(item,event) {
      if (this.tagList.length > 0) {
          if (event) {
            this.tagList.push(item)
          } else {
            let indexTag
            this.tagList.forEach((tag,index) => {
              if (item.id === tag.id) {
                indexTag = index
              }
            })
            this.tagList.splice(indexTag,1)
          }
      } else {
        this.tagList.push(item)
      }
      console.log(this.tagList);
    },
    delTag(item, index) {
      let indexChecked = this.checkedList.indexOf(item.serialNumber)
      this.checkedList.splice(indexChecked,1)
      this.tagList.splice(index, 1);
    },
    async getZtable() {
      const re = await getZtable()
      this.zTable = re.data
      this.active = re.data[0].id
      this.getMtable()
    },
    async getMtable() {
      const re = await getMtable({zoneId: this.active})
      this.mTable = re.rows
    },
    async _submit() {
      if (this.checkedList && this.checkedList.length > 0) {
      }else {
        this.$message('请勾选考勤机');
        return
      }
      const re = await addDance({
        guids:this.guids,
        serialNumbers: this.checkedList
      })
      if (re.code == 200) {
        this.$message({
          message: re.msg,type: "success",
        });
        this.$emit("on-close",true);
      }
    },
  }
}
</script>
<style lang='scss' scoped>
.dialog_warp{
  display: flex;
  .dialog_left{
    width: 200px;
    .d_left{
      padding-left: 20px;
      li{
        line-height: 32px;
        cursor:pointer;
      }
      .active{
        color: #1ab394;
      }
    }
  }
}
.tit {
  border-left: 4px solid #1ab394;
  padding-left: 10px;
  width: 100%;
  .tit_right {
    padding: 0;
    float: right;
  }
}
.el-tag {
  margin-right: 10px;
}
</style>
