package com.imdvlpr.sobatdompet.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Forgot(
    var userName: String = "",
    var phone: String = "",
    var email: String = ""
): Parcelable
