package com.example.game.objects.units;

import com.example.game.R;
import com.example.game.enumerations.AttackType;
import com.example.game.enumerations.Rarity;

public class Knight extends Unit {

    public Knight() {
        name = "knight";
        maxHealth = 40;
        currentHealth = maxHealth;
        damage = 35;
        attackType = AttackType.MELEE;
        range = 1;
        image = R.drawable.knight;
        rarity = Rarity.COMMON;
        ability = "None";
    }
}
