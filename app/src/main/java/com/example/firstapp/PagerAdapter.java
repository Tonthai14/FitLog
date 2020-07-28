package com.example.firstapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.firstapp.ui.CustomFragment;
import com.example.firstapp.ui.LowerFragment;
import com.example.firstapp.ui.UpperFragment;

public class PagerAdapter extends FragmentStateAdapter {

    public PagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new UpperFragment();
            case 1:
                return new LowerFragment();
            default:
                return new CustomFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
