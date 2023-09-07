package com.example.apprivew.moder;

public class Item_BinhLuan {
    String noiDung, ngayBinhLuan, tenNguoiDung;
    String avataNguoiDung;

    public Item_BinhLuan(String noiDung, String ngayBinhLuan, String tenNguoiDung, String avataNguoiDung) {
        this.noiDung = noiDung;
        this.ngayBinhLuan = ngayBinhLuan;
        this.tenNguoiDung = tenNguoiDung;
        this.avataNguoiDung = avataNguoiDung;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public String getAvataNguoiDung() {
        return avataNguoiDung;
    }

    public void setAvataNguoiDung(String avataNguoiDung) {
        this.avataNguoiDung = avataNguoiDung;
    }



    public Item_BinhLuan() {
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getNgayBinhLuan() {
        return ngayBinhLuan;
    }

    public void setNgayBinhLuan(String ngayBinhLuan) {
        this.ngayBinhLuan = ngayBinhLuan;
    }
}
