package com.thiishy.movlist.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmeDao {
    @Query("SELECT * FROM filme")
    fun getAllMovies(): Flow<List<Filme>>

    @Insert
    suspend fun insertMovie(filme: Filme)

    @Query("UPDATE filme SET ic_assistido_sim_nao = :status WHERE uid = :uid")
    suspend fun alterWatchedStatusByUid(status: Boolean, uid: Int)

    @Delete
    suspend fun removeMovie(filme: Filme)
}