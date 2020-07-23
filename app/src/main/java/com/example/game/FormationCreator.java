package com.example.game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import com.example.game.adapters.RecyclerViewAdapter;
import com.example.game.functions.GlobalProfile;
import com.example.game.functions.RecyclerItemClickListener;
import com.example.game.objects.GameBoard;
import com.example.game.objects.UnitsNotPlaced;
import com.example.game.objects.units.Unit;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FormationCreator extends AppCompatActivity {

    RecyclerView recycler_Units;

    TableLayout table_formation;

    RecyclerViewAdapter adapter;

    Button btn_confirm;

    GameBoard gameBoard;

    View selectedView;

    UnitsNotPlaced unitsNotPlaced = new UnitsNotPlaced();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formation_creator);

        /*
        Shield unit1 = new Shield();
        Shield unit2 = new Shield();
        Archer unit3 = new Archer();
        Archer unit4 = new Archer();
        Knight unit5 = new Knight();
        Knight unit6 = new Knight();
        Mage unit7 = new Mage();
        Mage unit8 = new Mage();
        DarkKnight unit9 = new DarkKnight();

        Collections.addAll(GlobalProfile.getGlobalProfile(FormationCreator.this).getUnits(), unit1, unit2, unit3, unit4, unit5, unit6, unit7, unit8, unit9);

         */

        Dialog dialog = LoadingDialog.create(this, "Loading...");
        dialog.show();

        getUnitsFromDatabase();

        dialog.cancel();

        recycler_Units = findViewById(R.id.recycler_Units);

        table_formation = findViewById(R.id.table_formation);

        gameBoard  = new GameBoard(this, table_formation);

        btn_confirm = findViewById(R.id.btn_confirm);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GlobalProfile.getGlobalProfile(FormationCreator.this).setArrayListFormation(gameBoard.getArrayListGameBoard());

                DatabaseReference databaseReferenceFormation = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Profile").child("formation");
                databaseReferenceFormation.setValue(GlobalProfile.getGlobalProfile(FormationCreator.this).getArrayListFormation());

                Intent intent = new Intent(FormationCreator.this, MainMenu.class);
                startActivity(intent);
            }
        });

        //On click listener for all units
        recycler_Units.addOnItemTouchListener(new RecyclerItemClickListener(this, recycler_Units , new RecyclerItemClickListener.OnItemClickListener()
                {
                    @Override public void onItemClick(View view, int position)
                    {

                        //Deselect Unit
                        if (unitsNotPlaced.getIndexUnitSelected() == position)
                        {
                            ((CardView) view).setCardBackgroundColor(getResources().getColor(R.color.card_unit_background));
                            unitsNotPlaced.setIndexUnitSelected(-1);
                            gameBoard.showDefaultTiles();
                        }

                        //User clicks on different unit
                        else if (unitsNotPlaced.getIndexUnitSelected() != position && unitsNotPlaced.getIndexUnitSelected() != -1)
                        {
                            ((CardView) selectedView).setCardBackgroundColor(getResources().getColor(R.color.card_unit_background));
                            unitsNotPlaced.setIndexUnitSelected(position);

                            ((CardView) view).setCardBackgroundColor(getResources().getColor(R.color.unitSelected));

                            gameBoard.showAvailableTiles();
                            gameBoard.waitForUnitPlaceFormation(unitsNotPlaced, adapter, recycler_Units);
                        }

                        //Unit is selecting a tile
                        else
                        {
                            ((CardView) view).setCardBackgroundColor(getResources().getColor(R.color.unitSelected));

                            unitsNotPlaced.setIndexUnitSelected(position);

                            gameBoard.showAvailableTiles();
                            gameBoard.waitForUnitPlaceFormation(unitsNotPlaced, adapter, recycler_Units);
                        }

                        selectedView = view;
                    }

                    @Override public void onLongItemClick(View view, int position) {
                    }
                })
        );
    }

    public RecyclerViewAdapter initializeRecyclerUnits()
    {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, unitsNotPlaced.getArrayListUnitsNotPlaced());
        recycler_Units.setAdapter(adapter);

        recycler_Units.setLayoutManager(new GridLayoutManager(this,3));
        return adapter;

    }

    public void getUnitsFromDatabase()
    {
        final DatabaseReference databaseReferenceUnits = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Profile").child("units");

        databaseReferenceUnits.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                GenericTypeIndicator<ArrayList<Unit>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<Unit>>() {};

                GlobalProfile.getGlobalProfile(FormationCreator.this).setUnits(dataSnapshot.getValue(genericTypeIndicator));

                unitsNotPlaced.setArrayListUnitsNotPlaced(GlobalProfile.getGlobalProfile(FormationCreator.this).getUnits());

                adapter = initializeRecyclerUnits();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
