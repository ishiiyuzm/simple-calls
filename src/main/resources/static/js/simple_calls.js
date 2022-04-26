    /*
    * Vueで実装
    */

    // 発信ボタン
    Vue.createApp({
        data:function() {
            return {
                disableFlag: false
            };
        },
        methods: {
            onclick: function(){
                // TODO 発信イベント呼び出し
            }
        }
    }).mount('#make-call');

    // 切断ボタン
    Vue.createApp({
        data:function() {
            return {
                disableFlag: true
            };
        },
        methods: {
            onclick: function(){
                // TODO 切断イベント呼び出し
            }
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