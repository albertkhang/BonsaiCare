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

    public interface OnItemLongClickListener {
        void onItemLongClickListener(View view, int position);
    }

    OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void update(ArrayList<BonsaiItem> bonsaiArrayList) {
        this.bonsaiArrayList.clear();
        this.bonsaiArrayList.addAll(bonsaiArrayList);
        notifyDataSetChanged();
    }

    public void remove(ArrayList<BonsaiItem> bonsaiArrayList, int position) {
        this.bonsaiArrayList.clear();
        this.bonsaiArrayList.addAll(bonsaiArrayList);

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, bonsaiArrayList.size());
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
        handleIcon(holder.imgBonsaiIcon, bonsaiArrayList.get(holder.getAdapterPosition()));
        holder.txtBonsaiItemPlace.setText(bonsaiArrayList.get(holder.getAdapterPosition()).getBonsaiPlacementName());
        holder.txtBonsaiItemName.setText(bonsaiArrayList.get(holder.getAdapterPosition()).getBonsaiName());
        holder.txtDayPlanted.setText(bonsaiArrayList.get(holder.getAdapterPosition()).getBonsaiDayPlanted());

        Log.d("_onBindViewHolder", "Loaded!");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickListener(view, holder.getAdapterPosition());
            }
        });

        holder.bonsai_item_frame_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("_onBindViewHolder", "Clicked!");

                onItemClickListener.onItemClickListener(view, holder.getAdapterPosition());
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onItemLongClickListener.onItemLongClickListener(view, holder.getAdapterPosition());
                return true;
            }
        });

        holder.bonsai_item_frame_manage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onItemLongClickListener.onItemLongClickListener(view, holder.getAdapterPosition());
                return true;
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
        ConstraintLayout bonsai_item_frame_manage;

        ImageView imgBonsaiIcon;
        TextView txtBonsaiItemName;
        TextView txtDayPlanted;
        TextView txtBonsaiItemPlace;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bonsai_item_frame_manage = itemView.findViewById(R.id.bonsai_item_frame_manage);

            imgBonsaiIcon = itemView.findViewById(R.id.imgPlacementIcon);
            txtBonsaiItemName = itemView.findViewById(R.id.txtPlacementName);
            txtDayPlanted = itemView.findViewById(R.id.txtTotalBonsaiValue);
            txtBonsaiItemPlace = itemView.findViewById(R.id.txtBonsaiItemPlace);
        }
    }

    public void Filter(String text, ArrayList<BonsaiItem> bonsaiArrayList) {
        if (!text.equals("")) {
            ArrayList<BonsaiItem> filterArrayList = new ArrayList<>();
            for (BonsaiItem item :
                    bonsaiArrayList) {
                if (item.getBonsaiName().toLowerCase().contains(text.toLowerCase())) {
                    filterArrayList.add(item);
                }
            }

            update(filterArrayList);
        } else {
            update(bonsaiArrayList);
        }
    }
}
