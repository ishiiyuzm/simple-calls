package com.example.app.simple_calls;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domain.service.LogService;
import com.example.domain.model.LogEntity;
import com.example.domain.model.LogModel;

@RestController
public class SimpleCallsRestController {

    @Autowired
    LogService logService;

    @RequestMapping("/")
    public String sample() {
        return "Maven Spring Boot";
    }

    /**
     * 接続開始ログを登録
     * @param logModel
     * @return
     */
    @PostMapping("/ConnectInsertLog")
    @ResponseBody
    public LogModel connectInsertLog(@RequestBody LogModel logModel) {
        //コンソールにjsonの値を表示
        String peerId = logModel.getPeer_id();
        String toPeerId = logModel.getTopeer_id();

        if (peerId == null || peerId == "") {
            peerId = "peerId Is Empty";
        }

        if (toPeerId == null || toPeerId == "") {
            toPeerId = "TopeerId IsEmpty";
        }

        LogEntity logEntity = new LogEntity();
        // 現在時刻を取得
        var sysDateTime = new Timestamp(System.currentTimeMillis());

        logEntity.setPeer_id(peerId);                   // 自分PeerId
        logEntity.setTopeer_id(toPeerId);               // 相手PeerId
        logEntity.setConnect_id(peerId);                // 接続者ID
        logEntity.setConnect_datetime(sysDateTime);     // 接続開始時間

        try {

            // ログ登録処理
            logService.insert(logEntity);
        
        } catch (Exception e) {
            
        }

        //オブジェクトを画面に返す(現在特に使用はしてない)
        return logModel;
    }

    /**
     * 切断情報を更新
     * @param logModel
     * @return
     */
    @PostMapping("/DisConnectUpdateLog")
    @ResponseBody
    public LogModel dicConnectUpdateLog(@RequestBody LogModel logModel) {
        //コンソールにjsonの値を表示
        String peerId = logModel.getPeer_id();
        String toPeerId = logModel.getTopeer_id();

        LogEntity logEntity = new LogEntity();
        // 現在時刻を取得
        var sysDateTime = new Timestamp(System.currentTimeMillis());

        logEntity.setPeer_id(peerId);                   // 自分PeerId
        logEntity.setTopeer_id(toPeerId);               // 相手PeerId
        logEntity.setDisconnect_id(peerId);             // 切断者ID
        logEntity.setDisconnect_datetime(sysDateTime);  // 切断日時

        try {

            // 切断情報を更新
            logService.updateDisConnect(logEntity);
        
        } catch (Exception e) {
            
        }

        //オブジェクトを画面に返す(現在特に使用はしてない)
        return logModel;
    }

}
