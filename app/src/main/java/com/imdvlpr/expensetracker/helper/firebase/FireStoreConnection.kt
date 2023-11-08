package com.imdvlpr.expensetracker.helper.firebase

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.imdvlpr.expensetracker.R
import com.imdvlpr.expensetracker.helper.utils.Constants
import com.imdvlpr.expensetracker.helper.utils.SharedPreference
import com.imdvlpr.expensetracker.helper.utils.encrypt
import com.imdvlpr.expensetracker.model.Login
import com.imdvlpr.expensetracker.model.Register
import com.imdvlpr.expensetracker.model.StatusResponse

class FireStoreConnection(val context: Context) {

    private val database = FirebaseFirestore.getInstance()
    private lateinit var sharedPreference: SharedPreference

    fun checkUsers(register: Register, callback: (StatusResponse) -> Unit) {
        database.collection(Constants.COLLECTION.USERS)
            .whereEqualTo(Constants.PARAM.PHONE, register.phone)
            .get()
            .addOnCompleteListener { phone ->
                if (phone.isSuccessful && phone.result != null && phone.result.documents.size > 0) {
                    callback(StatusResponse(false, message = context.getString(R.string.response_register_phone_registered)))
                } else {
                    if (register.email.isNotEmpty()) {
                        database.collection(Constants.COLLECTION.USERS)
                            .whereEqualTo(Constants.PARAM.EMAIL, register.email)
                            .get()
                            .addOnCompleteListener {email ->
                                if (email.isSuccessful && email.result != null && email.result.documents.size > 0) {
                                    callback(StatusResponse(false, context.getString(R.string.response_register_email_registered)))
                                } else {
                                    database.collection(Constants.COLLECTION.USERS)
                                        .whereEqualTo(Constants.PARAM.USER_NAME, register.userName)
                                        .get()
                                        .addOnCompleteListener { userName ->
                                            if (userName.isSuccessful && userName.result != null && userName.result.documents.size > 0) {
                                                callback(StatusResponse(false, context.getString(R.string.response_register_username_registered)))
                                            } else {
                                                callback(StatusResponse(true, context.getString(R.string.response_register_check_success)))
                                            }
                                        }
                                }
                            }
                    } else {
                        callback(StatusResponse(true, message = context.getString(R.string.response_register_phone_available)))
                    }
                }
            }
            .addOnFailureListener {
                callback(StatusResponse(isSuccess = false, message = it.message.toString()))
            }
    }

    fun registerUser(register: Register, callback: (StatusResponse) -> Unit) {
        database.collection(Constants.COLLECTION.USERS)
            .get()
            .addOnSuccessListener {
                val data = hashMapOf(
                    Constants.PARAM.USER_ID to (it.documents.size + 1),
                    Constants.PARAM.PHONE to register.phone,
                    Constants.PARAM.EMAIL to register.email,
                    Constants.PARAM.PASSWORD to context.encrypt(register.password),
                    Constants.PARAM.FULL_NAME to register.fullName,
                    Constants.PARAM.USER_NAME to register.userName,
                    Constants.PARAM.DATE_OF_BIRTH to register.dateOfBirth,
                    Constants.PARAM.USER_IMAGE to register.userImage
                )

                database.collection(Constants.COLLECTION.USERS)
                    .add(data)
                    .addOnSuccessListener {
                        callback(StatusResponse(true, context.getString(R.string.response_register_success)))
                    }
                    .addOnFailureListener { e ->
                        callback(StatusResponse(false, context.getString(R.string.response_register_failed)))
                        Log.e("register-exception", e.message.toString())
                    }
            }
    }

    fun login(login: Login, callback: (StatusResponse) -> Unit) {
        sharedPreference = SharedPreference()
        sharedPreference.sharedPreference(context)

        database.collection(Constants.COLLECTION.USERS)
            .whereEqualTo(Constants.PARAM.PHONE, login.phone)
            .whereEqualTo(Constants.PARAM.PASSWORD, context.encrypt(login.password))
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null && task.result.documents.size > 0) {
                    val documentSnapshot = task.result.documents[0]
                    val deviceId = documentSnapshot.getString(Constants.PARAM.DEVICE_ID)

                    if (deviceId == login.installationID || deviceId?.isEmpty() == true) {
                        sharedPreference.saveToPref(Constants.PREF.IS_NOT_FIRST_INSTALL, true)
                        sharedPreference.saveToPref(Constants.PREF.IS_SIGNED_IN, true)
                        sharedPreference.saveToPref(Constants.PREF.USER_ID, documentSnapshot.getLong(Constants.PREF.USER_ID)?.toInt())
                        sharedPreference.saveToPref(Constants.PREF.FULL_NAME, documentSnapshot.getString(Constants.PREF.FULL_NAME).toString())
                        sharedPreference.saveToPref(Constants.PREF.USER_NAME, documentSnapshot.getString(Constants.PREF.USER_NAME).toString())
                        sharedPreference.saveToPref(Constants.PREF.EMAIL, documentSnapshot.getString(Constants.PREF.EMAIL).toString())
                        sharedPreference.saveToPref(Constants.PREF.PHONE, documentSnapshot.getString(Constants.PARAM.PHONE).toString())
                        sharedPreference.saveToPref(Constants.PREF.USER_IMAGE, documentSnapshot.getString(Constants.PARAM.USER_IMAGE).toString())
                        sharedPreference.saveToPref(Constants.PREF.DATE_OF_BIRTH, documentSnapshot.getString(Constants.PARAM.DATE_OF_BIRTH).toString())
                        callback(StatusResponse(true, context.getString(R.string.response_login_success)))
                    } else {
                        callback(StatusResponse(false, context.getString(R.string.response_login_already_login)))
                    }
                }
            }
            .addOnFailureListener {
                callback(StatusResponse(false, context.getString(R.string.response_login_not_registered)))
                Log.e("login-exception", it.message.toString())
            }
    }
}