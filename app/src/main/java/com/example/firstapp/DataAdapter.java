package com.example.firstapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private Context context;
    private String[] days;

    public DataAdapter(Context ct, String[] days) {
        this.context = ct;
        this.days = days;
    }

    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.select_item_row, viewGroup, false);
        return new ViewHolder(view);
    }

    private int lastCheckedPosition = -1;

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ViewHolder viewHolder, int position) {
        viewHolder.radioButton.setChecked(position == lastCheckedPosition);
        viewHolder.item.setText(days[position]);

        // Remove bottom line from last entry
        if(position == days.length - 1) {
            viewHolder.divider.setVisibility(View.GONE);
        } else {
            viewHolder.divider.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return days.length;
    }

    public int getLastCheckedPosition() {
        return lastCheckedPosition;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RadioButton radioButton;
        TextView item;
        View divider;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.radioButton);
            item = itemView.findViewById(R.id.item);
            divider = itemView.findViewById(R.id.divider);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastCheckedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }
    }
}
