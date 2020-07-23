package com.example.game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.game.functions.GlobalProfile;
import com.example.game.objects.Game;
import com.example.game.objects.Profile;
import com.example.game.objects.Tile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LogIn extends AppCompatActivity {

    Button btn_login, btn_Register;
    EditText edit_email, edit_password;

    String Email, Password;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        btn_login = findViewById(R.id.btn_Login);
        btn_Register = findViewById(R.id.btn_Register);
        edit_email = findViewById(R.id.edit_email);
        edit_password = findViewById(R.id.edit_password);

        if (auth.getCurrentUser() != null)
        {
            logIn();
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email = edit_email.getText().toString();
                Password = edit_password.getText().toString();

                auth.signInWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful())
                                {
                                    Toast.makeText(LogIn.this, "Log In Successful", Toast.LENGTH_SHORT).show();
                                    logIn();
                                }
                                else
                                {
                                    Toast.makeText(LogIn.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this, Register.class);
                startActivity(intent);


            }
        });
    }

    public void logIn()
    {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    if (snapshot.getKey().equals(auth.getCurrentUser().getUid()))
                    {
                        Profile profile = (snapshot.child("Profile").getValue(Profile.class));
                        profile.setArrayListFormation((ArrayList<ArrayList<Tile>>) snapshot.child("Profile").child("formation").getValue());
                        GlobalProfile.setGlobalProfile(LogIn.this, profile);

                        Intent intent = new Intent(LogIn.this, MainMenu.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
