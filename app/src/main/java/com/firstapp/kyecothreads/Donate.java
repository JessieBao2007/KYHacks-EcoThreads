package com.firstapp.kyecothreads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.os.Parcelable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Donate extends AppCompatActivity {
    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ecothreads-5b2eb-default-rtdb.firebaseio.com/");

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText titleEditText, quantityInput;
    private ImageView imageView;
    private Uri imageUri;
    private Spinner typeSpinner;

    private List<DonationEntries> donationEntriesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        titleEditText = findViewById(R.id.titleEditText);
        quantityInput = findViewById(R.id.quantityEditText);
        imageView = findViewById(R.id.imageView);
        typeSpinner = findViewById(R.id.typeSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.clothing_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        Button uploadButton = findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        Button submitButton = findViewById(R.id.submitBtn);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
            }
        });

        Button swapButton = findViewById(R.id.swapButton);
        swapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent swapIntent = new Intent(Donate.this, Swap.class);
                swapIntent.putParcelableArrayListExtra("donationEntriesList", new ArrayList<>(donationEntriesList));
                startActivity(swapIntent);
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    private void submitData() {
        String title = titleEditText.getText().toString();
        int quantity = Integer.parseInt(quantityInput.getText().toString());
        String clothingType = typeSpinner.getSelectedItem().toString();

        DonationEntries donationEntry = new DonationEntries(title, quantity, clothingType, imageUri); // Use the selected imageUri
        donationEntriesList.add(donationEntry);
        UpdateScores();

        // Clear input fields after submission
        titleEditText.setText("");
        quantityInput.setText("");
        imageView.setImageResource(R.drawable.ic_launcher_foreground);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void UpdateScores(){
        String spinnertxt= typeSpinner.getSelectedItem().toString();

        //SHIRTS
        if (spinnertxt.equals("Shirts")) {
            int pointsToAdd = 20;
            int waterToAdd = 590;
            double cottonToAdd = 0.5;

            //  points
            reference.child("User").child(getCurrentUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String currentPointsStr = dataSnapshot.child("points").getValue(String.class);
                        if (currentPointsStr != null) {
                            int currentPoints = Integer.parseInt(currentPointsStr);
                            int updatedPoints = currentPoints + pointsToAdd;
                            reference.child("User").child(getCurrentUserId()).child("points").setValue(String.valueOf(updatedPoints));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("DatabaseError", "Error updating data", databaseError.toException());
                }
            });

            // WATER AND COTTON
            reference.child("User").child(getCurrentUserId()).child("water").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String currentWaterUsageStr = dataSnapshot.getValue(String.class);
                        if (currentWaterUsageStr != null) {
                            int currentWaterUsage = Integer.parseInt(currentWaterUsageStr);
                            int updatedWaterUsage = currentWaterUsage + waterToAdd;
                            reference.child("User").child(getCurrentUserId()).child("water").setValue(String.valueOf(updatedWaterUsage));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("DatabaseError", "Error updating data", databaseError.toException());
                }
            });

            reference.child("User").child(getCurrentUserId()).child("cotton").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String currentCottonUsageStr = dataSnapshot.getValue(String.class);
                        if (currentCottonUsageStr != null) {
                            double currentCottonUsage = Double.parseDouble(currentCottonUsageStr);
                            double updatedCottonUsage = currentCottonUsage + cottonToAdd;
                            reference.child("User").child(getCurrentUserId()).child("cotton").setValue(String.valueOf(updatedCottonUsage));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("DatabaseError", "Error updating data", databaseError.toException());
                }
            });


        }


        //PANTS
        if (spinnertxt.equals("Pants")) {
            int pointsToAdd = 30;
            int waterToAdd = 800;
            double cottonToAdd = 0.78;

            //  points
            reference.child("User").child(getCurrentUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String currentPointsStr = dataSnapshot.child("points").getValue(String.class);
                        if (currentPointsStr != null) {
                            int currentPoints = Integer.parseInt(currentPointsStr);
                            int updatedPoints = currentPoints + pointsToAdd;
                            reference.child("User").child(getCurrentUserId()).child("points").setValue(String.valueOf(updatedPoints));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("DatabaseError", "Error updating data", databaseError.toException());
                }
            });

            // WATER AND COTTON
            reference.child("User").child(getCurrentUserId()).child("water").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String currentWaterUsageStr = dataSnapshot.getValue(String.class);
                        if (currentWaterUsageStr != null) {
                            int currentWaterUsage = Integer.parseInt(currentWaterUsageStr);
                            int updatedWaterUsage = currentWaterUsage + waterToAdd;
                            reference.child("User").child(getCurrentUserId()).child("water").setValue(String.valueOf(updatedWaterUsage));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("DatabaseError", "Error updating data", databaseError.toException());
                }
            });

            reference.child("User").child(getCurrentUserId()).child("cotton").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String currentCottonUsageStr = dataSnapshot.getValue(String.class);
                        if (currentCottonUsageStr != null) {
                            double currentCottonUsage = Double.parseDouble(currentCottonUsageStr);
                            double updatedCottonUsage = currentCottonUsage + cottonToAdd;
                            reference.child("User").child(getCurrentUserId()).child("cotton").setValue(String.valueOf(updatedCottonUsage));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("DatabaseError", "Error updating data", databaseError.toException());
                }
            });


        }


        //JACKETS
        if (spinnertxt.equals("Jackets")) {
            int pointsToAdd = 40;
            int waterToAdd = 884;
            double cottonToAdd = 0.94;

            //  points
            reference.child("User").child(getCurrentUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String currentPointsStr = dataSnapshot.child("points").getValue(String.class);
                        if (currentPointsStr != null) {
                            int currentPoints = Integer.parseInt(currentPointsStr);
                            int updatedPoints = currentPoints + pointsToAdd;
                            reference.child("User").child(getCurrentUserId()).child("points").setValue(String.valueOf(updatedPoints));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("DatabaseError", "Error updating data", databaseError.toException());
                }
            });

            // WATER AND COTTON
            reference.child("User").child(getCurrentUserId()).child("water").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String currentWaterUsageStr = dataSnapshot.getValue(String.class);
                        if (currentWaterUsageStr != null) {
                            int currentWaterUsage = Integer.parseInt(currentWaterUsageStr);
                            int updatedWaterUsage = currentWaterUsage + waterToAdd;
                            reference.child("User").child(getCurrentUserId()).child("water").setValue(String.valueOf(updatedWaterUsage));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("DatabaseError", "Error updating data", databaseError.toException());
                }
            });

            reference.child("User").child(getCurrentUserId()).child("cotton").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String currentCottonUsageStr = dataSnapshot.getValue(String.class);
                        if (currentCottonUsageStr != null) {
                            double currentCottonUsage = Double.parseDouble(currentCottonUsageStr);
                            double updatedCottonUsage = currentCottonUsage + cottonToAdd;
                            reference.child("User").child(getCurrentUserId()).child("cotton").setValue(String.valueOf(updatedCottonUsage));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("DatabaseError", "Error updating data", databaseError.toException());
                }
            });


        }


        //Denim
        if (spinnertxt.equals("Shirts")) {
            int pointsToAdd = 60;
            int waterToAdd = 2600;
            double cottonToAdd = 1.5;

            //  points
            reference.child("User").child(getCurrentUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String currentPointsStr = dataSnapshot.child("points").getValue(String.class);
                        if (currentPointsStr != null) {
                            int currentPoints = Integer.parseInt(currentPointsStr);
                            int updatedPoints = currentPoints + pointsToAdd;
                            reference.child("User").child(getCurrentUserId()).child("points").setValue(String.valueOf(updatedPoints));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("DatabaseError", "Error updating data", databaseError.toException());
                }
            });

            // WATER AND COTTON
            reference.child("User").child(getCurrentUserId()).child("water").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String currentWaterUsageStr = dataSnapshot.getValue(String.class);
                        if (currentWaterUsageStr != null) {
                            int currentWaterUsage = Integer.parseInt(currentWaterUsageStr);
                            int updatedWaterUsage = currentWaterUsage + waterToAdd;
                            reference.child("User").child(getCurrentUserId()).child("water").setValue(String.valueOf(updatedWaterUsage));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("DatabaseError", "Error updating data", databaseError.toException());
                }
            });

            reference.child("User").child(getCurrentUserId()).child("cotton").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String currentCottonUsageStr = dataSnapshot.getValue(String.class);
                        if (currentCottonUsageStr != null) {
                            double currentCottonUsage = Double.parseDouble(currentCottonUsageStr);
                            double updatedCottonUsage = currentCottonUsage + cottonToAdd;
                            reference.child("User").child(getCurrentUserId()).child("cotton").setValue(String.valueOf(updatedCottonUsage));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("DatabaseError", "Error updating data", databaseError.toException());
                }
            });


        }


        //Shoes and Accessories
        if (spinnertxt.equals("Shoes") || spinnertxt.equals("Accessories")) {
            int pointsToAdd = 10;
            int waterToAdd = 880;
            double cottonToAdd = 0.38;

            //  points
            reference.child("User").child(getCurrentUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String currentPointsStr = dataSnapshot.child("points").getValue(String.class);
                        if (currentPointsStr != null) {
                            int currentPoints = Integer.parseInt(currentPointsStr);
                            int updatedPoints = currentPoints + pointsToAdd;
                            reference.child("User").child(getCurrentUserId()).child("points").setValue(String.valueOf(updatedPoints));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("DatabaseError", "Error updating data", databaseError.toException());
                }
            });

            // WATER AND COTTON
            reference.child("User").child(getCurrentUserId()).child("water").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String currentWaterUsageStr = dataSnapshot.getValue(String.class);
                        if (currentWaterUsageStr != null) {
                            int currentWaterUsage = Integer.parseInt(currentWaterUsageStr);
                            int updatedWaterUsage = currentWaterUsage + waterToAdd;
                            reference.child("User").child(getCurrentUserId()).child("water").setValue(String.valueOf(updatedWaterUsage));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("DatabaseError", "Error updating data", databaseError.toException());
                }
            });

            reference.child("User").child(getCurrentUserId()).child("cotton").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String currentCottonUsageStr = dataSnapshot.getValue(String.class);
                        if (currentCottonUsageStr != null) {
                            double currentCottonUsage = Double.parseDouble(currentCottonUsageStr);
                            double updatedCottonUsage = currentCottonUsage + cottonToAdd;
                            reference.child("User").child(getCurrentUserId()).child("cotton").setValue(String.valueOf(updatedCottonUsage));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("DatabaseError", "Error updating data", databaseError.toException());
                }
            });

        }

    }
    private String getCurrentUserId() {
        return MainActivity.currentuser;
    }

}