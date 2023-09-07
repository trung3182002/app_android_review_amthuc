package com.example.apprivew.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprivew.R;
import com.example.apprivew.moder.Item_mon_an_cac_nuoc;

import java.util.List;

public class Mon_an_cac_nuoc_Adapter extends RecyclerView.Adapter<Mon_an_cac_nuoc_Adapter.item_mon_an_cac_nuocViewHolder> {



    private Context context;
    private List<Item_mon_an_cac_nuoc> mon_an_cac_nuocList;

    public Mon_an_cac_nuoc_Adapter(Context context, List<Item_mon_an_cac_nuoc> mon_an_cac_nuocList) {
        this.context = context;
        this.mon_an_cac_nuocList = mon_an_cac_nuocList;
    }

    @NonNull
    @Override
    public item_mon_an_cac_nuocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mon_an_cac_nuoc_trang_chu, parent,false);
        return new item_mon_an_cac_nuocViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull item_mon_an_cac_nuocViewHolder holder, int position) {
        Item_mon_an_cac_nuoc i = mon_an_cac_nuocList.get(position);
        if(i == null)
        {
            return;
        }
        else {
            holder.imgView.setImageResource(i.getImg_mon_an_cac_nuoc());
            holder.textView.setText(i.getTile_mon_an_cac_nuoc());
        }
    }

    @Override
    public int getItemCount() {
        return mon_an_cac_nuocList.size();
    }

    public class item_mon_an_cac_nuocViewHolder extends RecyclerView.ViewHolder{

        ImageView imgView;
        TextView textView;


        public item_mon_an_cac_nuocViewHolder(@NonNull View itemView) {
            super(itemView);

            imgView = itemView.findViewById(R.id.imgView_mon_an_cac_nuoc);
            textView = itemView.findViewById(R.id.textView_mon_an_cac_nuoc);
        }
    }
}
