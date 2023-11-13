package com.imdvlpr.sobatdompet.helper.ui

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.InputType
import android.view.Window
import com.google.android.material.button.MaterialButton
import com.imdvlpr.sobatdompet.R
import com.imdvlpr.sobatdompet.activity.forgot.ForgotView
import com.imdvlpr.sobatdompet.databinding.DialogDatepickerBinding
import com.imdvlpr.sobatdompet.databinding.DialogResponseBinding
import com.imdvlpr.sobatdompet.databinding.DialogUpdateCredentialBinding
import com.imdvlpr.sobatdompet.model.Forgot

private lateinit var dialog: Dialog

fun Activity.datePickerDialog(listener: DatePickerDialog? = null) {
    dialog = Dialog(this, R.style.DialogSlideAnimFullWidth)
    val binding = DialogDatepickerBinding.bind(layoutInflater.inflate(R.layout.dialog_datepicker, null))
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setContentView(binding.root)

    binding.dismissBtn.setOnClickListener {
        dialog.dismiss()
    }

    dialog.show()
}

fun Activity.responseDialog(isSuccess:Boolean, responseDesc: String, responseIcon: Int = R.drawable.ic_error,
                            responseBtnText: String = getString(R.string.response_button_ok), listener: ResponseDialogListener? = null) {
    dialog = Dialog(this, R.style.DialogSlideAnimFullWidth)
    val binding = DialogResponseBinding.bind(layoutInflater.inflate(R.layout.dialog_response, null))
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setContentView(binding.root)
    dialog.setCancelable(false)

    if (isSuccess) binding.responseTitle.text = getString(R.string.response_success_title) else binding.responseTitle.text = getString(R.string.response_error_title)
    binding.responseDesc.text = responseDesc
    binding.alertIcon.setImageResource(responseIcon)
    binding.responseBtn.text = responseBtnText
    binding.responseBtn.setOnClickListener {
        listener?.onClick()
        dialog.dismiss()
    }
    dialog.show()
}

fun Activity.updateDialog(type: ForgotView.TYPE, listener: DialogClickListener? = null) {
    dialog = Dialog(this, R.style.DialogSlideAnimFullWidth)
    val binding = DialogUpdateCredentialBinding.bind(layoutInflater.inflate(R.layout.dialog_update_credential, null))
    val forgot = Forgot()
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setContentView(binding.root)
    dialog.setCancelable(false)

    when (type) {
        ForgotView.TYPE.USERNAME -> {
            binding.updateUsername.setVisible(true)
            binding.forgotTitle.text = getString(R.string.forgot_update_username)
        }
        ForgotView.TYPE.PASSWORD -> {
            binding.updatePassword.setVisible(true)
            binding.forgotTitle.text = getString(R.string.forgot_update_password)
        }
        ForgotView.TYPE.PHONE -> {
            binding.updatePhone.setVisible(true)
            binding.forgotTitle.text = getString(R.string.forgot_update_phone)
        }
    }

    binding.updateUsername.apply {
        setTitle(getString(R.string.register_user_name))
        setHint(getString(R.string.register_user_name_hint))
        setInputType(InputType.TYPE_CLASS_TEXT)
        setInputFilter(CustomInputView.InputFilter.LOWERCASE_WITH_SPECIAL_CHAR)
        setListener(object : CustomInputView.InputViewListener {
            override fun afterTextChanged(s: Editable?) {
                forgot.userName = s.toString()
                validateUpdateInput(binding.updateBtn, type, forgot)
            }
        })
    }

    binding.updatePassword.apply {
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
                        forgot.password = s.toString()
                        validateUpdateInput(binding.updateBtn, type, forgot)
                    }
                }
            }
        })
    }

    binding.updatePhone.apply {
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
                        forgot.phone = getString(R.string.indonesia_number_suffix) + s.toString()
                        validateUpdateInput(binding.updateBtn, type, forgot)
                    }
                }
            }
        })
    }

    binding.updateBtn.setOnClickListener {
        listener?.onClick()
        dialog.dismiss()
    }

    binding.cancelBtn.setOnClickListener {
        dialog.dismiss()
    }

    dialog.show()
}

private fun validateUpdateInput(button: MaterialButton, type: ForgotView.TYPE, forgot: Forgot) {
    when (type) {
        ForgotView.TYPE.USERNAME -> button.isEnabled = forgot.userName.isNotEmpty()
        ForgotView.TYPE.PASSWORD -> button.isEnabled = forgot.password.isNotEmpty()
        ForgotView.TYPE.PHONE -> button.isEnabled = forgot.phone.isNotEmpty()
    }
}

interface DialogClickListener {
    fun onClick()
}

interface DatePickerDialog {
    fun onDateSelected()
    fun onDismissClicked()
}

interface ResponseDialogListener {
    fun onClick()
}