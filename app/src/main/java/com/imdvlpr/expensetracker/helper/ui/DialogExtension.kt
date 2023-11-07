package com.imdvlpr.expensetracker.helper.ui

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.imdvlpr.expensetracker.R
import com.imdvlpr.expensetracker.databinding.DialogDatepickerBinding
import com.imdvlpr.expensetracker.databinding.DialogResponseBinding

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

    if (isSuccess) binding.responseTitle.text = getString(R.string.response_success_title) else binding.responseTitle.text = getString(R.string.response_error_title)
    binding.responseDesc.text = responseDesc
    binding.alertIcon.setImageResource(responseIcon)
    binding.responseBtn.text = responseBtnText
    binding.responseBtn.setOnClickListener {
        dialog.dismiss()
        listener?.onClick()
    }
    dialog.show()
}

interface DatePickerDialog {
    fun onDateSelected()
    fun onDismissClicked()
}

interface ResponseDialogListener {
    fun onClick()
}