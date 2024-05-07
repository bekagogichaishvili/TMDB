package ge.gogichaishvili.tmdb.main.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import ge.gogichaishvili.tmdb.R
import ge.gogichaishvili.tmdb.databinding.FragmentMainBinding
import ge.gogichaishvili.tmdb.main.data.network.paging.LoaderStateAdapter
import ge.gogichaishvili.tmdb.main.presentation.adapters.MoviesAdapter
import ge.gogichaishvili.tmdb.main.presentation.fragments.base.BaseFragment
import ge.gogichaishvili.tmdb.main.presentation.viewmodels.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment : BaseFragment<MainViewModel>(MainViewModel::class) {

    override fun isGlobal() = true

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.getMovies(null)

        setupRecyclerView()
        setupSearch()

        observeMovies()
        handleLoadStates()
    }

    private fun setupRecyclerView() {
        moviesAdapter = MoviesAdapter().apply {
            setOnItemClickListener {
               val detailsFragment = DetailsFragment.newInstance(it.id)
               detailsFragment.show(parentFragmentManager, null)

                //val action = MainFragmentDirections.actionMainFragmentToFavoritesFragment()
                //findNavController().navigate(action)
            }

        }
        binding.rvItemsRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = moviesAdapter.withLoadStateHeaderAndFooter(
                header = LoaderStateAdapter(),
                footer = LoaderStateAdapter()
            )
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupSearch() {
        binding.etSearch.apply {
            doAfterTextChanged { text ->
                performSearch(text.toString().trim())
            }
            setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP && event.rawX >= right - compoundDrawables[2].bounds.width()) {
                    text?.clear()
                    true
                } else false
            }
        }
    }

    private fun observeMovies() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.getMovies(binding.etSearch.text.toString().trim()).collectLatest {
                    moviesAdapter.submitData(it)
                }
            }
        }
    }

    private fun handleLoadStates() {
        moviesAdapter.addLoadStateListener { state ->
            val refreshState = state.refresh
            binding.rvItemsRecycler.isVisible = state.refresh != LoadState.Loading
            binding.progress.isVisible = state.refresh == LoadState.Loading
            if (refreshState is LoadState.NotLoading && moviesAdapter.itemCount == 0) {
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

    private fun performSearch(search: String?) {
        viewLifecycleOwner.lifecycleScope.launch {
            mViewModel.getMovies(search).collectLatest {
                moviesAdapter.clear()
                moviesAdapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
