package com.example.viewmodel.base

import com.example.usecase.base.Error


data class ErrorUiModel(
    val error: Error,
    val positiveText: String,
    val onPositiveClick: () -> Unit,
    val finishActivity: Boolean = false
)

fun Throwable.toUiModel(positiveText: String,
                        finishActivity: Boolean = false,
                        onPositiveClick: () -> Unit = {}): ErrorUiModel {
    return ErrorUiModel(
        error = Error.UnknownException(this),
        positiveText = positiveText,
        finishActivity = finishActivity,
        onPositiveClick = onPositiveClick
    )
}

fun Error.toUiModel(
    positiveText: String,
    finishActivity: Boolean = false,
    onPositiveClick: () -> Unit = {}): ErrorUiModel {
    return ErrorUiModel(
        error = this,
        positiveText = positiveText,
        finishActivity = finishActivity,
        onPositiveClick = onPositiveClick)
}