package com.example.game;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.game.objects.units.Unit;

public class Dialog_unit_info extends AppCompatDialogFragment {

    private Unit unit;
    private ImageView image_unit;
    private TextView text_maxHealth, text_currentHealth, text_Damage, text_Range, text_attackType, text_ability;

    public Dialog_unit_info(Unit unit) {
        this.unit = unit;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogTheme);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view  = inflater.inflate(R.layout.dialog_unit_info, null);

        image_unit = view.findViewById(R.id.image_unit);
        image_unit.setImageResource(unit.getImage());

        text_maxHealth = view.findViewById(R.id.text_maxHealth);
        text_maxHealth.setText(unit.getMaxHealth().toString());
        text_currentHealth = view.findViewById(R.id.text_currentHealth);
        text_currentHealth.setText(unit.getCurrentHealth().toString());
        text_Damage = view.findViewById(R.id.text_Damage);
        text_Damage.setText(unit.getDamage().toString());
        text_Range = view.findViewById(R.id.text_Range);
        text_Range.setText(unit.getRange().toString());
        text_attackType = view.findViewById(R.id.text_attackType);
        text_attackType.setText(unit.getAttackType().toString());
        text_ability = view.findViewById(R.id.text_ability);
        text_ability.setText(unit.getAbility());


        builder.setView(view)
                .setTitle(unit.getName())
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


        return builder.create();
    }
}
