package com.anandmali.upstox.remote

import com.anandmali.upstox.remote.data.UiStockModel
import com.anandmali.upstox.remote.data.DomainStockModel
import java.math.BigDecimal

//    1. Current value = sum of (Last traded price * quantity of holding ) of all the holdings
//    2. Total investment = sum of (Average Price * quantity of holding ) of all the holdings
//    3. Total PNL = Current value - Total Investment
//    4. Today’s PNL = sum of ((Close - LTP ) * quantity) of all the holdings

interface DomainMapper<in T, out R> {
    fun map(model: T): R
}

class StocksDomainToUiMapper : DomainMapper<DomainStockModel, UiStockModel> {
    override fun map(domainStockModel: DomainStockModel): UiStockModel {
        return convert(domainStockModel)
    }

    private fun convert(modelDomain: DomainStockModel): UiStockModel {
        with(modelDomain) {
            return UiStockModel(
                name = symbol,
                quantity = holdingsUpdateQty,
                ltp = BigDecimal.valueOf(ltp),
                pl = BigDecimal(holdingsUpdateQty).multiply(
                    BigDecimal.valueOf(close).subtract(BigDecimal.valueOf(ltp))
                ),
                isHolding = t1HoldingQty != 0
            )
        }
    }
}