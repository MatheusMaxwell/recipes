package com.yape.recipes.data.repository

import com.yape.recipes.data.dataSource.remote.DataSourceRemote
import com.yape.recipes.data.mapper.toDomain
import com.yape.recipes.domain.models.RecipeDomain
import com.yape.recipes.utils.extensions.toGenericErrorResult
import com.yape.recipes.utils.genericResult.GenericResult
import com.yape.recipes.utils.genericResult.GenericSuccessResult
import retrofit2.HttpException
import java.lang.Exception

class RepositoryImpl(
    private val dataSource: DataSourceRemote
) : Repository {
    override suspend fun fetchRecipes(): GenericResult<List<RecipeDomain>> {
        return try {
            dataSource.fetchRecipes().let { response ->
                if (response.code() == 200) {
                    val recipes = response.body()?.recipes
                    if (recipes.isNullOrEmpty()) {
                        GenericResult.Success(GenericSuccessResult.Empty())
                    } else {
                        GenericResult.Success(GenericSuccessResult.Populated(recipes.toDomain()))
                    }
                } else {
                    throw HttpException(response)
                }
            }
        } catch (exception: Exception) {
            exception.toGenericErrorResult()
        }
    }
}