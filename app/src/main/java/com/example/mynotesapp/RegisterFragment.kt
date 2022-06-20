package com.example.mynotesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.mynotesapp.databinding.FragmentRegisterBinding
import com.example.mynotesapp.model.singin.req.SignInReq
import com.example.mynotesapp.utils.NetworkResult
import com.example.mynotesapp.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding?= null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentRegisterBinding.inflate(inflater,container,false)
        binding.btnLogin.setOnClickListener {
                authViewModel.loginUser(SignInReq("Test@testW.com","324khk234","bibeka"))
//            findNavController().navigate(R.id.action_registerFragment_to_loginFrament)
        }

        binding.btnSignUp.setOnClickListener {
//            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)

//            authViewModel.registerUser(SignUpReq("Test@testW.com","324234","bibeka"))
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel.signInRes.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible=false
            when(it){
                is NetworkResult.Success->{
                   findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                }
                is NetworkResult.Error -> {
                    binding.txtError.text=it.message
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible=true
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding= null
    }

}