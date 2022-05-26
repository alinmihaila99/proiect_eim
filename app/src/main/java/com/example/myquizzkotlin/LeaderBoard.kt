package com.example.myquizzkotlin

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.example.myquizzkotlin.api.RetrofitInstance
import com.example.myquizzkotlin.databinding.ActivityLeaderBoardBinding
import com.example.myquizzkotlin.model.Profile
import com.example.myquizzkotlin.model.Question
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LeaderBoard : AppCompatActivity() {

    private lateinit var binding: ActivityLeaderBoardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLeaderBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))


        val leaderboardText: TextView = findViewById(R.id.leaderboard123)
        var auxLeaderboard = mutableListOf<String>();

        val call = RetrofitInstance.api.getLeaderboard();

        var leaderboard:MutableList<String> = mutableListOf();
        call.enqueue(object: Callback<MutableList<String>> {
            override fun onFailure(call: Call<MutableList<String>>, t: Throwable) {
                Log.e("Fail!!", "Fail####" + t.message)
            }

            override fun onResponse(
                call: Call<MutableList<String>>,
                response: Response<MutableList<String>>
            ) {
                if(response.isSuccessful()){
                    val leaderBoardFromDatabase = response.body();
                    leaderboard = leaderBoardFromDatabase!!;
                    println(leaderboard);
                    for(str in leaderboard){
                        auxLeaderboard.add(str);
                        auxLeaderboard.add("\n");
                    }
                    leaderboardText.text = auxLeaderboard.toString().replace("[", "")
                        .replace(",", "")
                        .replace("]", "");
                }
            }
        })
    }
}