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

    @PostMapping("/InsertLog")
    @ResponseBody
    public LogModel InsertLog(@RequestBody LogModel logModel) {
        //コンソールにjsonの値を表示
        String peerId = logModel.getPeer_id();
        String toPeerId = logModel.getTopeer_id();

        if (peerId == null || peerId == "") {
            peerId = "abcdefghi";
        }

        if (toPeerId == null || toPeerId == "") {
            toPeerId = "abcdefghi";
        }

        LogEntity logEntity = new LogEntity();

        var sysDateTime = new Timestamp(System.currentTimeMillis());

        logEntity.setSeq(1);
        logEntity.setPeer_id(peerId);
        logEntity.setTopeer_id(toPeerId);
        logEntity.setCreate_id("SYSTEM");
        logEntity.setCreate_date(sysDateTime);
        logEntity.setUpdate_id("SYSTEM");
        logEntity.setUpdate_date(sysDateTime);

        try {

            // ログ登録処理
            logService.insert(logEntity);
        
        } catch (Exception e) {
            //TODO: handle exception
        }

        //オブジェクトを画面に返す(現在特に使用はしてない)
        return logModel;
    }
}
