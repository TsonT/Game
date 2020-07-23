package com.example.game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.game.functions.GlobalProfile;
import com.example.game.objects.Recruiter;
import com.example.game.objects.units.Unit;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Recruit extends AppCompatActivity {

    Integer costForRecruit = 5;

    TextView text_Health, text_Damage, text_Range, text_attackType, text_Rarity, text_ability;
    ImageView image_Unit;
    Button btn_Recruit;
    LinearLayout cardLayout;
    Unit unit;
    Menu actionbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruit);

        getGoldFromDatabase();

        text_Rarity = findViewById(R.id.text_Rarity);
        text_Health = findViewById(R.id.text_Health);
        text_Damage = findViewById(R.id.text_Damage);
        text_Range = findViewById(R.id.text_Range);
        text_attackType = findViewById(R.id.text_attackType);
        text_ability = findViewById(R.id.text_ability);
        image_Unit = findViewById(R.id.image_Unit);
        btn_Recruit = findViewById(R.id.btn_recruit);
        cardLayout = findViewById(R.id.cardLayout);

        btn_Recruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (hasSufficientFunds())
                {
                    Recruiter recruiter = new Recruiter();
                    unit = recruiter.getRecruit();

                    displayUnit();

                    addUnitToDatabase(unit);

                    updateGold();
                }
                else
                {
                    Toast.makeText(Recruit.this, "Not Enough Gold!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.gold_menu, menu);
        menu.getItem(1).setTitle(GlobalProfile.getGlobalProfile(Recruit.this).getGold().toString());
        actionbar = menu;

        return true;
    }


    public void addUnitToDatabase(Unit unit)
    {
        GlobalProfile.getGlobalProfile(Recruit.this).getUnits().add(unit);

        DatabaseReference databaseReferenceFormation = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Profile").child("units");
        databaseReferenceFormation.setValue(GlobalProfile.getGlobalProfile(Recruit.this).getUnits());
    }

    public boolean hasSufficientFunds()
    {
        Integer currentGold;

        currentGold = GlobalProfile.getGlobalProfile(Recruit.this).getGold();

        if (currentGold >= 5)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void displayUnit()
    {
        cardLayout.setVisibility(View.VISIBLE);
        text_Rarity.setText(unit.getRarity().toString());
        switch (unit.getRarity())
        {
            case COMMON:
                text_Rarity.setTextColor(getResources().getColor(R.color.common));
                break;
            case UNCOMMON:
                text_Rarity.setTextColor(getResources().getColor(R.color.uncommon));
                break;
            case RARE:
                text_Rarity.setTextColor(getResources().getColor(R.color.rare));
                break;
            case LEGENDARY:
                text_Rarity.setTextColor(getResources().getColor(R.color.legendary));
                break;
        }
        text_Health.setText(unit.getMaxHealth().toString());
        text_Damage.setText(unit.getDamage().toString());
        text_Range.setText(unit.getRange().toString());
        text_attackType.setText(unit.getAttackType().toString());
        text_ability.setText(unit.getAbility());
        image_Unit.setImageResource(unit.getImage());
    }

    public void updateGold()
    {
        Integer currentGold = GlobalProfile.getGlobalProfile(Recruit.this).getGold();
        GlobalProfile.getGlobalProfile(Recruit.this).setGold(currentGold - costForRecruit);

        DatabaseReference databaseReferenceGold = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Profile").child("gold");
        databaseReferenceGold.setValue(GlobalProfile.getGlobalProfile(Recruit.this).getGold());

        actionbar.getItem(1).setTitle(GlobalProfile.getGlobalProfile(Recruit.this).getGold().toString());
    }

    public void getGoldFromDatabase()
    {

        final Dialog dialog = LoadingDialog.create(this, "Loading...");
        dialog.show();

        DatabaseReference databaseReferenceGold = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Profile").child("gold");

        databaseReferenceGold.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GlobalProfile.getGlobalProfile(Recruit.this).setGold(dataSnapshot.getValue(Integer.class));
                dialog.cancel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

}
