package id.smartech.moviedatabase.activity.detailtv

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.smartech.moviedatabase.model.*
import id.smartech.moviedatabase.services.ApiServices
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DetailTvViewModel: ViewModel(), CoroutineScope {
    private lateinit var services: ApiServices

    val onSuccessLiveData = MutableLiveData<DetailTvResponse>()
    val onSuccessCastLiveData = MutableLiveData<List<CastModel>>()
    val onSuccessGenresLiveData = MutableLiveData<List<GenresModel>>()
    val onSuccessRecommendationLiveData = MutableLiveData<List<TvModel>>()
    val onSuccessGetTrailerLiveData = MutableLiveData<String>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(services: ApiServices) {
        this.services = services
    }

    fun getDetailTv(tvId: Int) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getDetailTv(tvId)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is DetailTvResponse) {
                isLoadingLiveData.value = false
                onSuccessLiveData.value = response
                onSuccessGenresLiveData.value = response.genres
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getCastTv(tvId: Int) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getTvCredits(tvId)
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

    fun getRecommendationTv(tvId: Int) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getTvRecommendations(tvId)
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
                onSuccessRecommendationLiveData.value = list
            } else {
                isLoadingLiveData.value = false
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getVideosTv(tvId: Int) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getTvVideos(tvId)
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

                Log.d("zenosyne", response.results?.size.toString())

            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }
}