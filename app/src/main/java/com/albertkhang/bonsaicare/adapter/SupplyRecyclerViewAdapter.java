package com.albertkhang.bonsaicare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.albertkhang.bonsaicare.objectClass.SupplyItem;
import com.albertkhang.bonsaicare.R;

import java.util.ArrayList;

public class SupplyRecyclerViewAdapter extends RecyclerView.Adapter<SupplyRecyclerViewAdapter.ViewHolder> {
    Context context;
    ArrayList<SupplyItem> supplyArrayList = new ArrayList<>();

    public SupplyRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void uppdate(ArrayList<SupplyItem> supplyArrayList) {
        this.supplyArrayList.clear();
        this.supplyArrayList.addAll(supplyArrayList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.supply_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        handleIcon(holder.imgSupplyIcon, position);
        holder.txtSupplyItemName.setText(supplyArrayList.get(position).getSupplyName());
        holder.txtSupplyItemTotal.setText(String.valueOf(supplyArrayList.get(position).getTotal()));
        handleUnit(holder.txtSupplyItemUnit, position);
    }

    private void handleUnit(TextView textView, int position) {
        if (supplyArrayList.get(position).getTotal() > 1) {
            textView.setText(supplyArrayList.get(position).getSupplyUnit() + "s");
        } else {
            textView.setText(supplyArrayList.get(position).getSupplyUnit());
        }
    }

    private void handleIcon(ImageView imageView, int position) {
        if (supplyArrayList.get(position).getSupplyName().equals("Water")) {
            imageView.setBackgroundResource(R.drawable.ic_water);
        } else {
            if (supplyArrayList.get(position).getSupplyName().equals("Nitrogen fertilizer")) {
                imageView.setBackgroundResource(R.drawable.ic_nitrogen_fertilizer);
            } else {
                imageView.setBackgroundResource(R.drawable.ic_supplies_filled);
            }
        }
    }


    @Override
    public int getItemCount() {
        return supplyArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSupplyIcon;
        TextView txtSupplyItemName;
        TextView txtSupplyItemTotal;
        TextView txtSupplyItemUnit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgSupplyIcon = itemView.findViewById(R.id.imgPlacementIcon);
            txtSupplyItemName = itemView.findViewById(R.id.txtPlacementName);
            txtSupplyItemTotal = itemView.findViewById(R.id.txtTotalBonsaiValue);
            txtSupplyItemUnit = itemView.findViewById(R.id.txtSupplyItemUnit);
        }
    }
}
