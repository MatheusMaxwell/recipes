package com.yape.recipes.domain.models

data class RecipeDomain(
    val id: Int,
    val image: String,
    val name: String,
    val description: String,
    val ingredients: List<String>,
    val latitude: String,
    val longitude: String
): java.io.Serializable
