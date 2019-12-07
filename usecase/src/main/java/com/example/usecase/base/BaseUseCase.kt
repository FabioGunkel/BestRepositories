package com.example.usecase.base

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.HttpException


abstract class BaseUseCase<Req, Resp> where Req : BaseUseCase.DomainRequest, Resp : BaseUseCase.DomainResponse {

    interface DomainRequest

    interface DomainResponse

    abstract fun execute(
        @Suppress("UNCHECKED_CAST")
        request: Req = Empty as Req
    ): Single<Result<Resp>>

    object Empty : DomainRequest, DomainResponse

}

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure<out T>(val error: Error) : Result<T>()
}

sealed class Error {
    data class Default(
        val code: Int,
        val message: String,
        val cause: Throwable? = null
    ) : Error()

    object Unauthorized : Error()
    data class UnknownException(val cause: Throwable) : Error()

    companion object {
        const val INTERNAL_ERROR = 900
    }
}

fun <T> Throwable.toErrorResult(): Result<T> {
    return when (this) {
        is HttpException -> {
            when (val code = code()) {
                401 -> Result.Failure(Error.Unauthorized)
                in 400..499 -> {
                        Result.Failure(
                            Error.Default(
                                code = code,
                                message = this.message ?: "",
                                cause = this
                            )
                        )


                }
                else -> {
                    Result.Failure(Error.UnknownException(this))
                }
            }
        }
        else -> Result.Failure(Error.UnknownException(this))
    }
}

fun <T : BaseUseCase.DomainResponse> T.toResult(): Result<T> {
    return Result.Success(this)
}
