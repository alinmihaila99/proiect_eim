package com.example.myquizzkotlin.ui.dashboard

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myquizzkotlin.databinding.FragmentDashboardBinding
import android.widget.*
import android.widget.RadioGroup
import com.example.myquizzkotlin.api.RetrofitInstance
import com.example.myquizzkotlin.model.Question
import com.example.myquizzkotlin.model.UpdateScore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val call = RetrofitInstance.api.getQuestions();
        val settings = requireContext().getSharedPreferences("dailyGoal", 0)
        val userId = settings.getInt("logedinUserId", 2)

        var count = 0;
        var score = 0;
        var questionCount = 1;
        val radio_group = binding.radioGroup;

        val radioButton1 = binding.red;
        val radioButton2 = binding.green;
        val radioButton3 = binding.yellow;
        val radioButton4 = binding.pink;
        val nextButton = binding.button5;
        val question = binding.title;
        val scoreView = binding.textView2;
        val mTextField = binding.textView3;
        var flag = false;

        object : CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                mTextField.setText("seconds remaining: " + millisUntilFinished / 1000)
            }

            override fun onFinish() {
                questionCount = 5;
                mTextField.setText("done!")
            }
        }.start()

        var questionsList:MutableList<Question> = mutableListOf();
        call.enqueue(object: Callback<MutableList<Question>>{
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
                    question.text = questionsList[0].question;
                    radioButton1.text = questionsList[0].response1;
                    radioButton2.text = questionsList[0].response2;
                    radioButton3.text = questionsList[0].response3;
                    radioButton4.text = questionsList[0].response4;
                }
            }
        })

        fun getCorrectAnswerForQuestion(question: Question): String {
            return question.correctResponse
        }

        nextButton.setOnClickListener {
            Log.d("questionCount", questionCount.toString())
            if (questionCount < 5) {
                radioButton1.text = questionsList[questionCount].response1
                radioButton2.text = questionsList[questionCount].response2
                radioButton3.text = questionsList[questionCount].response3
                radioButton4.text = questionsList[questionCount].response4
                question.text = questionsList[questionCount].question
                questionCount++;
                count++
                radio_group.clearCheck()
            }

            if (questionCount == 5) {
                nextButton.text = "FINISH QUIZ";
                questionCount++;
            } else if (questionCount == 6) {
                println(score);
                scoreView.text = "Scorul tau este:  " + score;
                if(!flag) {
                    val callUpdateScore =
                        RetrofitInstance.api.updateScore(UpdateScore(score, userId))
                    callUpdateScore.enqueue(object : Callback<String> {
                        override fun onFailure(call: Call<String>, t: Throwable) {
                            Log.e("Fail!!", "Fail####" + t.message)
                        }

                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            TODO("Not yet implemented")
                        }
                    })
                }
                flag = true;
            }
        }

        radio_group.setOnCheckedChangeListener { radioGroup, i ->
            run {
                val radio: RadioButton = root.findViewById(binding.radioGroup.checkedRadioButtonId)
                Toast.makeText(
                    this.context, "On click : ${radio.text}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        // Get radio group selected item using on checked change listener
        radio_group.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                if (checkedId != -1 && questionsList.size > 0)
                {
                    val radio: RadioButton = root.findViewById(checkedId)
//                    Toast.makeText(this.context,"Raspuns selectat: "+
//                            " ${radio.text}",
//                        Toast.LENGTH_SHORT).show()
                    if(radio.text == questionsList[count].correctResponse){
                        println("Raspuns corect:" + radio.text);
                        score ++;
                    }
                }
            })
        // Get radio group selected status and text using button click event

//        button.setOnClickListener{
//            // Get the checked radio button id from radio group
//            var id: Int = radio_group.checkedRadioButtonId
//            if (id!=-1){ // If any radio button checked from radio group
//                // Get the instance of radio button using id
//                val radio:RadioButton = root.findViewById(id)
//                Toast.makeText(this.context,"On button click :" +
//                        " ${radio.text}",
//                    Toast.LENGTH_SHORT).show()
//            }else{
//                // If no radio button checked in this radio group
//                Toast.makeText(this.context,"On button click :" +
//                        " nothing selected",
//                    Toast.LENGTH_SHORT).show()
//            }
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}