package com.imdvlpr.expensetracker.activity.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import com.imdvlpr.expensetracker.R
import com.imdvlpr.expensetracker.databinding.ActivityLoginBinding
import com.imdvlpr.expensetracker.helper.base.BaseActivity
import com.imdvlpr.expensetracker.helper.ui.CustomInputView
import com.imdvlpr.expensetracker.helper.ui.CustomToolbar
import com.imdvlpr.expensetracker.helper.utils.getStatusBarHeight
import com.imdvlpr.expensetracker.model.Login

class LoginView : BaseActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LoginView::class.java)
        }

        fun intentRegister(context: Context): Intent {
            val intent = Intent(context, LoginView::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            return intent
        }
    }

    private lateinit var binding: ActivityLoginBinding
    private var login = Login()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.customToolbar.apply {
            setPadding(0, getStatusBarHeight(), 0, 0)
            setTitle(getString(R.string.login_text))
            setListener(object : CustomToolbar.ToolbarListener {
                override fun onBackButtonClick() {
                    onBackPressedDispatcher.onBackPressed()
                    finish()
                }
            })
        }

        binding.inputPhone.apply {
            setTitle(getString(R.string.phone_title))
            setHint(getString(R.string.phone_hint))
            setInputType(InputType.TYPE_CLASS_NUMBER)
            setListener(object : CustomInputView.InputViewListener {
                override fun afterTextChanged(s: Editable?) {
                    when (s.toString().length < 12 || s.toString().length > 13) {
                        true -> setError(true, getString(R.string.phone_error))
                        false -> {
                            setError(false)
                            login.phone = s.toString()
                            validateInput()
                        }
                    }
                }
            })
        }

        binding.inputPassword.apply {
            setTitle(getString(R.string.password_title))
            setHint(getString(R.string.password_hint))
            setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            setIsPassword(true)
            setIndicator(right = true)
            setListener(object : CustomInputView.InputViewListener {
                override fun afterTextChanged(s: Editable?) {
                    when (s.toString().length < 5 || s.toString().length > 10) {
                        true -> setError(true, getString(R.string.password_error))
                        false -> {
                            setError(false)
                            login.password = s.toString()
                            validateInput()
                        }
                    }
                }
            })
        }

        binding.loginBtn.setOnClickListener {
            startActivity(OtpView.intentLogin(this, login, OtpView.Companion.TYPE.LOGIN))
        }
    }

    private fun validateInput() {
        binding.loginBtn.isEnabled = binding.inputPhone.getText().isNotEmpty() &&
                binding.inputPassword.getText().isNotEmpty()
    }
}