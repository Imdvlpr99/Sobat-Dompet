package com.imdvlpr.sobatdompet.helper.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.imdvlpr.sobatdompet.R
import com.imdvlpr.sobatdompet.databinding.LayoutMenuBinding

class CustomMenu: ConstraintLayout {

    private var listener: MenuInterface? = null
    private var isChecked: Boolean = false
    private val binding: LayoutMenuBinding by lazy {
        LayoutMenuBinding.bind(LayoutInflater.from(context).inflate(R.layout.layout_menu, this, true))
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        binding.root.setOnClickListener {
            listener?.onClick()
        }

        binding.btnSwitch.setOnClickListener {
            if (isChecked) {
                isChecked = false
                binding.btnSwitch.setImageResource(R.drawable.ic_switch_off)
                listener?.onSwitchClick(isChecked)
            } else {
                isChecked = true
                binding.btnSwitch.setImageResource(R.drawable.ic_switch_on)
                listener?.onSwitchClick(isChecked)
            }
        }
    }

    fun setMenuData(menuTitle: String, menuIcon: Int) {
        binding.menuTitle.text = menuTitle
        binding.menuIcon.setImageResource(menuIcon)
    }

    fun setSwitch(isEnabled: Boolean) {
        binding.btnSwitch.setVisible(isEnabled)
    }

    fun setListener(listener: MenuInterface) {
        this.listener = listener
    }

    interface MenuInterface {
        fun onClick()
        fun onSwitchClick(isChecked: Boolean) {}
    }
}