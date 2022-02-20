package com.example.notesapp.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.notesapp.R
import com.example.notesapp.adapter.NotesAppAdapter
import com.example.notesapp.databinding.FragmentNotesBinding
import com.example.notesapp.model.Note
import com.example.notesapp.util.AdapterClickListener
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NotesFragment : Fragment(), AdapterClickListener {
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var dataBase: FirebaseFirestore
    private lateinit var noteArrayList: ArrayList<Note>
    private lateinit var notesAppAdapter: NotesAppAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteArrayList = ArrayList()

        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        notesAppAdapter = NotesAppAdapter(requireContext(), this)
        binding.recyclerView.adapter = notesAppAdapter

        setHasOptionsMenu(true)

        auth = Firebase.auth

        dataBase = Firebase.firestore
        getNotes()

        //not ekleme sayfasına geçiş
        val fab: View = binding.fabBtn
        fab.setOnClickListener {
            val action = NotesFragmentDirections.actionNotesFragmentToNoteTransactionFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun getNotes() {
        noteArrayList.clear()
        dataBase.collection(auth.uid.toString()).addSnapshotListener { value, error ->
            if (error != null) {
                Toast.makeText(context, error.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                if (value != null) {
                    if (!value.isEmpty) {
                        val documents = value.documents
                        for (document in documents) {
                            val id = document.get("id")
                            val date = document.get("date") as Timestamp
                            val note = document.get("note") as String
                            val documentId = document.get("documentId")
                            val noteList = Note(id.toString(), documentId.toString() ,date, note)
                            noteArrayList.add(noteList)
                            println(document.get("id"))
                        }
                        notesAppAdapter.notesList = noteArrayList
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.exit_menu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.signout) {
            auth.signOut()
            findNavController().navigate(R.id.action_notesFragment_to_loginFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun adapterClickListener() {
        getNotes()
    }

}