package com.example.firstapp.ui.presets.upper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.firstapp.R;

public class UpperExercisesFragment extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_upper_exercises, container, false);

        RelativeLayout benchVariations = root.findViewById(R.id.benchVariations);
        RelativeLayout pushUpVariations = root.findViewById(R.id.pushUpVariations);
        RelativeLayout rowVariations = root.findViewById(R.id.rowVariations);

        benchVariations.setOnClickListener(this);
        pushUpVariations.setOnClickListener(this);
        rowVariations.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.benchVariations:
                BenchFragment benchFragment = new BenchFragment();
                loadFragment(benchFragment);
                break;
            case R.id.pushUpVariations:
                PushUpFragment pushUpFragment = new PushUpFragment();
                loadFragment(pushUpFragment);
                break;
            case R.id.rowVariations:
                RowFragment rowFragment = new RowFragment();
                loadFragment(rowFragment);
                break;
        }
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame, fragment);
        ft.addToBackStack(fragment.toString());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }
}
