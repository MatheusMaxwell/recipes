package com.yape.recipes.presentation.recipesList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yape.recipes.domain.models.RecipeDomain
import com.yape.recipes.domain.usecases.FetchRecipesUseCase
import com.yape.recipes.utils.extensions.toViewStateError
import com.yape.recipes.utils.genericResult.GenericResult
import com.yape.recipes.utils.genericResult.GenericSuccessResult
import com.yape.recipes.utils.viewState.ViewState
import kotlinx.coroutines.launch

class RecipesListViewModel(
    private val fetchRecipesUseCase: FetchRecipesUseCase
) : ViewModel(){

    private val _viewStateRecipesLiveData =
        MutableLiveData<ViewState<List<RecipeDomain>>>()
    val viewStateRecipesLiveData: LiveData<ViewState<List<RecipeDomain>>> =
        _viewStateRecipesLiveData

    var recipesList: List<RecipeDomain> = emptyList()

    fun fetchRecipes(){
        _viewStateRecipesLiveData.value = ViewState.Loading()
        viewModelScope.launch {
            when(val result = fetchRecipesUseCase.execute()){
                is GenericResult.Success -> {
                    when (result.success) {
                        is GenericSuccessResult.Populated -> {
                            recipesList = ViewState.Success((result.success).data).data
                            _viewStateRecipesLiveData.value =
                                ViewState.Success((result.success).data)
                        }
                        is GenericSuccessResult.Empty -> {
                            _viewStateRecipesLiveData.value = ViewState.Empty()
                        }
                    }
                }
                is GenericResult.Error -> {
                    _viewStateRecipesLiveData.value = result.error.toViewStateError()
                }
            }
        }
    }
}