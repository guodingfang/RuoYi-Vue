import Vue from 'vue';
import DS from './main';
import options from './sConfig';

Vue.use(DS);

const dStore = new DS.DStore(options);

export default dStore;
