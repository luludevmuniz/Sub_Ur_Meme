package com.alpaca.telalogin.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
//@PrimaryKey(autoGenerate = true)
@Entity(tableName = "Usuarios")
public data class Usuario(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "login") val login: String,
    @ColumnInfo(name = "senha") val senha: String
)