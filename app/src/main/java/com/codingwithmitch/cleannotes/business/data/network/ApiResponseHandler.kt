package com.codingwithmitch.cleannotes.business.data.network

import com.codingwithmitch.cleannotes.business.data.cache.CacheErrors.CACHE_DATA_NULL
import com.codingwithmitch.cleannotes.business.data.cache.CacheResult
import com.codingwithmitch.cleannotes.business.data.network.NetworkErrors.NETWORK_DATA_NULL
import com.codingwithmitch.cleannotes.business.data.network.NetworkErrors.NETWORK_ERROR
import com.codingwithmitch.cleannotes.business.domain.state.*

abstract class ApiResponseHandler<ViewState, Data>(
    private val response: ApiResult<Data?>,
    private val stateEvent: StateEvent?
) {

    suspend fun getResult(): DataState<ViewState>? {
        return when (response) {
            is ApiResult.GenericError -> {
                DataState.error(
                    response = Response(
                        message = "${stateEvent?.errorInfo()}\n\n" +
                                "Reason: ${response.errorMessage}",
                        uiComponentType = UIComponentType.Dialog(),
                        messageType = MessageType.Error()
                    ),
                    stateEvent = stateEvent
                )
            }
            is ApiResult.NetworkError -> {
                DataState.error(
                    response = Response(
                        message = "${stateEvent?.errorInfo()}\n\n" +
                                "Reason: $NETWORK_ERROR",
                        uiComponentType = UIComponentType.Dialog(),
                        messageType = MessageType.Error()
                    ),
                    stateEvent = stateEvent
                )
            }
            is ApiResult.Success -> {
                if (response.value == null) {
                    DataState.error(
                        response = Response(
                            message = "${stateEvent?.errorInfo()}\n\n" +
                                    "Reason: $NETWORK_DATA_NULL",
                            uiComponentType = UIComponentType.Dialog(),
                            messageType = MessageType.Error()
                        ),
                        stateEvent = stateEvent
                    )
                } else {
                    handleSuccess(resultObj = response.value)
                }
            }
        }
    }

    abstract fun handleSuccess(resultObj: Data?): DataState<ViewState>

}