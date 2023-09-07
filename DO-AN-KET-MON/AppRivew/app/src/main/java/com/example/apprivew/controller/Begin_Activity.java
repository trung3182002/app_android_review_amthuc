package com.example.apprivew.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.apprivew.R;

public class Begin_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);

        Button btnRegister;
        View btn_login;
        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Begin_Activity.this, DangKy_Activity.class);
                startActivity(intent);
            }
        });
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Begin_Activity.this, DangNhapActivity.class);
                startActivity(intent);
            }
        });
    }
}