package com.imdvlpr.sobatdompet.activity.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.imdvlpr.sobatdompet.R
import com.imdvlpr.sobatdompet.activity.main.cards.CardsView
import com.imdvlpr.sobatdompet.activity.main.home.HomeView
import com.imdvlpr.sobatdompet.activity.main.profile.ProfileView
import com.imdvlpr.sobatdompet.activity.main.stats.StatsView
import com.imdvlpr.sobatdompet.databinding.ActivityMainBinding
import com.imdvlpr.sobatdompet.helper.base.BaseActivity
import com.imdvlpr.sobatdompet.helper.ui.CustomBottomBar
import com.imdvlpr.sobatdompet.helper.ui.responseDialog
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity(), MainInterface {

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            return intent
        }
    }

    private lateinit var binding: ActivityMainBinding
    private val presenter: MainPresenter by inject()
    private lateinit var fragmentHome: HomeView
    private lateinit var fragmentStats: StatsView
    private lateinit var fragmentCards: CardsView
    private lateinit var fragmentProfile: ProfileView
    private var listFragment: ArrayList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onAttach()
        initView()
        initFragment()
    }

    private fun initView() {
        binding.bottomBar.apply {
            setSelectedMenu(0)
            setListener(navListener)
        }
    }

    private fun initFragment() {
        fragmentHome = HomeView.newInstance()
        fragmentStats = StatsView.newInstance()
        fragmentCards = CardsView.newInstance()
        fragmentProfile = ProfileView.newInstance()

        listFragment.add(fragmentHome)
        listFragment.add(fragmentStats)
        listFragment.add(fragmentCards)
        listFragment.add(fragmentProfile)

        navListener.onMenuCLick(0)
    }

    private var navListener = object : CustomBottomBar.BottomBarListener {
        override fun onMenuCLick(position: Int) {
            setSelected(position)
        }

        override fun onCenterMenuClick() {
        }

    }

    fun setSelected(position: Int) {
        when (position) {
            0 -> displayFragment(fragmentHome)
            1 -> displayFragment(fragmentStats)
            2 -> displayFragment(fragmentCards)
            3 -> displayFragment(fragmentProfile)
        }
    }

    private fun displayFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        if (fragment.isAdded) {
            ft.show(fragment)
        } else {
            ft.add(R.id.frameContainer, fragment)
        }
        listFragment.map {
            if (it != fragment && it.isAdded) {
                ft.hide(it)
            }
        }
        ft.commit()
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
        presenter.onAttach(this)
    }

    override fun onDetach() {
        presenter.onDetach()
    }
}