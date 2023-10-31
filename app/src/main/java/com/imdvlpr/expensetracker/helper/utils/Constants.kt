package com.imdvlpr.expensetracker.helper.utils

object Constants {

    interface PREF {
        companion object {
            const val PREF_NAME = "ExpenseTracker"
            const val IS_NOT_FIRST_INSTALL = "is_not_first_install"
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
            const val PHONE = "phone"
            const val EMAIL = "email"
            const val PASSWORD = "password"
            const val DATE_OF_BIRTH = "date_of_birth"
            const val FULL_NAME = "full_name"
            const val USER_NAME = "user_name"
            const val USER_IMAGE = "user_image"
        }
    }
}