export default function(isAdd) {
  const obj = {
    static: {
      colConfig: [
        {
          prop: 'problemTypeName',
          label: '分类名称',
          'min-width': '200',
        },
      ],
    },
    reactive: {
      // 页面loading
      wrapLoading: false,
      selectedItem: [],
      tableLoading: false,
      tableData: [],
      tableSortParam: {
        sortName: '',
        sortType: '',
      },
      tableConfig: {
        sortable: false,
        props: {
          emptyText: '暂无数据',
          'row-class-name': 'click-ignore',
        },
        events: Object.freeze({
          'selection-change': selectedItem => {
            this.selectedItem = selectedItem;
          },
        })
      },
    },
  };

  if (isAdd) {
    obj.static.colConfig.push({
      label: '操作',
      'min-width': '100',
      align: 'center',
      render: h => config => {
        return (
          <div class='er-table-operatecol'>
            <el-button
              size='small'
              type='text'
              vOn:click={() => {
                this.showVisible(true, config.row)
                // this.handleSee(config.row);
              }}
            >
              修改
            </el-button>
            <el-button
              size='small'
              type='text'
              slot='reference'
              vOn:click={() => {
                this.deleteFn(config.row.id)
                // this.showModal(true, config.row.id)
                // this.handleSee(config.row);
              }}
            >
              删除
            </el-button>
            <el-button
              size='small'
              type='text'
              vOn:click={() => {
                this.typeFn(config.row)
                // this.showModal(true, config.row.id)
                // this.handleSee(config.row);
              }}
            >
              {
                config.row.isDelete === 'Y' ?
                '启用' : '禁用'
              }
            </el-button>
          </div>
        );
      },
    });
  }
  Object.assign(this, obj.static);
  return obj.reactive;
}
