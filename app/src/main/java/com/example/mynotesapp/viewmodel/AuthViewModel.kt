package com.example.mynotesapp.viewmodel

import android.text.TextUtils
import android.util.Patterns
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
import java.util.regex.Pattern
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
    fun validateCredentials(username:String,emailAddress:String,password:String,isLogin: Boolean): Pair<Boolean,String>{
        var result=Pair(true,"")
        if(!isLogin && TextUtils.isEmpty(username) || TextUtils.isEmpty(emailAddress) || TextUtils.isEmpty(password)){
            result=Pair(false,"Please provide the credentials")
        }else if(!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
            result=Pair(false,"Please provide the valid credentials")
        }else if(password.length <=5){
            result=Pair(false,"Password length should be greater then 5")
        }
        return result
    }

}