package com.example.apprivew.Adapter;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprivew.Lop_Hotro.IClickLinstener;
import com.example.apprivew.R;
import com.example.apprivew.moder.ItemObject;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class mybottomsheet extends BottomSheetDialogFragment {
    private List<ItemObject> mListItem;
    private IClickLinstener iClickLinstener;

    public mybottomsheet(List<ItemObject> mListItem, IClickLinstener iClickLinstener) {
        this.mListItem = mListItem;
        this.iClickLinstener = iClickLinstener;
    }

    public mybottomsheet(int contentLayoutId, List<ItemObject> mListItem, IClickLinstener iClickLinstener) {
        super(contentLayoutId);
        this.mListItem = mListItem;
        this.iClickLinstener = iClickLinstener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {



        BottomSheetDialog bottomSheetDialog =(BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_bts,null);
        bottomSheetDialog.setContentView(view);
        RecyclerView recdata = view.findViewById(R.id.rc_data);
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(getContext());
        recdata.setLayoutManager(linearLayoutManager );
        ItemAdapter itemAdapter = new ItemAdapter(mListItem, new IClickLinstener() {
            @Override
            public void clickItem(ItemObject itemObject) {
                iClickLinstener.clickItem(itemObject);
            }
        });
        recdata.setAdapter(itemAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        recdata.addItemDecoration(itemDecoration);
        return bottomSheetDialog;
    }
}
