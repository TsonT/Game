package com.example.game.objects;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.game.R;
import com.example.game.objects.statuseffects.StatusEffect;
import com.example.game.objects.units.Unit;
import com.google.firebase.database.Exclude;

public class Tile {

    private Boolean isOccupied = false;
    private Unit unit;
    private ImageView imageView;
    private LinearLayout linearLayout;
    private ProgressBar healthBar;
    private LinearLayout horizontalLinearLayout;
    private Context context;
    private Tile leftTile, rightTile, topTile, bottomTile;
    private Integer row;
    private Integer column;
    private TextView healthText;

    public Tile(Context context, ImageView imageView, LinearLayout linearLayout, Integer row, Integer column) {
        this.context = context;
        this.imageView = imageView;
        this.linearLayout = linearLayout;
        this.row = row;
        this.column = column;
    }

    public Tile()
    {

    }

    public Boolean getOccupied() {
        return isOccupied;
    }

    public void setOccupied(Boolean occupied) {
        isOccupied = occupied;
    }

    public Unit getUnit() {
        return unit;
    }

    @Exclude
    public void setUnit(Unit unit) {

        updateImageView(unit);

        this.unit = unit;

        isOccupied = true;

        unit.setCurrentTile(this);

        linearLayout.setBackgroundColor(context.getResources().getColor(R.color.transparent));

        updateHealthBar();
    }

    public void updateImageView(Unit unit)
    {
        if (this.unit != null)
        {
            //Unit is completely different
            if (!this.unit.getImage().equals(unit.getImage()))
            {
                imageView.setImageResource(unit.getImage());
                createHealthBar();
            }
        }
        //Tile was empty
        else
        {
            this.unit = unit;
            imageView.setImageResource(unit.getImage());
            createHealthBar();
            updateHealthBar();
        }
    }

    @Exclude
    public ImageView getImageView() {
        return imageView;
    }

    @Exclude
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    @Exclude
    public ProgressBar getHealthBar() {
        return healthBar;
    }

    @Exclude
    public void setHealthBar(ProgressBar healthBar) {
        this.healthBar = healthBar;
    }

    @Exclude
    public TextView getHealthText() {
        return healthText;
    }

    @Exclude
    public void setHealthText(TextView healthText) {
        this.healthText = healthText;
    }

    @Exclude
    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    @Exclude
    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    @Exclude
    public void createHealthBar()
    {
        horizontalLinearLayout = new LinearLayout(context);
        horizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        horizontalLinearLayout.setLayoutParams(linearLayoutParams);

        linearLayoutParams.gravity = Gravity.CENTER;
        linearLayout.addView(horizontalLinearLayout);

        healthText = new TextView(context);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams((int) context.getResources().getDimension(R.dimen.textHealth_width), (int) context.getResources().getDimension(R.dimen.textHealth_height));
        textParams.gravity = Gravity.CENTER;
        textParams.rightMargin = ((int) context.getResources().getDimension(R.dimen.textHealth_rightMargin));
        healthText.setGravity(Gravity.CENTER);
        healthText.setTypeface(null, Typeface.BOLD);
        healthText.setTextSize(context.getResources().getDimension(R.dimen.textHealth_size));
        healthText.setTextColor(context.getResources().getColor(R.color.white));
        healthText.setLayoutParams(textParams);

        healthText.setText(unit.getCurrentHealth() + " | " + unit.getMaxHealth());

        healthBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        LinearLayout.LayoutParams progressBarParams = new LinearLayout.LayoutParams((int) context.getResources().getDimension(R.dimen.progressBar_width), (int) context.getResources().getDimension(R.dimen.progressBar_height));
        progressBarParams.gravity = Gravity.CENTER;
        healthBar.setLayoutParams(progressBarParams);
        healthBar.getProgressDrawable().setColorFilter(context.getResources().getColor(R.color.healthBar), PorterDuff.Mode.SRC_IN);

        healthBar.setMax(unit.getMaxHealth());
        healthBar.setProgress(unit.getCurrentHealth());

        horizontalLinearLayout.addView(healthText);
        horizontalLinearLayout.addView(healthBar);
    }

    @Exclude
    public void removeUnit()
    {
        linearLayout.setBackgroundColor(context.getResources().getColor(R.color.tileDefault));
        imageView.setImageResource(0);
        linearLayout.removeView(horizontalLinearLayout);
        unit = null;
        isOccupied = false;
    }

    @Exclude
    public void showAvailable()
    {
        linearLayout.setBackgroundColor(context.getResources().getColor(R.color.tileAvailable));
    }

    @Exclude
    public void showDefault()
    {
        linearLayout.setBackgroundColor(context.getResources().getColor(R.color.tileDefault));
    }

    @Exclude
    public Tile getLeftTile() {
        return leftTile;
    }

    @Exclude
    public void setLeftTile(Tile leftTile) {
        this.leftTile = leftTile;
    }

    @Exclude
    public Tile getRightTile() {
        return rightTile;
    }

    @Exclude
    public void setRightTile(Tile rightTile) {
        this.rightTile = rightTile;
    }

    @Exclude
    public Tile getTopTile() {
        return topTile;
    }

    @Exclude
    public void setTopTile(Tile topTile) {
        this.topTile = topTile;
    }

    @Exclude
    public Tile getBottomTile() {
        return bottomTile;
    }

    @Exclude
    public void setBottomTile(Tile bottomTile) {
        this.bottomTile = bottomTile;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    @Exclude
    public void showVulnerable(final Unit attackingUnit)
    {
        linearLayout.setBackgroundColor(context.getResources().getColor(R.color.unitVulnerable));
    }

    @Exclude
    public void clearColors()
    {
        linearLayout.setBackgroundColor(context.getResources().getColor(R.color.transparent));
    }

    @Exclude
    public void dead()
    {
        updateHealthBar();

        removeUnit();
    }

    @Exclude
    public void updateHealthBar()
    {
        if (getHealthBar().getProgress() != getUnit().getCurrentHealth())
        {
            showAttackedAnimation();
        }
        getHealthBar().setProgress(getUnit().getCurrentHealth(), true);
        getHealthText().setText(getUnit().getCurrentHealth() + " | " + getUnit().getMaxHealth());
    }

    @Exclude
    public void showUnitSelected()
    {
        linearLayout.setBackgroundColor(context.getResources().getColor(R.color.unitSelected));
    }

    @Exclude
    public void opponentUnitAttacked(Unit attackingUnit)
    {

        showAttackedAnimation();

        getUnit().setCurrentHealth(getUnit().getCurrentHealth() - attackingUnit.getDamage());

        applyOnHitStatusEffects(attackingUnit);

        if(getUnit().getCurrentHealth() > 0)
        {
            updateHealthBar();
        }
        else if (getUnit().getCurrentHealth() <= 0)
        {
            dead();
        }
    }

    @Exclude
    public void opponentUnitStatusEffectDamaging(StatusEffect statusEffect)
    {
        showAttackedAnimation();

        getUnit().setCurrentHealth(getUnit().getCurrentHealth() - statusEffect.getDamage());

        if (getUnit().getCurrentHealth() > 0)
        {
            updateHealthBar();
        }
        else if (getUnit().getCurrentHealth() <= 0)
        {
            dead();
        }
    }

    public void showAttackedAnimation() {
        imageView.setColorFilter(context.getResources().getColor(R.color.attackedColorTint));

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imageView.setColorFilter(null);
            }
        }, 250);
    }

    @Exclude
    public void applyOnHitStatusEffects(Unit attackingUnit)
    {
        for (StatusEffect statusEffect : attackingUnit.getOnHitEffectArrayList())
        {
            getUnit().getInflictedStatusEffectArrayList().add(statusEffect);
            statusEffect.activate();
            statusEffect.setInflictedTile(this);
        }
    }

}
