import { get, set, isArray } from '@/utils';

export default function() {
  const vm = this;
  return (h, vmRcForm) => {
    // 存储vmRcForm的引用
    this.vmRcForm = vmRcForm;
    const rules = mes => {
      return {
        // required: valuesIn.state === 'NORMAL',
        required: true,
        message: `请输入${mes}`,
        trigger: 'blur'
      };
    };
    function normalSetValue(name) {
      return function setValue() {
        const prop = isArray(name) ? name[0] : name;
        vmRcForm.valuesIn[prop] = get(vm.modifyInfo, prop);
      };
    }
    return [
      {
        type: 'input',
        prop: 'name',
        label: '检查部位',
        rules: [rules('检查部位')],
      },
      {
        type: 'radio-group',
        prop: ['direction', '1'],
        label: '问题等级',
        vuemap: {
          on: {
            change: state => {
              console.log(vmRcForm.valuesIn);
            },
          },
        },
        optionMeta: {
          store: this,
          path: `stateList`
        },
        rules: [
          { required: true, message: '请选择问题等级', trigger: 'change' },
        ],
      },
      {
        type: 'select',
        prop: 'doorId',
        label: '问题分类',
        rules: [
          { required: true, message: '请选择问题分类', trigger: 'blur' },
        ],
      },
      {
        type: 'input',
        prop: 'remarks',
        label: '问题描述',
        vuemap: {
          attrs: {
            type: 'textarea',
            maxlength: 250,
          },
        },
      },
      {
        type: 'input',
        prop: 'remarkss',
        label: '整改要求',
        vuemap: {
          attrs: {
            type: 'textarea',
            maxlength: 250,
          },
        },
      },
      {
        type:'wrap',
        key: 'form-warp',
        items: [
          {
            type: 'select',
            prop: 'doorIds',
            label: '整改人',
            rules: [
              { required: true, message: '请选择整改人', trigger: 'blur' },
            ],
          },
          {
            type: 'date-picker',
            prop: 'installTime',
            label: '整改时限',
            key: 'pickers',
            vuemap: {
              props: {
                valueFormat: 'yyyy-MM-dd',
              },
            },
            rules: [
              { required: true, message: '请选择整改时限', trigger: 'blur' },
            ],
          },
          {
            type: 'select',
            prop: 'doorIwds',
            label: '验证人',
            rules: [
              { required: true, message: '请选择验证人', trigger: 'blur' },
            ],
          },
        ]
      },
      {
        type: 'area',
        prop: 'photo',
        label: '照片',
        rules: [rules('照片')],
      }
    ].map(config => {
      if (config.prop && !get(config, 'hooks.setValue')) {
        set(config, 'hooks.setValue', normalSetValue(config.prop));
      }
      return config;
    });
  };
}
