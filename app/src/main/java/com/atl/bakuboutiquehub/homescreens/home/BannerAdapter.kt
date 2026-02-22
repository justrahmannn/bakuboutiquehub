package com.atl.bakuboutiquehub.homescreens.home

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.atl.bakuboutiquehub.R

class BannerAdapter(private val items: List<Int>) : RecyclerView.Adapter<BannerAdapter.VH>() {
    class VH(val img: ImageView) : RecyclerView.ViewHolder(img)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val iv = ImageView(parent.context).apply {
            layoutParams = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            ).apply {
                setMargins(10, 0, 10, 0) // Sol və sağdan boşluq
            }
            scaleType = ImageView.ScaleType.CENTER_CROP
            // Şəkildəki kimi küncləri oval etmək üçün:
            clipToOutline = true
            background = context.getDrawable(R.drawable.banner_rounded_bg) // opsional
        }
        return VH(iv)
    }
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.img.setImageResource(items[position])
    }
    override fun getItemCount() = items.size
}