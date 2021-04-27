package id.smartech.moviedatabase.activity.home.popular.movie

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.smartech.moviedatabase.model.MovieModel
import id.smartech.moviedatabase.model.MovieResponse
import id.smartech.moviedatabase.model.TvMovieModel
import id.smartech.moviedatabase.model.TvMovieResponse
import id.smartech.moviedatabase.services.ApiServices
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PopularMovieViewModel: ViewModel(), CoroutineScope {
    private lateinit var services: ApiServices

    val onSuccessLiveData = MutableLiveData<List<MovieModel>>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(services: ApiServices) {
        this.services = services
    }

    fun getPopularMovie() {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getPopularMovie()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is MovieResponse) {
                isLoadingLiveData.value = false

                val list = response.results.map {
                    MovieModel(
                        id = it.id,
                        title = it.title,
                        posterPath = it.posterPath,
                        releaseDate = it.releaseDate,
                        voteAverage = it.voteAverage,
                        voteCount = it.voteCount
                    )
                }
                onSuccessLiveData.value = list
                Log.d("anuu", list.toString())
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }
}