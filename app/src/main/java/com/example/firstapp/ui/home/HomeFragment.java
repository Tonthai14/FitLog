package com.example.firstapp.ui.home;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.DataAdapter;
import com.example.firstapp.DayLayout;
import com.example.firstapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private String date_yesterday, date_today, date_tomorrow;
    private final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
    private Calendar calendar = Calendar.getInstance();
    private String[] currentWeek;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        LinearLayout linearLayout = root.findViewById(R.id.linearLayout);

        CardView previousWeek = root.findViewById(R.id.previousWeek);
        previousWeek.setOnClickListener(this);
        CardView yesterday = root.findViewById(R.id.yesterday);
        yesterday.setOnClickListener(this);
        CardView today = root.findViewById(R.id.today);
        today.setOnClickListener(this);
        CardView tomorrow = root.findViewById(R.id.tomorrow);
        tomorrow.setOnClickListener(this);
        CardView thisWeek = root.findViewById(R.id.thisWeek);
        thisWeek.setOnClickListener(this);

        getCalendarDates();
        currentWeek = getCurrentWeek();
        return root;
    }

    private void getCalendarDates() {
        date_today = sdf.format(calendar.getTime());
        calendar.add(Calendar.DATE, 1);
        date_tomorrow = sdf.format(calendar.getTime());
        calendar.roll(Calendar.DATE, -2);
        date_yesterday = sdf.format(calendar.getTime());
    }

    private String[] getCurrentWeek() {
        final int WEEK_SIZE = 7;
        String[] weekDates = new String[WEEK_SIZE];
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        for(int i = 0; i < weekDates.length; i++) {
            weekDates[i] = sdf.format(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
        }
        return weekDates;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void dayOfWeekSelection(View view) {
        final Dialog dialog = new Dialog(requireActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.dialog_day_entries);

        String[] days = getResources().getStringArray(R.array.days);
        RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView);
        Button cancel = dialog.findViewById(R.id.cancel);
        Button confirm = dialog.findViewById(R.id.confirm);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.round_menu));

        final DataAdapter dataAdapter = new DataAdapter(getContext(), days);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(dataAdapter);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                dialog.dismiss();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(dataAdapter.getLastCheckedPosition()) {
                    case 0:
                        Intent sunday = new Intent(getContext(), DayLayout.class);
                        sunday.putExtra("date", currentWeek[0]);
                        startActivity(sunday);
                        break;
                    case 1:
                        Intent monday = new Intent(getContext(), DayLayout.class);
                        monday.putExtra("date", currentWeek[1]);
                        startActivity(monday);
                        break;
                    case 2:
                        Intent tuesday = new Intent(getContext(), DayLayout.class);
                        tuesday.putExtra("date", currentWeek[2]);
                        startActivity(tuesday);
                        break;
                    case 3:
                        Intent wednesday = new Intent(getContext(), DayLayout.class);
                        wednesday.putExtra("date", currentWeek[3]);
                        startActivity(wednesday);
                        break;
                    case 4:
                        Intent thursday = new Intent(getContext(), DayLayout.class);
                        thursday.putExtra("date", currentWeek[4]);
                        startActivity(thursday);
                        break;
                    case 5:
                        Intent friday = new Intent(getContext(), DayLayout.class);
                        friday.putExtra("date", currentWeek[5]);
                        startActivity(friday);
                        break;
                    case 6:
                        Intent saturday = new Intent(getContext(), DayLayout.class);
                        saturday.putExtra("date", currentWeek[6]);
                        startActivity(saturday);
                        break;
                }
            }
        });
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.today:
                Intent today = new Intent(getContext(), DayLayout.class);
                today.putExtra("date", date_today);
                startActivity(today);
                break;
            case R.id.tomorrow:
                Intent tomorrow = new Intent(getContext(), DayLayout.class);
                tomorrow.putExtra("date", date_tomorrow);
                startActivity(tomorrow);
                break;
            case R.id.yesterday:
                Intent yesterday = new Intent(getContext(), DayLayout.class);
                yesterday.putExtra("date", date_yesterday);
                startActivity(yesterday);
                break;
            case R.id.thisWeek:
                dayOfWeekSelection(view);
                //Intent thisWeek = new Intent(getContext(), DayLayout.class);
                break;
            case R.id.previousWeek:
                //Intent previousWeek = new Intent(getContext(), DayLayout.class);
                break;
        }
    }
}