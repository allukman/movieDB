package id.smartech.moviedatabase.activity.detailmovie

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.smartech.moviedatabase.model.*
import id.smartech.moviedatabase.services.ApiServices
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DetailMovieViewModel: ViewModel(), CoroutineScope {
    private lateinit var services: ApiServices

    val onSuccessLiveData = MutableLiveData<DetailMovieResponse>()
    val onSuccessCastLiveData = MutableLiveData<List<CastModel>>()
    val onSuccessRecommendationLiveData = MutableLiveData<List<MovieRecommendationsModel>>()
    val onSuccessGenresLiveData = MutableLiveData<List<GenresModel>>()
    val onSuccessGetTrailerLiveData = MutableLiveData<String>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(services: ApiServices) {
        this.services = services
    }

    fun getDetailMovie(movieId: Int) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getDetailMovie(movieId)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is DetailMovieResponse) {
                isLoadingLiveData.value = false
                onSuccessLiveData.value = response
                onSuccessGenresLiveData.value = response.genres
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getCastMovie(movieId: Int) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getMovieCredits(movieId)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is CreditsResponse) {
                isLoadingLiveData.value = false

                val list = response.cast.map {
                    CastModel(
                            id = it.id,
                            name = it.name,
                            character = it.character,
                            knownForDepartment = it.knownForDepartment,
                            profilePath = it.profilePath

                    )
                }

                onSuccessCastLiveData.value = list
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getRecommendationMovie(movieId: Int) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getMovieRecommendations(movieId)
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
                    MovieRecommendationsModel(
                            id = it.id,
                            title = it.title,
                            posterPath = it.posterPath,
                            releaseDate = it.releaseDate,
                            voteAverage = it.voteAverage,
                            voteCount = it.voteCount
                    )
                }
                onSuccessRecommendationLiveData.value = list
            } else {
                isLoadingLiveData.value = false
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getVideosMovie(movieId: Int) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getMovieVideos(movieId)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is VideoResponse) {
                isLoadingLiveData.value = false

                val list = response.results?.map {
                    VideoModel(
                            id = it.id,
                            key = it.key,
                            name = it.name
                    )
                }

                if (response.results?.size!! >= 1) {
                    Log.d("zenosyne", "bener")
                    onSuccessGetTrailerLiveData.value = list?.get(0)?.key.toString()
                } else {
                    Log.d("zenosyne", "salah")
                    onSuccessGetTrailerLiveData.value = "empty"
                }

            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }
}