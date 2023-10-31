package com.imdvlpr.expensetracker.activity.auth

import com.imdvlpr.weatherappp.helper.base.BaseView
import eu.amirs.JSON

interface AuthInterface: BaseView {

    fun onProgress()

    fun onFinishProgress()

    fun onSuccessSendOtp(data: JSON) {}

    fun onSuccessLogin() {}
}