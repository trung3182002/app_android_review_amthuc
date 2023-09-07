package com.example.apprivew.moder;

import java.io.Serializable;
import java.util.List;

public class Item_BaiDang implements Serializable {

    String idPost, tieuDe, moTa, ngayDang, userId, tenNguoiDung, avata, diemDanhGia;
    int soLuongLike;
    List<String> imgBaiDang;
    List<String> diaChi;

    public String getDiemDanhGia() {
        return diemDanhGia;
    }

    public void setDiemDanhGia(String diemDanhGia) {
        this.diemDanhGia = diemDanhGia;
    }

    public Item_BaiDang(String idPost, String tieuDe, String moTa, String ngayDang, String userId, String tenNguoiDung, String avata, String diemDanhGia, int soLuongLike, List<String> imgBaiDang, List<String> diaChi) {
        this.idPost = idPost;
        this.tieuDe = tieuDe;
        this.moTa = moTa;
        this.ngayDang = ngayDang;
        this.userId = userId;
        this.tenNguoiDung = tenNguoiDung;
        this.avata = avata;
        this.diemDanhGia = diemDanhGia;
        this.soLuongLike = soLuongLike;
        this.imgBaiDang = imgBaiDang;
        this.diaChi = diaChi;
    }

    public String getAvata() {
        return avata;
    }

    public void setAvata(String avata) {
        this.avata = avata;
    }

    public Item_BaiDang() {
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }
    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getNgayDang() {
        return ngayDang;
    }

    public void setNgayDang(String ngayDang) {
        this.ngayDang = ngayDang;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getSoLuongLike() {
        return soLuongLike;
    }

    public void setSoLuongLike(int soLuongLike) {
        this.soLuongLike = soLuongLike;
    }

    public List<String> getImgBaiDang() {
        return imgBaiDang;
    }

    public void setImgBaiDang(List<String> imgBaiDang) {
        this.imgBaiDang = imgBaiDang;
    }

    public List<String> getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(List<String> diaChi) {
        this.diaChi = diaChi;
    }
}
