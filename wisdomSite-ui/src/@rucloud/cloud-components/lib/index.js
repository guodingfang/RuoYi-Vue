// import RcRef from '../packages/share/ref';
// import RcSearch from '../packages/search';
// import RcFormRcForm from '../packages/form';
// import RcTab from '../packages/tab';
import RcTitle from '../packages/title';
import RcUpload from '../packages/upload';
import RcTable from '../packages/table';
import RcDetail from '../packages/detail';
import RcLabel from '../packages/label';
// import initConfig from './initConfig';

// const cps = [RcSearch, RcFormRcForm, RcTab, RcTitle, RcTable, RcDetail, RcLabel];
const cps = [ RcTitle, RcTable, RcDetail, RcLabel];
// const cps = [RcSearch, RcFormRcForm, RcTab, RcTitle];

export default {
  install(vue, config) {
    // ref 组件
    // vue.use(RcRef, { name: 'rc-ref' });
    // 全局注册组件
    cps.forEach(cp => {
      vue.component(cp.cname, cp(config));
    });
    vue.component(RcUpload.name, RcUpload);
    // vue.component(RcTable.name, RcTable);
    // vue.prototype.$ER_CONFIG = initConfig;
  },
};
