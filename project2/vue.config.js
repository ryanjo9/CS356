module.exports = {
  devServer: {
    proxy: {
      '^/api': {
        target: 'http://10.37.114.221:3000',
      },
    }
  }
}