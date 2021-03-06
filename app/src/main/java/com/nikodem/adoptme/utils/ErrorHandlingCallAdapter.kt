package com.nikodem.adoptme.utils

import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.*
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

sealed class ApiResult<out T> {
    data class Success<T>(val data: T? = null) : ApiResult<T>()
    data class Failure(val apiError: ApiErrorResponse?) : ApiResult<Nothing>()
    data class NetworkError(val error: String) : ApiResult<Nothing>()
    data class OtherError(val error: String) : ApiResult<Nothing>()
}

abstract class CallDelegate<TIn, TOut>(
    protected val proxy: Call<TIn>
) : Call<TOut> {
    override fun execute(): Response<TOut> = throw NotImplementedError()
    final override fun enqueue(callback: Callback<TOut>) = enqueueImpl(callback)
    final override fun clone(): Call<TOut> = cloneImpl()

    override fun cancel() = proxy.cancel()
    override fun request(): Request = proxy.request()
    override fun isExecuted() = proxy.isExecuted
    override fun isCanceled() = proxy.isCanceled

    abstract fun enqueueImpl(callback: Callback<TOut>)
    abstract fun cloneImpl(): Call<TOut>
}

class ResultCall<T>(proxy: Call<T>, private val errorConverter: Converter<ResponseBody, ApiErrorResponse>) :
    CallDelegate<T, ApiResult<T>>(proxy) {

    @Suppress("MagicNumber", "TooGenericExceptionCaught")
    override fun enqueueImpl(callback: Callback<ApiResult<T>>) =
        proxy.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val code = response.code()
                val result = if (code in 200 until 300) {
                    val body = response.body()
                    ApiResult.Success(body)
                } else {
                    try {
                        val apiError = errorConverter.convert(response.errorBody()!!)
                        ApiResult.Failure(apiError)
                    } catch (ex: Exception) {
                        ApiResult.OtherError(ex.message!!)
                    }
                }
                callback.onResponse(this@ResultCall, Response.success(result))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val result = when (t) {
                    is IOException -> ApiResult.NetworkError(t.message!!)
                    else -> ApiResult.OtherError(t.message!!)
                }
                callback.onResponse(this@ResultCall, Response.success(result))
            }
        })

    override fun cloneImpl() = ResultCall(proxy.clone(), errorConverter)
    override fun timeout(): Timeout {
        return Timeout()
    }
}

class ResultAdapter(
    private val type: Type,
    private val errorConverter: Converter<ResponseBody, ApiErrorResponse>
) : CallAdapter<Type, Call<ApiResult<Type>>> {
    override fun responseType() = type
    override fun adapt(call: Call<Type>): Call<ApiResult<Type>> = ResultCall(call, errorConverter)
}

class ErrorHandlingCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ) = when (getRawType(returnType)) {
        Call::class.java -> {
            val callType = getParameterUpperBound(0, returnType as ParameterizedType)
            when (getRawType(callType)) {
                ApiResult::class.java -> {
                    val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                    val errorConverter: Converter<ResponseBody, ApiErrorResponse> =
                        retrofit.responseBodyConverter(ApiErrorResponse::class.java, emptyArray())
                    ResultAdapter(resultType, errorConverter)
                }
                else -> null
            }
        }
        else -> null
    }
}