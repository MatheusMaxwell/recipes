package com.yape.recipes.presentation.recipeDetail

import com.google.common.truth.Truth
import com.yape.recipes.domain.models.RecipeDomain
import com.yape.recipes.utils.genericResult.GenericResult
import com.yape.recipes.utils.genericResult.GenericSuccessResult
import com.yape.recipes.utils.liveData.getOrAwaitValue
import com.yape.recipes.utils.viewState.ViewState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class RecipeDetailViewModelTest {

    private val viewModel = RecipeDetailViewModel()

    @Test
    fun `when setRecipe and getRecipe work correctly`() = runBlocking {
        val recipe = mockk<RecipeDomain>()

        viewModel.setRecipe(recipe)

        Truth
            .assertThat(viewModel.getRecipe())
            .isEqualTo(recipe)
    }

}