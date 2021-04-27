package id.smartech.moviedatabase.activity.home.popular.movie

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.smartech.moviedatabase.R
import id.smartech.moviedatabase.activity.detailmovie.DetailMovieActivity
import id.smartech.moviedatabase.util.adapter.TvMovieAdapter
import id.smartech.moviedatabase.base.BaseFragment
import id.smartech.moviedatabase.databinding.FragmentPopularMovieBinding
import id.smartech.moviedatabase.model.MovieModel
import id.smartech.moviedatabase.model.TvMovieModel
import id.smartech.moviedatabase.util.adapter.MovieAdapter

class PopularMovieFragment : BaseFragment<FragmentPopularMovieBinding>() {
    private lateinit var viewModel: PopularMovieViewModel
    private lateinit var adapter: MovieAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var listTrending = ArrayList<MovieModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_popular_movie
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setViewModel()
        subscribeLiveData()
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(PopularMovieViewModel::class.java)
        viewModel.setService(createApi(activity))
        viewModel.getPopularMovie()
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
        bind.rvPopularMovie.isNestedScrollingEnabled = false
        layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        bind.rvPopularMovie.layoutManager = layoutManager
        adapter =
                MovieAdapter(
                        listTrending
                )
        bind.rvPopularMovie.adapter = adapter

        adapter.setOnItemClickCallback(object :
            MovieAdapter.OnItemClickCallback {
            override fun onClickItem(data: MovieModel) {
                val intent = Intent(activity, DetailMovieActivity::class.java)
                intent.putExtra("movie_id", data.id)
                startActivity(intent)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.getPopularMovie()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getPopularMovie()
    }

}