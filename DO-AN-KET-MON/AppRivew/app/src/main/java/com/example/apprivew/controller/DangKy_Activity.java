package com.example.apprivew.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprivew.Lop_Hotro.FireBaseIncreaseID;
import com.example.apprivew.Lop_Hotro.MyCallBack;
import com.example.apprivew.R;
import com.example.apprivew.moder.Item_NguoiDung;
import com.example.apprivew.moder.Item_User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DangKy_Activity extends AppCompatActivity {

    TextView txtSdt, txtTen, txtMatKhau, txt_repass;
    Button btnDangKy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        anhXa();
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangKy();
            }
        });
    }

    public  void dangKy(){

        FireBaseIncreaseID fireBaseIncreaseID = new FireBaseIncreaseID();
        DatabaseReference table_users = FirebaseDatabase.getInstance().getReference("Users");

        MyCallBack callBackId_Users = new MyCallBack() {


            @Override
            public void onCallback(String value) {
                ProgressDialog progressDialog  = new ProgressDialog(DangKy_Activity.this);
                progressDialog.setMessage("Vui lòng chờ...");
                progressDialog.show();
                String mk = txt_repass.getText().toString();
                if (!mk.equals(txtMatKhau.getText().toString())){
                    Toast.makeText(DangKy_Activity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else {


                Query ktrSdt = table_users.orderByChild("sdt").equalTo(txtSdt.getText().toString());

                ktrSdt.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            progressDialog.dismiss();
                            Toast.makeText(DangKy_Activity.this, "Số điện thoại đã tồn tại", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            progressDialog.dismiss();
                            Item_NguoiDung user = new Item_NguoiDung();
                            user.setTen(txtTen.getText().toString());
                            user.setSdt(txtSdt.getText().toString());
                            user.setMatKhau(txtMatKhau.getText().toString());
                            user.setAvata("https://firebasestorage.googleapis.com/v0/b/riview-app.appspot.com/o/images%2Favatar-fb-39.jpg?alt=media&token=57bae93b-d9af-4df3-b058-ea724c55ec28");
                            user.setNgaySinh("null");
                            user.setUserName("null");
                            user.setDiaChi("null");
                            table_users.child(value).setValue(user);
                            Toast.makeText(DangKy_Activity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DangKy_Activity.this, DangNhapActivity.class);
                            startActivity(intent);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                }

            }
        };
        fireBaseIncreaseID.tangIdTuDong(table_users, "userId", callBackId_Users);


        // loadding

    }
    public  void anhXa()
    {
        txtSdt = findViewById(R.id.txtSDT_re);
        txtTen = findViewById(R.id.txtName_re);
        txtMatKhau = findViewById(R.id.txtMatKhau_re);
        btnDangKy = findViewById(R.id.btnDangKy);
        txt_repass = findViewById(R.id.txtNhapLaiMatKhau_re);
    }
}