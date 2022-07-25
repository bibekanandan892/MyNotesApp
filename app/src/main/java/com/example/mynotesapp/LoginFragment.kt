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
import com.example.mynotesapp.databinding.FragmentLoginFramentBinding
import com.example.mynotesapp.model.signup.req.SignUpReq
import com.example.mynotesapp.model.singin.req.SignInReq
import com.example.mynotesapp.utils.NetworkResult
import com.example.mynotesapp.utils.TokenManager
import com.example.mynotesapp.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginFramentBinding?=null
    private val binding get ()= _binding!!
    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding=FragmentLoginFramentBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            val validationResult=validateUserInput()
            if(validationResult.first){
                authViewModel.loginUser(getUserRequest())
            }else{
                binding.txtError.text=validationResult.second
            }
        }
        authViewModel.signInRes.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible=false
            when(it){
                is NetworkResult.Success->{
                    tokenManager.saveToken(it.data!!.token)
                    findNavController().navigate(R.id.action_loginFrament_to_mainFragment)
                }
                is NetworkResult.Error -> {
                    binding.txtError.text=it.message
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible=true
                }
            }
        })
        binding.btnSignUp.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    private fun getUserRequest(): SignInReq {
        val emailAddress=binding.txtEmail.text.toString()
        val password=binding.txtPassword.text.toString()
        return SignInReq(emailAddress,password,"")
    }

    private fun validateUserInput() :Pair<Boolean,String>{
        val userRequest=getUserRequest()
        return authViewModel.validateCredentials(userRequest.username,userRequest.email,userRequest.password,true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}