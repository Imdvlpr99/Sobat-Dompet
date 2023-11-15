package com.imdvlpr.sobatdompet.helper.firebase

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.imdvlpr.sobatdompet.R
import com.imdvlpr.sobatdompet.activity.forgot.ForgotView
import com.imdvlpr.sobatdompet.helper.utils.Constants
import com.imdvlpr.sobatdompet.helper.utils.SharedPreference
import com.imdvlpr.sobatdompet.helper.utils.decrypt
import com.imdvlpr.sobatdompet.helper.utils.encrypt
import com.imdvlpr.sobatdompet.model.Forgot
import com.imdvlpr.sobatdompet.model.Login
import com.imdvlpr.sobatdompet.model.Register
import com.imdvlpr.sobatdompet.model.StatusResponse

class FireStoreConnection(val context: Context) {

    private val database = FirebaseFirestore.getInstance()
    private var sharedPreference: SharedPreference = SharedPreference()

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
                    Constants.PARAM.EMAIL to context.encrypt(register.email),
                    Constants.PARAM.PASSWORD to context.encrypt(register.password),
                    Constants.PARAM.FULL_NAME to register.fullName,
                    Constants.PARAM.USER_NAME to register.userName,
                    Constants.PARAM.DATE_OF_BIRTH to register.dateOfBirth,
                    Constants.PARAM.USER_IMAGE to register.userImage,
                    Constants.PARAM.DEVICE_ID to ""
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

    fun loginUsername(login: Login, callback: (Login, StatusResponse) -> Unit) {
        sharedPreference.sharedPreference(context)
        database.collection(Constants.COLLECTION.USERS)
            .whereEqualTo(Constants.PARAM.USER_NAME, login.userName)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null && task.result.documents.size > 0) {
                    val documentSnapshot = task.result.documents[0]
                    val password = context.decrypt(documentSnapshot.getString(Constants.PARAM.PASSWORD).toString())
                    val deviceId = if (documentSnapshot.getString(Constants.PARAM.DEVICE_ID).toString().isNotEmpty()) {
                        context.decrypt(documentSnapshot.getString(Constants.PARAM.DEVICE_ID).toString())
                    } else {
                        documentSnapshot.getString(Constants.PARAM.DEVICE_ID).toString()
                    }

                    if (login.password == password) {
                        if (deviceId == login.installationID || deviceId.isEmpty()) {
                            val email = context.decrypt(documentSnapshot.getString(Constants.PREF.EMAIL).toString())
                            val phoneNumber = documentSnapshot.getString(Constants.PARAM.PHONE).toString()

                            sharedPreference.saveToPref(Constants.PREF.IS_NOT_FIRST_INSTALL, true)
                            sharedPreference.saveToPref(Constants.PREF.EMAIL, email)
                            sharedPreference.saveToPref(Constants.PREF.PHONE, phoneNumber)
                            sharedPreference.saveToPref(Constants.PREF.DOC_ID, documentSnapshot.id)
                            sharedPreference.saveToPref(Constants.PREF.USER_ID, documentSnapshot.getLong(Constants.PREF.USER_ID)?.toInt())
                            sharedPreference.saveToPref(Constants.PREF.FULL_NAME, documentSnapshot.getString(Constants.PREF.FULL_NAME).toString())
                            sharedPreference.saveToPref(Constants.PREF.USER_NAME, documentSnapshot.getString(Constants.PREF.USER_NAME).toString())
                            sharedPreference.saveToPref(Constants.PREF.USER_IMAGE, documentSnapshot.getString(Constants.PARAM.USER_IMAGE).toString())
                            sharedPreference.saveToPref(Constants.PREF.DATE_OF_BIRTH, documentSnapshot.getString(Constants.PARAM.DATE_OF_BIRTH).toString())


                            database.collection(Constants.COLLECTION.USERS).document(
                                sharedPreference.getStringFromPref(Constants.PREF.DOC_ID))
                                .update(Constants.PARAM.DEVICE_ID, context.encrypt(login.installationID))
                                .addOnCompleteListener {
                                    callback(Login(phone = phoneNumber, email = email), StatusResponse(true, context.getString(R.string.response_login_success)))
                                }
                        } else {
                            callback(Login(), StatusResponse(false, context.getString(R.string.response_login_already_login)))
                        }
                    } else {
                        callback(Login(), StatusResponse(false, context.getString(R.string.response_login_incorrect_password)))
                    }
                } else {
                    callback(Login(), StatusResponse(false, context.getString(R.string.response_login_not_registered)))
                }
            }
            .addOnFailureListener {
                callback(Login(), StatusResponse(false, context.getString(R.string.response_server_error)))
                Log.e("login-exception", it.message.toString())
            }
    }

    fun forgot(forgot: Forgot, callback: (Forgot, StatusResponse) -> Unit) {
        when (forgot.forgotType) {
            ForgotView.TYPE.USERNAME, ForgotView.TYPE.PASSWORD -> {
                database.collection(Constants.COLLECTION.USERS)
                    .whereEqualTo(Constants.PARAM.PHONE, forgot.phone)
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful && task.result != null && task.result.documents.size > 0) {
                            val documentSnapshot = task.result.documents[0]
                            val email = context.decrypt(documentSnapshot.getString(Constants.PREF.EMAIL).toString())
                            if (forgot.email == email) {
                                callback(Forgot(docId = documentSnapshot.id, phone = forgot.phone), StatusResponse(true, context.getString(R.string.response_forgot_success)))
                            } else {
                                callback(Forgot(), StatusResponse(false, context.getString(R.string.response_forgot_email_invalid)))
                            }
                        } else {
                            callback(Forgot(), StatusResponse(false, context.getString(R.string.response_login_not_registered)))
                        }
                    }
            }
            ForgotView.TYPE.PHONE -> {
                database.collection(Constants.COLLECTION.USERS)
                    .whereEqualTo(Constants.PARAM.USER_NAME, forgot.userName)
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful && task.result != null && task.result.documents.size > 0) {
                            val documentSnapshot = task.result.documents[0]
                            val email = context.decrypt(documentSnapshot.getString(Constants.PREF.EMAIL).toString())
                            if (forgot.email == email) {
                                callback(Forgot(docId = documentSnapshot.id, email = email), StatusResponse(true, context.getString(R.string.response_forgot_success)))
                            } else {
                                callback(Forgot(), StatusResponse(false, context.getString(R.string.response_forgot_email_invalid)))
                            }
                        } else {
                            callback(Forgot(), StatusResponse(false, context.getString(R.string.response_login_not_registered)))
                        }
                    }
            }
        }
    }

    fun updateAccountCredential(forgot: Forgot, callback: (StatusResponse) -> Unit) {
        when (forgot.forgotType) {
            ForgotView.TYPE.USERNAME -> {
                database.collection(Constants.COLLECTION.USERS)
                    .document(forgot.docId)
                    .update(Constants.PARAM.USER_NAME, forgot.userName)
                    .addOnCompleteListener {
                        callback(StatusResponse(true, context.getString(R.string.forgot_update_username_success)))
                    }
                    .addOnFailureListener {
                        Log.e("update-credential-exception", it.message.toString())
                    }
            }
            ForgotView.TYPE.PASSWORD -> {
                database.collection(Constants.COLLECTION.USERS)
                    .document(forgot.docId)
                    .update(Constants.PARAM.PASSWORD, context.encrypt(forgot.password))
                    .addOnCompleteListener {
                        callback(StatusResponse(true, context.getString(R.string.forgot_update_password_success)))
                    }
                    .addOnFailureListener {
                        Log.e("update-credential-exception", it.message.toString())
                    }
            }
            ForgotView.TYPE.PHONE -> {
                database.collection(Constants.COLLECTION.USERS)
                    .document(forgot.docId)
                    .update(Constants.PARAM.PHONE, forgot.phone)
                    .addOnCompleteListener {
                        callback(StatusResponse(true, context.getString(R.string.forgot_update_phone_success)))
                    }
                    .addOnFailureListener {
                        Log.e("update-credential-exception", it.message.toString())
                    }
            }
        }
    }

    fun logout(callback: (StatusResponse) -> Unit) {
        sharedPreference.sharedPreference(context)
        database.collection(Constants.COLLECTION.USERS).document(
            sharedPreference.getStringFromPref(Constants.PREF.DOC_ID))
            .update(Constants.PARAM.DEVICE_ID, "")
            .addOnCompleteListener {
                sharedPreference.clearPref()
                callback(StatusResponse(true, context.getString(R.string.response_logout_success)))
            }
            .addOnFailureListener {
                callback(StatusResponse(false, context.getString(R.string.response_server_error)))
            }
    }
}