package com.andriiginting.core_network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.reactivex.rxjava3.core.Single
import kotlinx.android.parcel.Parcelize
import retrofit2.http.GET

interface MuviHomeService {
    @GET("movie/popular")
    fun getPopularMovies(): Single<MovieResponse>

    @GET("movie/latest")
    fun getLatestMovies(): Single<MovieResponse>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(): Single<MovieResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(): Single<MovieResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(): Single<MovieResponse>
}

@Parcelize
data class MovieResponse(
    @SerializedName("results") var resultsIntent: List<MovieItem> = mutableListOf()
) : BaseResponse(), Parcelable

@Parcelize
data class MovieItem(
    @SerializedName("id") var id: String = "",
    @SerializedName("movie_id") var movieId: String = "",
    @SerializedName("original_title") var title: String = "",
    @SerializedName("poster_path") var posterPath: String? = "",
    @SerializedName("overview") var overview: String = "",
    @SerializedName("backdrop_path") var backdropPath: String? = "",
    @SerializedName("release_date") var releaseDate: String = ""
) : Parcelable {
    companion object {
        fun default(): MovieItem {
            return MovieItem()
        }
    }
}

@Parcelize
data class DetailsMovieData(
    val similarMovies: List<MovieItem> = emptyList(),
    val movie: MovieItem
):Parcelable

@Parcelize
data class HomeBannerData(val movie: MovieItem):Parcelable