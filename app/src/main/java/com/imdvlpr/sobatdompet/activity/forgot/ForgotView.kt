package com.imdvlpr.sobatdompet.activity.forgot

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import com.imdvlpr.sobatdompet.R
import com.imdvlpr.sobatdompet.activity.auth.LoginView
import com.imdvlpr.sobatdompet.activity.auth.OtpView
import com.imdvlpr.sobatdompet.databinding.ActivityForgotBinding
import com.imdvlpr.sobatdompet.helper.base.BaseActivity
import com.imdvlpr.sobatdompet.helper.ui.CustomInputView
import com.imdvlpr.sobatdompet.helper.ui.CustomToolbar
import com.imdvlpr.sobatdompet.helper.ui.ResponseDialogListener
import com.imdvlpr.sobatdompet.helper.ui.UpdateDialogListener
import com.imdvlpr.sobatdompet.helper.ui.responseDialog
import com.imdvlpr.sobatdompet.helper.ui.updateDialog
import com.imdvlpr.sobatdompet.helper.utils.getSerializable
import com.imdvlpr.sobatdompet.helper.utils.getStatusBarHeight
import com.imdvlpr.sobatdompet.helper.utils.setVisible
import com.imdvlpr.sobatdompet.model.Forgot
import com.imdvlpr.sobatdompet.model.OTP
import org.koin.android.ext.android.inject

class ForgotView : BaseActivity(), ForgotInterface {

    enum class TYPE { USERNAME, PASSWORD, PHONE }
    companion object {
        private const val FORGOT_TYPE = "forgot_type"

        fun intentForgot(context: Context, type : TYPE): Intent {
            val intent = Intent(context, ForgotView::class.java)
            intent.putExtra(FORGOT_TYPE, type)
            return intent
        }
    }

    private lateinit var binding: ActivityForgotBinding
    private val presenter: ForgotPresenter by inject()
    private var forgotType: TYPE = TYPE.USERNAME
    private var forgot = Forgot()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onAttach()
        initBundle()
        initView()
    }

    private fun initBundle() {
        forgotType = when (intent.getSerializable(FORGOT_TYPE, TYPE::class.java)) {
            TYPE.USERNAME -> TYPE.USERNAME
            TYPE.PHONE -> TYPE.PHONE
            TYPE.PASSWORD -> TYPE.PASSWORD
            else -> TYPE.USERNAME
        }

        forgot.forgotType = forgotType
        when (forgotType) {
            TYPE.USERNAME, TYPE.PASSWORD -> {
                binding.inputPhone.setVisible(true)
                binding.inputEmail.setVisible(true)
            }
            TYPE.PHONE -> {
                binding.inputUsername.setVisible(true)
                binding.inputEmail.setVisible(true)
            }
        }
    }

    private fun initView() {
        binding.customToolbar.apply {
            setPadding(0, getStatusBarHeight(), 0, 0)
            setTitle(getString(R.string.forgot_recover_title))
            setListener(object : CustomToolbar.ToolbarListener {
                override fun onBackButtonClick() {
                    onBackPressedDispatcher.onBackPressed()
                    finish()
                }
            })
        }

        binding.inputUsername.apply {
            setTitle(getString(R.string.register_user_name))
            setHint(getString(R.string.forgot_username_hint))
            setInputType(InputType.TYPE_CLASS_TEXT)
            setInputFilter(CustomInputView.InputFilter.LOWERCASE_WITH_SPECIAL_CHAR)
            setListener(object : CustomInputView.InputViewListener {
                override fun afterTextChanged(s: Editable?) {
                    forgot.userName = s.toString()
                    validateInput()
                }
            })
        }

        binding.inputEmail.apply {
            setTitle(getString(R.string.register_email))
            setHint(getString(R.string.forgot_email_hint))
            setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
            setListener(object : CustomInputView.InputViewListener {
                override fun afterTextChanged(s: Editable?) {
                    if (binding.inputEmail.isEmailValid(s.toString())) {
                        setError(false)
                        forgot.email = s.toString()
                        validateInput()
                    } else {
                        setError(true, getString(R.string.register_email_invalid))
                    }
                }
            })
        }

        binding.inputPhone.apply {
            setTitle(getString(R.string.phone_title))
            setHint(getString(R.string.forgot_phone_hint))
            setInputType(InputType.TYPE_CLASS_NUMBER)
            setInputFilter(CustomInputView.InputFilter.PHONE)
            setSuffix("+62")
            setListener(object : CustomInputView.InputViewListener {
                override fun afterTextChanged(s: Editable?) {
                    forgot.phone = getString(R.string.indonesia_number_suffix) + s.toString()
                    validateInput()
                }
            })
        }

        binding.forgotBtn.setOnClickListener {
            presenter.forgot(forgot)
        }
    }

    private fun validateInput() {
        when (forgotType) {
            TYPE.USERNAME, TYPE.PASSWORD -> binding.forgotBtn.isEnabled = forgot.phone.isNotEmpty() && forgot.email.isNotEmpty()
            TYPE.PHONE -> binding.forgotBtn.isEnabled = forgot.email.isNotEmpty() && forgot.userName.isNotEmpty()
        }
    }

    override fun onProgress() {
        if (!isFinishing) showProgress()
    }

    override fun onFinishProgress() {
        if (!isFinishing) hideProgress()
    }

    override fun onFailed(message: String) {
        if (!isFinishing) responseDialog(false, message)
    }

    override fun onSuccessForgot(forgot: Forgot) {
        this.forgot.docId = forgot.docId
        if (!isFinishing) when (forgotType) {
            TYPE.USERNAME, TYPE.PASSWORD -> presenter.sendOtp(OTP(phoneNumber = forgot.phone))
            TYPE.PHONE -> presenter.sendOtpEmail(OTP(email = forgot.email))
        }
    }

    override fun onSuccessSendOtp(data: OTP) {
        if (!isFinishing) {
            forgot.messageId = data.messageId
            forgot.expiredTime = data.expiredIn
            activityLauncher.launch(OtpView.intentForgot(this, forgot, OtpView.Companion.TYPE.FORGOT, forgotType)) {
                when (it.resultCode) {
                    Activity.RESULT_OK -> {
                        updateDialog(forgotType, object : UpdateDialogListener {
                            override fun onClick(data: Forgot) {
                                data.docId = forgot.docId
                                presenter.updateCredential(data)
                            }
                        })
                    }
                }
            }
        }
    }

    override fun onSuccessUpdateAccount(message: String) {
        if (!isFinishing) responseDialog(true, message, R.drawable.ic_success, listener = object : ResponseDialogListener {
            override fun onClick() {
                startActivity(LoginView.intentClear(this@ForgotView))
            }
        })
    }

    override fun onAttach() {
        presenter.onAttach(this)
    }

    override fun onDetach() {
        presenter.onDetach()
    }
}