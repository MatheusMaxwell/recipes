package com.yape.recipes.domain.usecases

import com.yape.recipes.data.repository.Repository
import com.yape.recipes.domain.models.RecipeDomain
import com.yape.recipes.utils.genericResult.GenericResult

class FetchRecipesUseCase(
    private val repository: Repository
) {
    suspend fun execute(): GenericResult<List<RecipeDomain>> = repository.fetchRecipes()
}