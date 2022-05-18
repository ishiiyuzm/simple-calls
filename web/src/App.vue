<template>
  <div>
    <video autoplay :srcObject.prop="stream"></video>
  </div>
</template>

<script>
export default {
  name: 'app',
  data(){
    return {
      stream: undefined
    }
  },
  async created() {
    this.stream = await navigator.mediaDevices.getUserMedia({
      video: true,
      audio: true
    })
  }
}
</script>