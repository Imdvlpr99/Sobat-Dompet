package com.imdvlpr.sobatdompet.helper.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.imdvlpr.sobatdompet.R
import com.imdvlpr.sobatdompet.databinding.LayoutDualTabBinding

class CustomDualTab: ConstraintLayout {

    private lateinit var binding: LayoutDualTabBinding
    private var listener: DualTabListener? = null
    private var leftSelected: Boolean = true
    private var rightSelected: Boolean = false
    enum class SELECTED { LEFT, RIGHT }
    private var getSelected: SELECTED = SELECTED.LEFT

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
        binding = LayoutDualTabBinding.bind(LayoutInflater.from(context).inflate(R.layout.layout_dual_tab, this, true))

        binding.userName.setOnClickListener {
            if (!leftSelected) {
                setSelected(SELECTED.LEFT)
                listener?.onLeftClicked()
            }
        }
        binding.phone.setOnClickListener {
            if (!rightSelected) {
                setSelected(SELECTED.RIGHT)
                listener?.onRightClicked()
            }
        }
    }

    private fun fadeInAnim(view: View) {
        val fadeInAnimation = AlphaAnimation(0f, 1f)

        fadeInAnimation.duration = 500
        fadeInAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                view.setBackgroundResource(R.drawable.bg_white_corner)
            }
            override fun onAnimationRepeat(animation: Animation) {}
        })

        view.startAnimation(fadeInAnimation)
    }

    private fun fadeOutAnim(view: View) {
        val fadeInAnimation = AlphaAnimation(1f, 0f)

        fadeInAnimation.duration = 500
        fadeInAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                view.setBackgroundResource(android.R.color.transparent)
            }
            override fun onAnimationRepeat(animation: Animation) {}
        })

        view.startAnimation(fadeInAnimation)
    }

    private fun setSelected(selected: SELECTED) {
        if (selected == SELECTED.LEFT) {
            leftSelected = true
            rightSelected = false
            fadeInAnim(binding.userName)
            fadeOutAnim(binding.phone)
            binding.userName.setBackgroundResource(R.drawable.bg_white_corner)
            binding.phone.setBackgroundResource(android.R.color.transparent)
            binding.userNameTitle.setTextColor(ContextCompat.getColor(context, R.color.black))
            binding.phoneTitle.setTextColor(ContextCompat.getColor(context, R.color.white))
        } else if (selected == SELECTED.RIGHT) {
            leftSelected = false
            rightSelected = true
            fadeInAnim(binding.phone)
            fadeOutAnim(binding.userName)
            binding.phone.setBackgroundResource(R.drawable.bg_white_corner)
            binding.userName.setBackgroundResource(android.R.color.transparent)
            binding.userNameTitle.setTextColor(ContextCompat.getColor(context, R.color.white))
            binding.phoneTitle.setTextColor(ContextCompat.getColor(context, R.color.black))
        }
    }

    fun getSelected(): SELECTED {
        return getSelected
    }

    fun setListener(listener: DualTabListener) {
        this.listener = listener
    }

    interface DualTabListener {
        fun onLeftClicked()
        fun onRightClicked()
    }
}