package com.albertkhang.bonsaicare.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.database.FeedReaderDbHelper;
import com.albertkhang.bonsaicare.database.ManipulationDb;
import com.albertkhang.bonsaicare.database.SharedPreferencesSetting;

import static java.lang.String.valueOf;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentSetting.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentSetting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSetting extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ConstraintLayout fragment_layout_setting;

    ConstraintLayout frame_BonsaiNameDetail;
    ConstraintLayout frame_MaxMoneyPerSupply;

    EditText txtMaxBonsaiEdit;
    EditText txtMaxMoneyEdit;

    TextView txtSettingMaxBonsaiValue;
    TextView txtSettingMaxMoneyPerSupplyValue;

    TextView txtSettingMaxBonsaiTitle;
    TextView txtSettingMaxMoneyPerSupplyTitle;

    FeedReaderDbHelper dbHelper;

    public FragmentSetting() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSetting.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSetting newInstance(String param1, String param2) {
        FragmentSetting fragment = new FragmentSetting();
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
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addControl();
        addEvent();
    }

    private void addControl() {
        fragment_layout_setting = getView().findViewById(R.id.fragment_layout_setting);

        frame_BonsaiNameDetail = getView().findViewById(R.id.frame_BonsaiNameDetail);
        frame_MaxMoneyPerSupply = getView().findViewById(R.id.frame_MaxMoneyPerSupply);

        txtMaxBonsaiEdit = getView().findViewById(R.id.txtMaxBonsaiEdit);
        txtMaxMoneyEdit = getView().findViewById(R.id.txtMaxMoneyEdit);

        txtSettingMaxBonsaiValue = getView().findViewById(R.id.txtSettingMaxBonsaiValue);
        txtSettingMaxMoneyPerSupplyValue = getView().findViewById(R.id.txtSettingMaxMoneyPerSupplyValue);

        txtSettingMaxBonsaiTitle = getView().findViewById(R.id.txtSettingMaxBonsaiTitle);
        txtSettingMaxMoneyPerSupplyTitle = getView().findViewById(R.id.txtSettingMaxMoneyPerSupplyTitle);

        getSetting();

        dbHelper = new FeedReaderDbHelper(getContext());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void addEvent() {
        fragment_layout_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLongText(frame_BonsaiNameDetail);
                setLongText(frame_MaxMoneyPerSupply);

                hideEditText(frame_BonsaiNameDetail);
                hideEditText(frame_MaxMoneyPerSupply);

                hideKeyboard(frame_BonsaiNameDetail);
                hideKeyboard(frame_MaxMoneyPerSupply);
            }
        });

        frame_BonsaiNameDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLongText(frame_MaxMoneyPerSupply);
                setShortText(frame_BonsaiNameDetail);

                hideEditText(frame_MaxMoneyPerSupply);
                showEditText(frame_BonsaiNameDetail);

                hideKeyboard(frame_MaxMoneyPerSupply);
                showKeyboard(frame_BonsaiNameDetail);

                setPreData(frame_BonsaiNameDetail);
            }
        });

        frame_MaxMoneyPerSupply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLongText(frame_BonsaiNameDetail);
                setShortText(frame_MaxMoneyPerSupply);

                hideEditText(frame_BonsaiNameDetail);
                showEditText(frame_MaxMoneyPerSupply);

                hideKeyboard(frame_BonsaiNameDetail);
                showKeyboard(frame_MaxMoneyPerSupply);

                setPreData(frame_MaxMoneyPerSupply);
            }
        });

        txtMaxBonsaiEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    int newMaxBonsai = Integer.parseInt(txtMaxBonsaiEdit.getText().toString());
                    String errorText = ManipulationDb.getErrorText(dbHelper, newMaxBonsai);

                    if (errorText != null) {
                        txtMaxBonsaiEdit.setError(errorText);
                    } else {
                        setMaxBonsaiSetting(newMaxBonsai);

                        setLongText(frame_BonsaiNameDetail);
                        setLongText(frame_MaxMoneyPerSupply);

                        hideEditText(frame_BonsaiNameDetail);
                        hideEditText(frame_MaxMoneyPerSupply);

                        hideKeyboard(frame_BonsaiNameDetail);
                        hideKeyboard(frame_MaxMoneyPerSupply);

                        getSetting();

                        Toast.makeText(getContext(), "Change success!", Toast.LENGTH_LONG).show();
                    }

                    return true;
                }

                return false;
            }
        });

        txtMaxMoneyEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    SharedPreferencesSetting setting = new SharedPreferencesSetting(getContext());
                    setting.updateMaxMoney(Integer.parseInt(txtMaxMoneyEdit.getText().toString()));

                    setLongText(frame_BonsaiNameDetail);
                    setLongText(frame_MaxMoneyPerSupply);

                    hideEditText(frame_BonsaiNameDetail);
                    hideEditText(frame_MaxMoneyPerSupply);

                    hideKeyboard(frame_BonsaiNameDetail);
                    hideKeyboard(frame_MaxMoneyPerSupply);

                    getSetting();

                    return true;
                }

                return false;
            }
        });

        fragment_layout_setting.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                setDefaultUI();

                return false;
            }
        });
    }

    private int convertMoneyToInteger(String moneyString) {
        String s = moneyString.replace(".", "");
        int integerMoney = Integer.parseInt(s);
        Log.d("_convertMoneyToInteger", "" + integerMoney);

        return integerMoney;
    }

    private void setMaxBonsaiSetting(int newMaxBonsai) {
        SharedPreferencesSetting setting = new SharedPreferencesSetting(getContext());
        setting.updateMaxBonsai(newMaxBonsai);
    }

    private void getSetting() {
        SharedPreferencesSetting setting = new SharedPreferencesSetting(getContext());
        Log.d("_SharedPreferences", String.valueOf(setting.getMaxBonsai()));
        Log.d("_SharedPreferences", String.valueOf(setting.getMaxMoney()));

        txtSettingMaxBonsaiValue.setText(String.valueOf(setting.getMaxBonsai()));
        txtSettingMaxMoneyPerSupplyValue.setText(getMoneyFormat(setting.getMaxMoney(), true));
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

    private void setPreData(ConstraintLayout layout) {
        SharedPreferencesSetting setting = new SharedPreferencesSetting(getContext());

        if (layout == frame_BonsaiNameDetail) {
            txtMaxBonsaiEdit.setText(String.valueOf(setting.getMaxBonsai()));
            txtMaxBonsaiEdit.setSelection(txtMaxBonsaiEdit.getText().length());
        }

        if (layout == frame_MaxMoneyPerSupply) {
            txtMaxMoneyEdit.setText(String.valueOf(setting.getMaxMoney()));//change format when show here
//            txtMaxMoneyEdit.setText(getMoneyFormat(setting.getMaxMoney(), false));

            txtMaxMoneyEdit.setSelection(txtMaxMoneyEdit.getText().length());
        }
    }

    private void showEditText(ConstraintLayout layout) {
        if (layout == frame_BonsaiNameDetail) {
            txtSettingMaxBonsaiValue.setVisibility(View.GONE);
            txtMaxBonsaiEdit.setVisibility(View.VISIBLE);
        }

        if (layout == frame_MaxMoneyPerSupply) {
            txtSettingMaxMoneyPerSupplyValue.setVisibility(View.GONE);
            txtMaxMoneyEdit.setVisibility(View.VISIBLE);
        }

    }

    private void hideEditText(ConstraintLayout layout) {
        if (layout == frame_BonsaiNameDetail) {
            txtSettingMaxBonsaiValue.setVisibility(View.VISIBLE);
            txtMaxBonsaiEdit.setVisibility(View.GONE);
        }

        if (layout == frame_MaxMoneyPerSupply) {
            txtSettingMaxMoneyPerSupplyValue.setVisibility(View.VISIBLE);
            txtMaxMoneyEdit.setVisibility(View.GONE);
        }

    }

    private void setShortText(ConstraintLayout layout) {
        if (layout == frame_BonsaiNameDetail) {
            txtSettingMaxBonsaiTitle.setText(getString(R.string.maxBonsaiPerPlacementShort));
        }

        if (layout == frame_MaxMoneyPerSupply) {
            txtSettingMaxMoneyPerSupplyTitle.setText(getString(R.string.maxMoneyPerSupplyShort));
        }
    }

    private void setLongText(ConstraintLayout layout) {
        if (layout == frame_BonsaiNameDetail) {
            txtMaxBonsaiEdit.setError(null);
            txtSettingMaxBonsaiTitle.setText(getString(R.string.maxBonsaiPerPlacement));
        }

        if (layout == frame_MaxMoneyPerSupply) {
            txtMaxMoneyEdit.setError(null);
            txtSettingMaxMoneyPerSupplyTitle.setText(getString(R.string.maxMoneyPerSupply));
        }
    }

    private void showKeyboard(ConstraintLayout layout) {
        if (layout == frame_BonsaiNameDetail) {
            txtMaxBonsaiEdit.requestFocus();
        }

        if (layout == frame_MaxMoneyPerSupply) {
            txtMaxMoneyEdit.requestFocus();
        }

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    private void hideKeyboard(ConstraintLayout layout) {
        if (layout == frame_BonsaiNameDetail) {
            txtMaxBonsaiEdit.clearFocus();
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(txtMaxBonsaiEdit.getWindowToken(), 0);
        }

        if (layout == frame_MaxMoneyPerSupply) {
            txtMaxMoneyEdit.clearFocus();
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(txtMaxMoneyEdit.getWindowToken(), 0);
        }
    }

    private void setDefaultUI() {
        Log.d("_fragment", "setDefaultUI");
        setLongText(frame_BonsaiNameDetail);
        setLongText(frame_MaxMoneyPerSupply);

        hideEditText(frame_BonsaiNameDetail);
        hideEditText(frame_MaxMoneyPerSupply);

        hideKeyboard(frame_BonsaiNameDetail);
        hideKeyboard(frame_MaxMoneyPerSupply);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        Log.d("_fragment", "onCreateView");
        return inflater.inflate(R.layout.fragment_setting, container, false);
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
