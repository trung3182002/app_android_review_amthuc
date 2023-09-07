package com.example.apprivew.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apprivew.R;
import com.example.apprivew.moder.Item_NguoiDung;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DangNhapActivity extends AppCompatActivity {

    private  Button btnDangNhap;
    private EditText txtSdtLogin;
    private EditText txtMatKhauLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        anhXa();
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ktrDangNhap();

            }
        });


    }


    public void ktrDangNhap(){
        DatabaseReference table_users = FirebaseDatabase.getInstance().getReference("Users");
        ProgressDialog progressDialog  = new ProgressDialog(DangNhapActivity.this);
        progressDialog.setMessage("Vui lòng chờ...");
        progressDialog.show();

        Query ktrSdt = table_users.orderByChild("sdt").equalTo(txtSdtLogin.getText().toString());

        ktrSdt.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    for (DataSnapshot data : dataSnapshot.getChildren())
                    {
                        Item_NguoiDung user = data.getValue(Item_NguoiDung.class);

                        if(user.getMatKhau().equals(txtMatKhauLogin.getText().toString()))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                            SharedPreferences sharedPreferences = getSharedPreferences("taiKhoanDangNhap", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userID",  data.getKey());
                            editor.apply();
                            Intent intent  = new Intent(DangNhapActivity.this, TrangChuActivity.class);
//                           intent.putExtra("sdtDangNhap", txtSdtLogin.getText().toString());
                            startActivity(intent);

                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(DangNhapActivity.this, "Đăng nhập thất bại! Sai Mật khẩu", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(DangNhapActivity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public  void anhXa(){
        btnDangNhap = findViewById(R.id.btnDangNhap);
        txtSdtLogin = findViewById(R.id.txtSdt_lg);
        txtMatKhauLogin = findViewById(R.id.txtMatKhau_lg);
    }
}