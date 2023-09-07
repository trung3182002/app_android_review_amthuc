package com.example.apprivew.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apprivew.R;
import com.example.apprivew.moder.Item_BinhLuan;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class BinhLuan_Adapter extends RecyclerView.Adapter<BinhLuan_Adapter.binhLuanViewHolder> {

    List<Item_BinhLuan> itemBinhLuans  = new ArrayList<>();
    Context mContext;

    public BinhLuan_Adapter(List<Item_BinhLuan> itemBinhLuans, Context mContext) {
        this.itemBinhLuans = itemBinhLuans;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public binhLuanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_binh_luan, parent, false);
        return new binhLuanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull binhLuanViewHolder holder, int position) {
        Item_BinhLuan item_binhLuan = itemBinhLuans.get(position);

        if(itemBinhLuans != null)
        {
            if(item_binhLuan.getAvataNguoiDung() != null)
            {
                Glide.with(mContext).load(item_binhLuan.getAvataNguoiDung()).into(holder.avata_NguoiDung_BinhLuan);

            }
            else {
                holder.avata_NguoiDung_BinhLuan.setImageResource(View.GONE);
            }
            holder.txtTenNguoiDungBinhLuan.setText(item_binhLuan.getTenNguoiDung());
            holder.txtNoiDungBl.setText(item_binhLuan.getNoiDung());
            holder.txtNgayBinhLuan.setText(item_binhLuan.getNgayBinhLuan());

        }
        else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return itemBinhLuans.size();
    }

    public  class  binhLuanViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView avata_NguoiDung_BinhLuan;
        TextView txtTenNguoiDungBinhLuan,txtNoiDungBl,txtNgayBinhLuan;
        public binhLuanViewHolder(@NonNull View itemView) {
            super(itemView);

            avata_NguoiDung_BinhLuan = itemView.findViewById(R.id.avata_NguoiDung_BinhLuan);
            txtTenNguoiDungBinhLuan = itemView.findViewById(R.id.txtTenNguoiDungBinhLuan);
            txtNoiDungBl = itemView.findViewById(R.id.txtNoiDungBl);
            txtNgayBinhLuan = itemView.findViewById(R.id.txtNgayBinhLuan);
        }
    }
}
