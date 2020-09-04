import Vue from 'vue';
import vuescroll from 'vuescroll';

Vue.use(vuescroll, {
  ops: {
    bar: {
      background: '#000',
      opacity: '.6',
    },
  },
});

export const scrollConfig = {
  collapse: {
    bar: { background: '#ccc', size: '8px', onlyShowBarOnScroll: false },
    scrollPanel: {
      scrollingY: false,
    },
  },
};
