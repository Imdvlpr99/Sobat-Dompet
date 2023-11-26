package com.imdvlpr.sobatdompet.activity.main.cards

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.imdvlpr.sobatdompet.R
import com.imdvlpr.sobatdompet.activity.main.home.HomeView
import com.imdvlpr.sobatdompet.databinding.FragmentCardsBinding

class CardsView : Fragment() {

    companion object {
        fun newInstance(): CardsView {
            val fragment = CardsView()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var binding: FragmentCardsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCardsBinding.bind(inflater.inflate(R.layout.fragment_cards, container, false))
        initView()
        return binding.root
    }

    private fun initView() {

    }
}