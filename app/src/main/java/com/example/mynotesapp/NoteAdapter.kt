package com.example.mynotesapp

import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotesapp.databinding.NoteItemBinding
import com.example.mynotesapp.model.note.NoteResponse

class NoteAdapter () : ListAdapter<NoteResponse, NoteAdapter.NoteViewHolder>(ComparatorDiffUtil()){
    inner class NoteViewHolder(private val binding : NoteItemBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind(note : NoteResponse){
                    binding.title.text= note.title
                    binding.desc.text=note.title
                }
            }
    class ComparatorDiffUtil : DiffUtil.ItemCallback<NoteResponse>() {
        override fun areItemsTheSame(oldItem: NoteResponse, newItem: NoteResponse): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: NoteResponse, newItem: NoteResponse): Boolean {
            return oldItem == newItem
        }

    }
}