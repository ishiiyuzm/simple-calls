package com.example.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.model.LogEntity;
import com.example.domain.repository.LogRepository;
import com.example.utils.StringUtil;

/**
 * @Service：ビジネスロジックを提供するコンポーネントであることを宣言する。
 * ※データ永続の処理は@Repositoryを付与したコンポーネントで行う。
 */
@Service
public class LogService {

    // コンストラクタ
    LogService() {

    }
    /**
     * コンテナに登録されているRepositoryコンポーネントをインジェクションしてます
     */
    @Autowired
    LogRepository logRepository;
    
    /**
     * レコードを全件取得する。
     * @return
     */
    public List<LogEntity> findAllLogData(){
      
      return logRepository.findAll();
    }

    /**
     * 全ユーザ取得
     */
    @Transactional(readOnly = true)
    public List<LogEntity> logList() {
        // Spring Data JPAで用意されているメソッド（API）を実行しています。
        return logRepository.findAll();
    }

    /**
     * 登録
     */
    public void insert(LogEntity logEntity) {

      logRepository.save(logEntity);
    
    }

    /**
     * 切断情報を更新
     */
    public void updateDisConnect(LogEntity logEntity) {

      var peerId = logEntity.getPeer_id();
      if (StringUtil.IsNullorEmpty(peerId)) {
          return;
      }
      var toPeerId = logEntity.getTopeer_id();
      if (StringUtil.IsNullorEmpty(toPeerId)) {
          return;
      }
      var disconnect_id = logEntity.getDisconnect_id();
      if (StringUtil.IsNullorEmpty(disconnect_id)) {
          return;
      }
      var disconnect_datetime = logEntity.getDisConnect_datetime();
      if (disconnect_datetime == null) {
          return;
      }

      logRepository.updateDisConnectLog(peerId, toPeerId, disconnect_id, disconnect_datetime);
      
    }

}
