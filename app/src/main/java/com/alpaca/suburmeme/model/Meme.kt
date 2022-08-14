package com.alpaca.suburmeme.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Memes")
data class Meme(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "nome") val name: String,
    @ColumnInfo(name = "urlImagem") val url: String,
    @ColumnInfo(name = "largura") val width: Int,
    @ColumnInfo(name = "altura") val height: Int,
    @ColumnInfo(name = "numeroCaixas" )val boxCount: Int
)