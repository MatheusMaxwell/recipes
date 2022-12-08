package com.yape.recipes.utils.extensions

import com.yape.recipes.utils.genericResult.GenericErrorResult
import com.yape.recipes.utils.viewState.ViewState


fun <E, V> GenericErrorResult<E>.toViewStateError(): ViewState.Error<V> {
    return when (this) {
        is GenericErrorResult.Unknown -> {
            ViewState.Error()
        }
        is GenericErrorResult.BadRequest -> {
            ViewState.Error()
        }
    }
}