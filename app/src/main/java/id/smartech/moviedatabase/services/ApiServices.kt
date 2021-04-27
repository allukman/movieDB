package id.smartech.moviedatabase.services

import id.smartech.moviedatabase.model.*
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {

//      GET TRENDING
    @GET("trending/all/week?api_key=9aa507699ad0072e9588c8e3c9be6435")
    suspend fun getTrendingByWeek(): TvMovieResponse

    @GET("trending/all/day?api_key=9aa507699ad0072e9588c8e3c9be6435")
    suspend fun getTrendingByDay(): TvMovieResponse

//      GET POPULAR
    @GET("tv/popular?api_key=9aa507699ad0072e9588c8e3c9be6435")
    suspend fun getPopularTV(): TvResponse

    @GET("movie/popular?api_key=9aa507699ad0072e9588c8e3c9be6435")
    suspend fun getPopularMovie(): MovieResponse

    @GET("person/popular?api_key=9aa507699ad0072e9588c8e3c9be6435")
    suspend fun getPopularPeople(): PeopleResponse

//      GET NOW PLAYING MOVIE
    @GET("movie/now_playing?api_key=9aa507699ad0072e9588c8e3c9be6435")
    suspend fun getNowPlayingMovie(): MovieResponse

//      GET TOP RATED MOVIE
    @GET("movie/top_rated?api_key=9aa507699ad0072e9588c8e3c9be6435")
    suspend fun getTopRatedMovie(): MovieResponse

//      GET UPCOMING MOVIE
    @GET("movie/upcoming?api_key=9aa507699ad0072e9588c8e3c9be6435")
    suspend fun getUpcomingMovie(): MovieResponse

//      GET AIRING TODAY TV SERIES
    @GET("tv/airing_today?api_key=9aa507699ad0072e9588c8e3c9be6435")
    suspend fun getAiringTodayTV(): TvResponse

//      GET ON THE AIR TV SERIES
    @GET("tv/on_the_air?api_key=9aa507699ad0072e9588c8e3c9be6435")
    suspend fun getOnTheAirTV(): TvResponse

//      GET TOP RATED TV SERIES
    @GET("tv/top_rated?api_key=9aa507699ad0072e9588c8e3c9be6435")
    suspend fun getTopRatedTV(): TvResponse

//      GET DETAIL MOVIE
    @GET("movie/{movieId}?api_key=9aa507699ad0072e9588c8e3c9be6435")
    suspend fun getDetailMovie(@Path("movieId")movieId: Int): DetailMovieResponse

//      GET MOVIE CREDITS
    @GET("movie/{movieId}/credits?api_key=9aa507699ad0072e9588c8e3c9be6435")
    suspend fun getMovieCredits(@Path("movieId")movieId: Int): CreditsResponse

//      GET MOVIE RECOMMENDATIONS
    @GET("movie/{movieId}/recommendations?api_key=9aa507699ad0072e9588c8e3c9be6435")
    suspend fun getMovieRecommendations(@Path("movieId")movieId: Int): MovieResponse

//      GET MOVIE VIDEOS
    @GET("movie/{movieId}/videos?api_key=9aa507699ad0072e9588c8e3c9be6435")
    suspend fun getMovieVideos(@Path("movieId")movieId: Int): VideoResponse

//      GET DETAIL MOVIE
    @GET("tv/{tvId}?api_key=9aa507699ad0072e9588c8e3c9be6435")
    suspend fun getDetailTv(@Path("tvId")tvId: Int): DetailTvResponse

//      GET MOVIE CREDITS
    @GET("tv/{tvId}/credits?api_key=9aa507699ad0072e9588c8e3c9be6435")
    suspend fun getTvCredits(@Path("tvId")tvId: Int): CreditsResponse

//      GET TV VIDEOS
    @GET("tv/{tvId}/videos?api_key=9aa507699ad0072e9588c8e3c9be6435")
    suspend fun getTvVideos(@Path("tvId")tvId: Int): VideoResponse

//      GET TV RECOMMENDATIONS
    @GET("tv/{tvId}/recommendations?api_key=9aa507699ad0072e9588c8e3c9be6435")
    suspend fun getTvRecommendations(@Path("tvId")tvId: Int): TvResponse

//      GET DETAIL PERSON
    @GET("person/{personId}?api_key=9aa507699ad0072e9588c8e3c9be6435")
    suspend fun getDetailPerson(@Path("personId")personId: Int): DetailPersonResponse

//      GET CREDITS PERSON
    @GET("person/{personId}/combined_credits?api_key=9aa507699ad0072e9588c8e3c9be6435")
    suspend fun getPersonCredit(@Path("personId")personId: Int): CombinedCreditsResponse
}