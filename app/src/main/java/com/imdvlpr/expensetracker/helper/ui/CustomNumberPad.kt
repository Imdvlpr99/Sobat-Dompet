package com.imdvlpr.expensetracker.helper.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.imdvlpr.expensetracker.R
import com.imdvlpr.expensetracker.databinding.LayoutNumberInputBinding
import com.imdvlpr.expensetracker.helper.utils.setVisible

class CustomNumberPad: ConstraintLayout {

    private lateinit var binding: LayoutNumberInputBinding
    private var listener: NumPadListener? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        binding = LayoutNumberInputBinding.bind(LayoutInflater.from(context).inflate(R.layout.layout_number_input, this, true))

        binding.number0.setOnClickListener {
            listener?.onNumberClicked("0")
        }
        binding.number1.setOnClickListener {
            listener?.onNumberClicked("1")
        }
        binding.number2.setOnClickListener {
            listener?.onNumberClicked("2")
        }
        binding.number3.setOnClickListener {
            listener?.onNumberClicked("3")
        }
        binding.number4.setOnClickListener {
            listener?.onNumberClicked("4")
        }
        binding.number5.setOnClickListener {
            listener?.onNumberClicked("5")
        }
        binding.number6.setOnClickListener {
            listener?.onNumberClicked("6")
        }
        binding.number7.setOnClickListener {
            listener?.onNumberClicked("7")
        }
        binding.number8.setOnClickListener {
            listener?.onNumberClicked("8")
        }
        binding.number9.setOnClickListener {
            listener?.onNumberClicked("9")
        }
        binding.deleteBtn.setOnClickListener {
            listener?.onDeleteClicked()
        }

        binding.fingerprintBtn.setOnClickListener {
            listener?.onFingerprintClicked()
        }
    }

    fun isFingerprint(isFingerprint: Boolean) {
        binding.fingerprintBtn.setVisible(isVisible)
    }

    fun setListener(listener: NumPadListener) {
        this.listener = listener
    }

    interface NumPadListener {
        fun onNumberClicked(s: String)
        fun onDeleteClicked()
        fun onFingerprintClicked() {}
    }
}