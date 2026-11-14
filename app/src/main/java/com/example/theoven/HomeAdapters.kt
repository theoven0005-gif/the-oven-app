package com.example.theoven

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.theoven.databinding.ItemBannerBinding
import com.example.theoven.databinding.ItemCategoryBinding
import com.example.theoven.databinding.ItemProductBinding
import com.example.theoven.databinding.ItemProductFeaturedBinding

class BannerAdapter(private val banners: List<Banner>) :
    RecyclerView.Adapter<BannerAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemBannerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.imgBanner.setImageResource(banners[position].imageRes)
    }

    override fun getItemCount() = banners.size
}

class CategoryAdapter(private val categories: List<Category>, private val onCategoryClick: (Category) -> Unit) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.binding.tvCategoryName.text = category.name
        holder.binding.imgCategory.setImageResource(category.icon)
        holder.binding.root.setOnClickListener { onCategoryClick(category) }
    }

    override fun getItemCount() = categories.size
}

class ProductAdapter(private val products: List<Product>, private val onItemClick: (Product) -> Unit) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.binding.tvProductName.text = product.name
        holder.binding.tvProductPrice.text = "Rs. ${product.price}"
        holder.binding.imgProduct.setImageResource(product.imageRes)
        
        holder.binding.root.setOnClickListener { onItemClick(product) }
        holder.binding.btnAddToCartCard.setOnClickListener { onItemClick(product) }
    }

    override fun getItemCount() = products.size
}

class FeaturedAdapter(private val products: List<Product>, private val onItemClick: (Product) -> Unit) :
    RecyclerView.Adapter<FeaturedAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemProductFeaturedBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductFeaturedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.binding.tvFeaturedName.text = product.name
        holder.binding.tvFeaturedPrice.text = "Rs. ${product.price}"
        holder.binding.imgFeatured.setImageResource(product.imageRes)
        holder.binding.root.setOnClickListener { onItemClick(product) }
    }

    override fun getItemCount() = products.size
}