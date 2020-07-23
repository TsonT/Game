package com.example.game.objects;

import com.example.game.enumerations.Turn;
import com.example.game.objects.units.Unit;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;

public class Profile {

    private String Username;
    private String Uid;
    private Integer Gold = 0;
    private ArrayList<Unit> Units = new ArrayList<>();
    private ArrayList<ArrayList<Tile>> arrayListFormation = new ArrayList<ArrayList<Tile>>();
    @Exclude
    private Turn playerNumber;

    public Profile(String username, String uid) {
        Username = username;
        Uid = uid;
    }

    public Profile()
    {

    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public Integer getGold() {
        return Gold;
    }

    public void setGold(Integer gold) {
        Gold = gold;
    }

    public ArrayList<Unit> getUnits() {
        return Units;
    }

    public void setUnits(ArrayList<Unit> units) {
        Units = units;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public ArrayList<ArrayList<Tile>> getArrayListFormation() {
        
        return arrayListFormation;
    }

    public void setArrayListFormation(ArrayList<ArrayList<Tile>> arrayListFormation) {

        this.arrayListFormation = arrayListFormation;
    }

    @Exclude
    public Turn getPlayerNumber() {
        return playerNumber;
    }

    @Exclude
    public void setPlayerNumber(Turn playerNumber) {
        this.playerNumber = playerNumber;
    }
}
