package com.example.apprivew.controller.fragment;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apprivew.Adapter.DangBaiViet_Adapter;
import com.example.apprivew.Lop_Hotro.FireBaseIncreaseID;
import com.example.apprivew.Lop_Hotro.MyCallBack;
import com.example.apprivew.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThemBaiDangFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThemBaiDangFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    // khoi tao

    private static final int PICK_IMAGE_REQUEST = 1;
    List<Uri> listuri = new ArrayList<>();
    DangBaiViet_Adapter dangBaiViet_adapter;
    RecyclerView rcv_img_dangBai;
    String userIDLogin;
    Button btnAddHinh;
    Button btnDangBaiViet;

    EditText txtTieuDeBaiViet,txtDiemDanhGia;
    EditText txtMoTaBaiViet, txtTenQuan, txtDiaChiQuan;

    Date currentDate = new Date();
    public ThemBaiDangFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThemBaiDangFragment.
     */
    // TODO: Rename and change types and number of parameters

    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    if (data != null) {
                        if (data.getClipData() != null) { // Nếu người dùng đã chọn nhiều ảnh
                            int count = data.getClipData().getItemCount();
                            for (int i = 0; i < count; i++) {
                                Uri imageUri = data.getClipData().getItemAt(i).getUri();
                                listuri.add(imageUri);
                            }
                        } else { // Nếu người dùng chỉ chọn một ảnh
                            Uri imageUri = data.getData();
                            listuri.add(imageUri);
                        }
                        dangBaiViet_adapter.notifyDataSetChanged(); // Cập nhật lại danh sách ảnh trên RecyclerView
                    }
                }



            }
    );
    public static ThemBaiDangFragment newInstance(String param1, String param2) {
        ThemBaiDangFragment fragment = new ThemBaiDangFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_them_bai_dang, container, false);


        SharedPreferences sharedPreferences = getContext().getSharedPreferences("taiKhoanDangNhap", MODE_PRIVATE);
        userIDLogin = sharedPreferences.getString("userID", "");

        anhXa(view);
        moThuVienLayAnh();

        btnDangBaiViet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVaoFireBase();
            }


        });
        return view;

    }



    private void addVaoFireBase() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Vui lòng chờ...");
        progressDialog.setCancelable(false); // Tùy chọn: Có thể hủy bỏ hoặc không
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Tùy chọn: Kiểu hiển thị của ProgressDialog
        progressDialog.show();
        FireBaseIncreaseID fireBaseIncreaseID = new FireBaseIncreaseID();

        DatabaseReference tableDangBaiViet = FirebaseDatabase.getInstance().getReference("Posts");

        MyCallBack callBack = new MyCallBack() {
            @Override
            public void onCallback(String value) {


                for(Uri imgUri : listuri)
                {
                    if(imgUri!=null)
                    {
                        // khoi tao
                        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

                        // UUID.randomUUID() ham sinh ngau nhien ra 1 ten anh
                        StorageReference imageRef = storageRef.child("img_BaiDang//" + UUID.randomUUID().toString());

                        // upload anh len storage fire base
                        UploadTask uploadTask = imageRef.putFile(imgUri);

                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            // nếu tải lên thành công
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Đăng Bài Thành Công", Toast.LENGTH_SHORT).show();
                                // get dowloadURL tu storage ve readltime database
                                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        progressDialog.dismiss();

                                        // hàm lấy url load lên file base
                                        String imgUrl = uri.toString();
                                        tableDangBaiViet.child(value).child("imgUrlPost").push().setValue(imgUrl);
                                        tableDangBaiViet.child(value).child("tieuDe").setValue(txtTieuDeBaiViet.getText().toString());
                                        tableDangBaiViet.child(value).child("moTa").setValue(txtMoTaBaiViet.getText().toString());
                                        tableDangBaiViet.child(value).child("userId").setValue(userIDLogin);
                                        tableDangBaiViet.child(value).child("ngayDang").setValue("30/5/2023");
                                        tableDangBaiViet.child(value).child("diemDanhGia").setValue("5.0");
                                        tableDangBaiViet.child(value).child("diaChi").child("tenQuan").setValue(txtTenQuan.getText().toString());
                                        tableDangBaiViet.child(value).child("diaChi").child("diaChiCuThe").setValue(txtDiaChiQuan.getText().toString());
                                    }
                                });

                            }
                        });
                    }
                }
            }
        };
        fireBaseIncreaseID.tangIdTuDong(tableDangBaiViet, "postId_", callBack);

    }

    private void moThuVienLayAnh() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        dangBaiViet_adapter  = new DangBaiViet_Adapter(getContext(), listuri);
        rcv_img_dangBai.setLayoutManager(linearLayoutManager);
        rcv_img_dangBai.setAdapter(dangBaiViet_adapter);


        btnAddHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT <  Build.VERSION_CODES.M){
                    return;
                }
                else {

                    if (
                            checkSelfPermission(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE)  == PackageManager.PERMISSION_GRANTED
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
    }


    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent, "Select Pictures"));
    }

    public  void anhXa(View view){

        rcv_img_dangBai = view.findViewById(R.id.rcv_img_dangBai);
        btnAddHinh = view.findViewById(R.id.btnAddHinh);
        btnDangBaiViet = view.findViewById(R.id.btnDangBaiViet);
        txtTieuDeBaiViet = view.findViewById(R.id.txtTieuDeBaiViet);
        txtMoTaBaiViet = view.findViewById(R.id.txtMoTaBaiViet);
        txtTenQuan = view.findViewById(R.id.txtTenQuan);
        txtDiaChiQuan = view.findViewById(R.id.txtDiaChiQuan);
        txtDiemDanhGia =view.findViewById(R.id.txtDiemDanhGia);
    }

}