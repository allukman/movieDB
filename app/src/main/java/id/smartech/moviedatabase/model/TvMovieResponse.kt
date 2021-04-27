package id.smartech.moviedatabase.model

data class TvMovieResponse (
    val page: Int,
    val results: ArrayList<TvMovieModel>
)