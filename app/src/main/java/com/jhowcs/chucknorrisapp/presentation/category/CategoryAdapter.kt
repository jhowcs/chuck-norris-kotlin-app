package com.jhowcs.chucknorrisapp.presentation.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jhowcs.chucknorrisapp.R

class CategoryAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val categories = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_categories, parent, false)

        return CategoryVH(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CategoryVH) holder.bind(categories[position])
    }

    fun populateCategories(categories: List<String>) {
        this.categories.addAll(categories)
        notifyDataSetChanged()
    }

    class CategoryVH(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val txtCategory by lazy { itemView.findViewById<TextView>(R.id.txtCategory) }

        fun bind(category: String) {

            txtCategory.text = firstLetterToUppercase(category)
        }

        private fun firstLetterToUppercase(category: String): String {
            val firstLetter = category[0].toUpperCase()
            return firstLetter + category.substring(1)
        }
    }
}