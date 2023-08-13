package com.firstapp.kyecothreads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class MainActivity extends AppCompatActivity {

    TextInputLayout phoneuser, password;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ecothreads-5b2eb-default-rtdb.firebaseio.com/");
    FirebaseDatabase rootNode;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    Button register,login;

    public static String currentuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register=findViewById(R.id.register);
        phoneuser=findViewById(R.id.username);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        FirebaseApp.initializeApp(this);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, register.class));

            }
        });

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){


                rootNode = FirebaseDatabase.getInstance();
                //reference = rootNode.getReference("User");
                String phone = phoneuser.getEditText().getText().toString();
                String pass = password.getEditText().getText().toString();



                if(phone.isEmpty()||pass.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please fill phone number or password", Toast.LENGTH_SHORT).show();
                }

                else{
                    reference.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.hasChild(phone)) {
                                DataSnapshot userSnapshot = snapshot.child(phone);
                                Log.d("User", userSnapshot.getValue().toString());
                                String getpass = userSnapshot.child("password").getValue(String.class);
                                if (getpass.equals(pass)) {
                                    Toast.makeText(MainActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                                    MainActivity.currentuser = phone;
                                    startActivity(new Intent(MainActivity.this, Home.class));
                                    finish();
                                } else {
                                    Toast.makeText(MainActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });


    }
}