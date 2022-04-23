package com.example.domain.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @Entity：Entityクラスであることを宣言する
 * @Table：name属性で連携するテーブル名を指定する
 */
@Data
@Entity
@Table(name="tlog")
public class LogEntity {

    /**
     *  @Id：主キーに指定する。※複合キーの場合は@EmbeddedIdを使用
     *  @GeneratedValue：主キーの指定をJPAに委ねる
     *  @Column：name属性でマッピングするカラム名を指定する
     */
    
    // シーケンス番号
    @Id
    @SequenceGenerator(name = "tlog_seq_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private int seq;

    // Peer_Id
    @Column(name = "peer_id")
    private String peer_id;

    // ToPeer_id
    @Column(name = "topeer_id")
    private String topeer_id;

    // 接続日時
    @Column(name = "connect_datetime")
    private Timestamp connect_datetime;

    // 接続終了日時
    @Column(name = "disconnect_datetime")
    private Timestamp disconnect_datetime;

    public int getSeq(){
        return this.seq;
    }
    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getPeer_id(){
        return this.peer_id;
    }
    public void setPeer_id(String peer_id) {
        this.peer_id = peer_id;
    }

    public String getTopeer_id(){
        return this.topeer_id;
    }
    public void setTopeer_id(String topeer_id) {
        this.topeer_id = topeer_id;
    }

    public Timestamp getConnect_datetime(){
        return this.connect_datetime;
    }
    public void setConnect_datetime(Timestamp connect_datetime){
        this.connect_datetime = connect_datetime;
    }

    public Timestamp getDisConnect_datetime(){
        return this.disconnect_datetime;
    }
    public void setDisconnect_datetime(Timestamp disconnect_datetime){
        this.disconnect_datetime = disconnect_datetime;
    }
}
