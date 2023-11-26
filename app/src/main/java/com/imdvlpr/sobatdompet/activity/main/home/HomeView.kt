package com.imdvlpr.sobatdompet.activity.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.imdvlpr.sobatdompet.R
import com.imdvlpr.sobatdompet.databinding.FragmentHomeBinding
import com.imdvlpr.sobatdompet.helper.ui.CustomHomeHeader
import com.imdvlpr.sobatdompet.helper.utils.Constants
import com.imdvlpr.sobatdompet.helper.utils.SharedPreference
import com.imdvlpr.sobatdompet.helper.utils.decodeImage
import com.imdvlpr.sobatdompet.helper.utils.getStatusBarHeight

class HomeView : Fragment() {

    companion object {
        fun newInstance(): HomeView {
            val fragment = HomeView()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var binding: FragmentHomeBinding
    private var sharedPreference: SharedPreference = SharedPreference()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.bind(inflater.inflate(R.layout.fragment_home, container, false))
        sharedPreference.sharedPreference(requireContext())
        initView()
        return binding.root
    }

    private fun initView() {
        binding.customHeader.apply {
            setPadding(0, context.getStatusBarHeight(), 0, 0)
            setUserData(sharedPreference.getStringFromPref(Constants.PREF.FULL_NAME),
                decodeImage(sharedPreference.getStringFromPref(Constants.PREF.USER_IMAGE)))
            setListener(object : CustomHomeHeader.HomeHeaderListener {
                override fun onNotificationClick() {

                }
            })
        }
    }
}