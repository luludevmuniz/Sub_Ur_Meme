package com.alpaca.telalogin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alpaca.telalogin.db.dao.MemeDao
import com.alpaca.telalogin.model.Meme
import com.alpaca.telalogin.model.MemeLegendado

@Database(
    entities = [Meme::class, MemeLegendado::class],
    version = 4, exportSchema = false
)
@TypeConverters(Conversores::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getDaoMeme(): MemeDao

    companion object {
        private var INSTANCIA: AppDataBase? = null

        fun instanciar(context: Context): AppDataBase {
            val instanciaTemp = INSTANCIA

            if (instanciaTemp != null) {
                return instanciaTemp
            }
            synchronized(this) {
                val instancia = Room
                    .databaseBuilder(context, AppDataBase::class.java, "telalogin.db")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCIA = instancia
                return instancia
            }
        }
    }
}