package com.atl.bakuboutiquehub.homescreens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atl.bakuboutiquehub.databinding.ItemCategoryBinding

class CategoryAdapter(private val list: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = list[position]
        holder.binding.apply {
            tvCategoryName.text = item.name
            tvCollectionCount.text = item.count
            ivCategory.setImageResource(item.imageRes)

        }
    }

    override fun getItemCount(): Int = list.size
}