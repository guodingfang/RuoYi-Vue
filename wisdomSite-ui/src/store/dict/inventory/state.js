
export default {

  inventoryState: {
    key: 'key',
    value: 'value',
    refresh: {
      fetch: () =>
        Promise.resolve({
          data: [
            {
              key: 1,
              value: '自由态',
            },
            {
              key: 3,
              value: '签字完成',
            },
          ],
        }),
    },
  },
};
