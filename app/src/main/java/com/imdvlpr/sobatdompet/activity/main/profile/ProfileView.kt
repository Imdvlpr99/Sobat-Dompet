package com.imdvlpr.sobatdompet.activity.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.imdvlpr.sobatdompet.R
import com.imdvlpr.sobatdompet.activity.main.home.HomeView
import com.imdvlpr.sobatdompet.databinding.FragmentProfileBinding

class ProfileView : Fragment() {

    companion object {
        fun newInstance(): ProfileView {
            val fragment = ProfileView()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProfileBinding.bind(inflater.inflate(R.layout.fragment_cards, container, false))
        initView()
        return binding.root
    }

    private fun initView() {

    }
}