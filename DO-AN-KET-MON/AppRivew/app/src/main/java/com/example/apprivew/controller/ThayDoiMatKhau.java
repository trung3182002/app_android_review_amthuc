package com.example.apprivew.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprivew.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ThayDoiMatKhau extends AppCompatActivity {
    EditText edtMKCu,edtMKMoi,edtMKMoi2;
    TextView getMKCu;
    ImageView back2;
    Button btnUpdate2;
    String userIDLogin;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thay_doi_mat_khau);

        SharedPreferences sharedPreferences = getSharedPreferences("taiKhoanDangNhap", MODE_PRIVATE);
        userIDLogin = sharedPreferences.getString("userID", "");
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        edtMKCu=findViewById(R.id.edtMKCu);
        edtMKMoi=findViewById(R.id.edtMKMoi);
        edtMKMoi2=findViewById(R.id.edtMKMoi2);
        btnUpdate2 = findViewById(R.id.btnUpdate2);
        back2= findViewById(R.id.back2);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");

        btnUpdate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mkCu = edtMKCu.getText().toString();
                String mkMoi = edtMKMoi.getText().toString();
                String mkMoi2 = edtMKMoi2.getText().toString();
                if (mkCu.isEmpty()) {
                    Toast.makeText(ThayDoiMatKhau.this, "Vui lòng nhập mật khẩu cũ", Toast.LENGTH_SHORT).show();
                } else if (mkMoi.isEmpty() || mkMoi2.isEmpty()) {
                    Toast.makeText(ThayDoiMatKhau.this, "Vui lòng nhập cả hai mật khẩu mới", Toast.LENGTH_SHORT).show();
                } else if (!mkMoi.equals(mkMoi2)) {
                    Toast.makeText(ThayDoiMatKhau.this, "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();
                } else {

                    myRef.child(userIDLogin).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists() && dataSnapshot.child("matKhau").getValue(String.class).equals(edtMKCu.getText().toString())) {
                                myRef.child(userIDLogin).child("matKhau").setValue(edtMKMoi.getText().toString());
                                Toast.makeText(ThayDoiMatKhau.this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                //cancel ra màn hình đăng nhập
                                Intent intent = new Intent(ThayDoiMatKhau.this, ThayDoiThongTin.class);
                                ThayDoiMatKhau.this.startActivity(intent);
                            } else {
                                Toast.makeText(ThayDoiMatKhau.this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}