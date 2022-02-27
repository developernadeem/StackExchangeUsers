package com.proxidize.stackexchangeusers.features.mainscreen

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.haroldadmin.cnradapter.NetworkResponse
import com.proxidize.stackexchangeusers.R
import com.proxidize.stackexchangeusers.data.models.StackExchangeResponse
import com.proxidize.stackexchangeusers.databinding.FragmentSearchBinding
import com.proxidize.stackexchangeusers.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
    private lateinit var binding: FragmentSearchBinding

    private val viewModel by viewModels<SearchViewModel>()
    private val adapter by lazy { SearchAdapter() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        binding.apply {
            inputEditText.doAfterTextChanged {
                binding.materialButton.isEnabled = it.toString().isNotEmpty()
            }
            materialButton.setOnClickListener {
                performSearch()
            }
            recyclerView.adapter = adapter
            // add divider line between items
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                ).apply {
                    ContextCompat.getDrawable(requireContext(), R.drawable.divider)
                        ?.let { setDrawable(it) }
                }
            )
        }
        viewModel.users.observe(viewLifecycleOwner) { response ->
            hideProgress()
            when (response) {
                is NetworkResponse.Success -> showResults(response)
                is NetworkResponse.ServerError -> showError(response.body.toString())
                is NetworkResponse.NetworkError -> showError(
                    response.error.localizedMessage ?: getString(R.string.network_error)
                )
                is NetworkResponse.UnknownError -> showError(
                    response.error.localizedMessage ?: getString(R.string.something_went_wrong)
                )
            }
        }
    }

    private fun showResults(response: NetworkResponse.Success<StackExchangeResponse>) {
        binding.recyclerView.isVisible = response.body.items.isNotEmpty()
        binding.emptyState.empty.isVisible = response.body.items.isEmpty()
        adapter.submitList(response.body.items)
    }

    private fun performSearch() {
        binding.inputEditText.hideKeyboard()
        viewModel.setQuery(binding.inputEditText.text.toString())
        showProgress()
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun showProgress() {
        binding.progressBar.isVisible = true
    }

    private fun hideProgress() {
        binding.progressBar.isVisible = false
    }
}