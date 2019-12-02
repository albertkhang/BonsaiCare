package com.albertkhang.bonsaicare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.albertkhang.bonsaicare.database.FeedReaderDbHelper;
import com.albertkhang.bonsaicare.database.ManipulationDb;
import com.albertkhang.bonsaicare.objectClass.SupplyItem;
import com.albertkhang.bonsaicare.R;

import java.util.ArrayList;

public class SupplyRecyclerViewAdapter extends RecyclerView.Adapter<SupplyRecyclerViewAdapter.ViewHolder> {
    Context context;
    ArrayList<SupplyItem> supplyArrayList = new ArrayList<>();
    FeedReaderDbHelper dbHelper;

    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NOT_EMPTY = 1;

    public SupplyRecyclerViewAdapter(Context context) {
        this.context = context;
        dbHelper = new FeedReaderDbHelper(context);
    }

    public void update(ArrayList<SupplyItem> supplyArrayList) {
        this.supplyArrayList.clear();
        this.supplyArrayList.addAll(supplyArrayList);
        notifyDataSetChanged();
    }

    public void remove(ArrayList<SupplyItem> supplyArrayList, int position) {
        this.supplyArrayList.clear();
        this.supplyArrayList.addAll(supplyArrayList);

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, supplyArrayList.size());
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        if (viewType == VIEW_TYPE_EMPTY) {
            view = inflater.inflate(R.layout.item_empty_layout, parent, false);
        } else {
            view = inflater.inflate(R.layout.supply_item, parent, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        if (viewType == VIEW_TYPE_NOT_EMPTY) {
            handleIcon(holder.imgSupplyIcon, holder.getAdapterPosition());
            holder.txtSupplyItemName.setText(supplyArrayList.get(holder.getAdapterPosition()).getSupplyName());
            int totalSupplyBought = ManipulationDb.getTotalSupplyRemain(dbHelper, supplyArrayList.get(holder.getAdapterPosition()).getSupplyName());
            holder.txtSupplyItemTotal.setText(String.valueOf(totalSupplyBought));
            handleUnit(holder.txtSupplyItemUnit, holder.getAdapterPosition());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClickListener(view, holder.getAdapterPosition());
                }
            });
            holder.supply_item_frame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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

            holder.supply_item_frame.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemLongClickListener.onItemLongClickListener(view, holder.getAdapterPosition());
                    return true;
                }
            });
        }
    }

    private void handleUnit(TextView textView, int position) {
        if (supplyArrayList.get(position).getTotal() > 1) {
            textView.setText(supplyArrayList.get(position).getSupplyUnit().concat("s"));
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
                imageView.setBackgroundResource(R.drawable.ic_icon_supplies);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (supplyArrayList.size() == 0) {
            return 1;
        } else {
            return supplyArrayList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (supplyArrayList.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_NOT_EMPTY;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSupplyIcon;
        TextView txtSupplyItemName;
        TextView txtSupplyItemTotal;
        TextView txtSupplyItemUnit;

        ConstraintLayout supply_item_frame;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgSupplyIcon = itemView.findViewById(R.id.imgPlacementIcon);
            txtSupplyItemName = itemView.findViewById(R.id.txtPlacementName);
            txtSupplyItemTotal = itemView.findViewById(R.id.txtTotalBonsaiValue);
            txtSupplyItemUnit = itemView.findViewById(R.id.txtSupplyItemUnit);

            supply_item_frame = itemView.findViewById(R.id.supply_item_frame);
        }
    }

    public void Filter(String text, ArrayList<SupplyItem> supplyArrayList) {
        if (!text.equals("")) {
            ArrayList<SupplyItem> filterArrayList = new ArrayList<>();
            for (SupplyItem item :
                    supplyArrayList) {
                if (item.getSupplyName().toLowerCase().contains(text.toLowerCase())) {
                    filterArrayList.add(item);
                }
            }

            update(filterArrayList);
        } else {
            update(supplyArrayList);
        }
    }
}
