package id.smartech.moviedatabase.remote

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        const val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500"

        private fun provideHttpLoggingInterceptor() = run {
            HttpLoggingInterceptor().apply {
                apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            }
        }

        fun getApiClient(context: Context): Retrofit {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(provideHttpLoggingInterceptor())
                .addInterceptor(HeaderInterceptor(context))
                .connectTimeout(15, TimeUnit.MINUTES)
                .readTimeout(15, TimeUnit.MINUTES)
                .writeTimeout(15, TimeUnit.MINUTES)
                .callTimeout(30, TimeUnit.MINUTES)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}