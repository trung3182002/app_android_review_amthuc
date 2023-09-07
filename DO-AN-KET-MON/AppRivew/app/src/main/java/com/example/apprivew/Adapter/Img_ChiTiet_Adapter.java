package com.example.apprivew.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apprivew.R;
import com.example.apprivew.moder.Item_BaiDang;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class Img_ChiTiet_Adapter extends RecyclerView.Adapter<Img_ChiTiet_Adapter.imgChiTetViewHolder> {
    private List<String> mImages;
    private Context mContext;

    public Img_ChiTiet_Adapter(Context context, List<String> images) {
        mImages = images;
        mContext = context;
    }

    @NonNull
    @Override
    public imgChiTetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_img_chitiet, parent, false);
        return new imgChiTetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull imgChiTetViewHolder holder, int position) {
        String imgUrl = mImages.get(position);
        if(imgUrl != null)
        {
            Glide.with(mContext).load(imgUrl).into(holder.img_Item_ChiTiet);

        }
        else {
            holder.img_Item_ChiTiet.setImageResource(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }
    public class imgChiTetViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView img_Item_ChiTiet;
        public imgChiTetViewHolder(@NonNull View itemView) {
            super(itemView);
            img_Item_ChiTiet =itemView.findViewById(R.id.img_Item_ChiTiet);
        }
    }


}
