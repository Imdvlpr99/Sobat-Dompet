package com.imdvlpr.sobatdompet.activity.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import com.imdvlpr.sobatdompet.R
import com.imdvlpr.sobatdompet.databinding.ActivityRegisterBinding
import com.imdvlpr.sobatdompet.helper.base.BaseActivity
import com.imdvlpr.sobatdompet.helper.ui.CustomInputView
import com.imdvlpr.sobatdompet.helper.ui.CustomToolbar
import com.imdvlpr.sobatdompet.helper.ui.ResponseDialogListener
import com.imdvlpr.sobatdompet.helper.ui.responseDialog
import com.imdvlpr.sobatdompet.helper.utils.getStatusBarHeight
import com.imdvlpr.sobatdompet.model.Register
import com.imdvlpr.sobatdompet.model.StatusResponse
import org.koin.android.ext.android.inject

class RegisterView : BaseActivity(), AuthInterface {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, RegisterView::class.java)
        }
    }

    private lateinit var binding: ActivityRegisterBinding
    private val presenter: AuthPresenter by inject()
    private var register = Register()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onAttach()
        initView()
    }

    private fun initView() {
        binding.customToolbar.apply {
            setTitle(getString(R.string.register_title))
            setPadding(0, getStatusBarHeight(), 0, 0)
            setListener(object : CustomToolbar.ToolbarListener {
                override fun onBackButtonClick() {
                    onBackPressedDispatcher.onBackPressed()
                    finish()
                }
            })
        }

        binding.phoneEt.apply {
            setTitle(getString(R.string.phone_title))
            setHint(getString(R.string.phone_hint))
            setInputType(InputType.TYPE_CLASS_NUMBER)
            setInputFilter(CustomInputView.InputFilter.PHONE)
            setSuffix("+62")
            setListener(object : CustomInputView.InputViewListener {
                override fun afterTextChanged(s: Editable?) {
                    when (s.toString().length < 11 || s.toString().length > 12) {
                        true -> setError(true, getString(R.string.phone_error))
                        false -> {
                            setError(false)
                            register.phone = getString(R.string.indonesia_number_suffix) + s.toString()
                            validateInput()
                        }
                    }
                }
            })
        }

        binding.passwordEt.apply {
            setTitle(getString(R.string.password_title))
            setHint(getString(R.string.password_hint))
            setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            setInputFilter(CustomInputView.InputFilter.PASSWORD)
            setIsPassword(true)
            setIndicator(true)
            setListener(object : CustomInputView.InputViewListener {
                override fun afterTextChanged(s: Editable?) {
                    when (s.toString().length < 5 || s.toString().length > 10) {
                        true -> setError(true, getString(R.string.password_error))
                        false -> {
                            setError(false)
                            register.password = s.toString()
                            validateInput()
                        }
                    }
                }
            })
        }

        binding.confirmPasswordEt.apply {
            setTitle(getString(R.string.password_confirm_title))
            setHint(getString(R.string.password_confirm_hint))
            setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            setInputFilter(CustomInputView.InputFilter.PASSWORD)
            setIsPassword(true)
            setIndicator(true)
            setListener(object : CustomInputView.InputViewListener {
                override fun afterTextChanged(s: Editable?) {
                    when (s.toString().length < 5 || s.toString().length > 15) {
                        true -> setError(true, getString(R.string.password_error))
                        false -> {
                            if (binding.passwordEt.getText() != binding.confirmPasswordEt.getText()) {
                                setError(true, getString(R.string.password_confirm_error))
                            } else {
                                validateInput()
                                setError(false)
                            }
                        }
                    }
                }
            })
        }

        binding.registerBtn.setOnClickListener {
            presenter.checkUsers(register)
        }
    }

    private fun validateInput() {
        binding.registerBtn.isEnabled = binding.phoneEt.getText().isNotEmpty() &&
                binding.passwordEt.getText().isNotEmpty() &&
                binding.confirmPasswordEt.getText().isNotEmpty()
    }

    override fun onSuccessCheckUsers(response: StatusResponse) {
        if (!isFinishing) startActivity(UserDataView.newIntent(this, register))
    }

    override fun onProgress() {
        if (!isFinishing) showProgress()
    }

    override fun onFinishProgress() {
        if (!isFinishing) hideProgress()
    }

    override fun onFailed(message: String) {
        if (!isFinishing) responseDialog(false, message, responseBtnText = getString(R.string.response_button_ok), listener = object : ResponseDialogListener {
            override fun onClick() {
                binding.phoneEt.setError(true, message)
            }
        })
    }

    override fun onAttach() {
        presenter.onAttach(this)
    }

    override fun onDetach() {
        presenter.onDetach()
    }

    override fun onDestroy() {
        onDetach()
        super.onDestroy()
    }
}