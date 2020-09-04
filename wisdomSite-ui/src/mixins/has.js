import store from '@/store'

export default {
  // computed: {
  //   isDetail() {
  //     return this.state === 'detail';
  //   },
  // },
  methods: {
    flagHas(parm) {
      const all_permission = "*:*:*";
      const permissions = store.getters && store.getters.permissions
      let flag
      if (permissions.includes("*:*:*")) {
        flag = true
      } else {
        flag = permissions.includes(parm)
      }
      console.log(flag);
      return flag
    }
  },
};
