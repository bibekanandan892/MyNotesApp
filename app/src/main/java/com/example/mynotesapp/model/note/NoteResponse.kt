package com.example.mynotesapp.model.note

data class NoteResponse(
    val __v :Int,
    val _id :String,
    val createAt:String,
    val description : String,
    val title: String,
    val updateAt: String,
    val userId: String
)
