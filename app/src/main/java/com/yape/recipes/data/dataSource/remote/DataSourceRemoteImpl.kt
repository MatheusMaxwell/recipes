package com.yape.recipes.data.dataSource.remote

import com.yape.recipes.data.models.RecipeResponse
import retrofit2.Response

class DataSourceRemoteImpl(
    private val api: ApiService
) : DataSourceRemote {
    override suspend fun fetchRecipes(): Response<RecipeResponse>  = api.fetchRecipes()
}