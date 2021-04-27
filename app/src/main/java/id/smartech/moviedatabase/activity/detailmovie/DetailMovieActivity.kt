package id.smartech.moviedatabase.activity.detailmovie

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import id.smartech.moviedatabase.R
import id.smartech.moviedatabase.activity.MainActivity
import id.smartech.moviedatabase.activity.detailperson.DetailPersonActivity
import id.smartech.moviedatabase.base.BaseActivity
import id.smartech.moviedatabase.databinding.ActivityDetailMovieBinding
import id.smartech.moviedatabase.model.CastModel
import id.smartech.moviedatabase.model.GenresModel
import id.smartech.moviedatabase.model.MovieRecommendationsModel
import id.smartech.moviedatabase.remote.ApiClient
import id.smartech.moviedatabase.util.adapter.GenresAdapter
import id.smartech.moviedatabase.util.adapter.MovieCasterAdapter
import id.smartech.moviedatabase.util.adapter.MovieRecommendationsAdapter
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class DetailMovieActivity : BaseActivity<ActivityDetailMovieBinding>() {
    private lateinit var viewModel: DetailMovieViewModel
    private lateinit var adapterCast: MovieCasterAdapter
    private lateinit var adapterRecommendation: MovieRecommendationsAdapter
    private lateinit var adapterGenres: GenresAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private var listCast = ArrayList<CastModel>()
    private var listMovie = ArrayList<MovieRecommendationsModel>()
    private val listGenres = ArrayList<GenresModel>()
    private var linkTrailer: String? = null
    private var linkHomepage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_detail_movie
        super.onCreate(savedInstanceState)

        setViewModel(intent.getIntExtra("movie_id", 0))
        subscribeLiveData()
        setCastRecyclerView()
        setRecommendationsRecyclerView()
        setGenresRecyclerView()
        setOnClick()

    }

    private fun setOnClick(){
        bind.btnWatchTrailer.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$linkTrailer"))
            startActivity(browserIntent)
        }

        bind.btnHomepage.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(linkHomepage))
            startActivity(browserIntent)
        }
    }

    private fun setViewModel(movieId: Int) {
        viewModel = ViewModelProvider(this).get(DetailMovieViewModel::class.java)
        viewModel.setService(createApi(this))

        viewModel.getDetailMovie(movieId)
        viewModel.getCastMovie(movieId)
        viewModel.getRecommendationMovie(movieId)
        viewModel.getVideosMovie(movieId)
    }

    private fun subscribeLiveData() {
        this.let {
            viewModel.isLoadingLiveData.observe(it) { isLoading ->
                if (isLoading) {
                    bind.progressBar.visibility = View.VISIBLE
                } else {
                    bind.progressBar.visibility = View.GONE
                }
            }
        }

        this.let {
            viewModel.onSuccessLiveData.observe(it) { list ->

                setData(
                        title = list.title,
                        years = list.releaseDate,
                        runtime = list.runtime,
                        tagLine = list.tagline,
                        voteAverage = list.voteAverage,
                        overView = list.overview,
                        posterPath = list.posterPath,
                        backdropPath = list.backdropPath,
                        oriTitle = list.originalTitle,
                        oriLang = list.originalLanguage,
                        status = list.status,
                        budget = list.budget,
                        revenue = list.revenue,
                        voteCount = list.voteCount,
                        homepage = list.homepage
                )

                linkHomepage = list.homepage

            }
        }

        this.let {
            viewModel.onSuccessCastLiveData.observe(it) { list ->
                adapterCast.addList(list)
            }
        }

        this.let {
            viewModel.onSuccessGenresLiveData.observe(it) { list ->
                adapterGenres.addList(list)
            }
        }

        this.let {
            viewModel.onSuccessRecommendationLiveData.observe(it) { list ->
                adapterRecommendation.addList(list)
            }
        }

        this.let {
            viewModel.onSuccessGetTrailerLiveData.observe(it) { list ->
                if (list == "empty") {
                    bind.btnWatchTrailer.visibility = View.GONE
                } else {
                    bind.btnWatchTrailer.visibility = View.VISIBLE
                }
            }
        }

        this.let {
            viewModel.onFailLiveData.observe(it) { message ->
                noticeToast(message)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setData(title: String, years: String, runtime: Int, tagLine: String?, voteAverage: Double,
                        overView: String, posterPath: String?, backdropPath: String?, oriTitle: String,
                        oriLang: String, status: String, budget: Int, revenue: Int, voteCount: Int, homepage: String?) {
        val hours = runtime / 60
        val minutes = runtime % 60

        val time = hours.toString() + "h " + minutes.toString() + "m"
        val poster = ApiClient.BASE_URL_IMAGE + posterPath
        val backdrop = ApiClient.BASE_URL_IMAGE + backdropPath
        val year = years.split("-")[0]

        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance("USD")

        bind.tvTitle.text = title
        bind.tvYear.text = "($year)"
        bind.tvVoteAverage.text = voteAverage.toString()
        bind.tvTime.text = time
        bind.tvTagline.text = tagLine
        bind.tvOverview.text = overView
        bind.tvOriginalTitle.text = oriTitle
        bind.tvStatus.text = status
        bind.tvOriginalLanguage.text = oriLang
        bind.tvBudget.text = format.format(budget)
        bind.tvRevenue.text = format.format(revenue)
        bind.tvVoteCount.text = "($voteCount)"

        Glide.with(this)
                .load(poster)
                .placeholder(R.drawable.white)
                .error(R.drawable.white)
                .into(bind.ivPoster)

        Glide.with(this)
                .load(backdrop)
                .placeholder(R.drawable.white)
                .error(R.drawable.white)
                .into(bind.ivBackdrop)

        Log.d("homepage", homepage)
        if (homepage != "") {
            when {
                homepage!!.split("/")[2] == "www.netflix.com" -> {
                    bind.btnHomepage.text = "Watch on Netflix"
                }
                homepage.split("/")[2] == "www.disneyplus.com" -> {
                    bind.btnHomepage.text = "Watch on Hotstar"
                }
                else -> {
                    bind.btnHomepage.text = "Go to Homepage"
                }
            }
        } else {
            bind.btnHomepage.visibility = View.GONE
        }
    }

    private fun setCastRecyclerView() {
        bind.rvTopCastMovie.isNestedScrollingEnabled = false
        layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        bind.rvTopCastMovie.layoutManager = layoutManager
        adapterCast =
                MovieCasterAdapter(
                        listCast
                )
        bind.rvTopCastMovie.adapter = adapterCast

        adapterCast.setOnItemClickCallback(object :
                MovieCasterAdapter.OnItemClickCallback {
            override fun onClickItem(data: CastModel) {
                val intent = Intent(this@DetailMovieActivity, DetailPersonActivity::class.java)
                intent.putExtra("person_id", data.id)
                startActivity(intent)
            }
        })

    }

    private fun setGenresRecyclerView() {
        bind.rvGenresMovie.isNestedScrollingEnabled = false
        adapterGenres = GenresAdapter(listGenres)
        bind.rvGenresMovie.adapter = adapterGenres

        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        bind.rvGenresMovie.layoutManager = layoutManager

    }

    override fun onBackPressed() {
        intents<MainActivity>(this)
    }

    private fun setRecommendationsRecyclerView() {
        bind.rvRecommendationMovie.isNestedScrollingEnabled = false
        layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        bind.rvRecommendationMovie.layoutManager = layoutManager
        adapterRecommendation =
                MovieRecommendationsAdapter(
                        listMovie
                )
        bind.rvRecommendationMovie.adapter = adapterRecommendation

        adapterRecommendation.setOnItemClickCallback(object :
                MovieRecommendationsAdapter.OnItemClickCallback {
            override fun onClickItem(data: MovieRecommendationsModel) {
                val intent = Intent(this@DetailMovieActivity, DetailMovieActivity::class.java)
                intent.putExtra("movie_id", data.id)
                startActivity(intent)
            }
        })
    }

}