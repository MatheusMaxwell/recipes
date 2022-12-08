package com.yape.recipes.data.dataSource.remote

import com.yape.recipes.data.models.RecipeResponse
import retrofit2.Response

interface DataSourceRemote {

    suspend fun fetchRecipes(): Response<RecipeResponse>

}