package id.smartech.moviedatabase.model

import com.google.gson.annotations.SerializedName

data class DetailMovieResponse (
     val id: Int,
     val title: String,
     @SerializedName("release_date") val releaseDate: String,
     val runtime: Int,
     val tagline: String?,
     @SerializedName("vote_count") val voteCount: Int,
     @SerializedName("vote_average") val voteAverage: Double,
     val overview: String,
     val homepage: String?,
     @SerializedName("backdrop_path") val backdropPath: String?,
     @SerializedName("poster_path") val posterPath: String?,
     @SerializedName("original_title") val originalTitle: String,
     @SerializedName("original_language") val originalLanguage: String,
     val status: String,
     val budget: Int,
     val revenue: Int,
     val genres: ArrayList<GenresModel>

)