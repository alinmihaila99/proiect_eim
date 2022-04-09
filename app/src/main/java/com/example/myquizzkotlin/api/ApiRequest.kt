package com.example.myquizzkotlin.api

import com.example.myquizzkotlin.model.Profile
import com.example.myquizzkotlin.model.UserCredentials
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiRequest {

    @POST("/login")
    suspend fun login(@Body userCredentials: UserCredentials):  Profile

    @POST("/register")
    suspend fun register(@Body userCredentials: UserCredentials):  Profile
}