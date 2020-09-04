export default {
  computed: {
    isDetail() {
      return this.state === 'detail';
    },
  },
  methods: {
    _preJudgeDetail() {
      if (this.isDetail) {
        this.$message('详情状态不能修改');
      }
      return this.isDetail;
    },
  },
};
