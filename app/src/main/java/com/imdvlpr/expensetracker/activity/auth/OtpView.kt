package com.imdvlpr.expensetracker.activity.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import com.imdvlpr.expensetracker.R
import com.imdvlpr.expensetracker.databinding.ActivityOtpBinding
import com.imdvlpr.expensetracker.helper.base.BaseActivity
import com.imdvlpr.expensetracker.helper.ui.CustomNumberPad
import com.imdvlpr.expensetracker.helper.ui.CustomOTPInput
import com.imdvlpr.expensetracker.helper.ui.CustomToolbar
import com.imdvlpr.expensetracker.helper.utils.SpannableListener
import com.imdvlpr.expensetracker.helper.utils.getParcelable
import com.imdvlpr.expensetracker.helper.utils.getSerializable
import com.imdvlpr.expensetracker.helper.utils.getStatusBarHeight
import com.imdvlpr.expensetracker.helper.utils.setSpannable
import com.imdvlpr.expensetracker.model.Login
import com.imdvlpr.expensetracker.model.Register

class OtpView : BaseActivity() {

    companion object {

        private const val REGISTER_DATA = "register_data"
        private const val LOGIN_DATA = "login_data"
        private const val OTP_TYPE = "type"
        enum class TYPE { LOGIN, REGISTER }

        fun intentRegister(context: Context, data: Register, type: TYPE): Intent {
            val intent = Intent(context, OtpView::class.java)
            intent.putExtra(REGISTER_DATA, data)
            intent.putExtra(OTP_TYPE, type)
            return intent
        }

        fun intentLogin(context: Context, data: Login, type: TYPE): Intent {
            val intent = Intent(context, OtpView::class.java)
            intent.putExtra(LOGIN_DATA, data)
            intent.putExtra(OTP_TYPE, type)
            return intent
        }
    }

    private lateinit var binding: ActivityOtpBinding
    private var register = Register()
    private var login = Login()
    private var type: TYPE = TYPE.LOGIN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBundle()
        initView()
    }

    private fun initBundle() {
        register =  intent.getParcelable(REGISTER_DATA, Register::class.java) ?: Register()
        login = intent.getParcelable(LOGIN_DATA, Login::class.java) ?: Login()
        type = when (intent.getSerializable(OTP_TYPE, TYPE::class.java)) {
            TYPE.LOGIN -> TYPE.LOGIN
            TYPE.REGISTER -> TYPE.REGISTER
            else -> TYPE.LOGIN
        }
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

        val otpDesc = String.format(getString(R.string.otp_desc), when(type) {
            TYPE.LOGIN -> login.phone
            TYPE.REGISTER -> register.phone
        })

        binding.otpTitle.text = setSpannable(otpDesc, when (type) {
            TYPE.LOGIN -> login.phone
            TYPE.REGISTER -> register.phone
        }, object : SpannableListener {
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