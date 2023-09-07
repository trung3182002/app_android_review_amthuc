package com.example.apprivew.controller.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.apprivew.Adapter.BaiDang_Adapter;
import com.example.apprivew.Adapter.baiDang_profire_Adapter;
import com.example.apprivew.Adapter.mybottomsheet;
import com.example.apprivew.Lop_Hotro.IClickLinstener;
import com.example.apprivew.R;
import com.example.apprivew.controller.DangNhapActivity;
import com.example.apprivew.controller.ThayDoiMatKhau;
import com.example.apprivew.controller.ThayDoiThongTin;
import com.example.apprivew.moder.ItemObject;
import com.example.apprivew.moder.Item_BaiDang;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProFlie_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProFlie_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Khoi tao





    public ProFlie_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProFlie_Fragment.
     */
    // TODO: Rename and change types and number of parameters


    TextView tvHoTen,tvUser;
    ImageView avt;
    Button btnSua;
    DatabaseReference databaseReference;
    String userIDLogin;
    RecyclerView rcv_baiDang_pr;
    TextView txtslBaiDang;

    private baiDang_profire_Adapter baiDang_profire_adapter;
    public static ProFlie_Fragment newInstance(String param1, String param2) {
        ProFlie_Fragment fragment = new ProFlie_Fragment();
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

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("taiKhoanDangNhap", MODE_PRIVATE);
        userIDLogin = sharedPreferences.getString("userID", "");

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_pro_flie_, container, false);

        tvHoTen=view.findViewById(R.id.tvHoTen);
        tvUser=view.findViewById(R.id.tvUser);
        btnSua =view.findViewById(R.id.btnSua);
        rcv_baiDang_pr = view.findViewById(R.id.rcv_baiDang_pr);
        avt = view.findViewById(R.id.avt);
        txtslBaiDang = view.findViewById(R.id.txtslBaiDang);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        myRef.child(userIDLogin).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String n = snapshot.child("ten").getValue(String.class);
                String u = snapshot.child("userName").getValue(String.class);
                tvHoTen.setText(n);
                tvUser.setText(u);
                Glide.with(getContext()).load(snapshot.child("avata").getValue(String.class)).into(avt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOpen();
            }
        });

        khoiTaorcvChoBaiDang(view);
        return view;

    }


    private View khoiTaorcvChoBaiDang(View view)
    {
        List<Item_BaiDang> listBaiDang = new ArrayList<>();

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rcv_baiDang_pr.setLayoutManager(staggeredGridLayoutManager);

        // set du lieu
        baiDang_profire_adapter = new baiDang_profire_Adapter(getActivity(), listBaiDang);

        rcv_baiDang_pr.setAdapter(baiDang_profire_adapter);

        DatabaseReference tablePosts = FirebaseDatabase.getInstance().getReference("Posts");
        DatabaseReference tableUsers = FirebaseDatabase.getInstance().getReference("Users");


        tablePosts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listBaiDang.clear();
                int count = 0;
                for (DataSnapshot data : snapshot.getChildren()){
                    if(data.child("userId").getValue(String.class).equals(userIDLogin))
                    {
                        count++;
                        Item_BaiDang item = new Item_BaiDang();
                        String key = data.getKey();
                        String idBaiDang = data.getKey();
                        String tieuDe = data.child("tieuDe").getValue(String.class);
                        String moTa = data.child("moTa").getValue(String.class);
                        String ngayDang = data.child("ngayDang").getValue(String.class);
                        String diemDanhGia = data.child("diemDanhGia").getValue(String.class);
                        String idNguoiDung = data.child("userId").getValue(String.class);
//                        int slLike = data.child("likeCout").getValue(int.class);
                        tableUsers.child(idNguoiDung).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String tenNguoiDung = snapshot.child("ten").getValue(String.class);
                                String avata = snapshot.child("avata").getValue(String.class);
                                item.setTenNguoiDung(tenNguoiDung);
                                item.setAvata(avata);
                                baiDang_profire_adapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        List<String> imgUrls = new ArrayList<>();
                        for (DataSnapshot dataImgUri : snapshot.child(key).child("imgUrlPost").getChildren())
                        {
                            String imgUrl = dataImgUri.getValue(String.class);
                            imgUrls.add(imgUrl);
                        }
                        List<String> diaChi = new ArrayList<>();
                        for(DataSnapshot dataDiaChi : snapshot.child(key).child("diaChi").getChildren())
                        {
                            String daiDiem =  dataDiaChi.getValue(String.class);
                            diaChi.add(daiDiem);
                        }
                        item.setTieuDe(tieuDe);
                        item.setMoTa(moTa);
                        item.setImgBaiDang(imgUrls);
                        item.setNgayDang(ngayDang);
                        item.setDiaChi(diaChi);
                        item.setUserId(idNguoiDung);
                        item.setIdPost(idBaiDang);
//                        item.setSoLuongLike(slLike);
                        item.setDiemDanhGia(diemDanhGia);
                        listBaiDang.add(item);


                    }


                }
                txtslBaiDang.setText(String.valueOf(count));
                baiDang_profire_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error 303", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void clickOpen() {
        List<ItemObject> l  = new ArrayList<>();
        l.add(new ItemObject("Thông tin cá nhân","baseline_emoji_people_24.xml"));
        l.add(new ItemObject("Thay đổi mật khẩu","setting.xml"));
        l.add(new ItemObject("Đăng xuất","logout.xml"));
        mybottomsheet mybottomsheet = new mybottomsheet(l, new IClickLinstener() {
            @Override
            public void clickItem(ItemObject itemObject) {
                if(itemObject.getName()=="Thông tin cá nhân")
                {
                    Intent intent = new Intent(getContext(), ThayDoiThongTin.class);
                    getContext().startActivity(intent);
                }
                if(itemObject.getName()=="Thay đổi mật khẩu")
                {
                    Intent intent1 = new Intent(getContext(), ThayDoiMatKhau.class);
                    getContext().startActivity(intent1);
                }
                if(itemObject.getName()=="Đăng xuất")
                {
                    Intent intent1 = new Intent(getContext(), DangNhapActivity.class);
                    getContext().startActivity(intent1);
                }

            }
        });
        mybottomsheet.show(getParentFragmentManager(),mybottomsheet.getTag());
    }



}