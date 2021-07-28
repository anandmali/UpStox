package com.anandmali.upstox.remote.data

import java.math.BigDecimal

data class UiStockModel(
    val name: String,
    val quantity: Int,
    val ltp: BigDecimal,
    val pl: BigDecimal,
    val isHolding: Boolean = false
)