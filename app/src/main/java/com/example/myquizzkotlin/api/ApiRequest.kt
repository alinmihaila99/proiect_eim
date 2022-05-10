package com.example.myquizzkotlin.api

import com.example.myquizzkotlin.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiRequest {

    @POST("/login")
    suspend fun login(@Body userCredentials: UserCredentials):  Profile

    @POST("/register")
    suspend fun register(@Body userCredentials: UserCredentials):  Profile

    @GET("/quizz")
    fun getQuestions(): Call<MutableList<Question>>

    @GET("/leaderboard")
    fun getLeaderboard(): Call<MutableList<String>>

    @GET("/position")
    fun getPosition(@Query(value = "logedUserId") userId: String ): Call<String>

    @PUT("/updateLeaderboard")
    fun updateScore(@Body updateUserScore: UpdateScore): Call<String>

    @PUT("/updateDaily")
    fun updateDaily(@Body userId: UserId): Call<UserId>
}