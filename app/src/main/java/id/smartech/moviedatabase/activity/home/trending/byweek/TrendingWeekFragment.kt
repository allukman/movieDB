package id.smartech.moviedatabase.activity.home.trending.byweek

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.smartech.moviedatabase.R
import id.smartech.moviedatabase.activity.detailmovie.DetailMovieActivity
import id.smartech.moviedatabase.activity.detailtv.DetailTvActivity
import id.smartech.moviedatabase.util.adapter.TvMovieAdapter
import id.smartech.moviedatabase.base.BaseFragment
import id.smartech.moviedatabase.databinding.FragmentTrendingWeekBinding
import id.smartech.moviedatabase.model.TvMovieModel


class TrendingWeekFragment : BaseFragment<FragmentTrendingWeekBinding>() {
    private lateinit var viewModel: TrendingWeekViewModel
    private lateinit var adapter: TvMovieAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var listTrending = ArrayList<TvMovieModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_trending_week
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setViewModel()
        subscribeLiveData()
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(TrendingWeekViewModel::class.java)
        viewModel.setService(createApi(activity))
        viewModel.getAllTrendingByWeek()
    }

    private fun subscribeLiveData() {
        activity?.let {
            viewModel.isLoadingLiveData.observe(it) { isLoading ->
                if (isLoading) {
                    bind.progressBar.visibility = View.VISIBLE
                } else {
                    bind.progressBar.visibility = View.GONE
                }
            }
        }

        activity?.let {
            viewModel.onSuccessLiveData.observe(it) { list ->
                adapter.addList(list)
            }
        }

        activity?.let {
            viewModel.onFailLiveData.observe(it) { message ->
                noticeToast(message)
            }
        }
    }

    private fun setRecyclerView() {
        bind.rvWhatsTrending.isNestedScrollingEnabled = false
        layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        bind.rvWhatsTrending.layoutManager = layoutManager
        adapter =
                TvMovieAdapter(
                        listTrending
                )
        bind.rvWhatsTrending.adapter = adapter

        adapter.setOnItemClickCallback(object :
            TvMovieAdapter.OnItemClickCallback {
            override fun onClickItem(data: TvMovieModel) {
                when (data.mediaType) {
                    "movie" -> {
                        val intent = Intent(activity, DetailMovieActivity::class.java)
                        intent.putExtra("movie_id", data.id)
                        startActivity(intent)
                    }
                    "tv" -> {
                        val intent = Intent(activity, DetailTvActivity::class.java)
                        intent.putExtra("tv_id", data.id)
                        startActivity(intent)
                    }
                    else -> {
                        noticeToast("Error")
                    }
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllTrendingByWeek()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllTrendingByWeek()
    }
}