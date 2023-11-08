package com.imdvlpr.expensetracker.activity.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import com.imdvlpr.expensetracker.R
import com.imdvlpr.expensetracker.databinding.ActivityLoginBinding
import com.imdvlpr.expensetracker.helper.base.BaseActivity
import com.imdvlpr.expensetracker.helper.ui.CustomDualTab
import com.imdvlpr.expensetracker.helper.ui.CustomInputView
import com.imdvlpr.expensetracker.helper.utils.setSpannable
import com.imdvlpr.expensetracker.helper.utils.setTheme
import com.imdvlpr.expensetracker.helper.utils.setVisible
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

    enum class TYPE { USERNAME, PHONE }
    private lateinit var binding: ActivityLoginBinding
    private var login = Login()
    private var loginType: TYPE = TYPE.USERNAME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheme()
        initTab()
        initView()
    }

    private fun initTab() {
        binding.dualTab.apply {
            when (getSelected()) {
                CustomDualTab.SELECTED.LEFT -> {
                    binding.userNameLayout.setVisible(true)
                    binding.phoneLayout.setVisible(false)
                }
                CustomDualTab.SELECTED.RIGHT -> {
                    binding.phoneLayout.setVisible(true)
                    binding.userNameLayout.setVisible(false)
                }
            }
            setListener(object : CustomDualTab.DualTabListener {
                override fun onLeftClicked() {
                    loginType = TYPE.USERNAME
                    binding.userNameLayout.setVisible(true)
                    binding.phoneLayout.setVisible(false)
                }

                override fun onRightClicked() {
                    loginType = TYPE.PHONE
                    binding.phoneLayout.setVisible(true)
                    binding.userNameLayout.setVisible(false)
                }
            })
        }
    }

    private fun initView() {
        val createAccount = String.format(getString(R.string.login_do_not_have_account), getString(R.string.login_create_account))
        binding.createAccount.text = setSpannable(createAccount, getString(R.string.login_create_account))

        binding.inputUsername.apply {
            setTitle(getString(R.string.register_user_name))
            setHint(getString(R.string.register_user_name_hint))
            setHelper(true, getString(R.string.forgot_username))
            setInputType(InputType.TYPE_CLASS_TEXT)
            setInputFilter(CustomInputView.InputFilter.LOWERCASE_WITH_SPECIAL_CHAR)
            setListener(object : CustomInputView.InputViewListener {
                override fun afterTextChanged(s: Editable?) {
                    login.userName = s.toString()
                    validateInput()
                }

                override fun onHelperClicked() {

                }
            })
        }

        binding.inputPassword.apply {
            setTitle(getString(R.string.password_title))
            setHint(getString(R.string.password_hint))
            setHelper(true, getString(R.string.forgot_password))
            setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            setInputFilter(CustomInputView.InputFilter.PASSWORD)
            setIsPassword(true)
            setIndicator(true)
            setListener(object : CustomInputView.InputViewListener {
                override fun afterTextChanged(s: Editable?) {
                    login.password = s.toString()
                    validateInput()
                }

                override fun onHelperClicked() {

                }
            })
        }

        binding.inputPhone.apply {
            setTitle(getString(R.string.phone_title))
            setHint(getString(R.string.phone_hint))
            setHelper(true, getString(R.string.forgot_phone))
            setInputType(InputType.TYPE_CLASS_NUMBER)
            setInputFilter(CustomInputView.InputFilter.PHONE)
            setSuffix("+62")
            setListener(object : CustomInputView.InputViewListener {
                override fun afterTextChanged(s: Editable?) {
                    login.phone = s.toString()
                    validateInput()
                }

                override fun onHelperClicked() {

                }
            })
        }

        /*binding.loginBtn.setOnClickListener {
            startActivity(OtpView.intentLogin(this, login, OtpView.Companion.TYPE.LOGIN))
        }*/
    }

    private fun validateInput() {
        when (loginType) {
            TYPE.USERNAME -> binding.loginUsernameBtn.isEnabled = login.userName.isNotEmpty() && login.password.isNotEmpty()
            TYPE.PHONE -> binding.loginPhoneBtn.isEnabled = login.phone.isNotEmpty()
        }
    }
}