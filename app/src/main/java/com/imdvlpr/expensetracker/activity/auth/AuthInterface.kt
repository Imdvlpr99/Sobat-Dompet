package com.imdvlpr.expensetracker.activity.auth

import com.google.firebase.firestore.auth.User
import com.imdvlpr.expensetracker.model.OTP
import com.imdvlpr.expensetracker.model.StatusResponse
import com.imdvlpr.weatherappp.helper.base.BaseView

interface AuthInterface: BaseView {

    fun onProgress()

    fun onFinishProgress()

    fun onFailed(message: String)

    fun onSuccessSendOtp(data: OTP) {}

    fun onSuccessLogin(user: User) {}

    fun onSuccessRegister() {}

    fun onSuccessCheckUsers(response: StatusResponse) {}
}