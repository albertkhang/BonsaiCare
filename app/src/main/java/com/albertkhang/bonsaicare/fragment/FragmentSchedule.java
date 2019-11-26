package com.albertkhang.bonsaicare.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.albertkhang.bonsaicare.database.FeedReaderDbHelper;
import com.albertkhang.bonsaicare.database.ManipulationDb;
import com.albertkhang.bonsaicare.objectClass.ScheduleItem;
import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.activity.schedule.ScheduleItemActivity;
import com.albertkhang.bonsaicare.adapter.ScheduleRecyclerViewAdapter;
import com.albertkhang.bonsaicare.animation.TickMarkAnimation;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentSchedule.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentSchedule#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSchedule extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView rvSchedule;
    ScheduleRecyclerViewAdapter adapter;
    ArrayList<ScheduleItem> scheduleItems;

    FeedReaderDbHelper dbHelper;

    public FragmentSchedule() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSchedule.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSchedule newInstance(String param1, String param2) {
        FragmentSchedule fragment = new FragmentSchedule();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        Log.d("FragmentSchedule", "created");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("_FragmentSchedule", "onViewCreated");

        addControl();
        addEvent();
    }

    private void addControl() {
        scheduleItems = new ArrayList<>();
        rvSchedule = getView().findViewById(R.id.rvSchedule);
        adapter = new ScheduleRecyclerViewAdapter(getContext());

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);

        rvSchedule.setLayoutManager(manager);
        rvSchedule.setAdapter(adapter);

        dbHelper = new FeedReaderDbHelper(getActivity());

        ManipulationDb.getAllDataScheduleTable(dbHelper, scheduleItems);
        adapter.sort(scheduleItems);
        adapter.update(scheduleItems);
    }

    private void addEvent() {
//        createFakeData(30);

        adapter.setOnTickClickListener(new ScheduleRecyclerViewAdapter.OnTickClickListener() {
            @Override
            public void onTickClickListener(View view, int position) {
                ImageView imageView = (ImageView) view;
                if (!scheduleItems.get(position).isTicked()) {
                    scheduleItems.get(position).setTicked(true);

                    imageView.setScaleX(0);
                    imageView.setScaleY(0);
                    imageView.setImageResource(R.drawable.ic_ticked);
                    imageView.setVisibility(View.VISIBLE);
                    TickMarkAnimation.showTickMark(imageView);

                    //update in Db
                    ManipulationDb.updateTickedSchedule(dbHelper, scheduleItems.get(position).getId(), 1);
                } else {
                    scheduleItems.get(position).setTicked(false);
                    imageView.setImageResource(R.drawable.ic_nottick);

                    ManipulationDb.updateTickedSchedule(dbHelper, scheduleItems.get(position).getId(), 0);
                }
            }
        });

        adapter.setOnItemClickListener(new ScheduleRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Intent intent = new Intent(getContext(), ScheduleItemActivity.class);
                startActivity(intent);
            }
        });
    }

    public void updateAdapter() {
        ManipulationDb.getAllDataScheduleTable(dbHelper, scheduleItems);
        adapter.sort(scheduleItems);
        adapter.update(scheduleItems);
    }

    private void createFakeData(int n) {
        for (int i = 0; i < n; i++) {
            ScheduleItem item = new ScheduleItem();
            scheduleItems.add(item);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("_FragmentSchedule", "onCreateView");

        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof OnFragmentInteractionListener) {
                mListener = (OnFragmentInteractionListener) context;
            }
        } catch (ClassCastException e) {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
