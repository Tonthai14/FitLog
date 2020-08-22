package com.example.firstapp.ui.body;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.firstapp.R;

public class BodyFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_body, container, false);

        final TextView displayWeight = root.findViewById(R.id.displayWeight);
        final EditText weightInput = root.findViewById(R.id.weightInput);
        Button update = root.findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weightSaved = weightInput.getText().toString();
                displayWeight.setText(weightSaved);
                Toast.makeText(getContext(), "Weight Updated", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }
}
