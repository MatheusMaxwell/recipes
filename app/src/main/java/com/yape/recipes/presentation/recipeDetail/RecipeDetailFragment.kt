package com.yape.recipes.presentation.recipeDetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.test.core.app.ActivityScenario
import com.yape.recipes.databinding.FragmentRecipeDetailBinding
import com.yape.recipes.presentation.MainActivity
import com.yape.recipes.utils.extensions.loadImage
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class RecipeDetailFragment : Fragment() {
    private val viewModel: RecipeDetailViewModel by viewModel()
    private val args: RecipeDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentRecipeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args.recipe?.let {
            viewModel.setRecipe(recipe = it)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() = with(binding){
        viewModel.getRecipe()?.let { recipe ->
            (activity as? AppCompatActivity)?.supportActionBar?.title = recipe.name

            imageRecipeDetail.loadImage(recipe.image)
            recipe.ingredients.forEach {
                val text = TextView(requireContext())
                text.text = "- ${it}"
                layoutIngredients.addView(text)
            }
            txtRecipeDescription.text = recipe.description

            btnMap.setOnClickListener {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("geo:<" + recipe.latitude + ">,<" + recipe.longitude + ">?q=<" + recipe.latitude + ">,<" + recipe.longitude + ">")
                )
                requireContext().startActivity(intent)
            }
        }

    }

}