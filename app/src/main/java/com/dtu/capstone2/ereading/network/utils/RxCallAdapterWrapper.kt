package com.dtu.capstone2.ereading.network.utils

import okhttp3.ResponseBody
import retrofit2.*
import java.lang.reflect.Type
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.HttpsURLConnection

class RxCallAdapterWrapper<R>(type: Type, retrofit: Retrofit, wrapped: CallAdapter<R, *>?) : BaseRxCallAdapterWrapper<R>(type, retrofit, wrapped) {

    override fun convertRetrofitExceptionToCustomException(throwable: Throwable, retrofit: Retrofit): Throwable {

        if (throwable is HttpException) {
            val converter: Converter<ResponseBody, ApiException> = retrofit.responseBodyConverter(ApiException::class.java, arrayOfNulls<Annotation>(0))
            val response: Response<*>? = throwable.response()
            val responseBody = throwable.response().errorBody()?.source().toString()
            when (response?.code()) {
                HttpsURLConnection.HTTP_UNAUTHORIZED -> response.errorBody()?.let {
                    val apiException = converter.convert(it)
                    apiException?.statusCode = HttpsURLConnection.HTTP_UNAUTHORIZED
                    return apiException!!
                }

                ApiException.FORCE_UPDATE_ERROR_CODE -> response.errorBody()?.let {
                    val apiException = converter.convert(it)
                    apiException?.statusCode = ApiException.FORCE_UPDATE_ERROR_CODE
                    return apiException!!
                }

                HttpsURLConnection.HTTP_BAD_REQUEST -> response.errorBody()?.let {
                    val apiException = converter.convert(it)
                    apiException?.statusCode = HttpsURLConnection.HTTP_BAD_REQUEST
                    //Get source Error Body response
                    apiException?.responseBody = responseBody
                    return apiException!!
                }

                HttpsURLConnection.HTTP_INTERNAL_ERROR -> response.errorBody()?.let {
                    val apiException = converter.convert(it)
                    apiException?.statusCode = HttpsURLConnection.HTTP_INTERNAL_ERROR
                    return apiException!!
                }

                HttpsURLConnection.HTTP_FORBIDDEN -> response.errorBody()?.let {
                    val apiException = converter.convert(it)
                    apiException?.statusCode = HttpsURLConnection.HTTP_FORBIDDEN
                    return apiException!!
                }

                HttpsURLConnection.HTTP_NOT_FOUND -> response.errorBody()?.let {
                    val apiException = converter.convert(it)
                    apiException?.statusCode = HttpsURLConnection.HTTP_NOT_FOUND
                    return apiException!!
                }

                HttpsURLConnection.HTTP_NOT_ACCEPTABLE -> response.errorBody()?.let {
                    val apiException = converter.convert(it)
                    apiException?.statusCode = HttpsURLConnection.HTTP_NOT_ACCEPTABLE
                    return apiException!!
                }

                HttpURLConnection.HTTP_UNAVAILABLE -> response.errorBody()?.let {
                    val apiException = converter.convert(it)
                    apiException?.statusCode = ApiException.MAINTENANCE
                    return apiException!!
                }

                HttpURLConnection.HTTP_PRECON_FAILED -> response.errorBody()?.let {
                    val apiException = converter.convert(it)
                    apiException?.statusCode = HttpURLConnection.HTTP_PRECON_FAILED
                    return apiException!!
                }

                HttpURLConnection.HTTP_CONFLICT -> response.errorBody()?.let {
                    val apiException = converter.convert(it)
                    apiException?.statusCode = HttpURLConnection.HTTP_CONFLICT
                    return apiException!!
                }
                HttpURLConnection.HTTP_ENTITY_TOO_LARGE -> response.errorBody()?.let {
                    val apiException = converter.convert(it)
                    apiException?.statusCode = HttpURLConnection.HTTP_ENTITY_TOO_LARGE
                    return apiException!!
                }
                ApiException.USER_BANNER -> response.errorBody()?.let {
                    val apiException = converter.convert(it)
                    apiException?.statusCode = ApiException.USER_BANNER
                    return apiException!!
                }
                ApiException.FUNC_BANNER -> response.errorBody()?.let {
                    val apiException = converter.convert(it)
                    apiException?.statusCode = ApiException.FUNC_BANNER
                    return apiException!!
                }
                ApiException.LIMIT_MESSAGE_REQUEST -> response.errorBody()?.let {
                    val apiException = converter.convert(it)
                    apiException?.statusCode = ApiException.LIMIT_MESSAGE_REQUEST
                    return apiException!!
                }
                ApiException.LIMIT_LIKE_REQUEST -> response.errorBody()?.let {
                    val apiException = converter.convert(it)
                    apiException?.statusCode = ApiException.LIMIT_LIKE_REQUEST
                    return apiException!!
                }

                ApiException.MAINTENANCE_CARD -> response.errorBody()?.let {
                    val apiException = converter.convert(it)
                    apiException?.statusCode = ApiException.MAINTENANCE_CARD
                    return apiException!!
                }
            }
        }

        if (throwable is UnknownHostException || throwable is ConnectException) {
            // Set message error of this case in activity extension
            val apiException = ApiException("", MessageApiException())
            apiException.statusCode = ApiException.NETWORK_ERROR_CODE
            return apiException
        }

        if (throwable is SocketTimeoutException) {
            val apiException = ApiException("", MessageApiException())
            apiException.statusCode = HttpURLConnection.HTTP_CLIENT_TIMEOUT
            return apiException
        }

        return throwable
    }
}
