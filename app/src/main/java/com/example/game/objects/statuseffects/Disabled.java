package com.example.game.objects.statuseffects;

import com.example.game.enumerations.StatusEffectType;

public class Disabled extends StatusEffect {

    public Disabled() {
        statusEffectType = StatusEffectType.DISABLED;
        affectsEnemies = true;
        affectsAllies = false;
        affectsDefender = true;
        affectsAttacker = false;
        isGamePlayEffect = false;
        isStartingRoundEffect = true;
        isOnHitEffect = true;
        roundsAffected = 1;
        rangeLeft = 0;
        rangeRight = 0;
        rangeUp = 0;
        rangeDown = 0;
    }

}
