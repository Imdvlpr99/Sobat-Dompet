package com.imdvlpr.sobatdompet.helper.ui

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.imdvlpr.sobatdompet.R
import com.imdvlpr.sobatdompet.databinding.LayoutHomeHeaderBinding

class CustomHomeHeader: ConstraintLayout {

    private lateinit var binding: LayoutHomeHeaderBinding
    private var listener: HomeHeaderListener? = null

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

    fun setUserData(userName: String, userImg: Bitmap? = null) {
        binding.userName.text = userName
        if (userImg == null) {
            Glide.with(context).load(R.drawable.ic_user_placeholder).into(binding.userImage)
        } else {
            Glide.with(context)
                .load(userImg)
                .error(R.drawable.ic_user_placeholder)
                .placeholder(R.drawable.ic_user_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).into(binding.userImage)
        }
    }

    fun setListener(listener: HomeHeaderListener) {
        this.listener = listener
    }

    interface HomeHeaderListener {
        fun onNotificationClick()
    }
}