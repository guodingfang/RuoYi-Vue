<template>
  <div>
    <el-select
      v-model="projectId"
      clearable
      :disabled="disabled"
      :placeholder="placeholder"
      filterable
      @change="selectChange"
    >
      <el-option
        v-for="item in projcetList"
        :key="item.id"
        :label="item.projectName"
        :value="item.id"
      ></el-option>
    </el-select>
  </div>
</template>

<script>
  import _ from 'lodash';
  import { getProjList, getAllProject } from '@/api/project/project';
  export default {
    name: "ProjectSelect",
    data() {
      return {
        projcetList: []
      };
    },
    props: {
      formProjId: {
        type: [String, Number]
      },
      placeholder: {
        type: String,
        default: "请选择"
      },
      needName: {
        type: Boolean,
        default: false
      },
      disabled: {
        type: Boolean,
        default: false
      },
      type: {
        type: String,
        default: 'current',
      }
    },
    methods: {
      selectChange(val) {
        this.$emit("selectChange", val);
      }
    },
    computed: {
      projectId: {
        get() {
          return this.formProjId;
        },
        set(val) {
          this.$emit("update:formProjId", val);
          if (this.needName) {
            let obj =  _.find(this.projcetList, { 'id': val});
            this.$emit("update:formProjName", obj.projectName);
          }
        }
      }
    },
    created() {
      const getList = this.type === 'current' ? getProjList : getAllProject;
      if (this.projcetList.length === 0) {
        getList().then(res => {
          this.projcetList = this.type === 'current' ? res.rows : res.data;  // TODO因该搞成一致的
        })
      }
    }
  }
</script>

<style scoped>

</style>
