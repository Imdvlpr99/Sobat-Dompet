package com.imdvlpr.sobatdompet.activity.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.imdvlpr.sobatdompet.R
import com.imdvlpr.sobatdompet.databinding.ActivityOtpBinding
import com.imdvlpr.sobatdompet.helper.base.BaseActivity
import com.imdvlpr.sobatdompet.helper.ui.CustomNumberPad
import com.imdvlpr.sobatdompet.helper.ui.CustomOTPInput
import com.imdvlpr.sobatdompet.helper.ui.CustomToolbar
import com.imdvlpr.sobatdompet.helper.ui.ResponseDialogListener
import com.imdvlpr.sobatdompet.helper.ui.responseDialog
import com.imdvlpr.sobatdompet.helper.utils.Constants
import com.imdvlpr.sobatdompet.helper.utils.SharedPreference
import com.imdvlpr.sobatdompet.helper.utils.getParcelable
import com.imdvlpr.sobatdompet.helper.utils.getSerializable
import com.imdvlpr.sobatdompet.helper.utils.getStatusBarHeight
import com.imdvlpr.sobatdompet.helper.utils.setSpannable
import com.imdvlpr.sobatdompet.model.Forgot
import com.imdvlpr.sobatdompet.model.Login
import com.imdvlpr.sobatdompet.model.OTP
import com.imdvlpr.sobatdompet.model.Register

class OtpView : BaseActivity(), AuthInterface {

    companion object {

        private const val REGISTER_DATA = "register_data"
        private const val LOGIN_DATA = "login_data"
        private const val FORGOT_DATA = "forgot_data"
        private const val OTP_TYPE = "type"
        enum class TYPE { LOGIN, REGISTER, FORGOT }

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

        fun intentForgot(context: Context, data: Forgot, type: TYPE): Intent {
            val intent = Intent(context, OtpView::class.java)
            intent.putExtra(FORGOT_DATA, data)
            intent.putExtra(OTP_TYPE, type)
            return intent
        }
    }

    private lateinit var binding: ActivityOtpBinding
    private lateinit var presenter: AuthPresenter
    private var sharedPreference: SharedPreference = SharedPreference()
    private var register = Register()
    private var login = Login()
    private var forgot = Forgot()
    private var type: TYPE = TYPE.LOGIN
    private var expiredTime: Long = 0
    private var messageId: Int = 0
    private var phoneNumber: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreference.sharedPreference(this)
        onAttach()
        initBundle()
        initView()
        setOtpDesc()
    }

    private fun initBundle() {
        register =  intent.getParcelable(REGISTER_DATA, Register::class.java) ?: Register()
        login = intent.getParcelable(LOGIN_DATA, Login::class.java) ?: Login()
        forgot = intent.getParcelable(FORGOT_DATA, Forgot::class.java) ?: Forgot()
        type = when (intent.getSerializable(OTP_TYPE, TYPE::class.java)) {
            TYPE.LOGIN -> TYPE.LOGIN
            TYPE.REGISTER -> TYPE.REGISTER
            TYPE.FORGOT -> TYPE.FORGOT
            else -> TYPE.LOGIN
        }
        when (type) {
            TYPE.LOGIN -> {
                expiredTime = login.expiredTime.toLong()
                messageId = login.messageId
                phoneNumber = login.phone
            }
            TYPE.REGISTER -> {
                expiredTime = register.expiredIn.toLong()
                messageId = register.messageId
                phoneNumber = register.phone
            }
            TYPE.FORGOT -> {
                expiredTime = forgot.expiredTime.toLong()
                messageId = forgot.messageId
                phoneNumber = forgot.phone
            }
        }
    }

    private fun initView() {
        val otpDesc = String.format(getString(R.string.otp_desc), phoneNumber)
        binding.otpTitle.text = setSpannable(otpDesc, phoneNumber)
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

        binding.confirmBtn.setOnClickListener {
            presenter.sendOtp(OTP(action = Constants.PARAM.VERIFY_OTP, messageId = messageId, otpNumber = binding.inputOTP.getValue().toInt()))
        }
    }

    private fun setOtpDesc() {
        binding.inputOTP.apply {
            setCountDown(getString(R.string.otp_countdown), true, expiredTime)
            setListener(object : CustomOTPInput.InputOTPListener {
                override fun resendOtp() {
                    presenter.sendOtp(OTP(action = Constants.PARAM.SEND_OTP, phoneNumber = phoneNumber, isResend = true))
                }
            })
        }
    }

    override fun onSuccessSendOtp(data: OTP) {
        if (!data.isResend) {
            when (type) {
                TYPE.REGISTER -> presenter.registerUser(register)
                TYPE.FORGOT -> {
                    setResult(RESULT_OK)
                    finish()
                }
                TYPE.LOGIN -> {
                    sharedPreference.saveToPref(Constants.PREF.IS_SIGNED_IN, true)
                    setResult(RESULT_OK)
                    finish()
                }
            }
        } else {
            responseDialog(true, getString(R.string.response_otp_resend_success), R.drawable.ic_success, getString(R.string.response_button_ok), object : ResponseDialogListener {
                override fun onClick() {
                    messageId = data.messageId
                    expiredTime = data.expiredIn.toLong()
                    binding.inputOTP.resetValue()
                    validateInput(false)
                    setOtpDesc()
                }
            })
        }
    }

    override fun onSuccessRegister() {
        responseDialog(true, getString(R.string.response_otp_register_success_desc), R.drawable.ic_success, getString(R.string.response_button_next), object : ResponseDialogListener {
            override fun onClick() {
                startActivity(LoginView.intentClear(this@OtpView))
                finish()
            }
        })
    }

    private fun validateInput(isEnable: Boolean) {
        binding.confirmBtn.isEnabled = isEnable
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.inputOTP.cancelTimer()
    }

    override fun onProgress() {
        if (!isFinishing) showProgress()
    }

    override fun onFinishProgress() {
        if (!isFinishing) hideProgress()
    }

    override fun onFailed(message: String) {
        if (!isFinishing) {
            responseDialog(false, message, R.drawable.ic_error, getString(R.string.response_button_ok), object : ResponseDialogListener {
                override fun onClick() {
                    binding.inputOTP.resetValue()
                    validateInput(false)
                }
            })
        }
    }

    override fun onAttach() {
        presenter = AuthPresenter(this)
        presenter.onAttach(this)
    }

    override fun onDetach() {
        presenter.onDetach()
    }
}