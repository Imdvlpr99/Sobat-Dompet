package com.imdvlpr.expensetracker.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StatusResponse(
    var isSuccess: Boolean = true,
    var refNumber: String? = "",
    var errorCode: String = "",
    var status: String = "",
    var message: String = "",
    var api: String = "",
    var isCanceled: Boolean = false
) : Parcelable