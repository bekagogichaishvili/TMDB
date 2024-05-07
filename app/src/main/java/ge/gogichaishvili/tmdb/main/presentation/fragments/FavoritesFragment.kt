package ge.gogichaishvili.tmdb.main.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import es.dmoral.toasty.Toasty
import ge.gogichaishvili.tmdb.R
import ge.gogichaishvili.tmdb.app.network.ApiEndpoints
import ge.gogichaishvili.tmdb.app.network.Resource
import ge.gogichaishvili.tmdb.databinding.FragmentFavoritesBinding
import ge.gogichaishvili.tmdb.main.data.network.paging.LoaderStateAdapter
import ge.gogichaishvili.tmdb.main.presentation.adapters.RoomMoviesAdapter
import ge.gogichaishvili.tmdb.main.presentation.fragments.base.BaseFragment
import ge.gogichaishvili.tmdb.main.presentation.viewmodels.FavoritesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().moveTaskToBack(true)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        mViewModel.getMovies()

        setupRecyclerView()
        observeMovies()
        handleLoadStates()

    }

    private fun setupRecyclerView() {
        roomMoviesAdapter = RoomMoviesAdapter().apply {
            setOnItemClickListener {

            }
            setOnDeleteClickListener {

                val dialog = CustomDialogFragment().apply {
                    arguments = Bundle().apply {
                        putLong("id", it.id.toLong())
                    }
                }
                dialog.show(childFragmentManager, "CustomDialogFragment")

            }
        }
        binding.rvItemsRecycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = roomMoviesAdapter.withLoadStateHeaderAndFooter(
                header = LoaderStateAdapter(),
                footer = LoaderStateAdapter()
            )
            itemAnimator = DefaultItemAnimator().apply {
                addDuration = 250
                removeDuration = 250
            }
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

    @SuppressLint("NotifyDataSetChanged")
    fun deleteMovie (id: Long) {
        mViewModel.deleteMovie(id)
        roomMoviesAdapter.refresh()
        roomMoviesAdapter.notifyDataSetChanged()
    }

    override fun bindObservers() {

        mViewModel.statusMessage.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                Toasty.success(
                    requireContext(), R.string.movie_deleted, Toast.LENGTH_SHORT, true
                ).show()
            } else {
                Toasty.error(requireContext(), R.string.error, Toast.LENGTH_SHORT, true)
                    .show()
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}