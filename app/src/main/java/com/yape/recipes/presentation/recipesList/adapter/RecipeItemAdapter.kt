package com.yape.recipes.presentation.recipesList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yape.recipes.databinding.RecipeItemListBinding
import com.yape.recipes.domain.models.RecipeDomain
import com.yape.recipes.utils.extensions.loadImage

class RecipeItemAdapter(
    private var recipes: List<RecipeDomain>,
    private var onClick: (recipe: RecipeDomain) -> Unit
): RecyclerView.Adapter<RecipeItemViewHolder>() {

    private lateinit var binding: RecipeItemListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeItemViewHolder {
        binding = RecipeItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeItemViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.itemView.setOnClickListener {
            onClick.invoke(recipe)
        }
        holder.bind(recipe)
    }

    override fun getItemCount(): Int = recipes.size

    fun update(list: List<RecipeDomain>){
        recipes = list
        notifyDataSetChanged()
    }

}

class RecipeItemViewHolder(
    private val binding: RecipeItemListBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(recipe: RecipeDomain) {
        binding.imageRecipe.loadImage(recipe.image)
        binding.txtRecipeName.text = recipe.name
    }
}