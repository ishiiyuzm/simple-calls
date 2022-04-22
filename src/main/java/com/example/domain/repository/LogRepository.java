package com.example.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.domain.model.LogEntity;

/**
 * Entityと主キーの型をジェネリクスに指定したJpaRepositoryを継承する
 * Entity：LogEntity、主キーの型：Integer
 * 
 * Spring Data JPAで提供されているAPIではできない処理を記述する
 * 
 * ※カスタムメソッドを実装したクラスのインターフェースを指定することで、カスタムメソッドを追加可能
 */
@Repository
public interface LogRepository extends JpaRepository<LogEntity, Integer> {}
