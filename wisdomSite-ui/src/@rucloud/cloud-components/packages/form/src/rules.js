const trimValid = (rule, value, callback) => {
  const regStart = /^\s/gi;
  const regEnd = /\s$/gi;
  if (regStart.test(value)) {
    callback(new Error('头部不允许出现空格'));
  } else if (regEnd.test(value)) {
    callback(new Error('尾部不允许出现空格'));
  } else {
    callback();
  }
};

export default {
  trimValid(options = {}) {
    return {
      validator: trimValid,
      trigger: 'blur',
      ...options,
    };
  },
};
