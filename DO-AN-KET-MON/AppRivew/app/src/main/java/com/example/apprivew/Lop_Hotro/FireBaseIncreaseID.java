package com.example.apprivew.Lop_Hotro;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class FireBaseIncreaseID {
    private DatabaseReference databaseReference;

    public FireBaseIncreaseID() {
    }

    public void tangIdTuDong(DatabaseReference databaseReference, String idStar, final MyCallBack callback)
    {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot Datasnapshot) {
                int count = 0;
                // Duyệt qua từng phần tử trong dữ liệu
                for (DataSnapshot snapshot : Datasnapshot.getChildren()) {
                    // Lấy ID của phần tử và tách lấy số cuối cùng
                    String id = snapshot.getKey();
                    int number = Integer.parseInt(id.substring(id.length() - 2));
                    // Nếu số này lớn hơn số lượng hiện tại thì cập nhật lại
                    if (number > count) {
                        count = number;
                    }
                }
                // Tạo ID mới bằng cách tăng số lượng lên 1 và chuyển về chuỗi
                count++;
                String newId = (idStar + String.format("%03d", count)).toString();
                // Sử dụng ID mới để tạo nút trên Firebase


                // su dung callback vi addListenerForSingleValueEvent bat dong bo
                callback.onCallback(newId);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
