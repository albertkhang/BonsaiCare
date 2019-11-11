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

import com.albertkhang.bonsaicare.ObjectClass.PlacementItem;
import com.albertkhang.bonsaicare.R;

import java.util.ArrayList;

public class PlacementRecyclerViewAdapter extends RecyclerView.Adapter<PlacementRecyclerViewAdapter.ViewHolder> {
    Context context;
    ArrayList<PlacementItem> placementArrayList = new ArrayList<>();

    public PlacementRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void uppdate(ArrayList<PlacementItem> placementArrayList) {
        this.placementArrayList.clear();
        this.placementArrayList.addAll(placementArrayList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.placement_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        handleIcon(holder.imgPlacementIcon, position);
        handleDetail(holder, position);
    }

    private void handleDetail(ViewHolder holder, int position) {
        holder.txtPlacementItemPlace.setText(placementArrayList.get(position).getPlaccementName());
        holder.txtPlacementItemId.setText(String.valueOf(placementArrayList.get(position).getId()));

        Log.d("_handleDetail", "" + placementArrayList.get(position).getId());
    }

    private void handleIcon(ImageView imgPlacementIcon, int position) {
        if (placementArrayList.get(position).getPlaccementName().equals("Balcony")) {
            imgPlacementIcon.setBackgroundResource(R.drawable.ic_balcony);
        } else {
            if (placementArrayList.get(position).getPlaccementName().equals("Window")) {
                imgPlacementIcon.setBackgroundResource(R.drawable.ic_window);
            } else {
                if (placementArrayList.get(position).getPlaccementName().equals("Gate")) {
                    imgPlacementIcon.setBackgroundResource(R.drawable.ic_gate);
                } else {
                    imgPlacementIcon.setBackgroundResource(R.drawable.ic_location_filled);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return placementArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPlacementIcon;
        TextView txtPlacementItemPlace;
        TextView txtPlacementItemId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPlacementIcon = itemView.findViewById(R.id.imgSupplyIcon);
            txtPlacementItemPlace = itemView.findViewById(R.id.txtSupplyItemName);
            txtPlacementItemId = itemView.findViewById(R.id.txtSupplyItemTotal);
        }
    }
}
