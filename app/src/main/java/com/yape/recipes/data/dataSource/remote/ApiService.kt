package com.yape.recipes.data.dataSource.remote

import com.yape.recipes.data.models.RecipeResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/recipes")
    suspend fun fetchRecipes(): Response<RecipeResponse>

}