package com.example.notesapp.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.databinding.RecyclerRowBinding
import com.example.notesapp.model.Note
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.NonCancellable.cancel

class NotesAppAdapter(var context : Context): RecyclerView.Adapter<NotesAppAdapter.NotesHolder>() {

    var notesList = listOf<Note>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class NotesHolder(val binding: RecyclerRowBinding):RecyclerView.ViewHolder(binding.root)

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