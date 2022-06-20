package com.example.mynotesapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mynotesapp.api.UserAPI
import com.example.mynotesapp.model.signup.req.SignUpReq
import com.example.mynotesapp.model.signup.res.SignUpRes
import com.example.mynotesapp.model.singin.req.SignInReq
import com.example.mynotesapp.model.singin.res.SignInRes
import com.example.mynotesapp.utils.NetworkResult
import javax.inject.Inject

class UserRepository  @Inject constructor(private val userAPI: UserAPI) {
    private val _signUpRes= MutableLiveData<NetworkResult<SignUpRes>>()

    val signUpRes : LiveData<NetworkResult<SignUpRes>>
    get() = _signUpRes

    private val _signInRes = MutableLiveData<NetworkResult<SignInRes>>()

    val signInRes: LiveData<NetworkResult<SignInRes>>
    get() = _signInRes


    suspend fun registerUser(signUpReq: SignUpReq){
        val response= userAPI.signUp(signUpReq)
        if(response.isSuccessful && response.body()!=null){
            _signUpRes.postValue(NetworkResult.Success(response.body()!!))
        }
        else if(response.errorBody() !=null){
            _signUpRes.value=NetworkResult.Error("Something is wrong")
        }
        else{
            _signUpRes.value=NetworkResult.Error("Something is wrong")
        }
    }
    suspend fun loginUser(signInReq: SignInReq){
        _signInRes.value=NetworkResult.Loading()
        val response= userAPI.signIn(signInReq)
        if(response.isSuccessful && response.body()!=null){
            _signInRes.value=NetworkResult.Success(response.body()!!)
        }
        else if(response.errorBody()!==null){
            _signInRes.value=NetworkResult.Error("Something is Wrong")
        }
        else{
            _signInRes.value= NetworkResult.Error("Something is Wrong")
        }
    }

}