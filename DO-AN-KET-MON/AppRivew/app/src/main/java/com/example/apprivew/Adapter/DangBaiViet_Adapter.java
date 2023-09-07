package com.example.apprivew.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprivew.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.IOException;
import java.util.List;

public class DangBaiViet_Adapter extends RecyclerView.Adapter<DangBaiViet_Adapter.DangBaiViet_AdapterViewHolder> {


    private Context context;
    // Khoi tao
    private List<Uri> list_uri;
    public DangBaiViet_Adapter(Context context, List<Uri> list_uri) {
        this.context = context;
        this.list_uri = list_uri;
    }

    @NonNull
    @Override
    public DangBaiViet_AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img_dang_bai_viet, parent, false);
        return new DangBaiViet_AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DangBaiViet_AdapterViewHolder holder, int position) {

        Uri uri = list_uri.get(position);
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            holder.img_dangBaiViet.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list_uri.size();
    }

    public class DangBaiViet_AdapterViewHolder extends RecyclerView.ViewHolder{

        RoundedImageView img_dangBaiViet;
        public DangBaiViet_AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            img_dangBaiViet = itemView.findViewById(R.id.img_dangBaiViet);
        }
    }
}
