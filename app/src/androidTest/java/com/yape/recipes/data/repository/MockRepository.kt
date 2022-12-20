package com.yape.recipes.data.repository

import com.yape.recipes.domain.models.RecipeDomain
import com.yape.recipes.utils.genericResult.GenericErrorResult
import com.yape.recipes.utils.genericResult.GenericResult
import com.yape.recipes.utils.genericResult.GenericSuccessResult

class MockRepository: Repository {

    var hasError = false
    var hasEmptyList = false

    private val recipe = RecipeDomain(
        id = 0,
        image = "",
        name = "Receita 1",
        ingredients = listOf("Tomate", "Cebolla"),
        description = "Modo de preparo",
        latitude = "",
        longitude = ""
    )
    override suspend fun fetchRecipes(): GenericResult<List<RecipeDomain>> {
        return if(hasError){
            GenericResult.Error(GenericErrorResult.Unknown(Throwable()))
        } else{
            if(hasEmptyList){
                GenericResult.Success(GenericSuccessResult.Empty())
            }
            else{
                GenericResult.Success(GenericSuccessResult.Populated(
                    listOf(
                        recipe,
                        recipe.copy(id = 1, name = "Teste 2"),
                        recipe.copy(id = 2, name = "Receita 2", ingredients = listOf("Carne, Ajo"))
                    )
                    )
                )
            }


        }
    }
}