package com.imdvlpr.sobatdompet.model

import android.os.Parcelable
import com.imdvlpr.sobatdompet.activity.forgot.ForgotView
import kotlinx.parcelize.Parcelize

@Parcelize
data class Forgot(
    var userName: String = "",
    var phone: String = "",
    var email: String = "",
    var docId: String = "",
    var password: String = "",
    var expiredTime: Int = 0,
    var messageId: Int = 0,
    var forgotType: ForgotView.TYPE = ForgotView.TYPE.USERNAME
): Parcelable
