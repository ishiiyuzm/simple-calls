<template>
  <div>
    <div id="app">
    </div>
  </div>
</template>

<script>
  import Vue from 'vue'
  const app = new Vue({
      el: '#app',
      data: function() {
        return {
          audios: [],
          videos: []
        };
      },
      methods: {

      },
      mounted: async function () {
        //デバイスへのアクセス
        const deviceInfos = await navigator.mediaDevices.enumerateDevices();
        console.log(deviceInfos);

        //1. オーディオデバイスの情報を取得
        deviceInfos
        .filter(deviceInfo => deviceInfo.kind === 'audioinput')
        .map(audio => this.audios.push({text: audio.label || `Microphone ${this.audios.length + 1}`, value: audio.deviceId}));

        //2. カメラの情報を取得
        deviceInfos
        .filter(deviceInfo => deviceInfo.kind === 'videoinput')
        .map(video => this.videos.push({text: video.label || `Camera  ${this.videos.length - 1}`, value: video.deviceId}));

        console.log(this.audios, this.videos); 
      }
  })

  app.mount('#app');
</script>

<style>

</style>
