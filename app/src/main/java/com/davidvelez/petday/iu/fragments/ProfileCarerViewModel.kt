package com.davidvelez.petday.iu.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.davidvelez.petday.data.UserRepository

class ProfileCarerViewModel: ViewModel() {

    private var userRepository =UserRepository()
    val _isSignout : MutableLiveData<Boolean> = MutableLiveData()
    val isSingOut : LiveData<Boolean> = _isSignout

    private val _text = MutableLiveData<String>().apply {
        value = "This is profile Fragment"
    }
    val text: LiveData<String> = _text

    fun signOut(){
        val result = userRepository.signOut()
        _isSignout.value = result

    }
}