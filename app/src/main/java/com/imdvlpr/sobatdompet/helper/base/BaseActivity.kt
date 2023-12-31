package com.imdvlpr.sobatdompet.helper.base

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import com.imdvlpr.sobatdompet.R
import com.imdvlpr.sobatdompet.di.appModule
import com.imdvlpr.sobatdompet.di.mvpModule
import com.imdvlpr.sobatdompet.helper.utils.BetterActivityResult
import com.imdvlpr.sobatdompet.helper.utils.setLocale
import com.imdvlpr.sobatdompet.helper.utils.setWindowFlag
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication

open class BaseActivity: AppCompatActivity() {

    private var dialogProgress: Dialog? = null
    lateinit var activityLauncher: BetterActivityResult<Intent, ActivityResult>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWindow()
        initProgress()
        onBackPressedDispatcher.addCallback(this, callback)
        activityLauncher = BetterActivityResult.registerActivityForResult(this)
    }

    private fun initWindow() {
        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
    }

    private fun initProgress() {
        dialogProgress = Dialog(this, R.style.FullScreenDialogTheme)
        dialogProgress?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogProgress?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogProgress?.setContentView(R.layout.dialog_progress)
        val params = this.window.attributes
        params.x = 0
        params.y = 0
        dialogProgress?.window?.attributes = params
        dialogProgress?.setCancelable(false)
        dialogProgress?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
    }

    fun showProgress() { if (!isFinishing && dialogProgress?.isShowing == false) dialogProgress?.show() }
    fun hideProgress() { if (!isFinishing && dialogProgress?.isShowing == true) dialogProgress?.dismiss() }

    fun startKoin() {
        startKoin {
            androidContext(this@BaseActivity)
            modules(appModule, mvpModule)
        }
    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
        }
    }

    override fun attachBaseContext(newBase: Context) {
        val newLocale = "id"
        val context = setLocale(newBase, newLocale)
        super.attachBaseContext(context)
    }
}