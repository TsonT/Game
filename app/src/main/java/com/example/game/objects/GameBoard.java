package com.example.game.objects;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.recyclerview.widget.RecyclerView;

import com.example.game.enumerations.StatusEffectType;
import com.example.game.objects.statuseffects.Burned;
import com.example.game.R;
import com.example.game.adapters.RecyclerViewAdapter;
import com.example.game.objects.statuseffects.StatusEffect;
import com.example.game.objects.units.Unit;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.Collections;

public class GameBoard {

    private ArrayList<ArrayList<Tile>> arrayListGameBoard = new ArrayList<>();
    private Context context;
    private TableLayout tableLayout;
    private Tile selectedTile = new Tile();
    private Integer turnNumber = 0;

    public GameBoard(Context context, TableLayout tableLayout) {
        this.context = context;
        this.tableLayout = tableLayout;

        createBoard();
    }

    public ArrayList<ArrayList<Tile>> getArrayListGameBoard() {

        return arrayListGameBoard;
    }

    public void setArrayListGameBoard(ArrayList<ArrayList<Tile>> arrayListGameBoard) {
        this.arrayListGameBoard = arrayListGameBoard;
    }

    public void createBoard()
    {

        for (int i = 0; i < 3; i++)
        {
            TableRow tableRow = new TableRow(context);
            TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            tableLayoutParams.gravity = Gravity.CENTER;
            tableRow.setLayoutParams(tableLayoutParams);
            tableLayout.addView(tableRow);

            arrayListGameBoard.add(new ArrayList<Tile>());

            for (int j = 0; j < 4; j++)
            {
                //Linear layout holds health bar and tile
                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setBackgroundColor(context.getResources().getColor(R.color.tileDefault));
                TableRow.LayoutParams linearLayoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                linearLayoutParams.setMargins((int) context.getResources().getDimension(R.dimen.linearLayout_margins),(int) context.getResources().getDimension(R.dimen.linearLayout_margins),(int) context.getResources().getDimension(R.dimen.linearLayout_margins),(int) context.getResources().getDimension(R.dimen.linearLayout_margins));
                linearLayout.setLayoutParams(linearLayoutParams);

                //Tile
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams((int) context.getResources().getDimension(R.dimen.tile_width), (int) context.getResources().getDimension(R.dimen.tile_height));
                imageView.setLayoutParams(imageViewParams);

                linearLayout.addView(imageView);
                tableRow.addView(linearLayout);

                Tile tile = new Tile(context, imageView, linearLayout, i, j);
                arrayListGameBoard.get(i).add(tile);
            }
        }

    }

    public void showAvailableTiles()
    {
        for (int i = 0; i < arrayListGameBoard.size(); i++)
        {
            for (int j = 0; j < arrayListGameBoard.get(i).size(); j++)
            {
                Tile tile = arrayListGameBoard.get(i).get(j);

                if(tile.getOccupied() == false)
                {
                    tile.showAvailable();
                }
            }
        }
    }

    public void showDefaultTiles()
    {
        for (int i = 0; i < arrayListGameBoard.size(); i++)
        {
            for (int j = 0; j < arrayListGameBoard.get(i).size(); j++)
            {
                Tile tile = arrayListGameBoard.get(i).get(j);

                if(tile.getOccupied() == false)
                {
                    tile.showDefault();
                }
            }
        }
    }

    public void waitForUnitPlaceFormation(final UnitsNotPlaced unitsNotPlaced, final RecyclerViewAdapter adapter, final RecyclerView recyclerView)
    {
        showAvailableTiles();

        for (int i = 0; i < arrayListGameBoard.size(); i++)
        {
            for (int j = 0; j < arrayListGameBoard.get(i).size(); j++)
            {
                final Tile tile = arrayListGameBoard.get(i).get(j);
                final ImageView imageView = tile.getImageView();

                imageView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        //Tile is unoccupied and a unit is selected
                        if (tile.getOccupied() == false && unitsNotPlaced.getIndexUnitSelected() >= 0)
                        {
                            tile.setUnit(unitsNotPlaced.getArrayListUnitsNotPlaced().get(unitsNotPlaced.getIndexUnitSelected()));
                            unitsNotPlaced.removeSelectedUnit();
                            adapter.notifyItemRemoved(unitsNotPlaced.getIndexUnitSelected());
                        }

                        //Tile is occupied and a unit is selected
                        else if(tile.getOccupied() == true && unitsNotPlaced.getIndexUnitSelected() >= 0)
                        {
                            unitsNotPlaced.getArrayListUnitsNotPlaced().add(tile.getUnit());
                            adapter.notifyItemInserted(unitsNotPlaced.getArrayListUnitsNotPlaced().size() - 1);

                            tile.removeUnit();
                            tile.setUnit(unitsNotPlaced.getArrayListUnitsNotPlaced().get(unitsNotPlaced.getIndexUnitSelected()));
                            unitsNotPlaced.removeSelectedUnit();
                            adapter.notifyItemRemoved(unitsNotPlaced.getIndexUnitSelected());
                        }

                        //Tile is occupied and no unit is selected
                        else if (tile.getOccupied() == true && unitsNotPlaced.getIndexUnitSelected() < 0)
                        {
                            unitsNotPlaced.getArrayListUnitsNotPlaced().add(tile.getUnit());
                            adapter.notifyItemInserted(unitsNotPlaced.getArrayListUnitsNotPlaced().size() - 1);

                            tile.removeUnit();
                        }

                        unitsNotPlaced.setIndexUnitSelected(-1);

                        showDefaultTiles();
                    }

                });
            }
        }

    }

    public void populateBoardForPlayer(ArrayList<ArrayList<Tile>> arrayListPlayerServer)
    {
        for (int i = 0; i < arrayListGameBoard.size(); i++)
        {
            for (int j = 0; j < arrayListGameBoard.get(i).size(); j++)
                 {
                Tile gameTile = arrayListGameBoard.get(i).get(j);
                Tile serverTile = arrayListPlayerServer.get(i).get(j);

                setAdjacentTiles(gameTile, i, j);

                if (serverTile.getOccupied() == false && gameTile.getOccupied() == true)
                {
                    gameTile.removeUnit();
                }

                if (serverTile.getOccupied() == true)
                {
                    gameTile.setUnit(serverTile.getUnit());

                    for (StatusEffect statusEffect : gameTile.getUnit().getInflictedStatusEffectArrayList())
                    {

                    }


                    applyAllyAoeStartingStatusEffects(gameTile);
                    applyAllyInflictedStatusEffects(gameTile);
                }
            }
        }
    }

    public void populateBoardForOpponent(ArrayList<ArrayList<Tile>> arrayListOpponentServer)
    {
        if (turnNumber > 1)
        {
            Collections.reverse(arrayListGameBoard);
        }

        Collections.reverse(arrayListOpponentServer);
        for (int i = 0; i < arrayListGameBoard.size(); i++)
        {
            if (turnNumber > 1)
            {
                Collections.reverse(arrayListGameBoard.get(i));
            }
            Collections.reverse(arrayListOpponentServer.get(i));
            for (int j = 0; j < arrayListGameBoard.get(i).size(); j++)
            {
                Tile gameTile = arrayListGameBoard.get(i).get(j);
                Tile serverTile = arrayListOpponentServer.get(i).get(j);

                if (serverTile.getOccupied() == false && gameTile.getOccupied() == true)
                {
                    gameTile.removeUnit();
                }

                if (serverTile.getOccupied() == true)
                {
                    gameTile.setUnit(serverTile.getUnit());
                }

            }
            Collections.reverse(arrayListGameBoard.get(i));
        }
        Collections.reverse(arrayListGameBoard);

        for (int i = 0; i < arrayListGameBoard.size(); i++)
        {
            for (int j = 0; j < arrayListGameBoard.get(i).size(); j++)
            {
                Tile gameTile = arrayListGameBoard.get(i).get(j);

                setAdjacentTiles(gameTile, i, j);

                if (gameTile.getOccupied() == true)
                {
                    //applyAllyAoeStartingStatusEffects(gameTile);
                    //applyAllyInflictedStatusEffects(gameTile);
                }
            }
        }
    }

    public ArrayList<Tile> getTilesOccupied()
    {
        ArrayList<Tile> arrayListOccupiedTiles = new ArrayList<>();

        for (int i = 0; i < arrayListGameBoard.size(); i++)
        {
            for (int j = 0; j < arrayListGameBoard.get(i).size(); j++)
            {
                Tile tile = arrayListGameBoard.get(i).get(j);

                if (tile.getOccupied() == true)
                {
                    arrayListOccupiedTiles.add(tile);
                }
            }
        }

        return arrayListOccupiedTiles;
    }

    public ArrayList<Tile> showOpponentsVulnerableUnits(Tile tile)
    {
        ArrayList<Tile> unitsVulnerable = new ArrayList<>();

        Unit attackingUnit = tile.getUnit();
        Integer allyRowsInFront = tile.getRow();
        Integer numberOfOpponentRowsVulnerable = attackingUnit.getRange() - allyRowsInFront;

        if (numberOfOpponentRowsVulnerable >= 4)
        {
            numberOfOpponentRowsVulnerable = 3;
        }

        for (int i = 0; i < numberOfOpponentRowsVulnerable ; i++)
        {
            for (int j = 0; j < arrayListGameBoard.get(i).size(); j++)
            {
                Tile opponentTile = arrayListGameBoard.get(i).get(j);

                if (opponentTile.getOccupied() == true)
                {
                   boolean isVulnerable = opponentTile.getUnit().isWithinRange(attackingUnit);

                   if (isVulnerable)
                   {
                       unitsVulnerable.add(opponentTile);
                   }
                }
            }
        }

        return unitsVulnerable;
    }

    public void setAdjacentTiles(Tile gameTile, int i, int j)
    {
        try {gameTile.setLeftTile(arrayListGameBoard.get(i).get(j-1));}
        catch (Exception e) {gameTile.setLeftTile(null);}

        try {gameTile.setRightTile(arrayListGameBoard.get(i).get(j+1));}
        catch (Exception e){gameTile.setRightTile(null);}

        try {gameTile.setTopTile(arrayListGameBoard.get(i-1).get(j));}
        catch (Exception e){gameTile.setTopTile(null);}

        try {gameTile.setBottomTile(arrayListGameBoard.get(i+1).get(j));}
        catch (Exception e){gameTile.setBottomTile(null);}
    }

    public void applyAllyAoeStartingStatusEffects(Tile gameTile)
    {
        Unit unit = gameTile.getUnit();

        for (StatusEffect statusEffect : unit.allyAoeStatusEffectArrayList)
        {
            if (statusEffect.isStartingRoundEffect() && statusEffect.getRoundsAffected() > 0)
            {
                statusEffect.activate();
                statusEffect.setRoundsAffected(statusEffect.getRoundsAffected() - 1);
            }
        }
    }

    public void applyAllyInflictedStatusEffects(Tile gameTile)
    {
        Unit unit = gameTile.getUnit();

        for (StatusEffect statusEffect : unit.getInflictedStatusEffectArrayList())
        {
            if (statusEffect.isStartingRoundEffect() && statusEffect.getRoundsAffected() > 0)
            {
                statusEffect.activate();
                statusEffect.setRoundsAffected(statusEffect.getRoundsAffected() - 1);
            }
        }
    }

    public Tile getSelectedTile() {
        return selectedTile;
    }

    public void setSelectedTile(Tile selectedTile) {

        if (this.selectedTile == selectedTile)
        {
            selectedTile.clearColors();
            selectedTile = new Tile();
        }
        else
        {
            this.selectedTile = selectedTile;
        }
    }

    @Exclude
    public TableLayout getTableLayout() {
        return tableLayout;
    }

    @Exclude
    public void setTableLayout(TableLayout tableLayout) {
        this.tableLayout = tableLayout;
    }

    public Integer getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(Integer turnNumber) {
        this.turnNumber = turnNumber;
    }

    public void clearVulnerability(Unit attackingUnit)
    {
        for (ArrayList<Tile> arrayList : arrayListGameBoard)
        {
            for (Tile tile : arrayList)
            {
                if (tile.getOccupied() == true)
                {
                    tile.getUnit().setVulnerable(false, attackingUnit);
                }
            }
        }

    }

}



