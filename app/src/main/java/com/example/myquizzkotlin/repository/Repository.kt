package com.example.myquizzkotlin.repository

import com.example.myquizzkotlin.api.RetrofitInstance
import com.example.myquizzkotlin.model.Profile
import com.example.myquizzkotlin.model.Question
import com.example.myquizzkotlin.model.UserCredentials

class Repository {
    suspend fun login(userCredentials: UserCredentials): Profile{
        if(userCredentials.email == "" || userCredentials.password == "")
            return Profile(-1, -1, -1);
        return RetrofitInstance.api.login(userCredentials)
    }
    suspend fun register(userCredentials: UserCredentials): Profile{
        if(userCredentials.email == "" || userCredentials.password == "")
            return Profile(-1, -1, -1);
        return RetrofitInstance.api.register(userCredentials)
    }

}