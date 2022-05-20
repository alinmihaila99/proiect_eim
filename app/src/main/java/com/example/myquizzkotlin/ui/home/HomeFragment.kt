package com.example.myquizzkotlin.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myquizzkotlin.AlreadyCompletedDailyGoal
import com.example.myquizzkotlin.DailyGoal
import com.example.myquizzkotlin.LeaderBoard
import com.example.myquizzkotlin.R
import com.example.myquizzkotlin.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val startQuizzButton: Button = root.findViewById(R.id.button2);
        val seeDailyGoalButton: Button = root.findViewById(R.id.button3);
        val seeLeaderbordButton: Button = root.findViewById(R.id.button4);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

//        startQuizzButton.setOnClickListener { println("Start QUIZ!!!!!!!!") }
//        seeDailyGoalButton.setOnClickListener { println("Am apasat Daily Goal") }
        seeLeaderbordButton.setOnClickListener { val intent = Intent(context, LeaderBoard::class.java)
            startActivity(intent) }
        val settings = context?.getSharedPreferences("dailyGoal", 0)
        val dailyGoal = settings?.getInt("dailyGoal", 0)
        println("Daily goal is: ")
        println(dailyGoal);
        if(dailyGoal == 1){
            seeDailyGoalButton.setOnClickListener {  val intent = Intent(context, AlreadyCompletedDailyGoal::class.java)
                startActivity(intent) }
        }
        else{
            seeDailyGoalButton.setOnClickListener {  val intent = Intent(context, DailyGoal::class.java)
                startActivity(intent) }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}