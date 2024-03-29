package com.example.logger

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(ct: Context, entries: List<Entry?>?, date: String?) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private var inflater: LayoutInflater? = null
    private var entries: List<Entry?>? = null
    var date: String? = null
    private var ct: Context? = null

    init {
        this.inflater = LayoutInflater.from(ct)
        this.entries = entries
        this.date = date
        this.ct = ct
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater?.inflate(R.layout.exercise_entry_row, parent, false)
        return MyViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val entry = entries?.get(position)

        when (entry?.exerciseType) {
            "Weights" -> {
                when (entry.weightType) {
                    "Barbell" -> holder.exerciseVisual?.setImageResource(R.drawable.barbell)
                    "Dumbbells" -> holder.exerciseVisual?.setImageResource(R.drawable.dumbbell)
                }
            }
            "Bodyweight" -> holder.exerciseVisual?.setImageResource(R.drawable.bodyweight)
            "Weighted" -> holder.exerciseVisual?.setImageResource(R.drawable.weighted)
            "Cardio" -> holder.exerciseVisual?.setImageResource(R.drawable.running)
        }
        when (entry?.programType) {
            "Sets x Reps" -> holder.duration?.text = ct?.getString(R.string.programNumbers, entry.sets, "Sets", "x", entry.reps, "Reps")
            "Sets x Duration" -> holder.duration?.text = ct?.getString(R.string.programNumbers, entry.sets, "Sets", "x", entry.elapsedMin, "Mins")
            "Reps" -> holder.duration?.text = ct?.getString(R.string.programNumbers, "", entry.reps, "Reps", "", "")
            "1 Rep Max" -> holder.duration?.text = entry.programType
        }
        holder.exercise?.text = entry?.exercise

        if (entry?.exerciseType == "Weights") {
            holder.typeExercise?.text = ct?.getString(R.string.weightDetails, entry.weightType, "-", entry.weight, entry.weightUnit)
        } else {
            holder.typeExercise?.text = entry?.exerciseType
        }
    }

    override fun getItemCount(): Int {
        return entries?.size!!
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var exercise: TextView? = null
        var typeExercise: TextView? = null
        var duration: TextView? = null
        var exerciseVisual: ImageView? = null

        init {
            exercise = itemView.findViewById(R.id.exerciseName)
            typeExercise = itemView.findViewById(R.id.typeExercise)
            duration = itemView.findViewById(R.id.duration)
            exerciseVisual = itemView.findViewById(R.id.exerciseVisual)
            itemView.setOnClickListener {
                val details = Intent(it.context, Details::class.java)
                details.putExtra("ID", entries?.get(adapterPosition)?.id)
                details.putExtra("date", date)
                val CONFIRM_ENTRY_DELETION = 1 // TODO: Not properly getting rid of old entry list
                (it.context as Activity).startActivityForResult(details, CONFIRM_ENTRY_DELETION)
            }
        }
    }
}