package com.example.firstapp;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private List<Entry> entries;
    Context ct;

    public MyAdapter(Context ct, List<Entry> entries) {
        this.inflater = LayoutInflater.from(ct);
        this.entries = entries;
        this.ct = ct;
    }

    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView exercise, typeExercise, duration;
        ImageView exerciseVisual;

        public MyViewHolder(View itemView) {
            super(itemView);
            exercise = itemView.findViewById(R.id.exerciseName);
            typeExercise = itemView.findViewById(R.id.typeExercise);
            duration = itemView.findViewById(R.id.duration);
            exerciseVisual = itemView.findViewById(R.id.exerciseVisual);

            itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), Details.class);
                    intent.putExtra("ID", entries.get(getAdapterPosition()).getId());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    public void onBindViewHolder(@NonNull MyViewHolder holder, int pos) {
        Entry entry = entries.get(pos);
        String exercise = entry.getExercise();
        String typeExercise = entry.getExerciseType();

        switch (entry.getExerciseType()) {
            case "Weights":
                switch (entry.getWeightType()) {
                    case "Barbell":
                        holder.exerciseVisual.setImageResource(R.drawable.barbell);
                        break;
                    case "Dumbbells":
                        holder.exerciseVisual.setImageResource(R.drawable.dumbbell);
                        break;
                }
                break;
            case "Bodyweight":
                holder.exerciseVisual.setImageResource(R.drawable.bodyweight);
                break;
            case "Weighted":
                holder.exerciseVisual.setImageResource(R.drawable.weighted);
                break;
            case "Cardio":
                holder.exerciseVisual.setImageResource(R.drawable.running);
                break;
        }
        switch (entry.getProgramType()) {
            case "Sets x Reps":
                holder.duration.setText(ct.getString(R.string.programNumbers,
                        entry.getSets(), "Sets", "x", entry.getReps(), "Reps"));
                break;
            case "Sets x Duration":
                holder.duration.setText(ct.getString(R.string.programNumbers,
                        entry.getSets(), "Sets", "x", entry.getElapsedMin(), "Mins"));
                break;
            case "Reps":
                holder.duration.setText(ct.getString(R.string.programNumbers,
                        "", entry.getReps(), "Reps", "", ""));
                break;
            case "1 Rep Max":
                holder.duration.setText(entry.getProgramType());
                break;
        }
        holder.exercise.setText(exercise);
        if(typeExercise.equals("Weights")) {
            holder.typeExercise.setText(ct.getString(R.string.weightDetails,
                    entry.getWeightType(), "-", entry.getWeight(), entry.getWeightUnit()));
        } else {
            holder.typeExercise.setText(typeExercise);
        }
    }

    public int getItemCount() { return entries.size(); }
}
