package com.yape.recipes.presentation.recipeDetail

import androidx.lifecycle.ViewModel
import com.yape.recipes.domain.models.RecipeDomain

class RecipeDetailViewModel: ViewModel() {

    private var  recipe: RecipeDomain? = null


    fun setRecipe(recipe: RecipeDomain){
        this.recipe = recipe
    }

    fun getRecipe() = this.recipe
}