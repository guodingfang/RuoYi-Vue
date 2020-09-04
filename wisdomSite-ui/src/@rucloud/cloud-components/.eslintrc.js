module.exports = {
  root: true,
  env: {
    node: true,
  },
  extends: ['plugin:vue/essential'],
  rules: {
    'no-shadow': 0,
    'max-len': 0,
    'object-curly-newline': 0,
    'import/no-extraneous-dependencies': 0,
    'import/no-unresolved': 0,
    'import/extensions': [
      'off',
      'always',
      {
        js: 'never',
        vue: 'never',
      },
    ],
    'no-console': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    // allow paren-less arrow functions
    'arrow-parens': 0,
    // allow async-await
    'generator-star-spacing': 0,
    'eol-last': 0,
    semi: 0,
    'no-extra-semi': 1,
    'no-plusplus': 0,
    'comma-dangle': [2, 'never'],
    'space-before-function-paren': 0,
    // 'vue/html-self-closing': 0,
    'vue/html-self-closing': [
      'error',
      {
        html: {
          void: 'always',
          normal: 'always',
          component: 'always',
        },
        svg: 'always',
        math: 'always',
      },
    ],
    'vue/max-attributes-per-line': [
      'error',
      {
        singleline: 6,
        multiline: {
          max: 1,
          allowFirstLine: false,
        },
      },
    ],
    // 'vue/singleline-html-element-content-newline': 0,
    'comma-dangle': 'off',
    'linebreak-style': 'off', // lf  crlf
    'no-param-reassign': ['error', { props: false }],
    camelcase: 'off',
  },
  parserOptions: {
    parser: 'babel-eslint',
  },
  overrides: [
    {
      files: [
        '**/__tests__/*.{j,t}s?(x)',
        '**/tests/unit/**/*.spec.{j,t}s?(x)',
      ],
      env: {
        jest: true,
      },
    },
  ],
};
