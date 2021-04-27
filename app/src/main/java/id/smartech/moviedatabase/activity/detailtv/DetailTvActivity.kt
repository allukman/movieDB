package id.smartech.moviedatabase.activity.detailtv

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import id.smartech.moviedatabase.databinding.ActivityDetailTvBinding
import id.smartech.moviedatabase.model.CastModel
import id.smartech.moviedatabase.model.GenresModel
import id.smartech.moviedatabase.model.PeopleModel
import id.smartech.moviedatabase.model.TvModel
import id.smartech.moviedatabase.remote.ApiClient
import id.smartech.moviedatabase.util.adapter.GenresAdapter
import id.smartech.moviedatabase.util.adapter.MovieCasterAdapter
import id.smartech.moviedatabase.util.adapter.PeopleAdapter
import id.smartech.moviedatabase.util.adapter.TvAdapter

class DetailTvActivity : BaseActivity<ActivityDetailTvBinding>() {
    private lateinit var viewModel: DetailTvViewModel
    private lateinit var adapterGenres: GenresAdapter
    private lateinit var adapterCast: MovieCasterAdapter
    private lateinit var adapterRecommendation: TvAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private var listTv = ArrayList<TvModel>()
    private var listCast = ArrayList<CastModel>()
    private val listGenres = ArrayList<GenresModel>()
    private var linkTrailer: String? = null
    private var linkHomepage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_detail_tv
        super.onCreate(savedInstanceState)

        setViewModel(intent.getIntExtra("tv_id", 0))
        subscribeLiveData()
        setGenresRecyclerView()
        setCastRecyclerView()
        setRecommendationsRecyclerView()
        setOnClick()
    }

    private fun setViewModel(tvId: Int) {
        viewModel = ViewModelProvider(this).get(DetailTvViewModel::class.java)
        viewModel.setService(createApi(this))
        viewModel.getDetailTv(tvId)
        viewModel.getVideosTv(tvId)
        viewModel.getCastTv(tvId)
        viewModel.getRecommendationTv(tvId)
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
            viewModel.onSuccessGenresLiveData.observe(it) { list ->
                adapterGenres.addList(list)
            }
        }

        this.let {
            viewModel.onSuccessCastLiveData.observe(it) { list ->
                adapterCast.addList(list)
            }
        }

        this.let {
            viewModel.onSuccessRecommendationLiveData.observe(it) { list ->
                adapterRecommendation.addList(list)
            }
        }

        this.let {
            viewModel.onSuccessLiveData.observe(it) { list ->
                setData(
                        title = list.name,
                        year = list.firstAirDate,
                        tagLine = list.tagline,
                        voteAverage = list.voteAverage,
                        voteCount = list.voteCount,
                        backdropPath = list.backdropPath,
                        posterPath = list.posterPath,
                        status = list.status,
                        overView = list.overview,
                        type = list.type,
                        originalLanguage = list.originalLanguage,
                        homepage = list.homepage
                )

                linkHomepage = list.homepage
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
    private fun setData(title: String, year: String, tagLine: String, voteAverage: Double, voteCount: Int, backdropPath: String?,
                        posterPath: String?, overView: String, status: String, type: String, originalLanguage: String, homepage: String?) {

        val poster = ApiClient.BASE_URL_IMAGE + posterPath
        val backdrop = ApiClient.BASE_URL_IMAGE + backdropPath

        bind.tvTitle.text = title
        bind.tvYear.text = "($year)"
        bind.tvVoteAverage.text = voteAverage.toString()
        bind.tvTagline.text = tagLine
        bind.tvVoteCount.text = "($voteCount)"
        bind.tvOverview.text = overView
        bind.tvStatus.text = status
        bind.tvType.text = type
        bind.tvOriginalLanguage.text = originalLanguage

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
                val intent = Intent(this@DetailTvActivity, DetailPersonActivity::class.java)
                intent.putExtra("person_id", data.id)
                startActivity(intent)
            }
        })
    }

    private fun setRecommendationsRecyclerView() {
        bind.rvRecommendationTv.isNestedScrollingEnabled = false
        layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        bind.rvRecommendationTv.layoutManager = layoutManager
        adapterRecommendation =
                TvAdapter(
                        listTv
                )
        bind.rvRecommendationTv.adapter = adapterRecommendation

        adapterRecommendation.setOnItemClickCallback(object :
               TvAdapter.OnItemClickCallback {
            override fun onClickItem(data: TvModel) {
                val intent = Intent(this@DetailTvActivity, DetailTvActivity::class.java)
                intent.putExtra("tv_id", data.id)
                startActivity(intent)
            }
        })
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
}