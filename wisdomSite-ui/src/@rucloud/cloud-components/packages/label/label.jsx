import './label.scss';

const name = 'RcLabel';

const RcLabelFn = config => ({
  name,
  props: {
    dataIn: {
      type: Array,
      default: () => [],
    },
  },
  render() {
    const { dataIn } = this;
    return (
      <ul class="er-com-label">
        {dataIn.length &&
          dataIn.map(option => {
            const value = option;
            // isSisNooptatic 占位符 什么都没有
            const { styleTitle = {}, styleContent = {}, render, isNoop, isHide } = value;
            if (isHide) {
              return '';
            }
            const leftLabel = value.renderTitle ? value.renderTitle(h) : value.key;
            const rightLabel = value.renderContent ? value.renderContent(h) : value.value;
            let node = isNoop ? <li class="er-com-label__item" key={value.key}></li> : '';
            const formateEmpty = name => {
              return [undefined, null].includes(name) ? '--' : name;
            };
            if (!isNoop) {
              if (render) {
                node = render(h);
              } else {
                node = (
                  <li class="er-com-label__item" key={value.key}>
                    <div class="er-com-label__item--wrap">
                      <span class="er-com-label__item--left" style={{ ...styleTitle }}>
                        {formateEmpty(leftLabel)}
                      </span>
                      {/* <el-divider direction="vertical"></el-divider> */}
                      <span class="er-com-label__item--right" style={{ ...styleContent }}>
                        {formateEmpty(rightLabel)}
                      </span>
                    </div>
                  </li>
                );
              }
            }
            return node;
          })}
      </ul>
    );
  },
});

function RcLabel(config) {
  return RcLabelFn(config);
}
RcLabel.cname = name;

export default RcLabel;