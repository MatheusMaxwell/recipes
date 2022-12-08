package com.yape.recipes.data.mapper

import com.yape.recipes.data.models.Recipe
import com.yape.recipes.domain.models.RecipeDomain

fun Recipe.toDomain(): RecipeDomain{
    return RecipeDomain(
        id = id,
        image = image ?: "",
        name = name ?: "Unnamed",
        description = description ?: "Without description",
        ingredients = ingredients ?: emptyList(),
        latitude = latitude ?: "",
        longitude = longitude ?: ""
    )
}

fun List<Recipe>.toDomain(): List<RecipeDomain> {
    return this.map {
        it.toDomain()
    }
}