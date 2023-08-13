package com.firstapp.kyecothreads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Leaderboard extends AppCompatActivity {
    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ecothreads-5b2eb-default-rtdb.firebaseio.com/");

    private ListView leaderboardListView;
    private LeaderboardAdapter adapter;
    private TextView status;
    private ArrayList<LeaderboardItem> leaderboardItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        leaderboardListView = findViewById(R.id.leaderboardListView);
        status=findViewById(R.id.status);

        leaderboardItems = new ArrayList<>();
        adapter = new LeaderboardAdapter(this, leaderboardItems);
        leaderboardListView.setAdapter(adapter);

        retrieveUpdatedPoints();
        UpdateText();
    }

    private void retrieveUpdatedPoints() {
        reference.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                leaderboardItems.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    try {
                        userdata user = userSnapshot.getValue(userdata.class);
                        if (user != null) {
                            String userId = userSnapshot.getKey();
                            String userName = user.getName();
                            int points = Integer.parseInt(user.getPoints());

                            leaderboardItems.add(new LeaderboardItem(userId, userName, points));
                        }
                    } catch (Exception e) {
                        Log.e("DataError", "Error adding leaderboard item", e);
                    }
                }

                updateLeaderboard();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DatabaseError", "Error reading data", databaseError.toException());
            }
        });
    }


    private void updateLeaderboard() {
        Collections.sort(leaderboardItems, new Comparator<LeaderboardItem>() {
            @Override
            public int compare(LeaderboardItem item1, LeaderboardItem item2) {
                return Integer.compare(item2.getPoints(), item1.getPoints());
            }
        });

        for (int i = 0; i < leaderboardItems.size(); i++) {
            leaderboardItems.get(i).setRank(i + 1);
        }

        adapter.notifyDataSetChanged();
    }



    private void UpdateText(){
        reference.child("User").child(getCurrentUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    userdata user = dataSnapshot.getValue(userdata.class);
                    if (user != null) {
                        String points = user.getPoints();
                        String cotton = user.getCotton();
                        String water = user.getWater();

                        status.setText("You have "+points+" points and saved "+water+" gallons of water and "+cotton+" pounds of cotton.");
                    }
                }
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
