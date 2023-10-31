package com.imdvlpr.expensetracker.helper.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View.OnFocusChangeListener
import android.view.View.OnTouchListener
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.imdvlpr.expensetracker.R
import com.imdvlpr.expensetracker.databinding.LayoutOtpInputBinding
import com.imdvlpr.expensetracker.helper.utils.SpannableListener
import com.imdvlpr.expensetracker.helper.utils.setSpannable
import com.imdvlpr.expensetracker.helper.utils.setVisible
import java.util.concurrent.TimeUnit


class CustomOTPInput: ConstraintLayout {

    private lateinit var binding: LayoutOtpInputBinding
    private lateinit var countDownTimer: CountDownTimer
    private val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    private var listener: InputOTPListener? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init(context: Context, attrs: AttributeSet?) {
        binding = LayoutOtpInputBinding.bind(LayoutInflater.from(context).inflate(R.layout.layout_otp_input, this, true))

        binding.et1.onFocusChangeListener = onFocusChangeListener
        binding.et2.onFocusChangeListener = onFocusChangeListener
        binding.et3.onFocusChangeListener = onFocusChangeListener
        binding.et4.onFocusChangeListener = onFocusChangeListener
        binding.et1.setOnTouchListener(touchListener)
        binding.et2.setOnTouchListener(touchListener)
        binding.et3.setOnTouchListener(touchListener)
        binding.et4.setOnTouchListener(touchListener)
        binding.et1.addTextChangedListener(textWatcher)
        binding.et2.addTextChangedListener(textWatcher)
        binding.et3.addTextChangedListener(textWatcher)
        binding.et4.addTextChangedListener(textWatcher)
    }

    private val onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
        if (hasFocus) {
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private val touchListener = OnTouchListener { v, event ->
        v.requestFocus()
        true
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            if (s.toString().isEmpty()) {
                if (binding.et1.isFocused) {
                    binding.et1.requestFocus()
                } else if (binding.et2.isFocused) {
                    binding.et2.clearFocus()
                    binding.et2.requestFocus()
                } else if (binding.et3.isFocused) {
                    binding.et3.clearFocus()
                    binding.et3.requestFocus()
                } else if (binding.et4.isFocused) {
                    binding.et4.clearFocus()
                    binding.et4.requestFocus()
                }
            } else {
                if (binding.et1.isFocused) {
                    binding.et2.requestFocus()
                } else if (binding.et2.isFocused) {
                    binding.et3.requestFocus()
                } else if (binding.et3.isFocused) {
                    binding.et4.requestFocus()
                }
            }
        }
    }

    fun setOTP(s: String) {
        if (binding.et1.text.isEmpty()) {
            binding.et1.setText(s)
            binding.et2.requestFocus()
        } else if (binding.et2.text.isEmpty()) {
            binding.et2.setText(s)
            binding.et3.requestFocus()
        } else if (binding.et3.text.isEmpty()) {
            binding.et3.setText(s)
            binding.et4.requestFocus()
        } else if (binding.et4.text.isEmpty()) {
            binding.et4.setText(s)
        }
    }

    fun validateInput(): Boolean {
        return binding.et1.text.isNotEmpty() && binding.et2.text.isNotEmpty() && binding.et3.text.isNotEmpty() && binding.et4.text.isNotEmpty()
    }

    fun getValue(): String {
        return binding.et1.text.toString() + binding.et2.text.toString() + binding.et3.text.toString() + binding.et4.text.toString()
    }

    fun setCountDown(text: String, isVisible: Boolean, time: Long) {
        binding.infoText.setVisible(isVisible)
        val initialTimeInMillis = TimeUnit.SECONDS.toMillis(time)
        countDownTimer = object : CountDownTimer(initialTimeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                val otpInfo = String.format(text, secondsRemaining)
                binding.infoText.text = context.setSpannable(otpInfo, secondsRemaining.toString())
            }

            override fun onFinish() {
                val otpResend = String.format(context.getString(R.string.otp_info), context.getString(R.string.otp_resend))
                binding.infoText.text = context.setSpannable(otpResend, context.getString(R.string.otp_resend), object : SpannableListener {
                    override fun onClick() {
                        listener?.resendOtp()
                    }
                })
                binding.infoText.movementMethod = LinkMovementMethod.getInstance()
            }
        }
        countDownTimer.start()
    }

    fun cancelTimer() {
        countDownTimer.cancel()
    }

    fun deleteValue() {
        if (binding.et1.isFocused) {
            binding.et1.setText("")
        } else if (binding.et2.isFocused) {
            binding.et2.setText("")
            binding.et1.requestFocus()
        } else if (binding.et3.isFocused) {
            binding.et3.setText("")
            binding.et2.requestFocus()
        } else if (binding.et4.isFocused) {
            binding.et4.setText("")
            binding.et3.requestFocus()
        }
    }

    fun resetValue() {
        binding.et1.setText("")
        binding.et2.setText("")
        binding.et3.setText("")
        binding.et4.setText("")
        binding.et1.requestFocus()
    }

    fun setListener(listener: InputOTPListener) {
        this.listener = listener
    }

    interface InputOTPListener {
        fun resendOtp() {}
    }
}