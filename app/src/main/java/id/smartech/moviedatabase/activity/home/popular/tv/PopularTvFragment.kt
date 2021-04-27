package id.smartech.moviedatabase.activity.home.popular.tv

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.smartech.moviedatabase.R
import id.smartech.moviedatabase.activity.detailmovie.DetailMovieActivity
import id.smartech.moviedatabase.activity.detailtv.DetailTvActivity
import id.smartech.moviedatabase.util.adapter.TvMovieAdapter
import id.smartech.moviedatabase.base.BaseFragment
import id.smartech.moviedatabase.databinding.FragmentPopularTvBinding
import id.smartech.moviedatabase.model.TvModel
import id.smartech.moviedatabase.model.TvMovieModel
import id.smartech.moviedatabase.util.adapter.TvAdapter


class PopularTvFragment : BaseFragment<FragmentPopularTvBinding>() {
    private lateinit var viewModel: PopularTvViewModel
    private lateinit var adapter: TvAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var listTrending = ArrayList<TvModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_popular_tv
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setViewModel()
        subscribeLiveData()
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(PopularTvViewModel::class.java)
        viewModel.setService(createApi(activity))
        viewModel.getPopularTV()
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
        bind.rvPopularTv.isNestedScrollingEnabled = false
        layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        bind.rvPopularTv.layoutManager = layoutManager
        adapter =
                TvAdapter(
                        listTrending
                )
        bind.rvPopularTv.adapter = adapter

        adapter.setOnItemClickCallback(object :
            TvAdapter.OnItemClickCallback {
            override fun onClickItem(data: TvModel) {
                val intent = Intent(activity, DetailTvActivity::class.java)
                intent.putExtra("tv_id", data.id)
                startActivity(intent)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.getPopularTV()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getPopularTV()
    }

}