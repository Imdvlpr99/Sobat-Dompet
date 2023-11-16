package com.imdvlpr.sobatdompet.helper.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.imdvlpr.sobatdompet.R
import com.imdvlpr.sobatdompet.databinding.LayoutBottomBarBinding

class CustomBottomBar: ConstraintLayout {

    private lateinit var binding: LayoutBottomBarBinding
    private var listener: BottomBarListener? = null

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
        binding = LayoutBottomBarBinding.bind(LayoutInflater.from(context).inflate(R.layout.layout_bottom_bar, this, true))

        binding.homeBtn.setOnClickListener {
            setSelected(1)
            listener?.onMenuCLick(1)
        }

        binding.statsBtn.setOnClickListener {
            setSelected(2)
            listener?.onMenuCLick(2)
        }

        binding.cardsBtn.setOnClickListener {
            setSelected(3)
            listener?.onMenuCLick(3)
        }

        binding.profileBtn.setOnClickListener {
            setSelected(4)
            listener?.onMenuCLick(4)
        }

        binding.scanBtn.setOnClickListener {
            listener?.onCenterMenuClick()
        }
    }

    private fun setSelected(position: Int) {
        when (position) {
            1 -> {
                binding.homeIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_home_fill))
                binding.statsIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_stats))
                binding.cardsIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_cards))
                binding.userIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_user))
            }
            2 -> {
                binding.homeIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_home))
                binding.statsIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_stats_fill))
                binding.cardsIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_cards))
                binding.userIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_user))
            }
            3 -> {
                binding.homeIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_home))
                binding.statsIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_stats))
                binding.cardsIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_cards_fill))
                binding.userIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_user))
            }
            4 -> {
                binding.homeIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_home))
                binding.statsIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_stats))
                binding.cardsIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_cards))
                binding.userIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_user_fill))
            }
        }
    }

    fun setSelectedMenu(position: Int) {
        setSelected(position)
    }

    fun setListener(listener: BottomBarListener) {
        this.listener = listener
    }

    interface BottomBarListener {
        fun onMenuCLick(position: Int)
        fun onCenterMenuClick()
    }
}