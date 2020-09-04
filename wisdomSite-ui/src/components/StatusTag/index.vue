<script>
  import _ from "lodash";
  const statusMap = {
    'warning': ['待整改', '待验收', '一般', '无',  ''],
    'info': ['待验证'],
    "error": ['严重', '不合格', '锁定' ],
    "success": ['合格', '已完成', '启用']
  };
  export default {
    name: 'StatusTag',
    props: {
      status: {
        type: String,
        default: '无'
      },
    },
    computed: {
      tagType() {
        return _.findKey(statusMap, (o) => o.indexOf(this.status) !== -1);
      }
    },
    render(h) {
      const { status, tagType } = this;
      const classes = [
        'status-tag',
        `${tagType}`
      ];
      const tagEl = (
        <span class={ classes } >{ status }</span>
    );
      return tagEl
    }
  };
</script>

<style scoped lang="scss">
  .status-tag{
    color: #ffffff;
    border-radius: 5px;
    overflow: hidden;
    padding:2px 2px;
    &.warning{
      background-color: #ff6600;
    }
    &.error{
      background-color: #cc0000;
    }
    &.success{
      background-color: #008000;
    }
    &.info{
      background-color: #FF9900;
    }
  }
</style>
