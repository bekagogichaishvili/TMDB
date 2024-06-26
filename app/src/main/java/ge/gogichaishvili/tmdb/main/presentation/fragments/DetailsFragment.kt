package ge.gogichaishvili.tmdb.main.presentation.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import es.dmoral.toasty.Toasty
import ge.gogichaishvili.tmdb.R
import ge.gogichaishvili.tmdb.app.network.ApiEndpoints
import ge.gogichaishvili.tmdb.app.network.Resource
import ge.gogichaishvili.tmdb.databinding.FragmentDetailsBinding
import ge.gogichaishvili.tmdb.main.data.local.entities.FavoriteMovieModel
import ge.gogichaishvili.tmdb.main.presentation.viewmodels.DetailsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.IOException
import java.net.URL

class DetailsFragment : BottomSheetDialogFragment() {

    private val mViewModel: DetailsViewModel by viewModel()

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener {
            val dialog = it as BottomSheetDialog
            val bottomSheet =
                dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.skipCollapsed = true
            behavior.peekHeight = 0
            behavior.isHideable = false
            behavior.isDraggable = false

        }
        return bottomSheetDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt(ARG_MY_INT) ?: 0
        mViewModel.getMovieDetails(id)
        observe()

        binding.rootView.updateLayoutParams<FrameLayout.LayoutParams> {
            val h = (resources.displayMetrics.heightPixels * 0.95).toInt()
            height = h
        }

        binding.btnClose.setOnClickListener {
            dismiss()
        }

        binding.btnFavorite.setOnClickListener {
            fetchAndSaveMovieData()
        }
    }

    private fun fetchAndSaveMovieData() {
        showLoading(true)
        mViewModel.requestStateLiveData.value?.data?.let { currentMovie ->
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val imageUrl = URL(ApiEndpoints.IMAGE_PATH + currentMovie.backdropPath)
                    val imageData = imageUrl.readBytes()

                    val movieToFavorite = FavoriteMovieModel(
                        id = currentMovie.id ?: 0,
                        title = currentMovie.originalTitle ?: "Unknown Title",
                        overview = currentMovie.overview ?: "No description available.",
                        image = imageData
                    )

                    mViewModel.insertMovie(movieToFavorite)

                } catch (e: IOException) {
                    showErrorToast("Failed to fetch image data. Please try again.")
                    e.printStackTrace()
                } catch (e: Exception) {
                    showErrorToast("An unexpected error occurred. Please try again later.")
                    e.printStackTrace()
                } finally {
                    showLoading(false)
                }
            }
        } ?: run {
            Toast.makeText(requireContext(), "No movie data available", Toast.LENGTH_SHORT).show()
            showLoading(false)
        }
    }

    private fun showErrorToast(message: String) {
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(show: Boolean) {
        CoroutineScope(Dispatchers.Main).launch {
            binding.pbBtn.isVisible = show
        }
    }

    private fun observe() {
        mViewModel.requestStateLiveData.observe(viewLifecycleOwner) {
            when (it) {

                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is Resource.Success -> {

                    binding.apply {
                        progressBar.isVisible = false
                        btnFavorite.isVisible = true
                        tvTitle.text = it.data?.originalTitle.toString()
                        tvOverview.text = it.data?.overview.toString()
                        Glide.with(requireContext())
                            .load(ApiEndpoints.IMAGE_PATH + it.data?.backdropPath)
                            .error(R.drawable.no_poster)
                            .centerCrop()
                            .into(ivPoster)
                    }

                }

                is Resource.Error -> {
                    binding.apply {
                        progressBar.isVisible = false
                        btnFavorite.isVisible = false
                    }

                    Toasty.error(
                        requireContext(), it.message.toString(), Toast.LENGTH_SHORT, true
                    ).show()
                }
            }
        }

        mViewModel.statusMessage.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                Toasty.success(
                    requireContext(), R.string.movie_added, Toast.LENGTH_SHORT, true
                ).show()
                dismiss()
            } else {
                Toasty.error(requireContext(), R.string.error, Toast.LENGTH_SHORT, true)
                    .show()
            }

        }

    }

    companion object {
        private const val ARG_MY_INT = "myIntArgument"

        fun newInstance(myInt: Int): DetailsFragment {
            val args = Bundle()
            args.putInt(ARG_MY_INT, myInt)
            val fragment = DetailsFragment().apply {
                arguments = args
                isCancelable = false
            }
            return fragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}