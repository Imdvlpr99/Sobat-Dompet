package com.imdvlpr.expensetracker.helper.firebase

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.imdvlpr.expensetracker.R
import com.imdvlpr.expensetracker.helper.utils.Constants
import com.imdvlpr.expensetracker.model.Register
import com.imdvlpr.expensetracker.model.StatusResponse

class FireStoreConnection(val context: Context) {

    private val database = FirebaseFirestore.getInstance()

    fun registerUsers(register: Register, callback: (StatusResponse) -> Unit) {
        database.collection(Constants.COLLECTION.USERS)
            .whereEqualTo(Constants.PARAM.PHONE, register.phone)
            .get()
            .addOnCompleteListener { task ->
            }
    }
}