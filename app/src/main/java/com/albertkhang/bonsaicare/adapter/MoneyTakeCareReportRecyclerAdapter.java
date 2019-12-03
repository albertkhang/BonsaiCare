package com.albertkhang.bonsaicare.adapter;

import android.content.Context;
import android.util.Log;
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
import com.albertkhang.bonsaicare.objectClass.MoneyTakeCareReportItem;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MoneyTakeCareReportRecyclerAdapter extends RecyclerView.Adapter<MoneyTakeCareReportRecyclerAdapter.ViewHolder> {
    Context context;
    TextView txtSumOfMoney;
    int sumOfMoney = 0;

    ArrayList<MoneyTakeCareReportItem> moneyTakeCareReportArrayList = new ArrayList<>();
    FeedReaderDbHelper dbHelper;

    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NOT_EMPTY = 1;

    private static boolean isEmpty = false;

    public MoneyTakeCareReportRecyclerAdapter(Context context, TextView txtSumOfMoney) {
        this.context = context;
        this.txtSumOfMoney = txtSumOfMoney;
        dbHelper = new FeedReaderDbHelper(context);
    }

    public void update(int month, int year) {
//        Log.d("_update", "month: " + month);
//        Log.d("_update", "year: " + year);
        //get data
        moneyTakeCareReportArrayList.clear();
        ManipulationDb.getMoneyTakeCareReportData(dbHelper, moneyTakeCareReportArrayList, month, year);

        //find sum of money - set sum of money
        sumOfMoney = 0;
        for (int i = 0; i < moneyTakeCareReportArrayList.size(); i++) {
            sumOfMoney += moneyTakeCareReportArrayList.get(i).getTotalMoney();
        }

        for (int i = 0; i < moneyTakeCareReportArrayList.size(); i++) {
            if (sumOfMoney != 0) {
                double itemMoney = moneyTakeCareReportArrayList.get(i).getTotalMoney();
                double totalMoney = sumOfMoney;
                double percentage = (itemMoney / totalMoney) * 100;

                Log.d("_update", "itemMoney: " + itemMoney);
                Log.d("_update", "totalMoney: " + totalMoney);
                Log.d("_update", "percentage: " + percentage);

                moneyTakeCareReportArrayList.get(i).setPercentage(percentage);
            } else {
                moneyTakeCareReportArrayList.get(i).setPercentage(0);
            }
        }
        txtSumOfMoney.setText(getMoneyFormat(sumOfMoney, true));

        //notify date set change
        if (sumOfMoney == 0) {
            isEmpty = true;
        } else {
            isEmpty = false;
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        if (viewType == VIEW_TYPE_EMPTY) {
            view = inflater.inflate(R.layout.item_empty_layout, parent, false);
        } else {
            view = inflater.inflate(R.layout.item_money_take_care_report, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == VIEW_TYPE_NOT_EMPTY) {
            handleIcon(moneyTakeCareReportArrayList.get(position).getSupplyName(), holder.imgIcon);

            holder.txtSupplyName.setText(moneyTakeCareReportArrayList.get(position).getSupplyName());
            holder.txtTotalSupplyBillValue.setText(String.valueOf(moneyTakeCareReportArrayList.get(position).getTotalBill()));
            holder.txtTotalMoneyBoughtValue.setText(getMoneyFormat(moneyTakeCareReportArrayList.get(position).getTotalMoney(), true));

            holder.txtNoValue.setText("No. " + (position + 1));

            String percentage = new DecimalFormat("0.00").format(moneyTakeCareReportArrayList.get(position).getPercentage());
            holder.txtPercentageValue.setText(percentage + "%");
        }
    }

    @Override
    public int getItemCount() {
        if (isEmpty) {
            return 1;
        } else {
            return moneyTakeCareReportArrayList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isEmpty) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_NOT_EMPTY;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;

        TextView txtSupplyName;
        TextView txtTotalSupplyBillValue;
        TextView txtTotalMoneyBoughtValue;

        TextView txtNoValue;
        TextView txtPercentageValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgIcon = itemView.findViewById(R.id.imgIcon);

            txtSupplyName = itemView.findViewById(R.id.txtSupplyName);
            txtTotalSupplyBillValue = itemView.findViewById(R.id.txtTotalSupplyBillValue);
            txtTotalMoneyBoughtValue = itemView.findViewById(R.id.txtTotalMoneyBoughtValue);

            txtNoValue = itemView.findViewById(R.id.txtNoValue);
            txtPercentageValue = itemView.findViewById(R.id.txtPercentageValue);
        }
    }

    private void handleIcon(String supplyName, ImageView icon) {
        if (supplyName.equals("Water")) {
            icon.setImageResource(R.drawable.ic_water);
        } else {
            if (supplyName.equals("Nitrogen fertilizer")) {
                icon.setImageResource(R.drawable.ic_nitrogen_fertilizer);
            } else {
                icon.setImageResource(R.drawable.ic_icon_supplies);
            }
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
}
