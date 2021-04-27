package id.smartech.moviedatabase.activity.home.trending.byweek

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.smartech.moviedatabase.model.TvMovieModel
import id.smartech.moviedatabase.model.TvMovieResponse
import id.smartech.moviedatabase.services.ApiServices
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class TrendingWeekViewModel: ViewModel(), CoroutineScope {
    private lateinit var services: ApiServices

    val onSuccessLiveData = MutableLiveData<List<TvMovieModel>>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(services: ApiServices) {
        this.services = services
    }

    fun getAllTrendingByWeek() {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getTrendingByWeek()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is TvMovieResponse) {
                isLoadingLiveData.value = false

                val list = response.results.map {
                    TvMovieModel(
                        id = it.id,
                        name = it.name,
                        title = it.title,
                        posterPath = it.posterPath,
                        releaseDate = it.releaseDate,
                        firstAirDate = it.firstAirDate,
                        mediaType = it.mediaType,
                        voteAverage = it.voteAverage,
                        voteCount = it.voteCount
                    )
                }
                onSuccessLiveData.value = list
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }
}