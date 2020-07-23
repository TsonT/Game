package com.example.game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.game.enumerations.Turn;
import com.example.game.functions.GlobalProfile;
import com.example.game.objects.Match;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MatchMaking extends AppCompatActivity {

    String matchKey;

    TextView text_progress;

    DatabaseReference dataMatches = FirebaseDatabase.getInstance().getReference().child("Matches");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_making);

        text_progress = findViewById(R.id.text_progress);

        dataMatches.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0)
                {
                    boolean matchFound = false;

                    for (DataSnapshot snapshot: dataSnapshot.getChildren())
                    {
                        if (snapshot.child("available").getValue(Boolean.class) == true)
                        {
                            matchFoundPlayer2(snapshot);
                            matchFound = true;
                            break;
                        }
                        else if (snapshot.child("available").getValue(Boolean.class) == false)
                        {
                            //Do nothing
                        }
                    }

                    if (!matchFound)
                    {
                        createMatch();
                    }
                }
                else
                {
                    createMatch();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void matchFoundPlayer2(final DataSnapshot snapshot)
    {
        matchKey = snapshot.getKey();


        final DatabaseReference currentMatch = dataMatches.child(matchKey);


        GlobalProfile.getGlobalProfile(MatchMaking.this).setPlayerNumber(Turn.PLAYER2);

        currentMatch.child("currentMatch").child("player2profile").setValue(GlobalProfile.getGlobalProfile(MatchMaking.this))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        currentMatch.child("available").setValue(false)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        text_progress.setText("Match Found!");

                                        Intent intent = new Intent(MatchMaking.this, BattleField.class);
                                        intent.putExtra("matchKey", matchKey);
                                        startActivity(intent);
                                    }
                                });
                    }
                });
    }

    public void createMatch()
    {
        matchKey = dataMatches.push().getKey();

        final DatabaseReference currentMatch = dataMatches.child(matchKey);
        currentMatch.child("available").setValue(null);
        Match match = new Match();
        GlobalProfile.getGlobalProfile(MatchMaking.this).setPlayerNumber(Turn.PLAYER1);

        currentMatch.child("currentMatch").setValue(match)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        currentMatch.child("currentMatch").child("player1profile").setValue(GlobalProfile.getGlobalProfile(MatchMaking.this))
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        currentMatch.child("available").setValue(true)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        currentMatch.child("available").addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                Boolean isAvailable = dataSnapshot.getValue(Boolean.class);

                                                                try
                                                                {
                                                                    if (!isAvailable)
                                                                    {
                                                                        matchFoundPlayer1();
                                                                    }
                                                                }
                                                                catch (Exception e)
                                                                {

                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                            }
                                                        });
                                                    }
                                                });
                                    }
                                });
                    }
                });


    }

    public void matchFoundPlayer1()
    {
        text_progress.setText("Match Found!");

        Intent intent = new Intent(MatchMaking.this, BattleField.class);
        intent.putExtra("matchKey", matchKey);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        DatabaseReference currentMatch = dataMatches.child(matchKey);
        currentMatch.removeValue();

    }
}
