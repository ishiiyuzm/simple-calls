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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "seq")
    private int seq;

    // Peer_Id
    @Column(name = "peer_id")
    private String peer_id;

    // ToPeer_id
    @Column(name = "topeer_id")
    private String topeer_id;

    // 作成者
    @Column(name = "create_id")
    private String create_id;

    // 作成日時
    @Column(name = "create_date")
    private Timestamp create_date;

    // 更新者
    @Column(name = "update_id")
    private String update_id;

    // 更新日時
    @Column(name = "update_date")
    private Timestamp update_date;

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

    public String getCreate_id(){
        return this.create_id;
    }
    public void setCreate_id(String create_id) {
        this.create_id = create_id;
    }

    public Timestamp getCreate_date(){
        return this.create_date;
    }
    public void setCreate_date(Timestamp create_date){
        this.create_date = create_date;
    }

    public String getUpdate_id(){
        return this.update_id;
    }
    public void setUpdate_id(String update_id) {
        this.update_id = update_id;
    }

    public Timestamp getUpdate_date(){
        return this.update_date;
    }
    public void setUpdate_date(Timestamp update_date){
        this.update_date = update_date;
    }
}
