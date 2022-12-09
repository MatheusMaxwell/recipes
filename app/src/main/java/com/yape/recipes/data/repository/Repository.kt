package com.yape.recipes.data.repository

import com.yape.recipes.domain.models.RecipeDomain
import com.yape.recipes.utils.genericResult.GenericResult

interface Repository {

    suspend fun fetchRecipes(): GenericResult<List<RecipeDomain>>

}