package com.imdvlpr.expensetracker.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var userId: String = "",
    var fullName: String = "",
    var userName: String = "",
    var userImage: String = ""
): Parcelable
