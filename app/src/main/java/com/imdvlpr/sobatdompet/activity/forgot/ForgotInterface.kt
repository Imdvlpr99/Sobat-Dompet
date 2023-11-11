package com.imdvlpr.sobatdompet.activity.forgot

import com.imdvlpr.sobatdompet.model.StatusResponse
import com.imdvlpr.weatherappp.helper.base.BaseView

interface ForgotInterface: BaseView {

    fun onProgress()

    fun onFinishProgress()

    fun onFailed(message: String)

    fun onSuccessForgot(statusResponse: StatusResponse)
}