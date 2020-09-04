import get from 'lodash/get';
// import instance from '@/http/serve';
export const EMPTY_FLAG = '--';

export default {
  OPTION: {
    select: {
      sForm: '',
      props: {
        // size: 'small',
        size: 'small',
        filterable: true,
      },
      sWidth: {
        content: '228px',
        wrap: 'auto',
      },
    },
    'select-tree': {
      sWidth: {
        content: '228px',
        wrap: 'auto',
      },
    },
    input: {
      attrs: {
        maxlength: 50,
      },
      props: {
        size: 'small',
        // filterable: true,
      },
      sWidth: {
        content: '228px',
        wrap: 'auto',
      },
    },
    date: {
      sWidth: {
        content: '228px',
        wrap: 'auto',
      },
      HAS_DATETIMERANGE: {
        label: '64px',
        content: '328px',
        wrap: 'auto',
      },
      props: {
        size: 'small',
        'range-separator': '~',
      },
    },
    operate: {
      props: {
        size: 'small',
      },
      sWidth: {
        label: '0',
        content: '0',
        wrap: 'auto',
      },
      events: {},
    },
  },
  FORM: {
    form: {
      props: {
        size: 'default',
        inline: true,
        'label-position': 'top',
      },
      on: {},
    },
    select: {
      props: {
        filterable: true,
      },
      on: {},
    },
    input: {
      attrs: {
        maxlength: 50,
      },
      props: {
        clearable: true,
      },
      on: {},
    },
    date: {
      props: {},
      on: {},
    },
  },
  TABLE: {
    table: {
      props: {
        size: 'small',
        border: true,
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
};
