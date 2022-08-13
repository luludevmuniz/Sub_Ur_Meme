package com.alpaca.telalogin.model.resposta

import com.alpaca.telalogin.model.data.DataListaMemes

data class RespostaListaMemes(
    val success: Boolean,
    val data: DataListaMemes
)