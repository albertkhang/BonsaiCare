package com.albertkhang.bonsaicare.adapter;

import android.content.Context;
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
import com.albertkhang.bonsaicare.objectClass.BonsaiStatusReportItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BonsaiStatusReportAdapter extends RecyclerView.Adapter<BonsaiStatusReportAdapter.ViewHolder> {
    Context context;
    ArrayList<BonsaiStatusReportItem> bonsaiStatusReportArrayList = new ArrayList<>();
    FeedReaderDbHelper dbHelper;

    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NOT_EMPTY = 1;

    public BonsaiStatusReportAdapter(Context context) {
        this.context = context;
        dbHelper = new FeedReaderDbHelper(context);
    }

    public void update(int month, int year) {
        bonsaiStatusReportArrayList.clear();
        ManipulationDb.getBonsaiStatusReportData(dbHelper, bonsaiStatusReportArrayList);

        //filter day year valid
        ArrayList<BonsaiStatusReportItem> temp = new ArrayList<>();
        for (int i = 0; i < bonsaiStatusReportArrayList.size(); i++) {
            if (!bonsaiStatusReportArrayList.get(i).getBonsaiDayTakeCare().equals("")) {
                if (isMonthYearValid(bonsaiStatusReportArrayList.get(i), month, year)) {
                    temp.add(bonsaiStatusReportArrayList.get(i));
                }
            }
        }

        bonsaiStatusReportArrayList.clear();

        if (temp.size() > 0) {
            bonsaiStatusReportArrayList.addAll(temp);
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
            view = inflater.inflate(R.layout.item_bonsai_status_report, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == VIEW_TYPE_NOT_EMPTY) {
            //icon
            handleIcon(bonsaiStatusReportArrayList.get(position).getBonsaiPlace(), holder.imgIcon);

            //ticked
            handleTicked(bonsaiStatusReportArrayList.get(position).isTicked(), holder.imgTicked);

            //No.
            holder.txtNoValue.setText("No. " + (position + 1));

            //bonsaiName
            holder.txtBonsaiName.setText(bonsaiStatusReportArrayList.get(position).getBonsaiName());

            //bonsaiType
            holder.txtBonsaiTypeValue.setText(bonsaiStatusReportArrayList.get(position).getBonsaiType());

            //bonsaiDayTakeCare
            holder.txtDayTakeCareValue.setText(bonsaiStatusReportArrayList.get(position).getBonsaiDayTakeCare());
        }
    }

    @Override
    public int getItemCount() {
        if (bonsaiStatusReportArrayList.size() == 0) {
            Log.d("_BonsaiStatusReport", "isEmpty");
            return 1;
        } else {
            Log.d("_BonsaiStatusReport", "!isEmpty");
            return bonsaiStatusReportArrayList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (bonsaiStatusReportArrayList.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_NOT_EMPTY;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView txtNoValue;

        TextView txtBonsaiName;
        TextView txtBonsaiTypeValue;
        TextView txtDayTakeCareValue;

        ImageView imgTicked;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgIcon = itemView.findViewById(R.id.imgIcon);
            txtNoValue = itemView.findViewById(R.id.txtNoValue);

            txtBonsaiName = itemView.findViewById(R.id.txtBonsaiName);
            txtBonsaiTypeValue = itemView.findViewById(R.id.txtBonsaiTypeValue);
            txtDayTakeCareValue = itemView.findViewById(R.id.txtDayTakeCareValue);

            imgTicked = itemView.findViewById(R.id.imgTicked);
        }
    }

    private static boolean isMonthYearValid(BonsaiStatusReportItem item, int month, int year) {
        if (year == getYear(item.getBonsaiDayTakeCare())) {
            if (month == getMonth(item.getBonsaiDayTakeCare())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private static int getMonth(String date) {
        Date c = null;
        if (!date.isEmpty()) {
            try {
                SimpleDateFormat df = new SimpleDateFormat("MM - dd - yyyy");
                c = df.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(c);
        int month = calendar.get(Calendar.MONTH) + 1;

        return month;
    }

    private static int getYear(String date) {
        Date c = null;
        if (!date.isEmpty()) {
            try {
                SimpleDateFormat df = new SimpleDateFormat("MM - dd - yyyy");
                c = df.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(c);
        int year = calendar.get(Calendar.YEAR);

        return year;
    }

    private void handleTicked(boolean isTicked, ImageView imgTicked) {
        if (isTicked) {
            imgTicked.setVisibility(View.VISIBLE);
        } else {
            imgTicked.setVisibility(View.INVISIBLE);
        }
    }

    private void handleIcon(String bonsaiPlace, ImageView imgIcon) {
        if (bonsaiPlace.equals("Balcony")) {
            imgIcon.setImageResource(R.drawable.ic_balcony);
        } else {
            if (bonsaiPlace.equals("Window")) {
                imgIcon.setImageResource(R.drawable.ic_window);
            } else {
                if (bonsaiPlace.equals("Gate")) {
                    imgIcon.setImageResource(R.drawable.ic_gate);
                } else {
                    imgIcon.setImageResource(R.drawable.ic_icon_placement);
                }
            }
        }
    }
}
