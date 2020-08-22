package com.example.firstapp.ui.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.firstapp.R;

public class StatisticsFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle SavedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_statistics, container, false);

        return root;
    }
}
