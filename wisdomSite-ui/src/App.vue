<template>
  <div id="app">
    <router-view v-if="isRouterAlive"/>
  </div>
</template>

<script>
import preLoading from '@/store/dict/preLoading';
import { getToken } from '@/utils/auth';
export default  {
  name:  'App',
  provide (){
    return {
      reload:this.reload
    }
  },
  data(){
    return {
      isRouterAlive:true
    }
  },
  methods:{
    reload (){
      this.isRouterAlive = false
      this.$nextTick(function(){
        this.isRouterAlive = true
      })
    }
  },
  created() {
    if (getToken()) {
      this.$DStore.refresh(preLoading, true);
    }
  },
}
</script>
