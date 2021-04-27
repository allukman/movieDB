package id.smartech.moviedatabase.model

data class MovieResponse (
        val page: Int,
        val results: ArrayList<MovieModel>
)