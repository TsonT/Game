package com.example.game.objects.units;

import com.example.game.R;
import com.example.game.enumerations.AttackType;
import com.example.game.enumerations.Rarity;
import com.example.game.objects.statuseffects.Disabled;

public class Ninja extends Unit {

    public Ninja() {
        name = "Ninja";
        maxHealth = 30;
        currentHealth = maxHealth;
        damage = 10;
        attackType = AttackType.MELEE;
        onHitEffectArrayList.add(new Disabled());
        range = 1;
        image = R.drawable.ninja;
        rarity = Rarity.RARE;
        ability = "Can attack a unit and disable it from attacking next round";
    }
}
