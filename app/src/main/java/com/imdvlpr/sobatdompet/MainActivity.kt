package com.imdvlpr.sobatdompet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.imdvlpr.sobatdompet.databinding.ActivityMainBinding
import com.imdvlpr.sobatdompet.helper.base.BaseActivity
import com.imdvlpr.sobatdompet.helper.utils.getStatusBarHeight

class MainActivity : BaseActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.customHeader.apply {
            setPadding(0, getStatusBarHeight(), 0, 0)
            setUserData("John Doe")
        }
    }
}