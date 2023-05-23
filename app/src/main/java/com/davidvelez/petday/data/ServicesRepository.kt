package com.davidvelez.petday.data

import android.util.Log
import com.davidvelez.petday.Model.Service
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ServicesRepository {
    private var db = Firebase.firestore
    private var auth: FirebaseAuth = Firebase.auth


    suspend fun saveService(service: Service): ResourceRemote<String?> {
        return try {
            val uid = auth.currentUser?.uid
            val path = uid?.let { db.collection("users").document(it).collection("Services")}
            val documentService = path?.document()
            service.id = documentService?.id
            service?.id?.let { path?.document(it)?.set(service)?.await() }
            ResourceRemote.Success(data = service.id)

        } catch (e: FirebaseFirestoreException) {
            e.localizedMessage?.let { Log.e("FirebaseFirestoreEx", it) }
            ResourceRemote.Error(message = e.localizedMessage)
        } catch (e: FirebaseNetworkException) {
            e.localizedMessage?.let { Log.e("FirebaseNetworkEx", it) }
            ResourceRemote.Error(message = e.localizedMessage)
        }

    }


}