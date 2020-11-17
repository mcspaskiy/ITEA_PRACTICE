package com.mcspaskiy.model;

import java.time.LocalDateTime;

public class AppKey {
    private int id;
    private String key;
    private LocalDateTime createdDate;

    public AppKey() {
    }

    public AppKey(String key, LocalDateTime createdDate) {
        this.key = key;
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
