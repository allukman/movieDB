package id.smartech.moviedatabase.activity.tvseries

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.smartech.moviedatabase.R
import id.smartech.moviedatabase.activity.detailtv.DetailTvActivity
import id.smartech.moviedatabase.base.BaseFragment
import id.smartech.moviedatabase.databinding.FragmentTvSeriesBinding
import id.smartech.moviedatabase.model.TvAiringTodayModel
import id.smartech.moviedatabase.model.TvModel
import id.smartech.moviedatabase.model.TvOnTheAirModel
import id.smartech.moviedatabase.model.TvTopRatedModel
import id.smartech.moviedatabase.util.adapter.TvAdapter
import id.smartech.moviedatabase.util.adapter.TvAiringTodayAdapter
import id.smartech.moviedatabase.util.adapter.TvOnTheAirAdapter
import id.smartech.moviedatabase.util.adapter.TvTopRatedAdapter

class TvSeriesFragment : BaseFragment<FragmentTvSeriesBinding>() {
    private lateinit var viewModel: TvSeriesViewModel
    private lateinit var adapterPopular: TvAdapter
    private lateinit var adapterAiringToday: TvAiringTodayAdapter
    private lateinit var adapterOnTheAir: TvOnTheAirAdapter
    private lateinit var adapterTopRated: TvTopRatedAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private var listTrending = ArrayList<TvModel>()
    private var listAiringToday = ArrayList<TvAiringTodayModel>()
    private var listTopRated = ArrayList<TvTopRatedModel>()
    private var listOnTheAir = ArrayList<TvOnTheAirModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_tv_series
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPopularRecyclerView()
        setAiringTodayRecyclerView()
        setTopRatedRecyclerView()
        setOnTheAirRecyclerView()
        setViewModel()
        subscribeLiveData()
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(TvSeriesViewModel::class.java)
        viewModel.setService(createApi(activity))
        viewModel.getPopularTvSeries()
        viewModel.getAiringTodayTvSeries()
        viewModel.getOnTheAirTvSeries()
        viewModel.getTopRatedTvSeries()
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
            viewModel.onSuccessPopularLiveData.observe(it) { list ->
                adapterPopular.addList(list)
            }
        }

        activity?.let {
            viewModel.onSuccessAiringTodayLiveData.observe(it) { list ->
                adapterAiringToday.addList(list)
            }
        }

        activity?.let {
            viewModel.onSuccessOnTheAirLiveData.observe(it) { list ->
                adapterOnTheAir.addList(list)
            }
        }

        activity?.let {
            viewModel.onSuccessTopRatedLiveData.observe(it) { list ->
                adapterTopRated.addList(list)
            }
        }

        activity?.let {
            viewModel.onFailLiveData.observe(it) { message ->
                noticeToast(message)
            }
        }
    }

    private fun setPopularRecyclerView() {
        bind.rvPopularTvSeries.isNestedScrollingEnabled = false
        layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        bind.rvPopularTvSeries.layoutManager = layoutManager
        adapterPopular =
                TvAdapter(
                        listTrending
                )
        bind.rvPopularTvSeries.adapter = adapterPopular

        adapterPopular.setOnItemClickCallback(object :
                TvAdapter.OnItemClickCallback {
            override fun onClickItem(data: TvModel) {
                val intent = Intent(activity, DetailTvActivity::class.java)
                intent.putExtra("tv_id", data.id)
                startActivity(intent)
            }
        })
    }

    private fun setAiringTodayRecyclerView() {
        bind.rvAiringTodayTv.isNestedScrollingEnabled = false
        layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        bind.rvAiringTodayTv.layoutManager = layoutManager
        adapterAiringToday =
                TvAiringTodayAdapter(
                        listAiringToday
                )
        bind.rvAiringTodayTv.adapter = adapterAiringToday

        adapterAiringToday.setOnItemClickCallback(object : TvAiringTodayAdapter.OnItemClickCallback {
            override fun onClickItem(data: TvAiringTodayModel) {
                val intent = Intent(activity, DetailTvActivity::class.java)
                intent.putExtra("tv_id", data.id)
                startActivity(intent)
            }
        })
    }

    private fun setTopRatedRecyclerView() {
        bind.rvTopRatedTv.isNestedScrollingEnabled = false
        layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        bind.rvTopRatedTv.layoutManager = layoutManager
        adapterTopRated =
                TvTopRatedAdapter(
                        listTopRated
                )
        bind.rvTopRatedTv.adapter = adapterTopRated

        adapterTopRated.setOnItemClickCallback(object : TvTopRatedAdapter.OnItemClickCallback {
            override fun onClickItem(data: TvTopRatedModel) {
                val intent = Intent(activity, DetailTvActivity::class.java)
                intent.putExtra("tv_id", data.id)
                startActivity(intent)
            }
        })
    }

    private fun setOnTheAirRecyclerView() {
        bind.rvOnTheAirTv.isNestedScrollingEnabled = false
        layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        bind.rvOnTheAirTv.layoutManager = layoutManager
        adapterOnTheAir =
                TvOnTheAirAdapter(
                        listOnTheAir
                )
        bind.rvOnTheAirTv.adapter = adapterOnTheAir

        adapterOnTheAir.setOnItemClickCallback(object : TvOnTheAirAdapter.OnItemClickCallback {
            override fun onClickItem(data: TvOnTheAirModel) {
                val intent = Intent(activity, DetailTvActivity::class.java)
                intent.putExtra("tv_id", data.id)
                startActivity(intent)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.getPopularTvSeries()
        viewModel.getAiringTodayTvSeries()
        viewModel.getOnTheAirTvSeries()
        viewModel.getTopRatedTvSeries()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getPopularTvSeries()
        viewModel.getAiringTodayTvSeries()
        viewModel.getOnTheAirTvSeries()
        viewModel.getTopRatedTvSeries()
    }


}