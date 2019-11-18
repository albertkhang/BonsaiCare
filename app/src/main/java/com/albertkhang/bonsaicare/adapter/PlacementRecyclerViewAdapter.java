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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.placement_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        handleIcon(holder.imgPlacementIcon, position);
        handleDetail(holder, position);

        holder.txtPlacementName.setText(placementArrayList.get(position).getPlacementName());

        holder.txtTotalBonsaiValue.setText(String.valueOf(placementArrayList.get(position).getTotalBonsai()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });

        holder.bonsai_item_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onItemLongClickListener.onItemLongClickListener(view, position);
                return true;
            }
        });

        holder.bonsai_item_frame.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onItemLongClickListener.onItemLongClickListener(view, position);
                return true;
            }
        });
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
        return placementArrayList.size();
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
