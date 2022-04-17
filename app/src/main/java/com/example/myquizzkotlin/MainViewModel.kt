package com.example.myquizzkotlin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myquizzkotlin.model.Profile
import com.example.myquizzkotlin.model.UserCredentials
import com.example.myquizzkotlin.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository): ViewModel() {

    val myResponse: MutableLiveData<Profile> = MutableLiveData()
    fun login(userCredentials: UserCredentials){
        viewModelScope.launch {
            val response = repository.login(userCredentials)
            myResponse.value = response
        }
    }
    fun register(userCredentials: UserCredentials){
        viewModelScope.launch {
            val response = repository.register(userCredentials)
            myResponse.value = response
        }
    }
}