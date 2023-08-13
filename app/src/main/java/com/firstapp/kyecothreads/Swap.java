package com.firstapp.kyecothreads;


import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Swap extends AppCompatActivity {

    private LinearLayout entriesLayout;
    private ArrayList<DonationEntries> donationEntriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap2);

        entriesLayout = findViewById(R.id.entriesLayout);
        donationEntriesList = getIntent().getParcelableArrayListExtra("donationEntriesList");

        displayFilteredItems(null);

        Button allBtn = findViewById(R.id.allBtn);
        Button shirtBtn = findViewById(R.id.shirtBtn);
        Button pantBtn = findViewById(R.id.pantBtn);
        Button jacketBtn = findViewById(R.id.jacketBtn);
        Button denimBtn = findViewById(R.id.denimBtn);
        Button shoeBtn = findViewById(R.id.shoeBtn);
        Button accBtn = findViewById(R.id.accBtn);

        allBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFilteredItems(null); // Show all items
            }
        });

        shirtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFilteredItems("Shirts");
            }
        });

        pantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFilteredItems("Pants");
            }
        });

        jacketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFilteredItems("Jackets");
            }
        });

        denimBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFilteredItems("Denim");
            }
        });

        shoeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFilteredItems("Shoes");
            }
        });

        accBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFilteredItems("Accessories");
            }
        });
    }

    private void displayFilteredItems(String clothingType) {
        entriesLayout.removeAllViews();

        for (DonationEntries entry : donationEntriesList) {
            if (clothingType == null || entry.getClothingType().equals(clothingType)) {
                View entryView = LayoutInflater.from(this).inflate(R.layout.entry_layout, entriesLayout, false);
                TextView entryInfoTextView = entryView.findViewById(R.id.entryInfoTextView);
                ImageView entryImageView = entryView.findViewById(R.id.entryImageView);

                String entryInfo = "Title: " + entry.getTitle() +
                        "\nQuantity: " + entry.getQuantity() +
                        "\nClothing Type: " + entry.getClothingType();
                entryInfoTextView.setText(entryInfo);

                Uri imageUri = entry.getImageUri();
                if (imageUri != null) {
                    entryImageView.setImageURI(imageUri);
                }

                Button claimButton = entryView.findViewById(R.id.claimBtn);

                claimButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        entryView.setBackgroundResource(R.drawable.claimed_bg);
                        claimButton.setText("Claimed!");
                    }
                });

                entriesLayout.addView(entryView);
            }
        }
    }
}