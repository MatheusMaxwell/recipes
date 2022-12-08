package com.yape.recipes.presentation.recipesList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.yape.recipes.R
import com.yape.recipes.databinding.FragmentRecipesListBinding
import com.yape.recipes.domain.models.RecipeDomain
import com.yape.recipes.presentation.recipesList.adapter.RecipeItemAdapter
import com.yape.recipes.utils.extensions.makeGone
import com.yape.recipes.utils.extensions.makeVisible
import com.yape.recipes.utils.viewState.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel


class RecipesListFragment : Fragment() {

    private lateinit var binding: FragmentRecipesListBinding
    private var activityContext: FragmentActivity? = null
    private val viewModel: RecipesListViewModel by viewModel()
    private var adapter: RecipeItemAdapter? = null

    override fun onResume() {
        super.onResume()
        viewModel.fetchRecipes()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activityContext = requireActivity()
        binding = FragmentRecipesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObservables()
    }

    private fun setupView() = with(binding){

        searchView.queryHint = getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchInList(query)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                searchInList(query)
                return true
            }
        })

    }

    private fun searchInList(value: String?){
        value?.let { searchValue ->
            adapter?.update(viewModel.recipesList.filter { it.name.lowercase().contains(searchValue.lowercase()) || (it.ingredients.any { ing ->
                ing.lowercase().contains(searchValue.lowercase())
            }) })
        }
    }

    private fun createRecipesList(list: List<RecipeDomain>) = with(binding) {
        if(adapter == null){
            rvRecipes.removeAllViews()
            rvRecipes.layoutManager = LinearLayoutManager(activityContext)
            adapter = RecipeItemAdapter(list, ::onRecipeClickItem)
            rvRecipes.adapter = adapter
        }
        else{
            adapter?.update(list)
        }
    }

    private fun onRecipeClickItem(recipe: RecipeDomain){
        adapter = null
        val action = RecipesListFragmentDirections.actionRecipesListFragmentToRecipeDetailFragment(recipe)
        findNavController().navigate(action)
    }


    private fun setupObservables(){
        activityContext?.let { owner ->
            viewModel.viewStateRecipesLiveData.observe(owner) { viewState ->
                when (viewState) {
                    is ViewState.Loading -> {
                        binding.loading.progressLoading.makeVisible()
                    }
                    is ViewState.Empty -> {
                        binding.loading.progressLoading.makeGone()
                        createRecipesList(emptyList())
                    }
                    is ViewState.Success -> {
                        binding.loading.progressLoading.makeGone()
                        createRecipesList(viewState.data)
                    }
                    is ViewState.Error -> {
                        binding.loading.progressLoading.makeGone()
                        Toast.makeText(
                            activityContext,
                            getString(R.string.error_has_ocurred),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

}