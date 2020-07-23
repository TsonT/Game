package com.example.game.objects.units;

import com.example.game.R;
import com.example.game.enumerations.AttackType;
import com.example.game.enumerations.Rarity;
import com.example.game.objects.statuseffects.InvulnerableToRanged;

public class Shield extends Unit {

    public Shield() {
        name = "Shield";
        maxHealth = 60;
        currentHealth = maxHealth;
        damage = 5;
        attackType = AttackType.MELEE;
        range = 1;
        allyAoeStatusEffectArrayList.add(new InvulnerableToRanged());
        image = R.drawable.shield;
        rarity = Rarity.COMMON;
        ability = "Protects 1 unit behind from attacks";
    }
}
