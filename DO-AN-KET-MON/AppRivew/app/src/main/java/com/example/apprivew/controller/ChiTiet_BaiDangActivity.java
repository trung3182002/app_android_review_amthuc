package com.example.apprivew.controller;

import static androidx.core.content.ContextCompat.checkSelfPermission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.apprivew.Adapter.BinhLuan_Adapter;
import com.example.apprivew.Adapter.Img_ChiTiet_Adapter;
import com.example.apprivew.Adapter.Slide_trangChu_Adapter;
import com.example.apprivew.Lop_Hotro.FireBaseIncreaseID;
import com.example.apprivew.Lop_Hotro.MyCallBack;
import com.example.apprivew.R;
import com.example.apprivew.moder.Item_BaiDang;
import com.example.apprivew.moder.Item_BinhLuan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;


public class ChiTiet_BaiDangActivity extends AppCompatActivity {

    // Khoi tao

    private TextView txtTieuDe_ChiTiet, txtMoTa_ChiTiet, txtTenQuan_ChiTiet, txtDiaDiem_ChiTiet, txtBinhLuan;
    private RoundedImageView avata_NguoiDung_ChiTiet;
    private TextView tenNguoiDang_ChiTiet, ngayDangBai_ChiTiet;
    private ViewPager2 viewPager2_img_ChiTiet;

    private RecyclerView rcv_binhluan;

    private ImageView like;
    Button btnDangBinhLuan;
    String userIDLogin;
    TextView txtDiem;
    TextView txtslLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_bai_dang);

        SharedPreferences sharedPreferences = getSharedPreferences("taiKhoanDangNhap", MODE_PRIVATE);
        userIDLogin = sharedPreferences.getString("userID", "");


        anhXa();

        Bundle bundle = getIntent().getExtras();
        if(bundle == null)
        {
            return;
        }
        Item_BaiDang item_baiDang = (Item_BaiDang)bundle.get("object_BaiDang");
        ganDuLieuChoChiTietBaiDang(item_baiDang);
        ganDuLieuBinhLuan(item_baiDang);

        btnDangBinhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DangBinhLuan(item_baiDang);
            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            boolean likeCheck = false;
            @Override
            public void onClick(View view) {

                if(likeCheck == false)
                {
                    like.setImageResource(R.drawable.da_like);
                    likeBaiViet(item_baiDang);
                    Toast.makeText(ChiTiet_BaiDangActivity.this, "Đã lưu bài viết", Toast.LENGTH_SHORT).show();
                    likeCheck = true;
                }
                else {

                    like.setImageResource(R.drawable.chua_like);

                    likeCheck = false;
                    huylikeBaiViet(item_baiDang);
                    Toast.makeText(ChiTiet_BaiDangActivity.this, "Đã hủy lưu bài viết", Toast.LENGTH_SHORT).show();


                }


            }
        });


    }

    private void likeBaiViet(Item_BaiDang item_baiDang) {
        DatabaseReference tableLike  = FirebaseDatabase.getInstance().getReference("likes");
        String idPost = item_baiDang.getIdPost();
        tableLike.child(idPost).child(userIDLogin).setValue(userIDLogin);


    }
    private void huylikeBaiViet(Item_BaiDang item_baiDang) {
        DatabaseReference tableLike  = FirebaseDatabase.getInstance().getReference("likes");
        tableLike.child(item_baiDang.getIdPost()).child(userIDLogin).removeValue();

    }



    private void ganDuLieuChoChiTietBaiDang(Item_BaiDang item_baiDang) {
        txtTieuDe_ChiTiet.setText(item_baiDang.getTieuDe());
        txtMoTa_ChiTiet.setText(item_baiDang.getMoTa());
        txtTenQuan_ChiTiet.setText(item_baiDang.getDiaChi().get(1));
        txtDiaDiem_ChiTiet.setText(item_baiDang.getDiaChi().get(0));
        viewPager2_img_ChiTiet.setAdapter(new Img_ChiTiet_Adapter(ChiTiet_BaiDangActivity.this, item_baiDang.getImgBaiDang()));
        Glide.with(ChiTiet_BaiDangActivity.this).load(item_baiDang.getAvata()).into(avata_NguoiDung_ChiTiet);
        tenNguoiDang_ChiTiet.setText(item_baiDang.getTenNguoiDung());
        ngayDangBai_ChiTiet.setText(item_baiDang.getNgayDang());
        txtDiem.setText(item_baiDang.getDiemDanhGia());
        txtslLike.setText(String.valueOf(item_baiDang.getSoLuongLike()));

    }

    private void ganDuLieuBinhLuan(Item_BaiDang item_baiDang){

        List<Item_BinhLuan> listBL = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChiTiet_BaiDangActivity.this, RecyclerView.VERTICAL, false);
        rcv_binhluan.setLayoutManager(linearLayoutManager);

        BinhLuan_Adapter binhLuan_adapter = new BinhLuan_Adapter(listBL, ChiTiet_BaiDangActivity.this);
        rcv_binhluan.setAdapter(binhLuan_adapter);


        DatabaseReference table_Cmt = FirebaseDatabase.getInstance().getReference("cmt");
        DatabaseReference table_nguoiDung = FirebaseDatabase.getInstance().getReference("Users");
        table_Cmt.child(item_baiDang.getIdPost()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listBL.clear();
                for (DataSnapshot dataBinhLuan : snapshot.getChildren())
                {
                    Item_BinhLuan item_binhLuan = new Item_BinhLuan();
                    String idBL = dataBinhLuan.getKey();
                    String noiDung= dataBinhLuan.child("noiDungBinhLuan").getValue(String.class);
                    String ngayBinhLuan =dataBinhLuan.child("ngayDang").getValue(String.class);
                    String userId = dataBinhLuan.child("idUser").getValue(String.class);
                    table_nguoiDung.child(userId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String avata = snapshot.child("avata").getValue(String.class);
                            String tenNguoiDung = snapshot.child("ten").getValue(String.class);
                            item_binhLuan.setAvataNguoiDung(avata);
                            item_binhLuan.setTenNguoiDung(tenNguoiDung);

                            binhLuan_adapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    item_binhLuan.setNoiDung(noiDung);
                    item_binhLuan.setNgayBinhLuan(ngayBinhLuan);
                    listBL.add(item_binhLuan);

                }
                binhLuan_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void DangBinhLuan(Item_BaiDang item_baiDang)
    {
        ProgressDialog progressDialog  = new ProgressDialog(ChiTiet_BaiDangActivity.this);
        progressDialog.setMessage("Vui lòng chờ...");
        progressDialog.show();

        FireBaseIncreaseID fireBaseIncreaseID = new FireBaseIncreaseID();

        DatabaseReference tableBinhLuan = FirebaseDatabase.getInstance().getReference("cmt");
        DatabaseReference newCmt = tableBinhLuan.child(item_baiDang.getIdPost()).push();

        newCmt.child("idUser").setValue(userIDLogin).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    newCmt.child("noiDungBinhLuan").setValue(txtBinhLuan.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        progressDialog.dismiss();
                                        Toast.makeText(ChiTiet_BaiDangActivity.this, "Bình Luận Thành Công !", Toast.LENGTH_SHORT).show();
                                        txtBinhLuan.setText("");

                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(ChiTiet_BaiDangActivity.this, "Bình Luận Thất Bại !", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }


    public void anhXa()
    {
        txtTieuDe_ChiTiet = findViewById(R.id.txtTieuDe_ChiTiet);
        txtMoTa_ChiTiet = findViewById(R.id.txtMoTa_chiTiet);
        txtTenQuan_ChiTiet = findViewById(R.id.txtTenQuan_ChiTiet);
        txtDiaDiem_ChiTiet = findViewById(R.id.txtDiaChi_ChiTiet);
        viewPager2_img_ChiTiet = findViewById(R.id.viewPager2_ChiTiet);
        avata_NguoiDung_ChiTiet = findViewById(R.id.avata_NguoiDung_ChiTiet);
        tenNguoiDang_ChiTiet = findViewById(R.id.tenNguoiDang_ChiTiet);
        ngayDangBai_ChiTiet  =findViewById(R.id.ngayDangBai_ChiTiet);
        rcv_binhluan = findViewById(R.id.rcv_BinhLuan);
        txtBinhLuan = findViewById(R.id.txtBinhLuan);
        btnDangBinhLuan  = findViewById(R.id.buttonDangBinhLuan);
        like = findViewById(R.id.like);
        txtDiem = findViewById(R.id.txtDiem);
        txtslLike = findViewById(R.id.txtslLike);
    }
}