package com.alpaca.suburmeme.model.resposta

import com.alpaca.suburmeme.model.data.DataListaMemes

data class RespostaListaMemes(
    val success: Boolean,
    val data: DataListaMemes
)