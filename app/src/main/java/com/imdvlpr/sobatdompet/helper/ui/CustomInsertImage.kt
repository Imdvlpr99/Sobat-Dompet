package com.imdvlpr.sobatdompet.helper.ui

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.imdvlpr.sobatdompet.R
import com.imdvlpr.sobatdompet.databinding.LayoutEditImageBinding

class CustomInsertImage: ConstraintLayout {

    private lateinit var binding: LayoutEditImageBinding
    private var listener: InsertImageListener? = null

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

    fun setListener(listener: InsertImageListener) {
        this.listener = listener
    }

    interface InsertImageListener {
        fun onCameraClicked()
    }
}