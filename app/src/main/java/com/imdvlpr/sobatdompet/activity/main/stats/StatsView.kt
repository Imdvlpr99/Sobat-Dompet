package com.imdvlpr.sobatdompet.activity.main.stats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.imdvlpr.sobatdompet.R
import com.imdvlpr.sobatdompet.activity.main.home.HomeView
import com.imdvlpr.sobatdompet.databinding.FragmentStatsBinding

class StatsView : Fragment() {

    companion object {
        fun newInstance(): StatsView {
            val fragment = StatsView()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var binding: FragmentStatsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentStatsBinding.bind(inflater.inflate(R.layout.fragment_stats, container, false))
        initView()
        return inflater.inflate(R.layout.fragment_stats, container, false)
    }

    private fun initView() {

    }
}