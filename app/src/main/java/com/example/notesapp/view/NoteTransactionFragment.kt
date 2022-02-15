package com.example.notesapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentNoteTransactionBinding
import com.example.notesapp.databinding.FragmentNotesBinding
import com.example.notesapp.model.Note
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashMap

class NoteTransactionFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private var _binding: FragmentNoteTransactionBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNoteTransactionBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var user = Firebase.auth.currentUser
        var db = FirebaseFirestore.getInstance()
        binding.saveButton.setOnClickListener {
            if (user!=null){
                var note = Note(user.uid, Timestamp.now(),binding.addNoteText.text.toString())

                val noteMap = hashMapOf(
                    "email" to note.id,
                    "date" to note.date,
                    "note" to note.note
                )
                db.collection("notes").document(note.id)
                    .set(noteMap)
                    .addOnSuccessListener { Toast.makeText(context,"Transaction Successful",Toast.LENGTH_LONG).show() }
                    .addOnFailureListener{it.localizedMessage}

            }
        }
    }

}