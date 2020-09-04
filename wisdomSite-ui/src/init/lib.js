import Vue from 'vue';


import Rc from '@rucloud/cloud-components';
import Rcs from '@ruclouds/cloud-components';
// import dayjs from 'dayjs';
export const EMPTY_FLAG = '';
import { get } from "@rucloud/cloud-share/dist/utils";
Vue.prototype.$lGet  = get

// Vue.use(Rc, {
//   config: {
//     form: {
//       EL_FORM: {
//         attrs: {
//           size: 'small',
//         },
//       },
//       input: {
//         attrs: {
//           maxlength: 50,
//         },
//       },
//     },
//     search: {
//       input: {
//         props: {
//           size: 'small',
//         },
//       },
//       select: {
//         props: {
//           size: 'small',
//         },
//       },
//       'date-picker': {
//         props: {
//           size: 'small',
//         },
//       },
//     }
//   }
// })
Vue.use(Rcs, {
  config: {
    TABLE: {
      table: {
        props: {
          size: 'small',
          border: false,
          'header-cell-style': { backgroundColor: 'rgba(250,250,251,1)' }
          // 'header-cell-style': {backgroundColor:'rgba(250,250,251,1)'}
          // stripe: true,
        },
        events: {},
      },
      colConfig: {
        props: {
          align: 'left',
          'show-overflow-tooltip': true,
          formatter: (row, column, cellValue, index) => {
            if (cellValue === null || cellValue === undefined) {
              return EMPTY_FLAG;
            } else {
              return cellValue;
            }
          },
        },
      },
    },
    form: {
      EL_FORM: {
        attrs: {
          size: 'small',
        },
      },
      input: {
        attrs: {
          maxlength: 50,
        },
      },
    },
  },
});

// dayjs
// Vue.prototype.$dayjs = dayjs;
