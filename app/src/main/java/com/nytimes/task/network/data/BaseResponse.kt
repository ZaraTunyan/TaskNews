package com.nytimes.task.network.data

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("status") val status: String,
    @SerializedName("results") val result: T,
    @SerializedName("fault") val error: BaseError?
)

data class BaseError(
    @SerializedName("faultstring") val faulttring: String?,
    @SerializedName("detail") val detail: DetailError
)

data class DetailError(
    @SerializedName("errorCode") val errorCode: String?
)