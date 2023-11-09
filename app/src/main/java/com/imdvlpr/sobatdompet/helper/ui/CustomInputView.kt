package com.imdvlpr.sobatdompet.helper.ui

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.Spanned
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.imdvlpr.sobatdompet.R
import com.imdvlpr.sobatdompet.databinding.LayoutInputBinding
import com.imdvlpr.sobatdompet.helper.utils.setVisible

class CustomInputView: ConstraintLayout {

    private lateinit var binding: LayoutInputBinding
    private var isPassword: Boolean = false
    private var listener: InputViewListener? = null

    enum class InputFilter { UPPERCASE, LOWERCASE, TEXT_ONLY, UPPERCASE_WITH_SPECIAL_CHAR, LOWERCASE_WITH_SPECIAL_CHAR, PASSWORD, PHONE}

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

        binding.outputText.setOnClickListener {
            listener?.onInputClicked()
        }

        binding.helperText.setOnClickListener {
            listener?.onHelperClicked()
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

    fun setIndicator(visible: Boolean = false) {
        binding.rightIndicator.setVisible(visible)
    }

    fun setRightIndicatorIcon(icon: Int, isVisible: Boolean) {
        binding.rightIndicator.setImageResource(icon)
        binding.rightIndicator.setVisible(isVisible)
    }

    fun setIsPassword(isPassword: Boolean = false) {
        this.isPassword = isPassword
    }

    fun setHelper(visible: Boolean, text: String) {
        binding.helperText.setVisible(visible)
        binding.helperText.text = text
    }

    fun setSuffix(suffix: String) {
        if (suffix.isNotEmpty()) {
            binding.lblSuffix.text = suffix
            binding.lblSuffix.toVisible()
        }
    }

    fun setHint(hint: String) {
        binding.inputText.hint = hint
        binding.outputText.hint = hint
    }

    fun setInputType(type: Int) {
        if (type == InputType.TYPE_NULL) {
            binding.inputText.setVisible(false)
            binding.outputText.setVisible(true)
        } else {
            binding.inputText.setVisible(true)
            binding.outputText.setVisible(false)
            binding.inputText.inputType = type
        }
    }

    fun isEmailValid(email: CharSequence): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
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

    fun setInputFilter(inputFilter: InputFilter) {
        val filter = object : android.text.InputFilter {
            override fun filter(source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int): CharSequence? {
                val textOnly = "[a-zA-Z\\s]+".toRegex()
                val lowerCase = "[a-z\\s]+".toRegex()
                val lowerCaseWithSpecialChar = "[a-z\\p{P}]+".toRegex()
                val upperCase = "[A-Z\\s]+".toRegex()
                val upperCaseWithSpecialChar = "[A-Z\\p{P}]+".toRegex()
                val password = "[A-Za-z\\p{P}]+".toRegex()

                when (inputFilter) {
                    InputFilter.LOWERCASE -> {
                        for (i in start until end) {
                            if (!lowerCase.matches(source?.get(i).toString())) {
                                return ""
                            }
                        }
                    }
                    InputFilter.UPPERCASE -> {
                        for (i in start until end) {
                            if (!upperCase.matches(source?.get(i).toString())) {
                                return ""
                            }
                        }
                    }
                    InputFilter.TEXT_ONLY -> {
                        for (i in start until end) {
                            if (!textOnly.matches(source?.get(i).toString())) {
                                return ""
                            }
                        }
                    }
                    InputFilter.LOWERCASE_WITH_SPECIAL_CHAR -> {
                        for (i in start until end) {
                            if (!lowerCaseWithSpecialChar.matches(source?.get(i).toString())) {
                                return ""
                            }
                        }
                    }
                    InputFilter.UPPERCASE_WITH_SPECIAL_CHAR -> {
                        for (i in start until end) {
                            if (!upperCaseWithSpecialChar.matches(source?.get(i).toString())) {
                                return ""
                            }
                        }
                    }
                    InputFilter.PASSWORD -> {
                        for (i in start until end) {
                            if (!password.matches(source?.get(i).toString())) {
                                return ""
                            }
                        }
                    }
                    InputFilter.PHONE -> {
                        if (source.isNullOrEmpty()) {
                            return null
                        }
                        if (dstart == 0 && source[0] == '0') {
                            return ""
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
        binding.outputText.text = s
    }

    interface InputViewListener {
        fun onRightIndicatorClick() {}
        fun afterTextChanged(s: Editable?) {}
        fun beforeTextChanged() {}
        fun onTextChanged() {}
        fun onInputClicked() {}

        fun onHelperClicked() {}
    }
}