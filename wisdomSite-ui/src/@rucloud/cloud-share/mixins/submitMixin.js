import debounce from 'lodash/debounce';

export default {
  data() {
    return {
      subLoading: false,
    };
  },
  methods: {
    operateSubLoading(flag) {
      this.subLoading = flag;
    },
  },
  created() {
    // 保存
    this.submitForm = debounce(this._submitForm, 400);
  },
};
