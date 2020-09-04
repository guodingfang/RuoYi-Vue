// import instance from '@/http';
import get from 'lodash/get';

export const ASSETS_PROPS = ['width', 'min-width', 'fixed', 'er_hide', 'align', 'label', 'props'];

export default {
  table: {
    props: {
      size: 'medium',
      border: true,
      // stripe: true,
    },
    events: {},
  },
  colConfig: {
    props: {
      align: 'center',
    },
  },
  fetchConfig: function() {
    return {
      name: undefined,
      fetch: async config => {
        const url = '/material/myDataTableSetting/get';
        const param = {
          entityId: config.name,
        };
        let re = {};
        if (param.entityId) {
          re = await instance.get(url, {
            params: param,
          });
        }
        return JSON.parse(get(re, 'datas.resultJson', JSON.stringify([])));
      },
      update: async config => {
        const url = '/material/myDataTableSetting/update';
        const param = {
          entityId: config.name,
          resultJson: config._tableConfig,
        };
        let re = {};
        if (param.entityId && param.resultJson) {
          param.resultJson = JSON.stringify(param.resultJson);
          re = await instance.post(url, param);
        }
        return re;
      },
    };
  },
};
