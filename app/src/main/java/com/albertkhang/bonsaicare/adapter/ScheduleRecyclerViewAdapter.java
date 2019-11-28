package com.albertkhang.bonsaicare.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.albertkhang.bonsaicare.objectClass.BonsaiItem;
import com.albertkhang.bonsaicare.objectClass.ScheduleItem;
import com.albertkhang.bonsaicare.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScheduleRecyclerViewAdapter extends RecyclerView.Adapter<ScheduleRecyclerViewAdapter.ViewHolder> {
    Context context;
    ArrayList<ScheduleItem> scheduleItems = new ArrayList<>();

    public ScheduleRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void update(ArrayList<ScheduleItem> scheduleArrayList) {
        this.scheduleItems.clear();
        this.scheduleItems.addAll(scheduleArrayList);
        notifyDataSetChanged();
    }

    public void remove(ArrayList<ScheduleItem> scheduleArrayList, int position) {
        this.scheduleItems.clear();
        this.scheduleItems.addAll(scheduleArrayList);

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, scheduleArrayList.size());
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
        //bonsaiName
        holder.txtBonsaiName.setText(scheduleItems.get(holder.getAdapterPosition()).getBonsaiName());
        //location
        holder.txtBonsaiLocation.setText(scheduleItems.get(holder.getAdapterPosition()).getBonsaiPlace());
        //supply
        holder.txtItemSupplies.setText(scheduleItems.get(holder.getAdapterPosition()).getSupplyName());
        //supply icon
        handleSupplyIcon(holder, holder.getAdapterPosition());
        //timeTakeCare
        holder.txtItemTime.setText(scheduleItems.get(holder.getAdapterPosition()).getTimeTakeCare());
        //dayTakeCare
        int month = getMonth(scheduleItems.get(holder.getAdapterPosition()).getDayTakeCare());
        holder.txtItemDay.setText(getMonthText(month) + " " + getDay(scheduleItems.get(holder.getAdapterPosition()).getDayTakeCare()) + ", " + getYear(scheduleItems.get(holder.getAdapterPosition()).getDayTakeCare()));

        //ticked
        if (scheduleItems.get(holder.getAdapterPosition()).isTicked()) {
            holder.imgItemTick.setImageResource(R.drawable.ic_ticked);
        } else {
            holder.imgItemTick.setImageResource(R.drawable.ic_nottick);
        }

        if (scheduleItems.get(holder.getAdapterPosition()).isHaveNote()) {
            holder.imgItemNote.setVisibility(View.VISIBLE);
        } else {
            holder.imgItemNote.setVisibility(View.INVISIBLE);
        }

        //note
        if (!scheduleItems.get(holder.getAdapterPosition()).getNote().equals("")) {
            holder.imgItemNote.setVisibility(View.VISIBLE);
        } else {
            holder.imgItemNote.setVisibility(View.INVISIBLE);
        }

        holder.imgItemTick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTickClickListener.onTickClickListener(view, holder.getAdapterPosition());
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onItemLongClickListener.onItemLongClickListener(view, holder.getAdapterPosition());
                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickListener(view, holder.getAdapterPosition());
            }
        });
    }

    private void handleSupplyIcon(ViewHolder holder, int position) {
        holder.imgItemSupplies.setColorFilter(context.getResources().getColor(R.color.colorItemInfo));

        if (scheduleItems.get(position).getSupplyName().equals("Water")) {
            holder.imgItemSupplies.setImageResource(R.drawable.ic_water);
        } else {
            if (scheduleItems.get(position).getSupplyName().equals("Nitrogen fertilizer")) {
                holder.imgItemSupplies.setImageResource(R.drawable.ic_nitrogen_fertilizer);

            } else {
                holder.imgItemSupplies.setImageResource(R.drawable.ic_supplies_filled);
            }
        }
    }

    @Override
    public int getItemCount() {
        return scheduleItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtItemDay;
        TextView txtItemTime;

        TextView txtBonsaiName;
        ImageView imgItemNote;
        TextView txtBonsaiLocation;

        ImageView imgItemSupplies;
        TextView txtItemSupplies;

        ImageView imgItemTick;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtItemDay = itemView.findViewById(R.id.txtItemDay);
            txtItemTime = itemView.findViewById(R.id.txtItemTime);

            txtBonsaiName = itemView.findViewById(R.id.txtBonsaiName);
            imgItemNote = itemView.findViewById(R.id.imgItemNote);
            txtBonsaiLocation = itemView.findViewById(R.id.txtBonsaiLocation);

            imgItemSupplies = itemView.findViewById(R.id.imgItemSupplies);
            txtItemSupplies = itemView.findViewById(R.id.txtItemSupplies);

            imgItemTick = itemView.findViewById(R.id.imgItemTick);
        }
    }

    public void sort(ArrayList<ScheduleItem> scheduleArrayList) {
        Collections.sort(scheduleArrayList, new Comparator<ScheduleItem>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public int compare(ScheduleItem scheduleItem1, ScheduleItem scheduleItem2) {
                Log.d("_sort", "" + Integer.compare(getDay(scheduleItem1.getDayTakeCare()), getDay(scheduleItem2.getDayTakeCare())));

                return scheduleItem1.getDate().compareTo(scheduleItem2.getDate());
            }
        });
    }

    private int getMonth(String date) {
        //13 - 05 - 2001
        //01234567890123

        String monthStr = date.substring(0, 2);
        return Integer.parseInt(monthStr);
    }

    private int getDay(String date) {
        //13 - 05 - 2001
        //01234567890123

        String dayStr = date.substring(5, 7);
        Log.d("_get_Time", "getDay: " + dayStr);

        return Integer.parseInt(dayStr);
    }

    private int getYear(String date) {
        //13 - 05 - 2001
        //01234567890123

        String yearStr = date.substring(10, 14);
        Log.d("_get_Time", "getYear: " + yearStr);

        return Integer.parseInt(yearStr);
    }

    private String getMonthText(int month) {
        Log.d("_getMonthText", "" + month);

        switch (month) {
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "Aug";
            case 9:
                return "Sept";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
        }

        return null;
    }

    public void Filter(String text, ArrayList<ScheduleItem> scheduleArrayList) {
        if (!text.equals("")) {
            ArrayList<ScheduleItem> filterArrayList = new ArrayList<>();
            for (ScheduleItem item :
                    scheduleArrayList) {
                if (item.getBonsaiName().toLowerCase().contains(text.toLowerCase())) {
                    filterArrayList.add(item);
                }
            }

            update(filterArrayList);
        } else {
            update(scheduleArrayList);
        }
    }
}
