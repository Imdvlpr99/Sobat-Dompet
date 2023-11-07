package com.imdvlpr.expensetracker.helper.ui

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.Spanned
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.imdvlpr.expensetracker.R
import com.imdvlpr.expensetracker.databinding.LayoutInputBinding
import com.imdvlpr.expensetracker.helper.utils.setVisible

class CustomInputView: ConstraintLayout {

    private lateinit var binding: LayoutInputBinding
    private var isPassword: Boolean = false
    private var listener: InputViewListener? = null

    enum class INPUT_FILTER { UPPERCASE, LOWERCASE, TEXT_ONLY, UPPERCASE_WITH_SPECIAL_CHAR, LOWERCASE_WITH_SPECIAL_CHAR, PASSWORD}

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        binding = LayoutInputBinding.bind(LayoutInflater.from(context).inflate(R.layout.layout_input, this, true))

        binding.inputText.addTextChangedListener(textWatcher)
        binding.rightIndicator.setOnClickListener {
            when (isPassword) {
                true -> {
                    if (binding.inputText.inputType == InputType.TYPE_CLASS_TEXT) {
                        binding.rightIndicator.setImageResource(R.drawable.ic_eye)
                        binding.inputText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        binding.inputText.setSelection(binding.inputText.length())
                    } else if (binding.inputText.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                        binding.rightIndicator.setImageResource(R.drawable.ic_eye_off)
                        binding.inputText.inputType = InputType.TYPE_CLASS_TEXT
                        binding.inputText.setSelection(binding.inputText.length())
                    }
                }
                false -> listener?.onRightIndicatorClick()
            }
        }
        binding.inputText.setOnClickListener {
            listener?.onInputClicked()
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            listener?.beforeTextChanged()
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            listener?.onTextChanged()
        }

        override fun afterTextChanged(s: Editable?) {
            listener?.afterTextChanged(s)
        }

    }

    fun setTitle(title: String) {
        binding.titleName.text = title
    }

    fun setIndicator(left: Boolean = false, right: Boolean = false) {
        binding.leftIndicator.setVisible(left)
        binding.rightIndicator.setVisible(right)
    }

    fun setLeftIndicatorIcon(icon: Int) {
        binding.leftIndicator.setImageResource(icon)
    }

    fun setRightIndicatorIcon(icon: Int, isVisible: Boolean) {
        binding.rightIndicator.setImageResource(icon)
        binding.rightIndicator.setVisible(isVisible)
    }

    fun setIsPassword(isPassword: Boolean = false) {
        this.isPassword = isPassword
    }

    fun setHint(hint: String) {
        binding.inputText.hint = hint
    }

    fun setInputType(type: Int) {
        binding.inputText.inputType = type
    }

    fun setListener(listener: InputViewListener) {
        this.listener = listener
    }

    fun setError(isError: Boolean, messages: String? = null) {
        when (isError) {
            true -> {
                binding.alertMessage.setVisible(true)
                binding.alertMessage.text = messages
                binding.inputLayout.background = ContextCompat.getDrawable(context, R.drawable.bg_input_error)
            }
            false -> {
                binding.alertMessage.setVisible(false)
                binding.inputLayout.background = ContextCompat.getDrawable(context, R.drawable.bg_input)
            }
        }
    }

    fun setInputFilter(inputFilter: INPUT_FILTER) {
        val filter = object : InputFilter {
            override fun filter(source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int): CharSequence? {
                val textOnly = "[a-zA-Z\\s]+".toRegex()
                val lowerCase = "[a-z\\s]+".toRegex()
                val lowerCaseWithSpecialChar = "[a-z\\p{P}]+".toRegex()
                val upperCase = "[A-Z\\s]+".toRegex()
                val upperCaseWithSpecialChar = "[A-Z\\p{P}]+".toRegex()
                val password = "[A-Za-z\\p{P}]+".toRegex()

                when (inputFilter) {
                    INPUT_FILTER.LOWERCASE -> {
                        for (i in start until end) {
                            if (!lowerCase.matches(source?.get(i).toString())) {
                                return ""
                            }
                        }
                    }
                    INPUT_FILTER.UPPERCASE -> {
                        for (i in start until end) {
                            if (!upperCase.matches(source?.get(i).toString())) {
                                return ""
                            }
                        }
                    }
                    INPUT_FILTER.TEXT_ONLY -> {
                        for (i in start until end) {
                            if (!textOnly.matches(source?.get(i).toString())) {
                                return ""
                            }
                        }
                    }
                    INPUT_FILTER.LOWERCASE_WITH_SPECIAL_CHAR -> {
                        for (i in start until end) {
                            if (!lowerCaseWithSpecialChar.matches(source?.get(i).toString())) {
                                return ""
                            }
                        }
                    }
                    INPUT_FILTER.UPPERCASE_WITH_SPECIAL_CHAR -> {
                        for (i in start until end) {
                            if (!upperCaseWithSpecialChar.matches(source?.get(i).toString())) {
                                return ""
                            }
                        }
                    }
                    INPUT_FILTER.PASSWORD -> {
                        for (i in start until end) {
                            if (!password.matches(source?.get(i).toString())) {
                                return ""
                            }
                        }
                    }
                }

                return null
            }
        }
        binding.inputText.filters = arrayOf(filter)
    }

    fun getText(): String {
        return binding.inputText.text.toString()
    }

    fun setText(s: String) {
        binding.inputText.setText(s)
    }

    interface InputViewListener {
        fun onRightIndicatorClick() {}
        fun afterTextChanged(s: Editable?) {}
        fun beforeTextChanged() {}
        fun onTextChanged() {}
        fun onInputClicked() {}
    }
}