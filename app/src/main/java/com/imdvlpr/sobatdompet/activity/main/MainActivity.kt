package com.imdvlpr.sobatdompet.activity.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.imdvlpr.sobatdompet.activity.auth.LoginView
import com.imdvlpr.sobatdompet.databinding.ActivityMainBinding
import com.imdvlpr.sobatdompet.helper.base.BaseActivity
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
        binding.customHeader.apply {
            setPadding(0, getStatusBarHeight(), 0, 0)
            setUserData("John Doe")
        }

        binding.logoutBtn.setOnClickListener {
            presenter.logout()
        }
    }

    override fun onSuccessLogout() {
        if (!isFinishing) {
            startActivity(LoginView.intentClear(this))
            finish()
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