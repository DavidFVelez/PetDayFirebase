package com.davidvelez.petday.iu.login


import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidvelez.petday.data.ResourceRemote
import com.davidvelez.petday.data.UserRepository
import kotlinx.coroutines.launch


class CarerLoginViewModel : ViewModel() {

    private val userRepository = UserRepository()

    private val _isSuccessSignIn: MutableLiveData<Boolean> = MutableLiveData()
    val isSuccessSignIn: LiveData<Boolean> = _isSuccessSignIn

    private val _errorMsg: MutableLiveData<String?> = MutableLiveData()
    val errorMsg: LiveData<String?> = _errorMsg

    fun validarDatos(email: String, password: String) {

        if (email.isNotEmpty() and email.isNotEmpty()) {
            viewModelScope.launch {
                val resourceRemote = userRepository.signInpUser(email, password)
                when (resourceRemote) {
                    is ResourceRemote.Success -> {
                        _isSuccessSignIn.postValue(true)
                    }
                    is ResourceRemote.Error -> {
                        var msg = resourceRemote.message
                        when (resourceRemote.message) {
                            "A network error (such as timeout, interrupted connection or unreachable host) has occurred." -> msg =
                                "Revise su conexión a internet"
                            "The email address is badly formatted." -> msg =
                                "El correo electrónico esta mal escrito"
                            "The password is invalid or the user does not have a password." -> msg = "El correo electrónico o la contraseña es invalida"
                            "There is no user record corresponding to this identifier. The user may have been deleted." ->msg = "No existe usuario con este correo"
                        }
                        _errorMsg.postValue(msg)
                    }
                    else -> {

                    }
                }
            }
        } else {
            _errorMsg.value = "Rellenar los campos vacíos"
        }

    }
}