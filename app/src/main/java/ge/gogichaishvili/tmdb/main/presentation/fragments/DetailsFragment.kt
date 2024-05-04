package ge.gogichaishvili.tmdb.main.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ge.gogichaishvili.tmdb.databinding.FragmentDetailsBinding
import ge.gogichaishvili.tmdb.main.presentation.fragments.base.BaseFragment
import ge.gogichaishvili.tmdb.main.presentation.viewmodels.DetailsViewModel

class DetailsFragment : BaseFragment<DetailsViewModel>(DetailsViewModel::class) {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}