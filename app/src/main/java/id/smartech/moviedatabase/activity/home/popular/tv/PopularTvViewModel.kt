package id.smartech.moviedatabase.activity.home.popular.tv

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.smartech.moviedatabase.model.TvModel
import id.smartech.moviedatabase.model.TvMovieModel
import id.smartech.moviedatabase.model.TvMovieResponse
import id.smartech.moviedatabase.model.TvResponse
import id.smartech.moviedatabase.services.ApiServices
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PopularTvViewModel: ViewModel(), CoroutineScope {
    private lateinit var services: ApiServices

    val onSuccessLiveData = MutableLiveData<List<TvModel>>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(services: ApiServices) {
        this.services = services
    }

    fun getPopularTV() {
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
                onSuccessLiveData.value = list
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }
}