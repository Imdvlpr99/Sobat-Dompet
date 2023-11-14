package com.imdvlpr.sobatdompet.model

import android.os.Parcelable
import com.imdvlpr.sobatdompet.helper.network.getBoolean
import com.imdvlpr.sobatdompet.helper.network.getInt
import com.imdvlpr.sobatdompet.helper.network.getString
import eu.amirs.JSON
import kotlinx.parcelize.Parcelize

@Parcelize
data class OTP(
    var email: String = "",
    var messageId: Int = 0,
    var expiredIn: Int = 0,
    var otpNumber: Int = 0,
    var phoneNumber: String = "",
    var isSuccess: Boolean = false,
    var message: String = "",
    var isResend: Boolean = false,
): Parcelable {
    constructor(data: JSON): this() {
        this.messageId = data.getInt("message_id")
        this.message = data.getString("message")
        this.expiredIn = data.getInt("time_in_second")
        this.isSuccess = data.getBoolean("success")
        this.isResend = data.getBoolean("is_resend")
    }
}
