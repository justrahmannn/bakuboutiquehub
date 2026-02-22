package com.atl.bakuboutiquehub.homescreens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atl.bakuboutiquehub.databinding.ItemProductBinding

class ProductAdapter(private val list: List<Product>) : RecyclerView.Adapter<ProductAdapter.VH>() {
    inner class VH(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = list[position]
        holder.binding.apply {
            tvProductName.text = item.name
            tvPrice.text = item.price
            tvStock.text = "${item.stock} left"
            tvReviewCount.text = "(${item.reviews} Reviews)"
            ivProduct.setImageResource(item.imageRes)
        }
    }
    override fun getItemCount() = list.size
}