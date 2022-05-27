<template>
  <div class="container videoInfo">
    <div class="row">
      <div class="col-md-6">
        <video id="my-video" muted="true" width="650" autoplay playsinline></video>
      </div>
      <div class="col-md-6">
        <video id="their-video" width="650" autoplay playsinline></video>
      </div>
    </div>
    <div class="row">
      <div class="col-md-12">
        <div style="text-align:left">
          マイク:
          <select v-model="selectedAudio" @change="onChange">
            <option disabled value="">マイクを選択してください</option>
            <option v-for="(audio, key, index) in audios" v-bind:key="index" :value="audio.value">
              {{ audio.text }}
            </option>
          </select>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-md-12">
        <div style="text-align:left">
          カメラ: 
          <select v-model="selectedVideo" @change="onChange">
            <option disabled value="">カメラを選択してください</option>
            <option v-for="(video, key, index) in videos" v-bind:key="index" :value="video.value">
              {{ video.text }}
            </option>
          </select>
        </div>
      </div>
    </div>
    <p>あなたの PeerID: <span id="my-id">{{peerId}}</span></p>
    <input v-model="topeerId" placeholder="相手のPeerId">
    &nbsp;
    <button @click="makeCall" class="button--green btn btn-success">発信</button>
    &nbsp;
    <button @click="disConnect" class="button--green btn btn-danger">切断</button>
  </div>
</template>

<script>
  import Peer from 'skyway-js';
  import axios from 'axios';

  // Cors対策で以下を有効化
  axios.defaults.baseURL = 'http://localhost:8080';
  axios.defaults.headers.post['Content-Type'] = 'application/json;charset=utf-8';
  // axios.defaults.headers.post['Access-Control-Allow-Origin'] = '*';

  export default {
    name: 'VidepInfo',
    data() {
      return{
        audios: [],
        videos: [],
        selectedAudio: '',
        selectedVideo: '',  
        peerId: '',
        topeerId: '',
        localStream: {}
      }
    },
    mounted: async function () {

      // APIKey
      const APIKEY = '34bf33a0-6c1b-4e98-ac78-9d29b997c7a4';
      
      this.peer = new Peer({key: APIKEY, debug: 3}); //新規にPeerオブジェクトの作成
      this.peer.on('open', () => this.peerId = this.peer.id); //PeerIDを反映

      this.peer.on('call', call => {
          call.answer(this.localStream);
          this.connect(call);
      });

      //デバイスへのアクセス
      const deviceInfos = await navigator.mediaDevices.enumerateDevices();

      //1. オーディオデバイスの情報を取得
      deviceInfos
      .filter(deviceInfo => deviceInfo.kind === 'audioinput')
      .map(audio => this.audios.push({text: audio.label || `Microphone ${this.audios.length + 1}`, value: audio.deviceId}));

      //2. カメラの情報を取得
      deviceInfos
      .filter(deviceInfo => deviceInfo.kind === 'videoinput')
      .map(video => this.videos.push({text: video.label || `Camera  ${this.videos.length - 1}`, value: video.deviceId}));

      //console.log(this.audios, this.videos); 
    },
    methods: {
      onChange: function (){
          if(this.selectedAudio != '' && this.selectedVideo != ''){
              this.connectLocalCamera();
          }
      },
      connectLocalCamera: async function (){
          const constraints = {
              audio: this.selectedAudio ? { deviceId: { exact: this.selectedAudio } } : false,
              video: this.selectedVideo ? { deviceId: { exact: this.selectedVideo } } : false
          }

          const stream = await navigator.mediaDevices.getUserMedia(constraints);
          document.getElementById('my-video').srcObject = stream;

          this.localStream = stream;
      },
      makeCall: function(){
        // 接続開始ログを登録
        this.connectInsertLog();
        const call = this.peer.call(this.topeerId, this.localStream);
        // 接続処理
        this.connect(call);
      },
      connect: function(call){
        call.on('stream', stream => {
            const el = document.getElementById('their-video');
            el.srcObject = stream;
            el.play();
        });
      },
      connectInsertLog: function(){

        const model = {
            peer_id : this.peerId,
            topeer_id : this.topeerId
        };

        axios.post("/ConnectInsertLog", JSON.stringify(model))
          .then((res) => {
             console.log(res);
             this.posts = res.data.posts;
            //console.log('成功');
          })
          .catch((err) => {
            console.log(err);
            //console.log('失敗');
          });
      },
      disConnect: function() {
        // 切断情報を更新
        this.disConnectUpdateLog();
        this.peer.on('close', () => {
          alert('通信を切断しました。');
        });
      },
      disConnectUpdateLog: function() {

        const model = {
            peer_id : this.peerId,
            topeer_id : this.topeerId
        };

        axios.post("/DisConnectUpdateLog", JSON.stringify(model))
          .then((res) => {
             console.log(res);
             this.posts = res.data.posts;
            //console.log('成功');
          })
          .catch((err) => {
            console.log(err);
            //console.log('失敗');
          });

      }
    }
  }
</script>

<style scoped>
</style>
