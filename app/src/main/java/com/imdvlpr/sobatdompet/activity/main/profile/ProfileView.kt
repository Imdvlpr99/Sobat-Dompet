package com.imdvlpr.sobatdompet.activity.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.imdvlpr.sobatdompet.R
import com.imdvlpr.sobatdompet.databinding.FragmentProfileBinding
import com.imdvlpr.sobatdompet.helper.ui.CustomMenu
import com.imdvlpr.sobatdompet.helper.utils.Constants
import com.imdvlpr.sobatdompet.helper.utils.SharedPreference
import com.imdvlpr.sobatdompet.helper.utils.decodeImage
import com.imdvlpr.sobatdompet.helper.utils.getStatusBarHeight
import com.imdvlpr.sobatdompet.helper.utils.isBiometricAvailable
import com.imdvlpr.sobatdompet.helper.utils.showBiometricPrompt

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

        binding.profileBtn.apply {
            setMenuData(getString(R.string.profile_your_profile), R.drawable.ic_user)
            setListener(object : CustomMenu.MenuInterface {
                override fun onClick() {
                }
            })
        }

        binding.transactionHistoryBtn.apply {
            setMenuData(getString(R.string.profile_history_transaction), R.drawable.ic_history)
            setListener(object : CustomMenu.MenuInterface {
                override fun onClick() {
                }

            })
        }

        binding.biometricBtn.apply {
            setMenuData(getString(R.string.profile_biometric), R.drawable.ic_fingerprint)
            setSwitch(true)
            setListener(object : CustomMenu.MenuInterface {
                override fun onClick() {
                }

                override fun onSwitchClick(isChecked: Boolean) {
                    if (requireContext().isBiometricAvailable())
                        if (isChecked) showBiometricPrompt(
                            requireActivity(),
                            onSuccess = {
                                // Authentication successful
                            },
                            onError = { errorCode, errString ->
                                // Handle authentication error
                            },
                            onFailed = {
                                // Authentication failed
                            }
                        )
                }
            })
        }

        binding.changePasswordBtn.apply {
            setMenuData(getString(R.string.profile_change_password), R.drawable.ic_lock)
            setListener(object : CustomMenu.MenuInterface {
                override fun onClick() {

                }

            })
        }

        binding.forgotPasswordBtn.apply {
            setMenuData(getString(R.string.profile_forgot_password), R.drawable.ic_unlock)
            setListener(object : CustomMenu.MenuInterface {
                override fun onClick() {

                }

            })
        }

        binding.notificationBtn.apply {
            setMenuData(getString(R.string.profile_notification), R.drawable.ic_notification)
            setListener(object : CustomMenu.MenuInterface {
                override fun onClick() {

                }

            })
        }

        binding.languageBtn.apply {
            setMenuData(getString(R.string.profile_language), R.drawable.ic_internet)
            setListener(object : CustomMenu.MenuInterface {
                override fun onClick() {

                }

            })
        }

        binding.helpSupportBtn.apply {
            setMenuData(getString(R.string.profile_help_support), R.drawable.ic_info)
            setListener(object : CustomMenu.MenuInterface {
                override fun onClick() {

                }

            })
        }
    }
}
