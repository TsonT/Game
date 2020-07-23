package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import com.example.game.functions.GlobalProfile;
import com.example.game.objects.Profile;

public class MainMenu extends AppCompatActivity {

    Button btn_formation, btn_pvp, btn_recruit;

    Profile profile;

    Menu actionbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profile = GlobalProfile.getGlobalProfile(MainMenu.this);

        btn_formation = findViewById(R.id.btn_formation);
        btn_pvp = findViewById(R.id.btn_pvp);
        btn_recruit = findViewById(R.id.btn_recruit);

        btn_formation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, FormationCreator.class);
                startActivity(intent);
            }
        });

        btn_pvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, MatchMaking.class);
                startActivity(intent);
            }
        });

        btn_recruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, Recruit.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.gold_menu, menu);
        menu.getItem(1).setTitle(GlobalProfile.getGlobalProfile(MainMenu.this).getGold().toString());
        actionbar = menu;

        return true;
    }

}
