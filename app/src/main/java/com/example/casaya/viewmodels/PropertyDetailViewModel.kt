package com.example.casaya.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.casaya.adapters.entities.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class PropertyDetailViewModel : ViewModel() {
    private lateinit var user: User
}