package id.smartech.moviedatabase.model

import com.google.gson.annotations.SerializedName

data class TvMovieModel (
    val id: Int,
    val title: String?,
    val name: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("first_air_date") val firstAirDate: String?,
    @SerializedName("media_type") val mediaType: String,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("vote_average") val voteAverage: Double
)