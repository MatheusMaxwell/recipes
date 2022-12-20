package com.yape.recipes.domain.usecases

import com.google.common.truth.Truth
import com.yape.recipes.data.repository.Repository
import com.yape.recipes.domain.models.RecipeDomain
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
class FetchRecipesUseCaseTest{

    private val repository = mockk<Repository>()
    private val fetchRecipesUseCase = FetchRecipesUseCase(repository)

    @Test
    fun `when fetchRecipes return GenericSuccessResult_Populated`() = runBlocking {
        val recipeDomain = mockk<RecipeDomain>(relaxed = true)
        val recipes = listOf(recipeDomain)
        coEvery {
            repository.fetchRecipes()
        } returns GenericResult.Success(GenericSuccessResult.Populated(recipes))

        val result = fetchRecipesUseCase.execute() as GenericResult.Success

        Truth
            .assertThat(result.success)
            .isInstanceOf(GenericSuccessResult.Populated::class.java)
    }

    @Test
    fun `when fetchRecipes return GenericSuccessResult_Empty`() = runBlocking {
        coEvery {
            repository.fetchRecipes()
        } returns GenericResult.Success(GenericSuccessResult.Empty())

        val result = fetchRecipesUseCase.execute() as GenericResult.Success

        Truth
            .assertThat(result.success)
            .isInstanceOf(GenericSuccessResult.Empty::class.java)
    }

    @Test
    fun `when fetchRecipes return GenericErrorResult`() = runBlocking {
        coEvery {
            repository.fetchRecipes()
        } returns GenericResult.Error(GenericErrorResult.Unknown(Throwable("")))

        val result = fetchRecipesUseCase.execute() as GenericResult.Error

        Truth
            .assertThat(result.error)
            .isInstanceOf(GenericErrorResult.Unknown::class.java)
    }


}