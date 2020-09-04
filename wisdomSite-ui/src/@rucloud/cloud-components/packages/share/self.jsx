export default {
  name: 'SelfItem',

  props: {
    value: [String, Number, Boolean],
    propName: [String, Number],
  },

  data() {
    return {
      dataValues: {
        value: this.value,
      },
    };
  },

  render() {
    return this.$scopedSlots.default(this.dataValues);
  },
};
