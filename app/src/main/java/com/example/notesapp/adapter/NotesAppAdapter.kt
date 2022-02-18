package com.example.notesapp.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.databinding.RecyclerRowBinding
import com.example.notesapp.model.Note
import com.example.notesapp.view.NotesFragmentDirections
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.NonCancellable.cancel

class NotesAppAdapter(var context : Context): RecyclerView.Adapter<NotesAppAdapter.NotesHolder>() {

    private lateinit var db : FirebaseFirestore

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

        db = Firebase.firestore

        //uzun basıldıgında silmek isteyip istemediğimizi soracak olan fonksiyon
        holder.binding.cardView.setOnLongClickListener {

            println(notesList[position].note)

            val snack = Snackbar.make(it,"Silmek istediğinize emin misiniz?",Snackbar.LENGTH_LONG)

            snack.setAction("YES", View.OnClickListener {
                // executed when DISMISS is clicked
                println(notesList[position].id)
                db.collection(notesList[position].id).document(notesList[position].documentId)
                    .delete()
                    .addOnSuccessListener {
                        Toast.makeText(context,"DocumentSnapshot successfully deleted!",Toast.LENGTH_LONG).show()
                        notifyDataSetChanged()
                    }
                    .addOnFailureListener { e -> e.localizedMessage}
                System.out.println(notesList[position].documentId)
            })
            snack.show()

            return@setOnLongClickListener true
        }

        //emirin
        holder.binding.cardView.setOnClickListener {
            val action = NotesFragmentDirections.actionNotesFragmentToUpdateNoteFragment(notesList[position])
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return notesList.count()
    }

}