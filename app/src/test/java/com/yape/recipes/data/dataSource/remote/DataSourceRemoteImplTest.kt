package com.yape.recipes.data.dataSource.remote

import com.google.common.truth.Truth
import com.yape.recipes.data.models.RecipeResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response

@RunWith(RobolectricTestRunner::class)
class DataSourceRemoteImplTest{
    private val api = mockk<ApiService>()
    private val dataSource = DataSourceRemoteImpl(api = api)

    @Test
    fun `when fetchRecipes return RecipeResponse with success`() = runBlocking{
        val response = mockk<RecipeResponse>()
        coEvery {
            api.fetchRecipes()
        } returns Response.success(200, response)

        val result = dataSource.fetchRecipes()

        Truth
            .assertThat(result.body())
            .isEqualTo(response)
    }

}