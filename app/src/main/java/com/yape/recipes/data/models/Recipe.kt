package com.yape.recipes.data.models

data class Recipe (
    val id: Int,
    val image: String?,
    val name: String?,
    val description: String?,
    val ingredients: List<String>?,
    val latitude: String?,
    val longitude: String?
)