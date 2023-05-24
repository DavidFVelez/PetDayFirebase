package com.davidvelez.petday.iu.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidvelez.petday.Model.Service
import com.davidvelez.petday.data.ResourceRemote
import com.davidvelez.petday.data.ServicesRepository
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.perf.injection.modules.FirebasePerformanceModule_ProvidesConfigResolverFactory
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class InicioCuidadorViewModel : ViewModel() {

    private val servicesRepository = ServicesRepository()

    private var serviceList: ArrayList<Service> = ArrayList()

    private val _servicesList: MutableLiveData<ArrayList<Service>> = MutableLiveData()
    val servicesList: LiveData<ArrayList<Service>> = _servicesList

    private val _errorMsg: MutableLiveData<String?> = MutableLiveData()
    val errorMsg: LiveData<String?> = _errorMsg

    fun loadServices() {
        serviceList.clear()
        viewModelScope.launch {
            val result = servicesRepository.loadServices()
            result.let{
                resourceRemote ->
                when(resourceRemote){
                    is ResourceRemote.Success->{
                        result.data?.documents?.forEach{document ->
                            val service = document.toObject<Service>()
                            service?.let { serviceList.add(it) }
                        }
                        _servicesList.postValue(serviceList)

                    }
                    is ResourceRemote.Error -> {
                        val msg = result.message
                        _errorMsg.postValue(msg)

                    }
                    else -> {

                    }
                }
            }
        }

    }

}