package id.smartech.moviedatabase.model

import com.google.gson.annotations.SerializedName

data class MovieRecommendationsModel (
        val id: Int,
        val title: String,
        @SerializedName("poster_path") val posterPath: String?,
        @SerializedName("release_date") val releaseDate: String?,
        @SerializedName("vote_count") val voteCount: Int,
        @SerializedName("vote_average") val voteAverage: Double
)