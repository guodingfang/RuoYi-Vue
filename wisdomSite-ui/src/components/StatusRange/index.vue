<template>
  <div :class="['status-range', status]">
    <p class="title">{{title}}</p>
    <div class="progress-box">
      <p class="val">{{cur}}</p>
      <el-progress :percentage="percentage" :show-text="false"></el-progress>
      <p class="range"><span>{{min}}</span>{{max}}</p>
    </div>
  </div>
</template>

<script>
  export default {
    name: "StatusRange",
    props: {
      title: {
        type: String,
        default: '重量'
      },
      status: {
        type: String,
        default: ''
      },
      min: {
        type: [String, Number],
        default: 0
      },
      max: {
        type: [String, Number],
        default: 1
      },
      cur: {
        type: [String, Number],
        default: 0
      }
    },
    data() {
      return {
        // status: ''
      }
    },
    computed: {
      percentage() {
        if(!!this.max) {
          let perc =  isNaN(this.cur*100/this.max) ? 0 : this.cur*100/this.max;
          return perc;
        } else {
          return 100;
        }
      }
    }
  }
</script>

<style scoped lang="scss">
.status-range{
  width: 180px;
  height: 100px;
  border: 1px solid #09a17b;
  color: #09a17b;
  &.warning{
    border-color: #ffc267;
    .title{
      color: #ffc267;
    }
  }
  &.error{
    border-color: #ff813e;
  }
  .title{
    width: 150px;
    font-size: 14px;
    height: 14px;
    margin: 18px auto 0;
  }
  .progress-box{
    width: 150px;
    font-size: 12px;
    color: #47494a;
    margin: 0 auto;
    .val{
      text-align: center;
      height: 12px;
      margin: 8px 0;
    }
    .range{
      display: flex;
      height: 12px;
      justify-content: space-between;
      margin: 8px 0;
    }
  }
}
</style>
