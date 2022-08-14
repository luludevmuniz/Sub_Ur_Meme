package com.alpaca.telalogin.api

import com.alpaca.telalogin.model.resposta.RespostaListaMemes
import com.alpaca.telalogin.model.resposta.RespostaMemeLegendado
import com.alpaca.telalogin.util.Constantes
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET(Constantes.IMGFLIP_GET_MEMES_ENDPOINT)
    suspend fun getMemes(): Response<RespostaListaMemes>

    @FormUrlEncoded
    @POST(Constantes.IMGFLIP_CAPTION_IMAGE_ENDPOINT)
    suspend fun legendarMeme(
        @Field("template_id") id: Int,
        @Field("username") login: String,
        @Field("password") senha: String,
        @Field("text0") textoSuperior: String,
        @Field("text1") textoInferior: String
    ): Response<RespostaMemeLegendado>
}