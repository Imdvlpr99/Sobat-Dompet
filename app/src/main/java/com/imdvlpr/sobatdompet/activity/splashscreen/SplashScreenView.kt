package com.imdvlpr.sobatdompet.activity.splashscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.imdvlpr.sobatdompet.activity.main.MainActivity
import com.imdvlpr.sobatdompet.activity.auth.LoginView
import com.imdvlpr.sobatdompet.activity.onBoarding.OnBoardingView
import com.imdvlpr.sobatdompet.databinding.ActivitySplashScreenBinding
import com.imdvlpr.sobatdompet.helper.base.BaseActivity
import com.imdvlpr.sobatdompet.helper.utils.Constants
import com.imdvlpr.sobatdompet.helper.utils.SharedPreference
import com.imdvlpr.sobatdompet.helper.utils.decrypt
import com.imdvlpr.sobatdompet.helper.utils.encrypt


@SuppressLint("CustomSplashScreen")
class SplashScreenView : BaseActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var sessionManager: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startKoin()
        sessionManager = SharedPreference()
        sessionManager.sharedPreference(this)
        Log.d("test-decrypt", decrypt(encrypt("test encrypt")))

        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            run {
                Log.d("firebaseToken", token)
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            when (sessionManager.getBooleanFromPref(Constants.PREF.IS_NOT_FIRST_INSTALL)) {
                true -> {
                    if (sessionManager.getBooleanFromPref(Constants.PREF.IS_SIGNED_IN)) {
                        sessionManager.saveToPref(Constants.PREF.IS_NOT_FIRST_INSTALL, true)
                        startActivity(MainActivity.newIntent(this))
                        finish()
                    } else {
                        sessionManager.saveToPref(Constants.PREF.IS_NOT_FIRST_INSTALL, true)
                        startActivity(LoginView.newIntent(this))
                        finish()
                    }
                }
                false -> {
                    sessionManager.saveToPref(Constants.PREF.IS_NOT_FIRST_INSTALL, true)
                    startActivity(OnBoardingView.newIntent(this))
                    finish()
                }
            }
        }, 2000)
    }
}