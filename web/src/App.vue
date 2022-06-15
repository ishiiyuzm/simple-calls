<template>
  <div>
    <HeaderInfo/>
    <VidepInfo/>
    <FooterInfo/>
  </div>
</template>

<script>
import HeaderInfo from './components/HeaderInfo.vue';
import VidepInfo from './components/VideoInfo.vue'
import FooterInfo from './components/FooterInfo.vue';

export default {
  name: 'App',
  components: {
    VidepInfo,
    HeaderInfo,
    FooterInfo,
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
