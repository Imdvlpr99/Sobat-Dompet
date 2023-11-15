package com.imdvlpr.sobatdompet.activity.auth

import com.imdvlpr.sobatdompet.model.Login
import com.imdvlpr.sobatdompet.model.OTP
import com.imdvlpr.sobatdompet.model.StatusResponse
import com.imdvlpr.weatherappp.helper.base.BaseView

interface AuthInterface: BaseView {

    fun onProgress()

    fun onFinishProgress()

    fun onFailed(message: String)

    fun onSuccessSendOtp(data: OTP) {}

    fun onSuccessVerifyOtp(data: OTP) {}

    fun onSuccessLogin(login: Login) {}

    fun onSuccessRegister() {}

    fun onSuccessCheckUsers(response: StatusResponse) {}
}