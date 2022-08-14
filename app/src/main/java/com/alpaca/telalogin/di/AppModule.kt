package com.alpaca.telalogin.di

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.alpaca.telalogin.api.ApiService
import com.alpaca.telalogin.db.AppDataBase
import com.alpaca.telalogin.db.dao.MemeDao
import com.alpaca.telalogin.db.dao.UsuarioDao
import com.alpaca.telalogin.util.Constantes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // ROOM
    @Provides
    @Singleton
    fun proverBancoDeDados(@ApplicationContext context: Context): AppDataBase =
        AppDataBase.instanciar(context)


    @Provides
    @Singleton
    fun proverUsuarioDao(appDataBase: AppDataBase): UsuarioDao = appDataBase.getDaoUsuario()


    @Provides
    @Singleton
    fun proverMemeDao(appDataBase: AppDataBase): MemeDao = appDataBase.getDaoMeme()


    // RETROFIT2
    @Provides
    @Singleton
    fun proverImgflipUrlBase() = Constantes.IMGFLIP_URL_BASE

    @Provides
    @Singleton
    fun proverInstanciaRetrofit(URL_BASE: String): ApiService =
        Retrofit.Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
}