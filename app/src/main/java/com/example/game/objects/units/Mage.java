package com.example.game.objects.units;

import com.example.game.R;
import com.example.game.enumerations.AttackType;
import com.example.game.enumerations.Rarity;

public class Mage extends Unit {

    public Mage() {
        name = "Mage";
        maxHealth = 10;
        currentHealth = maxHealth;
        damage = 25;
        attackType = AttackType.RANGED;
        range = 5;
        image = R.drawable.mage;
        rarity = Rarity.COMMON;
        ability = "None";
    }
}
