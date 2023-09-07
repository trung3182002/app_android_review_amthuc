package com.example.apprivew.moder;

public class Item_User {
    String ten, matKhau, sdt;
    public Item_User() {
    }

    public Item_User(String ten, String matKhau, String sdt) {
        this.ten = ten;
        this.matKhau = matKhau;
        this.sdt = sdt;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
