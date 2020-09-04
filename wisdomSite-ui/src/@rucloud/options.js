export default function() {
  return h => {
    return [
      {
        type: 'input',
        prop: ['input', 100],
        label: 'input-label',
        hide: this.hide,
        vuemap: {
          ref: 'inputRef',
          props: {
            clearable: true,
          },
          attrs: {
            placeholder: 'input-placeholder',
          },
        },
        slotGen: {
          prefix: (
            <template slot="prefix">
              <div id="input_prefix"></div>
            </template>
          ),
          suffix: (
            <template slot="suffix">
              <div id="input_suffix"></div>
            </template>
          ),
        },
      },
      {
        type: 'select',
        prop: ['select', '2'],
        label: 'select-label',
        vuemap: {
          ref: 'selectRef',
          props: {},
          on: {
            change: () => console.log(this),
          },
        },
      },
      {
        type: 'time-select',
        prop: 'time-select',
        label: 'time-select-label',
        vuemap: {
          ref: 'timeSelectRef',
          props: {
            placeholder: 'time-select-placeholder',
          },
        },
      },
      {
        type: 'time-picker',
        prop: 'time-picker',
        label: 'time-picker-label',
        vuemap: {
          ref: 'timePickertRef',
          props: {
            placeholder: 'time-picker-placeholder',
          },
        },
      },
      {
        type: 'date-picker',
        prop: 'date',
        label: 'date-label',
        vuemap: {
          ref: 'dateRef',
          props: {
            placeholder: 'date-placeholder',
          },
        },
      },
      {
        type: 'date',
        prop: ['startTime|endTime', [], '|', []], // 分割符
        label: 'datetimerange-label',
        vuemap: {
          ref: 'dateTimeRangeRef',
          props: {
            type: 'datetimerange',
            placeholder: 'date-placeholder',
          },
        },
      },
      {
        type: 'selfType',
        prop: 'selfType', // 分割符
        label: 'selfType-label',
        vuemap: {
          ref: 'dateTimeRangeRef',
          props: {
            type: 'datetimerange',
            placeholder: 'date-placeholder',
          },
        },
      },
      {
        render: h => (
          <div
            ref="refRef"
            id="render-self"
            vOn:click={() => {
              this.hide = !this.hide;
            }}
          >
            render-self
          </div>
        ),
      },
    ];
  };
}
