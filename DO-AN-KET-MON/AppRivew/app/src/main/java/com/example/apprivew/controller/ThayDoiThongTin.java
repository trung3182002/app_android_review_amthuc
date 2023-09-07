package com.example.apprivew.controller;

import static androidx.core.content.ContextCompat.checkSelfPermission;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.apprivew.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class ThayDoiThongTin extends AppCompatActivity {

    Button btnUpdate;
    ImageView back, imageAvt;
    Spinner edtGioiTinh;
    EditText edtHoTen,edtUsername,edtMatKhau,edtNgaySinh,edtKhuVuc;

    String userIDLogin;
    Uri uriImg;
    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    if (result.getResultCode() == Activity.RESULT_OK && data != null) {
                        Uri imageUri = data.getData();
                        uriImg = imageUri;

                    }
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thay_doi_thong_tin);
        addControl();
        String[] gioiTinh = {"Nam", "Nữ", "Khác"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gioiTinh);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edtGioiTinh.setAdapter(adapter);
        SharedPreferences sharedPreferences = getSharedPreferences("taiKhoanDangNhap", MODE_PRIVATE);

        userIDLogin = sharedPreferences.getString("userID", "");
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("Users");
        myRef.child(userIDLogin).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                edtHoTen.setText(snapshot.child("ten").getValue(String.class));
                edtUsername.setText(snapshot.child("userName").getValue(String.class));
                edtMatKhau.setText(snapshot.child("matKhau").getValue(String.class));
                edtMatKhau.setEnabled(false);
                Object gioiTinhObj = snapshot.child("gioTinh").getValue();

                int defaultPosition = adapter.getPosition(gioiTinhObj.toString());
                edtGioiTinh.setSelection(defaultPosition);

                edtNgaySinh.setText(snapshot.child("ngaySinh").getValue(String.class));

                edtKhuVuc.setText(snapshot.child("diaChi").getValue(String.class));

                Glide.with(ThayDoiThongTin.this).load(snapshot.child("avata").getValue(String.class)).into(imageAvt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        imageAvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT <  Build.VERSION_CODES.M){
                    return;
                }
                else {

                    if (
                            checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)  == PackageManager.PERMISSION_GRANTED
                    ) {
                        openGallery();
                    }
                    else {
                        String [] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permission, 10);
                    }
                }
            }
        });







        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child(userIDLogin).child("ten").setValue(edtHoTen.getText().toString());
                myRef.child(userIDLogin).child("userName").setValue(edtUsername.getText().toString());
                myRef.child(userIDLogin).child("gioTinh").setValue(edtGioiTinh.getSelectedItem().toString());
                myRef.child(userIDLogin).child("ngaySinh").setValue(edtNgaySinh.getText().toString());
                myRef.child(userIDLogin).child("diaChi").setValue(edtKhuVuc.getText().toString());

                if(uriImg!=null) {
                    // khoi tao
                    StorageReference storageRef = FirebaseStorage.getInstance().getReference();

                    // UUID.randomUUID() ham sinh ngau nhien ra 1 ten anh
                    StorageReference imageRef = storageRef.child("img_BaiDang/" + UUID.randomUUID().toString());

                    // upload anh len storage fire base
                    UploadTask uploadTask = imageRef.putFile(uriImg);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        // nếu tải lên thành công
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // get dowloadURL tu storage ve readltime database
                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    // hàm lấy url load lên file base
                                    String imgUrl = uri.toString();
                                    myRef.child(userIDLogin).child("avata").setValue(imgUrl);

                                }
                            });

                        }
                    });
                }

                Toast.makeText(ThayDoiThongTin.this,"Cap nhat thanh cong",Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });



    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent, "Select Pictures"));
    }

    private void addControl()
    {
        btnUpdate=findViewById(R.id.btnUpdate);
        edtHoTen=findViewById(R.id.edtHoTen);
        edtUsername=findViewById(R.id.edtUsername);
        edtMatKhau=findViewById(R.id.edtMatKhau);
        edtGioiTinh=findViewById(R.id.edtGioiTinh);
        edtNgaySinh=findViewById(R.id.edtNgaySinh);
        edtKhuVuc=findViewById(R.id.edtKhuVuc);;
        back = findViewById(R.id.back);
        imageAvt = findViewById(R.id.imageAvt);
    }
}