<template>
  <div class="container videoInfo">
    <div class="row">
      <div class="col-md-6" style="margin-top:5%">
        <!-- mutedはハウリング防止の為、設定-->
        <video id="my-video" muted="true" width="650" height="500" autoplay playsinline controls></video>
      </div>
      <div class="col-md-6" style="margin-top:5%">
        <video id="their-video" width="650" height="500" autoplay playsinline controls></video>
      </div>
    </div>
    <p>あなたの PeerId: 
      <span id="my-id" style="font-size: 10pt; font-style:italic">{{peerId}}</span>
      &nbsp;
      <button @click="copyToClipboard(peerId)" class="btn btn-light">コピー</button>
    </p>
    <input v-model="topeerId" placeholder="相手のPeerID">
    &nbsp;
    <button v-bind:disabled="isCallButtonDisabled" @click="makeCall" class="btn btn-success">発信</button>
    &nbsp;
    <button v-bind:disabled="isDisconnectButtonDisabled" @click="disConnect" class="btn btn-danger">切断</button>
    &nbsp;
    <button v-bind:disabled="isScreenShareButtonDisabled" @click="screenShare" class="btn btn-primary">画面共有{{screenShareSwitch}}</button>
  </div>
</template>

<script>
  import Peer from 'skyway-js';
  import axios from 'axios';

  // Cors対策で以下を有効化
  // 開発用
  axios.defaults.baseURL = 'http://localhost:8080';
  // 本番用 
  //axios.defaults.baseURL = 'https://www.simplecalls.ml';
  axios.defaults.headers.post['Content-Type'] = 'application/json;charset=utf-8';
  // axios.defaults.headers.post['Access-Control-Allow-Origin'] = '*';

  export default {
    name: 'VidepInfo',
    data() {
      return{
        peerId: '',
        topeerId: '',
        isCallButtonDisabled: false,        // 発信ボタン無効フラグ
        isDisconnectButtonDisabled: true,   // 切断ボタン無効フラグ
        isScreenShareButtonDisabled: true,  // 画面共有ボタン無効フラグ
        screenShareSwitch: ' ON',
        localStream: {}
      }
    },
    mounted: async function () {
      // APIKey
      const APIKEY = '34bf33a0-6c1b-4e98-ac78-9d29b997c7a4';
      // 新規にPeerオブジェクトの作成
      this.peer = new Peer({key: APIKEY, debug: 3});
      // PeerIDを反映 
      this.peer.on('open', () => this.peerId = this.peer.id);
      this.peer.on('call', call => {
          call.answer(this.localStream);
          this.connect(call);
      });

      // カメラ映像取得
      navigator.mediaDevices.getUserMedia({video: true, audio: true})
          .then( stream => {
          // 成功時にvideo要素にカメラ映像をセットし、再生
          const myVideoElm = document.getElementById('my-video');
          myVideoElm.srcObject = stream;
          myVideoElm.play();
          // 着信時に相手にカメラ映像を返せるように、グローバル変数に保存しておく
          this.localStream = stream;

          const theirVideoElm = document.getElementById('their-video');
          theirVideoElm.play();

      }).catch( error => {
          // 失敗時にはエラーログを出力
          console.error('mediaDevice.getUserMedia() error:', error);
          return;
      });

    },
    methods: {
      // PeerIdをコピー
      copyToClipboard(text) {
          navigator.clipboard.writeText(text)
          .then(() => {
              alert('コピーしたお。\n通話したい相手にコピーしたPeerIDを渡してね。');
          })
          .catch(e => {
              console.error(e)
          })
      },
      // 発信ボタン押下後処理
      makeCall: function(){
        // 接続開始ログを登録
        this.connectInsertLog();
        const call = this.peer.call(this.topeerId, this.localStream);
        // 接続処理
        this.connect(call);
      },
      // 接続処理
      connect: function(call){
        call.on('stream', stream => {
            const theirVideoElm = document.getElementById('their-video');
            // 相手の映像を設定
            theirVideoElm.srcObject = stream;
            theirVideoElm.play();

            // 発信ボタン無効フラグをtrueへ更新
            this.isCallButtonDisabled = true;
            // 切断ボタン無効フラグをfalseへ更新
            this.isDisconnectButtonDisabled = false;
            // 画面共有ボタン無効フラグをfalseへ更新
            this.isScreenShareButtonDisabled = false;
        });
      },
      // ログ書き込み(登録処理)
      connectInsertLog: function(){
        const model = {
            peer_id : this.peerId,
            topeer_id : this.topeerId
        };
        // API呼び出し
        axios.post('/ConnectInsertLog', JSON.stringify(model))
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
      // 切断ボタン押下後処理
      disConnect: function() {
        this.disConnectUpdateLog();
        this.peer.destroy();
        this.peer.on('close', () => {
          alert('通信を切断しました。');
        });
        // 発信ボタン無効フラグをfalseへ更新
        this.isCallButtonDisabled = false;
        // 切断ボタン無効フラグをtrueへ更新
        this.isDisconnectButtonDisabled = true;
        // 画面共有ボタン無効フラグをtrueへ更新
        this.isScreenShareButtonDisabled = true;
      },
      // ログ書き込み(切断処理)
      disConnectUpdateLog: function() {
        const model = {
            peer_id : this.peerId,
            topeer_id : this.topeerId
        };
        // API呼び出し
        axios.post('/DisConnectUpdateLog', JSON.stringify(model))
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
      // 画面共有ボタン押下後処理
      screenShare: function() {
        var screenShareSwitch = this.screenShareSwitch;
        var localVideo = document.getElementById('my-video');
        var procMode = '0'; // 「0:ON処理、1:OFF処理」
        if (screenShareSwitch.includes('ON', 1)) {
          this.screenShareSwitch = ' OFF';
        } else {
          this.screenShareSwitch = ' ON';
          procMode = '1';
        }

        // ON処理
        if ('0' == procMode) {
          // 画面共有
          var mediaStreamConstraints = { video: true };
          navigator.mediaDevices.getDisplayMedia(mediaStreamConstraints)
            .then(stream => {
              localVideo.srcObject = stream;
              // 再度、相手に渡す為にstreamを格納
              this.localStream = stream;
              // 再度、接続する為に設定
              this.call = this.peer.call(this.topeerId, this.localStream);
              // 再度、接続処理
              this.connect(this.call);
            })
            .catch(
              console.log('えらーだお')
            );
        } else if ('1' == procMode) {
          // OFF処理
          // カメラ映像取得
          navigator.mediaDevices.getUserMedia({video: true, audio: true})
              .then( stream => {
              localVideo.srcObject = stream;
              // 再度、相手に渡す為にstreamを格納
              this.localStream = stream;
              // 再度、接続する為に設定
              this.call = this.peer.call(this.topeerId, this.localStream);
              // 再度、接続処理
              this.connect(this.call);
          }).catch(
            console.log('えらーだお')
          );
        }
      }
    }
  }
</script>

<style scoped>
</style>
