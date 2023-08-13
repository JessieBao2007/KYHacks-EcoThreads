package com.firstapp.kyecothreads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class register extends AppCompatActivity {

    TextInputLayout useremail, userpassword, userphone, nameuser;
    Button register;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register=findViewById(R.id.register);
        useremail=findViewById(R.id.username);
        userpassword=findViewById(R.id.password);
        userphone=findViewById(R.id.phone);
        nameuser=findViewById(R.id.name);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("User");

                String email = useremail.getEditText().getText().toString();
                String password = userpassword.getEditText().getText().toString();
                String phone = userphone.getEditText().getText().toString();
                String name = nameuser.getEditText().getText().toString();
                String water= String.valueOf(0);
                String points= String.valueOf(0);
                String cotton=String.valueOf(0);
                userdata helperClass = new userdata(email, password, phone, name, water, cotton, points);


                if(email.isEmpty()||password.isEmpty()||phone.isEmpty()||name.isEmpty()){
                    Toast.makeText(register.this, "Please fill all fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    reference.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(phone)){
                                Toast.makeText(register.this, "Phone taken",Toast.LENGTH_SHORT).show();
                            }

                            else{
                                reference.child(phone).setValue(helperClass);
                                Toast.makeText(register.this,"Registered successfully",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }


                //String emailwork = normalemail(email);
            }
        });


    }
}