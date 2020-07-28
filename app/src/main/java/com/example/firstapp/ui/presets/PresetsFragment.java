package com.example.firstapp.ui.presets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.example.firstapp.PagerAdapter;
import com.example.firstapp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class PresetsFragment extends Fragment {

    private PresetsViewModel presetsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        presetsViewModel =
                ViewModelProviders.of(this).get(PresetsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_presets, container, false);

        ViewPager2 viewPager = root.findViewById(R.id.presets_layout);
        viewPager.setAdapter(new PagerAdapter(getActivity()));

        TabLayout tabLayout = root.findViewById(R.id.preset_tabs);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position) {
                            case 0:
                                tab.setText("Upper Body");
                                break;
                            case 1:
                                tab.setText("Lower Body");
                                break;
                            case 2:
                                tab.setText("Custom");
                                break;
                        }
                    }
                });
        tabLayoutMediator.attach();

        return root;
    }
}