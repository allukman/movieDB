package id.smartech.moviedatabase.util.adapter

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import id.smartech.moviedatabase.activity.home.popular.movie.PopularMovieFragment
import id.smartech.moviedatabase.activity.home.popular.people.PopularPeopleFragment
import id.smartech.moviedatabase.activity.home.popular.tv.PopularTvFragment

class ViewPagerPopularAdapter (fragment: FragmentManager) : FragmentStatePagerAdapter(fragment, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    val fragment = arrayOf(
        PopularMovieFragment(),
        PopularTvFragment(),
        PopularPeopleFragment()

    )

    override fun getCount(): Int = fragment.size

    override fun getItem(position: Int): Fragment {
        return fragment[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Movies"
            1 -> "TV"
            2 -> "People"
            else -> ""
        }
    }

    override fun saveState(): Parcelable? {
        return null
    }
}