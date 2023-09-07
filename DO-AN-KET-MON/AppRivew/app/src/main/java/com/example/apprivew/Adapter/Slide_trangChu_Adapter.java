package com.example.apprivew.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.apprivew.R;
import com.example.apprivew.moder.Item_Slide_TrangChu;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class Slide_trangChu_Adapter extends RecyclerView.Adapter<Slide_trangChu_Adapter.slideViewHolder> {
    private List<Item_Slide_TrangChu> listSlideItem;
    private ViewPager2 viewPager2;

    public Slide_trangChu_Adapter(List<Item_Slide_TrangChu> listSlideItem, ViewPager2 viewPager2) {
        this.listSlideItem = listSlideItem;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public slideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider_trang_chu, parent, false);
        return new slideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull slideViewHolder holder, int position) {
        holder.setImg(listSlideItem.get(position));
    }

    @Override
    public int getItemCount() {
        return listSlideItem.size();
    }

    class slideViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView imageView;

        public slideViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_item_trangChu);
        }
        void setImg(Item_Slide_TrangChu slide)
        {
            if (imageView != null) {
                imageView.setImageResource(slide.getImage());
            }

        }


    }
}
