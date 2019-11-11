package com.albertkhang.bonsaicare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.albertkhang.bonsaicare.ObjectClass.ScheduleItem;
import com.albertkhang.bonsaicare.R;

import java.util.ArrayList;

public class ScheduleRecyclerViewAdapter extends RecyclerView.Adapter<ScheduleRecyclerViewAdapter.ViewHolder> {
    Context context;
    ArrayList<ScheduleItem> scheduleItems;

    public ScheduleRecyclerViewAdapter(Context context, ArrayList<ScheduleItem> scheduleItems) {
        this.context = context;
        this.scheduleItems = scheduleItems;
    }

    public void refresh(ArrayList<ScheduleItem> scheduleItems) {
        this.scheduleItems = scheduleItems;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClickListener(View view, int position);
    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnTickClickListener {
        void onTickClickListener(View view, int position);
    }

    OnTickClickListener onTickClickListener;

    public void setOnTickClickListener(OnTickClickListener onTickClickListener) {
        this.onTickClickListener = onTickClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.schedule_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if (scheduleItems.get(position).isTicked()) {
            holder.imgItemTick.setImageResource(R.drawable.ic_ticked);
        } else {
            holder.imgItemTick.setImageResource(R.drawable.ic_nottick);
        }

        holder.imgItemTick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTickClickListener.onTickClickListener(view, position);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickListener(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return scheduleItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtItemTime;
        TextView txtItemDay;

        TextView txtItemBonsaiName;
        ImageView imgItemNote;
        TextView txtItemLocation;
        TextView txtItemSupplies;

        ImageView imgItemTick;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtItemTime = itemView.findViewById(R.id.txtItemTime);
            txtItemDay = itemView.findViewById(R.id.txtItemDay);

            txtItemBonsaiName = itemView.findViewById(R.id.txtSupplyItemName);
            imgItemNote = itemView.findViewById(R.id.imgItemNote);
            txtItemLocation = itemView.findViewById(R.id.txtSupplyItemTotal);
            txtItemSupplies = itemView.findViewById(R.id.txtItemSupplies);

            imgItemTick = itemView.findViewById(R.id.imgItemTick);
        }
    }
}
