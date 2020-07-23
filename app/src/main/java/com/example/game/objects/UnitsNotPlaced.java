package com.example.game.objects;

import com.example.game.objects.units.Unit;

import java.util.ArrayList;

public class UnitsNotPlaced {

    private Integer indexUnitSelected = -1;
    private ArrayList<Unit> arrayListUnitsNotPlaced = new ArrayList<>();

    public Integer getIndexUnitSelected() {
        return indexUnitSelected;
    }

    public void setIndexUnitSelected(Integer indexUnitSelected) {
        this.indexUnitSelected = indexUnitSelected;
    }

    public ArrayList<Unit> getArrayListUnitsNotPlaced() {
        return arrayListUnitsNotPlaced;
    }

    public void setArrayListUnitsNotPlaced(ArrayList<Unit> arrayListUnitsNotPlaced) {
        this.arrayListUnitsNotPlaced = arrayListUnitsNotPlaced;
    }

    public void removeSelectedUnit()
    {
        arrayListUnitsNotPlaced.remove((int) indexUnitSelected);
    }
}
