import { get } from '@rucloud/cloud-share/dist/utils';
import './tab.scss';

const name = 'RcTab';

const RcTabFn = config => ({
  name,

  props: {
    config: {
      type: Array,
      default: () => [],
    },
  },

  data() {
    return {
      curTab: {},
      nextTab: {},
      sequence: [],
      contentLoading: false,
    };
  },

  watch: {
    config() {
      this.init();
    },
  },

  created() {
    this.init();
  },

  methods: {
    init() {
      const activeInit = get(
        this.config.filter(i => i.active),
        '0',
        {}
      );
      this.handleTabClick(activeInit);
    },
    setContentLoading(flag) {
      this.contentLoading = flag;
    },
    handleTabClick(tab) {
      if (tab.clickFn) {
        this.setContentLoading(true);
        tab.clickFn({
          setTab: () => {
            this.curTab = tab;
          },
          closeLoading: () => {
            this.setContentLoading(false);
          },
          param: [this.curTab, tab],
        });
      } else {
        this.curTab = tab;
      }
    },
  },

  render() {
    const { config, $scopedSlots, $slots, curTab, contentLoading } = this;
    const renderContentFn = $scopedSlots.default ? $scopedSlots.default : () => '';
    return (
      <div class="rc-tab">
        <div class="rc-tab__header">
          {config.map(tab => {
            return (
              <div
                key={tab.title}
                class={{
                  'rc-tab__item': true,
                  active: curTab.title === tab.title,
                  disabled: contentLoading,
                }}
                vOn:click={() => {
                  if (curTab.title === tab.title || contentLoading) return;
                  // 传递 callBack
                  this.handleTabClick(tab);
                }}
              >
                {tab.title}
              </div>
            );
          })}
        </div>
        <div class="rc-tab__content" vLoading={contentLoading}>
          {[renderContentFn({ curTab }), $slots.default]}
        </div>
      </div>
    );
  },
});

function RcTab(config) {
  return RcTabFn(config);
}

RcTab.cname = name;

export default RcTab;
