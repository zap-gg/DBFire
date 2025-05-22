package com.example.dbfire;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dbfire.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    String firstName, lastName, username;
    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = binding.firstName.getText().toString();
                lastName = binding.lastName.getText().toString();
                username = binding.username.getText().toString();

                if(!firstName.isEmpty() && !lastName.isEmpty() && !username.isEmpty()){
                    Users users = new Users(firstName, lastName, username);
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Users");

                    reference.child(username).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            binding.firstName.setText("");
                            binding.lastName.setText("");
                            binding.username.setText("");
                            Toast.makeText(MainActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Failed Registration", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Display.class);
                startActivity(intent);
            }
        });

    }
}