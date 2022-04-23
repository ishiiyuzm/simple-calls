package com.example.domain.model;

public class LogModel {

    private String peer_id;

    private String topeer_id;

    private String create_id;

    public String getPeer_id() {
        return this.peer_id;
    }
    public void setPeer_id(String peer_id) {
        this.peer_id = peer_id;
    }

    public String getTopeer_id() {
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
}
