package com.firstapp.kyecothreads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.service.autofill.UserData;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class checker extends AppCompatActivity {

    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ecothreads-5b2eb-default-rtdb.firebaseio.com/");
    RecyclerView recyclerView, recyclerView1;
    UserAdapter userAdapter, userAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checker);

        recyclerView = findViewById(R.id.RecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView1 = findViewById(R.id.RecycleView2);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        reference.child("User").child(getCurrentUserId()).child("points").setValue("24");

        retrieveUpdatedPoints();
    }
/*
    private void updateAllUsersPointsTo19() {
        reference.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userKey = userSnapshot.getKey();
                    reference.child("User").child(userKey).child("points").setValue("19");
                }

                retrieveUpdatedPoints();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DatabaseError", "Error updating data", databaseError.toException());
            }
        });
    }*/

    private void retrieveUpdatedPoints() {
        reference.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<userdata> userList = new ArrayList<>();

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    userdata user = userSnapshot.getValue(userdata.class);
                    if (user != null) {
                        userList.add(user);
                    }
                }

                userAdapter = new UserAdapter(userList);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DatabaseError", "Error reading data", databaseError.toException());
            }
        });
    }

    private String getCurrentUserId() {
        return MainActivity.currentuser;
    }



}