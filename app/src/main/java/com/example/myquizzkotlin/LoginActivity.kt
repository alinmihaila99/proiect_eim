package com.example.myquizzkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        val switchLoginRegister: Switch = findViewById(R.id.switch1);
        val loginRegisterButton: Button = findViewById(R.id.button);
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