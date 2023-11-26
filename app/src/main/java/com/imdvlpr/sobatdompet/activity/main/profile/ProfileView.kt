package com.imdvlpr.sobatdompet.activity.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.imdvlpr.sobatdompet.R
import com.imdvlpr.sobatdompet.databinding.FragmentProfileBinding
import com.imdvlpr.sobatdompet.helper.utils.Constants
import com.imdvlpr.sobatdompet.helper.utils.SharedPreference
import com.imdvlpr.sobatdompet.helper.utils.decodeImage
import com.imdvlpr.sobatdompet.helper.utils.getStatusBarHeight

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
    private val pref: SharedPreference = SharedPreference()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProfileBinding.bind(inflater.inflate(R.layout.fragment_profile, container, false))
        pref.sharedPreference(requireContext())
        initView()
        return binding.root
    }

    private fun initView() {
        binding.customToolbar.apply {
            setPadding(0, context.getStatusBarHeight(), 0, 0)
            setBackButton(false)
            setTitle(getString(R.string.profile_toolbar_title))
        }

        binding.userImageData.apply {
            setUserData(decodeImage(pref.getStringFromPref(Constants.PREF.USER_IMAGE)),
                pref.getStringFromPref(Constants.PREF.FULL_NAME), pref.getStringFromPref(Constants.PREF.USER_NAME))
        }
    }
}
