package com.example.myquizzkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myquizzkotlin.model.UserCredentials
import com.example.myquizzkotlin.repository.Repository

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

        button.setOnClickListener {
            if (!switchLoginRegister.isChecked) {
                viewModel.login(UserCredentials(registerDataEmail.text.toString(), registerDataPassword.text.toString()));
            } else {
                viewModel.register(UserCredentials(registerDataEmail.text.toString(), registerDataPassword.text.toString()));
            }
        }

        viewModel.myResponse.observe(this, Observer {
            response ->
            run {
                startActivity(Intent(this, MainActivity::class.java))
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
}