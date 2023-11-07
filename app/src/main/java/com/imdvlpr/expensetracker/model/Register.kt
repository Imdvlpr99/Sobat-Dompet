package com.imdvlpr.expensetracker.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Register(
    var fullName: String = "",
    var userName: String = "",
    var email: String = "",
    var dateOfBirth: String = "",
    var phone: String = "",
    var userImage: String = "",
    var password: String = "",
    var messageId: Int = 0,
    var expiredIn: Int = 0,
): Parcelable
