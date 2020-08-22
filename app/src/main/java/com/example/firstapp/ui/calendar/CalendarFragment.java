package com.example.firstapp.ui.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.firstapp.DayLayout;
import com.example.firstapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalendarFragment extends Fragment {
    private CalendarView calendarView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        calendarView = root.findViewById(R.id.calendarView);

        return root;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {
                String date = null;
                try {
                    // Month ranges 0 - 11 so add 1 for regular month numbers
                    Date toFormat = sdf.parse((month + 1) + "-" + day + "-" + year);
                    assert toFormat != null;
                    date = sdf.format(toFormat);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(view.getContext(), DayLayout.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });
    }
}
