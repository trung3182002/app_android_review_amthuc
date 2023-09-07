package com.example.apprivew.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.apprivew.Adapter.ViewPage_bottomAdapter;
import com.example.apprivew.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TrangChuActivity extends AppCompatActivity {




    private  BottomNavigationView bottomNavigationView;
    private ViewPager viewPagerBottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        DatabaseReference table_users = FirebaseDatabase.getInstance().getReference("Users");

        // them bottom trang chu

        anhXa();
        caiDatViewPageBottom();
        bottomNavigationView.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()){
                case R.id.bottom_home:
                    viewPagerBottom.setCurrentItem(0);
                    break;

                case R.id.bottom_post:
                    viewPagerBottom.setCurrentItem(2);
                    break;

                case R.id.bottom_profile:
                    viewPagerBottom.setCurrentItem(4);
                    break;

            }
            return true;
        });



    }

    private void caiDatViewPageBottom() {
        ViewPage_bottomAdapter viewPage_bottomAdapter = new ViewPage_bottomAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerBottom.setAdapter(viewPage_bottomAdapter);

    }

    public void anhXa()
    {
        bottomNavigationView =findViewById(R.id.bottom_trangChu);
        viewPagerBottom =findViewById(R.id.viewPager_bottom);
    }



}