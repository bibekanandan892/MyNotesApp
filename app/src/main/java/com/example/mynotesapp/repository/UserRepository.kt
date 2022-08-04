package com.example.mynotesapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mynotesapp.api.UserAPI
import com.example.mynotesapp.model.signup.req.SignUpReq
import com.example.mynotesapp.model.signup.res.SignUpRes
import com.example.mynotesapp.model.singin.req.SignInReq
import com.example.mynotesapp.model.singin.res.SignInRes
import com.example.mynotesapp.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository  @Inject constructor(private val userAPI: UserAPI) {
    private val _signUpRes= MutableLiveData<NetworkResult<SignUpRes>>()
    val signUpRes : LiveData<NetworkResult<SignUpRes>>
    get() = _signUpRes

    private val _signInRes = MutableLiveData<NetworkResult<SignInRes>>()
    val signInRes: LiveData<NetworkResult<SignInRes>>
    get() = _signInRes


    suspend fun registerUser(signUpReq: SignUpReq){
        _signUpRes.value=NetworkResult.Loading()
        val response= userAPI.signUp(signUpReq)
        if(response.isSuccessful && response.body()!=null){
            _signUpRes.postValue(NetworkResult.Success(response.body()!!))
        }
        else if(response.errorBody() !=null){
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())

            _signUpRes.value=NetworkResult.Error(errorObj.getString("message"))
        }
        else{
            _signUpRes.value=NetworkResult.Error("Something is wrong")
        }
    }
    suspend fun loginUser(signInReq: SignInReq){
        _signInRes.value=NetworkResult.Loading()
        val response= userAPI.signIn(signInReq)
        if (response.isSuccessful && response.body() != null) {
            _signInRes.value = NetworkResult.Success(response.body()!!)
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _signInRes.value = NetworkResult.Error(errorObj.getString("message"))
        } else {
            _signInRes.value = NetworkResult.Error("Something is Wrong")
        }
    }



}