package com.yape.recipes.di

import com.yape.recipes.data.dataSource.remote.ApiService
import com.yape.recipes.data.dataSource.remote.DataSourceRemote
import com.yape.recipes.data.dataSource.remote.DataSourceRemoteImpl
import com.yape.recipes.data.network.Network.provideOkHttpClient
import com.yape.recipes.data.network.Network.provideRetrofit
import com.yape.recipes.data.repository.Repository
import com.yape.recipes.data.repository.RepositoryImpl
import com.yape.recipes.domain.usecases.FetchRecipesUseCase
import com.yape.recipes.presentation.recipeDetail.RecipeDetailViewModel
import com.yape.recipes.presentation.recipesList.RecipesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

object AppDiModule {

    private const val RETROFIT_SCOPE_NAME = "retrofit_scope"

    val modules = module {
        single {
            provideOkHttpClient()
        }
        single (named(RETROFIT_SCOPE_NAME)) {
            provideRetrofit(okHttpClient = get())
        }

        single<ApiService> {
            get<Retrofit>(named(RETROFIT_SCOPE_NAME)).create(ApiService::class.java)
        }

        single<DataSourceRemote>{
            DataSourceRemoteImpl(api = get())
        }

        single<Repository>{
            RepositoryImpl(dataSource = get())
        }

        single {
            FetchRecipesUseCase(repository = get())
        }

        viewModel {
            RecipesListViewModel(fetchRecipesUseCase = get())
        }

        viewModel {
            RecipeDetailViewModel()
        }

    }
}