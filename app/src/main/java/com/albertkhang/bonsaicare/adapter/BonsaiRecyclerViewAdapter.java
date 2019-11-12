package com.albertkhang.bonsaicare.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.albertkhang.bonsaicare.ObjectClass.BonsaiItem;
import com.albertkhang.bonsaicare.ObjectClass.PlacementItem;
import com.albertkhang.bonsaicare.R;

import java.util.ArrayList;

public class BonsaiRecyclerViewAdapter extends RecyclerView.Adapter<BonsaiRecyclerViewAdapter.ViewHolder> {
    Context context;
    ArrayList<BonsaiItem> bonsaiArrayList = new ArrayList<>();

    public BonsaiRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void uppdate(ArrayList<BonsaiItem> bonsaiArrayList) {
        this.bonsaiArrayList.clear();
        this.bonsaiArrayList.addAll(bonsaiArrayList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bonsai_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        handleIcon(holder.imgBonsaiIcon, bonsaiArrayList.get(position));
        holder.txtBonsaiItemName.setText(bonsaiArrayList.get(position).getBonsaiName());
        holder.txtDayPlanted.setText(bonsaiArrayList.get(position).getBonsaiDayPlanted());
    }

    private void handleIcon(ImageView imageView, BonsaiItem item) {
        if (item.getBonsaiPlacementName().equals("Balcony")) {
            imageView.setBackgroundResource(R.drawable.ic_balcony);
        } else {
            if (item.getBonsaiPlacementName().equals("Window")) {
                imageView.setBackgroundResource(R.drawable.ic_window);
            } else {
                if (item.getBonsaiPlacementName().equals("Gate")) {
                    imageView.setBackgroundResource(R.drawable.ic_gate);
                } else {
                    imageView.setBackgroundResource(R.drawable.ic_location_filled);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return bonsaiArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBonsaiIcon;
        TextView txtBonsaiItemName;
        TextView txtDayPlanted;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBonsaiIcon = itemView.findViewById(R.id.imgBonsaiIcon);
            txtBonsaiItemName = itemView.findViewById(R.id.txtBonsaiItemName);
            txtDayPlanted = itemView.findViewById(R.id.txtDayPlanted);
        }
    }
}
