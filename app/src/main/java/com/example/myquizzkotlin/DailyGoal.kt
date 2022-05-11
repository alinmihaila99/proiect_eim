package com.example.myquizzkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.myquizzkotlin.api.RetrofitInstance
import com.example.myquizzkotlin.model.Question
import com.example.myquizzkotlin.model.UserId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DailyGoal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_goal)

        var count = 0;
        var score = 0;
        var questionCount = 1;
        val radio_group : RadioGroup = findViewById(R.id.radio_group)

        val settings = applicationContext.getSharedPreferences("dailyGoal", 0)
        val userId = settings.getInt("logedinUserId", 2)

        val radioButton1 : RadioButton = findViewById(R.id.red)
        val radioButton2 : RadioButton = findViewById(R.id.green)
        val radioButton3 : RadioButton = findViewById(R.id.yellow)
        val radioButton4 : RadioButton = findViewById(R.id.pink)
        val nextButton : Button = findViewById(R.id.button5)
        val question : TextView = findViewById(R.id.title)
        val scoreView : TextView = findViewById(R.id.textView2)
        val rnds = (0..9).random();
        println(rnds)

        val call = RetrofitInstance.api.getQuestions();
        var questionsList:MutableList<Question> = mutableListOf();
        call.enqueue(object: Callback<MutableList<Question>> {
            override fun onFailure(call: Call<MutableList<Question>>, t: Throwable) {
                Log.e("Fail!!", "Fail####" + t.message)
            }

            override fun onResponse(
                call: Call<MutableList<Question>>,
                response: Response<MutableList<Question>>
            ) {
                if(response.isSuccessful()){
                    val questionsFromDatabase = response.body();
                    questionsList = questionsFromDatabase!!;
                    println(questionsFromDatabase?.get(0));
                    println(questionsList);
                    question.text = questionsList[rnds].question;
                    radioButton1.text = questionsList[rnds].response1;
                    radioButton2.text = questionsList[rnds].response2;
                    radioButton3.text = questionsList[rnds].response3;
                    radioButton4.text = questionsList[rnds].response4;
                }
            }
        })

        nextButton.setOnClickListener {
            val callUpdateDaily = RetrofitInstance.api.updateDaily(UserId(userId));
            callUpdateDaily.enqueue(object : Callback<UserId> {
                override fun onFailure(call: Call<UserId>, t: Throwable) {
                    Log.e("Fail!!", "Fail####" + t.message)
                    val editor = settings.edit()
                    editor.putInt("dailyGoal", 1)
                    editor.apply()

                }

                override fun onResponse(call: Call<UserId>, response: Response<UserId>) {
                    val editor = settings.edit()
                    editor.putInt("dailyGoal", 1)
                    editor.apply()
                }
            })
        }

    }
}