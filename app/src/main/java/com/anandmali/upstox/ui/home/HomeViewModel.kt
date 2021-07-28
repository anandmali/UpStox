package com.anandmali.upstox.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anandmali.upstox.remote.Status
import com.anandmali.upstox.remote.StocksRepository
import com.anandmali.upstox.remote.data.UiStockModel
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val stocksRepository: StocksRepository
) : ViewModel() {

    private val stocksResponse = MutableLiveData<Status<List<UiStockModel>>>()

    fun getStockResponse(): LiveData<Status<List<UiStockModel>>> {
        return stocksResponse
    }

    private var job: Job = Job()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun getStocksList() {
        job = coroutineScope.launch {
            stocksRepository.getStocksList(::handleSuccess, ::handleError)
        }
    }

    private fun handleSuccess(list: List<UiStockModel>) {
        stocksResponse.postValue(Status.Success(list))
    }

    private fun handleError(error: String) {
        stocksResponse.postValue(Status.Failure(error))
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}