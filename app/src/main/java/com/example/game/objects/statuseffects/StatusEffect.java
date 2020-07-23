package com.example.game.objects.statuseffects;

import android.os.AsyncTask;
import android.util.Log;

import com.example.game.enumerations.StatusEffectType;
import com.example.game.objects.Tile;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;

public class StatusEffect {

    public StatusEffectType statusEffectType;
    public boolean affectsEnemies;
    public boolean affectsAllies;
    public boolean affectsDefender;
    public boolean affectsAttacker;
    public boolean isGamePlayEffect;
    public boolean isStartingRoundEffect;
    public boolean isOnHitEffect;
    public Integer rangeLeft = 0;
    public Integer rangeRight = 0;
    public Integer rangeUp = 0;
    public Integer rangeDown = 0;
    public Integer damage = 0;
    public boolean affectsSelf;
    //Starting the next round
    public Integer roundsAffected;
    @Exclude
    public Tile inflictingTile;
    @Exclude
    public Tile inflictedTile;
    public ArrayList<Tile> tilesEffected = new ArrayList<>();

    public StatusEffect() {
    }

    public StatusEffectType getStatusEffectType() {
        return statusEffectType;
    }

    public void setStatusEffectType(StatusEffectType statusEffectType) {
        this.statusEffectType = statusEffectType;
    }

    public boolean isAffectsEnemies() {
        return affectsEnemies;
    }

    public void setAffectsEnemies(boolean affectsEnemies) {
        this.affectsEnemies = affectsEnemies;
    }

    public boolean isAffectsAllies() {
        return affectsAllies;
    }

    public void setAffectsAllies(boolean affectsAllies) {
        this.affectsAllies = affectsAllies;
    }

    public boolean isGamePlayEffect() {
        return isGamePlayEffect;
    }

    public void setGamePlayEffect(boolean gamePlayEffect) {
        isGamePlayEffect = gamePlayEffect;
    }

    public boolean isStartingRoundEffect() {
        return isStartingRoundEffect;
    }

    public void setStartingRoundEffect(boolean startingRoundEffect) {
        isStartingRoundEffect = startingRoundEffect;
    }

    public ArrayList<Tile> getTilesEffected() {
        return tilesEffected;
    }

    public void setTilesEffected(ArrayList<Tile> tilesEffected) {
        this.tilesEffected = tilesEffected;
    }

    public void inflictStatusEffect()
    {
    }

    public Integer getRangeLeft() {
        return rangeLeft;
    }

    public void setRangeLeft(Integer rangeLeft) {
        this.rangeLeft = rangeLeft;
    }

    public Integer getRangeRight() {
        return rangeRight;
    }

    public void setRangeRight(Integer rangeRight) {
        this.rangeRight = rangeRight;
    }

    public Integer getRangeUp() {
        return rangeUp;
    }

    public void setRangeUp(Integer rangeUp) {
        this.rangeUp = rangeUp;
    }

    public Integer getRangeDown() {
        return rangeDown;
    }

    public void setRangeDown(Integer rangeDown) {
        this.rangeDown = rangeDown;
    }

    @Exclude
    public Tile getInflictingTile() {
        return inflictingTile;
    }

    @Exclude
    public void setInflictingTile(Tile inflictingTile) {
        this.inflictingTile = inflictingTile;
    }

    public boolean isAffectsDefender() {
        return affectsDefender;
    }

    public void setAffectsDefender(boolean affectsDefender) {
        this.affectsDefender = affectsDefender;
    }

    public boolean isAffectsAttacker() {
        return affectsAttacker;
    }

    public void setAffectsAttacker(boolean affectsAttacker) {
        this.affectsAttacker = affectsAttacker;
    }

    public Integer getRoundsAffected() {
        return roundsAffected;
    }

    public void setRoundsAffected(Integer roundsAffected) {
        this.roundsAffected = roundsAffected;
    }

    public boolean isOnHitEffect() {
        return isOnHitEffect;
    }

    public void setOnHitEffect(boolean onHitEffect) {
        isOnHitEffect = onHitEffect;
    }

    public boolean sameStatusEffect(StatusEffectType statusEffectType)
    {
        if (statusEffectType == getStatusEffectType())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    public boolean isAffectsSelf() {
        return affectsSelf;
    }

    public void setAffectsSelf(boolean affectsSelf) {
        this.affectsSelf = affectsSelf;
    }

    @Exclude
    public Tile getInflictedTile() {
        return inflictedTile;
    }

    @Exclude
    public void setInflictedTile(Tile inflictedTile) {
        this.inflictedTile = inflictedTile;
    }

    public void activate() {
        Log.e("","");
    }

    public StatusEffect cast()
    {
        StatusEffect statusEffect = new Burned();

        return statusEffect;
    }


}
