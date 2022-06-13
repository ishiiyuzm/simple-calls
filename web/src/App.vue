<template>
  <div>
    <HeaderInfo/>
    <VidepInfo/>
  </div>
</template>

<script>
import HeaderInfo from './components/HeaderInfo.vue';
import VidepInfo from './components/VideoInfo.vue'

export default {
  name: 'App',
  components: {
    VidepInfo,
    HeaderInfo,
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  /*margin-top: 60px;*/
}   
</style>
