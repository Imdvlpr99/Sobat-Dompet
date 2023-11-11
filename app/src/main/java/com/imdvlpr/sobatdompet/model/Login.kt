package com.imdvlpr.sobatdompet.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Login(
    var userName: String = "",
    var phone: String = "",
    var password: String = "",
    var email: String = "",
    var installationID: String = "",
    var expiredTime: Int = 0,
    var messageId: Int = 0
): Parcelable
