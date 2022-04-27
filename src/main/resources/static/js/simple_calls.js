    /*
    * Vueで実装
    */

    // カメラ映像取得
    navigator.mediaDevices.getUserMedia({video: true, audio: true})
        .then( stream => {
        // 成功時にvideo要素にカメラ映像をセットし、再生
        const videoElm = document.getElementById('my-video');
        videoElm.srcObject = stream;
        videoElm.play();
        // 着信時に相手にカメラ映像を返せるように、グローバル変数に保存しておく
        localStream = stream;
    }).catch( error => {
        // 失敗時にはエラーログを出力
        console.error('mediaDevice.getUserMedia() error:', error);
        return;
    });

    //Peer作成
    const peer = new Peer({
        key: '34bf33a0-6c1b-4e98-ac78-9d29b997c7a4',
        debug: 3
    });

    //PeerID取得
    peer.on('open', () => {
        document.getElementById('my-id').textContent = peer.id;
    });

    // 発信ボタン
    Vue.createApp({
        data() {
            return {
                disableFlag: false
            };
        },
        methods: {
            // 発信ボタンのクリック時
            makeCallButtonOnClick() {
                this.connectInsertLog();
            },
            // 接続開始ログを登録
            connectInsertLog() {
                console.log('connect');
                // TODO ajaxで呼び出し
            },
        }
    }).mount('#make-call');

    // 切断ボタン
    Vue.createApp({
        data() {
            return {
                disableFlag: true
            };
        },
        methods: {
            // 切断ボタンのクリック時
            disConnectButtonOnClick() {
                this.disConnectUpdateLog();
            },
            // 切断情報を更新
            disConnectUpdateLog() {
                console.log('diconnect');
                // TODO ajaxで呼び出し
            },
        }
    }).mount('#disConnect');

    // 現在時刻(リアクティブ)
    Vue.createApp({
        data() {
            return {
                // 現在日時
                current: new Date()
            };
        },
        // 起動時にタイマーを設定
        created(){
            this.timer = setInterval(() => {
                // setIntervalメソッドを経由し1000ミリ秒間隔でcurrentプロパティの値を
                // 現在遅刻に書き換え
                this.current = new Date();
            }, 1000);        
        },
        // 終了前にタイマーを破棄
        beforeUnmount() {
            clearInterval(this.timer);
        },
        // renderTracked(e) {
        //     // renderTracked：ページへのマウント(描画)時発生
        //     console.log('renderTracked');
        //     console.log(e);
        // },
        // renderTriggered(e) {
        //     // renderTriggered：データの更新時発生
        //     console.log('renderTriggerd');
        //     console.log(e);
        // }
    }).mount('#nowDateTime');