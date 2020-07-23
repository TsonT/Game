package com.example.game.objects.statuseffects;

import android.util.Log;

import com.example.game.objects.statuseffects.StatusEffect;
import com.example.game.enumerations.StatusEffectType;

public class Burned extends StatusEffect {


    public Burned() {

        statusEffectType = StatusEffectType.BURNED;
        affectsEnemies = true;
        affectsAllies = false;
        affectsDefender = true;
        affectsAttacker = false;
        isGamePlayEffect = false;
        isStartingRoundEffect = true;
        isOnHitEffect = true;
        roundsAffected = 3;
        rangeLeft = 0;
        rangeRight = 0;
        rangeUp = 0;
        rangeDown = 0;
        damage = 5;
    }

    @Override
    public void activate() {
        super.activate();

        inflictedTile.opponentUnitStatusEffectDamaging(this);
    }

}
