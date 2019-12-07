package com.example.viewmodel.base

import com.example.usecase.base.Error


data class ErrorUiModel(
    val error: Error,
    val message : String,
    val positiveText: String,
    val onPositiveClick: () -> Unit
)

fun Throwable.toUiModel(positiveText: String,
                        onPositiveClick: () -> Unit = {}): ErrorUiModel {
    return ErrorUiModel(
        error = Error.UnknownException(this),
        positiveText = positiveText,
        message = this.localizedMessage,
        onPositiveClick = onPositiveClick
    )
}

fun Error.toUiModel(
    positiveText: String,
    onPositiveClick: () -> Unit = {}): ErrorUiModel {
    val message = when(this){
        is Error.Default -> {
            this.message
        }
        is Error.UnknownException -> {
            this.cause.localizedMessage
        }

    }
    return ErrorUiModel(
        error = this,
        positiveText = positiveText,
        message = message,
        onPositiveClick = onPositiveClick)
}