package com.proxidize.stackexchangeusers.features.details

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.haroldadmin.cnradapter.NetworkResponse
import com.proxidize.stackexchangeusers.R
import com.proxidize.stackexchangeusers.data.models.TopBadge
import com.proxidize.stackexchangeusers.data.models.User
import com.proxidize.stackexchangeusers.databinding.FragmentDetailsBinding
import com.proxidize.stackexchangeusers.utils.epochToIso8601
import dagger.hilt.android.AndroidEntryPoint

// the fragment initialization parameters
private const val ARG_USER = "user"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private lateinit var binding: FragmentDetailsBinding
    private val viewModel by viewModels<DetailsViewModel>()
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.getParcelable(ARG_USER)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsBinding.bind(view)
        user?.let {
            bindUser(it)
            viewModel.serUserId(it.userId)
        }

        viewModel.topTags.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResponse.Success -> showTopBadges(response.body.items)
                else -> showError(getString(R.string.something_went_wrong))
            }

        }
    }

    private fun showTopBadges(topBadge: List<TopBadge>) {
        val badges = topBadge.joinToString(separator = "\n") { it.tagName }
        binding.tvTopTags.text = badges

    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun bindUser(user: User) {
        Glide.with(requireActivity())
            .load(user.profileImage)
            .into(binding.userImage)
        binding.apply {
            tvUserName.text = user.displayName
            tvLocation.text = user.location
            tvReputation.text = user.reputation.toString()
            user.badgeCount.run {
                tvBadges.text =
                    resources.getString(R.string.badges_count, gold, silver, bronze)
            }
            tvCreatedDate.text = epochToIso8601(user.creationDate)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param user is user whose details needs to  show
         * @return A new instance of fragment DetailsFragment.
         */
        @JvmStatic
        fun newInstance(user: User) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_USER, user)
                }
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            findNavController().popBackStack()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}