const { override, addWebpackAlias } = require('customize-cra');
const path = require('path');

module.exports = override(
    addWebpackAlias({
        '@components': path.resolve(__dirname, 'src/components'),
        '@styles': path.resolve(__dirname, 'src/styles'),
        '@src': path.resolve(__dirname, 'src/'),
        '@testJson': path.resolve(__dirname, 'src/testJson')
    })
);