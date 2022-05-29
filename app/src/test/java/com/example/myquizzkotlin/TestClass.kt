package com.example.myquizzkotlin

import com.example.myquizzkotlin.api.ApiRequest
import com.example.myquizzkotlin.model.*
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestClass {
    companion object{

        fun createMockQuestions():MutableList<Question>{
            return mutableListOf(
                Question("Intrebare2", "raspuns1", "raspuns2",
            "raspuns3", "raspuns4", "raspuns2"),
                (Question("Intrebare2", "raspuns1", "raspuns2",
                    "raspuns3", "raspuns4", "raspuns4")),
                (Question("Intrebare3", "raspuns1", "raspuns2",
                    "raspuns3", "raspuns4", "raspuns1"))
                )
        }

        fun createMockLeaderboard():MutableList<String>{
            return mutableListOf(
                "1, mihai, 30",
                "2, iulia, 20",
                "3, cristian, 14"
            )
        }

        fun mockUserPosition(): String{
            return "2";
        }

        fun createMockAPI(): ApiRequest{
            return object :ApiRequest{
                override suspend fun login(userCredentials: UserCredentials): Profile {
                    return Profile(1, 30, 1);
                }

                override suspend fun register(userCredentials: UserCredentials): Profile {
                    return Profile(1, 30, 1);
                }

                override fun getQuestions(): Call<MutableList<Question>> {
                    return object:Call<MutableList<Question>>{
                        override fun clone(): Call<MutableList<Question>> {
                            return this;
                        }

                        override fun execute(): Response<MutableList<Question>> {
                            return Response.success(createMockQuestions());
                        }

                        override fun enqueue(callback: Callback<MutableList<Question>>) {
                            TODO("Not yet implemented")
                        }

                        override fun isExecuted(): Boolean {
                            TODO("Not yet implemented")
                        }

                        override fun cancel() {
                            TODO("Not yet implemented")
                        }

                        override fun isCanceled(): Boolean {
                            TODO("Not yet implemented")
                        }

                        override fun request(): Request {
                            TODO("Not yet implemented")
                        }

                        override fun timeout(): Timeout {
                            TODO("Not yet implemented")
                        }

                    }
                }

                override fun getLeaderboard(): Call<MutableList<String>> {
                    return object:Call<MutableList<String>>{
                        override fun clone(): Call<MutableList<String>> {
                            return this;
                        }

                        override fun execute(): Response<MutableList<String>> {
                            return Response.success(createMockLeaderboard())
                        }

                        override fun enqueue(callback: Callback<MutableList<String>>) {
                            TODO("Not yet implemented")
                        }

                        override fun isExecuted(): Boolean {
                            TODO("Not yet implemented")
                        }

                        override fun cancel() {
                            TODO("Not yet implemented")
                        }

                        override fun isCanceled(): Boolean {
                            TODO("Not yet implemented")
                        }

                        override fun request(): Request {
                            TODO("Not yet implemented")
                        }

                        override fun timeout(): Timeout {
                            TODO("Not yet implemented")
                        }

                    }
                }

                override fun getPosition(userId: String): Call<String> {
                    return object: Call<String>{
                        override fun clone(): Call<String> {
                            return this;
                        }

                        override fun execute(): Response<String> {
                            return Response.success(mockUserPosition());
                        }

                        override fun enqueue(callback: Callback<String>) {
                            TODO("Not yet implemented")
                        }

                        override fun isExecuted(): Boolean {
                            TODO("Not yet implemented")
                        }

                        override fun cancel() {
                            TODO("Not yet implemented")
                        }

                        override fun isCanceled(): Boolean {
                            TODO("Not yet implemented")
                        }

                        override fun request(): Request {
                            TODO("Not yet implemented")
                        }

                        override fun timeout(): Timeout {
                            TODO("Not yet implemented")
                        }

                    }
                }

                override fun updateScore(updateUserScore: UpdateScore): Call<String> {
                    TODO("Not yet implemented")
                }

                override fun updateDaily(userId: UserId): Call<UserId> {
                    TODO("Not yet implemented")
                }

            }

        }
    }
}