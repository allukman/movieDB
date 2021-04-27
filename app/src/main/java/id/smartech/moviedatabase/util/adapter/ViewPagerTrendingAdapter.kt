package id.smartech.moviedatabase.util.adapter

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import id.smartech.moviedatabase.activity.home.trending.byday.TrendingDayFragment
import id.smartech.moviedatabase.activity.home.trending.byweek.TrendingWeekFragment

class ViewPagerTrendingAdapter (fragment: FragmentManager) : FragmentStatePagerAdapter(fragment, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    val fragment = arrayOf(
        TrendingDayFragment(),
        TrendingWeekFragment()
    )

    override fun getCount(): Int = fragment.size

    override fun getItem(position: Int): Fragment {
        return fragment[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Today"
            1 -> "This Week"
            else -> ""
        }
    }

    override fun saveState(): Parcelable? {
        return null
    }
}