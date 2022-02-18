package com.example.notesapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.RecyclerRowBinding
import com.example.notesapp.model.Note
import com.example.notesapp.util.AdapterClickListener
import com.example.notesapp.view.NotesFragmentDirections
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NotesAppAdapter(
    var context : Context,
    private val clickListener: AdapterClickListener
): RecyclerView.Adapter<NotesAppAdapter.NotesHolder>() {

    private lateinit var db : FirebaseFirestore

    var notesList = ArrayList<Note>()
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
        holder.binding.recyclerRowDateText.text= notesList[position].date.toDate().toString()
        holder.binding.recyclerRowNoteText.text = notesList[position].note

        db = Firebase.firestore

        //uzun basıldıgında silmek isteyip istemediğimizi soracak olan fonksiyon
        holder.binding.cardView.setOnLongClickListener {

            val snack = Snackbar.make(it,"Silmek istediğinize emin misiniz?",Snackbar.LENGTH_LONG)

            snack.setAction("YES") {
                // executed when DISMISS is clicked
                db.collection(notesList[position].id).document(notesList[position].documentId)
                    .delete()
                    .addOnSuccessListener {
                        clickListener.adapterClickListener()
                        Toast.makeText(
                            context,
                            "DocumentSnapshot successfully deleted!",
                            Toast.LENGTH_LONG
                        ).show()
                        notifyDataSetChanged()
                    }
                    .addOnFailureListener { e -> e.localizedMessage }
            }
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