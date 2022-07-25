package com.example.mynotesapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mynotesapp.api.NoteAPI
import com.example.mynotesapp.model.note.NoteRequest
import com.example.mynotesapp.model.note.NoteResponse
import com.example.mynotesapp.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteAPI: NoteAPI) {
    private val _notesLiveData = MutableLiveData<NetworkResult<List<NoteResponse>>>()
    val noteLiveData: LiveData<NetworkResult<List<NoteResponse>>>
    get() = _notesLiveData

    private val _statusLiveData= MutableLiveData<NetworkResult<String>>()

    val statusLiveData : LiveData<NetworkResult<String>>
    get() = _statusLiveData

    suspend fun getNotes(){
        _notesLiveData.postValue(NetworkResult.Loading())

        val response = noteAPI.getNote()
        if(response.isSuccessful && response.body()!=null){
            _notesLiveData.postValue(NetworkResult.Success(response.body()!!))
        }
        else if(response.errorBody() !=null){
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())

            _notesLiveData.value=NetworkResult.Error(errorObj.getString("message"))
        }
        else{
            _notesLiveData.value=NetworkResult.Error("Something is wrong")
        }
    }

    suspend fun createNote(noteRequest: NoteRequest){
        _statusLiveData.value= NetworkResult.Loading()
        val response= noteAPI.createNote(noteRequest)
        handleResponse(response,"Notes Created")
    }
    suspend fun deleteNote(noteId : String){
        _statusLiveData.value= NetworkResult.Loading()
        val response = noteAPI.deleteNode(noteId)
        handleResponse(response,"Note Deleted")

    }
    suspend fun updateNote(noteId: String, noteRequest: NoteRequest){
        _statusLiveData.value= NetworkResult.Loading()
        val  response = noteAPI.updateNote(noteId,noteRequest)
        handleResponse(response,"Note Updated")
    }
    private fun handleResponse(response: Response<NoteResponse>,message: String) {
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.value = NetworkResult.Success(message)
        } else {
            _statusLiveData.value = NetworkResult.Error("Something went wrong")
        }
    }
}