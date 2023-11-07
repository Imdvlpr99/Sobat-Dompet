package com.imdvlpr.expensetracker.activity.splashscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import com.imdvlpr.expensetracker.MainActivity
import com.imdvlpr.expensetracker.activity.onBoarding.OnBoardingView
import com.imdvlpr.expensetracker.databinding.ActivitySplashScreenBinding
import com.imdvlpr.expensetracker.helper.base.BaseActivity
import com.imdvlpr.expensetracker.helper.utils.Constants
import com.imdvlpr.expensetracker.helper.utils.SharedPreference
import com.imdvlpr.expensetracker.helper.utils.decrypt
import com.imdvlpr.expensetracker.helper.utils.encrypt


@SuppressLint("CustomSplashScreen")
class SplashScreenView : BaseActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var sessionManager: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SharedPreference()
        sessionManager.sharedPreference(this)
        Log.d("test-decrypt", decrypt(encrypt("test encrypt")))

        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            run {
                Log.d("firebaseToken", token)
            }
        }

        FirebaseInstallations.getInstance().id.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("firebaseInstallationID", task.result ?: "")
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            when (sessionManager.getBooleanFromPref(Constants.PREF.IS_NOT_FIRST_INSTALL)) {
                true -> {
                    startActivity(MainActivity.newIntent(this))
                    finish()
                }
                false -> {
                    startActivity(OnBoardingView.newIntent(this))
                    finish()
                }
            }
        }, 2000)
    }
}