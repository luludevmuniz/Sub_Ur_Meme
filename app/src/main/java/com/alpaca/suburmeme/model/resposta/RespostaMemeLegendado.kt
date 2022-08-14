package com.alpaca.suburmeme.model.resposta

import com.alpaca.suburmeme.model.MemeLegendado

data class RespostaMemeLegendado(
    val success: Boolean,
    val data: MemeLegendado,
    val error_message: String
)