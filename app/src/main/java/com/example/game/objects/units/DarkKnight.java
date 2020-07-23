package com.example.game.objects.units;

import com.example.game.R;
import com.example.game.objects.statuseffects.StatusEffect;
import com.example.game.enumerations.AttackType;
import com.example.game.enumerations.Rarity;
import com.example.game.objects.statuseffects.Burned;

public class DarkKnight extends Unit {

    public DarkKnight() {
        name = "Dark Knight";
        maxHealth = 60;
        currentHealth = maxHealth;
        damage = 30;
        attackType = AttackType.MELEE;
        StatusEffect burned = new Burned();
        onHitEffectArrayList.add(burned);
        range = 1;
        image = R.drawable.dark_knight;
        rarity = Rarity.LEGENDARY;
        ability = "Burns an enemy unit 5 damage for 3 turns";
    }
}
