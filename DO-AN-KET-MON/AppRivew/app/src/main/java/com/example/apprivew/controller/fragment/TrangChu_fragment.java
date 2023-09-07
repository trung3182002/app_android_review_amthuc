package com.example.apprivew.controller.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprivew.Adapter.BaiDang_Adapter;
import com.example.apprivew.Adapter.Mon_an_cac_nuoc_Adapter;
import com.example.apprivew.Adapter.Slide_trangChu_Adapter;
import com.example.apprivew.R;
import com.example.apprivew.moder.Item_BaiDang;
import com.example.apprivew.moder.Item_Slide_TrangChu;
import com.example.apprivew.moder.Item_mon_an_cac_nuoc;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrangChu_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrangChu_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //khoi tao
    ViewPager2 viewPager2_slide_trangChu;
    RecyclerView rcv_mon_an_cac_nuoc;
    RecyclerView rcv_quan_an_noi_tieng;
    RecyclerView rcv_baiDang;
    Mon_an_cac_nuoc_Adapter mon_an_cac_nuoc_adapter;

    TextView likeBai;
    private BaiDang_Adapter baiDang_adapter;

    public TrangChu_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrangChu_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrangChu_fragment newInstance(String param1, String param2) {
        TrangChu_fragment fragment = new TrangChu_fragment();
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
        View view =  inflater.inflate(R.layout.fragment_trang_chu, container, false);


        anhXa(view);


        khoiTaoSlide();
        khoiTaoMonAnCacNuoc();
        khoiTaoQuuanAnNoiTien();
        khoiTaorcvChoBaiDang(view);


//        likeBai.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                likeBai.setBackgroundResource(R.drawable.da_like);
//            }
//        });
        return view;

    }
    private View khoiTaorcvChoBaiDang(View view)
    {
        List<Item_BaiDang> listBaiDang = new ArrayList<>();

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rcv_baiDang.setLayoutManager(staggeredGridLayoutManager);

        // set du lieu
        baiDang_adapter = new BaiDang_Adapter(getActivity(), listBaiDang);

        rcv_baiDang.setAdapter(baiDang_adapter);

        DatabaseReference tablePosts = FirebaseDatabase.getInstance().getReference("Posts");
        DatabaseReference tableUsers = FirebaseDatabase.getInstance().getReference("Users");
        tablePosts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listBaiDang.clear();
                for (DataSnapshot data : snapshot.getChildren()){
                    Item_BaiDang item = new Item_BaiDang();
                    String key = data.getKey();
                    String idBaiDang = data.getKey();
                    String tieuDe = data.child("tieuDe").getValue(String.class);
                    String moTa = data.child("moTa").getValue(String.class);
                    String ngayDang = data.child("ngayDang").getValue(String.class);
                    String diemDanhGia = data.child("diemDanhGia").getValue(String.class);
                    String idNguoiDung = data.child("userId").getValue(String.class);

                    tableUsers.child(idNguoiDung).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String tenNguoiDung = snapshot.child("ten").getValue(String.class);
                            String avata = snapshot.child("avata").getValue(String.class);
                            item.setTenNguoiDung(tenNguoiDung);
                            item.setAvata(avata);
                            baiDang_adapter.notifyDataSetChanged();

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
                    item.setDiemDanhGia(diemDanhGia);
                    listBaiDang.add(item);

                }
                baiDang_adapter.notifyDataSetChanged();




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error 303", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void khoiTaoSlide() {
        List<Item_Slide_TrangChu> item_slides = new ArrayList<>();

        item_slides.add(new Item_Slide_TrangChu(R.drawable.slide_bg_1));
        item_slides.add(new Item_Slide_TrangChu(R.drawable.slide_bg_2));
        viewPager2_slide_trangChu.setAdapter(new Slide_trangChu_Adapter(item_slides, viewPager2_slide_trangChu));
        // Tự động chuyển slide sau một khoảng thời gian nhất định
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                int currentItem = viewPager2_slide_trangChu.getCurrentItem();
                int totalItems = viewPager2_slide_trangChu.getAdapter().getItemCount();
                if (currentItem < totalItems - 1) {
                    viewPager2_slide_trangChu.setCurrentItem(currentItem + 1);
                } else {
                    viewPager2_slide_trangChu.setCurrentItem(0);
                }
            }
        };
        int delay = 3000; // milliseconds
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                update.run();
                handler.postDelayed(this, delay);
            }
        }, delay);


    }

    private void khoiTaoMonAnCacNuoc()
    {
        List<Item_mon_an_cac_nuoc> list_mon_an_cac_nuoc = new ArrayList<>();
        mon_an_cac_nuoc_adapter = new Mon_an_cac_nuoc_Adapter(getActivity(), list_mon_an_cac_nuoc);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rcv_mon_an_cac_nuoc.setLayoutManager(linearLayoutManager);
        rcv_mon_an_cac_nuoc.setAdapter(mon_an_cac_nuoc_adapter);
        list_mon_an_cac_nuoc.add(new Item_mon_an_cac_nuoc(R.drawable.avatar1, "Món Thái"));
        list_mon_an_cac_nuoc.add(new Item_mon_an_cac_nuoc(R.drawable.z4388100146911_f4ad5a8c6c3923c4f2768af831d62a19, "Món Nhật"));
        list_mon_an_cac_nuoc.add(new Item_mon_an_cac_nuoc(R.drawable.anh2_mon, "Món Việt"));
        // add du lieu
    }

    private void khoiTaoQuuanAnNoiTien()
    {
        List<Item_mon_an_cac_nuoc> list_mon_an_cac_nuoc = new ArrayList<>();
        mon_an_cac_nuoc_adapter = new Mon_an_cac_nuoc_Adapter(getActivity(), list_mon_an_cac_nuoc);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rcv_quan_an_noi_tieng.setLayoutManager(linearLayoutManager);
        rcv_quan_an_noi_tieng.setAdapter(mon_an_cac_nuoc_adapter);
        list_mon_an_cac_nuoc.add(new Item_mon_an_cac_nuoc(R.drawable.z4388100151995_684241563986dbfa3afe2493790a5ad1, "Quán ngon"));
        list_mon_an_cac_nuoc.add(new Item_mon_an_cac_nuoc(R.drawable.z4388100151995_684241563986dbfa3afe2493790a5ad1,"Quán vĩa hè"));
        list_mon_an_cac_nuoc.add(new Item_mon_an_cac_nuoc(R.drawable.anh5, "Quán thành phố"));
        // add du lieu
    }


    public void anhXa(View view){
        viewPager2_slide_trangChu = view.findViewById(R.id.slide_trangchu);
        rcv_mon_an_cac_nuoc = view.findViewById(R.id.rcv_monAnCacNuoc);
        rcv_quan_an_noi_tieng = view.findViewById(R.id.rcv_quanNoiTieng);
        rcv_baiDang = view.findViewById(R.id.rcv_BaiDang);
    }
}