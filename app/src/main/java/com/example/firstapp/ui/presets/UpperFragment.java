package com.example.firstapp.ui.presets;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.firstapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpperFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpperFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpperFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment upperFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpperFragment newInstance(String param1, String param2) {
        UpperFragment fragment = new UpperFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_upper, container, false);
        LinearLayout entriesPage = root.findViewById(R.id.entriesPage);
        entriesPage = new LinearLayout(root.getContext());

        RelativeLayout benchVariations = root.findViewById(R.id.benchPressVariations);
        RelativeLayout pushUpVariations = root.findViewById(R.id.pushUpVariations);
        RelativeLayout rowVariations = root.findViewById(R.id.rowVariations);
        benchVariations.setOnClickListener(this);
        pushUpVariations.setOnClickListener(this);
        rowVariations.setOnClickListener(this);
        
        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.benchPressVariations:

                break;
            case R.id.pushUpVariations:

                break;
        }
    }
}