package com.yape.recipes.presentation.recipesList

import android.os.Build
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.yape.recipes.R
import com.yape.recipes.data.repository.MockRepository
import com.yape.recipes.domain.usecases.FetchRecipesUseCase
import com.yape.recipes.presentation.recipesList.adapter.RecipeItemViewHolder
import com.yape.recipes.utils.ToastMatcher
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module


@RunWith(AndroidJUnit4ClassRunner::class)
class RecipesListFragmentTest {

    private val repository = MockRepository()
    private val fetchRecipesUseCase = FetchRecipesUseCase(repository)
    private val viewModel = RecipesListViewModel(fetchRecipesUseCase)

    @Before
    fun setup(){
        loadKoinModules(module {
            viewModel {
                viewModel
            }
        })
    }

    @Test
    fun testRecipesIsShowing() {
        launchFragmentInContainer<RecipesListFragment>(null, R.style.Theme_Recipes_NoActionBar)

        performScrollToRecipeAndCheckText(1, "Receita 1")
        performScrollToRecipeAndCheckText(2, "Teste 2")
        performScrollToRecipeAndCheckText(3, "Receita 2")
    }

    @Test
    fun testSearchRecipePerName(){
        launchFragmentInContainer<RecipesListFragment>(null, R.style.Theme_Recipes_NoActionBar)

        onView(withId(R.id.search_view)).perform(click())
        onView(withId(R.id.search_view)).perform(typeSearchViewText("receita"))

        performScrollToRecipeAndCheckText(1, "Receita 1")
        performScrollToRecipeAndCheckText(2, "Receita 2")
    }

    @Test
    fun testSearchRecipePerIngredient(){
        launchFragmentInContainer<RecipesListFragment>(null, R.style.Theme_Recipes_NoActionBar)

        onView(withId(R.id.search_view)).perform(click())
        onView(withId(R.id.search_view)).perform(typeSearchViewText("cebolla"))

        performScrollToRecipeAndCheckText(1, "Receita 1")
        performScrollToRecipeAndCheckText(2, "Teste 2")
    }

    @Test
    fun showMessageErrorWhenRequestRecipes(){
        repository.hasError = true

        launchFragmentInContainer<RecipesListFragment>(null, R.style.Theme_Recipes_NoActionBar)

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.R){
            onView(withText(R.string.error_has_ocurred)).inRoot(ToastMatcher()).check(matches(isDisplayed()))
        }
    }

    private fun typeSearchViewText(text: String): ViewAction {
        return object : ViewAction {
            override fun getDescription(): String {
                return "Change view text"
            }

            override fun getConstraints(): Matcher<View> {
                return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
            }

            override fun perform(uiController: UiController?, view: View?) {
                (view as SearchView).setQuery(text, false)
            }
        }
    }

    private fun performScrollToRecipeAndCheckText(position: Int, text: String){
        onView(withId(R.id.rv_recipes))
            .perform(
                RecyclerViewActions.scrollToPosition<RecipeItemViewHolder>(position)
            )
        onView(withText(text)).check(matches(isDisplayed()))
    }

}