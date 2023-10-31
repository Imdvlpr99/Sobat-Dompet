package com.imdvlpr.expensetracker.helper.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.imdvlpr.expensetracker.R
import com.imdvlpr.expensetracker.databinding.LayoutHomeHeaderBinding

class CustomHomeHeader: ConstraintLayout {

    private lateinit var binding: LayoutHomeHeaderBinding

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
        binding = LayoutHomeHeaderBinding.bind(LayoutInflater.from(context).inflate(R.layout.layout_home_header, this, true))
    }

    fun setUserData(userName: String, userImg: Int? = null) {
        binding.userName.text = userName
        if (userImg == null) {
            Glide.with(context).load(R.drawable.ic_user_color).into(binding.userImage)
        } else {
            Glide.with(context)
                .load(userImg)
                .error(R.drawable.ic_user_color)
                .placeholder(R.drawable.ic_user_color)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).into(binding.userImage)
        }
    }
}