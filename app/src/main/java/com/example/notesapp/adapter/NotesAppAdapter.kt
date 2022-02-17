package com.example.notesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.RecyclerRowBinding
import com.example.notesapp.model.Note

class NotesAppAdapter(var notesList: ArrayList<Note>): RecyclerView.Adapter<NotesAppAdapter.NotesHolder>() {

    class NotesHolder(val binding: RecyclerRowBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NotesHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        holder.binding.recyclerRowDateText.text=notesList.get(position).date.toDate().toString()
        holder.binding.recyclerRowNoteText.text = notesList.get(position).note
    }

    override fun getItemCount(): Int {
        return notesList.count()
    }

}