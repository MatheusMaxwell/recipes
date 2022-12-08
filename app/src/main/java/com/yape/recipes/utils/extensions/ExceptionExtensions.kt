package com.yape.recipes.utils.extensions

import android.util.Log
import com.yape.recipes.utils.genericResult.GenericErrorResult
import com.yape.recipes.utils.genericResult.GenericResult
import retrofit2.HttpException
import java.lang.Exception

fun <T> Exception.toGenericErrorResult(): GenericResult<T> {
    Log.e("EXCEPTION", this.toString()+this.message)
    return when(this) {
        is HttpException -> {
            GenericResult.Error(GenericErrorResult.BadRequest(this))
        }
        else -> {
            GenericResult.Error(GenericErrorResult.Unknown(this))
        }
    }

}