package id.smartech.moviedatabase.activity.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.smartech.moviedatabase.model.*
import id.smartech.moviedatabase.services.ApiServices
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MovieViewModel: ViewModel(), CoroutineScope {
    private lateinit var services: ApiServices

    val onSuccessPopularLiveData = MutableLiveData<List<MovieModel>>()
    val onSuccessNowPlayingLiveData = MutableLiveData<List<MovieNowPlayingModel>>()
    val onSuccessTopRatedLiveData = MutableLiveData<List<MovieTopRatedModel>>()
    val onSuccessUpcomingLiveData = MutableLiveData<List<MovieUpcomingModel>>()
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
                onSuccessPopularLiveData.value = list
            } else {
                isLoadingLiveData.value = false
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getNowPlayingMovie() {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getNowPlayingMovie()
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
                    MovieNowPlayingModel(
                            id = it.id,
                            title = it.title,
                            posterPath = it.posterPath,
                            releaseDate = it.releaseDate,
                            voteAverage = it.voteAverage,
                            voteCount = it.voteCount
                    )
                }
                onSuccessNowPlayingLiveData.value = list
            } else {
                isLoadingLiveData.value = false
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getTopRatedMovie() {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getTopRatedMovie()
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
                    MovieTopRatedModel(
                            id = it.id,
                            title = it.title,
                            posterPath = it.posterPath,
                            releaseDate = it.releaseDate,
                            voteAverage = it.voteAverage,
                            voteCount = it.voteCount
                    )
                }
                onSuccessTopRatedLiveData.value = list
            } else {
                isLoadingLiveData.value = false
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getUpcomingMovie() {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getUpcomingMovie()
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
                    MovieUpcomingModel(
                            id = it.id,
                            title = it.title,
                            posterPath = it.posterPath,
                            releaseDate = it.releaseDate,
                            voteAverage = it.voteAverage,
                            voteCount = it.voteCount
                    )
                }
                onSuccessUpcomingLiveData.value = list
            } else {
                isLoadingLiveData.value = false
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }
}