package com.example.myquizzkotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myquizzkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val startQuizzButton: Button = findViewById(R.id.button2);
        val seeDailyGoalButton: Button = findViewById(R.id.button3);
        val seeLeaderbordButton: Button = findViewById(R.id.button4);

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        startQuizzButton.setOnClickListener { println("Am apasat Start Quizz!!!")}
        seeDailyGoalButton.setOnClickListener { println("Am apasat Daily Goal") }
        seeLeaderbordButton.setOnClickListener { val intent = Intent(this, LeaderBoard::class.java)
            startActivity(intent) }
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}