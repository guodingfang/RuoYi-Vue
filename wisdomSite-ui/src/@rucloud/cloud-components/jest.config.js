module.exports = {
  moduleFileExtensions: ['js', 'jsx', 'json', 'vue'],
  transform: {
    '^.+\\.vue$': 'vue-jest',
    '.+\\.(css|styl|less|sass|scss|svg|png|jpg|ttf|woff|woff2)$':
      'jest-transform-stub',
    '^.+\\.jsx?$': 'babel-jest',
  },
  moduleNameMapper: {
    '^@pkg/(.*)$': '<rootDir>/packages/$1',
  },
  snapshotSerializers: ['jest-serializer-vue'],
  testMatch: [
    '**/__tests__/**/*.spec.(js|jsx|ts|tsx)|**/__tests__/*.(js|jsx|ts|tsx)',
  ],
  collectCoverageFrom: ['utils/**/*.{js,vue}', 'packages/**/*.{js,jsx,vue}'],
  coverageDirectory: '<rootDir>/__tests__/unit/coverage',
  // 'collectCoverage': true,
  coverageReporters: ['lcov', 'text-summary'],
  testURL: 'http://localhost/',
};
