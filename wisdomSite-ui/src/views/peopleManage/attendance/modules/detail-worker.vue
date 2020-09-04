<template>
  <section class="warp" ref="scrollWrap">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item >
        <el-link :underline="false" @click="close">建筑工人</el-link>
      </el-breadcrumb-item>
      <el-breadcrumb-item>
        <span  class="jl-green mr10 pointer">人员详情</span>
      </el-breadcrumb-item>
    </el-breadcrumb>
    <div class="basic">
      <img class="b_img" :src="detailData.faceUrl">
      <ul class="b_text">
        <li class="name">{{detailData.name}}</li>
        <li>
          <span class="l_f">性别：{{detailData.sexName}}</span>
          <span class="l_r">年龄：{{detailData.age}}</span>
          <!-- <span>其他</span> -->
        </li>
        <li>
          <!-- <span class="l_f">文化程度：{{$lGet(detailData,'cultureStandard','暂无信息')}}</span> -->
          <span class="l_f">文化程度：{{detailData.cultureStandard}}</span>
          <span class="l_r">住址：{{$lGet(detailData,'address','暂无信息')}}</span>
        </li>
        <li>
          <span >身份证号：{{$lGet(detailData,'idNumber','暂无信息')}}</span>
        </li>
      </ul>
    </div>
    <el-tabs v-model="activeName">
      <el-tab-pane label="职业履历" name="first">
        <resume :parmId='detailData.id' v-if="activeName === 'first'" />
      </el-tab-pane>
      <el-tab-pane label="资格证书" name="second">
        <qualification :parmId='detailData.idNumber' v-if="activeName === 'second'" />
      </el-tab-pane>
      <el-tab-pane label="黑名单记录" name="dic">
        <blacklist :parmId='detailData.idNumber' v-if="activeName === 'dic'" />
      </el-tab-pane>
    </el-tabs>
  </section>
</template>
<script>
import resume from './../components/resume'
import qualification from './../components/qualification'
import blacklist from './../components/blacklist'
export default {
  name: '',
  components: {
    resume,
    qualification,
    blacklist,
  },
  props: {
    isShow: {
      type: Boolean,
      default: false
    },
    detailData: {
      type: Object,
      default: () => {}
    },
    state: {
      type: String,
      default: () => null
    }
  },
  watch: {
    isShow: {
      handler(val){
        if(val) {
          this.$nextTick(()=>{
            document.getElementById
            const inner = document.getElementById('att-warp')
            inner.scrollTop = 0
          })
        }
      },
      immediate:true
    }
  },
  created() {
  },
  data () {
    return {
      activeName: 'first',
    }
  },
  methods: {
    close() {
      this.$emit("on-close");
    }
  }
}
</script>
<style lang='scss' scoped>
$--border-size: 1px solid #dddcdc;
.warp{
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: #fff;
  z-index: 999;
  .el-breadcrumb{
    padding: 20px 0;
    font-size: 13px;
    border-bottom: $--border-size;
  }
  .basic{
    display: flex;
    align-items: center;
    .b_img{
      width: 120px; height: 120px;
    }
    .b_text{
      .name{
        font-size: 16px;
        font-weight: bold;
      }
      li {
        display: flex;
        line-height: 27px;
        span { margin-right: 10px; }
      }
    }
  }
}
</style>
