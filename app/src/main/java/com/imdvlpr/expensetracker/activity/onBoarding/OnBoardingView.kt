package com.imdvlpr.expensetracker.activity.onBoarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.imdvlpr.expensetracker.R
import com.imdvlpr.expensetracker.databinding.ActivityOnboardingBinding
import com.imdvlpr.expensetracker.helper.base.BaseActivity
import com.imdvlpr.expensetracker.helper.utils.getStatusBarHeight
import java.util.Timer
import java.util.TimerTask

class OnBoardingView : BaseActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, OnBoardingView::class.java)
        }
    }

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var onboardViewPager: OnboardViewPager
    val dots: Array<ImageView?> = arrayOfNulls(3)
    private var dotsCount = 0
    private var currentPage = 0
    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        onboardViewPager = OnboardViewPager(this)
        binding.vpOnBoarding.adapter = onboardViewPager
        binding.root.setPadding(0, this.getStatusBarHeight(), 0, 0)
        dotsCount = onboardViewPager.count

        val update = Runnable {
            if (currentPage == 3) {
                currentPage = 0
            }
            binding.vpOnBoarding.setCurrentItem(currentPage++, true)
        }

        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                Handler(Looper.getMainLooper()).post(update)
            }
        }, 2500, 2500)

        for (i in 0 until dotsCount) {
            dots[i] = ImageView(this)
            dots[i]?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_dot_unselected))
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(8, 0, 8, 0)
            binding.vpSliderDots.addView(dots[i], params)
        }
        dots[0]?.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_dot_selected))
        binding.vpOnBoarding.addOnPageChangeListener(onPageChangeListener)

        binding.gettingStartedBtn.setOnClickListener {
            startActivity(GettingStartedView.newIntent(this))
        }
    }

    private val onPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
        override fun onPageScrollStateChanged(state: Int) {}
        override fun onPageSelected(position: Int) {
            when(binding.vpOnBoarding.currentItem) {
                2 -> {
                    binding.onboardingText.alpha = 0f
                    binding.onboardSubTitle.alpha = 0f
                    binding.onboardingText.setText(R.string.onboarding_text_3)
                    binding.onboardSubTitle.text = getString(R.string.onboarding_sub_title_3)
                    binding.onboardingText.animate().alpha(1f).setDuration(500).start()
                    binding.onboardSubTitle.animate().alpha(1f).setDuration(500).start()
                    updateDotsWithAnimation(position)
                }
                1 -> {
                    binding.onboardingText.alpha = 0f
                    binding.onboardSubTitle.alpha = 0f
                    binding.onboardingText.setText(R.string.onboarding_text_2)
                    binding.onboardSubTitle.text = getString(R.string.onboarding_sub_title_2)
                    binding.onboardingText.animate().alpha(1f).setDuration(500).start()
                    binding.onboardSubTitle.animate().alpha(1f).setDuration(500).start()
                    updateDotsWithAnimation(position)
                }
                0 -> {
                    binding.onboardingText.alpha = 0f
                    binding.onboardSubTitle.alpha = 0f
                    binding.onboardingText.setText(R.string.onboarding_text_1)
                    binding.onboardSubTitle.text = getString(R.string.onboarding_sub_title_1)
                    binding.onboardingText.animate().alpha(1f).setDuration(500).start()
                    binding.onboardSubTitle.animate().alpha(1f).setDuration(500).start()
                    updateDotsWithAnimation(position)
                }
            }
            for (i in 0 until dotsCount) {
                dots[i]?.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_dot_unselected))
            }
            dots[position]?.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_dot_selected))
        }
    }

    private fun updateDotsWithAnimation(selectedPosition: Int) {
        for (i in 0 until dotsCount) {
            if (i == selectedPosition) {
                dots[i]?.animate()?.scaleX(1.2f)?.scaleY(1.2f)?.setDuration(500)?.start()
            } else {
                dots[i]?.animate()?.scaleX(1.0f)?.scaleY(1.0f)?.setDuration(500)?.start()
            }
        }
    }
}