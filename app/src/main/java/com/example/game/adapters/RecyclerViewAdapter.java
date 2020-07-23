package com.example.game.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.game.objects.units.Unit;
import com.example.game.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private Context mContext ;
    private ArrayList<Unit> Units;


    public RecyclerViewAdapter(Context mContext, ArrayList<Unit> Units) {
        this.mContext = mContext;
        this.Units = Units;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_unit, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter.ViewHolder holder, final int position) {
        holder.text_unitname.setText(Units.get(position).getName());
        holder.image_unit.setImageResource(Units.get(position).getImage());
        holder.cardView.setCardBackgroundColor(mContext.getColor(R.color.card_unit_background));
    }

    @Override
    public int getItemCount() {
        return Units.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_unitname;
        ImageView image_unit;
        CardView cardView;

        public ViewHolder(final View itemView) {
            super(itemView);
            text_unitname = itemView.findViewById(R.id.text_unitname);
            image_unit = itemView.findViewById(R.id.image_unit);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
