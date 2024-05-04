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

        moviesAdapter = MoviesAdapter().apply {
            setOnItemClickListener {

            }
        }

        binding.apply {

            rvItemsRecycler.apply {
                layoutManager = LinearLayoutManager(requireContext())
                val decoration =
                    DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
                addItemDecoration(decoration)
                adapter = moviesAdapter.withLoadStateHeaderAndFooter(
                    header = LoaderStateAdapter(),
                    footer = LoaderStateAdapter()
                )

            }

            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                    mViewModel.getMovies (null).collectLatest {
                        moviesAdapter.submitData(it)
                    }
                }
            }

            moviesAdapter.addLoadStateListener { state ->
                val refreshState = state.refresh
                rvItemsRecycler.isVisible = state.refresh != LoadState.Loading
                progress.isVisible = state.refresh == LoadState.Loading
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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}