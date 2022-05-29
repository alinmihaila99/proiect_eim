package com.example.myquizzkotlin

import android.util.Log
import com.example.myquizzkotlin.api.RetrofitInstance
import com.example.myquizzkotlin.model.Profile
import com.example.myquizzkotlin.model.Question
import com.example.myquizzkotlin.model.UserCredentials
import com.example.myquizzkotlin.repository.Repository
import io.mockk.every
import io.mockk.mockkObject
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Call

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class RequestTest {
    @Test
    fun loginTest() {
        mockkObject(RetrofitInstance);
        every { RetrofitInstance getProperty "api" } returns TestClass.createMockAPI();
        val user: Profile = runBlocking { RetrofitInstance.api.login(UserCredentials
            ("alinmihaila", "parola")) }
        assertEquals(user.id, 1);
        assertEquals(user.quizesSolved, 30);
        assertEquals(user.dailyGoal, 1);
    }

    @Test
    fun registerTest(){
        mockkObject(RetrofitInstance);
        every { RetrofitInstance getProperty "api" } returns TestClass.createMockAPI();
        val user: Profile = runBlocking { RetrofitInstance.api.register(UserCredentials
            ("alinmihaila", "parola")) }
        assertEquals(user.id, 1);
        assertEquals(user.quizesSolved, 30);
        assertEquals(user.dailyGoal, 1);
    }

    @Test
    fun testGetQuestions(){
        mockkObject(RetrofitInstance);
        every { RetrofitInstance getProperty "api" } returns TestClass.createMockAPI();
        val questions: Call<MutableList<Question>> = RetrofitInstance.api.getQuestions();
        val questionsList = questions.execute();

        assertEquals(questionsList.body()?.size ?: 0, 3)
    }

    //TEST REPOSITORY CLASS

    @Test
    fun loginTestFromRepository() {
        mockkObject(RetrofitInstance);
        every { RetrofitInstance getProperty "api" } returns TestClass.createMockAPI();
        val repostitory: Repository = Repository();
        val user: Profile = runBlocking { repostitory.login(UserCredentials
            ("alinmihaila", "parola")) }
        assertEquals(user.id, 1);
        assertEquals(user.quizesSolved, 30);
        assertEquals(user.dailyGoal, 1);

    }

    @Test
    fun loginTestFromRepositoryWithEmptyPasswordOrUsername() {
        mockkObject(RetrofitInstance);
        every { RetrofitInstance getProperty "api" } returns TestClass.createMockAPI();
        val repostitory: Repository = Repository();
        val user: Profile = runBlocking { repostitory.login(UserCredentials
            ("", "")) }
        assertEquals(user.id, -1);
        assertEquals(user.quizesSolved, -1);
        assertEquals(user.dailyGoal, -1);

    }

    @Test
    fun registerTestFromRepository() {
        mockkObject(RetrofitInstance);
        every { RetrofitInstance getProperty "api" } returns TestClass.createMockAPI();
        val repostitory: Repository = Repository();
        val user: Profile = runBlocking {
            repostitory.register(
                UserCredentials
                    ("alinmihaila", "parola")
            )
        }
        assertEquals(user.id, 1);
        assertEquals(user.quizesSolved, 30);
        assertEquals(user.dailyGoal, 1);
    }

    @Test
    fun registerTestFromRepositoryWithEmptyPasswordOrUsername() {
        mockkObject(RetrofitInstance);
        every { RetrofitInstance getProperty "api" } returns TestClass.createMockAPI();
        val repostitory: Repository = Repository();
        val user: Profile = runBlocking {
            repostitory.register(
                UserCredentials
                    ("", "")
            )
        }
        assertEquals(user.id, -1);
        assertEquals(user.quizesSolved, -1);
        assertEquals(user.dailyGoal, -1);
    }

    @Test
    fun testGetCorrectAnswer(){

        mockkObject(RetrofitInstance);
        every { RetrofitInstance getProperty "api" } returns TestClass.createMockAPI();
        val questions: Call<MutableList<Question>> = RetrofitInstance.api.getQuestions();
        val questionsList = questions.execute();
        val q:Question? = questionsList.body()?.get(0);
        if (q != null) {
            assertEquals(q.correctResponse, "raspuns2")
        }
    }

    @Test
    fun testGetLeaderboard(){
        mockkObject(RetrofitInstance);
        every { RetrofitInstance getProperty "api" } returns TestClass.createMockAPI();
        val leaderBoard: Call<MutableList<String>> = RetrofitInstance.api.getLeaderboard();
        val questionsList = leaderBoard.execute();

        assertEquals(questionsList.body()?.size ?: 0, 3)
    }

    @Test
    fun testGetLeaderboardOrder(){
        mockkObject(RetrofitInstance);
        every { RetrofitInstance getProperty "api" } returns TestClass.createMockAPI();
        val leaderBoard: Call<MutableList<String>> = RetrofitInstance.api.getLeaderboard();
        val leaderboardList = leaderBoard.execute();

        assertEquals(leaderboardList.body()?.get(0) ?: 0, "1, mihai, 30")
    }

    @Test
    fun testGetUserPosition(){
        mockkObject(RetrofitInstance);
        every {RetrofitInstance getProperty "api"} returns TestClass.createMockAPI();
        val position: Call<String> = RetrofitInstance.api.getPosition("9");
        val userPosition = position.execute();

        assertEquals(userPosition.body(), "2")
    }
}