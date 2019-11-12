package com.albertkhang.bonsaicare.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.activity.MainActivity;
import com.albertkhang.bonsaicare.activity.manage.ManageList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fragment_manage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_manage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_manage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ConstraintLayout frame_bonsai;
    ConstraintLayout frame_placement;
    ConstraintLayout frame_supplies;
    ConstraintLayout frame_report;

    public fragment_manage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_manage.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_manage newInstance(String param1, String param2) {
        fragment_manage fragment = new fragment_manage();
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

        Log.d("fragment_manage", "created");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addControl();
        addEvent();
    }

    private void addControl() {
        frame_bonsai = getView().findViewById(R.id.frame_bonsai);
        frame_placement = getView().findViewById(R.id.frame_placement);
        frame_supplies = getView().findViewById(R.id.frame_supplies);
        frame_report = getView().findViewById(R.id.frame_report);
    }

    private void addEvent() {
        frame_bonsai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ManageList.class, R.string.titleBonsai);
            }
        });

        frame_placement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ManageList.class, R.string.titlePlacement);
            }
        });

        frame_supplies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ManageList.class, R.string.titleSupplies);
            }
        });

        frame_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ManageList.class, R.string.titleReport);
            }
        });
    }

    private void startActivity(Class<ManageList> manageListClass, int titleId) {
        Intent intent = new Intent(getContext(), manageListClass);
        intent.putExtra(getString(R.string.putExtraManageTitle), getString(titleId));

        switch (titleId) {
            case R.string.titleBonsai:
                intent.putExtra(getString(R.string.putExtraManageShowIcon), "true");
                intent.putExtra(getString(R.string.putExtraManageLoadList), getString(R.string.titleBonsai));
                break;
            case R.string.titlePlacement:
                intent.putExtra(getString(R.string.putExtraManageShowIcon), "false");
                intent.putExtra(getString(R.string.putExtraManageLoadList), getString(R.string.titlePlacement));
                break;
            case R.string.titleSupplies:
                intent.putExtra(getString(R.string.putExtraManageShowIcon), "false");
                intent.putExtra(getString(R.string.putExtraManageLoadList), getString(R.string.titleSupplies));
                break;
            case R.string.titleReport:
                intent.putExtra(getString(R.string.putExtraManageShowIcon), "false");
                intent.putExtra(getString(R.string.putExtraManageLoadList), getString(R.string.titleReport));
                break;
        }

        startActivity(intent);
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
