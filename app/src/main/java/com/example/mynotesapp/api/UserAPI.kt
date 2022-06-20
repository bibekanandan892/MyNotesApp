package com.example.mynotesapp.api

import com.example.mynotesapp.model.signup.req.SignUpReq
import com.example.mynotesapp.model.signup.res.SignUpRes
import com.example.mynotesapp.model.singin.req.SignInReq
import com.example.mynotesapp.model.singin.res.SignInRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPI {

    @POST("/users/signup")
    suspend fun signUp(@Body singUpReq: SignUpReq) : Response<SignUpRes>

    @POST("/users/signin")
    suspend fun signIn(@Body signInReq: SignInReq): Response<SignInRes>
}