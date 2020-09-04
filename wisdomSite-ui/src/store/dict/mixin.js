export default function() {
  const options = this.$options;
  // store injection
  if (options.DStore) {
    this.$DStore =
      typeof options.DStore === 'function' ? options.DStore() : options.DStore;
  } else if (options.parent && options.parent.$DStore) {
    this.$DStore = options.parent.$DStore;
  }
}
