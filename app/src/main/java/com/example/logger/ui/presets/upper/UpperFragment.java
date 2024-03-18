package com.example.logger.ui.presets.upper;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.logger.R;


public class UpperFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_upper, container, false);

        if(root.findViewById(R.id.frame) != null) {
            UpperExercisesFragment ueFragment = new UpperExercisesFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction().add(R.id.frame, ueFragment).commit();
        }
        return root;
    }
}
