package ge.gogichaishvili.tmdb.login.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ge.gogichaishvili.tmdb.R
import ge.gogichaishvili.tmdb.app.network.Resource
import ge.gogichaishvili.tmdb.app.tools.SharedPreferenceManager
import ge.gogichaishvili.tmdb.app.tools.Utils
import ge.gogichaishvili.tmdb.databinding.FragmentLoginBinding
import ge.gogichaishvili.tmdb.login.presentation.viewmodels.LoginViewModel
import ge.gogichaishvili.tmdb.main.presentation.activities.MainActivity
import ge.gogichaishvili.tmdb.main.presentation.fragments.base.BaseFragment
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<LoginViewModel>(LoginViewModel::class) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvGuest.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToMainFragment()
            findNavController().navigate(action)
        }

        binding.btnLogin.setOnClickListener {
            mViewModel.onLogin(
                binding.etUsername.text.toString().trim(),
                binding.etPassword.text.toString().trim()
            )
        }

        binding.tvEng.setOnClickListener {
            SharedPreferenceManager(requireContext()).saveSelectedLanguageCode("en")
            restart()
        }

        binding.tvGeo.setOnClickListener {
            SharedPreferenceManager(requireContext()).saveSelectedLanguageCode("ka")
            restart()
        }

    }

    private fun restart () {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
    }

    override fun bindObservers() {
        lifecycleScope.launch {
            mViewModel.authFlow.collect {
                when (it) {

                    is Resource.Loading -> {
                    }

                    is Resource.Error -> {
                        Toast.makeText(
                            requireContext(),
                            it.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is Resource.Success -> {
                        println("token is ${it.data!!.request_token}")
                    }
                }
            }
        }

        lifecycleScope.launch {
            mViewModel.loginFlow.collect {
                when (it) {

                    is Resource.Loading -> {
                    }

                    is Resource.Error -> {
                        Toast.makeText(
                            requireContext(),
                            it.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is Resource.Success -> {
                        val action = LoginFragmentDirections.actionLoginFragmentToMainFragment()
                        findNavController().navigate(action)
                    }
                }
            }
        }

        mViewModel.viewStateLiveData.observe(viewLifecycleOwner) {
            if (!it.isValid) {
                val message = String.format(resources.getString(it.errorMessageRes))
                Utils.playAlertSound(requireContext())
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                val shakeAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
                binding.apply {
                    etUsername.startAnimation(shakeAnimation)
                    etPassword.startAnimation(shakeAnimation)
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}