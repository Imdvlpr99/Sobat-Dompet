package com.imdvlpr.expensetracker.helper.ui

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.imdvlpr.expensetracker.R
import com.imdvlpr.expensetracker.databinding.DialogDatepickerBinding

fun Activity.datePickerDialog(listener: DatePickerDialog? = null) {
    val dialog = Dialog(this, R.style.DialogSlideAnimFullWidth)
    val binding = DialogDatepickerBinding.bind(layoutInflater.inflate(R.layout.dialog_datepicker, null))
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setContentView(binding.root)

    binding.dismissBtn.setOnClickListener {
        dialog.dismiss()
    }

    dialog.show()
}

interface DatePickerDialog {
    fun onDateSelected()
    fun onDismissClicked()
}