package com.albertkhang.bonsaicare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.database.FeedReaderDbHelper;
import com.albertkhang.bonsaicare.database.ManipulationDb;
import com.albertkhang.bonsaicare.objectClass.SupplyBillItem;

import java.util.ArrayList;

public class SupplyBilRecyclerViewAdapter extends RecyclerView.Adapter<SupplyBilRecyclerViewAdapter.ViewHolder> {
    Context context;
    ArrayList<SupplyBillItem> supplyBillItemArrayList = new ArrayList<>();
    FeedReaderDbHelper dbHelper;

    public SupplyBilRecyclerViewAdapter(Context context) {
        this.context = context;
        dbHelper = new FeedReaderDbHelper(context);
    }

    public void update(ArrayList<SupplyBillItem> supplyBillItemArrayList) {
        this.supplyBillItemArrayList.clear();
        this.supplyBillItemArrayList.addAll(supplyBillItemArrayList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.supply_bill_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        handleIcon(holder.imgIcon, position);
        holder.txtDayBoughtValue.setText(supplyBillItemArrayList.get(position).getDayBought());
        holder.txtTotalBoughtValue.setText(String.valueOf(supplyBillItemArrayList.get(position).getTotalSupplies()));
        holder.txtSupplyItemUnit.setText(ManipulationDb.getSupplyUnitFromSupplyName(dbHelper, supplyBillItemArrayList.get(position).getSupplyName()));
        holder.txtSupplyMoneyValue.setText(String.valueOf(supplyBillItemArrayList.get(position).getTotalMoney()));
    }

    @Override
    public int getItemCount() {
        return supplyBillItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView txtDayBoughtValue;
        TextView txtTotalBoughtValue;
        TextView txtSupplyItemUnit;
        TextView txtSupplyMoneyValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgIcon = itemView.findViewById(R.id.imgIcon);
            txtDayBoughtValue = itemView.findViewById(R.id.txtDayBoughtValue);
            txtTotalBoughtValue = itemView.findViewById(R.id.txtTotalBoughtValue);
            txtSupplyItemUnit = itemView.findViewById(R.id.txtSupplyItemUnit);
            txtSupplyMoneyValue = itemView.findViewById(R.id.txtSupplyMoneyValue);
        }
    }

    private void handleIcon(ImageView imageView, int position) {
        if (supplyBillItemArrayList.get(position).getSupplyName().equals("Water")) {
            imageView.setBackgroundResource(R.drawable.ic_water);
        } else {
            if (supplyBillItemArrayList.get(position).getSupplyName().equals("Nitrogen fertilizer")) {
                imageView.setBackgroundResource(R.drawable.ic_nitrogen_fertilizer);
            } else {
                imageView.setBackgroundResource(R.drawable.ic_supplies_filled);
            }
        }
    }
}
