package com.example.apprivew.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprivew.Lop_Hotro.IClickLinstener;
import com.example.apprivew.R;
import com.example.apprivew.moder.ItemObject;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<ItemObject> mListItems;
    private IClickLinstener iClickLinstener;

    public ItemAdapter(List<ItemObject> mListItems, IClickLinstener iClickLinstener) {
        this.mListItems = mListItems;
        this.iClickLinstener = iClickLinstener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        final ItemObject itemObject = mListItems.get(position);
        if(itemObject==null)
            return;
        holder.tvitem.setText(itemObject.getName());
//holder hinh

        holder.tvitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickLinstener.clickItem(itemObject);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mListItems!=null)
            return mListItems.size();
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView tvitem;
        private ImageView imgitem;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvitem = itemView.findViewById(R.id.tv_item);
            imgitem = itemView.findViewById(R.id.img_item);
        }
    }
}
