import type from './state';

export default {
  type: { ...type },
  pre: ['inventoryState'],
};
