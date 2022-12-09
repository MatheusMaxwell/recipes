package com.yape.recipes.presentation.recipeDetail

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.yape.recipes.R
import com.yape.recipes.domain.models.RecipeDomain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.dsl.module


@RunWith(AndroidJUnit4ClassRunner::class)
class RecipeDetailFragmentTest {
    private val viewModel = RecipeDetailViewModel()
    private val recipe = RecipeDomain(0, "", "Receita 1", "Modo de preparo",
        listOf("tomate", "cebolla"), "", "")

    @Before
    fun setup(){
        loadKoinModules(module {
            viewModel {
                viewModel
            }
        })

        val args = RecipeDetailFragmentArgs(recipe)
        val bundle = args.toBundle()
        launchFragmentInContainer<RecipeDetailFragment>(fragmentArgs = bundle, themeResId = R.style.Theme_Recipes_NoActionBar)

    }

    @Test
    fun verifyIfImageIsVisible(){
        onView(withId(R.id.image_recipe_detail)).check(matches(isDisplayed()))
    }

    @Test
    fun verifyIngredientsIsVisible(){
        onView(withText(R.string.ingredients)).check(matches(isDisplayed()))
        onView(withText("- ${recipe.ingredients.first()}")).check(matches(isDisplayed()))
        onView(withText("- ${recipe.ingredients.last()}")).check(matches(isDisplayed()))
    }

    @Test
    fun verifyDescriptionIsVisible(){
        onView(withText(R.string.description)).check(matches(isDisplayed()))
        onView(withText(recipe.description)).check(matches(isDisplayed()))
    }

    @Test
    fun clickOnMapButton(){
        onView(withId(R.id.btn_map)).perform(click())
    }
}