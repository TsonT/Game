package com.example.game.objects.units;

import com.example.game.R;
import com.example.game.enumerations.AttackType;
import com.example.game.enumerations.Rarity;

public class Archer extends Unit {

    public Archer() {
        name = "Archer";
        maxHealth = 30;
        currentHealth = maxHealth;
        damage = 25;
        attackType = AttackType.RANGED;
        range = 3;
        image = R.drawable.archer;
        rarity = Rarity.COMMON;
        ability = "None";
    }
}
