package id.smartech.moviedatabase.activity.detailperson

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.smartech.moviedatabase.R
import id.smartech.moviedatabase.activity.MainActivity
import id.smartech.moviedatabase.activity.detailmovie.DetailMovieActivity
import id.smartech.moviedatabase.activity.detailmovie.DetailMovieViewModel
import id.smartech.moviedatabase.activity.detailtv.DetailTvActivity
import id.smartech.moviedatabase.base.BaseActivity
import id.smartech.moviedatabase.databinding.ActivityDetailPersonBinding
import id.smartech.moviedatabase.model.TvMovieModel
import id.smartech.moviedatabase.remote.ApiClient
import id.smartech.moviedatabase.util.adapter.MovieCasterAdapter
import id.smartech.moviedatabase.util.adapter.TvMovieAdapter

class DetailPersonActivity : BaseActivity<ActivityDetailPersonBinding>() {
    private lateinit var viewModel: DetailPersonViewModel
    private lateinit var adapter: TvMovieAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private var listCredits = ArrayList<TvMovieModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_detail_person
        super.onCreate(savedInstanceState)

        setViewModel(intent.getIntExtra("person_id", 0))
        subscribeLiveData()
        setCastCreditsRecyclerView()
    }

    private fun setViewModel(personId: Int) {
        viewModel = ViewModelProvider(this).get(DetailPersonViewModel::class.java)
        viewModel.setService(createApi(this))

        viewModel.getDetailPerson(personId)
        viewModel.getAllCredits(personId)
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
                    name = list.name,
                gender = list.gender,
                popularity = list.popularity,
                birthday = list.birthday,
                placeOfBirth = list.placeOfBirth,
                posterPath = list.profilePath,
                biography = list.biography)
            }
        }

        this.let {
            viewModel.onSuccessPersonCreditsLiveData.observe(it) { list ->
                adapter.addList(list)
            }
        }

        this.let {
            viewModel.onFailLiveData.observe(it) { message ->
                noticeToast(message)
            }
        }
    }

    private fun setCastCreditsRecyclerView() {
        bind.rvCreditsMovie.isNestedScrollingEnabled = false
        layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        bind.rvCreditsMovie.layoutManager = layoutManager
        adapter =
                TvMovieAdapter(
                        listCredits
                )
        bind.rvCreditsMovie.adapter = adapter

        adapter.setOnItemClickCallback(object :
                TvMovieAdapter.OnItemClickCallback {
            override fun onClickItem(data: TvMovieModel) {

                when (data.mediaType) {
                    "movie" -> {
                        val intent = Intent(this@DetailPersonActivity, DetailMovieActivity::class.java)
                        intent.putExtra("movie_id", data.id)
                        startActivity(intent)
                    }
                    "tv" -> {
                        val intent = Intent(this@DetailPersonActivity, DetailTvActivity::class.java)
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

    private fun setData(name: String, gender: Int, popularity: Double, birthday : String?, placeOfBirth: String?, posterPath: String?, biography: String) {
        val poster = ApiClient.BASE_URL_IMAGE + posterPath

        when (gender) {
            1 -> {
                bind.tvGender.text = "Female"
            }
            2 -> {
                bind.tvGender.text = "Male"
            }
            else -> {
                bind.tvGender.text = "-"
            }
        }

        bind.tvTitle.text = name
        bind.tvPopularity.text = popularity.toString()
        bind.tvBirthday.text = birthday
        bind.tvPlaceOfBirth.text = placeOfBirth
        bind.tvBiography.text = biography

        Glide.with(this)
            .load(poster)
            .placeholder(R.drawable.white)
            .error(R.drawable.white)
            .into(bind.ivPoster)
    }

    override fun onBackPressed() {
        intents<MainActivity>(this)
    }
}