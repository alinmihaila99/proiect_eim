package com.example.myquizzkotlin

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myquizzkotlin.model.UserCredentials
import com.example.myquizzkotlin.repository.Repository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton


class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)
        val button: Button = findViewById(R.id.button);
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)


        val switchLoginRegister: Switch = findViewById(R.id.switch1);
        val loginRegisterButton: Button = findViewById(R.id.button);
        val registerDataEmail: TextView = findViewById(R.id.editTextTextEmailAddress2);
        val registerDataPassword: TextView = findViewById(R.id.editTextTextPassword);

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        var mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        val account = GoogleSignIn.getLastSignedInAccount(this)

        val sign_in_button: SignInButton = findViewById(R.id.sign_in_button);
        sign_in_button.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, 123)
        }



        button.setOnClickListener {
            if (switchLoginRegister.isChecked) {
                viewModel.login(UserCredentials(registerDataEmail.text.toString(), registerDataPassword.text.toString()));
            } else {
                viewModel.register(UserCredentials(registerDataEmail.text.toString(), registerDataPassword.text.toString()));
            }
        }

        viewModel.myResponse.observe(this, Observer {
            response ->
            run {
                println(response)
                if(response.id > 0)
                    startActivity(Intent(this, MainActivity::class.java))
                val settings = applicationContext.getSharedPreferences("dailyGoal", 0)
                val editor = settings.edit()
                editor.putInt("dailyGoal", response.dailyGoal)
                editor.putInt("logedinUserId", response.id)
                editor.apply()
            }
        })
        switchLoginRegister.setOnCheckedChangeListener{
            buttonView, isChecked ->
                run {
                    if(isChecked){
                        switchLoginRegister.setText(R.string.login);
                        loginRegisterButton.setText(R.string.login)
                    } else{
                        switchLoginRegister.setText(R.string.register);
                        loginRegisterButton.setText(R.string.register)
                    }
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 123) {
            startActivity(Intent(this, MainActivity::class.java))
            val settings = applicationContext.getSharedPreferences("dailyGoal", 0)
            val editor = settings.edit()
            editor.putInt("dailyGoal", 0)
            editor.apply()


        }
    }


}