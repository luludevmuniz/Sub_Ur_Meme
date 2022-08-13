package com.alpaca.telalogin.di

import android.content.Context
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
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // ROOM
    @Provides
    @Singleton
    fun proverBancoDeDados(@ApplicationContext context: Context): AppDataBase {
        return AppDataBase.instanciar(context)
    }

    @Provides
    @Singleton
    fun proverUsuarioDao(appDataBase: AppDataBase): UsuarioDao {
        return appDataBase.getDaoUsuario()
    }

    @Provides
    @Singleton
    fun proverMemeDao(appDataBase: AppDataBase): MemeDao {
        return appDataBase.getDaoMeme()
    }

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