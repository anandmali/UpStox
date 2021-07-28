package com.anandmali.upstox.remote

import com.anandmali.upstox.remote.data.DomainStockModel
import com.anandmali.upstox.remote.data.UiStockModel
import java.lang.Exception
import javax.inject.Inject

class StocksRepository
@Inject constructor(
    private val stocksApi: StocksApi,
    private val mapper: StocksDomainToUiMapper
) {

    suspend fun getStocksList(
        handleSuccess: (List<UiStockModel>) -> Unit,
        handleError: (String) -> Unit
    ) {
        val response = stocksApi.getStocks()
        try {
            if (response.isSuccessful) {
                response.body()?.let {
                    if (it.domainStock.isNotEmpty()) {
                        handleSuccess(mapDomainToUiModel(it.domainStock))
                    }
                }
            }
            handleError("No results")
        } catch (e: Exception) {
            handleError(e.localizedMessage ?: "Error fetching holdings list")
        }
    }

    private fun mapDomainToUiModel(domainList: List<DomainStockModel>): List<UiStockModel> {
        return domainList.map {
            mapper.map(it)
        }
    }

}