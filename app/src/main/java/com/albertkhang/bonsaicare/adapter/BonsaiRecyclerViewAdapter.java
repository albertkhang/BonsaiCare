package com.albertkhang.bonsaicare.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.albertkhang.bonsaicare.objectClass.BonsaiItem;
import com.albertkhang.bonsaicare.R;

import java.util.ArrayList;

public class BonsaiRecyclerViewAdapter extends RecyclerView.Adapter<BonsaiRecyclerViewAdapter.ViewHolder> {
    Context context;
    ArrayList<BonsaiItem> bonsaiArrayList = new ArrayList<>();

    public BonsaiRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClickListener(View view, int position);
    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void uppdate(ArrayList<BonsaiItem> bonsaiArrayList) {
        this.bonsaiArrayList.clear();
        this.bonsaiArrayList.addAll(bonsaiArrayList);
        notifyDataSetChanged();
    }

    public void uppdate(Context context, ArrayList<BonsaiItem> bonsaiArrayList) {
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        handleIcon(holder.imgBonsaiIcon, bonsaiArrayList.get(position));
        holder.txtBonsaiItemName.setText(bonsaiArrayList.get(position).getBonsaiName());
        holder.txtDayPlanted.setText(bonsaiArrayList.get(position).getBonsaiDayPlanted());
        Log.d("_onBindViewHolder", "Loaded!");

        holder.bonsai_item_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("_onBindViewHolder", "Clicked!");

                onItemClickListener.onItemClickListener(view, position);
            }
        });
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
        ConstraintLayout bonsai_item_frame;

        ImageView imgBonsaiIcon;
        TextView txtBonsaiItemName;
        TextView txtDayPlanted;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bonsai_item_frame = itemView.findViewById(R.id.bonsai_item_frame);

            imgBonsaiIcon = itemView.findViewById(R.id.imgBonsaiIcon);
            txtBonsaiItemName = itemView.findViewById(R.id.txtBonsaiItemName);
            txtDayPlanted = itemView.findViewById(R.id.txtDayPlanted);
        }
    }
}