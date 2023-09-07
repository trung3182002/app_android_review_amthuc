package com.example.apprivew.moder;

public class Item_NguoiDung {
    String idUser, ten, matKhau, sdt;
    String avata;
    String ngaySinh;
    String userName;
    String diaChi;

    public Item_NguoiDung() {
    }

    public Item_NguoiDung(String idUser, String ten, String matKhau, String sdt, String avata, String ngaySinh, String userName, String diaChi) {
        this.idUser = idUser;
        this.ten = ten;
        this.matKhau = matKhau;
        this.sdt = sdt;
        this.avata = avata;
        this.ngaySinh = ngaySinh;
        this.userName = userName;
        this.diaChi = diaChi;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
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

    public String getAvata() {
        return avata;
    }

    public void setAvata(String avata) {
        this.avata = avata;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
