package com.example.mynotesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.mynotesapp.databinding.FragmentNoteBinding
import com.example.mynotesapp.model.note.NoteRequest
import com.example.mynotesapp.model.note.NoteResponse
import com.example.mynotesapp.utils.NetworkResult
import com.example.mynotesapp.viewmodel.NoteViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteFragment : Fragment() {
    private var _binding: FragmentNoteBinding? =null

    private val binding  get() = _binding!!

    private var note : NoteResponse? =null
    private val noteViewModel by viewModels<NoteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialData()
        bindHandlers()
        bindObservers()
    }

    private fun bindObservers() {
        noteViewModel.statusLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                is NetworkResult.Error -> TODO()
                is NetworkResult.Loading -> TODO()
                is NetworkResult.Success -> {
                    findNavController().popBackStack()
                }
            }
        })
    }

    private fun bindHandlers() {
        binding.btnDelete.setOnClickListener {
            note?.let {
                noteViewModel.deleteNote(it!!._id)
            }
        }
        binding.btnSubmit.setOnClickListener {
            val title = binding.txtTitle.text.toString()
            val description = binding.txtDescription.text.toString()
            val noteRequest = NoteRequest(description,title)

            if(note == null){
                noteViewModel.createNote(noteRequest)
            }else{
                noteViewModel.updateNote(note!!._id,noteRequest)
            }
        }
    }

    private fun setInitialData() {
        val jsonNote = arguments?.getString("note")
        if(jsonNote != null){
            binding.addEditText.text = "Edit Note"
            note = Gson().fromJson(jsonNote,NoteResponse::class.java)
            note?.let {
                binding.txtTitle.setText(it.title)
                binding.txtDescription.setText(it.description)
            }
        }
        else{
            binding.addEditText.text= "Add Note"
        }
    }

}