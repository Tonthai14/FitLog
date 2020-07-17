package com.example.firstapp.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.firstapp.DayLayout;
import com.example.firstapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DashboardFragment extends Fragment implements View.OnClickListener{

    private DashboardViewModel dashboardViewModel;
    private String date_today, date_tomorrow;
    String[] currentWeek = new String[7];

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        date_today = sdf.format(calendar.getTime());
        calendar.add(Calendar.DATE, 1);
        date_tomorrow = sdf.format(calendar.getTime());

        // Getting days of current week
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        for(int i = 0; i < currentWeek.length; i++) {
            if(sdf.format(calendar.getTime()).equals(date_today)) {
                currentWeek[i] = date_today;
            }
            else if(sdf.format(calendar.getTime()).equals(date_tomorrow)) {
                currentWeek[i] = date_tomorrow;
            } else {
                currentWeek[i] = sdf.format(calendar.getTime());
            }
        }

        // Buttons
        final Button view_today = root.findViewById(R.id.view_today);
        view_today.setOnClickListener(this);
        final Button view_tomorrow = root.findViewById(R.id.view_tomorrow);
        view_tomorrow.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.view_today:
                Intent today = new Intent(view.getContext(), DayLayout.class);
                today.putExtra("date", date_today);
                startActivity(today);
                break;
            case R.id.view_tomorrow:
                Intent tomorrow = new Intent(view.getContext(), DayLayout.class);
                tomorrow.putExtra("date", date_tomorrow);
                startActivity(tomorrow);
                break;
        }
    }
}