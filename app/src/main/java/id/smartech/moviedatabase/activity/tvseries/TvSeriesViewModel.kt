package id.smartech.moviedatabase.activity.tvseries

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.smartech.moviedatabase.model.*
import id.smartech.moviedatabase.services.ApiServices
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class TvSeriesViewModel: ViewModel(), CoroutineScope {
    private lateinit var services: ApiServices

    val onSuccessPopularLiveData = MutableLiveData<List<TvModel>>()
    val onSuccessAiringTodayLiveData = MutableLiveData<List<TvAiringTodayModel>>()
    val onSuccessOnTheAirLiveData = MutableLiveData<List<TvOnTheAirModel>>()
    val onSuccessTopRatedLiveData = MutableLiveData<List<TvTopRatedModel>>()

    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(services: ApiServices) {
        this.services = services
    }

    fun getPopularTvSeries() {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getPopularTV()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is TvResponse) {
                isLoadingLiveData.value = false

                val list = response.results.map {
                    TvModel(
                            id = it.id,
                            name = it.name,
                            posterPath = it.posterPath,
                            firstAirDate = it.firstAirDate,
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

    fun getAiringTodayTvSeries() {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getAiringTodayTV()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is TvResponse) {
                isLoadingLiveData.value = false

                val list = response.results.map {
                    TvAiringTodayModel(
                            id = it.id,
                            name = it.name,
                            posterPath = it.posterPath,
                            firstAirDate = it.firstAirDate,
                            voteAverage = it.voteAverage,
                            voteCount = it.voteCount
                    )
                }
                onSuccessAiringTodayLiveData.value = list
            } else {
                isLoadingLiveData.value = false
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getOnTheAirTvSeries() {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getOnTheAirTV()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is TvResponse) {
                isLoadingLiveData.value = false

                val list = response.results.map {
                    TvOnTheAirModel(
                            id = it.id,
                            name = it.name,
                            posterPath = it.posterPath,
                            firstAirDate = it.firstAirDate,
                            voteAverage = it.voteAverage,
                            voteCount = it.voteCount
                    )
                }
                onSuccessOnTheAirLiveData.value = list
            } else {
                isLoadingLiveData.value = false
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getTopRatedTvSeries() {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getTopRatedTV()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is TvResponse) {
                isLoadingLiveData.value = false

                val list = response.results.map {
                    TvTopRatedModel(
                            id = it.id,
                            name = it.name,
                            posterPath = it.posterPath,
                            firstAirDate = it.firstAirDate,
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
}