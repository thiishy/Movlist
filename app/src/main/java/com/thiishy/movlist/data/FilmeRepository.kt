package com.thiishy.movlist.data

import com.thiishy.movlist.model.AppDatabase
import com.thiishy.movlist.model.Filme
import kotlinx.coroutines.flow.Flow

class FilmeRepository(private val db: AppDatabase) {
    fun getAllMovies(): Flow<List<Filme>> = db.filmeDao().getAllMovies()
    suspend fun insertMovie(filme: Filme) = db.filmeDao().insertMovie(filme)
    suspend fun alterWatchedStatusByUid(id: Int, assistido: Boolean) =
        db.filmeDao().alterWatchedStatusByUid(assistido, id)

    suspend fun removeMovie(filme: Filme) =
        db.filmeDao().removeMovie(filme)
}