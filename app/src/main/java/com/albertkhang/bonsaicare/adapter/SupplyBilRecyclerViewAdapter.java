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

import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.database.FeedReaderDbHelper;
import com.albertkhang.bonsaicare.database.ManipulationDb;
import com.albertkhang.bonsaicare.objectClass.SupplyBillItem;

import java.security.PublicKey;
import java.util.ArrayList;

public class SupplyBilRecyclerViewAdapter extends RecyclerView.Adapter<SupplyBilRecyclerViewAdapter.ViewHolder> {
    Context context;
    ArrayList<SupplyBillItem> supplyBillItemArrayList = new ArrayList<>();
    FeedReaderDbHelper dbHelper;

    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NOT_EMPTY = 1;

    public SupplyBilRecyclerViewAdapter(Context context) {
        this.context = context;
        dbHelper = new FeedReaderDbHelper(context);
    }

    public void update(ArrayList<SupplyBillItem> supplyBillItemArrayList) {
        this.supplyBillItemArrayList.clear();
        this.supplyBillItemArrayList.addAll(supplyBillItemArrayList);
        notifyDataSetChanged();
    }

    public void remove(ArrayList<SupplyBillItem> supplyBillItemArrayList, int position) {
        this.supplyBillItemArrayList.clear();
        this.supplyBillItemArrayList.addAll(supplyBillItemArrayList);

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, supplyBillItemArrayList.size());
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
            view = inflater.inflate(R.layout.supply_bill_item, parent, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        if (viewType == VIEW_TYPE_NOT_EMPTY) {
            handleIcon(holder.imgIcon, holder.getAdapterPosition());
            holder.txtDayBoughtValue.setText(supplyBillItemArrayList.get(holder.getAdapterPosition()).getDayBought());
            holder.txtTotalBoughtValue.setText(String.valueOf(supplyBillItemArrayList.get(holder.getAdapterPosition()).getTotalSupplies()));
            holder.txtSupplyItemUnit.setText(ManipulationDb.getSupplyUnitFromSupplyName(dbHelper, supplyBillItemArrayList.get(holder.getAdapterPosition()).getSupplyName()));
            holder.txtSupplyMoneyValue.setText(getMoneyFormat(supplyBillItemArrayList.get(holder.getAdapterPosition()).getTotalMoney(), true));

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

    private String getMoneyFormat(int money, boolean haveVND) {
        String s = "";
        int countChar = 0;
        char[] charArray = String.valueOf(money).toCharArray();

        for (int i = charArray.length; i > 0; i--) {
            s = s.concat(String.valueOf(charArray[i - 1]));
            countChar++;

            if (countChar == 3 && i != 1) {
                s = s.concat(".");
                countChar = 0;
                continue;
            }
        }

        StringBuffer reverse = new StringBuffer(s);
        reverse.reverse().toString();

        if (haveVND) {
            return reverse + " VND";
        } else {
            return String.valueOf(reverse);
        }
    }

    @Override
    public int getItemCount() {
        if (supplyBillItemArrayList.size() == 0) {
            return 1;
        } else {
            return supplyBillItemArrayList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (supplyBillItemArrayList.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_NOT_EMPTY;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout supply_item_frame;

        ImageView imgIcon;
        TextView txtDayBoughtValue;
        TextView txtTotalBoughtValue;
        TextView txtSupplyItemUnit;
        TextView txtSupplyMoneyValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            supply_item_frame = itemView.findViewById(R.id.supply_item_frame);

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
