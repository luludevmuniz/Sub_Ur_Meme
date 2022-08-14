package com.alpaca.suburmeme.di

import android.content.Context
import com.alpaca.suburmeme.api.ApiService
import com.alpaca.suburmeme.db.AppDataBase
import com.alpaca.suburmeme.db.dao.MemeDao
import com.alpaca.suburmeme.util.Constantes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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