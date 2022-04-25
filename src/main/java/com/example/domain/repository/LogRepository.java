package com.example.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

import com.example.domain.model.LogEntity;

/**
 * Entityと主キーの型をジェネリクスに指定したJpaRepositoryを継承する
 * Entity：LogEntity、主キーの型：Integer
 * 
 * Spring Data JPAで提供されているAPIではできない処理を記述する
 * 
 * ※カスタムメソッドを実装したクラスのインターフェースを指定することで、カスタムメソッドを追加可能
 */
@Transactional
@Repository
public interface LogRepository extends JpaRepository<LogEntity, Integer> {
    
    @Query("update LogEntity set disconnect_id = :disconnect_id, disconnect_datetime = :disconnect_datetime where peer_id = :peer_id and topeer_id = :topeer_id")
//    @Query("select * from tlog")
    @Modifying
    public int updateDisConnectLog(@Param("peer_id") String peer_id, @Param("topeer_id") String topeer_id, @Param("disconnect_id") String disconnect_id, @Param("disconnect_datetime") Timestamp disconnect_datetime);

}
