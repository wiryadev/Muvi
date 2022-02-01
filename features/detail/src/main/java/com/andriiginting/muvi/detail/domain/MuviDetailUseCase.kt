package com.andriiginting.muvi.detail.domain

import com.andriiginting.core_network.DetailsMovieData
import com.andriiginting.core_network.MovieItem
import com.andriiginting.muvi.detail.data.MuviDetailRepository
import com.andriiginting.uttils.maybeIo
import com.andriiginting.uttils.singleIo
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface MuviDetailUseCase {
    fun getDetailMovies(movieId: String): Single<DetailsMovieData>
    fun storeToDatabase(data: MovieItem): Single<Long>
    fun removeFromDatabase(movieId: String): Single<Unit>
    fun checkFavoriteMovie(movieId: String): Maybe<MovieItem>
}

class MuviDetailUseCaseImpl @Inject constructor(
    private val repository: MuviDetailRepository,
    private val mapper: MuviDetailMapper
) : MuviDetailUseCase {

    override fun getDetailMovies(movieId: String): Single<DetailsMovieData> {
        return Single.zip(
            repository.getDetailMovie(movieId),
            repository.getSimilarMovie(movieId).map { it.resultsIntent }
        ) { movieItem, similarMovies ->
            DetailsMovieData(similarMovies, movieItem)
        }
    }

    override fun storeToDatabase(data: MovieItem): Single<Long> {
        val movieEntity = mapper.mapToMuviFavorite(data)
        return repository.storeToDatabase(movieEntity)
    }

    override fun removeFromDatabase(movieId: String): Single<Unit> {
        return repository.removeFromDatabase(movieId)
            .compose(singleIo())
    }

    override fun checkFavoriteMovie(movieId: String): Maybe<MovieItem> {
        return repository.isFavoriteMovie(movieId.toInt())
            .map(mapper::mapToMovieItem)
            .compose(maybeIo())
    }
}