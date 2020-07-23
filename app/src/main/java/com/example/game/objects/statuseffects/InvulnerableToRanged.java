package com.example.game.objects.statuseffects;

import com.example.game.enumerations.StatusEffectType;
import com.example.game.objects.Tile;

public class InvulnerableToRanged extends StatusEffect {

    public InvulnerableToRanged() {
        statusEffectType = StatusEffectType.INVULNERABLETORANGED;
        affectsEnemies = false;
        affectsAllies = true;
        affectsDefender = false;
        affectsAttacker = false;
        isGamePlayEffect = false;
        isStartingRoundEffect = true;
        isOnHitEffect = false;
        roundsAffected = 1;
        rangeLeft = 0;
        rangeRight = 0;
        rangeUp = 0;
        rangeDown = 1;
    }

    //@Override
    public void activate() {

        Tile nextTile;
        nextTile = inflictingTile;

        for (int i = 0; i < rangeLeft; i++)
        {
            nextTile = nextTile.getLeftTile();
            nextTile.getUnit().getInflictedStatusEffectArrayList().add(this);
        }

        nextTile = inflictingTile;

        for (int i = 0; i < rangeRight; i++)
        {
            nextTile = nextTile.getRightTile();
            nextTile.getUnit().getInflictedStatusEffectArrayList().add(this);
        }

        nextTile = inflictingTile;

        for (int i = 0; i < rangeUp; i++)
        {
            nextTile = nextTile.getTopTile();
            nextTile.getUnit().getInflictedStatusEffectArrayList().add(this);
        }

        nextTile = inflictingTile;

        for (int i = 0; i < rangeDown; i++)
        {
            nextTile = nextTile.getBottomTile();
            try {nextTile.getUnit().getInflictedStatusEffectArrayList().add(this);}
            catch (Exception e) {}
        }
    }
}
