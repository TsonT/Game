package com.example.game.objects.units;

import com.example.game.enumerations.AttackType;
import com.example.game.enumerations.Rarity;
import com.example.game.enumerations.StatusEffectType;
import com.example.game.objects.Tile;
import com.example.game.objects.statuseffects.StatusEffect;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;

public class Unit {

    @Exclude
    public Tile currentTile;
    public Integer currentHealth;
    public Integer damage;
    public Integer image;
    public Integer maxHealth;
    public Integer range;
    public Rarity rarity;
    public String name;
    public AttackType attackType;
    public String ability;

    public boolean isVulnerable;
    public ArrayList<StatusEffect> allyAoeStatusEffectArrayList = new ArrayList<>();
    public ArrayList<StatusEffect> enemyAoeStatusEffectArrayList = new ArrayList<>();
    public ArrayList<StatusEffect> onHitEffectArrayList = new ArrayList<>();
    public ArrayList<StatusEffect> inflictedStatusEffectArrayList = new ArrayList<>();

    public Unit()
    {

    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(Integer maxHealth) {
        this.maxHealth = maxHealth;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public Integer getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(Integer currentHealth) {
        this.currentHealth = currentHealth;
    }

    public Integer getRange() {
        return range;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public void setAttackType(AttackType attackType) {
        this.attackType = attackType;
    }

    public ArrayList<StatusEffect> getAllyAoeStatusEffectArrayList() {
        return allyAoeStatusEffectArrayList;
    }

    public void setAllyAoeStatusEffectArrayList(ArrayList<StatusEffect> allyAoeStatusEffectArrayList) {
        this.allyAoeStatusEffectArrayList = allyAoeStatusEffectArrayList;
    }

    public ArrayList<StatusEffect> getEnemyAoeStatusEffectArrayList() {
        return enemyAoeStatusEffectArrayList;
    }

    public void setEnemyAoeStatusEffectArrayList(ArrayList<StatusEffect> enemyAoeStatusEffectArrayList) {
        this.enemyAoeStatusEffectArrayList = enemyAoeStatusEffectArrayList;
    }

    public ArrayList<StatusEffect> getInflictedStatusEffectArrayList() {
        return inflictedStatusEffectArrayList;
    }

    public void setInflictedStatusEffectArrayList(ArrayList<StatusEffect> inflictedStatusEffectArrayList) {
        this.inflictedStatusEffectArrayList = inflictedStatusEffectArrayList;
    }

    @Exclude
    public Tile getCurrentTile() {
        return currentTile;
    }

    @Exclude
    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
        updateStatusEffects();
    }

    public boolean isVulnerable() {
        return isVulnerable;
    }

    @Exclude
    public void setVulnerable(boolean vulnerable, Unit attackingUnit) {
        isVulnerable = vulnerable;

        if (isVulnerable)
        {
            currentTile.showVulnerable(attackingUnit);
        }
        else
        {
            currentTile.clearColors();
        }
    }

    public boolean isWithinRange(Unit attackingUnit)
    {
        setVulnerable(true, attackingUnit);

        AttackType attackType = attackingUnit.getAttackType();
        StatusEffectType statusEffectType;

        for (StatusEffect statusEffect : inflictedStatusEffectArrayList)
        {

            if (attackType == AttackType.RANGED && statusEffect.sameStatusEffect(StatusEffectType.INVULNERABLETORANGED))
            {
                setVulnerable(false, attackingUnit);
            }
        }

        if (isVulnerable)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void updateStatusEffects()
    {
        /*
        for (StatusEffect statusEffect : statusEffectArrayList)
        {
            statusEffect.setInflictingTile(currentTile);
        }

         */
    }

    public ArrayList<StatusEffect> getOnHitEffectArrayList() {
        return onHitEffectArrayList;
    }

    public void setOnHitEffectArrayList(ArrayList<StatusEffect> onHitEffectArrayList) {
        this.onHitEffectArrayList = onHitEffectArrayList;
    }
}
