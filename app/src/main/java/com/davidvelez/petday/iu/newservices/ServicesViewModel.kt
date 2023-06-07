package com.davidvelez.petday.iu.newservices

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidvelez.petday.Model.Service
import com.davidvelez.petday.data.ResourceRemote
import com.davidvelez.petday.data.ServicesRepository
import kotlinx.coroutines.launch

class ServicesViewModel : ViewModel() {

    private val servicesRepository = ServicesRepository()

    private val _createServiceSuccess: MutableLiveData<String?> = MutableLiveData()
    val createServiceSuccess: LiveData<String?> = _createServiceSuccess

    private val _errorMsg: MutableLiveData<String?> = MutableLiveData()
    val errorMsg: LiveData<String?> = _errorMsg


    fun validateData(
        isCatSelected: Boolean,
        isDogSelected: Boolean,
        isPaseoSelected: Boolean,
        isCuidarSelected: Boolean,
        isBanharSelected: Boolean,
        description: String,
        cost: String,
        direcion:String

    ) {

        if(description.isNotEmpty() && cost.isNotEmpty()){
            viewModelScope.launch {
                val service =Service(
                    isCatSelected = isCatSelected,
                    isDogSelected = isDogSelected,
                    isPaseoSelected = isPaseoSelected,
                    isCuidarSelected = isCuidarSelected,
                    isBanharSelected = isBanharSelected,
                    description = description,
                    cost = cost.toInt(),
                    direcion =direcion,
                    publicado = true

                )

                val result = servicesRepository.saveService(service)
                result.let { resourceRemote ->
                    when(resourceRemote){
                        is ResourceRemote.Success -> {
                            _errorMsg.postValue("Solictud creada")
                            _createServiceSuccess.postValue(resourceRemote.data)

                        }
                        is ResourceRemote.Error -> {
                            val msg = resourceRemote.message
                            _errorMsg.postValue(msg)

                        }
                        else -> {

                        }
                    }

                }

            }



        }else {
            _errorMsg.value = "Rellenar los campos vacíos"

        }

    }

}