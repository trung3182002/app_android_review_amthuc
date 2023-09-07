package com.example.apprivew.moder;

public class ItemObject {
    private String name;
    private String hinh;

    public ItemObject(String name, String hinh) {
        this.name = name;
        this.hinh = hinh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }
}
