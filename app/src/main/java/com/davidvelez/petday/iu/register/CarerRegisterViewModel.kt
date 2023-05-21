package com.davidvelez.petday.iu.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidvelez.petday.data.ResourceRemote
import com.davidvelez.petday.data.UserRepository
import kotlinx.coroutines.launch

class CarerRegisterViewModel : ViewModel() {

    private val userRepository = UserRepository()

    private val _isSuccessSignUp: MutableLiveData<Boolean> = MutableLiveData()
    val isSuccessSignUp: LiveData<Boolean> = _isSuccessSignUp

    private val _errorMsg: MutableLiveData<String?> = MutableLiveData()
    val errorMsg: LiveData<String?> = _errorMsg

    fun validarContrasena(password: String, repPassword: String, email: String) {

        if (email.isNotEmpty() and password.isNotEmpty() and repPassword.isNotEmpty()) {
            if (password.length >= 6) {
                if (password == repPassword) {
                    viewModelScope.launch {
                        val result = userRepository.signUpUser(email, password)
                        result.let { resourceRemote ->
                            when (resourceRemote) {
                                is ResourceRemote.Success -> {
                                    _isSuccessSignUp.postValue(true)

                                }
                                is ResourceRemote.Error -> {
                                    var msg = resourceRemote.message
                                    when (resourceRemote.message) {
                                        "A network error (such as timeout, interrupted connection or unreachable host) has occurred."
                                        -> msg = "Revise su conexión a internet"
                                        "The email address is already in use by another account." -> msg = "Ya existe una cuenta con ese correo electrónico"
                                        "The email address is badly formatted." -> msg = "El correo electrónico esta mal escrito"

                                    }
                                    _errorMsg.postValue(msg)
                                }
                                else -> {

                                }

                            }
                        }
                    }
                } else {
                    _errorMsg.value = "Las contraseñas no son iguales"
                }
            } else {
                _errorMsg.value = "Error. La contraseña debe ser de almenos 6 caracteres"

            }
        } else {
            _errorMsg.value = "Rellenar los campos vacíos"
        }
    }
}