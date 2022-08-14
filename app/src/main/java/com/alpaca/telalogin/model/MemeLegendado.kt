package com.alpaca.telalogin.model

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "MemeLegendado")
data class MemeLegendado(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "urlImagem") val url: String,
    @ColumnInfo(name = "urlPagina") val page_url: String,
    @ColumnInfo(name = "bitmap") val bitmap: Bitmap?
) : Parcelable