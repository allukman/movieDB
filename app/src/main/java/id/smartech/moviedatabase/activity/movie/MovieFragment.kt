package id.smartech.moviedatabase.activity.movie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.smartech.moviedatabase.R
import id.smartech.moviedatabase.activity.detailmovie.DetailMovieActivity
import id.smartech.moviedatabase.base.BaseFragment
import id.smartech.moviedatabase.databinding.FragmentMovieBinding
import id.smartech.moviedatabase.model.MovieModel
import id.smartech.moviedatabase.model.MovieNowPlayingModel
import id.smartech.moviedatabase.model.MovieTopRatedModel
import id.smartech.moviedatabase.model.MovieUpcomingModel
import id.smartech.moviedatabase.util.adapter.MovieAdapter
import id.smartech.moviedatabase.util.adapter.MovieNowPlayingAdapter
import id.smartech.moviedatabase.util.adapter.MovieTopRatedAdapter
import id.smartech.moviedatabase.util.adapter.MovieUpcomingAdapter


class MovieFragment : BaseFragment<FragmentMovieBinding>() {
    private lateinit var viewModel: MovieViewModel
    private lateinit var adapterPopuler: MovieAdapter
    private lateinit var adapterNowPlaying: MovieNowPlayingAdapter
    private lateinit var adapterTopRated: MovieTopRatedAdapter
    private lateinit var adapterUpcoming: MovieUpcomingAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private var listMovie = ArrayList<MovieModel>()
    private var listMovieNowPlaying = ArrayList<MovieNowPlayingModel>()
    private var listMovieTopRated = ArrayList<MovieTopRatedModel>()
    private var listMovieUpcoming = ArrayList<MovieUpcomingModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_movie
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPopularRecyclerView()
        setNowPlayingRecyclerView()
        setTopRatedRecyclerView()
        setUpcomingRecyclerView()
        setViewModel()
        subscribeLiveData()
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        viewModel.setService(createApi(activity))
        viewModel.getPopularMovie()
        viewModel.getNowPlayingMovie()
        viewModel.getTopRatedMovie()
        viewModel.getUpcomingMovie()
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
                adapterPopuler.addList(list)
            }
        }

        activity?.let {
            viewModel.onSuccessNowPlayingLiveData.observe(it) { list ->
                adapterNowPlaying.addList(list)
            }
        }

        activity?.let {
            viewModel.onSuccessTopRatedLiveData.observe(it) { list ->
                adapterTopRated.addList(list)
            }
        }

        activity?.let {
            viewModel.onSuccessUpcomingLiveData.observe(it) { list ->
                adapterUpcoming.addList(list)
            }
        }

        activity?.let {
            viewModel.onFailLiveData.observe(it) { message ->
                noticeToast(message)
            }
        }
    }

    private fun setPopularRecyclerView() {
        bind.rvPopularMovie.isNestedScrollingEnabled = false
        layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        bind.rvPopularMovie.layoutManager = layoutManager
        adapterPopuler =
                MovieAdapter(
                        listMovie
                )
        bind.rvPopularMovie.adapter = adapterPopuler

        adapterPopuler.setOnItemClickCallback(object :
            MovieAdapter.OnItemClickCallback {
            override fun onClickItem(data: MovieModel) {
                val intent = Intent(activity, DetailMovieActivity::class.java)
                intent.putExtra("movie_id", data.id)
                startActivity(intent)
            }
        })
    }

    private fun setNowPlayingRecyclerView() {
        bind.rvNowPlayingMovie.isNestedScrollingEnabled = false
        layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        bind.rvNowPlayingMovie.layoutManager = layoutManager
        adapterNowPlaying =
                MovieNowPlayingAdapter(
                        listMovieNowPlaying
                )
        bind.rvNowPlayingMovie.adapter = adapterNowPlaying

        adapterNowPlaying.setOnItemClickCallback(object :
                MovieNowPlayingAdapter.OnItemClickCallback {
            override fun onClickItem(data: MovieNowPlayingModel) {
                val intent = Intent(activity, DetailMovieActivity::class.java)
                intent.putExtra("movie_id", data.id)
                startActivity(intent)
            }
        })
    }

    private fun setTopRatedRecyclerView() {
        bind.rvTopRatedMovie.isNestedScrollingEnabled = false
        layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        bind.rvTopRatedMovie.layoutManager = layoutManager
        adapterTopRated =
                MovieTopRatedAdapter(
                        listMovieTopRated
                )
        bind.rvTopRatedMovie.adapter = adapterTopRated

        adapterTopRated.setOnItemClickCallback(object :
                MovieTopRatedAdapter.OnItemClickCallback {
            override fun onClickItem(data: MovieTopRatedModel) {
                val intent = Intent(activity, DetailMovieActivity::class.java)
                intent.putExtra("movie_id", data.id)
                startActivity(intent)
            }
        })
    }

    private fun setUpcomingRecyclerView() {
        bind.rvUpcomingMovie.isNestedScrollingEnabled = false
        layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        bind.rvUpcomingMovie.layoutManager = layoutManager
        adapterUpcoming =
                MovieUpcomingAdapter(
                        listMovieUpcoming
                )
        bind.rvUpcomingMovie.adapter = adapterUpcoming

        adapterUpcoming.setOnItemClickCallback(object :
                MovieUpcomingAdapter.OnItemClickCallback {
            override fun onClickItem(data: MovieUpcomingModel) {
                val intent = Intent(activity, DetailMovieActivity::class.java)
                intent.putExtra("movie_id", data.id)
                startActivity(intent)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.getPopularMovie()
        viewModel.getNowPlayingMovie()
        viewModel.getTopRatedMovie()
        viewModel.getUpcomingMovie()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getPopularMovie()
        viewModel.getNowPlayingMovie()
        viewModel.getTopRatedMovie()
        viewModel.getUpcomingMovie()
    }

}