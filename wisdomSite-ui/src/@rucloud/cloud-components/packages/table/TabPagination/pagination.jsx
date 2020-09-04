import './pagination.scss';

export default {
  name: 'ErPagination',
  props: {
    propsPag: {
      type: Object,
      default() {
        return {};
      },
    },
    eventsPag: {
      type: Object,
      default() {
        return {};
      },
    },
  },
  render() {
    const { propsPag, eventsPag } = this;
    return (
      <div class="common-pagination">
        <el-pagination {...{ props: propsPag, on: eventsPag }} />
      </div>
    );
  },
};
