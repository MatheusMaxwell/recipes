package com.yape.recipes.utils.genericResult

sealed class GenericSuccessResult<T> {
    class Populated<T>(val data: T) : GenericSuccessResult<T>()
    class Empty<T> : GenericSuccessResult<T>()
}