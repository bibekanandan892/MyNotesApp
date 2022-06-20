package com.example.mynotesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotesapp.model.signup.req.SignUpReq
import com.example.mynotesapp.model.signup.res.SignUpRes
import com.example.mynotesapp.model.singin.req.SignInReq
import com.example.mynotesapp.model.singin.res.SignInRes
import com.example.mynotesapp.repository.UserRepository
import com.example.mynotesapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel(){
    val signUpRes: LiveData<NetworkResult<SignUpRes>>
    get() = userRepository.signUpRes

    val signInRes: LiveData<NetworkResult<SignInRes>>
    get() = userRepository.signInRes

    fun registerUser(signUpReq: SignUpReq){
        viewModelScope.launch {
            userRepository.registerUser(signUpReq)
        }
    }
    fun loginUser(signInReq: SignInReq){
        viewModelScope.launch {
            userRepository.loginUser(signInReq)
        }
    }

}