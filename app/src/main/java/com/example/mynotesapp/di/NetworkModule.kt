package com.example.mynotesapp.di

import com.example.mynotesapp.api.AuthInterceptor
import com.example.mynotesapp.api.NoteAPI
import com.example.mynotesapp.api.UserAPI
import com.example.mynotesapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofitBuilder() : Retrofit.Builder{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
    }

    @Provides
    @Singleton
    fun providesUserAPI(retrofitBuilder: Retrofit.Builder): UserAPI{
        return retrofitBuilder.build().create(UserAPI::class.java)
    }
    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor):OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideNoteAPI(okHttpClient: OkHttpClient,retrofitBuilder: Retrofit.Builder):NoteAPI{
        return retrofitBuilder
            .client(okHttpClient)
            .build()
            .create(NoteAPI::class.java)
    }


}