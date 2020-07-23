package com.example.game.objects;

import com.example.game.objects.units.Archer;
import com.example.game.objects.units.DarkKnight;
import com.example.game.objects.units.Mage;
import com.example.game.objects.units.Ninja;
import com.example.game.objects.units.Shield;
import com.example.game.objects.units.Knight;
import com.example.game.objects.units.Unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Recruiter {

    private ArrayList<Unit> commonUnits = new ArrayList<Unit>(Arrays.asList(new Shield(), new Knight(), new Archer(), new Mage()));
    private ArrayList<Unit> uncommonUnits = new ArrayList<Unit>(Arrays.asList(new Shield(), new Knight(), new Archer(), new Mage()));
    private ArrayList<Unit> rareUnits = new ArrayList<Unit>(Arrays.asList(new Ninja()));
    private ArrayList<Unit> legendaryUnits = new ArrayList<Unit>(Arrays.asList(new DarkKnight()));
    private Random randomNumberGenerator = new Random();
    private Integer max = 100;
    private Integer min = 1;

    private Integer chanceCommon = 45;
    private Integer chanceUncommon = 30;
    private Integer chanceRare = 20;
    private Integer chanceLegendary = 5;

    private Integer minCommon = 0;
    private Integer maxCommon = minCommon + chanceCommon;

    private Integer minUncommon = maxCommon;
    private Integer maxUncommon = minUncommon + chanceUncommon;

    private Integer minRare = maxUncommon;
    private Integer maxRare = minRare + chanceRare;

    private Integer minLegendary = maxRare;
    private Integer maxLegendary = minLegendary + chanceLegendary;


    public ArrayList<Unit> getCommonUnits() {
        return commonUnits;
    }

    public void setCommonUnits(ArrayList<Unit> commonUnits) {
        this.commonUnits = commonUnits;
    }

    public ArrayList<Unit> getUncommonUnits() {
        return uncommonUnits;
    }

    public void setUncommonUnits(ArrayList<Unit> uncommonUnits) {
        this.uncommonUnits = uncommonUnits;
    }

    public ArrayList<Unit> getRareUnits() {
        return rareUnits;
    }

    public void setRareUnits(ArrayList<Unit> rareUnits) {
        this.rareUnits = rareUnits;
    }

    public ArrayList<Unit> getLegendaryUnits() {
        return legendaryUnits;
    }

    public void setLegendaryUnits(ArrayList<Unit> legendaryUnits) {
        this.legendaryUnits = legendaryUnits;
    }

    public Unit getRecruit()
    {
        int randomNumber = randomNumberGenerator.nextInt(max-min) + min;

        int index;


        if (randomNumber > minCommon && randomNumber <= maxCommon)
        {
            index = randomNumberGenerator.nextInt(commonUnits.size());
            return commonUnits.get(index);
        }

        else if (randomNumber > minUncommon && randomNumber <= maxUncommon)
        {
            index = randomNumberGenerator.nextInt(uncommonUnits.size());
            return uncommonUnits.get(index);
        }

        else if (randomNumber > minRare && randomNumber <= maxRare)
        {
            index = randomNumberGenerator.nextInt(rareUnits.size());
            return rareUnits.get(index);
        }

        else if (randomNumber > minLegendary && randomNumber <= maxLegendary)
        {
            index = randomNumberGenerator.nextInt(legendaryUnits.size());
            return legendaryUnits.get(index);
        }
        else
        {
            return new Knight();
        }
    }
}
