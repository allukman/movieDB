package id.smartech.moviedatabase.activity.home

import android.os.Bundle
import android.view.View
import id.smartech.moviedatabase.R
import id.smartech.moviedatabase.activity.SearchActivity
import id.smartech.moviedatabase.util.adapter.ViewPagerPopularAdapter
import id.smartech.moviedatabase.util.adapter.ViewPagerTrendingAdapter
import id.smartech.moviedatabase.base.BaseFragment
import id.smartech.moviedatabase.databinding.FragmentHomeBinding


class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private lateinit var pagerAdapter: ViewPagerTrendingAdapter
    private lateinit var popularPagerAdapter: ViewPagerPopularAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_home
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTrendingViewPager()
        setPopularViewPager()
        setOnClick()
    }

    private fun setTrendingViewPager() {
        pagerAdapter =
                ViewPagerTrendingAdapter(
                        childFragmentManager
                )
        bind.viewPager.adapter = pagerAdapter
        bind.tabLayout.setupWithViewPager(bind.viewPager)
    }

    private fun setPopularViewPager() {
        popularPagerAdapter = ViewPagerPopularAdapter(childFragmentManager)
        bind.viewPagerPopular.adapter = popularPagerAdapter
        bind.viewPagerPopular.offscreenPageLimit = 2
        bind.tabLayoutPopular.setupWithViewPager(bind.viewPagerPopular)
    }

    private fun setOnClick() {
        bind.searchButton.setOnClickListener {
            intents<SearchActivity>(activity)
        }
    }

}