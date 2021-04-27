package id.smartech.moviedatabase.model

import com.google.gson.annotations.SerializedName

data class TvAiringTodayModel (
        val id: Int?,
        val name: String?,
        @SerializedName("poster_path") val posterPath: String?,
        @SerializedName("first_air_date") val firstAirDate: String?,
        @SerializedName("vote_count") val voteCount: Int?,
        @SerializedName("vote_average") val voteAverage: Double?
)