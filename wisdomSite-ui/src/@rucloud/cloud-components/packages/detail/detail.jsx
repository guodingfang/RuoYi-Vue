import './detail.scss';

const name = 'RcDetail';

const RcDetailFn = config => ({
  name,
  props: {
    description: {
      type: Object,
      default() {
        return {
          src: '',
          title: '',
        };
      },
    },
  },
  render() {
    const { description, $slots } = this;
    return (
      <div class="er-right-pop">
        <div class="er-right-pop__header">
          <div class="er-right-pop-des">
            {/* <div class="er-right-pop-des__icon">
              <svg-icon icon-class={description.icon}></svg-icon>
            </div> */}
            <p class="er-right-pop-des__title">{description.title}</p>
            {/* <div class="des-wrap">
              <p class="er-right-pop-des__title">{description.title}</p>
              <p class="er-right-pop-des__label">{$slots.label}</p>
            </div> */}
          </div>
          <i
            class="el-icon-close"
            vOn:click={() => {
              this.$emit('on-close');
            }}
          ></i>
          <div class="er-right-pop-operate">{$slots.operate}</div>
        </div>
        <div class="er-right-pop__content">{$slots.default}</div>
      </div>
    );
  },
})

function RcDetail(config) {
  return RcDetailFn(config);
}
RcDetail.cname = name;

export default RcDetail;