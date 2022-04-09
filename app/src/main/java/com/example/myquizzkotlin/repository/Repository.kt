package com.example.myquizzkotlin.repository

import com.example.myquizzkotlin.api.RetrofitInstance
import com.example.myquizzkotlin.model.Profile
import com.example.myquizzkotlin.model.UserCredentials

class Repository {
    suspend fun login(userCredentials: UserCredentials): Profile{
        return RetrofitInstance.api.login(userCredentials)
    }
    suspend fun register(userCredentials: UserCredentials): Profile{
        return RetrofitInstance.api.register(userCredentials)
    }
}