package com.imdvlpr.sobatdompet.activity.forgot

import com.imdvlpr.sobatdompet.model.Forgot
import com.imdvlpr.sobatdompet.model.OTP
import com.imdvlpr.sobatdompet.helper.base.BaseView

interface ForgotInterface: BaseView {

    fun onProgress()

    fun onFinishProgress()

    fun onFailed(message: String)

    fun onSuccessForgot(forgot: Forgot)

    fun onSuccessUpdateAccount(message: String)

    fun onSuccessSendOtp(data: OTP) {}
}