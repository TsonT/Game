package com.example.game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.game.objects.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    Button  btn_submit;
    EditText edit_email, edit_password, edit_username;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");

    String Email, Password, Username, Uid;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_submit = findViewById(R.id.btn_Submit);
        edit_email = findViewById(R.id.edit_email);
        edit_password = findViewById(R.id.edit_password);
        edit_username = findViewById(R.id.edit_username);

        auth = FirebaseAuth.getInstance();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email = edit_email.getText().toString();
                Password = edit_password.getText().toString();
                Username = edit_username.getText().toString();

                auth.createUserWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                    Uid = auth.getCurrentUser().getUid();
                                    Profile profile = new Profile(Username, Uid);

                                    profile.setGold(20);

                                    reference.child(Uid).child("Profile").setValue(profile);

                                    Intent intent = new Intent(Register.this, LogIn.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(Register.this, "Failed" + task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

}
