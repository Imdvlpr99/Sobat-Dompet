package com.imdvlpr.expensetracker.activity.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import com.imdvlpr.expensetracker.R
import com.imdvlpr.expensetracker.databinding.ActivityRegisterBinding
import com.imdvlpr.expensetracker.helper.base.BaseActivity
import com.imdvlpr.expensetracker.helper.ui.CustomInputView
import com.imdvlpr.expensetracker.helper.ui.CustomToolbar
import com.imdvlpr.expensetracker.helper.utils.getStatusBarHeight
import com.imdvlpr.expensetracker.model.Register

class RegisterView : BaseActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, RegisterView::class.java)
        }
    }

    private lateinit var binding: ActivityRegisterBinding
    private var register = Register()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
            setListener(object : CustomInputView.InputViewListener {
                override fun afterTextChanged(s: Editable?) {
                    when (s.toString().length < 12 || s.toString().length > 13) {
                        true -> setError(true, getString(R.string.phone_error))
                        false -> {
                            setError(false)
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
            setIsPassword(true)
            setIndicator(right = true)
            setListener(object : CustomInputView.InputViewListener {
                override fun afterTextChanged(s: Editable?) {
                    when (s.toString().length < 5 || s.toString().length > 10) {
                        true -> setError(true, getString(R.string.password_error))
                        false -> {
                            setError(false)
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
            setIsPassword(true)
            setIndicator(right = true)
            setListener(object : CustomInputView.InputViewListener {
                override fun afterTextChanged(s: Editable?) {
                    when (s.toString().length < 5 || s.toString().length > 10) {
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
            register.phone = binding.phoneEt.getText()
            register.password = binding.passwordEt.getText()
            startActivity(UserDataView.newIntent(this, register))
        }
    }

    private fun validateInput() {
        binding.registerBtn.isEnabled = binding.phoneEt.getText().isNotEmpty() &&
                binding.passwordEt.getText().isNotEmpty() &&
                binding.confirmPasswordEt.getText().isNotEmpty()
    }
}