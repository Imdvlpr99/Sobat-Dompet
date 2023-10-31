package com.imdvlpr.expensetracker.activity.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.imdvlpr.expensetracker.R
import com.imdvlpr.expensetracker.databinding.ActivityOtpBinding
import com.imdvlpr.expensetracker.helper.base.BaseActivity
import com.imdvlpr.expensetracker.helper.ui.CustomNumberPad
import com.imdvlpr.expensetracker.helper.ui.CustomOTPInput
import com.imdvlpr.expensetracker.helper.ui.CustomToolbar
import com.imdvlpr.expensetracker.helper.utils.SpannableListener
import com.imdvlpr.expensetracker.helper.utils.getParcelable
import com.imdvlpr.expensetracker.helper.utils.getStatusBarHeight
import com.imdvlpr.expensetracker.helper.utils.setSpannable
import com.imdvlpr.expensetracker.model.Register

class OtpView : BaseActivity() {

    companion object {

        private const val REGISTER_DATA = "register_data"

        fun intentRegister(context: Context, data: Register): Intent {
            val intent = Intent(context, OtpView::class.java)
            intent.putExtra(REGISTER_DATA, data)
            return intent
        }
    }

    private lateinit var binding: ActivityOtpBinding
    private var register = Register()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBundle()
        initView()
    }

    private fun initBundle() {
        register =  intent.getParcelable(REGISTER_DATA, Register::class.java) ?: Register()
    }

    private fun initView() {
        binding.customToolbar.apply {
            setPadding(0, getStatusBarHeight(), 0, 0)
            setTitle(getString(R.string.otp_title))
            setListener(object : CustomToolbar.ToolbarListener {
                override fun onBackButtonClick() {
                    onBackPressedDispatcher.onBackPressed()
                    finish()
                }
            })
        }

        binding.numPadLayout.apply {
            setListener(object : CustomNumberPad.NumPadListener {
                override fun onNumberClicked(s: String) {
                    binding.inputOTP.apply {
                        setOTP(s)
                        validateInput(validateInput())
                    }
                }

                override fun onDeleteClicked() {
                    binding.inputOTP.apply {
                        deleteValue()
                        validateInput(validateInput())
                    }
                }
            })
        }

        val otpDesc = String.format(getString(R.string.otp_desc), register.phone)
        binding.otpTitle.text = setSpannable(otpDesc, register.phone, object : SpannableListener {
            override fun onClick() {
                Log.d("span-clicked", true.toString())
            }
        })
        binding.otpTitle.movementMethod = LinkMovementMethod.getInstance()

        binding.inputOTP.apply {
            setCountDown(getString(R.string.otp_countdown), true, 60)
            setListener(object : CustomOTPInput.InputOTPListener {
                override fun resendOtp() {
                    Log.d("resend-clicked", true.toString())
                }
            })
        }
    }

    private fun validateInput(isEnable: Boolean) {
        binding.confirmBtn.isEnabled = isEnable
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.inputOTP.cancelTimer()
    }
}