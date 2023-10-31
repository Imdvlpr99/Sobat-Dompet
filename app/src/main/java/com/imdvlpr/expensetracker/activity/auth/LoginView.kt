package com.imdvlpr.expensetracker.activity.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.imdvlpr.expensetracker.databinding.ActivityLoginBinding
import com.imdvlpr.expensetracker.helper.base.BaseActivity

class LoginView : BaseActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LoginView::class.java)
        }
    }

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {

    }
}