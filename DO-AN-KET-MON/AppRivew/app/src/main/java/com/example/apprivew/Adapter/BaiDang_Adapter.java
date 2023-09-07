package com.example.apprivew.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apprivew.R;
import com.example.apprivew.controller.ChiTiet_BaiDangActivity;
import com.example.apprivew.moder.Item_BaiDang;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class BaiDang_Adapter extends RecyclerView.Adapter<BaiDang_Adapter.BaiDangViewHolder> {

    private Context context;

    private List<Item_BaiDang> listBaiDang;

    public BaiDang_Adapter(Context context, List<Item_BaiDang> listBaiDang) {
        this.context = context;
        this.listBaiDang = listBaiDang;
    }

    @NonNull
    @Override
    public BaiDangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_baidang_trangchu, parent, false);
        return new BaiDangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaiDangViewHolder holder, int position) {

        Item_BaiDang baiDang = listBaiDang.get(position);
        if(baiDang == null)
        {
            return;

        }
        else {
            if (baiDang.getImgBaiDang().size() > 0) {
                // Load the first image of the post into the ImageView
                Glide.with(context).load(baiDang.getImgBaiDang().get(0)).into(holder.imageViewBaiDang);
            } else {
                // Hide the ImageView if there is no image for the post
                holder.imageViewBaiDang.setImageResource(View.GONE);
            }
            holder.txtTieuDe_BaiDang.setText(baiDang.getTieuDe());
            holder.txtTenNguoiDung_BaiDang.setText(baiDang.getTenNguoiDung());
            Glide.with(context).load(baiDang.getAvata()).into(holder.avata_NguoiDung_BaiDang);

            holder.cardViewItemBaiDang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickPostDetails(baiDang);
                }
            });
        }
    }
    public void onClickPostDetails(Item_BaiDang baiDang)
    {
        Intent intent = new Intent(context, ChiTiet_BaiDangActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_BaiDang", baiDang);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    @Override
    public int getItemCount() {
        return listBaiDang.size();
    }

    public class BaiDangViewHolder extends RecyclerView.ViewHolder{

        ImageView imageViewBaiDang;
        RoundedImageView avata_NguoiDung_BaiDang;
        TextView txtTieuDe_BaiDang;
        TextView txtTenNguoiDung_BaiDang;
        CardView cardViewItemBaiDang;

        public BaiDangViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewBaiDang = itemView.findViewById(R.id.imgBaiDang);
            txtTieuDe_BaiDang = itemView.findViewById(R.id.txtTieuDe_BaiDang);
            txtTenNguoiDung_BaiDang = itemView.findViewById(R.id.tenNguoiDung_BaiDang);
            cardViewItemBaiDang = itemView.findViewById(R.id.viewCardView);
            avata_NguoiDung_BaiDang = itemView.findViewById(R.id.avata_NguoiDung_BaiDang);

        }
    }

}
