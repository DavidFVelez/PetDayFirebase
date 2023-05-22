package com.davidvelez.petday.data

import android.util.Log
import com.davidvelez.petday.Model.User
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class UserRepository {
    private var auth: FirebaseAuth = Firebase.auth
    suspend fun signUpUser(email: String, password: String): ResourceRemote<String?> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            ResourceRemote.Success(data = result.user?.uid)
        } catch (e: FirebaseAuthException) {
            e.localizedMessage?.let { Log.e("FirebaseAuthEx", it) }
            ResourceRemote.Error(message = e.localizedMessage)
        } catch (e: FirebaseNetworkException) {
            e.localizedMessage?.let { Log.e("FirebaseAuthEx", it) }
            ResourceRemote.Error(message = e.localizedMessage)
        }
    }

    suspend fun signInpUser(email: String, password: String): ResourceRemote<String?> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            ResourceRemote.Success(data = result.user?.uid)
        } catch (e: FirebaseAuthException) {
            e.localizedMessage?.let { Log.e("FirebaseAuthEx", it) }
            ResourceRemote.Error(message = e.localizedMessage)
        } catch (e: FirebaseNetworkException) {
            e.localizedMessage?.let { Log.e("FirebaseAuthEx", it) }
            ResourceRemote.Error(message = e.localizedMessage)
        }
    }

    fun isSessionActive(): Boolean {
        return auth.currentUser != null
    }

    fun signOut(){
        auth.signOut()
    }

    fun createUser(user: User) {
    }
}