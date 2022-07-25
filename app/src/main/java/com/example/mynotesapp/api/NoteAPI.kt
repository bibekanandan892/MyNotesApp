package com.example.mynotesapp.api

import com.example.mynotesapp.model.note.NoteRequest
import com.example.mynotesapp.model.note.NoteResponse
import retrofit2.Response
import retrofit2.http.*

interface NoteAPI {
    @GET("/note")
    suspend fun getNote(): Response<List<NoteResponse>>

    @POST("/note")
    suspend fun createNote(@Body noteRequest: NoteRequest) : Response<NoteResponse>

    @PUT("/note/{noteId}")
    suspend fun updateNote(@Path("noteId")noteId:String, noteRequest: NoteRequest): Response<NoteResponse>

    @DELETE("/note/{noteId}")
    suspend fun deleteNode(@Path("noteId")noteId: String) : Response<NoteResponse>
}