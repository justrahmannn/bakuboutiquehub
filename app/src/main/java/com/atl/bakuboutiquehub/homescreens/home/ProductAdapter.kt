package com.atl.bakuboutiquehub.homescreens.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atl.bakuboutiquehub.R
import com.atl.bakuboutiquehub.databinding.ItemProductBinding
import com.atl.bakuboutiquehub.network.db.SavedProductsManager

class ProductAdapter(
    private val productList: List<Product>,
    private val showSaveButton: Boolean,
    private val onFavoriteChanged: (() -> Unit)? = null, // Yeni: Status dəyişəndə xəbər vermək üçün
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        holder.binding.apply {
            tvProductName.text = product.name
            tvPrice.text = product.price
            ivProduct.setImageResource(product.imageRes)
            tvStock.text = "${product.stock} left"
            tvReviewCount.text = "(${product.reviews} Reviews)"

            btnSave.visibility = if (showSaveButton) View.VISIBLE else View.GONE

            // Başlanğıc ikon vəziyyəti
            val isSaved = SavedProductsManager.isSaved(product)
            btnSave.setImageResource(if (isSaved) R.drawable.saved else R.drawable.unsaved)

            btnSave.setOnClickListener {
                SavedProductsManager.toggleProduct(product)

                // Əgər LikeFragment-dəyiksə, siyahını yeniləməyi tələb et
                onFavoriteChanged?.invoke()

                // İkonu dərhal dəyiş (digər səhifələr üçün lazımdır)
                val nowSaved = SavedProductsManager.isSaved(product)
                btnSave.setImageResource(if (nowSaved) R.drawable.saved else R.drawable.unsaved)
            }

            root.setOnClickListener { onItemClick(product) }
        }
    }

    override fun getItemCount(): Int = productList.size
}