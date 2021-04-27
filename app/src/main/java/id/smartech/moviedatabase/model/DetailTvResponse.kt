package id.smartech.moviedatabase.model

import com.google.gson.annotations.SerializedName

data class DetailTvResponse (
        val id: Int,
        val name: String,
        @SerializedName("first_air_date") val firstAirDate: String,
        val tagline: String,
        @SerializedName("vote_average") val voteAverage: Double,
        @SerializedName("vote_count") val voteCount: Int,
        @SerializedName("backdrop_path") val backdropPath: String?,
        @SerializedName("poster_path") val posterPath: String?,
        val overview: String,
        val status: String,
        val type: String,
        @SerializedName("original_language") val originalLanguage: String,
        val genres: ArrayList<GenresModel>,
        val homepage: String
)