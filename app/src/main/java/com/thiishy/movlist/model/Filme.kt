package com.thiishy.movlist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Filme(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "nm_filme") val titulo: String,
    @ColumnInfo(name = "nm_genero_filme") val genero: String,
    @ColumnInfo(name = "ic_assistido_sim_nao") var assistido: Boolean = false,
    @ColumnInfo(name = "link_filme") var link: String,
    @ColumnInfo(name = "uri_poster_filme") var poster: String
)