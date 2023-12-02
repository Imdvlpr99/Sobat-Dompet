package com.imdvlpr.sobatdompet.activity.main

import com.imdvlpr.sobatdompet.helper.base.BaseView

interface MainInterface: BaseView {

    fun onProgress()

    fun onFinishProgress()

    fun onFailed(message: String)

    fun onSuccessLogout() {}
}