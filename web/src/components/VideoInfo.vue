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
  // 開発用
  //axios.defaults.baseURL = 'http://localhost:8080';
  // 本番用 
  axios.defaults.baseURL = 'https://www.simplecalls.ml';
  axios.defaults.headers.post['Content-Type'] = 'application/json;charset=utf-8';
  // axios.defaults.headers.post['Access-Control-Allow-Origin'] = '*';

  export default {
    name: 'VidepInfo',
    data() {
      return{
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

      // カメラ映像取得
      navigator.mediaDevices.getUserMedia({video: true, audio: true})
          .then( stream => {
          // 成功時にvideo要素にカメラ映像をセットし、再生
          const videoElm = document.getElementById('my-video');
          videoElm.srcObject = stream;
          videoElm.play();
          // 着信時に相手にカメラ映像を返せるように、グローバル変数に保存しておく
          this.localStream = stream;
      }).catch( error => {
          // 失敗時にはエラーログを出力
          console.error('mediaDevice.getUserMedia() error:', error);
          return;
      });
    },
    methods: {
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
             console.log('成功');
          })
          .catch((err) => {
            console.log(err);
            console.log('失敗');
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
