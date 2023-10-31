package com.imdvlpr.expensetracker.helper.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.imdvlpr.expensetracker.R
import com.imdvlpr.expensetracker.databinding.LayoutToolbarBinding
import com.imdvlpr.expensetracker.helper.utils.setVisible

class CustomToolbar: ConstraintLayout {

    private lateinit var binding: LayoutToolbarBinding
    private var listener: ToolbarListener? = null

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
        binding = LayoutToolbarBinding.bind(LayoutInflater.from(context).inflate(R.layout.layout_toolbar, this, true))
        binding.backBtn.setOnClickListener {
            listener?.onBackButtonClick()
        }
        binding.infoBtn.setOnClickListener {
            listener?.onInfoButtonClick()
        }
    }

    fun setTitle(title: String) {
        binding.titleName.text = title
    }

    fun setBackButton(isVisible: Boolean = true) {
        binding.backBtn.setVisible(isVisible)
    }

    fun setInfoButton(isVisible: Boolean = false) {
        binding.infoBtn.setVisible(isVisible)
    }

    fun setListener(listener: ToolbarListener) {
        this.listener = listener
    }

    interface ToolbarListener {
        fun onBackButtonClick() {}
        fun onInfoButtonClick() {}
    }
}