package com.proxidize.stackexchangeusers.features.mainscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.proxidize.stackexchangeusers.data.models.User
import com.proxidize.stackexchangeusers.databinding.ListItemSearchBinding


/**
 * Adapter for the [RecyclerView] in [SearchFragment].
 */
class SearchAdapter : ListAdapter<User, RecyclerView.ViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlantViewHolder(
            ListItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val plant = getItem(position)
        (holder as PlantViewHolder).bind(plant)
    }

    class PlantViewHolder(
        private val binding: ListItemSearchBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private fun navigateToDetailsScreen(user: User, view: View) {
            val action = SearchFragmentDirections.actionMainScreenFragmentToDetailsFragment(user)
            view.findNavController().navigate(action)

        }

        fun bind(item: User) {
            binding.apply {
                tvUserId.text = item.userId.toString()
                tvUserName.text = item.displayName
                root.setOnClickListener {
                    navigateToDetailsScreen(item, it)
                }
            }
        }
    }
}

private class UserDiffCallback : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.userId == newItem.userId
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}