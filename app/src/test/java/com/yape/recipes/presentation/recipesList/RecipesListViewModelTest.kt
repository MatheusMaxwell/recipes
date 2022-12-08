package com.yape.recipes.presentation.recipesList

import com.google.common.truth.Truth
import com.yape.recipes.domain.models.RecipeDomain
import com.yape.recipes.domain.usecases.FetchRecipesUseCase
import com.yape.recipes.utils.genericResult.GenericErrorResult
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
class RecipesListViewModelTest {

    private val fetchRecipesUseCase = mockk<FetchRecipesUseCase>()
    private val viewModel = RecipesListViewModel(fetchRecipesUseCase = fetchRecipesUseCase)

    @Test
    fun `when fetchRecipes return ViewState_Success`() = runBlocking {
        val recipeDomain = mockk<RecipeDomain>(relaxed = true)
        val recipes = listOf(recipeDomain)
        coEvery {
            fetchRecipesUseCase.execute()
        } returns GenericResult.Success(GenericSuccessResult.Populated(recipes))

        viewModel.fetchRecipes()

        Truth
            .assertThat(viewModel.viewStateRecipesLiveData.getOrAwaitValue())
            .isInstanceOf(ViewState.Success::class.java)
    }

    @Test
    fun `when fetchRecipes return ViewState_Empty`() = runBlocking {
        coEvery {
            fetchRecipesUseCase.execute()
        } returns GenericResult.Success(GenericSuccessResult.Empty())

        viewModel.fetchRecipes()

        Truth
            .assertThat(viewModel.viewStateRecipesLiveData.getOrAwaitValue())
            .isInstanceOf(ViewState.Empty::class.java)
    }

    @Test
    fun `when fetchRecipes return ViewState_Error`() = runBlocking {

        coEvery {
            fetchRecipesUseCase.execute()
        } returns GenericResult.Error(GenericErrorResult.Unknown(Throwable("")))

        viewModel.fetchRecipes()

        Truth
            .assertThat(viewModel.viewStateRecipesLiveData.getOrAwaitValue())
            .isInstanceOf(ViewState.Error::class.java)

    }

}