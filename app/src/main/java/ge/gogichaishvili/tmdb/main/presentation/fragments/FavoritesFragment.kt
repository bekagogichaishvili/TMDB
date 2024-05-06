package ge.gogichaishvili.tmdb.main.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import ge.gogichaishvili.tmdb.R
import ge.gogichaishvili.tmdb.databinding.FragmentFavoritesBinding
import ge.gogichaishvili.tmdb.main.data.network.paging.LoaderStateAdapter
import ge.gogichaishvili.tmdb.main.presentation.adapters.RoomMoviesAdapter
import ge.gogichaishvili.tmdb.main.presentation.fragments.base.BaseFragment
import ge.gogichaishvili.tmdb.main.presentation.viewmodels.FavoritesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoritesFragment : BaseFragment<FavoritesViewModel>(FavoritesViewModel::class) {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var roomMoviesAdapter: RoomMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.getMovies()

        setupRecyclerView()
        observeMovies()
        handleLoadStates()
    }

    private fun setupRecyclerView() {
        roomMoviesAdapter = RoomMoviesAdapter().apply {
            setOnItemClickListener {
                val detailsFragment = DetailsFragment.newInstance(it.id)
                detailsFragment.show(parentFragmentManager, null)
            }

        }
        binding.rvItemsRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = roomMoviesAdapter.withLoadStateHeaderAndFooter(
                header = LoaderStateAdapter(),
                footer = LoaderStateAdapter()
            )
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun observeMovies() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.getMovies().collectLatest {
                    roomMoviesAdapter.submitData(it)
                }
            }
        }
    }

    private fun handleLoadStates() {
        roomMoviesAdapter.addLoadStateListener { state ->
            val refreshState = state.refresh
            binding.rvItemsRecycler.isVisible = state.refresh != LoadState.Loading
            binding.progress.isVisible = state.refresh == LoadState.Loading
            if (refreshState is LoadState.NotLoading && roomMoviesAdapter.itemCount == 0) {
                Snackbar.make(
                    binding.root,
                    R.string.not_found,
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (refreshState is LoadState.Error) {
                Snackbar.make(
                    binding.root,
                    refreshState.error.localizedMessage ?: "",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}