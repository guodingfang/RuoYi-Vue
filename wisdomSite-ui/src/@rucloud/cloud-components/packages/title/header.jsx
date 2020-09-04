import './header.scss';

const name = 'RcTitle';

const RcTitleFn = config => ({
  name,
  render() {
    const { $slots } = this;
    return (
      <header class="er-title top">
        <span class="er-title__icon"></span>
        {$slots.default}
      </header>
    );
  },
});

function RcTitle(config) {
  return RcTitleFn(config);
}
RcTitle.cname = name;

export default RcTitle;