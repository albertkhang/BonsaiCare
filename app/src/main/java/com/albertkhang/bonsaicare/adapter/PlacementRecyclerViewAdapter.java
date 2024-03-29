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

import com.albertkhang.bonsaicare.database.FeedReaderDbHelper;
import com.albertkhang.bonsaicare.objectClass.PlacementItem;
import com.albertkhang.bonsaicare.R;

import java.util.ArrayList;

public class PlacementRecyclerViewAdapter extends RecyclerView.Adapter<PlacementRecyclerViewAdapter.ViewHolder> {
    Context context;
    ArrayList<PlacementItem> placementArrayList = new ArrayList<>();

    FeedReaderDbHelper dbHelper;

    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NOT_EMPTY = 1;

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

    public PlacementRecyclerViewAdapter(Context context) {
        this.context = context;
        dbHelper = new FeedReaderDbHelper(context);
    }

    public void update(ArrayList<PlacementItem> placementArrayList) {
        this.placementArrayList.clear();
        this.placementArrayList.addAll(placementArrayList);
        notifyDataSetChanged();
    }

    public void remove(ArrayList<PlacementItem> placementArrayList, int position) {
        this.placementArrayList.clear();
        this.placementArrayList.addAll(placementArrayList);

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, placementArrayList.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        if (viewType == VIEW_TYPE_EMPTY) {
            view = inflater.inflate(R.layout.item_empty_layout, parent, false);
        } else {
            view = inflater.inflate(R.layout.placement_item, parent, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        if (viewType == VIEW_TYPE_NOT_EMPTY) {
            handleIcon(holder.imgPlacementIcon, holder.getAdapterPosition());
            handleDetail(holder, holder.getAdapterPosition());

            holder.txtPlacementName.setText(placementArrayList.get(holder.getAdapterPosition()).getPlacementName());

            holder.txtTotalBonsaiValue.setText(String.valueOf(placementArrayList.get(holder.getAdapterPosition()).getTotalBonsai()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClickListener(view, holder.getAdapterPosition());
                }
            });

            holder.bonsai_item_frame.setOnClickListener(new View.OnClickListener() {
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

            holder.bonsai_item_frame.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemLongClickListener.onItemLongClickListener(view, holder.getAdapterPosition());
                    return true;
                }
            });
        }
    }

    private void handleDetail(ViewHolder holder, int position) {
        holder.txtTotalBonsaiValue.setText(placementArrayList.get(position).getPlacementName());
        holder.txtTotalBonsaiValue.setText(String.valueOf(placementArrayList.get(position).getId()));

        Log.d("_handleDetail", "" + placementArrayList.get(position).getId());
    }

    private void handleIcon(ImageView imageView, int position) {
        if (placementArrayList.get(position).getPlacementName().equals("Balcony")) {
            imageView.setBackgroundResource(R.drawable.ic_balcony);
        } else {
            if (placementArrayList.get(position).getPlacementName().equals("Window")) {
                imageView.setBackgroundResource(R.drawable.ic_window);
            } else {
                if (placementArrayList.get(position).getPlacementName().equals("Gate")) {
                    imageView.setBackgroundResource(R.drawable.ic_gate);
                } else {
                    imageView.setBackgroundResource(R.drawable.ic_location_filled);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if (placementArrayList.size() == 0) {
            return 1;
        } else {
            return placementArrayList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (placementArrayList.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_NOT_EMPTY;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout bonsai_item_frame;

        ImageView imgPlacementIcon;
        TextView txtPlacementName;
        TextView txtTotalBonsaiValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bonsai_item_frame = itemView.findViewById(R.id.supply_item_frame);

            imgPlacementIcon = itemView.findViewById(R.id.imgPlacementIcon);
            txtPlacementName = itemView.findViewById(R.id.txtPlacementName);
            txtTotalBonsaiValue = itemView.findViewById(R.id.txtTotalBonsaiValue);
        }
    }

    public void Filter(String text, ArrayList<PlacementItem> placeArrayList) {
        if (!text.equals("")) {
            ArrayList<PlacementItem> filterArrayList = new ArrayList<>();
            for (PlacementItem item :
                    placeArrayList) {
                if (item.getPlacementName().toLowerCase().contains(text.toLowerCase())) {
                    filterArrayList.add(item);
                }
            }

            update(filterArrayList);
        } else {
            update(placeArrayList);
        }
    }
}
