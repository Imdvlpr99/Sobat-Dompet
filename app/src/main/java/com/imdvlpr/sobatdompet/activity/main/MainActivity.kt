package com.imdvlpr.sobatdompet.activity.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.imdvlpr.sobatdompet.activity.auth.LoginView
import com.imdvlpr.sobatdompet.databinding.ActivityMainBinding
import com.imdvlpr.sobatdompet.helper.base.BaseActivity
import com.imdvlpr.sobatdompet.helper.ui.CustomBottomBar
import com.imdvlpr.sobatdompet.helper.ui.responseDialog
import com.imdvlpr.sobatdompet.helper.utils.getStatusBarHeight

class MainActivity : BaseActivity(), MainInterface {

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            return intent
        }
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onAttach()
        initView()
    }

    private fun initView() {
        binding.bottomBar.apply {
            setSelectedMenu(1)
            setListener(navListener)
        }
    }

    private var navListener = object : CustomBottomBar.BottomBarListener {
        override fun onMenuCLick(position: Int) {

        }

        override fun onCenterMenuClick() {
        }

    }

    override fun onProgress() {
        if (!isFinishing) showProgress()
    }

    override fun onFinishProgress() {
        if (!isFinishing) hideProgress()
    }

    override fun onFailed(message: String) {
        responseDialog(false, message)
    }

    override fun onAttach() {
        presenter = MainPresenter(this)
        presenter.onAttach(this)
    }

    override fun onDetach() {
        presenter.onDetach()
    }
}