package com.imdvlpr.sobatdompet.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StatusResponse(
    var isSuccess: Boolean = false,
    var message: String = ""
) : Parcelable