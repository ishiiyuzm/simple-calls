const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true
})
module.exports = {
  devServer: {
    proxy: {
      "/api/": {
        target:  process.env.USE_LOCAL_SERVER ? 'http://localhost:8080' : 'http://aaa.com',
      }
    }
  }
};