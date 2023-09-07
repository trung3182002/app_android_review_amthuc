package com.example.apprivew.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.apprivew.controller.fragment.ProFlie_Fragment;
import com.example.apprivew.controller.fragment.ThemBaiDangFragment;
import com.example.apprivew.controller.fragment.ThongBaoFragment;
import com.example.apprivew.controller.fragment.TrangChu_fragment;
import com.example.apprivew.controller.fragment.Video_Fragment;

public class ViewPage_bottomAdapter extends FragmentStatePagerAdapter {


    public ViewPage_bottomAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new TrangChu_fragment();
            case 1:
                return new Video_Fragment();
            case 2:
                return new ThemBaiDangFragment();
            case 3:
                return new ThongBaoFragment();
            case 4:
                return new ProFlie_Fragment();
            default:
                return new TrangChu_fragment();
        }

    }

    @Override
    public int getCount() {
        return 5;
    }
}
