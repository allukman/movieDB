package id.smartech.moviedatabase.activity.detailperson

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.smartech.moviedatabase.model.CombinedCreditsResponse
import id.smartech.moviedatabase.model.DetailPersonResponse
import id.smartech.moviedatabase.model.TvMovieModel
import id.smartech.moviedatabase.model.TvMovieResponse
import id.smartech.moviedatabase.services.ApiServices
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class DetailPersonViewModel: ViewModel(), CoroutineScope {
    private lateinit var services: ApiServices


    val onSuccessLiveData = MutableLiveData<DetailPersonResponse>()
    val onSuccessPersonCreditsLiveData = MutableLiveData<List<TvMovieModel>>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(services: ApiServices) {
        this.services = services
    }

    fun getDetailPerson(personId: Int) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getDetailPerson(personId)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is DetailPersonResponse) {
                isLoadingLiveData.value = false

                onSuccessLiveData.value = response
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getAllCredits(personId: Int) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getPersonCredit(personId)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is CombinedCreditsResponse) {
                isLoadingLiveData.value = false

                val list = response.cast.map {
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
                onSuccessPersonCreditsLiveData.value = list
                Log.d("berhasil", list.toString())
            } else {
                Log.d("gagal", "yhhaaa")
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }
}