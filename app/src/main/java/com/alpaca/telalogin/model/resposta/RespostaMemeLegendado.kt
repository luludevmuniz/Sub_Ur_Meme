package com.alpaca.telalogin.model.resposta

import com.alpaca.telalogin.model.MemeLegendado

data class RespostaMemeLegendado(
    val success: Boolean,
    val data: MemeLegendado,
    val error_message: String
)