package com.davidvelez.petday.iu.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.davidvelez.petday.data.UserRepository

class SplashViewModel : ViewModel() {

    private val userRepository = UserRepository()
    private val _isSessionActive: MutableLiveData<Boolean> = MutableLiveData()
    val isSessionActive: LiveData<Boolean> = _isSessionActive

    fun validateSessionActive() {
        val isSessionActive = userRepository.isSessionActive()
        _isSessionActive.postValue(isSessionActive)
    }


}