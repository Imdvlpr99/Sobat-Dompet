package com.imdvlpr.sobatdompet.activity.onBoarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.imdvlpr.sobatdompet.R
import com.imdvlpr.sobatdompet.databinding.ItemOnboardingBinding

class OnboardViewPager(private var context: Context) : PagerAdapter() {

    private val images = arrayOf(
        R.drawable.ic_onboard_1,
        R.drawable.ic_onboard_2,
        R.drawable.ic_onboard_3)

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding: ItemOnboardingBinding
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemOnboardingBinding.inflate(layoutInflater, container, false)
        val view: View = binding.root
        val vp = container as ViewPager

        binding.itemOnboarding.alpha = 0f
        binding.itemOnboarding.setImageResource(images[position])
        binding.itemOnboarding.animate().alpha(1f).setDuration(500).start()
        binding.itemOnboarding.setImageResource(images[position])

        vp.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }
}