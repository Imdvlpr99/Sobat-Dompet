package com.imdvlpr.sobatdompet.activity.onBoarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.imdvlpr.sobatdompet.activity.auth.LoginView
import com.imdvlpr.sobatdompet.activity.auth.RegisterView
import com.imdvlpr.sobatdompet.databinding.ActivityGettingStartedBinding
import com.imdvlpr.sobatdompet.helper.base.BaseActivity
import com.imdvlpr.sobatdompet.helper.utils.getStatusBarHeight

class GettingStartedView : BaseActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, GettingStartedView::class.java)
        }
    }

    private lateinit var binding: ActivityGettingStartedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGettingStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageView.setPadding(0, getStatusBarHeight(), 0, 0)
        initView()
    }

    private fun initView() {
        binding.loginBtn.setOnClickListener {
            startActivity(LoginView.newIntent(this))
        }

        binding.registerBtn.setOnClickListener {
            startActivity(RegisterView.newIntent(this))
        }
    }
}