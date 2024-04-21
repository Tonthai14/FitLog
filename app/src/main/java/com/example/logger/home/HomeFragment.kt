package com.example.logger.home

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.logger.DataAdapter
import com.example.logger.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeFragment : Fragment(), View.OnClickListener {
    private var dateYesterday: String? = null
    private var dateToday: String? = null
    private var dateTomorrow: String? = null
    private val sdf = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())
    private val calendar: Calendar = Calendar.getInstance()
    private var currentWeek: Array<String>? = null
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        ViewModelProvider(this)[HomeViewModel::class.java]
        val root: View = inflater.inflate(R.layout.fragment_home, container, false)

        val previousWeek: CardView = root.findViewById(R.id.previousWeek)
        previousWeek.setOnClickListener(this)
        val yesterday: CardView = root.findViewById(R.id.yesterday)
        yesterday.setOnClickListener(this)
        val today: CardView = root.findViewById(R.id.today)
        today.setOnClickListener(this)
        val tomorrow: CardView = root.findViewById(R.id.tomorrow)
        tomorrow.setOnClickListener(this)
        val thisWeek: CardView = root.findViewById(R.id.thisWeek)
        thisWeek.setOnClickListener(this)

        getCalendarDates()
        currentWeek = populateCurrentWeek()

        return root
    }

    private fun getCalendarDates() {
        dateToday = sdf.format(calendar.time)
        calendar.add(Calendar.DATE, 1)
        dateTomorrow = sdf.format(calendar.time)
        calendar.roll(Calendar.DATE, -2)
        dateYesterday = sdf.format(calendar.time)
    }

    private fun populateCurrentWeek(): Array<String> {
        val datesForWeek = Array(7) {""}
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        for (index in datesForWeek.indices) {
            datesForWeek[index] = sdf.format(calendar.time)
            calendar.add(Calendar.DATE, 1)
        }
        return datesForWeek
    }
    override fun onClick(view: View?) {
        //val intent = Intent(context, DayLayout::class.java)
        when (view?.id) {
            R.id.today -> {
                Log.d("HomeFrag", "Clicked on today: $dateToday")
                //intent.putExtra("date", dateToday)
            }
            //R.id.tomorrow -> intent.putExtra("date", dateTomorrow)
            //R.id.yesterday -> intent.putExtra("date", dateYesterday)
            R.id.thisWeek -> {
                displayDaysOfThisWeek(view)
                return
            }
        }
        //startActivity(intent)
    }

    private fun displayDaysOfThisWeek(view: View?) {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_day_entries)
        dialog.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.window?.setBackgroundDrawable(resources.getDrawable(R.drawable.round_menu))

        val days = resources.getStringArray(R.array.days)
        val recyclerView: RecyclerView = dialog.findViewById(R.id.recyclerView)
        val adapter = DataAdapter(context, days)
        recyclerView.layoutManager = LinearLayoutManager(view?.context)
        recyclerView.adapter = adapter

        val cancelBtn: Button = dialog.findViewById(R.id.cancel)
        cancelBtn.setOnClickListener { dialog.dismiss() }
        val confirmBtn: Button = dialog.findViewById(R.id.confirm)
        confirmBtn.setOnClickListener {
            //val day = Intent(context, DayLayout::class.java)
            if (adapter.lastCheckedPosition in 0..6) {
                //day.putExtra("date", currentWeek?.get(adapter.lastCheckedPosition))
            }
            //startActivity(day)
        }

        dialog.show()
    }
}