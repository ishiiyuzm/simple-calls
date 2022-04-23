package com.example.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.model.LogEntity;
import com.example.domain.repository.LogRepository;

/**
 * @Service：ビジネスロジックを提供するコンポーネントであることを宣言する。
 * ※データ永続の処理は@Repositoryを付与したコンポーネントで行う。
 */
@Service
public class LogService {

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

}
