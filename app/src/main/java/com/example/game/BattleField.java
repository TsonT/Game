package com.example.game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.game.enumerations.StatusEffectType;
import com.example.game.enumerations.Turn;
import com.example.game.functions.GlobalProfile;
import com.example.game.objects.GameBoard;
import com.example.game.objects.Match;
import com.example.game.objects.Profile;
import com.example.game.objects.Tile;
import com.example.game.objects.statuseffects.StatusEffect;
import com.example.game.objects.units.Unit;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class BattleField extends AppCompatActivity {

    TableLayout table_opponent;
    TableLayout table_player;
    GameBoard playerBoard, opponentBoard;
    String matchKey;
    DatabaseReference currentMatch;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    TextView text_turn, text_versus, text_winner;
    String Uid;
    ArrayList<Tile> vulnerableUnits;
    Boolean isTurn;
    Match match;
    Profile profile;
    DatabaseReference refWinner, refPlayerProfile;
    Button btn_Info;
    Unit unitInfo;
    KonfettiView view_Confetti;
    LinearLayout parent_layout;
    Integer winnerGold = 10;
    Integer loserGold = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_field);

        text_turn = findViewById(R.id.text_turn);
        text_versus = findViewById(R.id.text_versus);
        table_opponent = findViewById(R.id.table_opponent);
        table_player = findViewById(R.id.table_player);
        matchKey = getIntent().getStringExtra("matchKey");
        currentMatch = FirebaseDatabase.getInstance().getReference().child("Matches").child(matchKey).child("currentMatch");
        refWinner = FirebaseDatabase.getInstance().getReference().child("Matches").child(matchKey).child("currentMatch").child("winner");
        refPlayerProfile = FirebaseDatabase.getInstance().getReference().child("Matches").child(matchKey).child("currentMatch").child("player1profile");
        Uid = auth.getCurrentUser().getUid();
        profile = GlobalProfile.getGlobalProfile(BattleField.this);
        btn_Info = findViewById(R.id.btn_Info);
        btn_Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayUnitInfoDialog(unitInfo);
            }
        });
        view_Confetti = findViewById(R.id.view_Confetti);
        text_winner = findViewById(R.id.text_winner);
        parent_layout = findViewById(R.id.parent_layout);

        playerBoard = new GameBoard(this, table_player);
        opponentBoard = new GameBoard(this, table_opponent);

        currentMatch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("winner").getValue(String.class).equals(""))
                {
                    playerBoard.setTurnNumber(playerBoard.getTurnNumber() + 1);
                    opponentBoard.setTurnNumber(opponentBoard.getTurnNumber() + 1);

                    clearBoardsOnClick();

                    getMatchData();
                }
                else if (dataSnapshot.child("winner").getValue(String.class).equals(profile.getUsername()))
                {
                    DatabaseReference databaseReferenceGold = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Profile").child("gold");

                    databaseReferenceGold.setValue(profile.getGold() + winnerGold);
                }
                else
                {
                    DatabaseReference databaseReferenceGold = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Profile").child("gold");

                    databaseReferenceGold.setValue(profile.getGold() + loserGold);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.battle_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case (R.id.item_Surrender):
                if (text_winner.getVisibility() != View.VISIBLE)
                {
                    surrender();
                }
                break;
            case (R.id.item_pass):
                turnEnd();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getMatchData()
    {
        refWinner.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.getValue().equals(""))
                {
                    clearBoardsOnClick();
                    String winner = dataSnapshot.getValue(String.class);
                    showWinnerDialog(winner);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        currentMatch.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                match = dataSnapshot.getValue(Match.class);

                Gson gson = new Gson();
                String string = gson.toJson(match);
                Log.e("!!!!!!!!!!!", string);
                displayUserNames();
                populateBoards();
                checkTurn();

                if (isTurn)
                {
                    makePlayerUnitsClickable();
                }

                makeOpponentUnitsClickableForInfo();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void populateBoards()
    {

        if (Uid.equals(match.getPlayer1profile().getUid()))
        {
            playerBoard.populateBoardForPlayer(match.getPlayer1profile().getArrayListFormation());
        }
        else
        {
            opponentBoard.populateBoardForOpponent(match.getPlayer1profile().getArrayListFormation());
        }

        if (Uid.equals(match.getPlayer2profile().getUid()))
        {
            playerBoard.populateBoardForPlayer(match.getPlayer2profile().getArrayListFormation());
        }
        else
        {
            opponentBoard.populateBoardForOpponent(match.getPlayer2profile().getArrayListFormation());
        }

    }

    public void checkTurn()
    {

        if (GlobalProfile.getGlobalProfile(BattleField.this).getPlayerNumber() == match.getTurn())
        {
            isTurn = true;
            text_turn.setText("Your Turn!");
            text_turn.setTextColor(this.getColor(R.color.textYourTurn));

        }
        else
        {
            isTurn = false;
            text_turn.setText("Waiting For Opponent...");
            text_turn.setTextColor(this.getColor(R.color.textWaiting));
        }
    }

    public void displayUserNames()
    {
        String name1, name2;
        name1 = match.getPlayer1profile().getUsername();
        name2 = match.getPlayer2profile().getUsername();

        text_versus.setText(name1 + " vs. " + name2);
    }

    public void makePlayerUnitsClickable()
    {
        ArrayList<Tile> arrayListOccupiedTiles;
        arrayListOccupiedTiles = playerBoard.getTilesOccupied();

        for (final Tile tile : arrayListOccupiedTiles)
        {
            Boolean isDisabled = false;

            for (StatusEffect statusEffect : tile.getUnit().getInflictedStatusEffectArrayList())
            {
                if (statusEffect.sameStatusEffect(StatusEffectType.DISABLED) && statusEffect.getRoundsAffected() > 0)
                {
                    isDisabled = true;
                    statusEffect.setRoundsAffected(statusEffect.getRoundsAffected() - 1);
                }
            }

            if (!isDisabled)
            {
                tile.getLinearLayout().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (playerBoard.getSelectedTile() != null && playerBoard.getSelectedTile().getUnit() != null)
                        {
                            //If player clicks on a different unit
                            if (playerBoard.getSelectedTile().getOccupied() == true && !playerBoard.getSelectedTile().getUnit().equals(tile.getUnit()))
                            {
                                playerBoard.getSelectedTile().clearColors();
                                opponentBoard.clearVulnerability(tile.getUnit());
                                clearOnClick(vulnerableUnits);
                                unitSelected(tile);
                            }
                            //If player clicks on already selected unit
                            else if (playerBoard.getSelectedTile().getUnit().equals(tile.getUnit()))
                            {
                                opponentBoard.clearVulnerability(tile.getUnit());
                                playerBoard.getSelectedTile().clearColors();
                                playerBoard.setSelectedTile(null);
                                btn_Info.setEnabled(false);
                                clearOnClick(vulnerableUnits);
                            }
                            else
                            {
                                unitSelected(tile);
                            }
                        }
                        else
                        {
                            unitSelected(tile);
                        }

                    }
                });
            }
            else
            {

            }

        }
    }

    public void makeOpponentUnitsClickableForAttack(ArrayList<Tile> vulnerableUnits, final Tile attackingTile)
    {
        for (final Tile tile : vulnerableUnits)
        {
            tile.getLinearLayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tile.opponentUnitAttacked(attackingTile.getUnit());
                    turnEnd();
                }
            });
        }
    }

    public void makeOpponentUnitsClickableForInfo()
    {
        ArrayList<Tile> occupiedTiles = opponentBoard.getTilesOccupied();

        for (final Tile tile : occupiedTiles)
        {
            tile.getLinearLayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayUnitInfoDialog(tile.getUnit());
                }
            });
        }
    }

    public void turnEnd()
    {

        Boolean isWinner = isWinner();

        if (isWinner)
        {
            refWinner.setValue(profile.getUsername());
        }
        else
        {

            updateFormations();
            updateTurn();

            currentMatch.setValue(match);
        }

        clearBoardsOnClick();
    }

    public void updateFormations()
    {
        if (profile.getPlayerNumber() == Turn.PLAYER1)
        {
            match.getPlayer1profile().setArrayListFormation(playerBoard.getArrayListGameBoard());
        }
        else
        {
            match.getPlayer1profile().setArrayListFormation(opponentBoard.getArrayListGameBoard());
        }

        if (profile.getPlayerNumber() == Turn.PLAYER2)
        {
            match.getPlayer2profile().setArrayListFormation(playerBoard.getArrayListGameBoard());
        }
        else
        {
            match.getPlayer2profile().setArrayListFormation(opponentBoard.getArrayListGameBoard());
        }
    }

    public void updateTurn()
    {
        if (match.getTurn() == Turn.PLAYER1)
        {
            match.setTurn(Turn.PLAYER2);

        }
        else
        {
            match.setTurn(Turn.PLAYER1);
        }
    }

    public boolean isWinner()
    {
        Boolean stillHasUnits = false;

        for (int i = 0; i < opponentBoard.getArrayListGameBoard().size(); i++)
        {
            for (int j = 0; j < opponentBoard.getArrayListGameBoard().get(i).size(); j++)
            {
                Tile tile = opponentBoard.getArrayListGameBoard().get(i).get(j);

                if (tile.getOccupied() == true)
                {
                    stillHasUnits = true;
                }
            }
        }

        if (!stillHasUnits)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    public void clearBoardsOnClick()
    {
        for (ArrayList<Tile> arrayList : playerBoard.getArrayListGameBoard())
        {
            for (Tile tile : arrayList)
            {
                tile.getLinearLayout().setOnClickListener(null);

                if (tile.getUnit() != null)
                {
                    tile.getUnit().getInflictedStatusEffectArrayList().clear();
                }
            }
        }

        for (ArrayList<Tile> arrayList : opponentBoard.getArrayListGameBoard())
        {
            for (Tile tile : arrayList)
            {
                tile.getLinearLayout().setOnClickListener(null);
            }
        }
    }

    public void clearOnClick(ArrayList<Tile> arrayList)
    {
        for (Tile tile : arrayList)
        {
            tile.getLinearLayout().setOnClickListener(null);
        }
    }

    public void displayUnitInfoDialog(Unit unit)
    {
        Dialog_unit_info dialog_unit_info = new Dialog_unit_info(unit);
        dialog_unit_info.show(getSupportFragmentManager(), unit.getName());
    }

    public void unitSelected(Tile tile)
    {
        btn_Info.setEnabled(true);
        unitInfo = tile.getUnit();

        playerBoard.setSelectedTile(tile);

        tile.showUnitSelected();
        Tile attackTile = tile;

        vulnerableUnits = opponentBoard.showOpponentsVulnerableUnits(attackTile);
        makeOpponentUnitsClickableForAttack(vulnerableUnits, attackTile);
    }

    public void surrender()
    {
        String winner;

        if (Uid.equals(match.getPlayer1profile().getUid()))
        {
            winner = match.getPlayer2profile().getUsername();
        }
        else
        {
            winner = match.getPlayer1profile().getUsername();
        }

        refWinner.setValue(winner);
        showWinnerDialog(winner);
    }

    public void showWinnerDialog(String winner)
    {
        view_Confetti.setVisibility(View.VISIBLE);
        view_Confetti.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA, Color.CYAN, Color.RED, Color.WHITE)
            .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
            .setFadeOutEnabled(true)
                .setTimeToLive(1500L)
                .addShapes(Shape.Square.INSTANCE, Shape.Circle.INSTANCE)
                .addSizes(new Size(12, 5))
                .setPosition(0, (float) Resources.getSystem().getDisplayMetrics().widthPixels, 0, (float) -view_Confetti.getHeight())
            .streamFor(300, 10000L);

        text_winner.setText("WINNER: " + winner);
        text_winner.setVisibility(View.VISIBLE);
        parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BattleField.this, MainMenu.class);
                startActivity(intent);
            }
        });

    }
}
