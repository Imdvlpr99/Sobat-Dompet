package com.imdvlpr.sobatdompet.helper.utils

object Constants {

    interface PREF {
        companion object {
            const val PREF_NAME = "ExpenseTracker"
            const val IS_NOT_FIRST_INSTALL = "is_not_first_install"
            const val IS_SIGNED_IN = "is_signed_in"
            const val FULL_NAME = "full_name"
            const val USER_NAME = "user_name"
            const val USER_ID = "user_id"
            const val EMAIL = "email"
            const val PHONE = "phone"
            const val USER_IMAGE = "user_image"
            const val DATE_OF_BIRTH = "date_of_birth"
            const val BIOMETRIC_ENABLED = "biometric_enabled"
            const val PIN_ENABLED = "pin_enabled"
        }
    }

    interface COLLECTION {
        companion object {
            const val USERS = "users"
            const val ACCOUNT = "account"
        }
    }

    interface PARAM {
        companion object {
            const val USER_ID = "user_id"
            const val DEVICE_ID = "device_id"
            const val PHONE = "phone"
            const val EMAIL = "email"
            const val PASSWORD = "password"
            const val DATE_OF_BIRTH = "date_of_birth"
            const val FULL_NAME = "full_name"
            const val USER_NAME = "user_name"
            const val USER_IMAGE = "user_image"
            const val SEND_OTP = "request-otp"
            const val VERIFY_OTP = "verify-otp"
            const val IS_RESEND = "is_resend"
        }
    }

    interface ENDPOINT {
        companion object {
            const val OTP = "otp"
        }
    }
}