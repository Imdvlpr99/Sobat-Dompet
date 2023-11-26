package com.imdvlpr.sobatdompet.helper.ui

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.imdvlpr.sobatdompet.R
import com.imdvlpr.sobatdompet.databinding.LayoutEditImageBinding

class CustomUserImage: ConstraintLayout {

    private lateinit var binding: LayoutEditImageBinding
    private var listener: InsertImageListener? = null

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
        binding = LayoutEditImageBinding.bind(LayoutInflater.from(context).inflate(R.layout.layout_edit_image, this, true))
        initView()
    }

    private fun initView() {
        binding.insertImageBtn.setOnClickListener {
            listener?.onCameraClicked()
        }
    }

    fun setImage(bitmap: Bitmap) {
        binding.userImage.setImageBitmap(bitmap)
    }

    fun setUserData(bitmap: Bitmap, fullName: String, userName: String) {
        binding.userData.setVisible(true)
        binding.insertImageBtn.setVisible(false)
        binding.userImage.setImageBitmap(bitmap)
        binding.fullName.text = fullName
        binding.userName.text = userName
    }

    fun setListener(listener: InsertImageListener) {
        this.listener = listener
    }

    interface InsertImageListener {
        fun onCameraClicked() {}
    }
}