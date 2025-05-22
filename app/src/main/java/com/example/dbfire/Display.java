package com.example.dbfire;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dbfire.databinding.ActivityDisplayBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Display extends AppCompatActivity {
    ActivityDisplayBinding binding;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDisplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        reference = FirebaseDatabase.getInstance().getReference("Users");

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String search = binding.search.getText().toString();
                if (!search.isEmpty()) {
                    reference.child(search).get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DataSnapshot dataSnapshot = task.getResult();
                            if (dataSnapshot.exists()) {
                                String firstName = dataSnapshot.child("firstName").getValue(String.class);
                                String lastName = dataSnapshot.child("lastName").getValue(String.class);
                                Toast.makeText(Display.this, "User Found", Toast.LENGTH_SHORT).show();
                                binding.txtFirstName.setText(firstName);
                                binding.txtLastName.setText(lastName);
                            } else {
                                Toast.makeText(Display.this, "User not found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Display.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(Display.this, "Enter a username", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}