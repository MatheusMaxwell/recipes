package com.yape.recipes.data.repository

import com.google.common.truth.Truth
import com.yape.recipes.data.dataSource.remote.DataSourceRemote
import com.yape.recipes.data.models.Recipe
import com.yape.recipes.data.models.RecipeResponse
import com.yape.recipes.utils.genericResult.GenericErrorResult
import com.yape.recipes.utils.genericResult.GenericResult
import com.yape.recipes.utils.genericResult.GenericSuccessResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response

@RunWith(RobolectricTestRunner::class)
class RepositoryImplTest {

    private val dataSourceRemote = mockk<DataSourceRemote>(relaxed = true)
    private val repository = RepositoryImpl(dataSourceRemote)

    private val responseBodyError = "".toResponseBody("application/json".toMediaTypeOrNull())

    @Test
    fun `when repository fetchRecipes return GenericSuccessResult_Populated`() = runBlocking {
        val recipe = mockk<Recipe>(relaxed = true)
        val response = RecipeResponse(recipes = listOf(recipe))
        coEvery {
            dataSourceRemote.fetchRecipes()
        } returns Response.success(200, response)

        val result = repository.fetchRecipes() as GenericResult.Success

        Truth
            .assertThat(result.success)
            .isInstanceOf(GenericSuccessResult.Populated::class.java)
    }

    @Test
    fun `when repository fetchRecipes return GenericSuccessResult_Empty`() = runBlocking {

        val response = RecipeResponse(recipes = emptyList())
        coEvery {
            dataSourceRemote.fetchRecipes()
        } returns Response.success(200, response)

        val result = repository.fetchRecipes() as GenericResult.Success

        Truth
            .assertThat(result.success)
            .isInstanceOf(GenericSuccessResult.Empty::class.java)
    }

    @Test
    fun `when repository fetchRecipes return GenericErrorResult_BadRequest`() = runBlocking {

        coEvery {
            dataSourceRemote.fetchRecipes()
        } returns Response.error(500, responseBodyError)

        val result = repository.fetchRecipes() as GenericResult.Error

        Truth
            .assertThat(result.error)
            .isInstanceOf(GenericErrorResult.BadRequest::class.java)

    }

    @Test
    fun `when repository fetchRecipes return GenericErrorResult_Unknown`() = runBlocking {

        coEvery {
            dataSourceRemote.fetchRecipes()
        } throws  Exception("")

        val result = repository.fetchRecipes() as GenericResult.Error

        Truth
            .assertThat(result.error)
            .isInstanceOf(GenericErrorResult.Unknown::class.java)

    }

}